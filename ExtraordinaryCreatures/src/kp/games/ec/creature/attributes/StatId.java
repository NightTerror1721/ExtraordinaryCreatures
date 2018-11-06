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
public enum StatId
{
    HEALTH_POINTS("Puntos Salud"),
    ENERGY_POINTS("Puntos Energia"),
    STRENGTH("Fuerza"),
    DEFENSE("Defensa"),
    ENERGY_POWER("Poder Energético"),
    ENERGY_DEFENSE("Defensa Energética"),
    SPEED("Velocidad"),
    
    ACCURACY("Precisión"),
    EVASION("Evasión"),
    
    CRITICAL_HIT("Golpe Crítico"),
    CRITICAL_HIT_PREVENT("Prevención Golpe Crítico"),
    
    COUNTERATTACK("Contraataque"),
    COUNTERATTACK_PREVENT("Prevención Contraataque");
    
    public static final int MIN_BASE = 0;
    public static final int MAX_BASE = 9999;
    
    private final String name;
    
    private StatId(String name) { this.name = name; }
    
    public final String getName() { return name; }
    
    @Override
    public final String toString() { return name; }
    
    public final int getId() { return ordinal(); }
    
    private static final StatId[] VALUES = values();
    public static final int count() { return VALUES.length; }
    public static final StatId fromValue(int value)
    {
        return VALUES[value];
    }
    
    public static final Iterator<StatId> iterator()
    {
        return new Iterator<StatId>()
        {
            private int it = 0;
            @Override public boolean hasNext() { return it < VALUES.length; }
            @Override public StatId next() { return VALUES[it++]; }
        };
    }
    public static final Iterable<StatId> iterable() { return StatId::iterator; }
}
