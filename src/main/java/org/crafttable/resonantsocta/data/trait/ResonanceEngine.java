package org.crafttable.resonantsocta.data.trait;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class ResonanceEngine {

    /**
     * 投入された特性リスト（traitId -> count）と現在の共鳴ルール（ResonanceManager）をもとに、
     * 打消・変異・増幅といった干渉処理を適用した最終的な特性リストを計算して返します。
     *
     * @param inputTraits 初期状態の特性リスト（投入素材や設計図の固定特性等）
     * @return 干渉処理適用後の最終特性リスト
     */
    public static Map<ResourceLocation, Integer> executeResonance(Map<ResourceLocation, Integer> inputTraits) {
        // 現在の特性をコピー
        Map<ResourceLocation, Integer> currentTraits = new HashMap<>(inputTraits);
        boolean changed;

        // 共鳴ルールは優先度順にソートして評価する
        java.util.List<ResonanceDefinition> rules = ResonanceManager.getSortedResonances();

        int maxIterations = 100; // 無限ループ防止用
        int iterations = 0;

        do {
            changed = false;
            iterations++;

            for (ResonanceDefinition rule : rules) {
                if (matchesRequirements(currentTraits, rule.getRequiredTraits())) {
                    // ルールにマッチした場合、指定された特性を削除する
                    for (TraitRequirement req : rule.getRemoveTraits()) {
                        removeTraitCount(currentTraits, req.getTraitId(), req.getCount());
                    }
                    // 指定された新しい特性を追加する
                    for (TraitRequirement req : rule.getAddTraits()) {
                        addTraitCount(currentTraits, req.getTraitId(), req.getCount());
                    }
                    changed = true;
                    // 一度適用したら、現在のリスト状態が変わるので最初から評価し直す（再帰的処理）
                    break;
                }
            }
        } while (changed && iterations < maxIterations);

        return currentTraits;
    }

    private static boolean matchesRequirements(Map<ResourceLocation, Integer> stats,
            java.util.List<TraitRequirement> requirements) {
        for (TraitRequirement req : requirements) {
            int currentCount = stats.getOrDefault(req.getTraitId(), 0);
            if (currentCount < req.getCount()) {
                return false;
            }
        }
        return true;
    }

    private static void removeTraitCount(Map<ResourceLocation, Integer> stats, ResourceLocation traitId, int count) {
        int current = stats.getOrDefault(traitId, 0);
        int result = Math.max(0, current - count);
        if (result == 0) {
            stats.remove(traitId);
        } else {
            stats.put(traitId, result);
        }
    }

    private static void addTraitCount(Map<ResourceLocation, Integer> stats, ResourceLocation traitId, int count) {
        stats.put(traitId, stats.getOrDefault(traitId, 0) + count);
    }
}
