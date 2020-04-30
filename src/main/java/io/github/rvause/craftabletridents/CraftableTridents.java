package io.github.rvause.craftabletridents;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;


public final class CraftableTridents extends JavaPlugin implements Listener {
    private ShapedRecipe tridentRecipe;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        tridentRecipe = new ShapedRecipe(
                new NamespacedKey(this, "trident"),
                new ItemStack(Material.TRIDENT, 1)
        );
        tridentRecipe.shape("iii", " d ", " s ");
        tridentRecipe.setIngredient('i', Material.IRON_INGOT);
        tridentRecipe.setIngredient('d', Material.DIAMOND);
        tridentRecipe.setIngredient('s', Material.STICK);

        getServer().addRecipe(tridentRecipe);

        getLogger().info("Craftable Tridents Enabled");
    }

    @EventHandler
    public void preCraft(PrepareItemCraftEvent event) {
        HumanEntity human = event.getView().getPlayer();
        if (human instanceof Player) {
            Player player = (Player)human;
            if (isRecipeEqual(event.getRecipe(), tridentRecipe)) {
                if (!player.hasPermission("ctridents.craft") && !player.isOp()) {
                    event.getInventory().setResult(null);
                    getLogger().info(player.getDisplayName() + " does not have permission to craft Tridents");
                }
            }
        }
    }

    public static boolean isRecipeEqual(Recipe recipe1, Recipe recipe2) {
        if (recipe1 instanceof ShapedRecipe && recipe2 instanceof ShapedRecipe) {
            NamespacedKey key1 = ((ShapedRecipe) recipe1).getKey();
            NamespacedKey key2 = ((ShapedRecipe) recipe2).getKey();

            return key1.equals(key2);
        }
        return false;
    }

    @Override
    public void onDisable() {
        getLogger().info("Craftable Tridents Disabled");
    }
}
