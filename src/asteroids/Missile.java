package asteroids;

class Missile extends Sprite {
	
	public Missile() {
		super();
	}
	
	
	  public void initMissle() {

		    this.active = true;
		    this.angle = 0.0;
		    this.deltaAngle = 0.0;
		    this.x = Game.ufo.x;
		    this.y = Game.ufo.y;
		    this.deltaX = 0.0;
		    this.deltaY = 0.0;
		    this.render();
		    Game.missleCounter = Game.MISSLE_COUNT;
		    if (Game.sound)
		      Game.missleSound.loop();
		    Game.misslePlaying = true;
		  }

		  public void updateMissle() {

		    int i;

		    // Move the guided missle and check for collision with ship or photon. Stop
		    // it when its counter has expired.

		    if (Game.missle.active) {
		      if (--Game.missleCounter <= 0)
		        stopMissle();
		      else {
		        guideMissle();
		        Game.missle.advance();
		        Game.missle.render();
		        for (i = 0; i < Game.MAX_SHOTS; i++)
		          if (Game.photons[i].active && Game.missle.isColliding(Game.photons[i])) {
		            if (Game.sound)
		            	Game.crashSound.play();
		            Explosions.explode(Game.missle);
		            stopMissle();
		            Game.score += Game.MISSLE_POINTS;
		          }
		        if (Game.missle.active && Game.ship.active &&
		        		Game.hyperCounter <= 0 && Game.ship.isColliding(Game.missle)) {
		          if (Game.sound)
		        	  Game.crashSound.play();
		          Explosions.explode(Game.ship);
		          Game.ship.stopShip();
		          Game.ufo.stopUfo();
		          stopMissle();
		        }
		      }
		    }
		  }

		  public void guideMissle() {

		    double dx, dy, angle;

		    if (!Game.ship.active || Game.hyperCounter > 0)
		      return;

		    // Find the angle needed to hit the ship.

		    dx = Game.ship.x - Game.missle.x;
		    dy = Game.ship.y - Game.missle.y;
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

		    Game.missle.angle = angle - Math.PI / 2;

		    // Change the missle's angle so that it points toward the ship.

		    Game.missle.deltaX = 0.75 * Game.MAX_ROCK_SPEED * -Math.sin(Game.missle.angle);
		    Game.missle.deltaY = 0.75 * Game.MAX_ROCK_SPEED *  Math.cos(Game.missle.angle);
		  }

		  public void stopMissle() {

		    Game.missle.active = false;
		    Game.missleCounter = 0;
		    if (Game.loaded)
		      Game.missleSound.stop();
		    Game.misslePlaying = false;
		  }

}
