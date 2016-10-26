package mx.itesm;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by daniela on 26/10/16.
 */
public class Item {

        private Sprite sprite;

        public Item(Texture textura) {
            sprite = new Sprite(textura);
        }

        public Item(Texture textura, float x, float y) {
            this(textura);
            sprite.setPosition(x, y);

        }

        public void draw(SpriteBatch batch) {
            sprite.draw(batch);

        }

}
