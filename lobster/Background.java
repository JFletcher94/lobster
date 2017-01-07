import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Scanner;  
import java.io.*;

public class Background extends World
{
    public static Lobster lobster;
    public static ToDraw score;
    public static ToDraw kill;
    public static ToDraw shield;
    public static long[] highScores = new long[6];

    public Background()
    {    
        super(1600, 900, 1, false);

        setPaintOrder(Spider.class, ToDraw.class, Movement.class, Button.class, 
            PowerUp.class, Particle.class);
        
        Antigravity.exists = false;
        lobster = new Lobster();
        addObject(lobster, getWidth()/2, 7*getHeight()/8);
        lobster.setRotation(-90);
        lobster.spinning = 15;
        lobster.dy = -37;
        lobster.shieldImage = new ToDraw();
        lobster.antigravityImage = new ToDraw();
        lobster.slowImage = new ToDraw();
        addObject(lobster.shieldImage, getWidth()/2, 7*getHeight()/8);
        addObject(lobster.antigravityImage, getWidth()/2, 7*getHeight()/8);
        addObject(lobster.slowImage, getWidth()/2, 7*getHeight()/8);
        lobster.shieldImage.image = new GreenfootImage("none.png");
        lobster.antigravityImage.image = new GreenfootImage("none.png");
        lobster.slowImage.image = new GreenfootImage("none.png");
        lobster.shieldImage.set();
        lobster.antigravityImage.set();
        lobster.slowImage.set();
        score = new ToDraw();
        addObject(score, 75, 50);
        score.setImage(new GreenfootImage("none.png"));
        kill = new ToDraw();
        addObject(kill, 75, 75);
        kill.setImage(new GreenfootImage("none.png"));
        shield = new ToDraw();
        addObject(shield, 75, 100);
        shield.setImage(new GreenfootImage("none.png"));
        try {
            getHS();
        }
        catch(IOException e) {}
        finally {}
    } 

    public void getHS() throws IOException
    {
        Scanner sc = new Scanner(new File("scores.lobster"));
        int i = 0;
        while(sc.hasNext()){
            highScores[i++] = sc.nextLong();
        }
        sc.close();
    }

}
