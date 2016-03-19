package asteroids;

class Missile extends AsteroidsSprite {
	
	public Missile() {
		super();
	}
	
	
	  public void initMissle() {

		    this.active = true;
		    this.angle = 0.0;
		    this.deltaAngle = 0.0;
		    this.x = Asteroids.ufo.x;
		    this.y = Asteroids.ufo.y;
		    this.deltaX = 0.0;
		    this.deltaY = 0.0;
		    this.render();
		    Asteroids.missleCounter = Asteroids.MISSLE_COUNT;
		    if (Asteroids.sound)
		      Asteroids.missleSound.loop();
		    Asteroids.misslePlaying = true;
		  }

		  public void updateMissle() {

		    int i;

		    // Move the guided missle and check for collision with ship or photon. Stop
		    // it when its counter has expired.

		    if (Asteroids.missle.active) {
		      if (--Asteroids.missleCounter <= 0)
		        stopMissle();
		      else {
		        guideMissle();
		        Asteroids.missle.advance();
		        Asteroids.missle.render();
		        for (i = 0; i < Asteroids.MAX_SHOTS; i++)
		          if (Asteroids.photons[i].active && Asteroids.missle.isColliding(Asteroids.photons[i])) {
		            if (Asteroids.sound)
		            	Asteroids.crashSound.play();
		            Explosions.explode(Asteroids.missle);
		            stopMissle();
		            Asteroids.score += Asteroids.MISSLE_POINTS;
		          }
		        if (Asteroids.missle.active && Asteroids.ship.active &&
		        		Asteroids.hyperCounter <= 0 && Asteroids.ship.isColliding(Asteroids.missle)) {
		          if (Asteroids.sound)
		        	  Asteroids.crashSound.play();
		          Explosions.explode(Asteroids.ship);
		          Asteroids.ship.stopShip();
		          Asteroids.ufo.stopUfo();
		          stopMissle();
		        }
		      }
		    }
		  }

		  public void guideMissle() {

		    double dx, dy, angle;

		    if (!Asteroids.ship.active || Asteroids.hyperCounter > 0)
		      return;

		    // Find the angle needed to hit the ship.

		    dx = Asteroids.ship.x - Asteroids.missle.x;
		    dy = Asteroids.ship.y - Asteroids.missle.y;
		    if (dx == 0 && dy == 0)
		      angle = 0;
		    if (dx == 0) {
		      if (dy < 0)
		        angle = -Math.PI / 2;
		      else
		        angle = Math.PI / 2;
		    }
		    else {
		      angle = Math.atan(Math.abs(dy / dx));
		      if (dy > 0)
		        angle = -angle;
		      if (dx < 0)
		        angle = Math.PI - angle;
		    }

		    // Adjust angle for screen coordinates.

		    Asteroids.missle.angle = angle - Math.PI / 2;

		    // Change the missle's angle so that it points toward the ship.

		    Asteroids.missle.deltaX = 0.75 * Asteroids.MAX_ROCK_SPEED * -Math.sin(Asteroids.missle.angle);
		    Asteroids.missle.deltaY = 0.75 * Asteroids.MAX_ROCK_SPEED *  Math.cos(Asteroids.missle.angle);
		  }

		  public void stopMissle() {

		    Asteroids.missle.active = false;
		    Asteroids.missleCounter = 0;
		    if (Asteroids.loaded)
		      Asteroids.missleSound.stop();
		    Asteroids.misslePlaying = false;
		  }

}
