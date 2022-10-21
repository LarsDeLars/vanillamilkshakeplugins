package augustc.xyz.playermanager.serialisation;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class InventorySerialisation {

    public InventorySerialisation() {

    }

    public static String serialise(Inventory inventory){
        ItemStack[] mainInvContents = null;

        for (ItemStack item : inventory.getContents()) {
            if (item != null) {
                mainInvContents = inventory.getContents();
                break;
            }
        }
        return toBase64(mainInvContents);
    }

    public static Inventory deserialise(String data, Player p, String title, Integer size){

        Inventory inv = Bukkit.createInventory(p, size, title);

        Integer currentSlot = 0;
        for(ItemStack itemStack : stacksFromBase64(data)){
            if(itemStack != null){
                inv.setItem(currentSlot, itemStack);
            }
            currentSlot++;
            if(currentSlot > size){
                return inv;
            }
        }
        return inv;
    }


    private static String toBase64(ItemStack[] contents) {
        boolean convert = false;

        if(contents == null){
            return null;
        }

        for (ItemStack item : contents) {
            if (item != null) {
                convert = true;
                break;
            }
        }

        if (convert) {
            try {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

                dataOutput.writeInt(contents.length);

                for (ItemStack stack : contents) {
                    dataOutput.writeObject(stack);
                }
                dataOutput.close();
                byte[] byteArr = outputStream.toByteArray();
                return Base64Coder.encodeLines(byteArr);
            } catch (Exception e) {
                throw new IllegalStateException("Unable to save item stacks.", e);
            }
        }

        return null;
    }

    private static ItemStack[] getInventoryItems(String base64) {
        ItemStack[] inv = null;

        try {
            inv = stacksFromBase64(base64);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return inv;
    }

    private static ItemStack[] stacksFromBase64(String data) {
        if (data == null)
            return new ItemStack[]{};

        ByteArrayInputStream inputStream = null;

        try {
            inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
        } catch (IllegalArgumentException e) {
            return new ItemStack[]{};
        }

        BukkitObjectInputStream dataInput = null;
        ItemStack[] stacks = null;

        try {
            dataInput = new BukkitObjectInputStream(inputStream);
            stacks = new ItemStack[dataInput.readInt()];
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        if (stacks == null)
            return new ItemStack[]{};

        for (int i = 0; i < stacks.length; i++) {
            try {
                stacks[i] = (ItemStack) dataInput.readObject();
            } catch (IOException | ClassNotFoundException | NullPointerException e) {

                try { dataInput.close(); } catch (IOException e1) {}
                return null;
            }
        }

        try { dataInput.close(); } catch (IOException e1) {}

        return stacks;
    }

}
