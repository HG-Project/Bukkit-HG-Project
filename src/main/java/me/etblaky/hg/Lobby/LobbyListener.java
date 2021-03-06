package me.etblaky.hg.Lobby;

import me.etblaky.hg.Game.Game;
import me.etblaky.vip.VipSys;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by ETblaky on 02/11/2016.
 */
public class LobbyListener implements Listener{

    public static Lobby lobby;

    public static void setUp(Lobby l){
        lobby = l;
    }

    @EventHandler
    public void clickItem(PlayerInteractEvent e){

        if(e.getPlayer().getItemInHand().getType().equals(Material.WATCH)) {
            if (!e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals("Voltar")) return;

            for (Game g : Game.getGames()) {
                for (Player p : g.getLobby().getPlayers()) {
                    if (p.getUniqueId().equals(e.getPlayer().getUniqueId())) {
                        g.getLobby().removePlayer(e.getPlayer());
                        return;
                    }
                }
            }
        }

        if(e.getPlayer().getItemInHand().getType().equals(Material.CHEST)) {
            if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName() == null) return;
            if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals("Kit")){
                for (Game g : Game.getGames()) {
                    for (Player p : g.getLobby().getPlayers()) {
                        if (p.getUniqueId().equals(e.getPlayer().getUniqueId())) {
                            p.openInventory(lobby.getGUI().getInv());
                            return;
                        }
                    }
                }
            }

            if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals("Segundo Kit")){
                for (Game g : Game.getGames()) {
                    for (Player p : g.getLobby().getPlayers()) {
                        if (p.getUniqueId().equals(e.getPlayer().getUniqueId())) {
                            p.openInventory(lobby.getGUI().getVipInv());
                            return;
                        }
                    }
                }
            }

        }

        if(e.getPlayer().getItemInHand().getType().equals(Material.NETHER_STAR)) {
            if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName() == null) return;
            if (!e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals("Iniciar a partida")) return;

            for (Game g : Game.getGames()) {
                for (Player p : g.getLobby().getPlayers()) {
                    if (p.getUniqueId().equals(e.getPlayer().getUniqueId())) {
                        g.getLobby().stop();
                        return;
                    }
                }
            }
        }

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDie(final PlayerDeathEvent e){
        if(Lobby.playerLobby(e.getEntity()) == null) return;

        final Player player = e.getEntity();

        for(Game g : Game.getGames()){
            if(g.getLobby().getPlayers() != null)
                for(Player p : g.getLobby().getPlayers()){
                    if(p.getUniqueId().equals(player.getUniqueId())){

                        p.teleport(g.getLobby().loc);

                        for(ItemStack is : Lobby.items){

                            if(is.getType().equals(Material.WATCH)){
                                p.getInventory().setItem(8, is);
                            }
                            if(is.getType().equals(Material.CHEST)) {
                                if(is.getItemMeta().getDisplayName().equals("Kit")){
                                    p.getInventory().setItem(0, is);
                                }
                                if(is.getItemMeta().getDisplayName().equals("Segundo Kit")){
                                    if(VipSys.isVip(p))
                                        p.getInventory().setItem(1, is);
                                }
                            }
                            if(is.getType().equals(Material.NETHER_STAR)) {
                                if(p.isOp()) {
                                    p.getInventory().setItem(4, is);
                                }
                            }
                        }

                        p.updateInventory();

                    }
                }
        }
    }

    @EventHandler
    public void onPlayerLeave(final PlayerQuitEvent e){
        if(Lobby.playerLobby(e.getPlayer()) == null) return;

        Lobby.playerLobby(e.getPlayer()).removePlayer(e.getPlayer());
    }

    @EventHandler
    public void onPlayerDamage(final EntityDamageByEntityEvent e){
        if(!(e.getEntity() instanceof  Player)) return;
        if(Lobby.playerLobby((Player) e.getEntity()) == null) return;

        e.setCancelled(true);
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent e){
        if(Lobby.playerLobby(e.getPlayer()) == null) return;

        e.setCancelled(true);

        e.getPlayer().updateInventory();
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent e){
        if(Lobby.playerLobby(e.getPlayer()) == null) return;

        e.setCancelled(true);
    }

    @EventHandler
    public void onPickUpItem(PlayerPickupItemEvent e){
        if(Lobby.playerLobby(e.getPlayer()) == null) return;

        e.setCancelled(true);
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent e){
        if(Lobby.playerLobby(e.getPlayer()) == null) return;

        Player p = e.getPlayer();
        ItemStack item = e.getItemDrop().getItemStack().clone();
        e.getItemDrop().remove();
        p.getInventory().setItem(p.getInventory().getHeldItemSlot(), item);

        p.updateInventory();
    }

}
