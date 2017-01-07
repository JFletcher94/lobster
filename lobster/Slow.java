import greenfoot.*; 
import java.awt.Color;

public class Slow extends PowerUp
{

    public void power(Lobster lobster)
    {
        /**
         * called when powerup is activated by lobster;
         * slows down all currently spawned spiders
         */
        GreenfootSound sound = new GreenfootSound("slow.wav");
        sound.setVolume(75);
        sound.play();
        for (int i = 0; i < lobster.maxSpiders; i++)
        {
            if (lobster.spiders[i] != null)
            {
                for (int j = 0; j < 5; j++) {
                    getWorld().addObject(new Particle(rand.nextInt(56)+200,0,0, true), lobster.spiders[i].getX(), lobster.spiders[i].getY());
                }
                if (lobster.spiders[i].getClass() == SwingSpider.class)
                {
                    SwingSpider spider = (SwingSpider) lobster.spiders[i];
                    if (spider.amp >= 2.0/24.0)
                    {
                        spider.toSlow = true;
                        spider.dt /= 2;
                    }
                }
                else if (lobster.spiders[i].dy >= 4 || lobster.spiders[i].dy <= -2)
                {
                    lobster.spiders[i].dy /= 2;
                }
                lobster.spiders[i].setImage(lobster.spiders[i].spider2);
            }
        }
    }
}
