package asteroids.sprite;

/**
 * Created by jpp on 19/03/16.
 */
public abstract class SpriteDecorator extends Sprite {
    public Sprite base;

    public SpriteDecorator() {
        super();
        makeShape();
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

    public abstract void makeShape();
}
