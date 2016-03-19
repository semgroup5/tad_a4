package asteroids;

import java.awt.Polygon;

public class Explosions extends AsteroidsSprite {
	
	public Explosions() {
		super();
	}
	
	  public void initExplosions() {

		    int i;

		    for (i = 0; i < Asteroids.MAX_SCRAP; i++) {
		    	Asteroids.explosions[i].shape = new Polygon();
		    	Asteroids.explosions[i].active = false;
		    	Asteroids.explosionCounter[i] = 0;
		    }
		    Asteroids.explosionIndex = 0;
		  }
	  			
		  public static void explode(AsteroidsSprite s) {

		    int c, i, j;
		    int cx, cy;

		    // Create sprites for explosion animation. The each individual line segment
		    // of the given sprite is used to create a new sprite that will move
		    // outward  from the sprite's original position with a random rotation.

		    s.render();
		    c = 2;
		    if (Asteroids.detail || s.sprite.npoints < 6)
		      c = 1;
		    for (i = 0; i < s.sprite.npoints; i += c) {
		      Asteroids.explosionIndex++;
		      if (Asteroids.explosionIndex >= Asteroids.MAX_SCRAP)
		        Asteroids.explosionIndex = 0;
		      Asteroids.explosions[Asteroids.explosionIndex].active = true;
		      Asteroids.explosions[Asteroids.explosionIndex].shape = new Polygon();
		      j = i + 1;
		      if (j >= s.sprite.npoints)
		        j -= s.sprite.npoints;
		      
		      cx = (int) ((s.shape.xpoints[i] + s.shape.xpoints[j]) / 2);
		      cy = (int) ((s.shape.ypoints[i] + s.shape.ypoints[j]) / 2);
		      Asteroids.explosions[Asteroids.explosionIndex].shape.addPoint(
		        s.shape.xpoints[i] - cx,
		        s.shape.ypoints[i] - cy);
		      Asteroids.explosions[Asteroids.explosionIndex].shape.addPoint(
		        s.shape.xpoints[j] - cx,
		        s.shape.ypoints[j] - cy);
		      Asteroids.explosions[Asteroids.explosionIndex].x = s.x + cx;
		      Asteroids.explosions[Asteroids.explosionIndex].y = s.y + cy;
		      Asteroids.explosions[Asteroids.explosionIndex].angle = s.angle;
		      Asteroids.explosions[Asteroids.explosionIndex].deltaAngle = 4 * (Math.random() * 2 * Asteroids.MAX_ROCK_SPIN - Asteroids.MAX_ROCK_SPIN);
		      Asteroids.explosions[Asteroids.explosionIndex].deltaX = (Math.random() * 2 * Asteroids.MAX_ROCK_SPEED - Asteroids.MAX_ROCK_SPEED + s.deltaX) / 2;
		      Asteroids.explosions[Asteroids.explosionIndex].deltaY = (Math.random() * 2 * Asteroids.MAX_ROCK_SPEED - Asteroids.MAX_ROCK_SPEED + s.deltaY) / 2;
		      Asteroids.explosionCounter[Asteroids.explosionIndex] = Asteroids.SCRAP_COUNT;
		    }
		  }

		  public void updateExplosions() {

		    int i;

		    // Move any active explosion debris. Stop explosion when its counter has
		    // expired.

		    for (i = 0; i < Asteroids.MAX_SCRAP; i++)
		      if (Asteroids.explosions[i].active) {
		    	  Asteroids.explosions[i].advance();
		    	  Asteroids.explosions[i].render();
		        if (--Asteroids.explosionCounter[i] < 0)
		        	Asteroids.explosions[i].active = false;
		      }
		  }

}
