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
public final class HealthPoints extends BarStat
{
    public HealthPoints() { super(StatId.HEALTH_POINTS); }
    
    public final int getCurrentHealthPoints() { return current; }
    public final int getMaxHealthPoints() { return getValue(); }
    
    public final float getCurrentHealthPercentage() { return ((float) current) / getValue(); }
    
    public final void damage(int points) { modifyCurrent(points < 0 ? points : -points); }
    public final void kill() { current = 0; }
    
    public final void heal(int points) { modifyCurrent(points < 0 ? -points : points); }
    public final void completeHealing() { current = getValue(); }
    
    public final boolean isAlive() { return current > 0; }
    public final boolean isDead() { return current <= 0; }
    
    public final boolean hasFullHealth() { return current  == getValue(); }
}
