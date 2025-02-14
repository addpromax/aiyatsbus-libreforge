package cc.polarastrum.aiyatsbus.libreforge.command

import cc.polarastrum.aiyatsbus.libreforge.AiyatsbusLibreforgePlugin
import com.willfp.eco.core.command.impl.PluginCommand
import org.bukkit.command.CommandSender

/**
 * AiyatsbusLibreforge
 * com.mcstarrysky.aiyatsbus.libreforge.command.CommandAiyatsbusLibreforge
 *
 * @author mical
 * @date 2024/8/22 13:16
 */
class CommandAiyatsbusLibreforge(
    plugin: AiyatsbusLibreforgePlugin
) : PluginCommand(plugin, "aiyatsbuslibreforge", "aiyatsbus.libreforge.command", false) {

    override fun onExecute(sender: CommandSender, args: MutableList<String>) {
        sender.sendMessage(
            plugin.langYml.getMessage("invalid-command")
        )
    }

    init {
        addSubcommand(CommandReload(plugin))
    }
}