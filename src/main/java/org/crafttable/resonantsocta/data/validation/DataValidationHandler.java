package org.crafttable.resonantsocta.data.validation;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.crafttable.resonantsocta.ResonantsOcta;
import org.crafttable.resonantsocta.data.alchemical.AlchemicalMatterDefinition;
import org.crafttable.resonantsocta.data.alchemical.AlchemicalMatterManager;
import org.crafttable.resonantsocta.data.blueprint.BlueprintDefinition;
import org.crafttable.resonantsocta.data.blueprint.BlueprintManager;
import org.crafttable.resonantsocta.data.blueprint.ToolTypeManager;
import org.crafttable.resonantsocta.data.trait.ResonanceDefinition;
import org.crafttable.resonantsocta.data.trait.ResonanceManager;
import org.crafttable.resonantsocta.data.trait.TraitManager;
import org.crafttable.resonantsocta.data.trait.TraitRequirement;
import org.crafttable.resonantsocta.item.AlchemicalToolRegistry;
import org.slf4j.Logger;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = ResonantsOcta.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DataValidationHandler {
    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void onDatapackSync(OnDatapackSyncEvent event) {
        // 全てのデータパックがリロードされたタイミングで実行される
        LOGGER.info("Starting Data Validation and Cleanup...");

        validateAlchemicalMatters();
        validateBlueprints();
        validateResonances();

        LOGGER.info("Data Validation completed.");
    }

    private static void validateAlchemicalMatters() {
        for (Map.Entry<ResourceLocation, AlchemicalMatterDefinition> entry : AlchemicalMatterManager.getAll()
                .entrySet()) {
            ResourceLocation matterId = entry.getKey();
            List<String> traits = entry.getValue().getTraits();

            if (traits != null) {
                Iterator<String> it = traits.iterator();
                while (it.hasNext()) {
                    String traitString = it.next();
                    ResourceLocation traitId = new ResourceLocation(traitString);
                    if (TraitManager.get(traitId) == null) {
                        LOGGER.error(
                                "Validation Error: Invalid trait '{}' found in Alchemical Matter '{}'. Removing trait...",
                                traitString, matterId);
                        it.remove();
                    }
                }
            }
        }
    }

    private static void validateBlueprints() {
        Iterator<Map.Entry<ResourceLocation, BlueprintDefinition>> it = BlueprintManager.getAll().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<ResourceLocation, BlueprintDefinition> entry = it.next();
            ResourceLocation blueprintId = entry.getKey();
            BlueprintDefinition def = entry.getValue();

            // 1. ToolType の検証
            ResourceLocation toolTypeId = def.getToolType();

            // ToolTypeManager(データパック由来)に含まれているかチェック
            if (toolTypeId != null && ToolTypeManager.get(toolTypeId) == null) {
                LOGGER.error(
                        "Validation Error: Invalid tool_type '{}' specified in Blueprint '{}'. Removing Blueprint...",
                        toolTypeId, blueprintId);
                it.remove(); // ツールタイプが存在しない設計図は致命的なので設計図ごと除外
                continue;
            }

            // 2. 実装済みツールタイプの検証
            // データパックには存在しても、ModのJavaコードとして該当のツール挙動が実装されていなければ無効とする
            if (toolTypeId != null && !AlchemicalToolRegistry.isImplemented(toolTypeId)) {
                LOGGER.warn(
                        "Validation Guard: Tool_type '{}' specified in Blueprint '{}' is NOT IMPLEMENTED in codebase. Removing Blueprint...",
                        toolTypeId, blueprintId);
                it.remove();
                continue;
            }

            // 3. 特性の検証 (BlueprintDefinitionにtraitsリストがある場合)
            // 現在のBlueprintDefinition.javaの仕様に合わせて適宜実装（現在はtraitsがないためスキップ）
        }
    }

    private static void validateResonances() {
        Iterator<Map.Entry<ResourceLocation, ResonanceDefinition>> it = ResonanceManager.getAll().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<ResourceLocation, ResonanceDefinition> entry = it.next();
            ResourceLocation resId = entry.getKey();
            ResonanceDefinition def = entry.getValue();

            boolean isValid = true;
            isValid &= checkResonanceRequirements(resId, def.getRequiredTraits(), "required_traits");
            isValid &= checkResonanceRequirements(resId, def.getRemoveTraits(), "remove_traits");
            isValid &= checkResonanceRequirements(resId, def.getAddTraits(), "add_traits");

            if (!isValid) {
                // 不正な特性を含む共鳴ルールは、思わぬバグの温床となるためルールごと破棄する
                LOGGER.error(
                        "Validation Error: Invalid traits found in Resonance '{}'. Removing resonance rule completely.",
                        resId);
                it.remove();
            }
        }
    }

    private static boolean checkResonanceRequirements(ResourceLocation resId, List<TraitRequirement> reqs,
            String category) {
        if (reqs == null)
            return true;
        for (TraitRequirement req : reqs) {
            ResourceLocation traitId = req.getTraitId();
            if (traitId != null && TraitManager.get(traitId) == null) {
                LOGGER.error("  -> [{}] Invalid trait: {}", category, traitId);
                return false;
            }
        }
        return true;
    }
}
