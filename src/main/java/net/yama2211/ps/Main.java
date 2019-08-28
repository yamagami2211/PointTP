/*

  @author : yamagami2211 < https://yama2211.biz | https://twitter.com/yamagami2211_02 >

  ver: 1.0.0.5 | Configの不具合を修正(2)
  ver: 1.0.0.4 | Configの不具合を修正
  ver: 1.0.0.3 | tpmessageを追加、改行とかの崩れ修正
  ver: 1.0.0.2 | jointpコマンドを削除(ビルドなし)
  ver: 1.0.0.1 | checkコマンドとJoinTPをコマンドで変更できるように
  ver: 1.0.0.0 | バージョンリセット / 一部修正

//*/
package net.yama2211.ps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements Listener {

	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);

		saveDefaultConfig();

	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("pointtp")) {
			if (args.length == 0) {
				PluginDescriptionFile yml = getDescription();
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "===PointTP===");
				sender.sendMessage(ChatColor.GREEN + "PluginVersion : " + ChatColor.DARK_PURPLE + yml.getVersion());
				sender.sendMessage(ChatColor.AQUA + "/pointtp setpoint" + ChatColor.WHITE + ": ポイントを設定します。");
				sender.sendMessage(ChatColor.AQUA + "/pointtp tp" + ChatColor.WHITE + ": ポイントにテレポートします。");
				sender.sendMessage(ChatColor.AQUA + "/pointtp check" + ChatColor.WHITE + ": コンフィグの内容を表示します。");
			}
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("setpoint")) {
					if ((sender.hasPermission("pointtp.setpoint")) || (sender.isOp())) {
						if (sender instanceof Player) {

							Player player = (Player) sender;

							getConfig().set("point" + ".World", player.getLocation().getWorld().getName());
							getConfig().set("point" + ".X", player.getLocation().getBlockX());
							getConfig().set("point" + ".Y", player.getLocation().getBlockY());
							getConfig().set("point" + ".Z", player.getLocation().getBlockZ());
							getConfig().set("point" + ".Yaw", player.getLocation().getYaw());
							getConfig().set("point" + ".Pitch", player.getLocation().getPitch());
							saveConfig();
							sender.sendMessage(ChatColor.AQUA + "[PointTP] " + ChatColor.GREEN + "座標をconfigに記録しました");
						} else {
							sender.sendMessage(
									ChatColor.AQUA + "[PointTP/Error] " + ChatColor.RESET + "このコマンドは" + ChatColor.RED
											+ "コンソール" + ChatColor.RESET + "から実行することはできません。");
						}

					}
				}

				if (args[0].equalsIgnoreCase("tp")) {
					if ((sender.hasPermission("pointtp.teleport")) || (sender.isOp())) {
						if (sender instanceof Player) {

							Player player = (Player) sender;
							//boolean age = args.length == 2;

							float yaw = (float) getConfig().getDouble("point" + ".Yaw");
							float pitch = (float) getConfig().getDouble("point" + ".Pitch");
							double x = getConfig().getDouble("point" + ".X") + 0.5;
							double y = getConfig().getDouble("point" + ".Y");
							double z = getConfig().getDouble("point" + ".Z") + 0.5;
							World w = Bukkit.getServer().getWorld(getConfig().getString("point" + ".World"));

							Location point = new Location(w, x, y, z);
							point.setYaw(yaw);
							player.getLocation().setPitch(pitch);

							player.teleport(point);
							sender.sendMessage(
									ChatColor.AQUA + "[PointTP] " + ChatColor.translateAlternateColorCodes('&',
											getConfig().getString("tpmessage")));
						} else {
							sender.sendMessage(
									ChatColor.AQUA + "[PointTP/Error] " + ChatColor.RESET + "このコマンドは" + ChatColor.RED
											+ "コンソール" + ChatColor.RESET + "から実行することはできません。");
						}
					}
				}

				if (args[0].equalsIgnoreCase("reload")) {
					if ((sender.hasPermission("pointtp.cofreload")) || (sender.isOp())) {
						reloadConfig();
						sender.sendMessage(ChatColor.AQUA + "[PointTP] " + ChatColor.GREEN + "Configをリロードしました。");
					}
				}

				if (args[0].equalsIgnoreCase("check")) {
					if ((sender.hasPermission("pointtp.cofcheck")) || (sender.isOp())) {
						sender.sendMessage(ChatColor.AQUA + "=== Config check ===");
						sender.sendMessage(ChatColor.AQUA + "=== コンフィグの内容を確認できます。 ===");
						sender.sendMessage(
								ChatColor.GREEN + "World: " + ChatColor.WHITE + getConfig().getString("point.World"));
						sender.sendMessage(
								ChatColor.GREEN + "X: " + ChatColor.WHITE + getConfig().getString("point.X"));
						sender.sendMessage(
								ChatColor.GREEN + "Y: " + ChatColor.WHITE + getConfig().getString("point.Y"));
						sender.sendMessage(
								ChatColor.GREEN + "Z: " + ChatColor.WHITE + getConfig().getString("point.Z"));
						sender.sendMessage(
								ChatColor.GREEN + "Yaw: " + ChatColor.WHITE + getConfig().getString("point.Yaw"));
						sender.sendMessage(
								ChatColor.GREEN + "Pitch: " + ChatColor.WHITE + getConfig().getString("point.Pitch"));
						sender.sendMessage(
								ChatColor.GREEN + "JoinTP: " + ChatColor.WHITE + getConfig().getString("JoinTP"));
					}
				}

			}

		}

		return true;
	}

	@EventHandler
	public void onJoinEvent(PlayerJoinEvent e) {
		if (getConfig().getBoolean("JoinTP") == true) {

			Player player = e.getPlayer();
			float yaw = (float) getConfig().getDouble("point" + ".Yaw");
			float pitch = (float) getConfig().getDouble("point" + ".Pitch");
			double x = getConfig().getDouble("point" + ".X") + 0.5;
			double y = getConfig().getDouble("point" + ".Y");
			double z = getConfig().getDouble("point" + ".Z") + 0.5;
			World w = Bukkit.getServer().getWorld(getConfig().getString("point" + ".World"));

			Location point = new Location(w, x, y, z);
			point.setYaw(yaw);
			player.getLocation().setPitch(pitch);

			player.teleport(point);
		}
	}

}
