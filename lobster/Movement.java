import greenfoot.*; 
import java.util.Random;
import java.lang.Math;

public class Movement extends Actor
{

    //incremental movement values
    int dx = 0;
    int dy = 0;
    
    //rotation control
    int spinning = 0;
    long spTimer = 0;
    
    //random number generator
    static Random rand = new Random();
    
    //death and removal control
    boolean toRemove = false;
    int deathTime = 70;   

    public void setTimers()
    {
        /**
         * incrememt variables used to keep track of time-dependent
         * behavior, such as powerup effects and animations
         */
        spTimer++;
    } 

    public double getSpeed()
    {
        /**
         * return exact total speed (not velocity) as double
         */
        return Math.sqrt(dy*dy + dx*dx);
    }

    public double getAngle(int x, int y)
    {
        /**
         * return angle of rotion; east is 0
         */
        return Math.toDegrees(Math.atan2(y, x));
    }

    public double[] getDxDy(double speed, double angle)
    {
        /**
         * get dx and dy from speed and angle; depricated
         */
        double[] DxDy = new double[2];
        DxDy[0] = speed * Math.cos(angle);
        DxDy[1] = speed * Math.sin(angle); 
        return DxDy;
    }

    public double getDistance(Movement other) {
        /**
         * get distance from another Movement object
         */
        int xx = this.getX() - other.getX();
        int yy = this.getY() - other.getY();
        return Math.sqrt(xx*xx + yy*yy);
    }

    public boolean move()
    {
        /**
         * move dx cells horizontally and dy cells vertically;
         * move on diagonal path of the dx-dy rectangle;
         * use continuous (a priori) collision detection
         */
        if (toRemove)
        {
            spTimer = 0;
            return false;
        }
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
        while (adx != 0 || ady != 0)
        {
            if (adx == 0 || (ady != 0 && ((double) ady)/((double) adx) >= slope))
            {
                setLocation(getX(), getY() + yinc);
                ady--;
            }
            else if (ady == 0 || (adx != 0 && ((double) ady)/((double) adx) <= slope))
            {
                setLocation(getX() + xinc, getY());
                adx--;
            }
            //else System.out.println("Move loop problem: "+adx+" "+ady); //debugging
            if (check()) return true;
        }
        return false;
    }

    public void spin() {
        /**
         * turn image to face proper direction
         */
        if (!(dx == 0 && dy == 0)) turnTowards(getX() + dx, getY() + dy);
    }

    public boolean check()
    {
        /**
         * check for any event that interrupts movement; entirely
         * subclass-dependent
         */
        return false;
    }

    public void die()
    {
        /**
         * take actions required for removal from game; entirely
         * subclass-dependent
         */

    }

}
