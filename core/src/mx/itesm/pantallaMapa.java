package mx.itesm;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.Random;

/**
 * Created by Javier on 22/09/2016.
 */
public class pantallaMapa implements Screen {

    public static final int ANCHO_MAPA = 1280;
    public static final int ANCHO_CAMARA = 1280;
    public static final int ALTO_CAMARA = 800;

    //Declaramos la camara
    private OrthographicCamera camara;
    private Viewport vista;

    // HUD. Los componentes en la pantalla que no se mueven
    private OrthographicCamera camaraHUD; // Cámara fija
    private StretchViewport vistaHUD;

    // Escena para HUD
    private Stage escena;

    //Declaramos al jugador
    private Texture texturaJugador;
    private Personaje jugador;

    // Pad
    private Touchpad pad;

    //SpriteBatch sirve para administrar los trazos
    private SpriteBatch batch;
    private final Juego juego;
    //private AssetManager manager;
    // *****************

    //MAPA
    private TiledMap mapa; //Informacion del mapa en memoria
    private OrthogonalTiledMapRenderer rendererMapa; //Objeto para dibujar el mapa
    //private Stage escena;
    //******************

    //Personaje
    private Texture texturaPersonaje;
    private Personaje dude;

    //Enemigo
    private Texture texturaEnemigoPapa;
    private Enemigo enemigoPapa;
    private Random randomX;
    private Random randomY;
    int rdmX;
    int rdmY;

    //Item Playera
    private Texture texturaItemPlayera;
    private Item itemPlayera;


    public pantallaMapa(Juego juego) {
        this.juego= juego;
    }

    @Override
    public void show() {
        inicializarCamara();
        crearEscena();
        cargarMapa(); //nuevo

        //crearPersonaje();
        escena = new Stage();
        crearPad();
        Gdx.input.setInputProcessor(escena);
        //Quien procesa los eventos

    }

    private void crearPad() {

        // Para cargar las texturas y convertirlas en Drawable
        Skin skin = new Skin();
        skin.add("touchBackground", new Texture("touchBackground.png"));
        skin.add("touchKnob", new Texture("touchKnob.png"));


        //Create camera
        //Create a touchpad skin

        //Create Drawable's from TouchPad skin


        //Create a Stage and add TouchPad

        //Create block sprite

        // Carcaterísticas del pad
        Touchpad.TouchpadStyle tpEstilo = new Touchpad.TouchpadStyle();
        tpEstilo.background = skin.getDrawable("touchBackground");
        tpEstilo.knob = skin.getDrawable("touchKnob");

        // Crea el pad, revisa la clase Touchpad para entender los parámetros
        pad = new Touchpad(20, tpEstilo);
        pad.setBounds(0, 0, 200, 200); // Posición y tamaño
        pad.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (jugador.getEstadoMovimiento() != Personaje.EstadoMovimiento.INICIANDO) {
                    Touchpad p = (Touchpad) actor;
                    if (p.getKnobPercentX() > 0) {    //Derecha
                        jugador.setEstadoMovimiento(Personaje.EstadoMovimiento.MOV_DERECHA);
                    } else if (p.getKnobPercentX() < 0) { // Izquierda
                        jugador.setEstadoMovimiento(Personaje.EstadoMovimiento.MOV_IZQUIERDA);
                    } else {    // Nada
                        jugador.setEstadoMovimiento(Personaje.EstadoMovimiento.QUIETO);
                    }
                }
            }
        });

        escena.addActor(pad);
        pad.setColor(1, 1, 1, 0.4f);



        Gdx.input.setInputProcessor(escena);
    }

    private void crearEscena() {
        batch= new SpriteBatch();

        escena= new Stage();
        escena.setViewport(vistaHUD);
        crearPad();
    }

    private void cargarMapa() {
        //manager= new AssetManager();
        AssetManager manager= juego.getAssetManager();

        //Cargar mapa
        manager.setLoader(TiledMap.class,
                new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("mapa3.tmx", TiledMap.class);

        //Cargar personaje
        manager.load("DUDE_camina1.png", Texture.class);
        manager.finishLoading(); //Bloquea hasta que carga el mapa
        mapa= manager.get("mapa3.tmx");
        texturaJugador= manager.get("DUDE_camina1.png");

        //Crea el objeto que dibujara el mapa
        rendererMapa = new OrthogonalTiledMapRenderer(mapa, batch);
        rendererMapa.setView(camara);

        //Dude
        jugador = new Personaje(texturaJugador);

        //enemigo
        manager.load("Maceta.png", Texture.class);
        texturaEnemigoPapa=manager.get("Maceta.png");
        enemigoPapa= new Enemigo(texturaEnemigoPapa);

        //Item
        manager.load("Maceta.png", Texture.class);
        texturaItemPlayera= manager.get("Maceta.png");
        itemPlayera= new Item(texturaItemPlayera,500,500);


    }


    private void inicializarCamara() {
        camara = new OrthographicCamera(ANCHO_CAMARA, ALTO_CAMARA);
        camara.position.set(ANCHO_CAMARA/2, ALTO_CAMARA /2,0);
        camara.update();
        vista = new StretchViewport(ANCHO_CAMARA, pantallaMapa.ALTO_CAMARA,camara);

        // Cámara para HUD
        camaraHUD = new OrthographicCamera(ANCHO_CAMARA, pantallaMapa.ALTO_CAMARA);
        camaraHUD.position.set(ANCHO_CAMARA/2, pantallaMapa.ALTO_CAMARA /2, 0);
        camaraHUD.update();
        vistaHUD = new StretchViewport(ANCHO_CAMARA, pantallaMapa.ALTO_CAMARA,camaraHUD);
    }

    @Override
    public void render(float delta) {

        randomY= new Random();
        randomX= new Random();
        rdmX= randomX.nextInt();
        rdmY= randomY.nextInt();

        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined);

        float xActual = jugador.getX();
        float yActual = jugador.getY();

        jugador.setY(jugador.getY() + pad.getKnobPercentY()*5);
        jugador.setX(jugador.getX() + pad.getKnobPercentX()*5);

        enemigoPapa.setY(enemigoPapa.getY() + 1 );
        enemigoPapa.setX(enemigoPapa.getX() + 2);

        if(xActual == jugador.getX() && yActual == jugador.getY()){
            jugador.setEstadoMovimiento(Personaje.EstadoMovimiento.QUIETO);
        }else if(xActual > jugador.getX() && yActual == jugador.getY()){
            jugador.setEstadoMovimiento(Personaje.EstadoMovimiento.MOV_IZQUIERDA);
        }else if(xActual < jugador.getX() && yActual == jugador.getY()){
            jugador.setEstadoMovimiento(Personaje.EstadoMovimiento.MOV_DERECHA);
        }






        rendererMapa.setView(camara);
        rendererMapa.render();
        batch.begin();
        jugador.render(batch);
        enemigoPapa.render(batch);
        itemPlayera.render(batch);
        batch.end();

        // Dibuja el HUD
        batch.setProjectionMatrix(camaraHUD.combined);
        escena.draw();
    }

    @Override
    public void resize(int width, int height) {

        vista.update(width, height);
        vistaHUD.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        texturaJugador.dispose();
        texturaItemPlayera.dispose();
        texturaEnemigoPapa.dispose();
        mapa.dispose();
    }

}
