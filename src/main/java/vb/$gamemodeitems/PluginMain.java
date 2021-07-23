package vb.$gamemodeitems;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.plugin.java.*;
import org.bukkit.util.*;
import com.gmail.visualbukkit.stdlib.VariableManager;

public class PluginMain extends JavaPlugin implements Listener {

	private static PluginMain instance;
	private static Object localVariableScope = new Object();

	public void onEnable() {
		VariableManager.loadVariables(this);
		instance = this;
		getDataFolder().mkdir();
		getServer().getPluginManager().registerEvents(this, this);
		Object localVariableScope = new Object();
	}

	public void onDisable() {
		VariableManager.saveVariables();
	}

	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] commandArgs) {
		if (command.getName().equalsIgnoreCase("gamemodeitems")) {
			try {
				Object localVariableScope = new Object();
				((org.bukkit.entity.Player) commandSender).getInventory()
						.addItem(PluginMain.getNamedItem(org.bukkit.Material.DIAMOND_BLOCK, "Creative"));
				((org.bukkit.entity.Player) commandSender).getInventory()
						.addItem(PluginMain.getNamedItem(org.bukkit.Material.OAK_PLANKS, "Survival"));
				((org.bukkit.entity.Player) commandSender).getInventory()
						.addItem(PluginMain.getNamedItem(org.bukkit.Material.GLASS, "Spectator"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public static void procedure(String procedure, List<?> procedureArgs) throws Exception {
	}

	public static Object function(String function, List<?> functionArgs) throws Exception {
		return null;
	}

	public static List<Object> createList(Object obj) {
		List<Object> list = new ArrayList<>();
		if (obj.getClass().isArray()) {
			int length = java.lang.reflect.Array.getLength(obj);
			for (int i = 0; i < length; i++) {
				list.add(java.lang.reflect.Array.get(obj, i));
			}
		} else if (obj instanceof Collection<?>) {
			list.addAll((Collection<?>) obj);
		} else {
			list.add(obj);
		}
		return list;
	}

	public static String color(String string) {
		return string != null ? ChatColor.translateAlternateColorCodes('&', string) : null;
	}

	public static void createResourceFile(String path) {
		Path file = getInstance().getDataFolder().toPath().resolve(path);
		if (Files.notExists(file)) {
			try (InputStream inputStream = PluginMain.class.getResourceAsStream("/" + path)) {
				Files.createDirectories(file.getParent());
				Files.copy(inputStream, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static PluginMain getInstance() {
		return instance;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockPlaceEvent1(org.bukkit.event.block.BlockPlaceEvent event) throws Exception {
		if (PluginMain.checkEquals(event.getItemInHand().getItemMeta().getDisplayName(), "Creative")) {
			event.getPlayer().setGameMode(org.bukkit.GameMode.CREATIVE);
			event.getBlock().breakNaturally();
			VariableManager.setVariable(false, new java.lang.Boolean(true), "blockplaced");
		}
		if (PluginMain.checkEquals(event.getItemInHand().getItemMeta().getDisplayName(), "Survival")) {
			event.getPlayer().setGameMode(org.bukkit.GameMode.SURVIVAL);
			event.getBlock().breakNaturally();
		}
		if (PluginMain.checkEquals(event.getItemInHand().getItemMeta().getDisplayName(), "Spectator")) {
			event.getPlayer().setGameMode(org.bukkit.GameMode.SPECTATOR);
			event.getBlock().breakNaturally();
		}
		if (((java.lang.Boolean) VariableManager.getVariable(false, "blockplaced")).booleanValue()) {
			event.getPlayer().getInventory().setItem(((int) 4d), new ItemStack(org.bukkit.Material.AIR));
			event.getPlayer().getInventory().setItem(((int) 5d), new ItemStack(org.bukkit.Material.NETHER_PORTAL));
			event.getPlayer().getInventory().setItem(((int) 6d), new ItemStack(org.bukkit.Material.AIR));
		}
	}

	public static ItemStack getNamedItem(Material material, String name) {
		ItemStack item = new ItemStack(material);
		ItemMeta itemMeta = item.getItemMeta();
		if (itemMeta != null) {
			itemMeta.setDisplayName(PluginMain.color(name));
			item.setItemMeta(itemMeta);
		}
		return item;
	}

	public static boolean checkEquals(Object o1, Object o2) {
		if (o1 == null || o2 == null) {
			return false;
		}
		return o1 instanceof Number && o2 instanceof Number
				? ((Number) o1).doubleValue() == ((Number) o2).doubleValue()
				: o1.equals(o2);
	}
}
