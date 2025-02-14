package cc.polarastrum.aiyatsbus.libreforge.command

import com.willfp.eco.core.EcoPlugin
import com.willfp.eco.core.Prerequisite
import com.willfp.eco.core.command.impl.Subcommand
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

/**
 * AiyatsbusLibreforge
 * com.mcstarrysky.aiyatsbus.libreforge.command.CommandReload
 *
 * @author mical
 * @date 2024/8/22 13:13
 */
class CommandReload(plugin: EcoPlugin) : Subcommand(plugin, "reload", "aiyatsbus.libreforge.command.reload", false) {

    override fun onExecute(sender: CommandSender, args: MutableList<String>) {
        plugin.reload()
        Bukkit.getOnlinePlayers().forEach { player ->
            player.updateInventory()
        }
        if (Prerequisite.HAS_1_21.isMet) {
            sender.sendMessage(plugin.langYml.getMessage("reloaded-121"))
        } else if (Prerequisite.HAS_1_20_3.isMet) {
            sender.sendMessage(plugin.langYml.getMessage("reloaded-1203"))
        } else {
            sender.sendMessage(plugin.langYml.getMessage("reloaded"))
        }
    }
}