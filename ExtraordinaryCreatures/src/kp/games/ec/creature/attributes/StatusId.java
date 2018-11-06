/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.games.ec.creature.attributes;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

/**
 *
 * @author Asus
 */
public enum StatusId
{
    CONFUSION(SpecialStatusId.CONFUSION),
    PARALYZE(SpecialStatusId.PARALYZE),
    BURN(SpecialStatusId.BURN),
    POISON(SpecialStatusId.POISON),
    SLEEP(SpecialStatusId.SLEEP),
    FREEZE(SpecialStatusId.FREEZE),
    MUTE(SpecialStatusId.MUTE),
    CURSE(SpecialStatusId.CURSE),
    SLOW(SpecialStatusId.SLOW),
    HURRY(SpecialStatusId.HURRY),
    REGENERATE(SpecialStatusId.REGENERATE),
    BARRIER(SpecialStatusId.BARRIER),
    ENERGY_BARRIER(SpecialStatusId.ENERGY_BARRIER),
    INSTANT_DEATH(SpecialStatusId.INSTANT_DEATH),
    
    BLOCKED("Bloqueo"),
    ASLEEP("Somnolencia"),
    DEFEND_MODE("Modo Defensa");
    
    private final SpecialStatusId special;
    private final String name;
    
    private StatusId(SpecialStatusId special)
    {
        this.special = Objects.requireNonNull(special);
        this.name = special.getName();
    }
    
    private StatusId(String name)
    {
        this.special = null;
        this.name = name;
    }
    
    public final String getName() { return name; }
    
    @Override
    public final String toString() { return name; }
    
    public final boolean isSpecialStatus() { return special != null; }
    
    public final SpecialStatusId getSpecialStatusId() { return special; }
    
    private static final StatusId[] VALUES = values();
    public static final int count() { return VALUES.length; }
    public static final StatusId fromValue(int value)
    {
        return VALUES[value];
    }
    
    public static final Iterator<StatusId> iterator()
    {
        return new Iterator<StatusId>()
        {
            private int it = 0;
            @Override public boolean hasNext() { return it < VALUES.length; }
            @Override public StatusId next() { return VALUES[it++]; }
        };
    }
    public static final Iterable<StatusId> iterable() { return StatusId::iterator; }
    
    
    private static final HashMap<SpecialStatusId, StatusId> SIDS = new HashMap<>();
    static {
        for(StatusId id : iterable())
            if(id.isSpecialStatus())
                SIDS.put(id.special, id);
    }
    
    
    public static final StatusId specialToNormal(SpecialStatusId id) { return SIDS.get(id); }
}
