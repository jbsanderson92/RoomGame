/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.masterjefferson.rooms.model;

/**
 * Simple enumeration of compass directions.
 */
public enum Direction {
    NORTH, SOUTH, EAST, WEST;
    
    public static Direction getOpposite(Direction dir) {
        if (dir == NORTH) {
            return SOUTH;
        } else if (dir == SOUTH) {
            return NORTH;
        } else if (dir == EAST)  {
            return WEST;
        } else {
            return EAST;
        }
    }
}
