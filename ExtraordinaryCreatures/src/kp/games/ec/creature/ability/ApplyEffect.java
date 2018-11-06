/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.games.ec.creature.ability;

import kp.games.ec.battle.cmd.BattleCommandManager;
import kp.games.ec.creature.Creature;
import kp.games.ec.creature.attributes.Element;
import kp.games.ec.utils.Formula;
import kp.games.ec.utils.RNG;

/**
 *
 * @author Asus
 */
public final class ApplyEffect
{
    private ApplyEffect() {}
    
    
    
    public static final int physicFormula(Creature user, Creature target, RNG rng, int userAttack, int targetDefense, int power, Element element)
    {
        int dam = Formula.basePhysicDamage(user, target, element, user.getLevel(), power, userAttack, targetDefense);
        dam = Formula.criticalHitModifier(user, target, rng, dam);
        dam = Formula.defendModifier(target, dam);
        dam = Formula.barrierModifier(target, element, dam);
        dam = Formula.randomVariation(rng, dam);
        return dam;
    }
    
    public static final int energyFormula(Creature user, Creature target, RNG rng, int userAttack, int targetDefense, int power, Element element)
    {
        int dam = Formula.baseEnergyDamage(user, target, element, user.getLevel(), power, userAttack, targetDefense);
        dam = Formula.barrierModifier(target, element, dam);
        dam = Formula.randomVariation(rng, dam);
        return dam;
    }
    
    public static final int cureFormula(Creature user, Creature target, RNG rng, int userAttack, int power, Element element)
    {
        int dam = Formula.baseCureDamage(user, target, element, user.getLevel(), power, userAttack);
        dam = Formula.barrierModifier(target, element, dam);
        dam = Formula.randomVariation(rng, dam);
        return dam;
    }
    
    public static final int healthPartFormula(Creature user, Creature target, RNG rng, int healthBPercentage, Element element)
    {
        int dam = Formula.baseHealthPartDamage(user, target, element, healthBPercentage);
        return dam;
    }
    
    public static final void applyDamage(Creature user, Creature target, BattleCommandManager bcm, int damage)
    {
        if(user == target)
        {
            if(damage > 0)
            {
                bcm.message(user.getName() + " recibe " + damage + " puntos de daño.")
                   .damage(target, damage);
            }
            else if(damage < 0)
            {
                damage = -damage;
                bcm.message(user.getName() + " se cura " + damage + " puntos de salud.")
                   .heal(target, damage);
            }
        }
        else
        {
            if(damage > 0)
            {
                bcm.message(user.getName() + " infige a " + target.getName() + " " + damage + " puntos de daño.")
                   .damage(target, damage);
            }
            else if(damage < 0)
            {
                damage = -damage;
                bcm.message(user.getName() + " cura a " + target.getName() + " " + damage + " puntos de salud.")
                   .heal(target, damage);
            }
        }
    }
    public static final void applyDamage(Creature user, BattleCommandManager bcm, int damage) { applyDamage(user, user, bcm, damage); }
}
