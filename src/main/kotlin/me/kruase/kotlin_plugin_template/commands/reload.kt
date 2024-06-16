package me.kruase.kotlin_plugin_template.commands

import me.kruase.kotlin_plugin_template.Template.Companion.instance
import me.kruase.kotlin_plugin_template.Template.Companion.mainConfig
import me.kruase.kotlin_plugin_template.getMainConfig
import me.kruase.kotlin_plugin_template.util.hasPluginPermission
import org.bukkit.command.CommandSender


fun reload(sender: CommandSender, args: Array<out String>) {
    if (!sender.hasPluginPermission("reload")) throw UnsupportedOperationException()

    if (args.isNotEmpty()) throw IllegalArgumentException()

    mainConfig = instance.getMainConfig()
}


fun reload() {
    reload(instance.server.consoleSender, emptyArray())
}
