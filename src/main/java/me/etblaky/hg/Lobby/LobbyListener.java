package me.etblaky.hg.Lobby;

import me.etblaky.hg.Game.Game;
import me.etblaky.hg.Kit.KitGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

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

        if(e.getPlayer().getItemInHand().getType().equals(Material.STICK)) {
            if (!e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals("Kits")) return;

            for (Game g : Game.getGames()) {
                for (Player p : g.getLobby().getPlayers()) {
                    if (p.getUniqueId().equals(e.getPlayer().getUniqueId())) {
                        p.openInventory(new KitGUI(lobby.getKit()).getInv());
                        return;
                    }
                }
            }
        }

    }

}
