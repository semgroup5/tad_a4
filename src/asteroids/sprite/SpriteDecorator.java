package asteroids.sprite;

/**
 * Created by jpp on 19/03/16.
 */
public class SpriteDecorator extends Sprite {
    public Sprite base;

    public SpriteDecorator() {
        super();
    }

    @Override
    public boolean advance() {
        return base.advance();
    }

    @Override
    public void render() {
        base.render();
    }

    @Override
    public boolean isColliding(Sprite s) {
        return super.isColliding(s);
    }
}
