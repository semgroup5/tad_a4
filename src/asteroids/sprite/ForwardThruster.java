package asteroids.sprite;

/**
 * Created by jpp on 19/03/16.
 */
public class ForwardThruster extends SpriteDecorator {
    @Override
    public void makeShape() {
        base.shape.addPoint(0, 12);
        base.shape.addPoint(-3, 16);
        base.shape.addPoint(0, 26);
        base.shape.addPoint(3, 16);
    }
}
