/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.games.ec.creature.aw;

import kp.games.ec.creature.attributes.Element;
import kp.games.ec.utils.Formula;

/**
 *
 * @author Asus
 */
public enum WeaponType
{
    SWORD("Espada",             Element.SLASHING, 1f,    1f),
    GREAT_SWORD("Espadón",      Element.SLASHING, 1.35f, 0.9f),
    GREAT_AXE("Gran Hacha",     Element.SLASHING, 1.5f,  0.85f),
    HAND_AXE("hacha de mano",   Element.SLASHING, 0.75f, 1.15f),
    SCYTHE("Guadaña",           Element.SLASHING, 1.65f, 0.8f),
    SHURIKEN("Shuriken",        Element.SLASHING, 15, 1.2f),
    
    HAMMER("Martillo",          Element.BLUDGEONING, 25, 1.05f),
    MACE("Maza",                Element.BLUDGEONING, 40, 0.95f),
    QUARTERSTAFF("Bastón",      Element.BLUDGEONING, 30, 1.05f),
    CLUB("Porra",               Element.BLUDGEONING, 35, 1f),
    SLING("Honda",              Element.BLUDGEONING, 20, 1.2f),
    
    /*DAGGER("Daga",              Element.PIERCING, 25, 1.1f),
    SPEAR("Lanza",              Element.PIERCING, ),
    BOWL("Arco",                Element.PIERCING),
    CROSSBOW("Ballesta",        Element.PIERCING)*/;
    
    private final String name;
    private final Element defaultElement;
    private final int basePower;
    private final float speedModificator;
    
    private WeaponType(String name, Element defaultElement, float basePowerModificator, float speedModificator)
    {
        this.name = name;
        this.defaultElement = defaultElement;
        this.basePower = (int) (Formula.POWER_UNIT * basePowerModificator);
        this.speedModificator = speedModificator;
    }
    
    public final String getName() { return name; }
    
    public final Element getDefaultElement() { return defaultElement; }
    
    public final int getBasePower() { return basePower; }
    
    @Override
    public final String toString() { return name; }
}
