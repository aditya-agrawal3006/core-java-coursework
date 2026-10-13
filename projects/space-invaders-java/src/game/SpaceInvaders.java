package game;

/*
CLASS: YourGameNameoids
DESCRIPTION: Extending Game, YourGameName is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.

*/
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

class SpaceInvaders extends Game {
	static int counter = 0;
	static int points = 0;
	private Spaceship spaceship;
	private ArrayList<Asteroid> asteroids;
	private ArrayList<Projectile> projectiles;
	private ArrayList<Asteroid.Debris> debrisList;
	private boolean gameOver;
	private Random rand;
                   
	public SpaceInvaders() {
		super("Space Invaders!", 800, 600);
		this.setFocusable(true);
		this.requestFocus();
		
		spaceship = new Spaceship(new Point(400,500));
		
		// Add the spaceship's own key controls (using an anonymous inner class).
        this.addKeyListener(spaceship.getKeyControls());
		
        // Add additional key controls for firing projectiles.
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // When SPACE is pressed, fire a projectile from the spaceship.
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    projectiles.add(new Projectile(new Point(spaceship.position.x, spaceship.position.y)));
                }
            }
        });
        
		asteroids = new ArrayList<>();
        projectiles = new ArrayList<>();
        debrisList = new ArrayList<>();
        gameOver = false;
        rand = new Random();
	}

	public void paint(Graphics brush) {
		brush.setColor(Color.black);
		brush.fillRect(0, 0, width, height);

		// sample code for printing message for debugging
		// counter is incremented and this message printed
		// each time the canvas is repainted
		counter++;
		brush.setColor(Color.white);
		brush.drawString("Counter is " + counter, 10, 10);
		brush.drawString("Press UP Arrow for a 50% boost for 10sec"+
									"(There will be a 10sec cooldown)", 300, 10);
		brush.drawString("Speed is " + spaceship.speed, 200, 10);
		brush.drawString("Points: " + points, 110, 10);
		
		// Drawing boarders
		brush.drawLine(0, 15, height, 15);
		brush.drawLine(15, 15, 15, width);
		brush.drawLine(height, 15, height, width);
		
		if (rand.nextInt(100) < 2) {
			if(rand.nextInt(2)==1) {
				asteroids.add(new Asteroid(new Point(rand.nextInt(width),0), true));
			} else {
				asteroids.add(new Asteroid(new Point(rand.nextInt(width),0), false));
			}
			
		}
		
		if(!gameOver) {
			spaceship.draw(brush);
			spaceship.move();
		}
		
		for(Asteroid a: asteroids) {
			a.move();
			a.draw(brush);
			
			if(spaceship.collidesWith(a)) {
				gameOver = true;
			}
		}
		
		for(Projectile p: projectiles) {
			p.move();
			p.draw(brush);
		}
		
		for(Projectile p: projectiles) {
			for(Asteroid a: asteroids) {
				if(a.collidesWith(p)) {
					p.deactivate();
					if (!a.hasSpawnedDebris()) {
                        debrisList.addAll(a.spawnDebris());
    					points++;
                    }
				}
			}
		}
		
		// Update, draw, and remove debris fragments.
        for (Asteroid.Debris d : debrisList) {
            d.move();
            d.draw(brush);
        }
		
        debrisList.removeIf(d -> !d.getStatus());
		asteroids.removeIf(a -> !a.getStatus());
		projectiles.removeIf(p -> !p.getStatus());
		
		if(gameOver) {
			brush.setColor(Color.RED);
            brush.drawString("GAME OVER", width / 2 - 30, height / 2);
            on = false;
		}
		
	}

	public static void main(String[] args) {
		SpaceInvaders a = new SpaceInvaders();
		a.repaint();
	}
}