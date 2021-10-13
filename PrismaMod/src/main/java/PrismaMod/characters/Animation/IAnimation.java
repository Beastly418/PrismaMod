package PrismaMod.characters.Animation;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface IAnimation {

    void render(SpriteBatch sb);
    void update(float dt);
    void dispose();
}