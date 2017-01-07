import greenfoot.*;  

public class Shield extends PowerUp
{

    public void power(Lobster lobster)
    {
        /**
         * called when powerup is activated by lobster;
         * add a shield to lobster
         */
        GreenfootSound sound = new GreenfootSound("shield.wav");
        sound.setVolume(75);
        sound.play();
        for (int j = 0; j < 9; j++) {
            getWorld().addObject(new Particle(0,0,rand.nextInt(56)+200, true), lobster.getX(), lobster.getY());
        }
        lobster.shield++;
        if (lobster.shield == 1)
        {
            lobster.shieldImage.image = new GreenfootImage("shield.png");
            lobster.shieldImage.set();
        }
    }

}
