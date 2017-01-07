import greenfoot.*; 
import java.lang.Math;

public class SwingSpider extends Spider
{
    
    //values for starting and changing motion
    int speed;
    long wait;
    boolean started = false;
    boolean toSlow = false;
    
    //pendulum motion calculation values
    final double g = 10.0;
    final double l;
    double t = 0.0;
    double dt = 0.25;
    double theta = 0.0;
    
    public SwingSpider(Spider old)
    {
        super();
        wait = 0;
        forWeb = old.forWeb;
        xTop = old.getX();
        toughness = old.toughness;
        amp = (old.amp > 0.5) ? 0.5 : old.amp;
        setImage(old.getImage());
        spider2 = old.spider2;
        color = old.color;
        score = old.score + 4;
        l = (double) old.getY();
        if (rand.nextBoolean()) dt *= -1;
    }
    
    public void setTheta() 
    {
        /**
         * calculate max swing angle
         */
        theta = amp*Math.cos(t*Math.sqrt(g/l)-Math.PI/2);
    }
    
    public void act() 
    {
        setTimers();
        if (toRemove)
        {
            if (spinning == 0)
            {
                getWorld().removeObject(forWeb);
                spinning = 15;
                spTimer = 0;
            }
            die();
            return;
        }
        if (!started && wait < 12) wait++;
        else if (!started) started = true;
        else 
        {
            pendulate();
            move();
        }
        if (!toRemove) drawWeb();
    }
    
    public void pendulate()
    {
        /**
         * move like a pendulum; uses sin(x) ~ x approximation
         */
		t += dt;
        setTheta();
		dx = -getX() + (int) (xTop+l*Math.sin(theta));
        dy = -getY() + (int) (l*Math.cos(theta));
        setRotation(90 - (int) Math.toDegrees(theta));
    }
    
    public boolean check()
    {
        /**
         * check for any event that interrupts movement, in this
         * case lobster collision or the slow powerup 
         */
        if (Background.lobster != null && Background.lobster.checkSpider(this)) return true;
        if (toSlow && getX() == xTop)
        {
            amp /= 2;
            toSlow = false;
            dt *= 2;
            return true;
        }
        return false;
    }
    
    public void drawWeb()
    {
        /**
         * update and draw web image
         */
        if (!made) {
            getWorld().addObject(forWeb, 0, 0);
            made =true;
        }
        forWeb.image = new GreenfootImage(Math.abs(getX() - xTop) + 2*(1-((getX() - xTop)%2)), getWorld().getHeight());
        forWeb.image.setColor(color);
        forWeb.set();
        if (getX() >= xTop) 
        {
            forWeb.image.drawLine(0, 0, getX()-xTop, getY());//-getImage().getHeight()/4);
            forWeb.image.drawLine(1, 0, getX()-xTop+1, getY());//-getImage().getHeight()/4);
        }
        else
        {
            forWeb.image.drawLine(xTop-getX(), 0, 0, getY());//-getImage().getHeight()/4);
            forWeb.image.drawLine(xTop-getX()+1, 0, 1, getY());//-getImage().getHeight()/4);
        }
        forWeb.setLocation((xTop+getX())/2, getWorld().getHeight()/2);
    }
    
}
