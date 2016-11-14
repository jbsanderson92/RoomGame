
package com.masterjefferson.rooms.model;

/**
 *
 * @author Jeff
 */
public class Item {
    
    public final String desc;
    public final int weight;
    
    public Item(String desc, int weight) {
        this.desc = desc;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return String.format("Item [desc='%s', weight=%d]", desc, weight);
    }
    
    
    
}
