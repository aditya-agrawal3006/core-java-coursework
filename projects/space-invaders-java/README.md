# Space Invaders — Java Arcade Game

A desktop arcade-style game implemented with Java AWT. The player controls a spaceship, fires projectiles at incoming asteroids, and earns points by destroying them. Asteroids move and rotate, and hits can create moving debris fragments.

## Gameplay

- Move the spaceship **left/right** with the arrow keys.
- Fire a projectile with **Space**.
- Use the **Up arrow** to activate a temporary movement-speed boost with a cooldown.
- Avoid colliding with falling asteroids; a collision ends the game.
- Earn points by hitting asteroids and watch debris animations after impacts.

## Implementation details

- Organizes the game into separate spaceship, asteroid, projectile, geometry, and game-loop classes.
- Uses Java AWT to draw shapes, text, game status, and animated objects.
- Handles keypress events through listeners.
- Updates object positions, checks collisions, spawns debris, and removes inactive objects during the update/draw cycle.

## Code structure

- [`SpaceInvaders.java`](src/game/SpaceInvaders.java) — entry point, input listeners, scoring, collision checks, and game state.
- [`Spaceship.java`](src/game/Spaceship.java) — ship controls, drawing, and speed boost.
- [`Asteroid.java`](src/game/Asteroid.java) — asteroid movement, collision handling, and debris.
- [`Projectile.java`](src/game/Projectile.java) — projectile movement and rendering.
- [`Game.java`](src/game/Game.java) and [`Polygon.java`](src/game/Polygon.java) — AWT game framework and geometry.

## Build and play

Requires a graphical desktop environment and **JDK 17+**. From this project directory on Linux/macOS/WSL:

```bash
javac -d out $(find src -name '*.java')
java -cp out game.SpaceInvaders
```

On WSL, the game requires working GUI support (such as WSLg). Make sure the game window has keyboard focus before using the controls.

**Technical focus:** object-oriented design, event-driven programming, AWT graphics, collision detection, and interactive simulation.

> University of Maryland Java coursework. Some game framework classes and project scaffolding may have been provided by the course.
