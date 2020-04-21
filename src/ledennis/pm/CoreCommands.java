package ledennis.pm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class CoreCommands implements CommandExecutor {
	
	private PM pl;
	public CoreCommands(PM pl) {
		this.pl = pl;
		pl.getCommand("pm").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender p, Command cmd, String label, String[] args) {
		
		if(!p.hasPermission("pm.core")) {
			pl.noPerms(p);
		} else {
			if(args.length == 0) {
				pl.help(p);
			} else if(args.length == 1) {
				if(args[0].equalsIgnoreCase("list")) {
					List<String> enabled = new ArrayList<>();
					List<String> disabled = new ArrayList<>();
					for(Plugin plugin : pl.getPluginManager().getPlugins()) {
						if(plugin.isEnabled()) {
							enabled.add(plugin.getName());
						} else {
							disabled.add(plugin.getName());
						}
					}
					p.sendMessage("§8[§aPluginManager§8] §7Enabled plugins:");
					enabled.forEach(name -> {
						p.sendMessage("§a" + name + "§7, ");
					});
					p.sendMessage("§8[§aPluginManager§8] §7Disabled plugins:");
					disabled.forEach(name -> {
						p.sendMessage("§c" + name + "§7, ");
					});
				} else {
					pl.help(p);
				}
			} else if(args.length == 2) {
				if(args[0].equalsIgnoreCase("enable")) {
					boolean b = changePluginStatus(args[1], true);
					if(b) {
						p.sendMessage("§8[§aPluginManager§8] §7Success.");
					} else {
						p.sendMessage("§8[§cPluginManager§8] §7An error occured.");
					}
				} else if(args[0].equalsIgnoreCase("disable")) {
					boolean b = changePluginStatus(args[1], false);
					if(b) {
						p.sendMessage("§8[§aPluginManager§8] §7Success.");
					} else {
						p.sendMessage("§8[§cPluginManager§8] §7An error occured.");
					}
				} else if(args[0].equalsIgnoreCase("load")) {
					boolean b = loadPlugin(args[1]);
					if(b) {
						p.sendMessage("§8[§aPluginManager§8] §7Success.");
					} else {
						p.sendMessage("§8[§cPluginManager§8] §7An error occured.");
					}
				} else {
					pl.help(p);
				}
			} else {
				pl.help(p);
			}
		}
		
		return true;
	}
	
	/**
	 * Load a plugin
	 */
	private boolean loadPlugin(String filename) {
		File file = new File("plugins", filename);
		if(!file.exists()) return false;
		if(!file.isFile()) return false;
		try {
			Plugin plugin = pl.getPluginManager().loadPlugin(file);
			return plugin != null;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Enable/disable a plugin
	 */
	private boolean changePluginStatus(String pluginName, boolean status) {
		Plugin plugin = pl.getPluginManager().getPlugin(pluginName);
		if(plugin == null) return false;
		if(status) {
			if(plugin.isEnabled()) return false;
			pl.getPluginManager().enablePlugin(plugin);
			return true;
		} else {
			if(!plugin.isEnabled()) return false;
			pl.getPluginManager().disablePlugin(plugin);
			return true;
		}
	}

}
