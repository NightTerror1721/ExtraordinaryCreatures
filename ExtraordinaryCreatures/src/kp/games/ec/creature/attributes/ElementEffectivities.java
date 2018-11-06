/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.games.ec.creature.attributes;

import java.util.LinkedList;
import java.util.Objects;

/**
 *
 * @author Asus
 */
public final class ElementEffectivities
{
    private static final float[] EMPTY_ALTERATIONS = new float[Element.count()];
    
    private final float[] base = new float[Element.count()];
    private final float[] value = new float[Element.count()];
    private final LinkedList<ElementEffectivityAlteration> alterations = new LinkedList<>();
    private final float[] externMultiplier = new float[Element.count()];
    
    
    public final void setBase(Element element, float base)
    {
        this.base[element.ordinal()] = base < -1f ? 1f : base;
    }
    public final float getBase(Element element) { return base[element.ordinal()]; }
    
    public final float getValue(Element element) { return value[element.ordinal()]; }
    
    public final void clearAlterations() { alterations.clear(); }
    public final void addAlteration(ElementEffectivityAlteration alteration) { alterations.add(Objects.requireNonNull(alteration)); }
    
    public final void clearExternAlterations() { System.arraycopy(EMPTY_ALTERATIONS, 0, externMultiplier, 0, externMultiplier.length); }
    public final void addExternAlteration(Element element, float multiplier) { externMultiplier[element.ordinal()] += multiplier; }
    
    public final void updateValue()
    {
        for(int i = 0; i < value.length; i++)
            value[i] = Math.max(-1f, base[i] + externMultiplier[i]);
        for(ElementEffectivityAlteration alteration : alterations)
        {
            int index = alteration.element.ordinal();
            value[index] = Math.max(-1f, value[index] + alteration.multiplier);
        }
    }
    
    public final int applyEffectivity(Element element, int points)
    {
        if(element == null)
            return points;
        return (int) (getValue(element) * points);
    }
}
