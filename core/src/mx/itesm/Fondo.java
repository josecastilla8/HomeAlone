package mx.itesm;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/**
 * Created by JoseCastilla on 08/09/2016.
 */
public class Fondo {
    private Sprite sprite;
    public Fondo(Texture textura){
        sprite = new Sprite(textura);
        sprite.setAlpha(1f);
        //fondo1 con capas

    }

    public void setTextura(Texture textura){
        sprite = new Sprite(textura);
        sprite.setAlpha(1f);
    }

    public void render(SpriteBatch batch){
        sprite.draw(batch);
    }

    public Sprite getSprite() {
        return sprite;
    }
}
