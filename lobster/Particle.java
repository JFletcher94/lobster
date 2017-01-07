import greenfoot.*;
import java.awt.Color;
import java.util.Random;

public class Particle extends Actor
{
    
    //random number generator
    static Random rand = new Random();
    
    //movement variables
    int dx;
    int dy;
    int ddy;
    int spin;
    
    public Particle() {
        super();
        GreenfootImage image = new GreenfootImage(1, 1);
        image.setColor(Color.BLACK);
        image.fill();
        this.setImage(image);
        dx = dy = ddy = 0;
    }
    
    public Particle(int r, int g, int b, int size) {
        super();
        int len = rand.nextInt(5) + 5 + 4*size;
        GreenfootImage image = new GreenfootImage(len, len);
        image.setColor(new Color(r, g, b));
        image.fillOval(0, 0, len, len);
        this.setImage(image);
        dx = rand.nextInt(21) - 10;
        dy = -rand.nextInt(20);
        ddy = 1;
        spin = 0;
    }
    
    public Particle(int r, int g, int b, boolean pu) {
        super();
        int len = rand.nextInt(10) + 10;
        GreenfootImage image = new GreenfootImage(len, len);
        image.setColor(new Color(r, g, b));
        image.fill();
        this.setImage(image);
        dx = rand.nextInt(15) + 3;
        dy = rand.nextInt(15) + 3;
        dx = rand.nextBoolean() ? dx : -dx;
        dy = rand.nextBoolean() ? dy : -dy;
        ddy = 0;
        spin = rand.nextInt(15) - 7;
    }
    
    public void act() 
    {
        setLocation(getX()+dx, getY()+dy);
        turn(spin);
        dy += ddy;
        if (getY() >= getWorld().getHeight() || getY() <= 0 || getX() >= getWorld().getWidth() || getX() <= 0) {
            getWorld().removeObject(this);
            return;
        }
    }    
}
