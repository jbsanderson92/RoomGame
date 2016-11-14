/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.masterjefferson.rooms.model;

import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author Jeff
 */
public class Room {

    //"exits" or doors that lead to other rooms.
    private final HashMap<Direction, Room> exits;

    private final String desc;
    //Items in this room
    private final Item[] items;
    
    private boolean itemsTaken = false;

    /**
     * Constructor.
     *
     * @param exits The map of rooms that are connected to this one through
     * exits.
     * @param items The items to be contained in this room.
     */
    public Room(String desc, Item[] items) {
        this.exits = new HashMap<>();
        this.desc = desc;
        this.items = items;
    }

    public void setExit(Direction direction, Room room) {
       if (exits.get(direction) == null) {
           exits.put(direction, room);
           room.setExit(Direction.getOpposite(direction), this);
       }
    }

    /**
     * Check if there is an exit in the specified direction.
     *
     * @param dir The direction of interest
     * @return true if there is a door to another room in that direction
     */
    public boolean hasExit(Direction dir) {
        return exits.get(dir) != null;
    }
    
    public boolean connectedToRoom(Room other) {
        return exits.containsValue(other);
    }

    /**
     * Returns the room in the specified direction.
     *
     * @param direction The direction of the door you want to open.
     * @return The room behind the door, or null if there isn't a door in that
     * direction.
     */
    public Room exit(Direction direction) {
        return exits.get(direction);
    }

    /**
     * Returns the items contained in this room.
     */
    public Item[] takeItems() {
        if (!itemsTaken) {
            itemsTaken = true;
            return items;
        } 
        return null;
    }

    @Override
    public String toString() {
        Room north = exit(Direction.NORTH);
        Room south = exit(Direction.SOUTH);
        Room east = exit(Direction.EAST);
        Room west = exit(Direction.WEST);
        return String.format("%s (N=%s, S=%s, E=%s, W=%s)", 
                desc, 
                north == null ? "none" : north.desc, 
                south == null ? "none" : south.desc, 
                east == null ? "none" : east.desc, 
                west == null ? "none" : west.desc
        );
    }

}
