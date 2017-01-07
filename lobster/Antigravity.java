import greenfoot.*; 

public class Antigravity extends PowerUp
{
    
    //only one of these powerups may be in world at a time
    public static boolean exists;
    
    public Antigravity()
    {
        super();
        if (!exists) exists = true;
    }
    
    public void power(Lobster lobster)
    {
        /**
         * called when powerup is activated by lobster;
         * reverses direction of gravity (only for lobster)
         */
        GreenfootSound sound = new GreenfootSound("antigravity.wav");
        sound.setVolume(75);
        sound.play();
        for (int j = 0; j < 9; j++) {
            getWorld().addObject(new Particle(rand.nextInt(156)+100,0,rand.nextInt(56)+200, true), lobster.getX(), lobster.getY());
        }
        lobster.gTimer = 400;
        lobster.gravity = false;
        exists = false;
        getWorld().setBackground("fingerprint.jpg");
    }

    public void act() 
    {
        time++;
        if (time > 750) 
        {
            getWorld().removeObject(this);
            exists = false;
        }
    }   
}
