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

    // Animación
    private Animation animacion;    // Caminando
    private float timerAnimacion;   // tiempo para calcular el frame

    public Enemigo(Texture textura) {


        // Lee la textura como región
        TextureRegion texturaCompleta = new TextureRegion(textura);

        // La divide en 4 frames de 32x64 (ver marioSprite.png)
        TextureRegion[][] texturaEnemigo = texturaCompleta.split(62,141);

        // Crea la animación con tiempo de 0.25 segundos entre frames.
        animacion = new Animation(0.25f, texturaEnemigo[0][3],
                texturaEnemigo[0][2], texturaEnemigo[0][1]);

        // Animación infinita
        animacion.setPlayMode(Animation.PlayMode.LOOP);

        // Inicia el timer que contará tiempo para saber qué frame se dibuja
        timerAnimacion = 0;

        // Crea el sprite con el personaje quieto (idle)
        sprite = new Sprite(texturaEnemigo[0][0]);    // QUIETO
        sprite.setPosition(200,200);    // Posición inicial

    }

    public Enemigo(Texture textura, float x, float y) {
        this(textura);
        // Lee la textura como región
        TextureRegion texturaCompleta = new TextureRegion(textura);

        // La divide en 4 frames de 32x64 (ver marioSprite.png)
        TextureRegion[][] texturaEnemigo = texturaCompleta.split(62,141);

        // Crea la animación con tiempo de 0.25 segundos entre frames.
        animacion = new Animation(0.25f, texturaEnemigo[0][3],
                texturaEnemigo[0][2], texturaEnemigo[0][1]);

        // Animación infinita
        animacion.setPlayMode(Animation.PlayMode.LOOP);

        // Inicia el timer que contará tiempo para saber qué frame se dibuja
        timerAnimacion = 0;

        // Crea el sprite con el personaje quieto (idle)
        sprite = new Sprite(texturaEnemigo[0][0]);    // QUIETO
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
                timerAnimacion += Gdx.graphics.getDeltaTime();
                TextureRegion region = animacion.getKeyFrame(timerAnimacion);
                    if (!region.isFlipX()) {
                        region.flip(true,false);
                    }


            case MOV_IZQUIERDA:
                timerAnimacion += Gdx.graphics.getDeltaTime();
                region = animacion.getKeyFrame(timerAnimacion);

                batch.draw(region,sprite.getX(),sprite.getY());
                break;
            case MOV_ARRIBA:
            case MOV_ABAJO:
            case QUIETO:
            case INICIANDO:
                 // Dibuja el sprite
                sprite.draw(batch);

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