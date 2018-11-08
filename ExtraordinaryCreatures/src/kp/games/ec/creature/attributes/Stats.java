/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.games.ec.creature.attributes;

/**
 *
 * @author Asus
 */
public final class Stats
{
    private final HealthPoints hp = new HealthPoints();
    private final EnergyPoints ep = new EnergyPoints();
    private final Stat strength = new Stat(StatId.STRENGTH);
    private final Stat defense = new Stat(StatId.DEFENSE);
    private final Stat energyPower = new Stat(StatId.ENERGY_POWER);
    private final Stat energyDefense = new Stat(StatId.ENERGY_DEFENSE);
    private final Stat speed = new Stat(StatId.SPEED);
    private final Stat accuracy = new Stat(StatId.ACCURACY);
    private final Stat evasion = new Stat(StatId.EVASION);
    private final Stat criticalHit = new Stat(StatId.CRITICAL_HIT);
    private final Stat criticalHitPrevent = new Stat(StatId.CRITICAL_HIT_PREVENT);
    private final Stat counterattack = new Stat(StatId.COUNTERATTACK);
    private final Stat counterattackPrevent = new Stat(StatId.COUNTERATTACK_PREVENT);
    
    public final Stat getStat(StatId id)
    {
        switch(id)
        {
            case HEALTH_POINTS: return hp;
            case ENERGY_POINTS: return ep;
            case STRENGTH: return strength;
            case DEFENSE: return defense;
            case ENERGY_POWER: return energyPower;
            case ENERGY_DEFENSE: return energyDefense;
            case SPEED: return speed;
            case ACCURACY: return accuracy;
            case EVASION: return evasion;
            case CRITICAL_HIT: return criticalHit;
            case CRITICAL_HIT_PREVENT: return criticalHitPrevent;
            case COUNTERATTACK: return counterattack;
            case COUNTERATTACK_PREVENT: return counterattackPrevent;
            default: throw new IllegalStateException();
        }
    }
    public final HealthPoints getHealthPoints() { return hp; }
    public final EnergyPoints getEnergyPoints() { return ep; }
    
    public final int getStatValue(StatId id) { return getStat(id).getValue(); }
    
    public final void clearAlterations()
    {
        for(StatId id : StatId.iterable())
            getStat(id).clearAlterations();
    }
    public final void clearAlterations(StatId id) { getStat(id).clearAlterations(); }
    public final void addAlteration(StatId id, float alteration) { getStat(id).addAlteration(alteration); }
    
    public final void clearExternAlterations()
    {
        for(StatId id : StatId.iterable())
            getStat(id).clearExternAlteration();
    }
    public final void clearExternAlteration(StatId id) { getStat(id).clearExternAlteration(); }
    public final void addExternAlteration(StatId id, int points) { getStat(id).addExternAlteration(points); }
    public final void addExternAlterationPercentage(StatId id, float percentage) { getStat(id).addExternAlterationPercentage(percentage); }
    
    public final void updateValues(int level)
    {
        for(StatId id : StatId.iterable())
            getStat(id).updateValue(level);
    }
}
