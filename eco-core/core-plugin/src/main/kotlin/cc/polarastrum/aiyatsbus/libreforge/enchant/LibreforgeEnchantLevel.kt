package cc.polarastrum.aiyatsbus.libreforge.enchant

import com.google.common.base.Objects
import com.willfp.eco.core.EcoPlugin
import com.willfp.libreforge.Holder
import com.willfp.libreforge.conditions.ConditionList
import com.willfp.libreforge.effects.EffectList

/**
 * AiyatsbusLibreforge
 * com.mcstarrysky.aiyatsbus.libreforge.LibreforgeEnchantLike
 *
 * @author mical
 * @date 2024/8/21 19:34
 */
class LibreforgeEnchantLevel(
    val enchant: LibreforgeAiyatsbusEnchant,
    val level: Int,
    override val effects: EffectList,
    override val conditions: ConditionList,
    plugin: EcoPlugin
) : Holder {

    override val id = plugin.createNamespacedKey("${enchant.id}_$level")

    override fun equals(other: Any?): Boolean {
        if (other !is LibreforgeEnchantLevel) {
            return false
        }

        return this.id == other.id
    }

    override fun toString(): String {
        return id.toString()
    }

    override fun hashCode(): Int {
        return Objects.hashCode(this.id)
    }
}