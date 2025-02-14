@file:Suppress("UNUSED_PARAMETER", "DuplicatedCode")

package cc.polarastrum.aiyatsbus.libreforge.enchant

import cc.polarastrum.aiyatsbus.core.Aiyatsbus
import cc.polarastrum.aiyatsbus.core.registration.modern.ModernEnchantmentRegisterer
import cc.polarastrum.aiyatsbus.libreforge.AiyatsbusLibreforgePlugin
import cc.polarastrum.aiyatsbus.libreforge.feature.MigrateEcoEnchants
import com.willfp.eco.core.Prerequisite
import com.willfp.eco.core.config.Configs
import com.willfp.eco.core.config.interfaces.Config
import com.willfp.libreforge.loader.LibreforgePlugin
import com.willfp.libreforge.loader.configs.RegistrableCategory
import java.io.File

/**
 * AiyatsbusLibreforge
 * com.mcstarrysky.aiyatsbus.libreforge.enchant.LibreforgeEnchants
 *
 * @author mical
 * @date 2024/8/21 19:47
 */
object LibreforgeEnchants : RegistrableCategory<LibreforgeAiyatsbusEnchant>("aiyatsbus_enchants", "enchantments") {

    override fun beforeReload(plugin: LibreforgePlugin) {
        if (Prerequisite.HAS_1_20_3.isMet) {
            (Aiyatsbus.api().getEnchantmentRegisterer() as ModernEnchantmentRegisterer).replaceRegistry()
        }
    }

    override fun afterReload(plugin: LibreforgePlugin) {
        plugin as AiyatsbusLibreforgePlugin
        sendPrompts(plugin)
    }

    override fun acceptPreloadConfig(plugin: LibreforgePlugin, id: String, config: Config) {
        if (Aiyatsbus.api().getEnchantmentManager().getEnchant(id) != null) {
            return
        }
        val file = File(plugin.dataFolder, "enchantments")
            .walk()
            .firstOrNull { file -> file.nameWithoutExtension == id }!!

        if (!config.has("basic")) {
            MigrateEcoEnchants.migrate(file, id, config)
        }

        plugin as AiyatsbusLibreforgePlugin

        val newConfig = Configs.fromFile(file)
        if (!newConfig.has("effects")) {
            return
        }
        try {
            val enchant = LibreforgeAiyatsbusEnchant(
                id,
                file,
                newConfig,
                plugin
            )
            doRegister(plugin, enchant)
        } catch (e: MissingDependencyException) {
            // Ignore missing dependencies for preloaded enchants
        } catch (e: Throwable) {
            plugin.logger.warning("Failed to loaded enchantment $id for reason: $e")
        }
    }

    override fun acceptConfig(plugin: LibreforgePlugin, id: String, config: Config) {
        if (Aiyatsbus.api().getEnchantmentManager().getEnchant(id) != null) {
            return
        }
        val file = File(plugin.dataFolder, "enchantments")
            .walk()
            .firstOrNull { file -> file.nameWithoutExtension == id }!!

        if (!config.has("basic")) {
            MigrateEcoEnchants.migrate(file, id, config)
        }

        plugin as AiyatsbusLibreforgePlugin

        val newConfig = Configs.fromFile(file)
        if (!newConfig.has("effects")) {
            return
        }
        try {
            val enchant = LibreforgeAiyatsbusEnchant(
                id,
                file,
                newConfig,
                plugin
            )
            doRegister(plugin, enchant)
        } catch (e: MissingDependencyException) {
            addPluginPrompt(plugin, e.plugins)
        } catch (e: Throwable) {
            plugin.logger.warning("Failed to loaded enchantment $id for reason: $e")
        }
    }

    override fun clear(plugin: LibreforgePlugin) {
        for (enchant in registry.values()) {
            Aiyatsbus.api().getEnchantmentManager().unregister(enchant)
        }
        registry.clear()
    }

    private fun doRegister(plugin: AiyatsbusLibreforgePlugin, enchant: LibreforgeAiyatsbusEnchant) {
        Aiyatsbus.api().getEnchantmentManager().register(enchant)
        registry.register(enchant)
    }
}