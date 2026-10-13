package game;

import java.awt.*;

import game.Point;

public class Projectile extends Polygon implements Movable {
	private double speed;
	private boolean status;
	
	public Projectile(Point startPosition) {
		super(new Point[] {new Point(-2, -2), new Point(2, -2), new Point(2, 2), 
	            new Point(-2, 2)}, startPosition, 0);
		this.speed = -10;
		this.status = true;
	}
	
	public boolean getStatus() {
		if(position.y < 0 || position.y > Game.width || 
				position.x < 0 || position.x > Game.height) {
			status = false;
		}
		
		return status;
	}
	
	public void move() {
		position.y += speed;
		
		if(position.y < 0 || position.y > Game.width || 
									position.x < 0 || position.x > Game.height) {
			status = false;
		}
	}
	
	public void deactivate() {
		status = false;
	}
	
	/**
     * Draws the debris fragment using a light gray color.
     *
     * @param g The graphics context used for drawing.
     */
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
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
