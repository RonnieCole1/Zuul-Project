
/**
 * This class represents a character whos health is separated into different body parts. When the health is dropped on the characters
 * arms, the charater is disarmed. When the health of the characters torso or head is 0, the character is downed.
 *
 * @author Ronnie Cole
 * @version 5/10/2020
 */
public class Character
{
    private int headHealth;
    private int armsHealth;
    private int legsHealth;
    private int torsoHealth;
    private String name;
    
    /**
     *
     */
    public Character()
    {
        name = "";
        headHealth = 0;
        armsHealth = 0;
        legsHealth = 0;
        torsoHealth = 0;
    }
    
    /**
     *
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     *
     */
    public String getName()
    {
        return name;
    }
    
    /**
     *
     */
    public void setArmsHealth(int charHealth)
    {
        armsHealth = charHealth;
    }
    
    /**
     *
     */
    public int getArmsHealth()
    {
        return armsHealth;
    }
    
    /**
     *
     */
    public void setLegsHealth(int charHealh)
    {
        legsHealth = charHealh;
    }
    
    /**
     *
     */
    public int getLegsHealth()
    {
        return legsHealth;
    }
    
    /**
     *
     */
    public void setTorsoHealth(int charHealh)
    {
        torsoHealth = charHealh;
    }
    
    /**
     *
     */
    public int getTorsoHealth()
    {
        return torsoHealth;
    }
    
    /**
     *
     */
    public void setHeadHealth(int charHealh)
    {
        headHealth = charHealh;
    }
    
    /**
     *
     */
    public int getHeadHealth()
    {
        return headHealth;
    }
    
    /**
     *
     */
    public boolean downedState()
    {
        boolean downedState = false;
        if (getHeadHealth() == 0 || getTorsoHealth() == 0) {
            downedState = true;
        } else {
            downedState = false;
        }
        return downedState;
    }
    
    /**
     *
     */
    public boolean disarmedState()
    {
        boolean disarmedState = false;
        if (getArmsHealth() == 0) {
            disarmedState = true;
        } else {
            disarmedState = false;
        }
        return disarmedState;
    }
}
