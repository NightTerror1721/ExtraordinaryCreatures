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
public final class EnergyPoints extends BarStat
{
    public EnergyPoints() { super(StatId.ENERGY_POINTS); }
    
    public final int getCurrentEnergyPoints() { return current; }
    public final int getMaxEnergyPoints() { return getValue(); }
    
    public final float getCurrentEnergyPercentage() { return ((float) current) / getValue(); }
    
    public final void use(int points) { modifyCurrent(points < 0 ? points : -points); }
    public final void empty() { current = 0; }
    
    public final void fill(int points) { modifyCurrent(points < 0 ? -points : points); }
    public final void fill() { current = getValue(); }
    
    public final boolean isFull() { return current  == getValue(); }
    public final boolean isEmpty() { return current <= 0; }
}
