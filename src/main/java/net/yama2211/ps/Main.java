/*

  @author : yamagami2211 < https://yama2211.biz | https://twitter.com/yamagami2211_02 >

  ver: 1.0.0.21 | ブロックの角に飛ぶのを直したい....
  ver: 1.0.0.20 | ブロックの角に飛ぶのを直したい....
  ver: 1.0.0.19 | ブロックの角に飛ぶのを直したい....
  ver: 1.0.0.18 | ブロックの角に飛ぶのを直したい....
  ver: 1.0.0.17 | テレポートコマンド(/ps tp)が使えねぇ...

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
	    if(cmd.getName().equalsIgnoreCase("pointtp"))
	    {
 	       if(args.length == 0)
 	       {
 	    	  PluginDescriptionFile yml = getDescription();
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "===buildblock===");
				sender.sendMessage(ChatColor.GREEN + "PluginVersion : " + ChatColor.DARK_PURPLE + yml.getVersion());
				sender.sendMessage(ChatColor.AQUA + "/pointtp setpoint" + ChatColor.WHITE + ": ポイントを設定します。");
				sender.sendMessage(ChatColor.AQUA + "/pointtp tp" + ChatColor.WHITE + ": ポイントにテレポートします。");
 	       }
 	       if(args.length == 1)
 	       {
 	    	  if(args[0].equalsIgnoreCase("setpoint"))
 	    	  {
 	    		 if ((sender.hasPermission("pointtp.setpoint")) || (sender.isOp())) {
 	    		 if (sender instanceof Player) {

 			Player player = (Player)sender;


 	          getConfig().set(args[0] + ".World", player.getLocation().getWorld().getName() );
 	          getConfig().set("point" + ".X", player.getLocation().getBlockX() );
 	          getConfig().set("point" + ".Y", player.getLocation().getBlockY() );
 	          getConfig().set("point" + ".Z", player.getLocation().getBlockZ() );
 	          getConfig().set("point" + ".Yaw",player.getLocation().getYaw() );
 	          getConfig().set("point" + ".Pitch", player.getLocation().getPitch() );
 			saveConfig();
			sender.sendMessage(ChatColor.AQUA + "[PointTP] " + ChatColor.GREEN + "座標をconfigに記録しました");
 			} else {
    			 sender.sendMessage("このコマンドは" + ChatColor.RED + "コンソール" + ChatColor.RESET + "から実行することはできません。");
    			 }

 	    		 }
 	    	  }

 	    	  if(args[0].equalsIgnoreCase("tp"))
 	    	  {
 	    		 if ((sender.hasPermission("pointtp.teleport")) || (sender.isOp())) {
 	    		 if (sender instanceof Player) {

 			Player player = (Player)sender;
 			//boolean age = args.length == 2;

            float yaw = (float) getConfig().getDouble("point"+".Yaw");
            float pitch = (float) getConfig().getDouble("point"+".Pitch");
            double x = getConfig().getDouble("point"+".X") + 0.5;
            double y = getConfig().getDouble("point"+".Y");
            double z = getConfig().getDouble("point"+".Z") + 0.5;
 			World w = Bukkit.getServer().getWorld(getConfig().getString("point"+".World"));

 			Location point = new Location (w, x, y, z);
 			point.setYaw(yaw);
 			player.getLocation().setPitch(pitch);

 			player.teleport(point);
 			sender.sendMessage(ChatColor.AQUA + "[PointTP] " + ChatColor.GREEN + "テレポートしました。");
 	    		 } else {
 	    			 sender.sendMessage("このコマンドは" + ChatColor.RED + "コンソール" + ChatColor.RESET + "から実行することはできません。");
 	    			 }
 	    		 }
 	    	  }

 	    	  if(args[0].equalsIgnoreCase("reload"))
 	    	  {
 	    		 if ((sender.hasPermission("pointtp.cofreload")) || (sender.isOp())) {
 	    		 reloadConfig();
 	    		 sender.sendMessage(ChatColor.AQUA + "[PointTP] " + ChatColor.GREEN + "Configをリロードしました。");
 	    	  }
 	    	  }

	    }

	}

return true;
	}

	@EventHandler
	public void onJoinEvent(PlayerJoinEvent e) {
		if(getConfig().getBoolean("JoinTP") == true) {

			Player player = e.getPlayer();
            float yaw = (float) getConfig().getDouble("point"+".Yaw");
            float pitch = (float) getConfig().getDouble("point"+".Pitch");
            double x = getConfig().getDouble("point"+".X") + 0.5;
            double y = getConfig().getDouble("point"+".Y");
            double z = getConfig().getDouble("point"+".Z") + 0.5;
 			World w = Bukkit.getServer().getWorld(getConfig().getString("point"+".World"));

 			Location point = new Location (w, x, y, z);
 			point.setYaw(yaw);
 			player.getLocation().setPitch(pitch);

 			player.teleport(point);
		}
	}


}
