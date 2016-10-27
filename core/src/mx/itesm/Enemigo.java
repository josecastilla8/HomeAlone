package mx.itesm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import java.util.Random;


/**
 * Created by Javier on 26/10/2016.
 */
public class Enemigo {

    private Sprite sprite;
    private EstadoMovimiento estadoMovimiento=EstadoMovimiento.INICIANDO;

    public Enemigo(Texture textura) {

        sprite = new Sprite(textura);
    }

    public Enemigo(Texture textura, float x, float y) {
        this(textura);
        sprite.setPosition(x, y);
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);

    }

    public void setX(float x){
        this.sprite.setX(x);
    }

    public void setY(float y){
        this.sprite.setY(y);
    }

    public float getX(){
        return this.sprite.getX();
    }

    public float getY(){
        return this.sprite.getY();
    }
    public void render(SpriteBatch batch) {
        // Dibuja el personaje dependiendo del estadoMovimiento
        switch (estadoMovimiento) {
            case MOV_DERECHA:
            case MOV_IZQUIERDA:
            case MOV_ARRIBA:
            case MOV_ABAJO:
            case QUIETO:
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
        QUIETO,
        MOV_IZQUIERDA,
        MOV_DERECHA,
        MOV_ARRIBA,
        MOV_ABAJO
    }

}