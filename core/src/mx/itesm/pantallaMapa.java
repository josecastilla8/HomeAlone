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


import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Javier on 22/09/2016.
 */
//Comentario
public class pantallaMapa implements Screen {

    public static final int ANCHO_MAPA = 1280;
    public static final int ANCHO_CAMARA = 1280;
    public static final int ALTO_CAMARA = 800;

    private int contador = 0;
    private Texto texto;
    private Texto vidas;

    //Declaramos la camara
    private OrthographicCamera camara;
    private Viewport vista;

    private Random random;

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
    private ArrayList<Item> itemPlayera;
    private int estadoJuego = 0;
    private Texture texturaFinalGano;
    private Fondo fondoFinalGano;
    private Texture texturaFinalPierde;
    private Fondo fondoFinalPierde;
    private int contadorvidas = 100;
    private Texture texturaBtnAtras;
    private Stage escena2;

    public pantallaMapa(Juego juego) {
        this.juego= juego;
    }

    @Override
    public void show() {
        random = new Random();
        texturaFinalGano = new Texture("Pantalla Winner.png");
        fondoFinalGano = new Fondo(texturaFinalGano);

        texturaFinalPierde = new Texture("Pantalla Loser.png");
        fondoFinalPierde = new Fondo(texturaFinalPierde);

        texturaBtnAtras= new Texture("botonback.png");
        TextureRegionDrawable trBtnJugador = new TextureRegionDrawable(new TextureRegion(texturaBtnAtras));
        ImageButton btnJugar = new ImageButton(trBtnJugador);
        btnJugar.setPosition(0,0);

        escena2 = new Stage();
        btnJugar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x,float y){
                //Gdx.app.log("clicked","TAP sobre el botón de jugar");
                juego.setScreen(new MenuPrincipal(juego));
            }
        });

        escena2.addActor(btnJugar);

        inicializarCamara();
        crearEscena();
        cargarMapa(); //nuevo
        texto = new Texto();
        vidas = new Texto();
        itemPlayera = new ArrayList<Item>();

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
        manager.load("mapa4.tmx", TiledMap.class);

        //Cargar personaje
        manager.load("DUDE_camina1.png", Texture.class);
        manager.load("Playera.png",Texture.class);
        manager.load("Papa_sprite.png", Texture.class);
        manager.finishLoading(); //Bloquea hasta que carga el mapa
        mapa= manager.get("mapa4.tmx");
        texturaJugador= manager.get("DUDE_camina1.png");

        //Crea el objeto que dibujara el mapa
        rendererMapa = new OrthogonalTiledMapRenderer(mapa, batch);
        rendererMapa.setView(camara);

        //Dude
        jugador = new Personaje(texturaJugador);

        //enemigo

        texturaEnemigoPapa=manager.get("Papa_sprite.png");
        enemigoPapa= new Enemigo(texturaEnemigoPapa);

        //Item
        manager.load("Maceta.png", Texture.class);
        texturaItemPlayera= manager.get("Playera.png");



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

        if(random.nextInt(1000) <50 && itemPlayera.size()<=5){
            itemPlayera.add(new Item(texturaItemPlayera,random.nextInt(1000),random.nextInt(700)));


        }



        float xActual = jugador.getX();
        float yActual = jugador.getY();

        //0 - Jugando, 1 - Gano, 2- Perdio, 3 - Pausa
        switch (this.estadoJuego){
            case 0:
                Gdx.input.setInputProcessor(escena);
                jugador.setY(jugador.getY() + pad.getKnobPercentY()*5);
                jugador.setX(jugador.getX() + pad.getKnobPercentX()*5);

                if(random.nextInt(100)<20){
                    if(enemigoPapa.getX()<jugador.getX()){
                        enemigoPapa.setX(enemigoPapa.getX()+3);
                    }if(enemigoPapa.getX()>jugador.getX()){
                        enemigoPapa.setX(enemigoPapa.getX()-3);
                    }if(enemigoPapa.getY()<jugador.getY()){
                        enemigoPapa.setY(enemigoPapa.getY()+3);
                    }if(enemigoPapa.getY()>jugador.getY()){
                        enemigoPapa.setY(enemigoPapa.getY()-3);
                    }
                }




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
                texto.mostrarMensaje(batch, "Score: " + contador, 100, 750);
                vidas.mostrarMensaje(batch, "Vidas: " + contadorvidas, 750,750);
                jugador.render(batch);
                enemigoPapa.render(batch);
                if(papaChocoContigo()){
                    contadorvidas--;
                }
                //System.out.println(checarColisiones());
                for (int i = 0; i < itemPlayera.size(); i++) {
                    if(checarColisiones(itemPlayera.get(i)) == false){
                        itemPlayera.get(i).render(batch);
                    }else if(checarColisiones(itemPlayera.get(i)) == true){
                        itemPlayera.get(i).setX(10000);
                        contador++;
                    }

                }
                if(contadorvidas==0){
                    this.estadoJuego = 2;
                }
                if(contador == 5){
                    this.estadoJuego = 1;
                }
                batch.end();
                escena.draw();
                break;
            case 1:
                Gdx.input.setInputProcessor(escena2);
                batch.begin();
                fondoFinalGano.render(batch);
                batch.end();
                escena2.draw();
                break;
            case 2:
                Gdx.input.setInputProcessor(escena2);
                batch.begin();
                fondoFinalPierde.render(batch);
                batch.end();
                escena2.draw();
                break;
            default:
                break;
        }

        //System.out.println(estadoJuego);

        // Dibuja el HUD
        batch.setProjectionMatrix(camaraHUD.combined);

    }

    private boolean papaChocoContigo() {
        if((enemigoPapa.getX() + 50 >= jugador.getX()
                && enemigoPapa.getX() -50 <= jugador.getX())
                && (enemigoPapa.getY() +50 >= jugador.getY()
                && enemigoPapa.getY() -50 <= jugador.getY())){
            return true;
        }
        return false;
    }

    private boolean checarColisiones(Item item) {
        if((item.getX() + 50 >= jugador.getX()
                && item.getX() -50 <= jugador.getX())
                && (item.getY() +50 >= jugador.getY()
                && item.getY() -50 <= jugador.getY())){
            return true;
        }
        return false;
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
