package asteroids.sprite;

/**
 * Created by jpp on 19/03/16.
 */
public class ReverseThruster extends SpriteDecorator {
    @Override
    public void makeShape() {
        base.shape.addPoint(-2, 12);
        base.shape.addPoint(-4, 14);
        base.shape.addPoint(-2, 20);
        base.shape.addPoint(0, 14);
        base.shape.addPoint(2, 12);
        base.shape.addPoint(4, 14);
        base.shape.addPoint(2, 20);
        base.shape.addPoint(0, 14);
    }
}
