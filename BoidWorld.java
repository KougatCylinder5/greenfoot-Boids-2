import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BoidWorld extends World
{

    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public BoidWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(700, 700, 1, false);
        for(int i = 0; i < 50; i++){
            addObject(new Boid(),Greenfoot.getRandomNumber(250) + 250,Greenfoot.getRandomNumber(250) + 250);
        }
    }
}
