package asteroids;

public class Photons extends Sprite {
	
	public Photons() {
		super();
	}
	
	  public void initPhotons() {

		    int i;

		    for (i = 0; i < Game.MAX_SHOTS; i++)
		      Game.photons[i].active = false;
		    Game.photonIndex = 0;
		  }

		  public void updatePhotons() {

		    int i;

		    // Move any active photons. Stop it when its counter has expired.

		    for (i = 0; i < Game.MAX_SHOTS; i++)
		      if (Game.photons[i].active) {
		        if (!Game.photons[i].advance())
		          Game.photons[i].render();
		        else
		          Game.photons[i].active = false;
		      }
		  }

}
