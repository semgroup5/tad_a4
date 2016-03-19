package asteroids;

public class Ship extends AsteroidsSprite {
	
	public Ship() {
		super();
	}

	
	  public void initShip() {

		    // Reset the ship sprite at the center of the screen.

		    this.active = true;
		    this.angle = 0.0;
		    this.deltaAngle = 0.0;
		    this.x = 0.0;
		    this.y = 0.0;
		    this.deltaX = 0.0;
		    this.deltaY = 0.0;
		    this.render();

		    // Initialize thruster sprites.

		    Asteroids.fwdThruster.x = this.x;
		    Asteroids.fwdThruster.y = this.y;
		    Asteroids.fwdThruster.angle = this.angle;
		    Asteroids.fwdThruster.render();
		    Asteroids.revThruster.x = this.x;
		    Asteroids.revThruster.y = this.y;
		    Asteroids.revThruster.angle = this.angle;
		    Asteroids.revThruster.render();

		    if (Asteroids.loaded)
		    	Asteroids.thrustersSound.stop();
		    Asteroids.thrustersPlaying = false;
		    Asteroids.hyperCounter = 0;
		  }
	  
	  public void stopShip() {

		    Asteroids.ship.active = false;
		    Asteroids.shipCounter = Asteroids.SCRAP_COUNT;
		    if (Asteroids.shipsLeft > 0)
		    	Asteroids.shipsLeft--;
		    if (Asteroids.loaded)
		    	Asteroids.thrustersSound.stop();
		    Asteroids.thrustersPlaying = false;
		  }

		  public void updateShip() {

		    double dx, dy, speed;

		    if (!Asteroids.playing)
		      return;

		    // Rotate the ship if left or right cursor key is down.

		    if (Asteroids.left) {
		      Asteroids.ship.angle += Asteroids.SHIP_ANGLE_STEP;
		      if (Asteroids.ship.angle > 2 * Math.PI)
		        Asteroids.ship.angle -= 2 * Math.PI;
		    }
		    if (Asteroids.right) {
		      Asteroids.ship.angle -= Asteroids.SHIP_ANGLE_STEP;
		      if (Asteroids.ship.angle < 0)
		        Asteroids.ship.angle += 2 * Math.PI;
		    }

		    // Fire thrusters if up or down cursor key is down.

		    dx = Asteroids.SHIP_SPEED_STEP * -Math.sin(Asteroids.ship.angle);
		    dy = Asteroids.SHIP_SPEED_STEP *  Math.cos(Asteroids.ship.angle);
		    if (Asteroids.up) {
		      Asteroids.ship.deltaX += dx;
		      Asteroids.ship.deltaY += dy;
		    }
		    if (Asteroids.down) {
		        Asteroids.ship.deltaX -= dx;
		        Asteroids.ship.deltaY -= dy;
		    }

		    // Don't let ship go past the speed limit.

		    if (Asteroids.up || Asteroids.down) {
		      speed = Math.sqrt(Asteroids.ship.deltaX * Asteroids.ship.deltaX + Asteroids.ship.deltaY * Asteroids.ship.deltaY);
		      if (speed > Asteroids.MAX_SHIP_SPEED) {
		        dx = Asteroids.MAX_SHIP_SPEED * -Math.sin(Asteroids.ship.angle);
		        dy = Asteroids.MAX_SHIP_SPEED *  Math.cos(Asteroids.ship.angle);
		        if (Asteroids.up)
		          Asteroids.ship.deltaX = dx;
		        else
		          Asteroids.ship.deltaX = -dx;
		        if (Asteroids.up)
		          Asteroids.ship.deltaY = dy;
		        else
		          Asteroids.ship.deltaY = -dy;
		      }
		    }

		    // Move the ship. If it is currently in hyperspace, advance the countdown.

		    if (Asteroids.ship.active) {
		      Asteroids.ship.advance();
		      Asteroids.ship.render();
		      if (Asteroids.hyperCounter > 0)
		    	  Asteroids.hyperCounter--;

		      // Update the thruster sprites to match the ship sprite.

		      Asteroids.fwdThruster.x = Asteroids.ship.x;
		      Asteroids.fwdThruster.y = Asteroids.ship.y;
		      Asteroids.fwdThruster.angle = Asteroids.ship.angle;
		      Asteroids.fwdThruster.render();
		      Asteroids.revThruster.x = Asteroids.ship.x;
		      Asteroids.revThruster.y = Asteroids.ship.y;
		      Asteroids.revThruster.angle = Asteroids.ship.angle;
		      Asteroids.revThruster.render();
		    }

		    // Ship is exploding, advance the countdown or create a new ship if it is
		    // done exploding. The new ship is added as though it were in hyperspace.
		    // (This gives the player time to move the ship if it is in imminent
		    // danger.) If that was the last ship, end the game.

		    else
		      if (--Asteroids.shipCounter <= 0)
		        if (Asteroids.shipsLeft > 0) {
		          initShip();
		          Asteroids.hyperCounter = Asteroids.HYPER_COUNT;
		        }
		        else
		        	Asteroids.endGame();
		  }

}
