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
public enum Element
{
    BLUDGEONING("Contundente"),
    SLASHING("Cortante"),
    PIERCING("Perforante"),
    FIRE("Fuego"),
    ELECTRICAL("Eléctrico"),
    WATER("Agua"),
    ICE("Hielo"),
    GROUND("Tierra"),
    POISON("Veneno"),
    AIR("Aire"),
    POSITIVE("Positivo"),
    NEGATIVE("Negativo"),
    HOLY("Sagrado");
    
    private final String name;
    
    private Element(String name) { this.name = name; }
    
    public final String getName() { return name; }
    
    @Override
    public final String toString() { return name; }
    
    private static final Element[] VALUES = values();
    public static final int count() { return VALUES.length; }
    public static final Element fromValue(int value)
    {
        return VALUES[value];
    }
}
