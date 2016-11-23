package mx.itesm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by safin on 22/11/2016.
 */
public class PantallaHistoria implements Screen {
    private final float ALTO= 800;
    private final float ANCHO= 1280;

    private Juego juego;
    private Stage escena;

    //Fondo
    private Texture fondoHistoria1;
    private Texture fondoHistoria2;
    private Texture fondoHistoria3;
    private Texture fondoHistoria4;
    private Texture fondoHistoria5;
    private Texture fondoHistoria6;
    private Texture fondoHistoria7;
    private Fondo fondo1;
    private Fondo fondo2;
    private Fondo fondo3;
    private Fondo fondo4;
    private Fondo fondo5;
    private Fondo fondo6;
    private Fondo fondo7;

    private SpriteBatch batch;
    private OrthographicCamera camara;

    private int cont= 0;

    public PantallaHistoria(Juego juego){
        this.juego= juego;
    }

    @Override
    public void show() {
        escena = new Stage();

        //Fondo
        camara = new OrthographicCamera(1280,800);
        camara.position.set(1280 / 2, 800 / 2, 0);
        camara.update();
        fondoHistoria1 = new Texture(Gdx.files.internal("Frame1.png"));
        fondoHistoria2 = new Texture(Gdx.files.internal("Frame2.png"));
        fondoHistoria3 = new Texture(Gdx.files.internal("Frame3.png"));
        fondoHistoria4 = new Texture(Gdx.files.internal("Frame4.png"));
        fondoHistoria5 = new Texture(Gdx.files.internal("Frame5.png"));
        fondoHistoria6 = new Texture(Gdx.files.internal("Frame6.png"));
        fondoHistoria7 = new Texture(Gdx.files.internal("Frame7.png"));
        fondo1 = new Fondo(fondoHistoria1);
        fondo2 = new Fondo(fondoHistoria2);
        fondo3 = new Fondo(fondoHistoria3);
        fondo4 = new Fondo(fondoHistoria4);
        fondo5 = new Fondo(fondoHistoria5);
        fondo6 = new Fondo(fondoHistoria6);
        fondo7 = new Fondo(fondoHistoria7);
        batch = new SpriteBatch();

        Gdx.input.setInputProcessor(escena);

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
        fondo1.render(batch);
        //fondo2.render(batch);
        batch.end();
        escena.draw();

        if(cont >150 && cont <300){
            //fondo2.render(batch);
            juego.setScreen(new PantallaCargando(juego, 1));
            this.dispose();


        }

        if(cont >300 && cont <450){
            //fondo3.render(batch);
            juego.setScreen(new PantallaCargando(juego, 1));
            this.dispose();
            //escena.draw();

        }
        //batch.end();
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

