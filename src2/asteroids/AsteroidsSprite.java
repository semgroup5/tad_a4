package asteroids;

/******************************************************************************
Asteroids, Version 1.3

Copyright 1998-2001 by Mike Hall.
Please see http://www.brainjar.com for terms of use.

Revision History:

1.01, 12/18/1999: Increased number of active photons allowed.
                  Improved explosions for more realism.
                  Added progress bar for loading of sound clips.
1.2,  12/23/1999: Increased frame rate for smoother animation.
                  Modified code to calculate game object speeds and timer
                  counters based on the frame rate so they will remain
                  constant.
                  Improved speed limit checking for ship.
                  Removed wrapping of photons around screen and set a fixed
                  firing rate.
                  Added sprites for ship's thrusters.
1.3,  01/25/2001: Updated to JDK 1.1.8.

Usage:

<applet code="Asteroids.class" width=w height=h></applet>

Keyboard Controls:

S            - Start Game    P           - Pause Game
Cursor Left  - Rotate Left   Cursor Up   - Fire Thrusters
Cursor Right - Rotate Right  Cursor Down - Fire Retro Thrusters
Spacebar     - Fire Cannon   H           - Hyperspace
M            - Toggle Sound  D           - Toggle Graphics Detail

******************************************************************************/

import java.awt.Polygon;

class AsteroidsSprite {
	

		  // Fields:

		  static int width;          // Dimensions of the graphics area.
		  static int height;

		  Polygon shape;             // Base sprite shape, centered at the origin (0,0).
		  boolean active;            // Active flag.
		  double  angle;             // Current angle of rotation.
		  double  deltaAngle;        // Amount to change the rotation angle.
		  double  x, y;              // Current position on screen.
		  double  deltaX, deltaY;    // Amount to change the screen position.
		  Polygon sprite;            // Final location and shape of sprite after
		                             // applying rotation and translation to get screen
		                             // position. Used for drawing on the screen and in
		                             // detecting collisions.

		  // Constructors:

		  public AsteroidsSprite() {

		    this.shape = new Polygon();
		    this.active = false;
		    this.angle = 0.0;
		    this.deltaAngle = 0.0;
		    this.x = 0.0;
		    this.y = 0.0;
		    this.deltaX = 0.0;
		    this.deltaY = 0.0;
		    this.sprite = new Polygon();
		  }

		  // Methods:

		  public boolean advance() {

		    boolean wrapped;

		    // Update the rotation and position of the sprite based on the delta
		    // values. If the sprite moves off the edge of the screen, it is wrapped
		    // around to the other side and TRUE is returnd.

		    this.angle += this.deltaAngle;
		    if (this.angle < 0)
		      this.angle += 2 * Math.PI;
		    if (this.angle > 2 * Math.PI)
		      this.angle -= 2 * Math.PI;
		    wrapped = false;
		    this.x += this.deltaX;
		    if (this.x < -width / 2) {
		      this.x += width;
		      wrapped = true;
		    }
		    if (this.x > width / 2) {
		      this.x -= width;
		      wrapped = true;
		    }
		    this.y -= this.deltaY;
		    if (this.y < -height / 2) {
		      this.y += height;
		      wrapped = true;
		    }
		    if (this.y > height / 2) {
		      this.y -= height;
		      wrapped = true;
		    }

		    return wrapped;
		  }

		  public void render() {

		    int i;

		    // Render the sprite's shape and location by rotating it's base shape and
		    // moving it to it's proper screen position.

		    this.sprite = new Polygon();
		    for (i = 0; i < this.shape.npoints; i++)
		      this.sprite.addPoint((int) Math.round(this.shape.xpoints[i] * Math.cos(this.angle) + this.shape.ypoints[i] * Math.sin(this.angle)) + (int) Math.round(this.x) + width / 2,
		                           (int) Math.round(this.shape.ypoints[i] * Math.cos(this.angle) - this.shape.xpoints[i] * Math.sin(this.angle)) + (int) Math.round(this.y) + height / 2);
		  }

		  public boolean isColliding(AsteroidsSprite s) {

		    int i;

		    // Determine if one sprite overlaps with another, i.e., if any vertice
		    // of one sprite lands inside the other.

		    for (i = 0; i < s.sprite.npoints; i++)
		      if (this.sprite.contains(s.sprite.xpoints[i], s.sprite.ypoints[i]))
		        return true;
		    for (i = 0; i < this.sprite.npoints; i++)
		      if (s.sprite.contains(this.sprite.xpoints[i], this.sprite.ypoints[i]))
		        return true;
		    return false;
		  }
		}
