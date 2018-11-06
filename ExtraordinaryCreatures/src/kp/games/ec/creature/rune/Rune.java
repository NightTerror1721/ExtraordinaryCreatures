/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.games.ec.creature.rune;

/**
 *
 * @author Asus
 */
public abstract class Rune
{
    private int requiredLevel;
    
    Rune() {}
    
    public abstract boolean isPasive();
    
    public final int getRequiredLevel() { return requiredLevel; }
}
