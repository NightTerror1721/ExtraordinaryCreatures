/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.games.ec.creature.attributes;

import java.util.Iterator;

/**
 *
 * @author Asus
 */
public enum SpecialStatusId
{
    CONFUSION("Confusión"),
    PARALYZE("Paralización"),
    BURN("Quemadura"),
    POISON("Envenenamiento"),
    SLEEP("Sueño"),
    FREEZE("Congelación"),
    MUTE("Silencio"),
    CURSE("Maldición"),
    SLOW("Lentitud"),
    HURRY("Rapidez"),
    REGENERATE("Regeneración"),
    BARRIER("Barrera"),
    ENERGY_BARRIER("Barrera Energia"),
    INSTANT_DEATH("Muerte Instantania");
    
    private final String name;
    
    private SpecialStatusId(String name) { this.name = name; }
    
    public final String getName() { return name; }
    
    @Override
    public final String toString() { return name; }
    
    private static final SpecialStatusId[] VALUES = values();
    public static final int count() { return VALUES.length; }
    public static final SpecialStatusId fromValue(int value)
    {
        return VALUES[value];
    }
    
    public static final Iterator<SpecialStatusId> iterator()
    {
        return new Iterator<SpecialStatusId>()
        {
            private int it = 0;
            @Override public boolean hasNext() { return it < VALUES.length; }
            @Override public SpecialStatusId next() { return VALUES[it++]; }
        };
    }
    public static final Iterable<SpecialStatusId> iterable() { return SpecialStatusId::iterator; }
    
    public final StatusId getNormalId() { return StatusId.specialToNormal(this); }
}
