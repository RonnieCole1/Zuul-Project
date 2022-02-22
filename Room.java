import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room. The room can also be locked.
 * 
 * Rooms can have items or characters stored in them. They may also be used to teleport to other rooms.
 * 
 * @author  Michael Kölling, David J. Barnes and Ronnie Cole
 * @version 5/10/2020
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private ArrayList<Item> itemsInRoom;        // stores items in room.
    private ArrayList<Character> charactersInRoom; //stores characters in room.
    private boolean roomLocked;
    private ArrayList<Room> teleportToRooms;
    
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        itemsInRoom = new ArrayList<>();
        charactersInRoom = new ArrayList<>();
        teleportToRooms = new ArrayList<>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
    
    /**
     * Sets the room to locked or unlocked
     * @param lock determins if room is locked or not
     */
    public void setLocked(boolean lock)
    {
        roomLocked = lock;
    }
    
    /**
     * Checks to see if room is locked
     * @return true if locked, false otherwise.
     */
    public boolean getLocked()
    {
        return roomLocked;
    }
    
    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Finds and returns an item.
     * @param name The name of the item we are trying to find
     * @return The item we found.
     */
    public Item findItems(String name)
    {
        int i = 0;
        while (i < itemsInRoom.size()) {
            if (itemsInRoom.get(i).getName().equals(name))
            {
                return itemsInRoom.get(i);
            }
            i++;
        } 
        return null;
    }
    
    /**
     * Removes an item from the room by name.
     * @param name The name of the item we want removed.
     */
    public void removeItems(Item name)
    {
        itemsInRoom.remove(name);
    }
    
    /**
     * Removes a character from the room by name.
     * @param name The name of the character we want removed.
     */
    public void removeCharacters(Character name)
    {
        charactersInRoom.remove(name);
    }
    
    /**
     * Add an item to the room.
     * @param name The name of the item we want added in our room.
     */
    public void addItem(Item name)
    {
        itemsInRoom.add(name);
    }
    
    /**
     * Add a character to the room.
     * @param name The name of the character we want added in our room.
     */
    public void addCharacter(Character name)
    {
        charactersInRoom.add(name);
    }
    
    /**
     * Check if there is a character in the room
     * @return True if character is in the room, false otherwise.
     */
    public boolean checkRoomForCharacter()
    {
        boolean result = false;
        int i = 0;
        if (charactersInRoom.size() == 0) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }
    
    /**
     * Returns whatever character is in the room
     * @return The character in the room.
     */
    public Character findCharacter()
    {
        Character character = null;
        int i = 0;
        while (i < charactersInRoom.size()) {
            character = charactersInRoom.get(i);
            i++;
        } 
        return character;
    }
    
    /**
     * Returns a list of items in the room
     * @return An item list.
     */
    public String getItemList()
    {
        String itemList = "";
        if (itemsInRoom.size() == 0) {
            itemList = "This room is empty";
        } else {
            itemList = "This room contains:";
        }
        int i = 0;
        while (i < itemsInRoom.size()) {
            itemList = itemList + " " + itemsInRoom.get(i).getName();
            i++;
        }
        return itemList;
    }
    
    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ". " + getItemList() + ".\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
}

