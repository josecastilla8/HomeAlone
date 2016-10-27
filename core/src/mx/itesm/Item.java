package mx.itesm;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by daniela on 26/10/16.
 */
public class Item {

    private Sprite sprite;
    private EstadoMovimiento estadoMovimiento=EstadoMovimiento.INICIANDO;


        public Item(Texture textura) {
            sprite = new Sprite(textura);

        }

        public float getX(){
            return this.sprite.getX();
        }

        public float getY(){
            return this.sprite.getY();
        }

        public void setX(float x){
            this.sprite.setX(x);
        }

        public Item(Texture textura, float x, float y) {
            this(textura);
            sprite.setPosition(x, y);

        }

        public void draw(SpriteBatch batch) {
            sprite.draw(batch);

        }

        public void render(SpriteBatch batch) {
        // Dibuja el personaje dependiendo del estadoMovimiento
        switch (estadoMovimiento) {
            case INICIANDO:
                sprite.draw(batch); // Dibuja el sprite
                break;
        }
    }

    public EstadoMovimiento getEstadoMovimiento() {
        return estadoMovimiento;
    }

    // Modificador de estadoMovimiento
    public void setEstadoMovimiento(EstadoMovimiento estadoMovimiento) {
        this.estadoMovimiento = estadoMovimiento;
    }


    public enum EstadoMovimiento {
        INICIANDO,

    }


}
