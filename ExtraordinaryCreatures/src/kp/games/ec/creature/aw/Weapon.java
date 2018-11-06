/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.games.ec.creature.aw;

import kp.games.ec.creature.attributes.Element;
import kp.games.ec.creature.attributes.SpecialStatusId;
import kp.games.ec.utils.Utils;

/**
 *
 * @author Asus
 */
public final class Weapon extends BattleUtility
{
    private Element damageElement;
    private final float[] statusProv = new float[SpecialStatusId.count()];
    
    public final void setDamageElement(Element element) { this.damageElement = element; }
    public final Element getDamageElement() { return damageElement; }
    
    public final void setAttack(int attack) { this.physic = attack < 0 ? 0 : attack; }
    public final int getAttack() { return physic; }
    
    public final void setEnergyAttackBonus(int energyAttack) { this.energy = energyAttack; }
    public final int getEnergyAttackBonus() { return energy; }
    
    public final void setAccuracyBonus(int accuracy) { this.accuracyEvasion = accuracy; }
    public final int getAccuracyBonus() { return accuracyEvasion; }
    
    public final void setStatusProbability(SpecialStatusId id, float prob) { statusProv[id.ordinal()] = Utils.range(0f, 1f, prob); }
    public final float getStatusProbability(SpecialStatusId id) { return statusProv[id.ordinal()]; }
    
    
}
