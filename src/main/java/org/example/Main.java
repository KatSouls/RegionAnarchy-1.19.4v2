package org.example;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    public static int CoordPlaceBlockX;
    public static int CoordPlaceBlockY;
    public static int CoordPlaceBlockZ;
    //кб сосать
    //пипокс сосать

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this); //при включении сервера срабатывают все ивенты
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) { //ивент вхождения игрока на сервер
        event.getPlayer().sendMessage(Component.text("Hello, " + event.getPlayer().getName() + "!")); //при входе в игру в чат выводится приветствие
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event) { //ивент на постройку блока
        if(event.getBlockPlaced().getType() == Material.DIAMOND_ORE){ //проверка на то поставил ли игрок алмазную руду
            World world = BukkitAdapter.adapt(event.getPlayer().getWorld()); //получение мира в котором находится игрок
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer(); //контейнер для всех регионов
            RegionManager regions = container.get(world); //контейнер со всеми регионами мира world

            CoordPlaceBlockX = event.getBlockPlaced().getX(); //получаем координату поставленного блока
            CoordPlaceBlockY = event.getBlockPlaced().getY(); //получаем координату поставленного блока
            CoordPlaceBlockZ = event.getBlockPlaced().getZ(); //получаем координату поставленного блока
            BlockVector3 min = BlockVector3.at(CoordPlaceBlockX-25, CoordPlaceBlockY-25, CoordPlaceBlockZ-25); //получаем первую точку для привата
            BlockVector3 max = BlockVector3.at(CoordPlaceBlockX+25, CoordPlaceBlockY+25, CoordPlaceBlockZ+25); //получаем вторую точку для привата
            var RegionName = "" + CoordPlaceBlockX + "" + CoordPlaceBlockY + "" + CoordPlaceBlockZ; //переменная для названия региона

            ProtectedRegion region = new ProtectedCuboidRegion(RegionName, min, max); //создаем наш регион
            regions.addRegion(region); //добавляем его в наш контейнер
            event.getPlayer().sendMessage(Component.text("\u0412\u044B \u0441\u043E\u0437\u0434\u0430\u043B\u0438 \u043C\u0430\u043B\u0435\u043D\u044C\u043A\u0438\u0439 \u0440\u0435\u0433\u0438\u043E\u043D!"));

        }
    }

//    @EventHandler
//    public void onBlockBreakEvent(BlockBreakEvent event) {
//        if(event.getBlock().getX() == CoordPlaceBlockX && event.getBlock().getY() == CoordPlaceBlockY &&
//                event.getBlock().getZ() == CoordPlaceBlockZ) {
//            regions.removeRegion("mall", RemovalStrategy.UNSET_PARENT_IN_CHILDREN);
//        }
//    }
}