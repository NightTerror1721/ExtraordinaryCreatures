/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.games.ec.battle.cmd;

import java.util.Objects;

/**
 *
 * @author Asus
 */
public class BattleCommand
{
    private final BattleCommandId id;
    private final Object[] data;
    
    public BattleCommand(BattleCommandId id, Object... data)
    {
        this.id = Objects.requireNonNull(id);
        this.data = Objects.requireNonNull(data);
    }
    
    public final BattleCommandId getId() { return id; }
    
    public final <T> T getData(int index) { return (T) data[index]; }
}
