/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.games.ec.battle.cmd;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import kp.games.ec.creature.Creature;
import kp.games.ec.creature.attributes.StatId;
import kp.games.ec.creature.attributes.StatusId;

/**
 *
 * @author Asus
 */
public final class BattleCommandManager implements Iterable<BattleCommand>
{
    private final LinkedList<BattleCommand> cmds = new LinkedList<>();
    
    public final BattleCommandManager addCommand(BattleCommand cmd) { cmds.addLast(Objects.requireNonNull(cmd)); return this; }
    public final BattleCommandManager addCommand(BattleCommandId id, Object... data) { cmds.addLast(new BattleCommand(id, data)); return this; }
    
    public final boolean hasMoreCommands() { return !cmds.isEmpty(); }
    
    public final BattleCommand pollCommand() { return cmds.removeFirst(); }

    @Override
    public final Iterator<BattleCommand> iterator() { return cmds.iterator(); }
    
    
    public final BattleCommandManager message(String message)
    {
        return addCommand(BattleCommandId.MESSAGE, message);
    }
    
    public final BattleCommandManager damage(int creatureBattlePositionId, int points)
    {
        return addCommand(BattleCommandId.DAMAGE, creatureBattlePositionId, points);
    }
    public final BattleCommandManager damage(Creature target, int points)
    {
        return damage(target.getBattlePositionId(), points);
    }
    
    public final BattleCommandManager heal(int creatureBattlePositionId, int points)
    {
        return addCommand(BattleCommandId.HEAL, creatureBattlePositionId, points);
    }
    public final BattleCommandManager heal(Creature target, int points)
    {
        return damage(target.getBattlePositionId(), points);
    }
    
    public final BattleCommandManager statModif(int creatureBattlePositionId, StatId stat, int points)
    {
        return addCommand(BattleCommandId.STAT_MODIF, creatureBattlePositionId, stat, points);
    }
    public final BattleCommandManager statModif(Creature target, StatId stat, int points)
    {
        return statModif(target.getBattlePositionId(), stat, points);
    }
    
    public final BattleCommandManager applyStatus(int creatureBattlePositionId, StatusId stateId, boolean immunity)
    {
        return addCommand(BattleCommandId.APPLY_STATUS, creatureBattlePositionId, stateId, immunity);
    }
    public final BattleCommandManager applyStatus(Creature target, StatusId stateId, boolean immunity)
    {
        return applyStatus(target.getBattlePositionId(), stateId, immunity);
    }
    
    public final BattleCommandManager sleep(long millis)
    {
        return addCommand(BattleCommandId.SLEEP, millis);
    }
}
