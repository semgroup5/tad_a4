package asteroids;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

public class keyHandler extends Sprite implements KeyListener {
	
	public keyHandler () {
		super();
		
	}
	
	  public void keyPressed(KeyEvent e) {

		    char c;

		    // Check if any cursor keys have been pressed and set flags.

		    if (e.getKeyCode() == KeyEvent.VK_LEFT)
		      Game.left = true;
		    if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		    	Game.right = true;
		    if (e.getKeyCode() == KeyEvent.VK_UP)
		    	Game.up = true;
		    if (e.getKeyCode() == KeyEvent.VK_DOWN)
		    	Game.down = true;

		    if ((Game.up || Game.down) && Game.ship.active && !Game.thrustersPlaying) {
		      if (Game.sound && !Game.paused)
		        Game.thrustersSound.loop();
		      Game.thrustersPlaying = true;
		    }

		    // Spacebar: fire a photon and start its counter.

		    if (e.getKeyChar() == ' ' && Game.ship.active) {
		      if (Game.sound & !Game.paused)
		    	  Game.fireSound.play();
		      Game.photonTime = System.currentTimeMillis();
		      Game.photonIndex++;
		      if (Game.photonIndex >= Game.MAX_SHOTS)
		    	  Game.photonIndex = 0;
		      Game.photons[Game.photonIndex].active = true;
		      Game.photons[Game.photonIndex].x = Game.ship.x;
		      Game.photons[Game.photonIndex].y = Game.ship.y;
		      Game.photons[Game.photonIndex].deltaX = 2 * Game.MAX_ROCK_SPEED * -Math.sin(Game.ship.angle);
		      Game.photons[Game.photonIndex].deltaY = 2 * Game.MAX_ROCK_SPEED *  Math.cos(Game.ship.angle);
		    }

		    // Allow upper or lower case characters for remaining keys.

		    c = Character.toLowerCase(e.getKeyChar());

		    // 'H' key: warp ship into hyperspace by moving to a random location and
		    // starting counter.

		    if (c == 'h' && Game.ship.active && Game.hyperCounter <= 0) {
		      Game.ship.x = Math.random() * Sprite.width;
		      Game.ship.y = Math.random() * Sprite.height;
		      Game.hyperCounter = Game.HYPER_COUNT;
		      if (Game.sound & !Game.paused)
		    	  Game.warpSound.play();
		    }

		    // 'P' key: toggle pause mode and start or stop any active looping sound
		    // clips.

		    if (c == 'p') {
		      if (Game.paused) {
		        if (Game.sound && Game.misslePlaying)
		        	Game.missleSound.loop();
		        if (Game.sound && Game.saucerPlaying)
		        	Game.saucerSound.loop();
		        if (Game.sound && Game.thrustersPlaying)
		          Game.thrustersSound.loop();
		      }
		      else {
		        if (Game.misslePlaying)
		        	Game.missleSound.stop();
		        if (Game.saucerPlaying)
		        	Game.saucerSound.stop();
		        if (Game.thrustersPlaying)
		          Game.thrustersSound.stop();
		      }
		      Game.paused = !Game.paused;
		    }

		    // 'M' key: toggle sound on or off and stop any looping sound clips.

		    if (c == 'm' && Game.loaded) {
		      if (Game.sound) {
		    	  Game.crashSound.stop();
		    	  Game.explosionSound.stop();
		    	  Game.fireSound.stop();
		    	  Game.missleSound.stop();
		    	  Game.saucerSound.stop();
		        Game.thrustersSound.stop();
		        Game.warpSound.stop();
		      }
		      else {
		        if (Game.misslePlaying && !Game.paused)
		        	Game.missleSound.loop();
		        if (Game.saucerPlaying && !Game.paused)
		        	Game.saucerSound.loop();
		        if (Game.thrustersPlaying && !Game.paused)
		          Game.thrustersSound.loop();
		      }
		      Game.sound = !Game.sound;
		    }

		    // 'D' key: toggle graphics detail on or off.

		    if (c == 'd')
		    	Game.detail = !Game.detail;

		    // 'S' key: start the game, if not already in progress.

		    if (c == 's' && Game.loaded && !Game.playing)
		      Game.initGame();

		    // 'HOME' key: jump to web site (undocumented).

		    if (e.getKeyCode() == KeyEvent.VK_HOME)
		      try {
		        new Game().getAppletContext().showDocument(new URL(Game.copyLink));
		      }
		      catch (Exception excp) {}
		  }

		  public void keyReleased(KeyEvent e) {

		    // Check if any cursor keys where released and set flags.

		    if (e.getKeyCode() == KeyEvent.VK_LEFT)
		      Game.left = false;
		    if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		    	Game.right = false;
		    if (e.getKeyCode() == KeyEvent.VK_UP)
		    	Game.up = false;
		    if (e.getKeyCode() == KeyEvent.VK_DOWN)
		    	Game.down = false;

		    if (!Game.up && !Game.down && Game.thrustersPlaying) {
		      Game.thrustersSound.stop();
		      Game.thrustersPlaying = false;
		    }
		  }

		  public void keyTyped(KeyEvent e) {}
	
}
