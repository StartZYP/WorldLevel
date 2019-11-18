//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Br.WorldLevel;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import github.saukiya.sxlevel.SXLevel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    Map<String, Integer> Limit = new HashMap();
    String msg;
    public static SXLevel sxLevel;

    public Main() {
    }

    public static void loadData() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("SX-Level");
        sxLevel = SXLevel.class.cast(plugin);
        System.out.println(sxLevel != null);
        System.out.println("WorldLevel加载中……………………………………………………");
    }

    public void onEnable() {
        if (!this.getDataFolder().exists()) {
            this.saveDefaultConfig();
        }

        this.getConfig().getStringList("WorldLevel.List").forEach((r) -> {
            String[] s = r.split("\\|");
            this.Limit.put(s[0], Integer.parseInt(s[1]));
        });
        this.msg = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("WorldLevel.Message"));
        Bukkit.getPluginManager().registerEvents(this, this);
        loadData();
    }

    @EventHandler
    public void onChangeWorld(PlayerTeleportEvent evt) {
        if (evt.getTo().getWorld() != evt.getFrom().getWorld() && this.Limit.containsKey(evt.getTo().getWorld().getName())) {
            int lv = (Integer)this.Limit.get(evt.getTo().getWorld().getName());
            if (sxLevel.getExpDataManager().getPlayerData(evt.getPlayer()).getLevel() < lv) {
                evt.setCancelled(true);
                evt.getPlayer().sendMessage(MessageFormat.format(this.msg, lv, evt.getTo().getWorld().getName()));
            }
        }

    }
}
