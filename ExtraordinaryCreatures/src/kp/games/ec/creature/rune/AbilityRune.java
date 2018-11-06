/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.games.ec.creature.rune;

import kp.games.ec.creature.ability.Ability;

/**
 *
 * @author Asus
 */
public final class AbilityRune extends Rune
{
    private Ability ability;
    
    @Override
    public final boolean isPasive() { return false; }
    
}
