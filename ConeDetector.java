import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class ConeDetector here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ConeDetector extends SmoothMover
{
    public ConeDetector(){
        setImage(new GreenfootImage(100,1));
        getImage().setColor(Color.BLACK);
        getImage().fill();
    }
    public List getIntersecting(){
        return getIntersectingObjects(Boid.class);
    }
}
