package me.kruase.kotlinplugintemplate.commands

import org.bukkit.command.CommandSender
import me.kruase.kotlinplugintemplate.Template.Companion.tConfig


fun help(sender: CommandSender, args: Array<out String>) {
    if (!sender.hasPermission("template.help")) throw UnsupportedOperationException()

    if (args.size > 1) throw IllegalArgumentException()

    when (args.getOrNull(0)) {
        null -> tConfig.messages.help.keys
            .filter { sender.hasPermission("template.${it.replace("-", ".")}") || it == "header"}
            .forEach { sender.sendMessage(arrayOf(tConfig.messages.help[it])) }
        in tConfig.messages.help.keys - "header" -> sender.sendMessage(arrayOf(tConfig.messages.help[args[0]]))
        else -> throw IllegalArgumentException()
    }
}
