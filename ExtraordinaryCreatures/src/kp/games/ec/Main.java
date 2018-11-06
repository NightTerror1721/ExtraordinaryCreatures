/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.games.ec;

import kp.games.ec.creature.ability.AbilityEffect;
import kp.games.ec.creature.attributes.Element;
import kp.games.ec.creature.attributes.StatId;

/**
 *
 * @author Asus
 */
public final class Main
{
    public static void main(String[] args)
    {
        int id = AbilityEffect.FLAG_TYPE_BYTE_PERCENTAGE;
        AbilityEffect effect = AbilityEffect.createEffect(5);
        effect.setFlag(0, 1);
        effect.setFlag(1, 30);
        effect.setFlag(2, StatId.STRENGTH);
        effect.setFlag(3, 16);
        System.out.println(effect.getDescription());
        
        effect = AbilityEffect.createEffect(3);
        effect.setFlag(0, 0);
        effect.setFlag(1, 128);
        effect.setFlag(2, Element.GROUND);
        System.out.println(effect.getDescription());
    }
}
