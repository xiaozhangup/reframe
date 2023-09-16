package me.eereeska.reframe.listeners;

import me.eereeska.reframe.ReFrame;
import me.eereeska.reframe.gui.ItemFrameMenuInventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.InventoryHolder;

public class PlayerInteractAtEntityEventListener implements Listener {

    private final ReFrame plugin;

    public PlayerInteractAtEntityEventListener(final ReFrame plugin) {
        this.plugin = plugin;
    }

    @EventHandler (ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onItemFrameBreak(PlayerInteractEntityEvent e) {
        if (e.getRightClicked() instanceof ItemFrame) {
            if (!e.getHand().equals(EquipmentSlot.HAND)) {
                return;
            }

            final Player p = e.getPlayer();
            final ItemFrame itemFrame = (ItemFrame) e.getRightClicked();

            if (p.isSneaking()) {
                if (p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                    e.setCancelled(true);
                    p.openInventory(new ItemFrameMenuInventoryHolder(plugin, p, itemFrame).getInventory());
                }
            } else {
                final Block blockBehind = itemFrame.getLocation().getBlock().getRelative(itemFrame.getFacing().getOppositeFace());
                final BlockState blockBehindState = blockBehind.getState();

                if (blockBehindState instanceof InventoryHolder && !itemFrame.isVisible()) {
                    e.setCancelled(true);

                    PlayerInteractEvent event = new PlayerInteractEvent(p, Action.RIGHT_CLICK_BLOCK, p.getInventory().getItemInMainHand(), blockBehind, itemFrame.getFacing());
                    Bukkit.getPluginManager().callEvent(event);
                    if (event.isCancelled()) return;

                    p.openInventory(((InventoryHolder) blockBehindState).getInventory());
                }
            }
        }
    }
}
