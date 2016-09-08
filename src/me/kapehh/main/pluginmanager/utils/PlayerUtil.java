package me.kapehh.main.pluginmanager.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Karen on 12.04.2015.
 */
public class PlayerUtil {

    // Возвращает количество пустых слотов у игрока в инвентаре
    public static int getInventoryEmptySize(Inventory inventory) {
        ItemStack[] content = inventory.getContents();
        int count = 0;
        for (ItemStack itemStack : content) {
            if (itemStack == null || itemStack.getType().equals(Material.AIR)) {
                count++;
            }
        }
        return count;
    }

    // TODO: Реализовать поддержку оружий (возможно заменить ItemStack на Material[AMOUNT] как-нибудь
    // Проверяет, есть ли у игрока данный предмет (возможны проблемы с оружием, ибо у них Durability не 0 может быть)
    public static boolean isContainsItem(Inventory inventory, ItemStack it) {
        if ((it == null) || it.getType().equals(Material.AIR) || (it.getAmount() <= 0)) {
            return false;
        }
        ItemStack cit = it.clone();
        for(ItemStack i : inventory.getContents()) {
            if (i == null || i.getType().equals(Material.AIR)) continue;
            if (i.getType().equals(cit.getType()) && (i.getDurability() == cit.getDurability())) {
                cit.setAmount(cit.getAmount() - Math.min(cit.getAmount(), i.getAmount()));
            }
            if (cit.getAmount() <= 0) {
                return true;
            }
        }
        return false;
    }

    // TODO: Реализовать аналогичный метод, для снятия предметов по ID:DATA[AMOUNT]
    // Забирает у игрока ItemStack
    public static void takeItems(Inventory inventory, ItemStack it) {
        if ((it == null) || it.getType().equals(Material.AIR) || (it.getAmount() <= 0)) {
            return;
        }
        ItemStack cit = it.clone();
        ItemStack[] invContents = inventory.getContents();
        int take = 0;
        for(ItemStack i : invContents) {
            if (i == null || i.getType().equals(Material.AIR)) continue;
            if (i.getType().equals(cit.getType()) && (i.getDurability() == cit.getDurability())) {
                take = Math.min(cit.getAmount(), i.getAmount());
                cit.setAmount(cit.getAmount() - take);
                i.setAmount(i.getAmount() - take);
            }
            if (cit.getAmount() <= 0) {
                break;
            }
        }
        inventory.setContents(invContents);
    }

    public static Player getOnlinePlayer(String name) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().equalsIgnoreCase(name)) {
                return player;
            }
        }
        return null;
    }

    public static OfflinePlayer getOfflinePlayer(String name) {
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            if (player.getName().equalsIgnoreCase(name)) {
                return player;
            }
        }
        return null;
    }

    // ***
    // * Методы получают направление в котором смотрит игрок
    // ***

    private static final BlockFace[] axis = {BlockFace.SOUTH, BlockFace.WEST, BlockFace.NORTH, BlockFace.EAST};

    public static BlockFace playerFace(Player player) {
        return yawToFace(player.getEyeLocation().getYaw());
    }

    public static BlockFace yawToFace(float yaw) {
        return axis[Math.round(yaw / 90f) & 0x3];
    }

}
