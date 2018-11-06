/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.games.ec.creature.rune;

import java.util.ArrayList;
import kp.games.ec.creature.effect.Effect;

/**
 *
 * @author Asus
 */
public final class PasiveRune extends Rune
{
    private final ArrayList<Effect> effects = new ArrayList<>();
    
    @Override
    public final boolean isPasive() { return true; }
    
}
