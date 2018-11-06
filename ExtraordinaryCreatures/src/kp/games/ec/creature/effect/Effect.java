/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.games.ec.creature.effect;

import kp.games.ec.battle.cmd.BattleCommandManager;
import kp.games.ec.creature.Creature;
import kp.games.ec.utils.RNG;

/**
 *
 * @author Asus
 */
public abstract class Effect
{
    public static final int MAX_AI_SCORE = 65535;
    public static final int MIN_AI_SCORE = 0;
    
    
    public abstract EffectType getEffectType();
    
    public abstract void apply(Creature user, Creature target, RNG rng, BattleCommandManager bcm);
    
    public abstract int AI_Selection(Creature user, Creature target, RNG rng);
}
