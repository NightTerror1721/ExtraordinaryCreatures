/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.games.ec.creature.ability;

import kp.games.ec.creature.Creature;
import kp.games.ec.creature.attributes.Element;
import kp.games.ec.creature.attributes.StatId;
import kp.games.ec.creature.attributes.StatusId;
import kp.games.ec.utils.Formula;
import kp.games.ec.utils.RNG;

/**
 *
 * @author Asus
 */
public final class AIUtils
{
    private AIUtils() {}
    
    public static final int MAX_SCORE = 65535;
    public static final int MIN_SCORE = -MAX_SCORE;
    
    public static final int basicDamage(Creature user, Creature target, RNG rng, int damage)
    {
        float requiredHealMod, dif, precisionMod;
        if(user == target)
        {
            if(damage >= 0)
                return 0;
            
            damage = -damage;
            requiredHealMod = (1f - user.getHealthPoints().getCurrentHealthPercentage()) * 1.5f;
            dif = (float) damage / user.getCurrentHealthPoints();
            dif = dif < 0 ? 0 : dif > 1 ? MAX_SCORE : (int) (MAX_SCORE / dif);
            precisionMod = Formula.computePrecision(user, target);
        }
        else
        {
            if(damage <= 0)
                return 0;
            
            requiredHealMod = 1f;
            dif = (float) damage / target.getCurrentHealthPoints();
            dif = dif < 0 ? 0 : dif > 1 ? MAX_SCORE : (int) (MAX_SCORE / dif);
            precisionMod = Formula.computePrecision(user, target);
        }
        
        return (int) (damage * requiredHealMod * dif * precisionMod);
    }
    
    
    public static final int applyAlteredStatus(Creature user, Creature target, RNG rng, StatusId status, int prob)
    {
        int base = MAX_SCORE / 3;
        boolean self = user == target;
        float specificMod, healthMod;
        float probMod = (prob < 0 ? 110 : prob) / 100f;
        float appliedMod = target.getStatus().isStatusActive(status) ? 0f : 1f;
        
        switch(status)
        {
            default:
                base = 0;
                specificMod = healthMod = 0f;
                break;
            case ASLEEP:
                specificMod = self ? 0f : 0.85f;
                healthMod = user.getHealthPoints().getCurrentHealthPercentage();
                break;
            case BARRIER:
            case ENERGY_BARRIER:
                specificMod = self ? 1f : 0f;
                float health = user.getHealthPoints().getCurrentHealthPercentage();
                if(health >= 0.5f)
                    healthMod = (1f - user.getHealthPoints().getCurrentHealthPercentage()) + 1f;
                else healthMod = user.getHealthPoints().getCurrentHealthPercentage();
                break;
            case BURN:
                specificMod = self ? 0f : 1.25f;
                healthMod = user.getHealthPoints().getCurrentHealthPercentage();
                break;
            case BLOCKED:
                specificMod = self ? 0f : 0.8f;
                healthMod = user.getHealthPoints().getCurrentHealthPercentage();
                break;
            case CONFUSION:
                specificMod = self ? 0f : 1.1f;
                healthMod = user.getHealthPoints().getCurrentHealthPercentage();
                break;
            case CURSE:
                specificMod = self ? 0f : 1.5f;
                healthMod = 1f - ((1f - user.getHealthPoints().getCurrentHealthPercentage()) * 2.5f);
                if(healthMod < 0)
                    healthMod = 0;
                break;
            case DEFEND_MODE:
                specificMod = self ? 0.8f : 0f;
                healthMod = 1f - user.getHealthPoints().getCurrentHealthPercentage();
                break;
            case FREEZE:
                specificMod = self ? 0f : 1.3f;
                healthMod = user.getHealthPoints().getCurrentHealthPercentage();
                break;
            case HURRY:
                specificMod = self ? 1.3f : 0f;
                healthMod = user.getHealthPoints().getCurrentHealthPercentage();
                break;
            case INSTANT_DEATH:
                specificMod = self ? 0f : 1.3f;
                healthMod = 1f + ((1f - user.getHealthPoints().getCurrentHealthPercentage()) * 0.25f);
                break;
            case MUTE:
                specificMod = self ? 0f : 0.9f;
                healthMod = user.getHealthPoints().getCurrentHealthPercentage();
                break;
            case PARALYZE:
                specificMod = self ? 0f : 1.15f;
                healthMod = user.getHealthPoints().getCurrentHealthPercentage();
                break;
            case POISON:
                if(self)
                {
                    specificMod = user.getElementEffectivity(Element.POISON) < 0 ? 3f : 0f;
                    healthMod = 1f - user.getHealthPoints().getCurrentHealthPercentage();
                }
                else
                {
                    specificMod = 1.2f;
                    healthMod = user.getHealthPoints().getCurrentHealthPercentage();
                }
                break;
            case REGENERATE:
                specificMod = self ? 0f : 2f;
                healthMod = 1f;
                break;
            case SLEEP:
                specificMod = self ? 0f : 1.2f;
                healthMod = user.getHealthPoints().getCurrentHealthPercentage();
                break;
            case SLOW:
                specificMod = self ? 0f : 0.95f;
                healthMod = user.getHealthPoints().getCurrentHealthPercentage();
                break;
        }
        
        return (int) (base * appliedMod * specificMod * healthMod * probMod);
    }
    
    
    public static final int applyStatAlteration(Creature user, Creature target, RNG rng,  StatId stat, int prob, int alt, boolean selfTarget)
    {
        int base = MAX_SCORE / 4;
        float diffMod;
        float probMod = (prob < 0 ? 110 : prob) / 100f;
        float altMod = alt / 256f;
        float needMod = selfTarget ? alt >= 0 ? 1f : 0f: alt >= 0 ? 0f : 1f;
        
        switch(stat)
        {
            default:
            case HEALTH_POINTS:
            case ENERGY_POINTS:
                return 0;
            case STRENGTH:
                diffMod = (target.getStatValue(StatId.DEFENSE) * 1.1f) / user.getStatValue(StatId.STRENGTH);
                break;
            case DEFENSE:
                diffMod = (target.getStatValue(StatId.STRENGTH) * 1.1f) / user.getStatValue(StatId.DEFENSE);
                break;
            case ENERGY_POWER:
                diffMod = (target.getStatValue(StatId.ENERGY_DEFENSE) * 1.1f) / user.getStatValue(StatId.ENERGY_POWER);
                break;
            case ENERGY_DEFENSE:
                diffMod = (target.getStatValue(StatId.ENERGY_POWER) * 1.1f) / user.getStatValue(StatId.ENERGY_DEFENSE);
                break;
            case SPEED:
                diffMod = ((float) user.getStatValue(StatId.SPEED)) / target.getStatValue(StatId.SPEED);
                if(diffMod < 1f)
                    diffMod *= 1.5f;
                break;
            case ACCURACY:
                diffMod = ((float) target.getStatValue(StatId.EVASION)) / user.getStatValue(StatId.ACCURACY);
                break;
            case EVASION:
                diffMod = ((float) target.getStatValue(StatId.ACCURACY)) / user.getStatValue(StatId.EVASION);
                break;
            case COUNTERATTACK:
                diffMod = ((float) target.getStatValue(StatId.COUNTERATTACK_PREVENT)) / user.getStatValue(StatId.COUNTERATTACK);
                break;
            case COUNTERATTACK_PREVENT:
                diffMod = ((float) target.getStatValue(StatId.COUNTERATTACK)) / user.getStatValue(StatId.COUNTERATTACK_PREVENT);
                break;
            case CRITICAL_HIT:
                diffMod = ((float) target.getStatValue(StatId.CRITICAL_HIT_PREVENT)) / user.getStatValue(StatId.CRITICAL_HIT);
                break;
            case CRITICAL_HIT_PREVENT:
                diffMod = ((float) target.getStatValue(StatId.CRITICAL_HIT)) / user.getStatValue(StatId.CRITICAL_HIT_PREVENT);
                break;
        }
        
        return (int) (base * needMod * diffMod * probMod * altMod);
    }
}
