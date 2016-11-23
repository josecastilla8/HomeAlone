package mx.itesm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * Created by daniela on 17/11/16.
 */
public class EnemigoB {

    public static final float VELOCIDAD_X = 4;      // Velocidad horizontal

    private Sprite sprite;
    private EstadoMovimiento estadoMovimiento=EstadoMovimiento.INICIANDO;

    // Animación
    private Animation animacion;    // Caminando
    private float timerAnimacion;   // tiempo para calcular el frame

    public EnemigoB(Texture textura) {


        // Lee la textura como región
        TextureRegion texturaCompleta = new TextureRegion(textura);

        // La divide en 4 frames de 32x64 (ver marioSprite.png)
        TextureRegion[][] texturaEnemigo = texturaCompleta.split(54,44);

        // Crea la animación con tiempo de 0.25 segundos entre frames.
        animacion = new Animation(0.25f, texturaEnemigo[0][4],
                texturaEnemigo[0][3], texturaEnemigo[0][2],texturaEnemigo[0][1]);

        // Animación infinita
        animacion.setPlayMode(Animation.PlayMode.LOOP);

        // Inicia el timer que contará tiempo para saber qué frame se dibuja
        timerAnimacion = 0;

        // Crea el sprite con el personaje quieto (idle)
        sprite = new Sprite(texturaEnemigo[0][0]);    // QUIETO
        sprite.setPosition(800,200);    // Posición inicial

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

    public void actualizar(TiledMap mapa) {
        switch (estadoMovimiento) {
            case MOV_DERECHA:
            case MOV_IZQUIERDA:
                moverHorizontal(mapa);

                break;
            case MOV_ARRIBA:
            case MOV_ABAJO:
                moverVertical(mapa);

                break;
        }
    }

    private void moverHorizontal(TiledMap mapa) {

        // Obtiene la primer capa del mapa (en este caso es la única)
        TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getLayers().get(6);
        // Ejecutar movimiento horizontal
        float nuevaX = sprite.getX();
        // ¿Quiere ir a la Derecha?
        if ( estadoMovimiento==EstadoMovimiento.MOV_DERECHA) {

            // Obtiene el bloque del lado derecho. Asigna null si puede pasar.
            int x = (int) ((sprite.getX() + 32) / 32);   // Convierte coordenadas del mundo en coordenadas del mapa
            int y = (int) (sprite.getY() / 32);
            TiledMapTileLayer.Cell celdaDerecha = capa.getCell(x, y);
            //Gdx.app.log("Mover horizontal ","Calculando celda " + x + "," + y);
            if (celdaDerecha != null) {

                Object tipo = celdaDerecha.getTile().getProperties().get("tipo");
                //Gdx.app.log("Mover horizontal ","Hay celda derecha " + tipo);
                if (!"paredes".equals(tipo)) {
                    celdaDerecha = null;// Puede pasar
                }
            }
            if ( celdaDerecha==null) {
                // Ejecutar movimiento horizontal
                nuevaX += VELOCIDAD_X;
                // Prueba que no salga del mundo por la derecha
                if (nuevaX <= PantallaNivelUno.ANCHO_MAPA - sprite.getWidth()) {
                    sprite.setX(nuevaX);
                    //probarCaida(mapa);
                }
            }else{
                //Gdx.app.log("Mover horizonta","No se puede mover");
            }
        }
        // ¿Quiere ir a la izquierda?
        if (estadoMovimiento==EstadoMovimiento.MOV_IZQUIERDA) {
            int xIzq = (int) ((sprite.getX()) / 32);
            int y = (int) (sprite.getY() / 32);
            // Obtiene el bloque del lado izquierdo. Asigna null si puede pasar.
            TiledMapTileLayer.Cell celdaIzquierda = capa.getCell(xIzq, y);
            //Gdx.app.log("Mover izquierda ","Calculando celda " + xIzq + "," + y);
            if (celdaIzquierda != null) {
                Object tipo = (String) celdaIzquierda.getTile().getProperties().get("tipo");
                if (!"paredes".equals(tipo)) {
                    celdaIzquierda = null;  // Puede pasar
                }
            }
            if ( celdaIzquierda==null) {
                // Prueba que no salga del mundo por la izquierda
                nuevaX -= VELOCIDAD_X;
                if (nuevaX >= 0) {
                    sprite.setX(nuevaX);
                }
            }else{
                this.setEstadoMovimiento(EstadoMovimiento.MOV_ABAJO);
            }
        }
    }

    private void moverVertical(TiledMap mapa) {
        TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getLayers().get(6);
        float nuevaX = sprite.getY();
        if (estadoMovimiento==EstadoMovimiento.MOV_ARRIBA) {
            int xIzq = (int) ((sprite.getX()) / 32);
            int y = (int) (sprite.getY() / 32)+1;
            // Obtiene el bloque del lado izquierdo. Asigna null si puede pasar.
            TiledMapTileLayer.Cell celdaIzquierda = capa.getCell(xIzq, y);
            //Gdx.app.log("Mover izquierda ","Calculando celda " + xIzq + "," + y);
            if (celdaIzquierda != null) {
                Object tipo = (String) celdaIzquierda.getTile().getProperties().get("tipo");
                if (!"paredes".equals(tipo)) {
                    celdaIzquierda = null;  // Puede pasar
                }
            }
            if ( celdaIzquierda==null) {
                // Prueba que no salga del mundo por la izquierda
                nuevaX += VELOCIDAD_X;
                if (nuevaX >= 0) {
                    sprite.setY(nuevaX);
                }
            }else{
                this.setEstadoMovimiento(EstadoMovimiento.MOV_ARRIBA);
            }
        }

        if (estadoMovimiento==EstadoMovimiento.MOV_ABAJO) {
            int xIzq = (int) ((sprite.getX()) / 32);
            int y = (int) ((sprite.getY() / 32));
            // Obtiene el bloque del lado izquierdo. Asigna null si puede pasar.
            TiledMapTileLayer.Cell celdaIzquierda = capa.getCell(xIzq, y);
            //Gdx.app.log("Mover izquierda ","Calculando celda " + xIzq + "," + y);
            if (celdaIzquierda != null) {
                Object tipo = (String) celdaIzquierda.getTile().getProperties().get("tipo");
                if (!"paredes".equals(tipo)) {
                    celdaIzquierda = null;  // Puede pasar
                }
            }
            if ( celdaIzquierda==null) {
                // Prueba que no salga del mundo por la izquierda
                nuevaX -= VELOCIDAD_X;
                if (nuevaX >= 0) {
                    sprite.setY(nuevaX);
                }
            }
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
