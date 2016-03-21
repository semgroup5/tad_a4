package asteroids.sprite;

import java.applet.AudioClip;

/**
 * Created by jpp on 19/03/16.
 */
public class Ufo extends SpriteDecorator{
    public int counter = 0;
    public int passesLeft = 0;
    public boolean playing;
    public AudioClip sound;
    public boolean loaded;

    public void makeShape(){
        base.shape.addPoint(-15, 0);
        base.shape.addPoint(-10, -5);
        base.shape.addPoint(-5, -5);
        base.shape.addPoint(-5, -8);
        base.shape.addPoint(5, -8);
        base.shape.addPoint(5, -5);
        base.shape.addPoint(10, -5);
        base.shape.addPoint(15, 0);
        base.shape.addPoint(10, 5);
        base.shape.addPoint(-10, 5);
    }

    public void stop()
    {
        this.active = false;
        counter = 0;
        passesLeft = 0;
        if (loaded)
            sound.stop();
        playing = false;
    }

    public void init(boolean sound, int maxRockSpeed)
    {
        double angle, speed;

        // Randomly set flying saucer at left or right edge of the screen.

        this.active = true;
        this.x = -Sprite.width / 2;
        this.y = Math.random() * 2 * Sprite.height - Sprite.height;
        angle = Math.random() * Math.PI / 4 - Math.PI / 2;
        speed = maxRockSpeed / 2 + Math.random() * (maxRockSpeed / 2);
        this.deltaX = speed * -Math.sin(angle);
        this.deltaY = speed *  Math.cos(angle);
        if (Math.random() < 0.5) {
            this.x = Sprite.width / 2;
            this.deltaX = -this.deltaX;
        }
        if (this.y > 0)
        {
            this.deltaY = -this.deltaY;
        }

        this.render();
        playing = true;
        if (sound)
            this.sound.loop();
        counter = (int) Math.abs(Sprite.width / this.deltaX);
    }
}
