package me.kruase.kotlinplugintemplate

import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.ChatColor


class Template : JavaPlugin() {
    companion object {
        lateinit var instance: Template
        lateinit var tConfig: TemplateConfig

        fun sendGlobalMessage(message: String?) {
            if (message == null) return
            instance.server.onlinePlayers.forEach {
                it.sendMessage(
                    "${ChatColor.GOLD}[${ChatColor.GREEN}Template${ChatColor.GOLD}]${ChatColor.RESET} $message"
                )
            }
        }
    }

    override fun onEnable() {
        instance = this
        tConfig = getTemplateConfig(instance)

        getCommand("template")!!.setExecutor(TemplateCommands())
    }
}
