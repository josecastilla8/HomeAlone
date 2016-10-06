package mx.itesm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by JoseCastilla on 01/09/2016.
 */
public class PantallaAcercaDe implements Screen {
    //Boton back
    private final Juego juego;
    private Stage escena;
    private Texture texturaBtnBack;


    public PantallaAcercaDe(Juego juego) {
        this.juego = juego;
    }

    //Fondo
    private Texture fondoAboutTextura;
    private Fondo fondo;
    private SpriteBatch batch;
    private OrthographicCamera camara;


    @Override
    public void show() {
        //Fondo
        camara = new OrthographicCamera(1280,800);
        camara.position.set(1280 / 2, 800 / 2, 0);
        camara.update();
        fondoAboutTextura = new Texture(Gdx.files.internal("PantallaAbout.png"));
        fondo = new Fondo(fondoAboutTextura);
        batch = new SpriteBatch();

        //Boton back
        texturaBtnBack = new Texture("botonback.png");

        TextureRegionDrawable trdBtnBack = new TextureRegionDrawable(new TextureRegion(texturaBtnBack));
        ImageButton btnBack = new ImageButton(trdBtnBack);

        escena = new Stage();
        escena.addActor(btnBack);

        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new MenuPrincipal(juego));

            }
        });

        Gdx.input.setInputProcessor(escena);


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        escena.draw();

        //Fondo
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        fondo.render(batch);
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

    }
}
