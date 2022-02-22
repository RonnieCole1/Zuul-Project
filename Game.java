import java.util.Random;
import java.util.ArrayList;
/**
 *  This class describes a game where you are a space pirate who comes across a disabled ship.
 *  Your goal is to board the ship, secure it, and then repair it.
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, characters, and items creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 *  
 *  When the engines are repaired and the player arrives at the bridge, the game is over and the player won. If the players
 *  health is set to 0, the game is over and the player lost.
 * 
 * @author  Michael Kölling, David J. Barnes, and Ronnie Cole
 * @version 5/10/2020
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Random rng;
    private ArrayList<Item> inventory;
    private static final int LIMIT = 12;
    private int inventoryWeight;
    private Room previousRoom;
    private boolean engineDamageState;
    private int playerHealth = 200;
    private ArrayList<Room> roomList;
    private static final int timeLimit = 30;
    
    public static void main(String[] args) 
    {
        Game game = new Game();
        game.play();
    }
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRoomsAndItemsAndCharacters();
        rng = new Random(4);
        parser = new Parser();
        inventory = new ArrayList<>();
        inventoryWeight = 0;
        engineDamageState = false;
        roomList = new ArrayList<>();
    }
    
    /**
     * Create all the rooms and link their exits together. Also creates and puts items and characters in the rooms
     */
    private void createRoomsAndItemsAndCharacters()
    {
        Room engineering, cargo, airlock, space, habitation, bathroom, bridge, ship, teleporter;
      
        // creates teleporter room
        teleporter = new Room("in the teleporter room");
        
        // creates engineering with items
        engineering = new Room("in engineering");
        
        // creates damaged engines with description
        Item engines = new Item();
        engines.setName("engines");
        engines.setDescription("The engines of the ship appear to be heavily damaged. However, a repair tool may put them in working order.");
        engines.setWeight(0);
        engines.setGrabable(false);
        
        // creates engineer character
        Character engineer = new Character();
        engineer.setName("engineer");
        engineer.setArmsHealth(50);
        engineer.setLegsHealth(50);
        engineer.setHeadHealth(50);
        engineer.setTorsoHealth(50);
        
        // adds items to engineering
        engineering.addItem(engines);
        
        // adds characters to engineering
        engineering.addCharacter(engineer);
        
        // creates cargo room with items
        cargo = new Room("in the cargo hold");;
        
        // creates guard character
        Character guard = new Character();
        guard.setName("guard");
        guard.setArmsHealth(50);
        guard.setLegsHealth(50);
        guard.setHeadHealth(100);
        guard.setTorsoHealth(75);
        
        // creates cargo crates with description
        Item crates = new Item();
        crates.setName("crates");
        crates.setDescription("The cargohold appears fully loaded with unmarked cargo. You aren't sure what they contain.");
        crates.setWeight(0);
        crates.setGrabable(false);
        
        // creates keycard to be picked up and used
        Item keycard = new Item();
        keycard.setName("keycard");
        keycard.setDescription("A blue keycard marked with the ship's brandname RSI. This likely belongs to a locked door within the ship.");
        keycard.setWeight(0);
        keycard.setGrabable(true);
        keycard.setTakeString("You pick up the keycard.");
        
        // adds characters to cargo
        cargo.addCharacter(guard);
        
        // adds items to cargo room
        cargo.addItem(crates);
        cargo.addItem(keycard);
        
        // creates airlock
        airlock = new Room("in the airlock");
        
        // creates space
        space = new Room("in space");
        
        // creates airlock with description
        Item airlock_door = new Item();
        airlock_door.setName("airlock_door");
        airlock_door.setDescription("A reinforced airlock door. You might be able to hack into it");
        airlock_door.setWeight(0);
        airlock_door.setGrabable(false);
        
        // adds item to space
        space.addItem(airlock_door);
        
        // creates habitation deck with items
        habitation = new Room("in the ship's living quarters");
        
        // creates window with description
        Item window = new Item();
        window.setName("window");
        window.setDescription("Through the window you see your own ship and the surronding nebula.");
        window.setWeight(0);
        window.setGrabable(false);
        
        // creates kitchen with description
        Item kitchen = new Item();
        kitchen.setName("kitchen");
        kitchen.setDescription("The ship's kitchen contain");
        kitchen.setWeight(0);
        kitchen.setGrabable(false);
        
        // creates beds with description
        Item beds = new Item();
        beds.setName("beds");
        beds.setDescription("There are 2 beds on both sides of the room.");
        beds.setWeight(0);
        beds.setGrabable(false);
        
        // creates journal with description
        Item journal = new Item();
        journal.setName("journal");
        journal.setDescription("A small journal by one of the crewmembers");
        journal.setWeight(1);
        journal.setGrabable(true);
        journal.setTakeString("You pick up the journal.");
        
        // creates picture with description
        Item picture = new Item();
        picture.setName("picture");
        picture.setDescription("A framed picuture of one of the crewmembers.");
        picture.setWeight(1);
        picture.setGrabable(true);
        picture.setTakeString("You pick up the picture.");
        
        // adds items to habitation
        habitation.addItem(window);
        habitation.addItem(kitchen);
        habitation.addItem(beds);
        habitation.addItem(journal);
        habitation.addItem(picture);
        
        // creates bathroom with items
        bathroom = new Room("in a bathroom");
        
        // creates toilet paper with description
        Item toilet_paper = new Item();
        toilet_paper.setName("toilet_paper");
        toilet_paper.setDescription("Just some toilet paper.");
        toilet_paper.setWeight(0);
        toilet_paper.setGrabable(true);
        toilet_paper.setTakeString("You pick up the toilet_paper.");
        
        // adds items to bathroom
        bathroom.addItem(toilet_paper);
        
        // creates bridge with items
        bridge = new Room("on the ship's bridge");
        
        // creats pilot character
        Character pilot = new Character();
        pilot.setName("pilot");
        pilot.setArmsHealth(40);
        pilot.setLegsHealth(40);
        pilot.setHeadHealth(40);
        pilot.setTorsoHealth(40);
        
        // creates pilot seat with description
        Item pilot_seat = new Item();
        pilot_seat.setName("pilot_seat");
        pilot_seat.setDescription("");
        pilot_seat.setWeight(0);
        pilot_seat.setGrabable(false);
        
        // adds characters to bridge
        bridge.addCharacter(pilot);
        
        // adds items to bridge
        bridge.addItem(pilot_seat);
        bridge.setLocked(true);
        
        // creates ship with items
        ship = new Room("on your own ship");
        
        // creates lethal weapon with description and weight
        Item Rifle = new Item();
        Rifle.setName("Rifle");
        Rifle.setDescription("A lethal weapon which can take down enemies.");
        Rifle.setWeight(7);
        Rifle.setGrabable(true);
        Rifle.setTakeString("You pick up the Rifle");
        
        // creates non-lethal weapon with description and weight
        Item StunGun = new Item();
        StunGun.setName("StunGun");
        StunGun.setDescription("A non-lethal weapon which can take down enemies. Difficult to aim.");
        StunGun.setWeight(1);
        StunGun.setGrabable(true);
        StunGun.setTakeString("You pick up the StunGun");
        
        // creates hackingtool to be used on airlock door with description and weight 
        Item Hackingtool = new Item();
        Hackingtool.setName("HackingTool");
        Hackingtool.setDescription("A tool capable of opening some doors.");
        Hackingtool.setWeight(2);
        Hackingtool.setGrabable(true);
        Hackingtool.setTakeString("You pick up the HackingTool");
        
        // adds items to ship
        ship.addItem(Rifle);
        ship.addItem(StunGun);
        ship.addItem(Hackingtool);
        
        // initialise room exits
        
        teleporter.setExit("south", cargo);
        
        ship.setExit("north", space);
        
        space.setExit("north", airlock);
        space.setExit("south", ship);
        
        airlock.setExit("north", cargo);
        airlock.setExit("south", space);
        
        cargo.setExit("east", habitation);
        cargo.setExit("west", engineering);
        cargo.setExit("south", airlock);
        cargo.setExit("north", teleporter);
        
        engineering.setExit("east", cargo);
        
        habitation.setExit("east", bridge);
        habitation.setExit("west", cargo);
        habitation.setExit("north", bathroom);
        
        bridge.setExit("west", habitation);
        
        bathroom.setExit("south", habitation);
        
        // add rooms to roomList
        roomList.add(teleporter);
        roomList.add(engineering);
        roomList.add(cargo);
        roomList.add(airlock);
        roomList.add(space);
        roomList.add(habitation);
        roomList.add(bathroom);
        roomList.add(bridge);
        roomList.add(ship);
        
        currentRoom = ship;  // start game outside
    }
    
    // Inventory methods
    
    /**
     * Finds and returns an item from inventory.
     * @param Name of item we are looking for
     * @return The Item we are looking for
     */
    public Item findInventoryItem(String name)
    {
        int i = 0;
        while (i < inventory.size()) {
            if (inventory.get(i).getName().equals(name))
            {
                return inventory.get(i);
            }
            i++;
        }
        return null;
    }
    
    /**
     * Removes an item from inventory.
     * @param Name of item we want to remove
     */
    public void removeInventoryItem(Item name)
    {
        inventory.remove(name);
    }
    
    /**
     * Add an item to inventory.
     * @param Name of item we want to add to our inventory.
     */
    public void addInventoryItem(Item name)
    {
        inventory.add(name);
    }
    
    /**
     * Returns list of inventory items.
     * @return A list of items
     */
    public String getItemList()
    {
        String itemInventoryList = "Your inventory contains:";
        int i = 0;
        while (i < inventory.size()) {
            itemInventoryList = itemInventoryList + " " + inventory.get(i).getName();
            i++;
        }
        return itemInventoryList;
    }
    
    // end of inventory methods. 
    
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        int time = 0;
        while (! finished) {
            Command command = parser.getCommand();
            time = time + 1;
            finished = processCommand(command);
            if (winConditions() == true) {
                System.out.println("You sit in the pilot seat and attempt to fly the ship. You set a marker on the nearest station on your Starmap and set off with your new ship.");
                System.out.println("You've won!");
                finished = true;
            }
            if (playerHealth == 0) {
                System.out.println("You've taken too much damage to continue your mission. The crew holds you down until the authorities arive.");
                System.out.println("You've lost!");
                finished = true;
            }
            if (time == timeLimit) {
                System.out.println("You've run out of time.");
                System.out.println("You've lost!");
                finished = true;
            }
        }
        System.out.println("Thank you for playing.  Good bye.");
    }
    
    /**
     * Checks if the winConditions have been met
     * @return true if the engines are repaired and we are in the bridge, false otherwise.
     */
    private boolean winConditions()
    {
        if (engineDamageState == true && currentRoom == roomList.get(7)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("    The year is 2950. You are a freelance space pirate tasked to find or steal ships for salvage or resale.");
        System.out.println("While out on patrol, something appears on your radar. A cargo ship has sent out a distress signal, claiming");
        System.out.println("they have broken down and need assistance. You fly closer and inspect the condition of the ship. While its");
        System.out.println("engines appear to be aftermarket and busted, the rest of the ship is in pristine condition. Even with");
        System.out.println("aftermarket engines,the ship could still sell for thousands, enough for a months worth of rent. You park next");
        System.out.println("to the ship, and ready yourself to board the ship and engage its crew.");
        System.out.println("");
        System.out.println("    Your goal is to clear the ship of hostiles, repair the engines if possible, and reach the bridge where you");
        System.out.println("can take control of the ship.");
        System.out.println("");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("take")) {
            goTake(command);
        }
        else if (commandWord.equals("drop")) {
            goDrop(command);
        }
        else if (commandWord.equals("look")) {
            goLook(command);
        }
        else if (commandWord.equals("inspect")) {
            goInspect(command);
        }
        else if (commandWord.equals("back")) {
            goBack(command);
        }
        else if (commandWord.equals("use")) {
            use(command);
        }
        else if (commandWord.equals("inspectitem")) {
            goInspectItem(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Also shows command words.
     */
    private void printHelp() 
    {
        System.out.println("You must board the ship, repair its engines, subdue the crew, and reach the bridge");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     *  Allows player to pick up item if it is in the room, is grabable, and isnt too heavy.
     *  @param command The command to be processed.
     */
    private void goTake(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to take...
            System.out.println("Take what?");
            return;
        }
        
        String findItem = command.getSecondWord();
        
        Item i = currentRoom.findItems(findItem);
        
        if (i == null)
        {
            System.out.println("The item your looking for doesn't appear to be in your room");
        } else {
            if (i.isGrabable() == false)
            {
                System.out.println("You can't pick this item up");
                return;
            } else {
                if (inventoryWeight + i.getWeight() > LIMIT) {
                    System.out.println("The item your trying to pick up is too heavy");
                    return;
                } else {
                    currentRoom.removeItems(i);
                    inventory.add(i);
                    i.takeString();
                    inventoryWeight = inventoryWeight + i.getWeight();
                    return;
                }
            }
        }
    }
    
    /** 
     * Allows player to drop an item.
     * @param command The command to be processed.
     */
    private void goDrop(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to drop...
            System.out.println("Drop what?");
            return;
        }
        
        String findItem = command.getSecondWord();
        
        Item i = findInventoryItem(findItem);
        
        if (i == null)
        {
            System.out.println("The item your looking for isn't in your inventory");
        } else {
            removeInventoryItem(i);
            currentRoom.addItem(i);
            inventoryWeight = inventoryWeight - i.getWeight();
        }
    }
    
    /** 
     * Allows player to inspect the room.
     * @param command The command to be processed.
     */
    private void goLook(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Look at what?");
            return;
        }
        else {
            System.out.println(currentRoom.getLongDescription());
            return;
        }
    }   
    
    // Use methods
    
    /** 
     * Checks which secondword the player inputed into use.
     * @param command The command to be processed.
     */
    private void use(Command command)
    {
        if (command.getSecondWord().equals("Rifle")) {
            useRifle();
        }
        else if (command.getSecondWord().equals("StunGun")) {
            useStunGun();
        }
        else if (command.getSecondWord().equals("HackingTool")) {
            useHackingTool();
        }
        else if (command.getSecondWord().equals("RepairTool")) {
            useRepairTool();
        }
        else if (command.getSecondWord().equals("keycard")) {
            useKeycard();
        }
    }
    
    /** 
     * Allows the player to use a rifle on characters in currentroom. If there are characters in the room and the player
     * has the gun in their inventory, the gun will be able to fire and damage the enemy character, inflicting some
     * damage value on some part of the enemy players body (with some chance to fail).
     */
    private void useRifle()
    {
        if (findInventoryItem("Rifle") == null) {
            System.out.println("This item is not in your inventory!");
        } else {
            if (currentRoom.checkRoomForCharacter() == false) {
                System.out.println("Use Rifle on what? No one is in this room.");
            } else {
                rng = new Random(2);
                int fireAtTarget = rng.nextInt(3);
                int newLegHealth = 0;
                int newArmHealth = 0;
                int newTorsoHealth = 0;
                int newHeadHealth = 0;
                Character target = currentRoom.findCharacter();
                System.out.println("You use your Rifle on the " + target.getName());
                if (fireAtTarget == 0) {
                    newLegHealth = target.getLegsHealth()-50;
                    target.setLegsHealth(newLegHealth);
                    System.out.println("Your shot hits the " + target.getName() + " in the leg.");
                }
                if (fireAtTarget == 1) {
                    newArmHealth = target.getArmsHealth()-50;
                    target.setArmsHealth(newArmHealth);
                    System.out.println("Your shot hits the " + target.getName() + " in the arm.");
                }
                if (fireAtTarget == 2) {
                    newTorsoHealth = target.getTorsoHealth()-50;
                    target.setTorsoHealth(newTorsoHealth);
                    System.out.println("Your shot hits the " + target.getName() + " in the torso.");
                }
                if (fireAtTarget == 3) {
                    newHeadHealth = target.getHeadHealth()-50;
                    target.setHeadHealth(newHeadHealth);
                    System.out.println("Your shot hits the " + target.getName() + " in the head.");
                }
                if (target.downedState() == true) {
                    System.out.println("The" + target.getName() + " has been downed");
                    currentRoom.removeCharacters(target);
                    if (target.getName().equals("engineer") && currentRoom.findItems("RepairTool") == null) {
                        Item RepairTool = new Item();
                        RepairTool.setName("RepairTool");
                        RepairTool.setDescription("A basic repair tool. Capable of repairing ship and gun components.");
                        RepairTool.setWeight(0);
                        RepairTool.setGrabable(true);
                        currentRoom.addItem(RepairTool);
                    }
                }
                if (target.disarmedState() == true) {
                    System.out.println("The" + target.getName() + " has been disarmed");
                    if (target.getName().equals("engineer") && currentRoom.findItems("RepairTool") == null) {
                        Item RepairTool = new Item();
                        RepairTool.setName("RepairTool");
                        RepairTool.setDescription("A basic repair tool. Capable of repairing ship and gun components.");
                        RepairTool.setWeight(0);
                        RepairTool.setGrabable(true);
                        currentRoom.addItem(RepairTool);
                    }
                }
            }
        }
    }
    
    /** 
     * Allows the player to use some non-lethal alternative to the rilfe and functions similarly to the rifle. The stungun
     * has a higher failure rate.
     */
    private void useStunGun()
    {
        if (findInventoryItem("StunGun") == null) {
            System.out.println("This item is not in your inventory!");
        } else {
            if (currentRoom.checkRoomForCharacter() == false) {
                System.out.println("Use StunGun on what? No one is in this room.");
            } else {
                rng = new Random(0);
                int stunTarget = rng.nextInt(3);
                Character target = currentRoom.findCharacter();
                System.out.println("you used your stungun on " + target.getName());
                if (stunTarget == 0 ) {
                    System.out.println("The" + target.getName() + " has been stunned");
                    currentRoom.removeCharacters(target);
                    if (target.getName().equals("engineer") && currentRoom.findItems("RepairTool") == null) {
                        Item RepairTool = new Item();
                        RepairTool.setName("RepairTool");
                        RepairTool.setDescription("A basic repair tool. Capable of repairing ship and gun components.");
                        RepairTool.setWeight(0);
                        RepairTool.setGrabable(true);
                        currentRoom.addItem(RepairTool);
                    }
                } else {
                    System.out.println("You missed");
                }
            }      
        } 
    }
    
    /** 
     * Allows player to use the hackingtool in order to access the airlock, assuming the player has the hackingtool in
     * their inventory.
     */
    private void useHackingTool()
    {
        if (findInventoryItem("HackingTool") == null) {
            System.out.println("This item is not in your inventory!");
        } else {
            if (currentRoom == roomList.get(4)) {
                roomList.get(3).setLocked(false);
                System.out.println("The door to the airlock has been unlocked.");
            }
        }
    }
    
    
    /** 
     * Allows player to use the keycard in order to access the bridge, assuming the player has the keycard in
     * their inventory.
     */
    private void useKeycard() 
    {
        if (findInventoryItem("keycard") == null) {
            System.out.println("This item is not in your inventory!");
        } else {
            if (currentRoom == roomList.get(5)) {
                roomList.get(7).setLocked(false);
                System.out.println("The door to the bridge has been unlocked.");
            }
        }
    }
    
    /** 
     * Allows player to access the repairtool if the engineer is in a downed state and the player has it in their inventory.
     */
    private void useRepairTool()
    {
        if (findInventoryItem("RepairTool") == null) {
            System.out.println("This item is not in your inventory!");
        } else {
            if (currentRoom.findItems("engines") == null) {
                System.out.println("There is nothing to use the RepairTool on!");
            } else {
                System.out.println("You equip the RepairTool and attempt to fix the engines. After some investigating, you");
                System.out.println("discover the problem. You begin to repair the ships engines.");
                System.out.println(". . .");
                System.out.println("After some time, the engines start up once again. The engines are successfully repaired");
                engineDamageState = true;
            }
        }
    }    
    
    /** 
     * Inspects an item of your choosing.
     * @param command The command to be processed.
     */
    private void goInspectItem(Command command) 
    {
        if(!command.hasSecondWord()) {
            System.out.println("Inspect what item?");
            return;
        }
        
        String findItem = command.getSecondWord();
        
        Item i = findInventoryItem(findItem);
        Item t = currentRoom.findItems(findItem);
        
        if ( i == null && t != null) {
            System.out.println(t.getDescription());
        } else if (i != null && t == null) {
            System.out.println(i.getDescription());
        } else if (i == null && i == null) {
            System.out.println("The item your looking for isn't here");
        } else {
            System.out.println("How are there duplicate items? Something went seriously wrong.");
        }
    }
    
    /** 
     * Checks inventory.
     * @param command The command to be processed.
     */
    private void goInspect(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Inspect what?");
            return;
        }
        else {
            if ( inventory.size() == 0) {
                System.out.println("Your inventory is empty");
                return;
            } else {
                System.out.println(getItemList());
                return;
            }  
        }
    }

    /** 
     * Try to go in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * @param command The command to be processed.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            previousRoom = currentRoom;
            if (nextRoom.getLocked() == false) {
                currentRoom = nextRoom;
                System.out.println(currentRoom.getLongDescription());
            } else {
                System.out.println("The door is locked!");
            }
        }
    }
    
    /**
     * Allows the player to go back to the previous room.
     * @param command The command to be processed.
     */
    private void goBack(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Back what?");
            return;
        } 
        
        if (previousRoom == null) {
            System.out.println("There is no previous room");
        } else {
            currentRoom = previousRoom;
            System.out.println("You've gone back to the previous room.");
            System.out.println(currentRoom.getLongDescription());
        }
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
