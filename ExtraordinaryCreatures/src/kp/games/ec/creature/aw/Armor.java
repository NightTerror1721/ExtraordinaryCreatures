/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.games.ec.creature.aw;

/**
 *
 * @author Asus
 */
public final class Armor extends BattleUtility
{
    public final void setDefenseBonus(int defense) { this.physic = defense; }
    public final int getDefenseBonus() { return physic; }
    
    public final void setEnergyDefenseBonus(int energyDefense) { this.energy = energyDefense; }
    public final int getEnergyDefenseBonus() { return energy; }
    
    public final void setEvasionBonus(int evasion) { this.accuracyEvasion = evasion; }
    public final int getEvasionBonus() { return accuracyEvasion; }
}
