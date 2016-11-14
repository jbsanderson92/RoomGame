/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.masterjefferson.rooms.game;

import com.masterjefferson.rooms.model.Direction;
import com.masterjefferson.rooms.model.Item;
import com.masterjefferson.rooms.model.Player;
import com.masterjefferson.rooms.model.Room;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Jeff
 */
public class RoomGame {

    private final List<Item> allItems = Arrays.asList(
            new Item("Used syringe", 1),
            new Item("Tampon", 1),
            new Item("Brick", 5),
            new Item("Severed arm", 3),
            new Item("Prescription painkillers", 1),
            new Item("Dick in a box", 50),
            new Item("Chainsaw", 8),
            new Item("Hillary clinton's emails", 30000)
    );

    enum GameState {
        START, PLAYING, FINISHED, EXIT
    }

    enum Command {
        START("start"),
        EXIT("exit"),
        ROOM("room"),
        TAKE_ITEMS("take items"),
        NORTH("north"),
        SOUTH("south"),
        EAST("east"),
        WEST("west"),
        INVENTORY("inventory"),
        HELP("help");

        String cmdString;

        static Command forString(String command) {
            for (Command c : values()) {
                if (c.cmdString.equals(command)) {
                    return c;
                }
            }
            return null;
        }

        Command(String cmdString) {
            this.cmdString = cmdString;
        }
    }

    private final Scanner consoleInput;
    private GameState state;
    private Player currentPlayer;
    private Room currentRoom;

    public RoomGame() {
        this.consoleInput = new Scanner(System.in);
        this.state = GameState.START;
    }

    public void play() {
        while (true) {
            switch (state) {
                case START:
                    startStateHandler();
                    break;
                case PLAYING:
                    playingStateHandler();
                    break;
                case FINISHED:
                    finishedStateHandler();
                    break;
                case EXIT:
                    return;
            }
        }
    }

    private void startStateHandler() {
        printf("Hi, welcome to the Rooms Game.\n");
        printf("Type 'help' to see a list of commands. type 'start' to start the game.\n");
        Command cmd = getConsoleCommand();
        if (cmd == Command.START) {
            createPlayer();
            initializeRooms();
        } else if (cmd == Command.EXIT) {
            state = GameState.FINISHED;
        } else if (cmd == Command.HELP) {
            printCommands();
        }
    }
    
    private void playingStateHandler() {
        Command cmd = getConsoleCommand();
        switch (cmd) {
            case ROOM:
                printCurrentRoom();
                break;
            case TAKE_ITEMS:
                takeRoomItems();
                break;
            case NORTH:
                moveRooms(Direction.NORTH);
                break;
            case SOUTH:
                moveRooms(Direction.SOUTH);
                break;
            case EAST:
                moveRooms(Direction.EAST);
                break;
            case WEST:
                moveRooms(Direction.WEST);
                break;
            case INVENTORY:
                printInventory();
                break;
            case EXIT:
                state = GameState.FINISHED;
                break;
            default:
                printf("You can't use that right now (%s).", cmd.cmdString);
        }
    }
    
    private void finishedStateHandler() {
        printf("Thanks for playing%s\n", 
                currentPlayer == null ? "!" : currentPlayer.getName() + "!"
        );
        state = GameState.EXIT;
    }
    
    private void takeRoomItems() {
        Item[] items = currentRoom.takeItems();
        if (items != null) {
            printf("Taking items: %s\n", Arrays.toString(items));
            currentPlayer.giveItems(items);
        } else {
            printf("There nothing here...\n");
        }
    }
    
    private void moveRooms(Direction dir) {
        Room next = currentRoom.exit(dir);
        if (next != null) {
            printf("Moving %s to %s.\n", dir, next);
            currentRoom = next;
        } else {
            printf("Hmm, there doesn't seem to be anything to the %s.\n", dir);
        }
    }

    private void initializeRooms() {
        //randomize avaialble items
        Collections.shuffle(allItems);
        //will contain all rooms
        List<Room> rooms = new ArrayList<>();
        Random rand = new Random();
        //arbitrary
        int numRooms = 5;
        for (int i = 0; i < numRooms; i++) {
            //rooms have 3 items max
            rooms.add(new Room("Room #" + i, getRandomItems(3)));
        }
        printf("There will be %d rooms.\n", rooms.size());
        //connect rooms
        Room roomOne = rooms.get(0);
        roomOne.setExit(Direction.NORTH, rooms.get(1));
        roomOne.setExit(Direction.SOUTH, rooms.get(2));
        roomOne.setExit(Direction.EAST, rooms.get(3));
        roomOne.setExit(Direction.WEST, rooms.get(4));
        printRooms(rooms);
        printf("Game started.\n");
        currentRoom = roomOne;
        state = GameState.PLAYING;
    }
    
    private Item[] getRandomItems(int maxCount) {
        Random rand = new Random();
        int numItems = rand.nextInt(maxCount);
        Item[] items = new Item[numItems];
        for (int i = 0; i < numItems; i++) {
            items[i] = allItems.get(rand.nextInt(allItems.size()));
        }
        return items;
    }

    private void createPlayer() {
        printf("Please enter your name > ");
        String name = consoleInput.nextLine();
        currentPlayer = new Player(name);
        printf("Hi %s, let's play.\n", currentPlayer.getName());
    }

    private Command getConsoleCommand() {
        printf("enter command > ");
        Command cmd = null;
        while (cmd == null) {
            String input = consoleInput.nextLine();
            cmd = Command.forString(input);
            if (cmd == null) {
                printf("unknown command: '%s'\n", input);
            }
        }
        return cmd;
    }

    private void printCommands() {
        printf("Commands:\n");
        for (Command c : Command.values()) {
            printf("\t%s\n", c.cmdString);
        }
    }
    
    private void printRooms(List<Room> rooms) {
        printf("Rooms:\n");
        for (Room r : rooms) {
            printf("\t%s\n", r);
        }
    }
    
    private void printCurrentRoom() {
        printf("you are in %s\n", currentRoom);
    }
    
    private void printInventory() {
        printf("Inventory:\n");
        printf("\tBackpack weight: %d lbs\n", currentPlayer.backpackWeight());
        for (Item i : currentPlayer.getItems()) {
            printf("\t%s\n", i);
        }
    }

    private void printf(String fmt, Object... args) {
        System.out.printf(fmt, args);
    }

}
