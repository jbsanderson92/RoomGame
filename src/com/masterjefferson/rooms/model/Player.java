/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.masterjefferson.rooms.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents player's character.
 */
public class Player {
    //name of player
    private final String name;
    //player inventory
    private final List<Item> backpack;
    
    public Player(String name) {
        this.name = name;
        this.backpack = new ArrayList<>();
    }
    
    /**
     * Add items to the players inventory.
     */
    public void giveItems(Item[] items) {
        Collections.addAll(backpack, items);
    }
    
    public String getName() {
        return name;
    }
    
    public List<Item> getItems() {
        return backpack;
    }
    
    /**
     * Computes the weight of the players backpack (inventory)
     */
    public int backpackWeight() {
        int weight = 0;
        for (Item i : backpack) {
            weight += i.weight;
        }
        return weight;
    }
    
}
