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
					boolean b = a(args[1], true);
					if(b) {
						p.sendMessage("§8[§aPluginManager§8] §7Success.");
					} else {
						p.sendMessage("§8[§cPluginManager§8] §7An error occured.");
					}
				} else if(args[0].equalsIgnoreCase("disable")) {
					boolean b = a(args[1], false);
					if(b) {
						p.sendMessage("§8[§aPluginManager§8] §7Success.");
					} else {
						p.sendMessage("§8[§cPluginManager§8] §7An error occured.");
					}
				} else if(args[0].equalsIgnoreCase("load")) {
					boolean b = a(args[1]);
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
	private boolean a(String p0) {
		File file = new File("plugins", p0);
		if(!file.exists()) return false;
		if(!file.isFile()) return false;
		try {
			Plugin var0 = pl.getPluginManager().loadPlugin(file);
			return var0 != null;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Enable/disable a plugin
	 */
	private boolean a(String p0, boolean p1) {
		Plugin var0 = pl.getPluginManager().getPlugin(p0);
		if(var0 == null) return false;
		if(p1) {
			if(var0.isEnabled()) return false;
			pl.getPluginManager().enablePlugin(var0);
			return true;
		} else {
			if(!var0.isEnabled()) return false;
			pl.getPluginManager().disablePlugin(var0);
			return true;
		}
	}

}
