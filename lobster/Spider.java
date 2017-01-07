import greenfoot.*; 
import java.awt.Color;
import java.util.Random;

public class Spider extends Movement
{

    //visuals
    ToDraw forWeb;
    Color color;
    Color colorS;
    String spider2 = "spider2.png";
    boolean made = false;
    
    //data for interaction with lobster
    int toughness = 1;
    int score = 8;
    double amp;
    
    //x value of spawn point, for fulcrum of swining spiders
    int xTop;

    public Spider()
    {
        super();
        color = Color.WHITE;
        colorS = new Color(200, 35, 0);
        setRotation(90);
        dx = 0;
        dy = 2*(2 + rand.nextInt(2) + (1+rand.nextInt(2))*(Background.lobster.killed/64));
        forWeb = new ToDraw();
        deathTime = 25;
    }

    public void act() 
    {
        setTimers();
        if (toRemove)
        {
            if (spinning == 0)
            {
                getWorld().removeObject(forWeb);
                forWeb = null;
                spinning = 15;
                spTimer = 0;
            }
            die();
            return;
        }
        move();
        amp = ((double) dy)/24;
        amp = (amp > 0) ? amp : -amp;
        if (!toRemove) drawWeb();
    }

    public void drawWeb()
    {
        /**
         * update and draw web image
         */
        if (!made) {
            getWorld().addObject(forWeb, 0, 0);
            made = true;
        }
        forWeb.image = new GreenfootImage(2, getWorld().getHeight());
        forWeb.image.setColor(color);
        forWeb.set();
        forWeb.image.drawLine(0, 0, 0, getY());
        forWeb.image.drawLine(1, 0, 1, getY());
        forWeb.setLocation(getX(), getWorld().getHeight()/2);
    }

    public boolean check()
    {
        /**
         * check for any event that interrupts movement, in this case,
         * collision with floor/ceiling
         */
        if (getY() >= getWorld().getHeight() - getImage().getHeight()/2)
        {
            dy = -dy/2;
            if (dy == 0) dy = 1;
            return true;
        }
        if (getY() <= getImage().getHeight()/2) {
            dy = -2*dy;
            return true;
        } 
        return false;
    }

    public void die()
    {
        /**
         * play death animation, then run kill()
         */
        turn(spinning);
        if (spTimer > deathTime) 
        {
            kill();
        }
    }

    public void kill()
    {
        /**
         * remove object from world
         */
        getWorld().removeObject(this);
        return;
    }

}
