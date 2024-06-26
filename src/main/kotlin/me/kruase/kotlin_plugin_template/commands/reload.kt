package me.kruase.kotlin_plugin_template.commands

import me.kruase.kotlin_plugin_template.Template.Companion.instance
import me.kruase.kotlin_plugin_template.Template.Companion.mainConfig
import me.kruase.kotlin_plugin_template.Template.Companion.sendPlayerMessage
import me.kruase.kotlin_plugin_template.getMainConfig
import me.kruase.kotlin_plugin_template.util.hasPluginPermission
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player


fun reload(sender: CommandSender) {
    if (!sender.hasPluginPermission("reload")) throw UnsupportedOperationException()

    mainConfig = instance.getMainConfig()

    if (sender is Player)
        sendPlayerMessage(sender, mainConfig.messages.info["config-loaded"])
}


fun reload() {
    reload(instance.server.consoleSender)
}
