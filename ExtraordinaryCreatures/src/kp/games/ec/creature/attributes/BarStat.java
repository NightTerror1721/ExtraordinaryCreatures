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
class BarStat extends Stat
{
    int current;
    
    public BarStat(StatId id)
    {
        super(id);
        this.current = getValue();
    }
    
    final void modifyCurrent(int amount)
    {
        current += amount;
        int value = getValue();
        current = current < 0 ? 0 : current > value ? value : current;
    }
    
    @Override
    public final void updateValue(int level)
    {
        super.updateValue(level);
        int value = getValue();
        if(current > value)
            current = value;
    }
}
