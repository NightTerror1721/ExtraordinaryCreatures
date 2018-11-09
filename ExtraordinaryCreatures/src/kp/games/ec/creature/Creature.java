/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.games.ec.creature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import kp.games.ec.creature.ability.Ability;
import kp.games.ec.creature.attributes.Element;
import kp.games.ec.creature.attributes.ElementEffectivities;
import kp.games.ec.creature.attributes.EnergyPoints;
import kp.games.ec.creature.attributes.HealthPoints;
import kp.games.ec.creature.attributes.Stat;
import kp.games.ec.creature.attributes.StatId;
import kp.games.ec.creature.attributes.Stats;
import kp.games.ec.creature.attributes.Status;
import kp.games.ec.creature.aw.Armor;
import kp.games.ec.creature.aw.Weapon;

/**
 *
 * @author Asus
 */
public final class Creature
{
    private String name = "";
    private int level = 1;
    private Weapon weapon;
    private Armor armor;
    private final Stats stats = new Stats();
    private final ElementEffectivities elementEffectivities = new ElementEffectivities();
    private final Status status = new Status(this);
    private final ArrayList<Ability> abilities = new ArrayList<>();
    
    private int battlePositionId;
    
    
    public final void setName(String name) { this.name = Objects.requireNonNull(name); }
    public final String getName() { return name; }
    
    public final void setLevel(int level) { this.level = level < 1 ? 1 : level; }
    public final int getLevel() { return level; }
    
    public final void setWeapon(Weapon weapon) { this.weapon = weapon; }
    public final Weapon getWeapon() { return weapon; }
    
    public final void setArmor(Armor armor) { this.armor = armor; }
    public final Armor getArmor() { return armor; }
    
    
    public final Stats getAllStats() { return stats; }
    public final Stat getStat(StatId id) { return stats.getStat(id); }
    public final int getStatValue(StatId id) { return stats.getStat(id).getValue(); }
    
    public final ElementEffectivities getElementEffectivities() { return elementEffectivities; }
    public final float getElementEffectivity(Element element) { return elementEffectivities.getValue(element); }
    
    public final Status getStatus() { return status; }
    
    public final HealthPoints getHealthPoints() { return stats.getHealthPoints(); }
    public final int getMaxHealthPoints() { return stats.getHealthPoints().getMaxHealthPoints(); }
    public final int getCurrentHealthPoints() { return stats.getHealthPoints().getCurrentHealthPoints(); }
    
    public final EnergyPoints getEnergyPoints() { return stats.getEnergyPoints(); }
    public final int getMaxEnergyPoints() { return stats.getEnergyPoints().getMaxEnergyPoints(); }
    public final int getCurrentEnergyPoints() { return stats.getEnergyPoints().getCurrentEnergyPoints(); }
    
    public final void setBattlePositionId(int id) { this.battlePositionId = id; }
    public final int getBattlePositionId() { return battlePositionId; }
    
    
    public final void addAbility(Ability ability)
    {
        this.abilities.add(Objects.requireNonNull(ability));
    }
    
    public final int getAbilityCount() { return abilities.size(); }
    
    public final Ability getAbility(int index) { return abilities.get(index); }
    public final List<Ability> getAbilities() { return Collections.unmodifiableList(abilities); }
    
    public final boolean hasAbilityType(int type)
    {
        for(Ability ab : abilities)
            if(ab.hasType(type))
                return true;
        return false;
    }
    
    
    
    public final void updateAttributeValues()
    {
        stats.clearExternAlterations();
        
    }
    
    
    
    /* Direct path methods */
    public final int getWeaponAttack() { return weapon != null ? weapon.getStrengthBonus(): 0; }
    public final int getWeaponEnergyAttackBonus() { return weapon != null ? weapon.getEnergyAttackBonus() : 0; }
    public final int getWeaponAccuracyBonus() { return weapon != null ? weapon.getAccuracyBonus() : 0; }
    public final Element getWeaponDamageElement() { return weapon != null ? weapon.getDamageElement() : null; }
    
    public final int getArmorDefenseBonus() { return armor != null ? armor.getDefenseBonus(): 0; }
    public final int getArmorEnergyDefenseBonus() { return armor != null ? armor.getEnergyDefenseBonus(): 0; }
    public final int getArmorEvasionBonus() { return armor != null ? armor.getEvasionBonus(): 0; }
}
