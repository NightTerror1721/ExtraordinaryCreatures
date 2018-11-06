/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.games.ec.creature.effect;

import kp.games.ec.battle.cmd.BattleCommandManager;
import kp.games.ec.creature.Creature;
import kp.games.ec.creature.attributes.Element;
import kp.games.ec.utils.Formula;
import kp.games.ec.utils.RNG;

/**
 *
 * @author Asus
 */
public final class DamageEffect extends Effect
{
    private DamageFormula formula = DamageFormula.PHYSIC;
    private Element element;
    private int userAttack;
    private int targetDefense;
    private int power;
    //private final float[][] statAlterations = new float[StatId.count()][2];
    //private final float[] statusAlterations = new float[StatusId.count()];
    
    
    public final void setDamageElement(Element element) { this.element = element; }
    public final Element getDamageElement() { return element; }
    
    public final void setUserAttack(int value) { this.userAttack = value; }
    public final int getUserAttack() { return userAttack; }
    
    public final void setTargetDefense(int value) { this.targetDefense = value; }
    public final int getTargetDefense() { return targetDefense; }
    
    public final void setPower(int value) { this.power = value; }
    public final int getPower() { return power; }
    
    /*public final void setStatAlteration(StatId id, float probability, float alterationPercentage)
    {
        statAlterations[id.ordinal()][0] = Utils.range(0f, 1f, probability);
        statAlterations[id.ordinal()][1] = Math.max(-0.99f, alterationPercentage);
    }
    public final float getStatAlterationProbability(StatId id) { return statAlterations[id.ordinal()][0]; }
    public final float getStatAlterationPercentage(StatId id) { return statAlterations[id.ordinal()][1]; }
    
    public final void setStatusAlteration(StatusId id, float probability)
    {
        statusAlterations[id.ordinal()] = Utils.range(0f, 1f, probability);
    }
    public final float getStatusAlteration(StatusId id) { return statusAlterations[id.ordinal()]; }*/
    
    @Override
    public final EffectType getEffectType() { return EffectType.DAMAGE; }

    @Override
    public final void apply(Creature user, Creature target, RNG rng, BattleCommandManager bcm)
    {
        int damage;
        switch(formula)
        {
            default:
            case PHYSIC: damage = physicFormula(user, target, rng, userAttack, targetDefense, power, element); break;
            case ENERGY: damage = energyFormula(user, target, rng, userAttack, targetDefense, power, element); break;
            case CURE: damage = cureFormula(user, target, rng, userAttack, power, element); break;
            case HEALTH_PART: damage = healthPartFormula(user, target, rng, power, element); break;
        }
        
        if(damage > 0)
        {
            bcm.message(user.getName() + " causa a " + target.getName() + " " + damage + " puntos de daño.")
               .damage(target, damage);
        }
        else if(damage < 0)
        {
            damage = -damage;
            bcm.message(user.getName() + " cura a " + target.getName() + " " + damage + " puntos de salud.")
               .heal(target, damage);
        }
    }
    
    
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
    
    public static final int healthPartFormula(Creature user, Creature target, RNG rng, int power, Element element)
    {
        int dam = Formula.baseHealthPartDamage(user, target, element, power);
        dam = Formula.randomVariation(rng, dam);
        return dam;
    }
    
    
    
    @Override
    public final int AI_Selection(Creature user, Creature target, RNG rng)
    {
        int base = formulaAIScore(user, target, rng);
        float precisionMod = Formula.computePrecision(user, target);
        return (int) (base * precisionMod);
    }
    
    private int formulaAIScore(Creature user, Creature target, RNG rng)
    {
        int damage;
        switch(formula)
        {
            default:
            case PHYSIC: damage = physicFormula(user, target, rng, userAttack, targetDefense, power, element); break;
            case ENERGY: damage = energyFormula(user, target, rng, userAttack, targetDefense, power, element); break;
            case CURE: damage = cureFormula(user, target, rng, userAttack, power, element); break;
            case HEALTH_PART: damage = healthPartFormula(user, target, rng, power, element); break;
        }
        
        if(damage <= 0)
            return 0;
        float dif = (float) damage / target.getCurrentHealthPoints();
        return dif < 0 ? MIN_AI_SCORE : dif > 1 ? MAX_AI_SCORE : (int) (MAX_AI_SCORE / dif);
    }
    
    
    
    public static enum DamageFormula { PHYSIC, ENERGY, CURE, HEALTH_PART; }
}
