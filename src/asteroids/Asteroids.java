package asteroids;

import java.awt.Polygon;


public class Asteroids extends Sprite {
	
	public Asteroids() {
		super();
	}
	
	  public void initAsteroids() {

		    int i, j;
		    int s;
		    double theta, r;
		    int x, y;

		    // Create random shapes, positions and movements for each asteroid.

		    for (i = 0; i < Game.MAX_ROCKS; i++) {

		      // Create a jagged shape for the asteroid and give it a random rotation.

		    	Game.asteroids[i].shape = new Polygon();
		      s = Game.MIN_ROCK_SIDES + (int) (Math.random() * (Game.MAX_ROCK_SIDES - Game.MIN_ROCK_SIDES));
		      for (j = 0; j < s; j ++) {
		        theta = 2 * Math.PI / s * j;
		        r = Game.MIN_ROCK_SIZE + (int) (Math.random() * (Game.MAX_ROCK_SIZE - Game.MIN_ROCK_SIZE));
		        x = (int) -Math.round(r * Math.sin(theta));
		        y = (int)  Math.round(r * Math.cos(theta));
		        Game.asteroids[i].shape.addPoint(x, y);
		      }
		      Game.asteroids[i].active = true;
		      Game.asteroids[i].angle = 0.0;
		      Game.asteroids[i].deltaAngle = Math.random() * 2 * Game.MAX_ROCK_SPIN - Game.MAX_ROCK_SPIN;

		      // Place the asteroid at one edge of the screen.

		      if (Math.random() < 0.5) {
		    	  Game.asteroids[i].x = -Sprite.width / 2;
		        if (Math.random() < 0.5)
		        	Game.asteroids[i].x = Sprite.width / 2;
		        Game.asteroids[i].y = Math.random() * Sprite.height;
		      }
		      else {
		    	  Game.asteroids[i].x = Math.random() * Sprite.width;
		    	  Game.asteroids[i].y = -Sprite.height / 2;
		        if (Math.random() < 0.5)
		        	Game.asteroids[i].y = Sprite.height / 2;
		      }

		      // Set a random motion for the asteroid.

		      Game.asteroids[i].deltaX = Math.random() * Game.asteroidsSpeed;
		      if (Math.random() < 0.5)
		    	  Game.asteroids[i].deltaX = -Game.asteroids[i].deltaX;
		      Game.asteroids[i].deltaY = Math.random() * Game.asteroidsSpeed;
		      if (Math.random() < 0.5)
		    	  Game.asteroids[i].deltaY = -Game.asteroids[i].deltaY;

		      Game.asteroids[i].render();
		      Game.asteroidIsSmall[i] = false;
		    }

		    Game.asteroidsCounter = Game.STORM_PAUSE;
		    Game.asteroidsLeft = Game.MAX_ROCKS;
		    if (Game.asteroidsSpeed < Game.MAX_ROCK_SPEED)
		    	Game.asteroidsSpeed += 0.5;
		  }

		  public void initSmallAsteroids(int n) {

		    int count;
		    int i, j;
		    int s;
		    double tempX, tempY;
		    double theta, r;
		    int x, y;

		    // Create one or two smaller asteroids from a larger one using inactive
		    // asteroids. The new asteroids will be placed in the same position as the
		    // old one but will have a new, smaller shape and new, randomly generated
		    // movements.

		    count = 0;
		    i = 0;
		    tempX = Game.asteroids[n].x;
		    tempY = Game.asteroids[n].y;
		    do {
		      if (!Game.asteroids[i].active) {
		    	  Game.asteroids[i].shape = new Polygon();
		        s = Game.MIN_ROCK_SIDES + (int) (Math.random() * (Game.MAX_ROCK_SIDES - Game.MIN_ROCK_SIDES));
		        for (j = 0; j < s; j ++) {
		          theta = 2 * Math.PI / s * j;
		          r = (Game.MIN_ROCK_SIZE + (int) (Math.random() * (Game.MAX_ROCK_SIZE - Game.MIN_ROCK_SIZE))) / 2;
		          x = (int) -Math.round(r * Math.sin(theta));
		          y = (int)  Math.round(r * Math.cos(theta));
		          Game.asteroids[i].shape.addPoint(x, y);
		        }
		        Game.asteroids[i].active = true;
		        Game.asteroids[i].angle = 0.0;
		        Game.asteroids[i].deltaAngle = Math.random() * 2 * Game.MAX_ROCK_SPIN - Game.MAX_ROCK_SPIN;
		        Game.asteroids[i].x = tempX;
		        Game.asteroids[i].y = tempY;
		        Game.asteroids[i].deltaX = Math.random() * 2 * Game.asteroidsSpeed - Game.asteroidsSpeed;
		        Game.asteroids[i].deltaY = Math.random() * 2 * Game.asteroidsSpeed - Game.asteroidsSpeed;
		        Game.asteroids[i].render();
		        Game.asteroidIsSmall[i] = true;
		        count++;
		        Game.asteroidsLeft++;
		      }
		      i++;
		    } while (i < Game.MAX_ROCKS && count < 2);
		  }

		  public void updateAsteroids() {

		    int i, j;

		    // Move any active asteroids and check for collisions.

		    for (i = 0; i < Game.MAX_ROCKS; i++)
		      if (Game.asteroids[i].active) {
		    	  Game.asteroids[i].advance();
		    	  Game.asteroids[i].render();

		        // If hit by photon, kill asteroid and advance score. If asteroid is
		        // large, make some smaller ones to replace it.

		        for (j = 0; j < Game.MAX_SHOTS; j++)
		          if (Game.photons[j].active && Game.asteroids[i].active && Game.asteroids[i].isColliding(Game.photons[j])) {
		        	  Game.asteroidsLeft--;
		        	  Game.asteroids[i].active = false;
		        	  Game.photons[j].active = false;
		            if (Game.sound)
		            	Game.explosionSound.play();
		            Explosions.explode(Game.asteroids[i]);
		            if (!Game.asteroidIsSmall[i]) {
		            	Game.score += Game.BIG_POINTS;
		              initSmallAsteroids(i);
		            }
		            else
		            	Game.score += Game.SMALL_POINTS;
		          }

		        // If the ship is not in hyperspace, see if it is hit.

		        if (Game.ship.active && Game.hyperCounter <= 0 &&
		        		Game.asteroids[i].active && Game.asteroids[i].isColliding(Game.ship)) {
		          if (Game.sound)
		        	  Game.crashSound.play();
		          Explosions.explode(Game.ship);
		          Game.ship.stopShip();
		          Game.ufo.stopUfo();
		          Game.missle.stopMissle();
		        }
		    }
		  }

}
