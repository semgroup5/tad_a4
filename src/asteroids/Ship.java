package asteroids;

public class Ship extends Sprite {
	
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

		    Game.fwdThruster.x = this.x;
		    Game.fwdThruster.y = this.y;
		    Game.fwdThruster.angle = this.angle;
		    Game.fwdThruster.render();
		    Game.revThruster.x = this.x;
		    Game.revThruster.y = this.y;
		    Game.revThruster.angle = this.angle;
		    Game.revThruster.render();

		    if (Game.loaded)
		    	Game.thrustersSound.stop();
		    Game.thrustersPlaying = false;
		    Game.hyperCounter = 0;
		  }
	  
	  public void stopShip() {

		    Game.ship.active = false;
		    Game.shipCounter = Game.SCRAP_COUNT;
		    if (Game.shipsLeft > 0)
		    	Game.shipsLeft--;
		    if (Game.loaded)
		    	Game.thrustersSound.stop();
		    Game.thrustersPlaying = false;
		  }

		  public void updateShip() {

		    double dx, dy, speed;

		    if (!Game.playing)
		      return;

		    // Rotate the ship if left or right cursor key is down.

		    if (Game.left) {
		      Game.ship.angle += Game.SHIP_ANGLE_STEP;
		      if (Game.ship.angle > 2 * Math.PI)
		        Game.ship.angle -= 2 * Math.PI;
		    }
		    if (Game.right) {
		      Game.ship.angle -= Game.SHIP_ANGLE_STEP;
		      if (Game.ship.angle < 0)
		        Game.ship.angle += 2 * Math.PI;
		    }

		    // Fire thrusters if up or down cursor key is down.

		    dx = Game.SHIP_SPEED_STEP * -Math.sin(Game.ship.angle);
		    dy = Game.SHIP_SPEED_STEP *  Math.cos(Game.ship.angle);
		    if (Game.up) {
		      Game.ship.deltaX += dx;
		      Game.ship.deltaY += dy;
		    }
		    if (Game.down) {
		        Game.ship.deltaX -= dx;
		        Game.ship.deltaY -= dy;
		    }

		    // Don't let ship go past the speed limit.

		    if (Game.up || Game.down) {
		      speed = Math.sqrt(Game.ship.deltaX * Game.ship.deltaX + Game.ship.deltaY * Game.ship.deltaY);
		      if (speed > Game.MAX_SHIP_SPEED) {
		        dx = Game.MAX_SHIP_SPEED * -Math.sin(Game.ship.angle);
		        dy = Game.MAX_SHIP_SPEED *  Math.cos(Game.ship.angle);
		        if (Game.up)
		          Game.ship.deltaX = dx;
		        else
		          Game.ship.deltaX = -dx;
		        if (Game.up)
		          Game.ship.deltaY = dy;
		        else
		          Game.ship.deltaY = -dy;
		      }
		    }

		    // Move the ship. If it is currently in hyperspace, advance the countdown.

		    if (Game.ship.active) {
		      Game.ship.advance();
		      Game.ship.render();
		      if (Game.hyperCounter > 0)
		    	  Game.hyperCounter--;

		      // Update the thruster sprites to match the ship sprite.

		      Game.fwdThruster.x = Game.ship.x;
		      Game.fwdThruster.y = Game.ship.y;
		      Game.fwdThruster.angle = Game.ship.angle;
		      Game.fwdThruster.render();
		      Game.revThruster.x = Game.ship.x;
		      Game.revThruster.y = Game.ship.y;
		      Game.revThruster.angle = Game.ship.angle;
		      Game.revThruster.render();
		    }

		    // Ship is exploding, advance the countdown or create a new ship if it is
		    // done exploding. The new ship is added as though it were in hyperspace.
		    // (This gives the player time to move the ship if it is in imminent
		    // danger.) If that was the last ship, end the game.

		    else
		      if (--Game.shipCounter <= 0)
		        if (Game.shipsLeft > 0) {
		          initShip();
		          Game.hyperCounter = Game.HYPER_COUNT;
		        }
		        else
		        	Game.endGame();
		  }

}
