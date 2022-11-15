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
    private double speed = 6; 
    private int angle = 0;
    private ConeDetector detector = new ConeDetector();
    public Boid(){
        angle = Greenfoot.getRandomNumber(180);
        setRotation((int)angle);
    }
    public void act(){
        List boids = getBoidsInCone();
        accum(getHeading(boids),getSeperation(boids),getCohesion(boids));
        borderless();
        
    }
    public void accum(int head, double sep, int coh){
        setRotation(head + (int)sep + coh + this.angle);
        this.angle += head + sep + coh; 
        move(this.speed);
    }
    public int getCohesion(List boids){
        int aX = 0;
        int aY = 0;
        for(int i = 0; i < boids.size(); i++){
            Boid boid = (Boid)boids.get(i);
            if(getDist(boid.getX(),boid.getY()) > 15){
                aX += boid.getX();
                aY += boid.getY();
            }
            
        }
        try{
            aX /= boids.size();
            aY /= boids.size();
            turnTowards(aX,aY);
            int angle = getRotation() - this.angle;
            setRotation(this.angle);
            return angle > 0 ? (angle > 5 ? 5 : angle) : (angle < -5 ? -5 : angle);
        }catch(ArithmeticException e){
            aX = 0;
            aY = 0;
            return 0;
        }
        
    }
    public double getSeperation(List boids){
        List ratios = new ArrayList<Double>();
        for(int i = 0; i < boids.size(); i++){
            Boid boid = (Boid)boids.get(i);
            if(getDist(boid.getX(),boid.getY()) < 50){
                turnTowards(boid.getX(),boid.getY());
                ratios.add(180-(50-getDist(boid.getX(),boid.getY()))/50 * getRotation());
            }
        }
        double ratio = 0;
        for(int i = 0; i < ratios.size(); i++){
            ratio += (double)ratios.get(i);
        }

        ratio /= ratios.size();
        ratio -= this.angle;
        setRotation(this.angle);
        if(Double.isNaN(ratio)){
            return 0;
        }else{
            return ratio > 0 ? (ratio > 5 ? 5 : ratio) : (ratio < -5 ? -5 : ratio);
        }
    }
    public int getHeading(List boids){
        int aHeading = 0;
        for(int i = 0; i < boids.size(); i++){
            aHeading += ((Boid)boids.get(i)).getAngle();
        }
        try{
            aHeading /= boids.size();
        }catch(ArithmeticException e){
            aHeading = this.angle;   
        }
        
        aHeading -= this.angle;
        return aHeading > 0 ? (aHeading > 5 ? 5 : aHeading) : (aHeading < -5 ? -5 : aHeading);
    }
    public List getBoidsInCone(){
        Set boids = new HashSet<>();
        getWorld().addObject(detector,getX(),getY());
        for(int i = -90; i < 90; i+= 1){
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
