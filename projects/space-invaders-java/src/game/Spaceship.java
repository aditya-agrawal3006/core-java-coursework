package game;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Spaceship extends Polygon implements Movable{
	double speed;
    private boolean status;
    private final double normalSpeed = 5.0;
    private final double boostSpeed = 7.5;
    private boolean boostActive = false;
    private long boostActivationTime = 0;
    private long boostCooldownEndTime = 0; // initially, no cooldown
    private Thruster thruster;

    public Spaceship(Point position) {
        super(new Point[]{
                new Point(-10, -10), new Point(0, -40), new Point(10, -10)
        }, position, 0); // making a triangle
        this.speed = normalSpeed; // Set default speed
        this.status = true;
        this.thruster = new Thruster();
    }
    
    public boolean getStatus() {
    	return status;
    }
    
    public void move() {
    	// If boost is active, check if 10 seconds have passed.
        if (boostActive) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - boostActivationTime >= 10000) {
                boostActive = false;
                boostCooldownEndTime = currentTime + 10000;
                speed = normalSpeed;
            }
        }
    }
    
    public void moveLeft() {
        position.x -= speed; // Movement handling
    }
    
    

    public void moveRight() {
        position.x += speed; 
    }
    
    // Check if spaceship collided with something
    public boolean collidesWith(Polygon other) {
        Point[] otherPoints = other.getPoints();
        for (Point p : otherPoints) {
            if (this.contains(p)) {
            	this.status = false;
                return true;
            }
        }
        this.status = true;
        return false;
    }
 
    
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        Point[] points = getPoints();
        int[] xPoints = new int[points.length];
        int[] yPoints = new int[points.length];

        for (int i = 0; i < points.length; i++) {
            xPoints[i] = (int) points[i].x;
            yPoints[i] = (int) points[i].y;
        }

        g.fillPolygon(xPoints, yPoints, points.length);
    }
    
    //inner class
    private class Thruster {
        private double accelerate = 0.5;
        
        // Activates boost if not already active and if cooldown is over.
        public void accelerate() {
            long currentTime = System.currentTimeMillis();
            if (!boostActive && currentTime >= boostCooldownEndTime) {
                boostActive = true;
                boostActivationTime = currentTime;
                speed = boostSpeed;
            }
        }
    }
    
    public KeyListener getKeyControls() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_LEFT) {
                    moveLeft();
                } else if (key == KeyEvent.VK_RIGHT) {
                    moveRight();
                } else if (key == KeyEvent.VK_UP) {
                    thruster.accelerate();
                }   
            }
            
        };
    }


}
