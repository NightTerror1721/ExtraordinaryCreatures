/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.games.ec.creature.ability;

import java.util.LinkedList;
import java.util.Objects;

/**
 *
 * @author Asus
 */
public final class Ability
{
    private int id;
    private String name = "";
    private int energyCost;
    private int type;
    private final LinkedList<AbilityEffect> effects = new LinkedList<>();
    
    public final void setId(int id) { this.id = id; }
    public final int getId() { return id; }
    
    public final void setName(String name) { this.name = name == null ? "" : name; }
    public final String getName() { return name; }
    
    public final void setEnergyCost(int cost) { this.energyCost = cost < 0 ? 0 : cost; }
    public final int getEnergyCost() { return energyCost; }
    
    public final void addEffect(AbilityEffect effect)
    {
        effects.add(Objects.requireNonNull(effect));
    }
    public final int getEffectCount() { return effects.size(); }
    
    
    public final int getType()
    {
        if(type == 0)
        {
            for(AbilityEffect effect : effects)
                type |= effect.getType();
            if(type == 0)
                type = AbilityEffect.EFFECT_TYPE_STATE;
        }
        return type;
    }
    
    public final boolean hasType(int type)
    {
        return (getType() & type) != 0;
    }
    
    
}
