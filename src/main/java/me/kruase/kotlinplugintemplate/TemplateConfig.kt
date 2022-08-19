package me.kruase.kotlinplugintemplate

import java.io.File
import org.bukkit.plugin.Plugin
import org.bukkit.configuration.file.FileConfiguration


data class TemplateConfig(private val config: FileConfiguration) {
    val messages = MessagesConfig(config)
}

fun getTemplateConfig(plugin: Plugin): TemplateConfig {
    return try {
        plugin.saveDefaultConfig()
        plugin.reloadConfig()
        TemplateConfig(plugin.config)
    } catch (e: Exception) {
        when (e) {
            is NullPointerException, is NumberFormatException -> {
                newDefaultConfig(plugin)
                TemplateConfig(plugin.config)
            }
            else -> throw e
        }
    }.also { plugin.logger.info("Config loaded!") }
}

fun newDefaultConfig(plugin: Plugin) {
    plugin.logger.severe("Invalid Template config detected! Creating a new one (default)...")
    File(plugin.dataFolder, "config.yml").renameTo(
        File(plugin.dataFolder, "config.yml.old-${System.currentTimeMillis()}")
    )
    plugin.saveDefaultConfig()
    plugin.reloadConfig()
    plugin.logger.info("New (default) config created!")
}


data class MessagesConfig(private val config: FileConfiguration) {
    val help: Map<String, String> = config.getConfigurationSection("messages.help")!!
        .getKeys(false).associateWith { config.getString("messages.help.$it")!! }
    val error: Map<String, String> = config.getConfigurationSection("messages.error")!!
        .getKeys(false).associateWith { config.getString("messages.error.$it")!! }
}
