import greenfoot.*;

public class Flight extends PowerUp
{

    public void power(Lobster lobster)
    {
        /**
         * called when powerup is activated by lobster;
         * grants lobster the power of flight
         */
        GreenfootSound sound = new GreenfootSound("flight.wav");
        sound.setVolume(80);
        sound.play();
        for (int j = 0; j < 9; j++) {
            int col = rand.nextInt(56)+200;
            getWorld().addObject(new Particle(col, col, 0, true), lobster.getX(), lobster.getY());
        }
        if (!lobster.flight)
        {
            lobster.dy /= 4;
            lobster.dx /= 4;
        }
        lobster.flight = true;
        lobster.fTimer = 400;
        lobster.antigravityImage.image = new GreenfootImage("antigravity.png");
        lobster.antigravityImage.set();
    }

}
