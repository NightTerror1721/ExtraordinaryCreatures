/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.games.ec;

import kp.games.ec.creature.ability.AbilityEffect;
import kp.games.ec.creature.attributes.Element;
import kp.games.ec.creature.attributes.StatId;
import kp.games.ec.utils.Formula;

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
        
        
        for(int i=1;i<101;i++)
        {
            System.out.println("Level " + i + ":");
            
            for(StatId stat : StatId.iterable())
                System.out.println("\t" + stat.getName() + ": " + Formula.computeStatValue(stat, 100, 0, i));
            
            System.out.println();
        }
    }
}
