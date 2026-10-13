package game;

import java.awt.*;
import java.util.Random;
import java.util.ArrayList;


public class Asteroid extends Polygon implements Movable{
    private double speed; 
    private boolean status;
    private boolean direction;
    private boolean debrisSpawned;
    private static final Random rand = new Random();

    // Constructs Asteroid with random rotation that moves downward
    public Asteroid(Point position, boolean direction) {
        // Create an irregular 8-point shape for the asteroid.
        super(new Point[] {
            new Point(rand.nextInt(-25,-20), 0), 
            new Point(rand.nextInt(-25,-20), rand.nextInt(-25,-20)), 
            new Point(0, rand.nextInt(-25,-20)),
            new Point(rand.nextInt(20,25), rand.nextInt(-25,-10)), 
            new Point(rand.nextInt(10,20), 0), 
            new Point(rand.nextInt(10,20), rand.nextInt(10,20)),
            new Point(0, rand.nextInt(10,20)), 
            new Point(rand.nextInt(-20,-10), rand.nextInt(10,20))
        }, position, rand.nextInt(360));  // Random initial rotation.
        
        // Set a random downward speed between 1 and 3 pixels per update
        this.speed = 1 + rand.nextDouble() * 2;
        this.status = true;
        this.debrisSpawned = false;
        this.direction = direction;
    }

    // Moves asteroid downward with speed and rotation
    public void move() {
        position.y += speed;
        rotateAsteroid(direction);
        if(position.y < 0 || position.y > Game.width || 
									position.x < 0 || position.x > Game.height) {
        	status = false;
        }
    }
    
    public boolean getStatus() {
    	if(position.y < 0 || position.y > Game.width || 
				position.x < 0 || position.x > Game.height) {
    		status = false;
    	}
    	return status;
    }
    
    public void rotateAsteroid(boolean direction) {
        // Rotate 1 degree left or right based on a coin flip.
        if(direction) {
        	rotate(3);
        } else {
        	rotate(-3);
        }
    }
    

    // Draws the asteroid
    public void draw(Graphics g) {
        g.setColor(Color.GRAY);
        Point[] points = getPoints();
        int[] xPoints = new int[points.length];
        int[] yPoints = new int[points.length];
        for (int i = 0; i < points.length; i++) {
            xPoints[i] = (int) points[i].x;
            yPoints[i] = (int) points[i].y;
        }
        g.fillPolygon(xPoints, yPoints, points.length);
    }
    
    // Check if asteroid collided with something
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
    
    /**
     * Spawns debris fragments if they have not already been spawned.
     *
     * @return an ArrayList of Debris fragments.
     */
    public ArrayList<Debris> spawnDebris() {
        ArrayList<Debris> fragments = new ArrayList<>();
        if (!debrisSpawned) {
            debrisSpawned = true;
            // Generate 3 debris fragments.
            for (int i = 0; i < 3; i++) {
                double speedX = (rand.nextDouble() - 0.5) * 4;
                double speedY = (rand.nextDouble() - 0.5) * 4;
                fragments.add(new Debris(new Point(position.x, position.y), speedX, speedY));
            }
        }
        return fragments;
    }
    
    /**
     * Checks whether debris have already been spawned.
     *
     * @return true if debris have been spawned; false otherwise.
     */
    public boolean hasSpawnedDebris() {
        return debrisSpawned;
    }
    
    /**
     * The Debris class represents smaller asteroid fragments that disappear after 3 seconds.
     * It extends Polygon and implements Movable.
     */
    public class Debris extends Polygon implements Movable {
        private double speedX;
        private double speedY;
        private boolean status;
        private long creationTime; // Timestamp when this debris was created.

        /**
         * Constructs a Debris fragment.
         *
         * @param position the starting position.
         * @param speedX the horizontal speed.
         * @param speedY the vertical speed.
         */
        public Debris(Point position, double speedX, double speedY) {
            super(new Point[] {
                new Point(0, -3), new Point(3, 3), new Point(-3, 3)
            }, position, 0);
            this.speedX = speedX;
            this.speedY = speedY;
            this.status = true;
            this.creationTime = System.currentTimeMillis();
        }
        
        /**
         * Moves the debris fragment according to its speeds.
         * After 3 seconds have passed, marks the debris as inactive.
         */
        @Override
        public void move() {
            position.x += speedX;
            position.y += speedY;
            if (System.currentTimeMillis() - creationTime >= 3000) {
                status = false;
            }
        }
        
        /**
         * Returns the status of the debris.
         *
         * @return true if the debris is still active; false otherwise.
         */
        @Override
        public boolean getStatus() {
            if (System.currentTimeMillis() - creationTime >= 3000) {
                status = false;
            }
            return status;
        }
        
        /**
         * Draws the debris fragment as a filled light gray polygon.
         *
         * @param g the Graphics context used for drawing.
         */
        public void draw(Graphics g) {
            g.setColor(Color.LIGHT_GRAY);
            Point[] points = getPoints();
            int[] xPoints = new int[points.length];
            int[] yPoints = new int[points.length];
            for (int i = 0; i < points.length; i++) {
                xPoints[i] = (int) points[i].x;
                yPoints[i] = (int) points[i].y;
            }
            g.fillPolygon(xPoints, yPoints, points.length);
        }
    }
}
