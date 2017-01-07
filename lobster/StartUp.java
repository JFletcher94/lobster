import greenfoot.*;  

public class StartUp extends World
{
    
    public StartUp()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1600, 900, 1); 
    }
    
    public void act()
    {
        Greenfoot.setWorld(new Splash());
    }
}
