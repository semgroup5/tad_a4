package asteroids;

public class Ufo extends AsteroidsSprite {
	
	public Ufo() {
		super();
	}
	
	  public void initUfo() {

		    double angle, speed;

		    // Randomly set flying saucer at left or right edge of the screen.

		    Asteroids.ufo.active = true;
		    Asteroids.ufo.x = -AsteroidsSprite.width / 2;
		    Asteroids.ufo.y = Math.random() * 2 * AsteroidsSprite.height - AsteroidsSprite.height;
		    angle = Math.random() * Math.PI / 4 - Math.PI / 2;
		    speed = Asteroids.MAX_ROCK_SPEED / 2 + Math.random() * (Asteroids.MAX_ROCK_SPEED / 2);
		    Asteroids.ufo.deltaX = speed * -Math.sin(angle);
		    Asteroids.ufo.deltaY = speed *  Math.cos(angle);
		    if (Math.random() < 0.5) {
		      Asteroids.ufo.x = AsteroidsSprite.width / 2;
		      Asteroids.ufo.deltaX = -Asteroids.ufo.deltaX;
		    }
		    if (Asteroids.ufo.y > 0)
		      Asteroids.ufo.deltaY = Asteroids.ufo.deltaY;
		    Asteroids.ufo.render();
		    Asteroids.saucerPlaying = true;
		    if (Asteroids.sound)
		    	Asteroids.saucerSound.loop();
		    Asteroids.ufoCounter = (int) Math.abs(AsteroidsSprite.width / Asteroids.ufo.deltaX);
		  }

		  public void updateUfo() {

		    int i, d;
		    //----boolean wrapped;    //initialiazed in AsteroidsSprite.class

		    // Move the flying saucer and check for collision with a photon. Stop it
		    // when its counter has expired.

		    if (Asteroids.ufo.active) {
		      if (--Asteroids.ufoCounter <= 0) {
		        if (--Asteroids.ufoPassesLeft > 0)
		          initUfo();
		        else
		          stopUfo();
		      }
		      if (Asteroids.ufo.active) {
		        Asteroids.ufo.advance();
		        Asteroids.ufo.render();
		        for (i = 0; i < Asteroids.MAX_SHOTS; i++)
		          if (Asteroids.photons[i].active && Asteroids.ufo.isColliding(Asteroids.photons[i])) {
		            if (Asteroids.sound)
		            	Asteroids.crashSound.play();
		            Explosions.explode(Asteroids.ufo);
		            stopUfo();
		            Asteroids.score += Asteroids.UFO_POINTS;
		          }

		          // On occassion, fire a missle at the ship if the saucer is not too
		          // close to it.

		          d = (int) Math.max(Math.abs(Asteroids.ufo.x - Asteroids.ship.x), Math.abs(Asteroids.ufo.y - Asteroids.ship.y));
		          if (Asteroids.ship.active && Asteroids.hyperCounter <= 0 &&
		              Asteroids.ufo.active && !Asteroids.missle.active &&
		              d > Asteroids.MAX_ROCK_SPEED * Asteroids.FPS / 2 &&
		              Math.random() < Asteroids.MISSLE_PROBABILITY){
		      }
		        new Missile().initMissle();
		       }
		    }
		    }
		  

		  public void stopUfo() {

		    Asteroids.ufo.active = false;
		    Asteroids.ufoCounter = 0;
		    Asteroids.ufoPassesLeft = 0;
		    if (Asteroids.loaded)
		    	Asteroids.saucerSound.stop();
		    Asteroids.saucerPlaying = false;
		  }


}
