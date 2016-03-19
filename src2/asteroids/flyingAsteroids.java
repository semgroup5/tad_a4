package asteroids;

import java.awt.Polygon;


public class flyingAsteroids extends AsteroidsSprite {
	
	public flyingAsteroids() {
		super();
	}
	
	  public void initAsteroids() {

		    int i, j;
		    int s;
		    double theta, r;
		    int x, y;

		    // Create random shapes, positions and movements for each asteroid.

		    for (i = 0; i < Asteroids.MAX_ROCKS; i++) {

		      // Create a jagged shape for the asteroid and give it a random rotation.

		    	Asteroids.asteroids[i].shape = new Polygon();
		      s = Asteroids.MIN_ROCK_SIDES + (int) (Math.random() * (Asteroids.MAX_ROCK_SIDES - Asteroids.MIN_ROCK_SIDES));
		      for (j = 0; j < s; j ++) {
		        theta = 2 * Math.PI / s * j;
		        r = Asteroids.MIN_ROCK_SIZE + (int) (Math.random() * (Asteroids.MAX_ROCK_SIZE - Asteroids.MIN_ROCK_SIZE));
		        x = (int) -Math.round(r * Math.sin(theta));
		        y = (int)  Math.round(r * Math.cos(theta));
		        Asteroids.asteroids[i].shape.addPoint(x, y);
		      }
		      Asteroids.asteroids[i].active = true;
		      Asteroids.asteroids[i].angle = 0.0;
		      Asteroids.asteroids[i].deltaAngle = Math.random() * 2 * Asteroids.MAX_ROCK_SPIN - Asteroids.MAX_ROCK_SPIN;

		      // Place the asteroid at one edge of the screen.

		      if (Math.random() < 0.5) {
		    	  Asteroids.asteroids[i].x = -AsteroidsSprite.width / 2;
		        if (Math.random() < 0.5)
		        	Asteroids.asteroids[i].x = AsteroidsSprite.width / 2;
		        Asteroids.asteroids[i].y = Math.random() * AsteroidsSprite.height;
		      }
		      else {
		    	  Asteroids.asteroids[i].x = Math.random() * AsteroidsSprite.width;
		    	  Asteroids.asteroids[i].y = -AsteroidsSprite.height / 2;
		        if (Math.random() < 0.5)
		        	Asteroids.asteroids[i].y = AsteroidsSprite.height / 2;
		      }

		      // Set a random motion for the asteroid.

		      Asteroids.asteroids[i].deltaX = Math.random() * Asteroids.asteroidsSpeed;
		      if (Math.random() < 0.5)
		    	  Asteroids.asteroids[i].deltaX = -Asteroids.asteroids[i].deltaX;
		      Asteroids.asteroids[i].deltaY = Math.random() * Asteroids.asteroidsSpeed;
		      if (Math.random() < 0.5)
		    	  Asteroids.asteroids[i].deltaY = -Asteroids.asteroids[i].deltaY;

		      Asteroids.asteroids[i].render();
		      Asteroids.asteroidIsSmall[i] = false;
		    }

		    Asteroids.asteroidsCounter = Asteroids.STORM_PAUSE;
		    Asteroids.asteroidsLeft = Asteroids.MAX_ROCKS;
		    if (Asteroids.asteroidsSpeed < Asteroids.MAX_ROCK_SPEED)
		    	Asteroids.asteroidsSpeed += 0.5;
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
		    tempX = Asteroids.asteroids[n].x;
		    tempY = Asteroids.asteroids[n].y;
		    do {
		      if (!Asteroids.asteroids[i].active) {
		    	  Asteroids.asteroids[i].shape = new Polygon();
		        s = Asteroids.MIN_ROCK_SIDES + (int) (Math.random() * (Asteroids.MAX_ROCK_SIDES - Asteroids.MIN_ROCK_SIDES));
		        for (j = 0; j < s; j ++) {
		          theta = 2 * Math.PI / s * j;
		          r = (Asteroids.MIN_ROCK_SIZE + (int) (Math.random() * (Asteroids.MAX_ROCK_SIZE - Asteroids.MIN_ROCK_SIZE))) / 2;
		          x = (int) -Math.round(r * Math.sin(theta));
		          y = (int)  Math.round(r * Math.cos(theta));
		          Asteroids.asteroids[i].shape.addPoint(x, y);
		        }
		        Asteroids.asteroids[i].active = true;
		        Asteroids.asteroids[i].angle = 0.0;
		        Asteroids.asteroids[i].deltaAngle = Math.random() * 2 * Asteroids.MAX_ROCK_SPIN - Asteroids.MAX_ROCK_SPIN;
		        Asteroids.asteroids[i].x = tempX;
		        Asteroids.asteroids[i].y = tempY;
		        Asteroids.asteroids[i].deltaX = Math.random() * 2 * Asteroids.asteroidsSpeed - Asteroids.asteroidsSpeed;
		        Asteroids.asteroids[i].deltaY = Math.random() * 2 * Asteroids.asteroidsSpeed - Asteroids.asteroidsSpeed;
		        Asteroids.asteroids[i].render();
		        Asteroids.asteroidIsSmall[i] = true;
		        count++;
		        Asteroids.asteroidsLeft++;
		      }
		      i++;
		    } while (i < Asteroids.MAX_ROCKS && count < 2);
		  }

		  public void updateAsteroids() {

		    int i, j;

		    // Move any active asteroids and check for collisions.

		    for (i = 0; i < Asteroids.MAX_ROCKS; i++)
		      if (Asteroids.asteroids[i].active) {
		    	  Asteroids.asteroids[i].advance();
		    	  Asteroids.asteroids[i].render();

		        // If hit by photon, kill asteroid and advance score. If asteroid is
		        // large, make some smaller ones to replace it.

		        for (j = 0; j < Asteroids.MAX_SHOTS; j++)
		          if (Asteroids.photons[j].active && Asteroids.asteroids[i].active && Asteroids.asteroids[i].isColliding(Asteroids.photons[j])) {
		        	  Asteroids.asteroidsLeft--;
		        	  Asteroids.asteroids[i].active = false;
		        	  Asteroids.photons[j].active = false;
		            if (Asteroids.sound)
		            	Asteroids.explosionSound.play();
		            Explosions.explode(Asteroids.asteroids[i]);
		            if (!Asteroids.asteroidIsSmall[i]) {
		            	Asteroids.score += Asteroids.BIG_POINTS;
		              initSmallAsteroids(i);
		            }
		            else
		            	Asteroids.score += Asteroids.SMALL_POINTS;
		          }

		        // If the ship is not in hyperspace, see if it is hit.

		        if (Asteroids.ship.active && Asteroids.hyperCounter <= 0 &&
		        		Asteroids.asteroids[i].active && Asteroids.asteroids[i].isColliding(Asteroids.ship)) {
		          if (Asteroids.sound)
		        	  Asteroids.crashSound.play();
		          Explosions.explode(Asteroids.ship);
		          Asteroids.ship.stopShip();
		          Asteroids.ufo.stopUfo();
		          Asteroids.missle.stopMissle();
		        }
		    }
		  }

}
