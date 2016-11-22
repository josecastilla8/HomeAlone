package mx.itesm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
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
 * Created by Daniela on 12/09/2016.
 */
//Comentario
public class PantallaNivelDos implements Screen {

    public static final int ANCHO_MAPA = 1280;
    public static final int ANCHO_CAMARA = 1280;
    public static final int ALTO_CAMARA = 800;

    private int contador = 0;
    private Texto texto;
    private Texto vida;

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

    //Musica
    private Music musicaFondo;

    //SpriteBatch sirve para administrar los trazos
    private SpriteBatch batch;
    private final Juego juego;
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

    //Enemigo Mama
    private Texture texturaEnemigoMama;
    private Enemigo enemigoMama;

    //Item Playera
    private Texture texturaItemPlayera;
    private Item playeraItem;
    private ArrayList<Item> itemPlayera;
    private int estadoJuego = 0;
    private Texture texturaFinalGano;
    private Fondo fondoFinalGano;
    private Texture texturaFinalPierde;
    private Fondo fondoFinalPierde;
    private int contadorvidas = 100;
    private Texture texturaBtnAtras;
    private Stage escena2;
    private Boton btnFlechaArriba;
    private Boton btnFlechaDerecha;
    private Boton btnFlechaIzquierda;
    private Boton btnFlechaAbajo;
    private Stage escena3;
    AssetManager manager;
    private Texture texturaPausa;
    private Fondo fondoPausa;
    private Boton btnPausa;

    //Item Redbull
    private Texture texturaItemRedbull;
    private Item redbullItem;
    private ArrayList<Item> itemRedbull;

    public PantallaNivelDos(Juego juego) {
        this.juego= juego;
    }

    @Override
    public void show() {
        manager = juego.getAssetManager();
        random = new Random();
        texturaFinalGano = new Texture("Pantalla Winner.png");
        fondoFinalGano = new Fondo(texturaFinalGano);

        texturaFinalPierde = new Texture("Pantalla Loser.png");
        fondoFinalPierde = new Fondo(texturaFinalPierde);

        texturaPausa= new Texture ("FondoPausa.png");
        fondoPausa= new Fondo(texturaPausa);

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

        ImageButton btnContinue = new ImageButton(new TextureRegionDrawable(new TextureRegion(manager.get("Continue .png",Texture.class))));
        ImageButton btnExit = new ImageButton(new TextureRegionDrawable(new TextureRegion(manager.get("Exit.png",Texture.class))));

        btnContinue.setPosition(200,100);
        btnExit.setPosition(350,90);

        escena2.addActor(btnJugar);
        escena3 = new Stage();

        btnContinue.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x,float y){
                //Gdx.app.log("clicked","TAP sobre el botón de jugar");
                estadoJuego = 0;
            }
        });

        btnExit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x,float y){
                //Gdx.app.log("clicked","TAP sobre el botón de jugar");
                juego.setScreen(new MenuPrincipal(juego));
            }
        });

        escena3.addActor(btnContinue);
        escena3.addActor(btnExit);

        inicializarCamara();
        crearEscena();
        cargarMapa(); //nuevo
        texto = new Texto();
        vida = new Texto();
        itemPlayera = new ArrayList<Item>();
        itemRedbull= new ArrayList<Item>();


        crearPad();

        Gdx.gl.glClearColor(1,0,0,1);

    }

    private void crearPad() {

        // Para cargar las texturas y convertirlas en Drawable
        Skin skin = new Skin();
        skin.add("touchBackground", new Texture("touchBackground.png"));
        skin.add("touchKnob", new Texture("touchKnob.png"));


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
                    }else if(p.getKnobPercentY()>0) {
                        jugador.setEstadoMovimiento(Personaje.EstadoMovimiento.MOV_ARRIBA);
                    }else if(p.getKnobPercentY() < 0){
                        jugador.setEstadoMovimiento(Personaje.EstadoMovimiento.MOV_ABAJO);
                    } else {    // Nada
                        jugador.setEstadoMovimiento(Personaje.EstadoMovimiento.QUIETO);
                    }
                }
            }
        });

        //escena.addActor(pad);
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

        //AssetManager manager= juego.getAssetManager();

        //Si ya cargo los assets...
        mapa= manager.get("mapa4.tmx");
        texturaJugador= manager.get("DUDE_camina.png");
        texturaEnemigoPapa= manager.get("Papa_camina.png");
        texturaItemPlayera= manager.get("Playera.png");
        texturaEnemigoMama= manager.get("Mama_camina.png");
        texturaItemRedbull= manager.get("RedBull.png");

        //Audio
        musicaFondo= manager.get("audio/cancionJuego.mp3");

        Texture texturaBtnFlechaArriba= manager.get("BotonFlecha.png");

        btnFlechaArriba = new Boton(texturaBtnFlechaArriba);
        btnFlechaDerecha = new Boton(texturaBtnFlechaArriba);
        btnFlechaIzquierda = new Boton(texturaBtnFlechaArriba);
        btnFlechaAbajo = new Boton(texturaBtnFlechaArriba);

        Texture texturaBtnPausa = manager.get("Pause.png");
        btnPausa = new Boton(texturaBtnPausa);

        btnPausa.setPosicion(1100,725);


        btnFlechaArriba.setPosicion(70,200);
        btnFlechaAbajo.setPosicion(70,50);
        btnFlechaDerecha.setPosicion(10,125);
        btnFlechaIzquierda.setPosicion(130,125);



        musicaFondo.setLooping(true);
        musicaFondo.play();

        //Crea el objeto que dibujara el mapa
        rendererMapa = new OrthogonalTiledMapRenderer(mapa, batch);
        rendererMapa.setView(camara);

        //Dude
        jugador = new Personaje(texturaJugador);

        //enemigo
        enemigoPapa= new Enemigo(texturaEnemigoPapa);
        enemigoMama= new Enemigo(texturaEnemigoMama);

        //Item
        //manager.load("Maceta.png", Texture.class);
        //texturaItemPlayera= manager.get("Playera.png");
        playeraItem= new Item(texturaItemPlayera);

        //Item Redbull
        redbullItem= new Item(texturaItemRedbull);

    }


    private void inicializarCamara() {
        camara = new OrthographicCamera(ANCHO_CAMARA, ALTO_CAMARA);
        camara.position.set(ANCHO_CAMARA/2, ALTO_CAMARA /2,0);
        camara.update();
        vista = new StretchViewport(ANCHO_CAMARA, PantallaNivelUno.ALTO_CAMARA,camara);

        // Cámara para HUD
        camaraHUD = new OrthographicCamera(ANCHO_CAMARA, PantallaNivelUno.ALTO_CAMARA);
        camaraHUD.position.set(ANCHO_CAMARA/2, PantallaNivelUno.ALTO_CAMARA /2, 0);
        camaraHUD.update();
        vistaHUD = new StretchViewport(ANCHO_CAMARA, PantallaNivelUno.ALTO_CAMARA,camaraHUD);
    }

    public boolean tocando(Boton boton){
        Vector3 coordenadas = new Vector3();
        coordenadas.set(Gdx.input.getX(),Gdx.input.getY(),0);
        camara.unproject(coordenadas);
        //if(boton.equals(btnFlechaArriba))
        //System.out.println(coordenadas.toString() + " " + boton.getSprite().getX() + " : " + boton.getSprite().getY());
        if(Gdx.input.isTouched()){
            if(coordenadas.x >= boton.getSprite().getX() && coordenadas.x <= boton.getSprite().getX()+boton.getSprite().getWidth()
                    && coordenadas.y>=boton.getSprite().getY() && coordenadas.y <= boton.getSprite().getY()+boton.getSprite().getHeight()){
                return true;
            }
        }
        return false;
    }

    @Override
    public void render(float delta) {
        jugador.actualizar(mapa);

        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined);

        if(random.nextInt(1000) <50 && itemPlayera.size()<=5){
            itemPlayera.add(new Item(texturaItemPlayera,random.nextInt(1000),random.nextInt(700)));


        }

        if(random.nextInt(1000) <50 && itemRedbull.size()<=2){
            itemRedbull.add(new Item(texturaItemRedbull,random.nextInt(1000),random.nextInt(700)));


        }



        float xActual = jugador.getX();
        float yActual = jugador.getY();

        //0 - Jugando, 1 - Gano, 2- Perdio, 3 - Pausa
        switch (this.estadoJuego){
            case 0:
                Gdx.input.setInputProcessor(escena);
                jugador.setY(jugador.getY() + pad.getKnobPercentY()*5);
                jugador.setX(jugador.getX() + pad.getKnobPercentX()*5);

                //movimiento papa
                movimientoEnemigo(enemigoPapa);

                if(enemigoPapa.getX()<jugador.getX()){
                    enemigoPapa.setEstadoMovimiento(Enemigo.EstadoMovimiento.MOV_IZQUIERDA);
                }else{
                    enemigoPapa.setEstadoMovimiento(Enemigo.EstadoMovimiento.MOV_DERECHA);
                }

                //movimientoMama
                movimientoEnemigo(enemigoMama);

                if(enemigoMama.getX()<jugador.getX()){
                    enemigoMama.setEstadoMovimiento(Enemigo.EstadoMovimiento.MOV_IZQUIERDA);
                }else{
                    enemigoMama.setEstadoMovimiento(Enemigo.EstadoMovimiento.MOV_DERECHA);
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
                btnFlechaIzquierda.render(batch);
                btnFlechaArriba.render(batch);
                btnFlechaAbajo.render(batch);
                btnFlechaDerecha.render(batch);
                texto.mostrarMensaje(batch, "Score: " + contador, 100, 750);
                vida.mostrarMensaje(batch, "Vida: " + contadorvidas, 750,750);
                jugador.render(batch);
                enemigoPapa.render(batch);
                enemigoMama.render(batch);
                if(enemigoChocoContigo(enemigoPapa)){
                    contadorvidas--;
                }
                if(enemigoChocoContigo(enemigoMama)){
                    contadorvidas--;
                }


                //FLECHAS
                if(tocando(btnFlechaAbajo)){
                    jugador.setEstadoMovimiento(Personaje.EstadoMovimiento.MOV_ABAJO);
                    System.out.println("MOVABA");
                }
                if(tocando(btnFlechaArriba)){
                    jugador.setEstadoMovimiento(Personaje.EstadoMovimiento.MOV_ARRIBA);
                    System.out.println("MOVARRI");
                }
                if(tocando(btnFlechaDerecha)){
                    jugador.setEstadoMovimiento(Personaje.EstadoMovimiento.MOV_IZQUIERDA);
                    System.out.println("MOVIZQ");
                }
                if(tocando(btnFlechaIzquierda)){
                    jugador.setEstadoMovimiento(Personaje.EstadoMovimiento.MOV_DERECHA);
                    System.out.println("MOVDER");
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

                for (int i = 0; i < itemRedbull.size(); i++) {
                    if(checarColisiones(itemRedbull.get(i)) == false){
                        itemRedbull.get(i).render(batch);
                    }else if(checarColisiones(itemRedbull.get(i)) == true){
                        itemRedbull.get(i).setX(10000);
                        contadorvidas=contadorvidas+30;
                    }

                }


                if(contadorvidas==0){
                    this.estadoJuego = 2;
                }
                if(contador == 5){
                    this.estadoJuego = 1;
                }
                btnPausa.render(batch);
                if (tocando(btnPausa)){
                    this.estadoJuego=3;
                }
                batch.end();
                escena.draw();
                break;
            case 1:
                Gdx.input.setInputProcessor(escena2);
                batch.begin();
                juego.setScreen(new PantallaCargando(juego,3));
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
            case 3:
                Gdx.input.setInputProcessor(escena3);
                batch.begin();
                fondoPausa.render(batch);
                batch.end();
                escena3.draw();
                break;
            default:
                break;
        }


        // Dibuja el HUD
        batch.setProjectionMatrix(camaraHUD.combined);

    }

    private boolean enemigoChocoContigo(Enemigo enemigo) {
        if((enemigo.getX() + 50 >= jugador.getX()
                && enemigo.getX() -50 <= jugador.getX())
                && (enemigo.getY() +50 >= jugador.getY()
                && enemigo.getY() -50 <= jugador.getY())){
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

    private boolean movimientoEnemigo(Enemigo enemigo){
        if(random.nextInt(100)<20){
            if(enemigo.getX()<jugador.getX()){
                enemigo.setX(enemigo.getX()+2);
            }if(enemigo.getX()>jugador.getX()){
                enemigo.setX(enemigo.getX()-2);
            }if(enemigo.getY()<jugador.getY()){
                enemigo.setY(enemigo.getY()+2);
            }if(enemigo.getY()>jugador.getY()){
                enemigo.setY(enemigo.getY()-2);
            }
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
        texturaEnemigoMama.dispose();
        texturaItemRedbull.dispose();
        musicaFondo.dispose();
        mapa.dispose();

        //Parte actualizada
        juego.getAssetManager().unload("DUDE_camina.png");
        juego.getAssetManager().unload("Papa_camina.png");
        juego.getAssetManager().unload("Playera.png");
        juego.getAssetManager().unload("RedBull.png");
        juego.getAssetManager().unload("Mama_camina.png");

    }

}

