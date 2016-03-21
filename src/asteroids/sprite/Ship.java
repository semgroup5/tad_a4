package asteroids.sprite;

/**
 * Created by jpp on 19/03/16.
 */
public class Ship extends SpriteDecorator{
    @Override
    public void makeShape() {
        base.shape.addPoint(0, -10);
        base.shape.addPoint(7, 10);
        base.shape.addPoint(-7, 10);
    }
}
