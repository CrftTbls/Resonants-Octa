package org.crafttable.resonantsocta.data.alchemical;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.crafttable.resonantsocta.ResonantsOcta;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = ResonantsOcta.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AlchemicalMatterManager extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().create();
    private static final Map<ResourceLocation, AlchemicalMatterDefinition> MATTERS = new HashMap<>();

    public AlchemicalMatterManager() {
        super(GSON, "alchemical_matters");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn,
            ProfilerFiller profilerIn) {
        MATTERS.clear();
        objectIn.forEach((id, element) -> {
            try {
                AlchemicalMatterDefinition def = GSON.fromJson(element, AlchemicalMatterDefinition.class);
                if (def != null) {
                    MATTERS.put(id, def);
                }
            } catch (Exception e) {
                ResonantsOcta.LOGGER.error("Failed to parse Alchemical Matter Definition: {}", id, e);
            }
        });
        ResonantsOcta.LOGGER.info("Loaded {} Alchemical Matter Definitions.", MATTERS.size());
    }

    public static AlchemicalMatterDefinition get(ResourceLocation id) {
        return MATTERS.get(id);
    }

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new AlchemicalMatterManager());
    }
}
