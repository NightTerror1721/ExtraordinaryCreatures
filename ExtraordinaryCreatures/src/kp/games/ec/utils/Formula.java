/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.games.ec.utils;

import kp.games.ec.creature.Creature;
import kp.games.ec.creature.attributes.Element;
import kp.games.ec.creature.attributes.StatId;

/**
 *
 * @author Asus
 */
public final class Formula
{
    private Formula() {}
    
    public static final int POWER_UNIT = 40;
    
    public static final int computeStatValue(StatId stat, int base, int sp, int gp, int level)
    {
        switch(stat)
        {
            case HEALTH_POINTS:
                return healthPointsStat(base, sp, gp, level);
            case ENERGY_POINTS:
                /*return (int) (25 +
                        ((level * 75f) / (200f - level * (2f - level / 100f))) +
                        (level) +
                        ((base * level * 3.5f) / (75f - level / 4f)) +
                        (sp / 4f * level / 100f));*/
                return (232 + (base * 512 / 100) + (gp * 2) + (sp * 3 / 4)) * level / (200 - level) + 24;
            case CRITICAL_HIT:
            case CRITICAL_HIT_PREVENT:
            case COUNTERATTACK:
            case COUNTERATTACK_PREVENT:
                return ((base * 128 / 100) + (gp / 2) + (sp / 8)) * level / (400 - level * 3);
            default:
                return ((base * 248 / 100) + gp + (sp / 4)) * level / (200 - level) + 8;
        }
    }
    
    
    private static final int COMMON_BASE = 5120;
    private static final int SPECIFIC_BASE = COMMON_BASE * 3;
    private static int healthPointsStat(int base, int sp, int gp, int level)
    {
        int filled = 300 - 2 * level;
        int specific = base * SPECIFIC_BASE / 100;
        int genetic = gp * 32;
        int stat = sp * 16;
        
        return (COMMON_BASE + specific + genetic + stat) * level / filled;
    }
    
    
    
    public static final int effectivity(Element attackElement, Creature target, int points)
    {
        return target.getElementEffectivities().applyEffectivity(attackElement, points);
    }
    
    public static final float toHitProbability(Creature user, Creature target, float accuracyModifier)
    {
        int acc = user.getStatValue(StatId.ACCURACY) + user.getWeaponAccuracyBonus();
        int eva = target.getStatValue(StatId.EVASION) + target.getArmorEvasionBonus();
        
        float prov = acc / eva * accuracyModifier;
        return prov < 0f ? 0f : prov > 1f ? 1f : prov;
    }
    
    public static final boolean tryCriticalHit(Creature user, Creature target, RNG rng)
    {
        int base = (int) (user.getLevel() * 0.01f * 15);
        int critPer = (base + user.getStatValue(StatId.CRITICAL_HIT) - target.getStatValue(StatId.CRITICAL_HIT_PREVENT)) / 4;
        if(critPer >= 100)
            return true;
        
        int rand = (rng.d65536() * 99 / 65535) + 1;
        return rand <= critPer;
    }
    
    public static final float computePrecision(Creature user, Creature target)
    {
        float acc = user.getStatValue(StatId.ACCURACY) * user.getLevel();
        float eva = target.getStatValue(StatId.EVASION) * user.getLevel();
        
        return 1f + ((acc / eva - 1f) / 2f);
    }
    
    /*public static final boolean tryApplyStatus(Creature target, RNG rng, SpecialStatusId id, float probability)
    {
        int chance = (int) ((probability < 0 ? 0 : probability > 1f ? 1f : probability) * 65535);
        return rng.d65536() < chance;
    }*/
    
    private static int toRealPower(int power)
    {
        float fpower = power < 1 ? 1 : power;
        return (int) (fpower / POWER_UNIT * 16f);
    }
    
    public static final int basePhysicDamage(Creature user, Creature target, Element element, int userLevel, int power, int userAttack, int targetDefense)
    {
        power = toRealPower(power);
        userLevel = userLevel < 1 ? user.getLevel() : userLevel;
        userAttack = userAttack < 1 ? user.getStatValue(StatId.STRENGTH) : userAttack;
        targetDefense = targetDefense < 1 ? target.getStatValue(StatId.DEFENSE) : targetDefense;
        
        /*int base = (int) (userAttack + ((userAttack + userLevel) / 32f) * ((userAttack * userLevel) / 32f));
        int dam = (power * (512 - targetDefense) * base) / (16 * 512);*/
        int dam = (int) ((userLevel / 2f) * userAttack * power / (2.25f - (userLevel * 1.25f / 100f)) / targetDefense);
        return effectivity(element, target, dam);
    }
    
    public static final int baseEnergyDamage(Creature user, Creature target, Element element, int userLevel, int power, int userEAttack, int targetEDefense)
    {
        power = toRealPower(power);
        userLevel = userLevel < 1 ? user.getLevel() : userLevel;
        userEAttack = userEAttack < 1 ? user.getStatValue(StatId.ENERGY_POWER) : userEAttack;
        targetEDefense = targetEDefense < 1 ? target.getStatValue(StatId.ENERGY_DEFENSE) : targetEDefense;
        
        //int base = 6 * (userEAttack + userLevel);
        //int dam = (power * (512 - targetEDefense) * base) / (16 * 512);
        int dam = (int) ((userLevel / 2f) * userEAttack * power / (2.25f - (userLevel * 1.25f / 100f)) / targetEDefense);
        return effectivity(element, target, dam);
    }
    
    public static final int baseCureDamage(Creature user, Creature target, Element element, int userLevel, int power, int userEAttack)
    {
        power = toRealPower(power);
        userLevel = userLevel < 1 ? user.getLevel() : userLevel;
        userEAttack = userEAttack < 1 ? user.getStatValue(StatId.ENERGY_POWER) +  + user.getWeaponEnergyAttackBonus(): userEAttack;
        
        //int base = 6 * (userEAttack + userLevel);
        //int dam = base + 22 * power;
        int dam = (userEAttack + userLevel) * power / 6;
        return effectivity(element, target, dam);
    }
    
    public static final int baseHealthPartDamage(Creature user, Creature target, Element element, int healthBPercentage)
    {
        int dam = bytePercentage(target.getMaxHealthPoints(), healthBPercentage);
        return effectivity(element, target, dam);
    }
    
    
    public static final int criticalHitModifier(Creature user, Creature target, RNG rng, int points)
    {
        if(tryCriticalHit(user, target, rng))
            return points * 2;
        return points;
    }
    
    public static final int defendModifier(Creature target, int points)
    {
        return target.getStatus().isDefendModeEnabled() ? points / 2 : points;
    }
    
    public static final int barrierModifier(Creature target, Element element, int points)
    {
        if(element == null)
            return points;
        switch(element)
        {
            case BLUDGEONING:
            case SLASHING:
            case PIERCING:
                return target.getStatus().hasBarrier() ? points / 2 : points;
            default: return target.getStatus().hasEnergyBarrier()? points / 2 : points;
        }
    }
    
    public static final int randomVariation(RNG rng, int points)
    {
        int dam = points * ((3841 + rng.d256()) / 4096);
        if(dam == 0)
            return points <= 0 ? 0 : 1;
        return dam;
    }
    
    public static float percentage(int per) { return per / 100f; }
    public static int percentage(int value, int per) { return value * per / 100; }
    public static float bytePercentage(int per) { return per / 256f; }
    public static int bytePercentage(int value, int per) { return value * per / 256; }
}
