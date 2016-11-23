package mx.itesm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;


/**
 * Created by Javier on 17/11/2016.
 */
public class PantallaSplash implements Screen{

    private final float ALTO= 800;
    private final float ANCHO= 1280;

    //*private Fondo fondo;
    //private Sprite fondoS;
    //private Texture fondo2;
    //private SpriteBatch batch;
    //private OrthographicCamera camara;
    //private FitViewport vista;
    private Juego juego;
    private Stage escena;


    //Fondo
    private Texture fondoSplash;
    private Fondo fondo;
    private SpriteBatch batch;
    private OrthographicCamera camara;

    private int cont= 0;

    private Preferences pref = Gdx.app.getPreferences("Preferencias");

    public PantallaSplash(Juego juego){
        this.juego= juego;
    }

    @Override
    public void show() {
        //fondo= new Fondo("Splash.png");
        //fondoS= new Sprite(fondo);
        //fondoS.setX(0);
        //fondoS.setY(0);
        //fondoS.setScale(4);
        //batch= new SpriteBatch();
        //camara= new OrthographicCamera(ANCHO, ALTO);
        //camara.position.set(ANCHO/2, ALTO/2, 0);
        //camara.update();
        //vista= new FitViewport(ANCHO, ALTO);

        escena = new Stage();

        //Fondo
        camara = new OrthographicCamera(1280,800);
        camara.position.set(1280 / 2, 800 / 2, 0);
        camara.update();
        fondoSplash = new Texture(Gdx.files.internal("Splash .png"));
        fondo = new Fondo(fondoSplash);
        batch = new SpriteBatch();

        Gdx.input.setInputProcessor(escena);
        Sonidos.cargaAudio();
        if(pref.getBoolean("musica",true)){
            Sonidos.musicaFondo.play();
        }

    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cont++;
        escena.draw();

        //Fondo
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        fondo.render(batch);
        batch.end();
        escena.draw();

        if(cont >150){
            juego.setScreen(new MenuPrincipal(juego));
            this.dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        //vista.update(width, height);

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
        //fondo.dispose();
        batch.dispose();
    }
}
