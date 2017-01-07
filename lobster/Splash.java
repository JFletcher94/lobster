import greenfoot.*; 

public class Splash extends World
{

    /**
     * Constructor for objects of class Splash.
     * 
     */
    public Splash()
    {    
        super(1600, 900, 1); 
    }
    
    public void act()
    {
        if (Greenfoot.mouseClicked(this) || Greenfoot.isKeyDown("space"))
        {
            Greenfoot.setWorld(new Background());
        }
    }
}
