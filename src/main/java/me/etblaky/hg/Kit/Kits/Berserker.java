package me.etblaky.hg.Kit.Kits;

import me.etblaky.hg.Kit.Kit;
import me.etblaky.hg.Kit.KitBase;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by ETblaky on 05/11/2016.
 */
public class Berserker extends KitBase {

    public ItemStack[] items = new ItemStack[] {};
    public String name = "Berserker";

    public Kit k;

    public Berserker(){ }

    public Berserker(Kit k){ this.k = k; }

    public ItemStack[] getItems(){ return items; }

    public String getName() { return name; }

    public void setAbilities(Player p){ }

    public void removeAbilities(Player p){
        p.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e){
        if(e.getEntity().getKiller() == null) return;
        if(!(e.getEntity() instanceof Player)) return;

        k = setKit(k, e.getEntity().getKiller());
        if(k== null) return;

        if(!k.isKit((Player) e.getEntity(), Kit.Kits.BERSERKER)) return;

        e.getEntity().getKiller().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 30 * 20, 1));

        //e.getEntity().setHealth(e.getEntity().getMaxHealth());

    }

}
