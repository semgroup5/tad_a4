package asteroids.sprite;

import java.awt.*;

/**
 * Created by jpp on 19/03/16.
 */
public class Asteroid extends SpriteDecorator{
    @Override
    public void makeShape() {
        int i, j;
        int s;
        double theta, r;
        int x, y;
        int minRockSides, maxRockSides, minRockSpin, maxRockSpin;


        base.shape = new Polygon();
        s = minRockSides + (int) (Math.random() * (MAX_ROCK_SIDES - minRockSides));
        for (j = 0; j < s; j ++) {
            theta = 2 * Math.PI / s * j;
            r = minRockSides+ (int) (Math.random() * (maxRockSides -));
            x = (int) -Math.round(r * Math.sin(theta));
            y = (int)  Math.round(r * Math.cos(theta));
            base.shape.addPoint(x, y);
        }
        base.active = true;
        base.angle = 0.0;
        base.deltaAngle = Math.random() * 2 * ma - MAX_ROCK_SPIN;
    }
}
