package mx.itesm;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Boton {
    private Sprite sprite;

    public Boton(Texture textura){
        sprite = new Sprite(textura);
        sprite.setAlpha(1f);

    }

    public void render(SpriteBatch batch){
        sprite.draw(batch);
    }

    public void setPosicion(float x, float y) {
        sprite.setPosition(x, y);
    }

    public Sprite getSprite() {
        return sprite;
    }


    public void setTextura(Texture textura) {
        this.sprite = new Sprite(textura);
        sprite.setAlpha(1f);
    }
}
