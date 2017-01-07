import greenfoot.*;  
import java.util.Random;

public class PowerUp extends Actor
{
    
    //random number generator
    static Random rand = new Random();
    
    //variable for timed expiration
    int time;
    
    public PowerUp()
    {
        super();
        time = 0;
    }
    
    public void act() 
    {
        time++;
        if (time > 250) getWorld().removeObject(this);
    }    
    
    public void power(Lobster lobster)
    {
        /**
         * called when powerup is activated by lobster;
         * subclass-dependent
         */
        
    }
}
