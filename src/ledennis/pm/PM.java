package ledennis.pm;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PM extends JavaPlugin {
	
	@Override
	public void onEnable() {
		new CoreCommands(this);
	}
	
	public PluginManager getPluginManager() {
		return getServer().getPluginManager();
	}
	
	public void help(CommandSender p) {
		p.sendMessage("§8[§cPluginManager§8] §7Syntax help for core commands:");
		p.sendMessage("§a/pm list §7- §eList of all plugins");
		p.sendMessage("§a/pm enable <plugin> §7- §eEnable a plugin");
		p.sendMessage("§a/pm disable <plugin> §7- §eDisable a plugin");
		p.sendMessage("§a/pm load <file> §7- §eLoad a plugin; optional from another directory");
		p.sendMessage("§a/pm depend <plugin> §7- §eList dependencies of a plugin");
	}
	
	public void noPerms(CommandSender p) {
		p.sendMessage("§8[§cPluginManager§8] §7You don't have permission to do that!");
	}
	
}
