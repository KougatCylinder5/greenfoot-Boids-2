import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class Boid here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Boid extends SmoothMover
{
    private double speed = 5; 
    private int angle = 0;
    private ConeDetector detector = new ConeDetector();
    public Boid(){
        angle = Greenfoot.getRandomNumber(180);
        setRotation((int)angle);
    }
    public void act(){
        List boids = getBoidsInCone();
        accum(getHeading(boids),getSeperation(boids));
        borderless();
        
    }
    public void accum(int head, double sep){
        setRotation(head + (int)sep + this.angle);
        this.angle += head + sep; 
        move(this.speed);
    }
    public double getSeperation(List boids){
        double aHeading = 0;
        for(int i = 0; i < boids.size(); i++){
            Boid boid = (Boid)boids.get(i);
            turnTowards(boid.getX(),boid.getY());
            aHeading = -(this.angle-getRotation()) * 1;
        }
        try{
            aHeading /= boids.size();
        }catch(ArithmeticException e){
            aHeading = 0;   
        }
        setRotation(this.angle);
        return aHeading > 0 ? (aHeading > 5 ? 5 : aHeading) : (aHeading < -5 ? -5 : aHeading);
    }
    public int getHeading(List boids){
        int aHeading = 0;
        for(int i = 0; i < boids.size(); i++){
            aHeading += ((Boid)boids.get(i)).getAngle();
        }
        try{
            aHeading /= boids.size();
        }catch(ArithmeticException e){
            aHeading = 0;   
        }
        
        aHeading -= this.angle;
        return aHeading > 0 ? (aHeading > 15 ? 15 : aHeading) : (aHeading < -15 ? -15 : aHeading);
    }
    public List getBoidsInCone(){
        Set boids = new HashSet<>();
        
        getWorld().addObject(detector,getX(),getY());
        for(int i = -120; i < 120; i+= 1){
            detector.setLocation(getX() + Math.cos(Math.toRadians(i + getRotation())) * 50,getY() + Math.sin(Math.toRadians(i + getRotation())) * 50);
            detector.borderless();
            detector.turnTowards(getX(),getY());
            boids.addAll(detector.getIntersecting());
            boids.remove(this);
        }
        for(int i = 0; i < boids.size(); i++){
            if(Collections.frequency(boids,this) > 1){
                boids.remove(i);
            }
        }
        getWorld().removeObject(detector);
        List outBoids = new ArrayList<>();
        outBoids.addAll(boids);
        return outBoids;
    }
    public double getDist(int x, int y){
        return Math.hypot(x - getX(), y- getY());
    }
    public void accumMove(){
        move(this.speed);
    }
    public double getSpeed(){
        return speed;
    }
    public double getAngle(){
        return angle;
    }
    
}
