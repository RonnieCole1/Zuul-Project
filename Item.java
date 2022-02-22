
/**
 * Class represents a single item with a name, description, weight, and whether it can be picked up.
 *
 * @author Ronnie Cole
 * @version 4/14/2020
 */
public class Item
{
    private String name;
    private String description;
    private int weight;
    private boolean grabable;
    private String takeString;
    private boolean usable;
    
    /**
     * Constructor for objects of class Item
     */
    public Item()
    {
        name = "";
        description= "";
        weight = 0;
        grabable = false;
    }

    /**
     *  Gives item a name
     *  @param itemName The name we want to give this item.
     */
    public void setName(String itemName)
    {
        name = itemName;
    }

    /**
     *  Retrieves the name of the item
     *  @return A string which represents the name of the item.
     */    
    public String getName()
    {
        return name;
    }

    /**
     *  Sets the description.
     *  @param itemDescription The description of the item.
     */
    public void setDescription(String itemDescription)
    {
        description = itemDescription;
    }
    
    /**
     *  Returns the description.
     *  @return getDescription A description of our current room.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     *  Sets the items weight to some integer value.
     *  @param itemWeight Sets the weight of our item.
     */
    public void setWeight(int itemWeight)
    {
        weight = itemWeight;
    }
    
    /**
     *  Gives us our item weight
     *  @return Returns the weight of the item.
     */
    public int getWeight()
    {
        return weight;
    }
    
    /**
     *  Sets the usability state of the item.
     *  @param itemusability Sets the items usability to true or false.
     */
    public void setUsability(boolean itemUsability)
    {
        usable = itemUsability;
    }
    
    /**
     *  Checks if the item is usable
     *  @return True if the item is usable, false otherwise.
     */
    public boolean getUsability()
    {
        return usable;
    }
    
    /**
     * Sets the item's grabable state.
     * @param itemGrabable sets the items ability to be picked up
     */
    public void setGrabable(boolean itemGrabable)
    {
        grabable = itemGrabable;
    }
    
    /**
     * Checks if the item is grabable
     * @return true if the item is grabable and false otherwise.
     */
    public boolean isGrabable()
    {
        return grabable;
    }

    /**
     * @return some text when an item is picked up.
     */
    public String takeString()
    {
        return takeString;
    }

    /**
     * @param takeString Sets an items takeString string.
     */
    public void setTakeString(String takeString)
    {
        this.takeString = takeString;
    }
}
