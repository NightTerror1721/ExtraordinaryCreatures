/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.games.ec.creature.attributes;

import java.util.Objects;
import kp.games.ec.battle.cmd.BattleCommandManager;
import kp.games.ec.creature.Creature;
import kp.games.ec.utils.RNG;
import kp.games.ec.utils.Utils;

/**
 *
 * @author Asus
 */
public final class Status
{
    private final Creature self;
    private int baseImmunity;
    private int immunity;
    private int sactive;
    
    public Status(Creature self) { this.self = Objects.requireNonNull(self); }
    
    
    public final void setBaseImmunity(SpecialStatusId id, boolean flag) { baseImmunity = Utils.setbit(baseImmunity, id.ordinal(), flag); }
    public final boolean hasBaseImmunity(SpecialStatusId id) { return Utils.getbit(baseImmunity, id.ordinal()); }
    
    public final boolean hasImmunity(SpecialStatusId id) { return Utils.getbit(immunity, id.ordinal()); }
    public final boolean hasImmunity(StatusId id) { return id.isSpecialStatus() && hasImmunity(id.getSpecialStatusId()); }
    
    public final boolean isStatusActive(StatusId id)
    {
        int index = id.ordinal();
        return Utils.getbit(sactive, index) && !Utils.getbit(immunity, index);
    }
    public final boolean isStatusActive(SpecialStatusId id) { return isStatusActive(id.getNormalId()); }
    
    private boolean activateStatus(StatusId id)
    {
        if(hasImmunity(id) || Utils.getbit(sactive, id.ordinal()))
            return false;
        sactive = Utils.setbit(sactive, id.ordinal(), true);
        return true;
    }
    
    private boolean deactivateStatus(StatusId id)
    {
        if(!hasImmunity(id) && Utils.getbit(sactive, id.ordinal()))
            return false;
        sactive = Utils.setbit(sactive, id.ordinal(), false);
        return true;
    }
    
    public final void setConfusion(RNG rng, BattleCommandManager bcm, boolean enabled)
    {
        if(enabled)
        {
            if(!activateStatus(StatusId.CONFUSION))
                return;
            confTurns = rng.d4() + 1;
            bcm.message(self.getName() + " se ha confundido.");
        }
        else
        {
            deactivateStatus(StatusId.CONFUSION);
            confTurns = 0;
            bcm.message(self.getName() + " ya no está confuso.");
        }
    }
    public final boolean isConfused() { return isStatusActive(StatusId.CONFUSION); }
    
    public final void setParalyze(BattleCommandManager bcm, boolean enabled)
    {
        if(enabled)
        {
            if(activateStatus(StatusId.PARALYZE))
                bcm.message(self.getName() + " se ha paralizado.");
        }
        else
        {
            if(deactivateStatus(StatusId.PARALYZE))
                bcm.message(self.getName() + " ya no está paralizado.");
        }
    }
    public final boolean isParalyzed() { return isStatusActive(StatusId.PARALYZE); }
    
    public final void setBurn(BattleCommandManager bcm, boolean enabled)
    {
        if(enabled)
        {
            if(!activateStatus(StatusId.BURN))
                return;
            bcm.message(self.getName() + " se ha quemado.");
            setFreeze(bcm, false);
        }
        else
        {
            if(deactivateStatus(StatusId.BURN))
                bcm.message(self.getName() + " ya no está quemado.");
        }
    }
    public final boolean isBurned() { return isStatusActive(StatusId.BURN); }
    
    public final void setPoison(BattleCommandManager bcm, boolean enabled)
    {
        if(enabled)
        {
            if(activateStatus(StatusId.POISON))
                bcm.message(self.getName() + " se ha envenenado.");
        }
        else
        {
            if(deactivateStatus(StatusId.POISON))
                bcm.message(self.getName() + " ya no está envenenado.");
        }
    }
    public final boolean isPoisoned() { return isStatusActive(StatusId.POISON); }
    
    public final void setSleepiness(BattleCommandManager bcm, boolean enabled)
    {
        if(enabled)
        {
            if(isStatusActive(StatusId.SLEEP) || asleepCounter > 0 || !activateStatus(StatusId.ASLEEP))
                return;
            asleepCounter = 1;
            bcm.message(self.getName() + " se siente somnoliento.");
        }
        else
        {
            if(!deactivateStatus(StatusId.ASLEEP))
                return;
            asleepCounter = 0;
        }
    }
    
    public final void setSleep(BattleCommandManager bcm, int turns)
    {
        if(turns >= 0)
        {
            if(!activateStatus(StatusId.SLEEP))
                return;
            sleepTurns = turns;
            asleepCounter = 0;
            bcm.message(self.getName() + " se ha dormido.");
        }
        else
        {
            if(!deactivateStatus(StatusId.SLEEP))
                return;
            sleepTurns = 0;
            asleepCounter = 0;
            bcm.message(self.getName() + " se despertó.");
        }
    }
    public final boolean isSleeping() { return isStatusActive(StatusId.SLEEP); }
    
    public final void setFreeze(BattleCommandManager bcm, boolean enabled)
    {
        if(enabled)
        {
            if(!activateStatus(StatusId.FREEZE))
                return;
            frozenCounter = 0;
            bcm.message(self.getName() + " se ha congelado.");
            setBurn(bcm, false);
        }
        else
        {
            deactivateStatus(StatusId.FREEZE);
            frozenCounter = 0;
            bcm.message(self.getName() + " ya no está congelado.");
        }
    }
    public final boolean isFrozen() { return isStatusActive(StatusId.FREEZE); }
    
    public final void setMute(BattleCommandManager bcm, boolean enabled)
    {
        if(enabled)
        {
            if(activateStatus(StatusId.MUTE))
                bcm.message(self.getName() + " se ha quedado mudo.");
        }
        else
        {
            if(deactivateStatus(StatusId.MUTE))
                bcm.message(self.getName() + " ya no está mudo.");
        }
    }
    public final boolean isMuted() { return isStatusActive(StatusId.MUTE); }
    
    public final void setCurse(BattleCommandManager bcm, boolean enabled)
    {
        if(enabled)
        {
            if(activateStatus(StatusId.CURSE))
                bcm.message(self.getName() + " ha sido maldito.");
        }
        else
        {
            if(deactivateStatus(StatusId.CURSE))
                bcm.message(self.getName() + " ya no está maldito.");
        }
    }
    public final boolean isCursed() { return isStatusActive(StatusId.CURSE); }
    
    public final void setSlow(BattleCommandManager bcm, boolean enabled)
    {
        if(enabled)
        {
            if(activateStatus(StatusId.SLOW))
                bcm.message(self.getName() + " ha entrado en estado lento.");
            setHurry(bcm, false);
        }
        else
        {
            if(deactivateStatus(StatusId.SLOW))
                bcm.message(self.getName() + " ya no está en estado lento.");
        }
    }
    public final boolean isSlowned() { return isStatusActive(StatusId.SLOW); }
    
    public final void setHurry(BattleCommandManager bcm, boolean enabled)
    {
        if(enabled)
        {
            if(activateStatus(StatusId.HURRY))
                bcm.message(self.getName() + " ha entrado en estado prisa.");
            setSlow(bcm, false);
        }
        else
        {
            if(deactivateStatus(StatusId.HURRY))
                bcm.message(self.getName() + " ya no está en estado prisa.");
        }
    }
    public final boolean isHurried() { return isStatusActive(StatusId.HURRY); }
    
    public final void setRegenerate(BattleCommandManager bcm, int turns)
    {
        if(turns >= 0)
        {
            if(!activateStatus(StatusId.REGENERATE))
                return;
            regenerateTurns = turns;
            bcm.message(self.getName() + " ha empezado a regenerarse.");
        }
        else
        {
            if(!deactivateStatus(StatusId.REGENERATE))
                return;
            regenerateTurns = 0;
            bcm.message(self.getName() + " ya no se regenera.");
        }
    }
    public final boolean isRegenerating() { return isStatusActive(StatusId.REGENERATE); }
    
    public final void setBarrier(BattleCommandManager bcm, int turns)
    {
        if(turns >= 0)
        {
            if(!activateStatus(StatusId.BARRIER))
                return;
            barrierTurns = turns;
            bcm.message(self.getName() + " está protegido por una barrera.");
        }
        else
        {
            if(!deactivateStatus(StatusId.BARRIER))
                return;
            barrierTurns = 0;
            bcm.message(self.getName() + " ya no está protegido por una barrera.");
        }
    }
    public final boolean hasBarrier() { return isStatusActive(StatusId.BARRIER); }
    
    public final void setEnergyBarrier(BattleCommandManager bcm, int turns)
    {
        if(turns >= 0)
        {
            if(!activateStatus(StatusId.ENERGY_BARRIER))
                return;
            energyBarrierTurns = turns;
            bcm.message(self.getName() + " está protegido por una barrera energetica.");
        }
        else
        {
            if(!deactivateStatus(StatusId.ENERGY_BARRIER))
                return;
            energyBarrierTurns = 0;
            bcm.message(self.getName() + " ya no está protegido por una barrera energetica.");
        }
    }
    public final boolean hasEnergyBarrier() { return isStatusActive(StatusId.ENERGY_BARRIER); }
    
    public final void setInstantDeath(BattleCommandManager bcm, boolean enabled)
    {
        if(enabled)
        {
            activateStatus(StatusId.INSTANT_DEATH);
        }
        else
        {
            deactivateStatus(StatusId.INSTANT_DEATH);
        }
    }
    
    
    public final void blockAttack() { activateStatus(StatusId.BLOCKED); }
    public final void unblockAttack() { deactivateStatus(StatusId.BLOCKED); }
    public final boolean isBlocked() { return isStatusActive(StatusId.BLOCKED); }
    
    public final void setDefendMode(boolean flag) { activateStatus(StatusId.DEFEND_MODE); }
    public final boolean isDefendModeEnabled() { return isStatusActive(StatusId.DEFEND_MODE); }
    
    
    public final void updateActiveStatus(RNG rng, BattleCommandManager bcm, boolean beforeAttackMode)
    {
        updateActiveSpecialStatus(rng, bcm, beforeAttackMode);
        updateActiveNormalStatus(rng, bcm, beforeAttackMode);
    }
    
    private void updateActiveSpecialStatus(RNG rng, BattleCommandManager bcm, boolean beforeAttackMode)
    {
        for(SpecialStatusId id : SpecialStatusId.iterable())
        {
            if(!isStatusActive(id))
                return;
            switch(id)
            {
                case CONFUSION: {
                    if(beforeAttackMode)
                    {
                        if(confTurns > 0)
                        {
                            confTurns--;
                            bcm.message(self.getName() + " está confuso...").sleep(1500);
                            if(rng.d100(50))
                            {
                                bcm.message("Tan confuso que se hiere a si mismo.")
                                   .sleep(1500)
                                   .damage(self, self.getMaxHealthPoints() / 16);
                                blockAttack();
                            }
                        }
                        else setConfusion(rng, bcm, false);
                    }
                } break;
                case PARALYZE: {
                    if(beforeAttackMode)
                    {
                        if(rng.d100(25))
                        {
                            bcm.message(self.getName() + " está paralizado y no se puede mover")
                               .sleep(1000);
                            blockAttack();
                        }
                    }
                } break;
                case BURN: {
                    if(!beforeAttackMode)
                    {
                        if(self.getElementEffectivity(Element.FIRE) > 0)
                        {
                            bcm.message(self.getName() + " sufre daño por sus quemaduras...")
                               .damage(self, self.getMaxHealthPoints() / 8);
                        }
                    }
                } break;
                case POISON: {
                    if(!beforeAttackMode)
                    {
                        float eff = self.getElementEffectivity(Element.POISON);
                        if(eff > 0)
                        {
                            bcm.message(self.getName() + " sufre daño por el veneno...")
                               .damage(self, self.getMaxHealthPoints() / 8);
                        }
                        else if (eff < 0)
                        {
                            bcm.message(self.getName() + " se siente regenerado por el veneno...")
                               .heal(self, self.getMaxHealthPoints() / 16);
                        }
                    }
                } break;
                case SLEEP: {
                    if(beforeAttackMode)
                    {
                        if(sleepTurns > 0)
                        {
                            sleepTurns--;
                            bcm.message(self.getName() + " está dormido.")
                               .sleep(1500);
                            blockAttack();
                        }
                        else
                        {
                            setSleep(bcm, -1);
                            bcm.sleep(1500);
                        }
                    }
                } break;
                case FREEZE: {
                    if(beforeAttackMode)
                    {
                        if(rng.d128(frozenCounter * 10))
                        {
                            setFreeze(bcm, false);
                        }
                        else
                        {
                            bcm.message(self.getName() + " está congelado y no puede moverse.")
                               .sleep(1200);
                            blockAttack();
                        }
                    }
                } break;
                case MUTE: {} break;
                case CURSE: {
                    if(!beforeAttackMode)
                    {
                        bcm.message(self.getName() + " sufre los efectos de una maldición.")
                           .sleep(1500)
                           .damage(self, self.getMaxHealthPoints() / 4);
                    }
                } break;
                case SLOW: {} break;
                case HURRY: {} break;
                case REGENERATE: {
                    if(!beforeAttackMode)
                    {
                        if(regenerateTurns > 0)
                        {
                            regenerateTurns--;
                            if(!self.getHealthPoints().hasFullHealth())
                            {
                                bcm.message(self.getName() + " recupera un poco de salud.")
                                   .heal(self, self.getMaxHealthPoints() / 16);
                            }
                        }
                        else setRegenerate(bcm, -1);
                    }
                } break;
                case BARRIER: {
                    if(!beforeAttackMode)
                    {
                        if(barrierTurns > 0)
                            barrierTurns--;
                        else setBarrier(bcm, -1);
                    }
                } break;
                case ENERGY_BARRIER: {
                    if(!beforeAttackMode)
                    {
                        if(energyBarrierTurns > 0)
                            energyBarrierTurns--;
                        else setEnergyBarrier(bcm, -1);
                    }
                } break;
                case INSTANT_DEATH: {
                    if(!beforeAttackMode)
                    {
                        bcm.damage(self, self.getMaxHealthPoints());
                    }
                } break;
            }
        }
    }
    
    private void updateActiveNormalStatus(RNG rng, BattleCommandManager bcm, boolean beforeAttackMode)
    {
        if(beforeAttackMode)
        {
            
        }
        else
        {
            deactivateStatus(StatusId.BLOCKED);
            
            if(asleepCounter > 0)
            {
                if(asleepCounter > 2)
                {
                    setSleep(bcm, rng.d7());
                    setSleepiness(bcm, false);
                }
                else asleepCounter++;
            }
            
            deactivateStatus(StatusId.DEFEND_MODE);
        }
    }
    
    public final void applyStatus(StatusId id, RNG rng, BattleCommandManager bcm)
    {
        switch(id)
        {
            case ASLEEP: setSleepiness(bcm, true); break;
            case BARRIER: setBarrier(bcm, 5); break;
            case BLOCKED: blockAttack(); break;
            case BURN: setBurn(bcm, true); break;
            case CONFUSION: setConfusion(rng, bcm, true); break;
            case CURSE: setCurse(bcm, true); break;
            case DEFEND_MODE: setDefendMode(true); break;
            case ENERGY_BARRIER: setEnergyBarrier(bcm, 5); break;
            case FREEZE: setFreeze(bcm, true); break;
            case HURRY: setHurry(bcm, true); break;
            case INSTANT_DEATH: setInstantDeath(bcm, true); break;
            case MUTE: setMute(bcm, true); break;
            case PARALYZE: setParalyze(bcm, true); break;
            case POISON: setPoison(bcm, true); break;
            case REGENERATE: setRegenerate(bcm, 5); break;
            case SLEEP: setSleep(bcm, rng.d7()); break;
            case SLOW: setSlow(bcm, true); break;
        }
    }
    
    
    
    
    /* Special Status extra parameters */
    private int confTurns;
    private int frozenCounter;
    private int sleepTurns;
    private int regenerateTurns;
    private int barrierTurns;
    private int energyBarrierTurns;
    
    
    /* Status extra parameters */
    private int asleepCounter;

}
