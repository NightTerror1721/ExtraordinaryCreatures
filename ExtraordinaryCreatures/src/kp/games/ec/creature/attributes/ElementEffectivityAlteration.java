/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.games.ec.creature.attributes;

import java.util.Objects;

/**
 *
 * @author Asus
 */
public final class ElementEffectivityAlteration
{
    Element element;
    float multiplier;
    
    public ElementEffectivityAlteration(Element element, float multiplier)
    {
        this.element = Objects.requireNonNull(element);
        this.multiplier = multiplier;
    }
    
    public final float getMultiplier() { return multiplier; }
    
    public final void add(ElementEffectivityAlteration alteration)
    {
        this.multiplier += alteration.multiplier;
    }
}
