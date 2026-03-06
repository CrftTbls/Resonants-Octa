package org.crafttable.resonantsocta.item.base;

/**
 * すべての錬金ツール（剣、斧、弓などバニラの異なるBaseItemを継承するもの）に付与されるマーカーインターフェース。
 * 共通のメソッドシグネチャを持たせたり、instanceofでの判定に利用します。
 */
public interface IAlchemicalTool {
    // 現在はマーカーとしての役割が主ですが、将来的に特定のツール共通メソッド（例: NBT由来の基本耐久値取得）
    // を実装させる場合はここに定義します。
}
