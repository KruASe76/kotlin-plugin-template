package me.kruase.kotlin_plugin_template

import me.kruase.kotlin_plugin_template.commands.reload
import net.md_5.bungee.api.ChatColor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin


class Template : JavaPlugin() {
    companion object {
        lateinit var instance: Template
        lateinit var mainConfig: TemplateConfig

        fun sendPlayerMessage(player: Player, message: String?) {
            if (message == null)
                return

            player.sendMessage(
                "${ChatColor.GOLD}[${ChatColor.GREEN}${instance.name}${ChatColor.GOLD}]${ChatColor.RESET} $message"
            )
        }
    }

    override fun onEnable() {
        instance = this

        reload()

        getCommand("template")!!.setExecutor(TemplateCommands())

        server.pluginManager.registerEvents(TemplateEvents(), instance)
    }
}
