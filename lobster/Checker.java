//import greenfoot.*;
import java.lang.Math;

/**
 * Write a description of class checker here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Checker
{
    /**
     * Act - do whatever the checker wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    Lobster lobster;
    int x, y, dx, dy;
    
    public Checker(Lobster l)
    {
        super();
        lobster = l;
    }
    
    public void act() 
    {
        
    } 
    
    public double getDistance() {
        double xDist = x - lobster.getX();
        double yDist = y - lobster.getY();
        return Math.sqrt(xDist*xDist + yDist*yDist);
    }
    
    public boolean check()
    {
        //if (getDistance() <= 5.0) return true;
        if (x == lobster.getX() && y == lobster.getY()) return true;
        return false;
    }
    
    public boolean move()
    {
        int ady = Math.abs(dy), adx = Math.abs(dx), xinc, yinc;
        double slope;
        if (ady == 0) yinc = 0;
        else yinc = dy/ady;
        if (adx == 0) 
        {
            xinc = 0;
            slope = (double) Integer.MAX_VALUE;
        }
        else 
        {
            xinc = dx/adx;
            slope = ((double) ady)/((double) adx);
        }
        while ((adx != 0 || ady != 0))
        {
            if (adx == 0 || (ady != 0 && ((double) ady)/((double) adx) >= slope))
            {
                y += yinc;
                ady--;
            }
            else if (ady == 0 || (adx != 0 && ((double) ady)/((double) adx) <= slope))
            {
                x += xinc;
                adx--;
            }
            //getWorld().addObject(new Particle(), x, y);
            if (check()) return true;
        }
        return false;
    }
}
