/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.masterjefferson.rooms.game;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author Jeff
 */
public class RoomGameApp {
    
    public static void main(String[] args) {
        RoomGame game = new RoomGame();
        long start = System.currentTimeMillis();
        game.play();
        long elapsed = System.currentTimeMillis() - start;
        String playTime = String.format(
                "%dh %dm %ds",
                TimeUnit.MILLISECONDS.toHours(elapsed),
                TimeUnit.MILLISECONDS.toMinutes(elapsed),
                TimeUnit.MILLISECONDS.toSeconds(elapsed)
        );
        System.out.printf("PLAY TIME: %s\n", playTime);
    }
    
}
