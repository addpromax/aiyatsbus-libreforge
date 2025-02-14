package cc.polarastrum.aiyatsbus.libreforge

import cc.polarastrum.aiyatsbus.core.Aiyatsbus
import cc.polarastrum.aiyatsbus.core.registration.modern.ModernEnchantmentRegisterer
import cc.polarastrum.aiyatsbus.libreforge.command.CommandAiyatsbusLibreforge
import cc.polarastrum.aiyatsbus.libreforge.enchant.LibreforgeEnchantLevel
import cc.polarastrum.aiyatsbus.libreforge.enchant.LibreforgeEnchants
import cc.polarastrum.aiyatsbus.libreforge.target.LibreforgeEnchantFinder
import cc.polarastrum.aiyatsbus.libreforge.target.LibreforgeEnchantFinder.clearEnchantmentCache
import cc.polarastrum.aiyatsbus.module.ingame.command.AiyatsbusCommand
import com.willfp.eco.core.Prerequisite
import com.willfp.eco.core.command.impl.PluginCommand
import com.willfp.libreforge.NamedValue
import com.willfp.libreforge.loader.LibreforgePlugin
import com.willfp.libreforge.loader.configs.ConfigCategory
import com.willfp.libreforge.registerHolderPlaceholderProvider
import com.willfp.libreforge.registerHolderProvider
import com.willfp.libreforge.registerSpecificRefreshFunction
import org.bukkit.entity.LivingEntity

/**
 * AiyatsbusLibreforgePlugin
 * com.mcstarrysky.aiyatsbus.libreforge.AiyatsbusLibreforgePlugin
 *
 * @author mical
 * @since 2024/7/20 22:30
 */
lateinit var plugin: AiyatsbusLibreforgePlugin
    private set

class AiyatsbusLibreforgePlugin : LibreforgePlugin() {

    init {
        plugin = this
    }

    override fun loadConfigCategories(): List<ConfigCategory> {
        return listOf(
            LibreforgeEnchants
        )
    }

    override fun handleEnable() {
        registerHolderProvider(LibreforgeEnchantFinder.toHolderProvider())

        registerSpecificRefreshFunction<LivingEntity> {
            it.clearEnchantmentCache()
        }

        registerHolderPlaceholderProvider<LibreforgeEnchantLevel> { it, _ ->
            listOf(
                NamedValue("level", it.level)
            )
        }
    }

    override fun handleAfterLoad() {
        if (Prerequisite.HAS_1_21.isMet) {
            (Aiyatsbus.api().getEnchantmentRegisterer() as ModernEnchantmentRegisterer).replaceRegistry()
        }

        AiyatsbusCommand.init()
    }

    override fun loadPluginCommands(): MutableList<PluginCommand> {
        return mutableListOf(
            CommandAiyatsbusLibreforge(this)
        )
    }
}