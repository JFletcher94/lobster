import greenfoot.*;  

public class Button extends Actor
{
    
    //animation variables
    int count;
    boolean claw;
    
    public Button(int rotation, boolean cl)
    {
        super();
        setRotation(rotation);
        claw = cl;
        count = 0;
    }
    
    public Button() 
    {
        super();
    }
    
    public void act()
    {
        turn(5);
        if (count > 15)
        {
            if (claw) 
            {
                setImage("lobster.png");
                claw = false;
            }
            else
            {
                setImage("lobster2.png");
                claw = true;
            }
            count = 0;
        }
        count++;
        if (Greenfoot.mouseClicked(this) || Greenfoot.isKeyDown("space"))
        {
            Greenfoot.setWorld(new Background());
        }
    }    
}
