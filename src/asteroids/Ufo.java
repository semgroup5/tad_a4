package asteroids;

public class Ufo extends Sprite {
	
	public Ufo() {
		super();
	}
	
	  public void initUfo() {

		    double angle, speed;

		    // Randomly set flying saucer at left or right edge of the screen.

		    Game.ufo.active = true;
		    Game.ufo.x = -Sprite.width / 2;
		    Game.ufo.y = Math.random() * 2 * Sprite.height - Sprite.height;
		    angle = Math.random() * Math.PI / 4 - Math.PI / 2;
		    speed = Game.MAX_ROCK_SPEED / 2 + Math.random() * (Game.MAX_ROCK_SPEED / 2);
		    Game.ufo.deltaX = speed * -Math.sin(angle);
		    Game.ufo.deltaY = speed *  Math.cos(angle);
		    if (Math.random() < 0.5) {
		      Game.ufo.x = Sprite.width / 2;
		      Game.ufo.deltaX = -Game.ufo.deltaX;
		    }
		    if (Game.ufo.y > 0)
		      Game.ufo.deltaY = Game.ufo.deltaY;
		    Game.ufo.render();
		    Game.saucerPlaying = true;
		    if (Game.sound)
		    	Game.saucerSound.loop();
		    Game.ufoCounter = (int) Math.abs(Sprite.width / Game.ufo.deltaX);
		  }

		  public void updateUfo() {

		    int i, d;
		    //----boolean wrapped;    //initialiazed in AsteroidsSprite.class

		    // Move the flying saucer and check for collision with a photon. Stop it
		    // when its counter has expired.

		    if (Game.ufo.active) {
		      if (--Game.ufoCounter <= 0) {
		        if (--Game.ufoPassesLeft > 0)
		          initUfo();
		        else
		          stopUfo();
		      }
		      if (Game.ufo.active) {
		        Game.ufo.advance();
		        Game.ufo.render();
		        for (i = 0; i < Game.MAX_SHOTS; i++)
		          if (Game.photons[i].active && Game.ufo.isColliding(Game.photons[i])) {
		            if (Game.sound)
		            	Game.crashSound.play();
		            Explosions.explode(Game.ufo);
		            stopUfo();
		            Game.score += Game.UFO_POINTS;
		          }

		          // On occassion, fire a missle at the ship if the saucer is not too
		          // close to it.

		          d = (int) Math.max(Math.abs(Game.ufo.x - Game.ship.x), Math.abs(Game.ufo.y - Game.ship.y));
		          if (Game.ship.active && Game.hyperCounter <= 0 &&
		              Game.ufo.active && !Game.missle.active &&
		              d > Game.MAX_ROCK_SPEED * Game.FPS / 2 &&
		              Math.random() < Game.MISSLE_PROBABILITY){
		      }
		        new Missile().initMissle();
		       }
		    }
		    }
		  

		  public void stopUfo() {

		    Game.ufo.active = false;
		    Game.ufoCounter = 0;
		    Game.ufoPassesLeft = 0;
		    if (Game.loaded)
		    	Game.saucerSound.stop();
		    Game.saucerPlaying = false;
		  }


}
