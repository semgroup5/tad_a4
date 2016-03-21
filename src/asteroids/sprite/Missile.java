package asteroids.sprite;

/**
 * Created by jpp on 19/03/16.
 */
public class Missile extends SpriteDecorator {
    @Override
    public void makeShape() {
        base.shape.addPoint(0, -4);
        base.shape.addPoint(1, -3);
        base.shape.addPoint(1, 3);
        base.shape.addPoint(2, 4);
        base.shape.addPoint(-2, 4);
        base.shape.addPoint(-1, 3);
        base.shape.addPoint(-1, -3);
    }
}
