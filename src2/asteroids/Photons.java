package asteroids;

public class Photons extends AsteroidsSprite {
	
	public Photons() {
		super();
	}
	
	  public void initPhotons() {

		    int i;

		    for (i = 0; i < Asteroids.MAX_SHOTS; i++)
		      Asteroids.photons[i].active = false;
		    Asteroids.photonIndex = 0;
		  }

		  public void updatePhotons() {

		    int i;

		    // Move any active photons. Stop it when its counter has expired.

		    for (i = 0; i < Asteroids.MAX_SHOTS; i++)
		      if (Asteroids.photons[i].active) {
		        if (!Asteroids.photons[i].advance())
		          Asteroids.photons[i].render();
		        else
		          Asteroids.photons[i].active = false;
		      }
		  }

}
