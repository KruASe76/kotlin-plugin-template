package me.kruase.kotlin_plugin_template

import me.kruase.kotlin_plugin_template.Template.Companion.instance
import me.kruase.kotlin_plugin_template.Template.Companion.userConfig
import org.bukkit.command.TabExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.Command
import org.bukkit.entity.Player
import org.bukkit.ChatColor
import me.kruase.kotlin_plugin_template.commands.*
import me.kruase.kotlin_plugin_template.util.hasPluginPermission


class TemplateCommands : TabExecutor {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        val fullArgs = args.dropLast(1)
        return when (fullArgs.getOrNull(0)) {
            null ->
                userConfig.messages.help.keys
                    .filter { sender.hasPluginPermission(it.replace("-", ".")) } - "header"
            "help" ->
                when {
                    sender.hasPluginPermission("help") ->
                        when (fullArgs.getOrNull(1)) {
                            null ->
                                userConfig.messages.help.keys
                                    .filter {
                                        sender.hasPluginPermission(it.replace("-", "."))
                                    } - "header"
                            else -> emptyList()
                        }
                    else -> emptyList()
                }
            else -> emptyList()
        }
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        try {
            when (args.getOrNull(0)) {
                null -> help(sender, emptyList())
                "help" -> help(sender, args.drop(1))
                "reload" -> {
                    if (!sender.hasPluginPermission("reload")) throw UnsupportedOperationException()

                    userConfig = instance.getUserConfig()
                }
            }
        } catch (e: UnsupportedOperationException) {
            sender.sendMessage(
                "${ChatColor.RED}${userConfig.messages.error["no-permission"] ?: "Error: no-permission"}"
            )
        } catch (e: AssertionError) {
            sender.sendMessage(
                "${ChatColor.RED}${userConfig.messages.error["invalid-command"] ?: "Error: invalid-command"}"
            )
        } catch (e: IllegalStateException) {
            // "Unknown error" should never happen
            sender.sendMessage("${ChatColor.RED}${e.message ?: "Unknown error"}")
        }

        return true
    }

    private fun playerOnly(sender: CommandSender, command: (Player, List<String>) -> Unit, args: List<String>) {
        when (sender) {
            is Player -> command(sender, args)
            else ->
                sender
                    .sendMessage("${ChatColor.RED}${userConfig.messages.error["player-only"] ?: "Error: player-only"}")
        }
    }
}
