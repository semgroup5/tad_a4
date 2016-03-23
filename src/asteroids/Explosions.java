package asteroids;

import java.awt.Polygon;

public class Explosions extends Sprite {
	
	public Explosions() {
		super();
	}
	
	  public void initExplosions() {

		    int i;

		    for (i = 0; i < Game.MAX_SCRAP; i++) {
		    	Game.explosions[i].shape = new Polygon();
		    	Game.explosions[i].active = false;
		    	Game.explosionCounter[i] = 0;
		    }
		    Game.explosionIndex = 0;
		  }
	  			
		  public static void explode(Sprite s) {

		    int c, i, j;
		    int cx, cy;

		    // Create sprites for explosion animation. The each individual line segment
		    // of the given sprite is used to create a new sprite that will move
		    // outward  from the sprite's original position with a random rotation.

		    s.render();
		    c = 2;
		    if (Game.detail || s.sprite.npoints < 6)
		      c = 1;
		    for (i = 0; i < s.sprite.npoints; i += c) {
		      Game.explosionIndex++;
		      if (Game.explosionIndex >= Game.MAX_SCRAP)
		        Game.explosionIndex = 0;
		      Game.explosions[Game.explosionIndex].active = true;
		      Game.explosions[Game.explosionIndex].shape = new Polygon();
		      j = i + 1;
		      if (j >= s.sprite.npoints)
		        j -= s.sprite.npoints;
		      
		      cx = (int) ((s.shape.xpoints[i] + s.shape.xpoints[j]) / 2);
		      cy = (int) ((s.shape.ypoints[i] + s.shape.ypoints[j]) / 2);
		      Game.explosions[Game.explosionIndex].shape.addPoint(
		        s.shape.xpoints[i] - cx,
		        s.shape.ypoints[i] - cy);
		      Game.explosions[Game.explosionIndex].shape.addPoint(
		        s.shape.xpoints[j] - cx,
		        s.shape.ypoints[j] - cy);
		      Game.explosions[Game.explosionIndex].x = s.x + cx;
		      Game.explosions[Game.explosionIndex].y = s.y + cy;
		      Game.explosions[Game.explosionIndex].angle = s.angle;
		      Game.explosions[Game.explosionIndex].deltaAngle = 4 * (Math.random() * 2 * Game.MAX_ROCK_SPIN - Game.MAX_ROCK_SPIN);
		      Game.explosions[Game.explosionIndex].deltaX = (Math.random() * 2 * Game.MAX_ROCK_SPEED - Game.MAX_ROCK_SPEED + s.deltaX) / 2;
		      Game.explosions[Game.explosionIndex].deltaY = (Math.random() * 2 * Game.MAX_ROCK_SPEED - Game.MAX_ROCK_SPEED + s.deltaY) / 2;
		      Game.explosionCounter[Game.explosionIndex] = Game.SCRAP_COUNT;
		    }
		  }

		  public void updateExplosions() {

		    int i;

		    // Move any active explosion debris. Stop explosion when its counter has
		    // expired.

		    for (i = 0; i < Game.MAX_SCRAP; i++)
		      if (Game.explosions[i].active) {
		    	  Game.explosions[i].advance();
		    	  Game.explosions[i].render();
		        if (--Game.explosionCounter[i] < 0)
		        	Game.explosions[i].active = false;
		      }
		  }

}
