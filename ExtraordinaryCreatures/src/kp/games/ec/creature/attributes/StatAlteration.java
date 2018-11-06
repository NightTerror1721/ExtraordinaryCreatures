/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.games.ec.creature.attributes;

/**
 *
 * @author Asus
 */
public final class StatAlteration
{
    int additional;
    float multiplier;
    
    public StatAlteration(int additional, float multiplier)
    {
        this.additional = additional;
        this.multiplier = multiplier < 0 ? 0 : multiplier;
    }
    
    public final int getAdditional() { return additional; }
    public final float getMultiplier() { return multiplier; }
    
    public final void add(StatAlteration other)
    {
        additional += other.additional;
        multiplier += other.multiplier;
    }
}
