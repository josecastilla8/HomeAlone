package mx.itesm;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Javier on 22/09/2016.
 */
public class pantallaMapa implements Screen {

    //Declaramos la camara
    private OrthographicCamera camara;
    private Viewport vista;


    private Texture texturaJugador;
    private Personaje jugador;


    //SpriteBatch sirve para administrar los trazos
    private SpriteBatch batch;
    private final Juego juego;
    //private AssetManager manager;
    // *****************
    //MAPA
    private TiledMap mapa; //Informacion del mapa en memoria
    private OrthogonalTiledMapRenderer rendererMapa; //Objeto para dibujar el mapa
    private Stage escena;
    //******************
    //Personaje
    private Texture texturaPersonaje;
    private Personaje dude;


    public pantallaMapa(Juego juego) {
        this.juego= juego;
    }

    @Override
    public void show() {
        inicializarCamara();
        crearEscena();
        cargarMapa(); //nuevo
        crearPersonaje();
        escena = new Stage();
        Gdx.input.setInputProcessor(escena);
        //Quien procesa los eventos

    }

    private void crearPersonaje() {
        AssetManager manager= new AssetManager();

    }

    private void crearEscena() {
        batch= new SpriteBatch();
    }

    private void cargarMapa() {
        //manager= new AssetManager();
        AssetManager manager= juego.getAssetManager();

        //Cargar mapa
        manager.setLoader(TiledMap.class,
                new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("mapa3.tmx", TiledMap.class);
        //Cargar personaje
        manager.load("DUDE_camina.png", Texture.class);
        manager.finishLoading(); //Bloquea hasta que carga el mapa
        mapa= manager.get("mapa3.tmx");
        texturaJugador= manager.get("DUDE_camina.png");


        //Crea el objeto que dibujara el mapa
        rendererMapa = new OrthogonalTiledMapRenderer(mapa, batch);
        rendererMapa.setView(camara);

        //Dude
        jugador = new Personaje(texturaJugador);
    }

    private void inicializarCamara() {
        camara= new OrthographicCamera(1280, 800);
        camara.position.set(1280/2, 800/2, 0);
        camara.update();
        vista= new StretchViewport(1280, 800, camara);


    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined);

        rendererMapa.setView(camara);
        rendererMapa.render();
        batch.begin();
        jugador.render(batch);
        batch.end();
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

    }

}