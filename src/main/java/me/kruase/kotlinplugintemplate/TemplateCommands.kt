package me.kruase.kotlinplugintemplate

import org.bukkit.command.TabExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.Command
import org.bukkit.entity.Player
import org.bukkit.ChatColor
import me.kruase.kotlinplugintemplate.commands.*


class TemplateCommands : TabExecutor {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        val fullArgs = args.dropLast(1)
        return when (fullArgs.getOrNull(0)) {
            null -> Template.tConfig.messages.help.keys
                .filter { sender.hasPermission("template.${it.replace("-", ".")}") } - "header"
            "help" -> when {
                sender.hasPermission("template.help") -> when (fullArgs.getOrNull(1)) {
                    null -> Template.tConfig.messages.help.keys
                        .filter { sender.hasPermission("template.${it.replace("-", ".")}") } - "header"
                    else -> listOf()
                }
                else -> listOf()
            }
            else -> listOf()
        }
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        try {
            when (args.getOrNull(0)) {
                null -> help(sender, arrayOf())
                "help" -> help(sender, args.drop(1).toTypedArray())
                "reload" -> {
                    if (!sender.hasPermission("template.reload")) throw UnsupportedOperationException()
                    Template.tConfig = getTemplateConfig(Template.instance)
                }
            }
        } catch (e: UnsupportedOperationException) {
            sender.sendMessage(
                "${ChatColor.RED}${Template.tConfig.messages.error["no-permission"] ?: "Error: no-permission"}"
            )
        } catch (e: IllegalArgumentException) {
            sender.sendMessage(
                "${ChatColor.RED}${Template.tConfig.messages.error["invalid-command"] ?: "Error: invalid-command"}"
            )
        } catch (e: IllegalStateException) {
            // "Unknown error" should never happen
            sender.sendMessage("${ChatColor.RED}${e.message ?: "Unknown error"}")
        }

        return true
    }

    private fun playerOnly(sender: CommandSender, run: () -> Unit) {
        when (sender) {
            is Player -> run()
            else -> sender.sendMessage(
                "${ChatColor.RED}${Template.tConfig.messages.error["player-only"] ?: "Error: player-only"}"
            )
        }
    }
}
