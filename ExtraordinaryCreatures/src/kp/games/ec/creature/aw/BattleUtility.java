/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.games.ec.creature.aw;

import java.util.Objects;

/**
 *
 * @author Asus
 */
class BattleUtility
{
    private String name = "";
    protected int physic;
    protected int energy;
    protected int accuracyEvasion;
    private int slots;
    private int price;
    
    BattleUtility() {}
    
    public final void setName(String name) { this.name = Objects.requireNonNull(name); }
    public final String getName() { return name; }
    
    public final void setSlots(int slots) { this.slots = slots < 0 ? 0 : slots; }
    public final int getSlots() { return slots; }
    
    public final void setPrice(int price) { this.price = price < 0 ? 0 : price; }
    public final int getPrice() { return price; }
}
