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


/**
 * Created by daniela on 20/10/16.
 */
public class Personaje {

    //Velocidad
    public static final float VELOCIDAD_X = 4;      // Velocidad horizontal
    public static final float VELOCIDAD_UP = 4;    // Vel. vertical (arriba)
    public static final float VELOCIDAD_DOWN=4;   //Vel. vertical(abajo)


    //Personaje
    private Sprite sprite;  // Sprite cuando no se mueve (QUIETO)
    private Sound sonidoItem;     // Efecto cuando colecta un item

    // Animación
    private Animation animacion;    // Caminando
    private float timerAnimacion;   // tiempo para calcular el frame

    private EstadoMovimiento estadoMovimiento=EstadoMovimiento.INICIANDO;


    /*
    Constructor del personaje, recibe una imagen con varios frames, (ver imagen marioSprite.png 128x64, cada tile 32x64)
     */

    public Personaje(Texture textura) {
        //this.sonidoItem = sonidoItem;

        // Lee la textura como región
        TextureRegion texturaCompleta = new TextureRegion(textura);

        // La divide en 4 frames de 32x64 (ver marioSprite.png)
        TextureRegion[][] texturaJugador = texturaCompleta.split(53,143);

        // Crea la animación con tiempo de 0.25 segundos entre frames.
        animacion = new Animation(0.25f,texturaJugador[0][4], texturaJugador[0][3],
                texturaJugador[0][2], texturaJugador[0][1]);

        // Animación infinita
        animacion.setPlayMode(Animation.PlayMode.LOOP);

        // Inicia el timer que contará tiempo para saber qué frame se dibuja
        timerAnimacion = 0;

        // Crea el sprite con el personaje quieto (idle)
        sprite = new Sprite(texturaJugador[0][0]);    // QUIETO
        sprite.setPosition(300,400);    // Posición inicial
    }

    // Dibuja el personaje
    public void render(SpriteBatch batch) {
        // Dibuja el personaje dependiendo del estadoMovimiento
        switch (estadoMovimiento) {
            case MOV_DERECHA:
            case MOV_IZQUIERDA:
                timerAnimacion += Gdx.graphics.getDeltaTime();
                TextureRegion region = animacion.getKeyFrame(timerAnimacion);
                if (estadoMovimiento==EstadoMovimiento.MOV_IZQUIERDA) {
                    if (!region.isFlipX()) {
                        region.flip(true,false);
                    }
                } else {
                    if (region.isFlipX()) {
                        region.flip(true,false);
                    }
                }
                batch.draw(region,sprite.getX(),sprite.getY());
                break;
            case MOV_ARRIBA:
            case MOV_ABAJO:
            case QUIETO:
            case INICIANDO:
                sprite.draw(batch); // Dibuja el sprite
                break;
        }
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

    private void recolectarObjetos(TiledMap mapa) {
        // Revisar si está sobre una moneda (pies)
        TiledMapTileLayer capa = (TiledMapTileLayer)mapa.getLayers().get(0);
        int x = (int)(sprite.getX()/32);
        int y = (int)(sprite.getY()/32);
        TiledMapTileLayer.Cell celda = capa.getCell(x,y);
        if (celda!=null ) {
            Object tipo = (String)celda.getTile().getProperties().get("tipo");
            if ( "moneda".equals(tipo) ) {
                capa.setCell(x,y,null);    // Cuadro azul en lugar de la moneda
                capa.setCell(x,y,capa.getCell(0,4));
            }
        }
    }

    private void moverHorizontal(TiledMap mapa) {
        // Obtiene la primer capa del mapa (en este caso es la única)
        TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getLayers().get(0);
        // Ejecutar movimiento horizontal
        float nuevaX = sprite.getX();
        // ¿Quiere ir a la Derecha?
        if ( estadoMovimiento==EstadoMovimiento.MOV_DERECHA) {
            // Obtiene el bloque del lado derecho. Asigna null si puede pasar.
            int x = (int) ((sprite.getX() + 32) / 32);   // Convierte coordenadas del mundo en coordenadas del mapa
            int y = (int) (sprite.getY() / 32);
            TiledMapTileLayer.Cell celdaDerecha = capa.getCell(x, y);
            if (celdaDerecha != null) {
                Object tipo = (String) celdaDerecha.getTile().getProperties().get("tipo");
                if (!"paredes".equals(tipo)) {
                    celdaDerecha = null;// Puede pasar
                }
            }
            if ( celdaDerecha==null) {
                // Ejecutar movimiento horizontal
                nuevaX += VELOCIDAD_X;
                // Prueba que no salga del mundo por la derecha
                if (nuevaX <= pantallaMapa.ANCHO_MAPA - sprite.getWidth()) {
                    sprite.setX(nuevaX);
                    //probarCaida(mapa);
                }
            }
        }
        // ¿Quiere ir a la izquierda?
        if ( estadoMovimiento==EstadoMovimiento.MOV_IZQUIERDA) {
            int xIzq = (int) ((sprite.getX()) / 32);
            int y = (int) (sprite.getY() / 32);
            // Obtiene el bloque del lado izquierdo. Asigna null si puede pasar.
            TiledMapTileLayer.Cell celdaIzquierda = capa.getCell(xIzq, y);
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
            }
        }
    }


    // Accesor de estadoMovimiento
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
