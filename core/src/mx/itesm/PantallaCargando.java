package mx.itesm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by daniela on 08/11/16.
 */
public class PantallaCargando implements Screen {

    private Juego juego;

    //Camara y vista
    private OrthographicCamera camara;
    private Viewport vista;
    private SpriteBatch batch;

    // Texturas
    private Texture texturaCargando;
    private Sprite spriteCargando;

    private AssetManager assetManager;
    private int nivel;

    public PantallaCargando(Juego juego,int nivel){
        this.juego= juego;
        this.assetManager= juego.getAssetManager();
        this.nivel = nivel;
    }

    @Override
    public void show() {
        //Crea camara y vista
        camara= new OrthographicCamera(1280,800);
        camara.position.set(1280/2,800/2,0);
        camara.update();
        vista = new StretchViewport(1280,800,camara);
        batch = new SpriteBatch();

        // Cargar recursos de esta pantalla
        assetManager.load("Loading.png", Texture.class);
        assetManager.finishLoading();   // Se bloquea, pero es una sola imagen
        texturaCargando = assetManager.get("Loading.png");

        spriteCargando = new Sprite(texturaCargando);
        //spriteCargando.setPosition(1280/2-spriteCargando.getWidth()/2, 800/2-spriteCargando.getHeight()/2);

        // Ahora INICIA la carga los recursos de la siguiente pantalla
        cargarRecursos();
    }


    // Estos son los recursos de la pantalla siguiente (PantallaNivelUno)
    private void cargarRecursos() {
        assetManager.load("mapa4.tmx", TiledMap.class);
        assetManager.load("DUDE_camina.png", Texture.class);
        assetManager.load("Papa_camina.png", Texture.class);
        assetManager.load("Playera.png", Texture.class);
        assetManager.load("Mama_camina.png", Texture.class);
        assetManager.load("RedBull.png", Texture.class);
        assetManager.load("Bruno_camina.png", Texture.class);

        //Cargar musica
        assetManager.load("audio/cancionJuego.mp3", Music.class);
        Gdx.app.log("Cargar recursos", "Cargando mapas");

        // Carga música
        //assetManager.load("audio/marioBros.mp3", Music.class);
        //assetManager.load("audio/muereMario.mp3", Sound.class);
        //assetManager.load("audio/moneda.mp3", Sound.class);
        //assetManager.finishLoading();
    }

    @Override
    public void render(float delta) {
        // Revisar cómo va la carga de los recursos siguientes
        actualizarCarga();

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //spriteCargando.setRotation(spriteCargando.getRotation()+10);    // Animación

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        spriteCargando.draw(batch);
        batch.end();
    }

    // Revisa cómo va la carga de assets
    private void actualizarCarga() {

        if ( assetManager.update() ) {  // regresa true si ya terminó la carga
            switch (nivel){
                case 1:
                    juego.setScreen(new PantallaNivelUno(juego));
                    break;
                case 2:
                    juego.setScreen(new PantallaNivelDos(juego));
                    break;
                case 3:
                    juego.setScreen(new PantallaNivelTres(juego));
                    break;
            }

        } else {
            // Aún no termina, preguntar cómo va
            float avance = assetManager.getProgress();
        }
    }

    @Override
    public void resize(int width, int height) {
        vista.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        texturaCargando.dispose();
        assetManager.unload("Loading.png");
    }
}
