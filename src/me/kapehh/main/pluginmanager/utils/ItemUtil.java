package me.kapehh.main.pluginmanager.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by karen on 08.09.2016.
 */
public class ItemUtil {

    // Сохраняет ItemStack (со всеми его свойствами) в массив byte
    public static byte[] serialize(ItemStack itemStack) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BukkitObjectOutputStream bukkitObjectOutputStream = new BukkitObjectOutputStream(byteArrayOutputStream);
        bukkitObjectOutputStream.writeObject(itemStack);
        bukkitObjectOutputStream.flush();
        bukkitObjectOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    // Восстанавливает ItemStack из массива byte
    public static ItemStack deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        BukkitObjectInputStream bukkitObjectInputStream = new BukkitObjectInputStream(byteArrayInputStream);
        ItemStack ret = (ItemStack) bukkitObjectInputStream.readObject();
        bukkitObjectInputStream.close();
        return ret;
    }

    public static ItemStack parseItem(String item) {
        String type;
        short damage;
        item = item.trim();

        if (item.matches("^[^:]+(:[0-9]+)?$")) {
            String[] p = item.split(":");

            type = p[0];
            if (p.length > 1) {
                damage = Short.valueOf(p[1]);
            } else {
                damage = 0;
            }

            return new ItemStack(Material.valueOf(type), 1, damage);
        }

        return null;
    }

    public static String toString(ItemStack item) {
        if (item.getDurability() != 0)
            return String.format("%s:%s", item.getType(), item.getDurability());
        else
            return String.valueOf(item.getType());
    }

}
