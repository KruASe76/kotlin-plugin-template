package me.kruase.kotlin_plugin_template.commands

import me.kruase.kotlin_plugin_template.Template.Companion.mainConfig
import me.kruase.kotlin_plugin_template.util.hasPluginPermission
import org.bukkit.command.CommandSender


fun help(sender: CommandSender, args: List<String>) {
    if (!sender.hasPluginPermission("help")) throw UnsupportedOperationException()

    require(args.size <= 1)

    when (args.getOrNull(0)) {
        null ->
            mainConfig.messages.help.keys
                .filter { sender.hasPluginPermission(it.replace("-", ".")) || it == "header"}
                .forEach { sender.sendMessage(arrayOf(mainConfig.messages.help[it])) }
        in mainConfig.messages.help.keys - "header" -> sender.sendMessage(arrayOf(mainConfig.messages.help[args[0]]))
        else -> throw IllegalArgumentException()
    }
}
