/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.games.ec.creature.aw;

import java.util.Iterator;
import java.util.Objects;
import kp.games.ec.creature.attributes.Element;
import kp.games.ec.creature.attributes.SpecialStatusId;
import kp.games.ec.creature.attributes.StatusId;
import kp.games.ec.utils.Utils;

/**
 *
 * @author Asus
 */
public final class Weapon extends BattleUtility
{
    private final WeaponType type;
    private int weaponPower;
    private Element damageElement;
    private final float[] statusProv = new float[SpecialStatusId.count()];
    
    public Weapon(WeaponType type)
    {
        this.type = Objects.requireNonNull(type);
    }
    
    public final WeaponType getWeaponType() { return type; }
    
    public final void setWeaponPower(int power) { this.weaponPower = power < 1 ? 1 : power; }
    public final int getWeaponPower() { return weaponPower; }
    
    public final void setDamageElement(Element element) { this.damageElement = element; }
    public final Element getDamageElement() { return damageElement == null ? type.getDefaultElement() : damageElement; }
    
    public final void setStrengthBonus(int attack) { this.physic = attack < 0 ? 0 : attack; }
    public final int getStrengthBonus() { return physic; }
    
    public final void setEnergyAttackBonus(int energyAttack) { this.energy = energyAttack; }
    public final int getEnergyAttackBonus() { return energy; }
    
    public final void setAccuracyBonus(int accuracy) { this.accuracyEvasion = accuracy; }
    public final int getAccuracyBonus() { return accuracyEvasion; }
    
    public final void setStatusProbability(SpecialStatusId id, float prob) { statusProv[id.ordinal()] = Utils.range(0f, 1f, prob); }
    public final float getStatusProbability(SpecialStatusId id) { return statusProv[id.ordinal()]; }
    
    public final Iterator<StatusModification> statusIterator()
    {
        return new Iterator<StatusModification>()
        {
            private final StatusModification res = new StatusModification();
            private int it = 0;
            
            @Override public final boolean hasNext() { return it < statusProv.length; }
            @Override public final StatusModification next() { return res.fill(it, statusProv[it++]); }
        };
    }
    public final Iterable<StatusModification> statusIterable() { return this::statusIterator; }
    
    
    public static final class StatusModification
    {
        private StatusId status;
        private float probability;
        
        private StatusModification() {}
        
        private StatusModification fill(int id, float prob)
        {
            status = SpecialStatusId.fromValue(id).getNormalId();
            probability = prob;
            return this;
        }
        public final StatusId getStatus() { return status; }
        public final float getProbability() { return probability; }
    }
    
}
