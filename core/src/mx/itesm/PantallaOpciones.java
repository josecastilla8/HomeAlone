package mx.itesm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by JoseCastilla on 01/09/2016.
 */
public class PantallaOpciones implements Screen {
    AssetManager manager;

    //Fondo
    private Texture fondoOpcionesTextura;
    private SpriteBatch batch;
    private OrthographicCamera camara;
    private Fondo fondo;

    //Musica
    private Music musicaFondo;

    private final Juego juego;
    private Stage escena;

    //Para el boton back
    private Texture texturaBtnBack;

    //Para el boton mute/play
    private Texture texturaMute;
    private Boton btnMute;
    private Texture texturaPlay;
    private Boton btnPlay;
    private Preferences pref = Gdx.app.getPreferences("Preferencias");

    public PantallaOpciones(Juego juego) {

        this.juego = juego;
    }

    @Override
    public void show() {
        manager= juego.getAssetManager();

        //Audio





        camara = new OrthographicCamera(1280,800);
        camara.position.set(1280 / 2, 800 / 2, 0);
        camara.update();
        fondoOpcionesTextura = new Texture(Gdx.files.internal("PantallaOpciones.png"));
        fondo = new Fondo(fondoOpcionesTextura);
        batch = new SpriteBatch();

        //Boton back
        texturaBtnBack = new Texture("botonback.png");
        TextureRegionDrawable trdBtnBack = new TextureRegionDrawable(new TextureRegion(texturaBtnBack));
        ImageButton btnBack = new ImageButton(trdBtnBack);

        //Boton mute
        texturaMute= new Texture("cuadrado.png");
        btnMute= new Boton(texturaMute);

        //Boton play
        texturaPlay=new Texture("cuadrado.png");
        btnPlay= new Boton(texturaPlay);
        //TextureRegionDrawable trdBtnMute = new TextureRegionDrawable(new TextureRegion(texturaMute));
        //ImageButton btnMute = new ImageButton(trdBtnMute);
        btnMute.setPosicion(682, 505);
        btnPlay.setPosicion(539, 505);


        escena = new Stage();
        escena.addActor(btnBack);
        //escena.addActor(btnMute);

        //Acciones de los botones
        //back
        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new MenuPrincipal(juego));

            }
        });



        Gdx.input.setInputProcessor(escena);


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
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        escena.draw();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();

        fondo.render(batch);
        btnMute.render(batch);
        btnPlay.render(batch);
        if(tocando(btnMute)){
            Sonidos.musicaFondo.pause();
            pref.putBoolean("musica",false);
        }
        if(tocando(btnPlay)){
            Sonidos.musicaFondo.play();
            pref.putBoolean("musica",true);
        }
        pref.flush();
        batch.end();
        escena.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        musicaFondo.dispose();
    }
}
