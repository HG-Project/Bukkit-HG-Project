package me.etblaky.hg.Kit.Kits;

import me.etblaky.hg.Kit.Kit;
import me.etblaky.hg.Kit.KitBase;
import me.etblaky.hg.Lobby.Lobby;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

/**
 * Created by ETblaky on 05/11/2016.
 */
public class Barbarian extends KitBase {

    public ItemStack[] items = new ItemStack[] {mark(is(Material.WOOD_SWORD, 1))};
    public String name = "Barbarian";

    public Kit k;

    public Barbarian(){ }

    public Barbarian(Kit k){
        this.k = k;
    }

    public ItemStack[] getItems(){
        return items;
    }

    public String getName() {
        return name;
    }

    public void setAbilities(Player p){ }

    public void removeAbilities(Player p){ }

    @EventHandler
    public void onPlayerDeath(EntityDeathEvent e){
        if(!(e.getEntity() instanceof Player)) return;
        Player dead = (Player) e.getEntity();

        Player killer = dead.getKiller();
        if(killer == null) return;

        k = setKit(k, killer);
        if(k == null) return;

        if(!k.isKit((Player) e.getEntity(), Kit.Kits.BARBARIAN)) return;
        if(!k.getLobby().state.equals(Lobby.MatchState.GAME)) return;

        for(int i = 0; i < killer.getInventory().getSize(); i ++){
            ItemStack is = killer.getInventory().getItem(i);

            if(is == null) return;
            if(is.getItemMeta().getLore() == null) return;

            for(String s : is.getItemMeta().getLore()){
                if(s.equals("A Barbarian Sword")){

                    if(is.getType().equals(Material.WOOD_SWORD)) { killer.getInventory().remove(is); killer.getInventory().setItem(i, mark(new ItemStack(Material.STONE_SWORD))); }
                    if(is.getType().equals(Material.STONE_SWORD)) { killer.getInventory().remove(is); killer.getInventory().setItem(i, mark(new ItemStack(Material.IRON_SWORD))); }
                    if(is.getType().equals(Material.IRON_SWORD)) { killer.getInventory().remove(is); killer.getInventory().setItem(i, mark(new ItemStack(Material.DIAMOND_SWORD))); }
                }
            }
        }

    }

    public ItemStack mark(ItemStack is){

        ArrayList<String> lore = new ArrayList<String>();
        lore.add("A Barbarian Sword");

        ItemMeta im = is.getItemMeta();
        im.setLore(lore);

        is.setItemMeta(im);

        return is;
    }

}
