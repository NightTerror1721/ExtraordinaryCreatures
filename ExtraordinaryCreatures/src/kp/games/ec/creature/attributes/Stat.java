/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.games.ec.creature.attributes;

import java.util.Objects;
import kp.games.ec.utils.Formula;

/**
 *
 * @author Asus
 */
public class Stat
{
    private final StatId id;
    private int base;
    private int sp;
    private int value;
    private float alteration;
    private StatAlteration externAlteration = new StatAlteration(0, 0);
    
    public Stat(StatId id) { this.id = Objects.requireNonNull(id); }
    
    public final void setBase(int base) { this.base = base < 0 ? 0 : base; }
    public final int getBase() { return base; }
    
    public final void setStatPoints(int points) { this.sp = points < 0 ? 0 : points; }
    public final void addStatPoints(int points) { this.sp += points < 0 ? 0 : points; }
    public final int getStatPoints() { return sp; }
    
    public final int getValue() { return value; }
    
    public final void clearAlterations() { alteration = 0f; }
    public final void addAlteration(float alteration) { this.alteration += alteration; }
    
    public final void clearExternAlteration() { externAlteration.additional = 0; externAlteration.multiplier = 0; }
    public final void addExternAlteration(int points) { this.externAlteration.additional += points; }
    public final void addExternAlterationPercentage(float percentage) { this.externAlteration.multiplier += percentage; }
    
    public void updateValue(int level)
    {
        int original = (int) (Formula.computeStatValue(id, base, sp, level) *
                computeExternAlterationMultiplier(externAlteration.multiplier)) + externAlteration.additional;
        this.value = (int) (original * computeExternAlterationMultiplier(alteration));
    }
    
    private static float computeExternAlterationMultiplier(float percentageAlteration) { return Math.max(0.01f, 1 + percentageAlteration); }
}
