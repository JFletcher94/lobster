import greenfoot.*;
import java.awt.Color;
import java.awt.Font;
import java.lang.Math;
import java.io.*;

public class Lobster extends Movement
{

    //shield and images layered on top of lobster
    int shield = 0;
    ToDraw shieldImage;
    ToDraw antigravityImage;
    ToDraw slowImage;

    //speed limit factors
    int xLim = 20;
    int slowed = 1;
    
    //swinging spider counter
    int swingCount = 0;

    //timers
    long clawTimer = 0;
    long slowedTimer = 0;
    long gTimer = 0;
    int fTimer = 0;

    //spider generation and management
    int maxSpiders = 12;
    int numSpiders = 0;
    Spider[] spiders = new Spider[maxSpiders];
    
    //gravity and flight effects
    boolean gravity = true;
    boolean flight = false;
    int grav = 1;
    
    //claw animation
    boolean claw = false;

    //sounds
    static GreenfootSound bounce = new GreenfootSound("bounce.wav");
    static GreenfootSound snip1 = new GreenfootSound("snip.wav");
    static GreenfootSound snip2 = new GreenfootSound("snip2.wav");
    static GreenfootSound snip3 = new GreenfootSound("snip3.wav");
    static GreenfootSound die = new GreenfootSound("die.wav");
    static GreenfootSound hit = new GreenfootSound("hit.wav");

    //score and displayed information
    long score = 0;
    long combo = 10;
    int killed = 0;
    int showKilled = 0;

    public void act() 
    {
        setTimers();
        if (gravity) grav = 1;
        else grav = -1;
        if (toRemove)
        {
            die();
            return;
        }
        setMove();
        spin();
        addSpider();
        addPU();
        draw(Background.score, "Score: " + addCommas("" + score));
        draw(Background.kill, "Spiders killed: " + showKilled);
        draw(Background.shield, "Shield: " + shield);
        if (killed > 400) killed = 400; //limit difficulty 
        if (slowed != 1 && slowedTimer > 400) 
        {
            slowed = 1;
            slowImage.image = new GreenfootImage("none.png");
            slowImage.set();
        }
        if (claw && clawTimer > 15)
        {
            claw = false;
            this.setImage("lobster.png");
        }
        if (!gravity && gTimer == 0)
        {
            getWorld().setBackground("granite-light.jpg");
            gravity = true;
        }
        if (flight && fTimer == 0)
        {
            antigravityImage.image = new GreenfootImage("none.png");
            antigravityImage.set();
            flight = false;
        }
        move();
        checkPU();
        shieldImage.setLocation(getX(), getY());
        antigravityImage.setLocation(this.getX(), this.getY());
        slowImage.setLocation(getX(), getY());
    }      

    public void setTimers()
    {
        /**
         * incrememt variables used to keep track of time-dependent
         * behavior, such as powerup effects and animations
         */
        clawTimer++;
        slowedTimer++;
        fTimer--;
        if (gTimer > 0) gTimer--;
        else gTimer = 0;
        spTimer++;
    }

    public void draw(ToDraw toDraw, Object str)
    {
        /**
         * create and draw image for displaying text
         */
        toDraw.image = new GreenfootImage(400, 24);
        toDraw.image.setColor(Color.BLACK);
        toDraw.image.setFont(new Font("Arial", Font.BOLD, 16));
        toDraw.image.drawString(str.toString(), 160, 12);
        toDraw.set();
    }

    public void setMove() 
    {
        /**
         * determine and set values of dx and dy 
         */
        if (flight)
        {
            if (Greenfoot.isKeyDown("up") && !(getY() <= getImage().getHeight()/2)) dy--;
            else if (Greenfoot.isKeyDown("down") && !(getY() >= getWorld().getHeight() - getImage().getHeight()/2)) dy++;
        }
        else if (gravity && (!(getY() >= getWorld().getHeight() - getImage().getHeight()/2))) dy += grav;
        else if (!gravity && (!(getY() <= getImage().getHeight()/2))) dy += grav;
        if(Greenfoot.isKeyDown("left") && !(getX() <= getImage().getHeight()/2)) 
        {
            dx--;
        }
        if(Greenfoot.isKeyDown("right") && !(getX() >= getWorld().getWidth() - getImage().getHeight()/2)) 
        {
            dx++;
        }
        if (dx > xLim/slowed) dx = xLim/slowed;
        else if (dx < -xLim/slowed) dx = -xLim/slowed;
        if (flight)
        {
            if (dy > xLim/slowed) dy = xLim/slowed;
            else if (dy < -xLim/slowed) dy = -xLim/slowed;
        }
    }

    public boolean check()
    {
        /**
         * check for any event that interrupts motion, such as any collision
         * (calls checkSpider)
         */
        boolean checked = false;
        for (int i = 0; i < maxSpiders; i++) {
            if (spiders[i] != null) {
                if (swingCount < 3 && spiders[i].getClass() != SwingSpider.class && spiders[i].getY() >= getWorld().getHeight()/4
                && spiders[i].getX() > spiders[i].getY()*Math.sin(spiders[i].amp) && spiders[i].getX() < getWorld().getWidth() 
                - spiders[i].getY()*Math.sin(spiders[i].amp) && rand.nextInt(16000 - 20*killed) == 0) 
                {
                    swingCount++;
                    SwingSpider temp = new SwingSpider(spiders[i]);
                    getWorld().addObject(temp, spiders[i].getX(), spiders[i].getY());
                    spiders[i].kill();
                    spiders[i] = temp;
                }
                checked = checked || checkSpider(spiders[i]);
                if (spiders[i].toRemove) spiders[i] = null;
            }
        }
        if (getY() >= getWorld().getHeight() - getImage().getHeight()/2)
        {
            if (dy > 0) 
            {
                dy = -9*dy/10;
                bounce.stop();
                bounce.play();
            }
            combo = 10;
            if (gravity && !flight && dy == 0)
            {
                toRemove = true;
                dx = 0;
            }
            return true;
        }
        if (getY() <= getImage().getHeight()/2) 
        {
            if (!gravity && dy == 0)
            {
                getWorld().setBackground("granite-light.jpg");
                gTimer = 0;
                gravity = true;
            }
            else if (dy < 0)
            {
                dy = -9*dy/10;
                bounce.stop();
                bounce.play();
            }
            return true;
        } 
        if (dx != 0 && ((getX() <= getImage().getHeight()/2 && dx < 0) || (getX() >= getWorld().getWidth() - getImage().getHeight()/2 && dx > 0)))
        {
            dx = -9*dx/10;
            bounce.stop();
            bounce.play();
            return true;
        }
        return checked;
    }

    public boolean checkSpider(Spider spider)
    {
        /**
         * check for collision with spider or its web (calls checkWeb)
         */
        if (!spider.toRemove && this.getDistance(spider) <= getImage().getWidth()/2)
        {
            hit.play();
            combo = 10;
            shield -= spider.toughness;
            if (spider.toughness == 0) 
            {
                showKilled++;
                slowedTimer = 0;
                slowed *= 2;
                spider.toRemove = true;
                spider = null;
                slowImage.image = new GreenfootImage("slow.png");
                slowImage.set();
                for (int j = 0; j < 9; j++) {
                    getWorld().addObject(new Particle(0,rand.nextInt(56)+200,0, true), getX(), getY());
                }
            }
            if (shield < 0 && spider != null)
            {
                if (spider.toughness > 0)
                {
                    toRemove = true;
                    die.play();
                }
            }
            else if (spider != null)
            {
                showKilled++;
                spider.toRemove = true;
                spider = null;
                numSpiders--;
            }
            if (shield < 0) shield = 0;
            if (shield == 0)
            {
                shieldImage.image = new GreenfootImage("none.png");
                shieldImage.set();
            }
        }
        else if (getY() < spider.getY() && checkWeb(spider))
        {
            if (!flight)
            {
                if (gravity)
                {
                    if (dy > -15)
                    {
                        if (dy < 15) dy = -15;
                        else dy = -(2*dy+15)/3;
                    }
                    else dy -= 2;
                }
                else
                {
                    if (dy < 15)
                    {
                        if (dy > -15) dy = 15;
                        else dy = -(2*dy-15)/3;
                    }
                    else dy += 2;
                }
            }
            numSpiders--;
            long newScore = combo * (1+180/(spider.getY() - this.getY()));
            if (score + newScore < score) score = Long.MAX_VALUE;
            else score += newScore;
            killed++; showKilled++;
            combo = spider.score*combo/6 + (killed*killed);
            if (!flight)
            {
                combo += killed*killed;
                if (!gravity) combo = 2*combo;
            }
            if (newScore > 1000000)  {
                snip3.stop();
                snip3.play();
            }
            else if (newScore > 10000) {
                snip2.stop();
                snip2.play();
            }
            else {
                snip1.stop();
                snip1.play();
            }
            claw = true;
            clawTimer = 0;
            this.setImage("lobster2.png");
            if (spider.toughness == 2) dx = -2*dx/3;
            spider.toRemove = true;
            spider = null;
            int logs = (int) Math.log10(newScore);
            for (int j = 0; j < 3+logs; j++) {
                getWorld().addObject(new Particle(rand.nextInt(256),rand.nextInt(256), rand.nextInt(256), logs), getX(), getY());
            }
            return true;
        }
        return false;
    }

    public boolean checkWeb(Spider sp)
    {
        /**
         * check for collision with web. Trivial for regular spiders, but
         * more complicated for swinging spiders
         */
        if (sp.getClass() != SwingSpider.class) return getX() == sp.getX();
        SwingSpider spider = (SwingSpider) sp;
        Checker checker = new Checker(this);
        checker.x = spider.getX();
        checker.y = spider.getY();
        checker.dx = -spider.getX()+spider.xTop;
        checker.dy = -spider.getY();
        if (checker.move())
        {
            swingCount--;
            return true;
        }
        return false;
    }

    public void checkPU()
    {
        /**
         * check for collision with power up
         */
        Actor actor = getOneObjectAtOffset(0, 0, PowerUp.class);
        PowerUp pu = (PowerUp) actor;
        if (pu != null)
        {
            pu.power(this);
            getWorld().removeObject(pu);
        }
    }

    public void addSpider()
    {
        /**
         * called by act(): if conditions are right, spawn a new spider
         */
        if (spTimer + killed/16 > 45)
        {
            spTimer = 0;
            for (int i = 0; i < maxSpiders; i++)
            {
                if (spiders[i] == null)
                {
                    int sx = 50 + rand.nextInt(getWorld().getWidth() - 100);
                    if (rand.nextInt(4) == 0) 
                    {
                        spiders[i] = new SpiderS();
                    }
                    else if (rand.nextInt(1000) <= 900 - killed) 
                    {
                        spiders[i] = new Spider();
                    }
                    else {
                        spiders[i] = new SpiderL();
                    }
                    getWorld().addObject(spiders[i], sx, spiders[i].getImage().getHeight()/2);
                    spiders[i].xTop = sx;
                    numSpiders++;
                    return;
                }
            }
        }
    }

    public void addPU()
    {
        /**
         * called by act(): small chance to spawn a powerup
         */
        if (rand.nextInt(400-killed/2) <= 1)
        {
            int x = 50 + rand.nextInt(getWorld().getWidth() - 100);
            int y = 50 + rand.nextInt(getWorld().getHeight() - 100);
            int z = rand.nextInt(25);
            if (z <= 7) getWorld().addObject(new Shield(), x, y);
            else if (z <= 15) getWorld().addObject(new Slow(), x, y);
            else if (z <= 23) getWorld().addObject(new Flight(), x, y);
            else if (gravity && !Antigravity.exists) getWorld().addObject(new Antigravity(), x, y);
        }
    }

    public void die()
    {
        /**
         * set up objects and information added to screen on game over, then
         * remove lobster from the world
         */
        Background.lobster = null;
        getWorld().removeObject(antigravityImage);
        getWorld().removeObject(shieldImage);
        getWorld().removeObject(slowImage);
        Background.highScores[Background.highScores.length-1] = score;
        for (int i = Background.highScores.length-1; i > 0; i--)
        {
            if (Background.highScores[i] >  Background.highScores[i-1])
            {
                long temp = Background.highScores[i];
                Background.highScores[i] =  Background.highScores[i-1];
                Background.highScores[i-1] = temp;
            }
        }
        getWorld().addObject(new Button(getRotation(), claw), getX(), getY());
        try
        {
            ToDraw scores = new ToDraw();
            scores.image = new GreenfootImage(200, 125);
            scores.image.setColor(Color.BLACK);
            scores.image.setFont(new Font("Arial", Font.BOLD, 16));
            scores.image.drawString("High Scores:", 12, 16);
            PrintWriter writer = new PrintWriter("scores.lobster");
            for (int i = 0; i < Background.highScores.length-1; i++)
            {
                writer.println(Background.highScores[i]);
                scores.image.drawString(addCommas("" + Background.highScores[i]), 12, 18*(i+2));
            }
            writer.close();
            getWorld().addObject(scores, 9*getWorld().getWidth()/10, getWorld().getHeight()/8-20);
            scores.set();
        }
        catch(IOException e) {}
        finally {}
        getWorld().removeObject(this);
    }
    
    public String addCommas(String s)
    {
        /**
         * add commas to a string representing a number, 
         * i.e., 10000000 -> 10,000,000
         */
        char[] chars = s.toCharArray();
        String string = "";
        int comma = 0;
        for (int i = chars.length - 1; i >= 0; i--) {
            string += chars[i];
            if (++comma % 3 == 0) string += ',';
        }
        if (string.charAt(string.length()-1) == ',') string = string.substring(0, string.length()-1);
        chars = string.toCharArray();
        string = "";
        for (int i = chars.length - 1; i >= 0; i--) {
            string += chars[i];
        }
        return string;
    }
}
