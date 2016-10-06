package mx.itesm;

/**
 * Created by JoseCastilla on 08/09/2016.
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by JoseCastilla on 01/09/2016.
 */
public class PantallaScores implements Screen {

    //Fondo
    private Texture fondoScoresTextura;
    private SpriteBatch batch;
    private OrthographicCamera camara;
    private Fondo fondo;

    //Para el boton
    private final Juego juego;
    private Stage escena;
    private Texture texturaBtnBack;

    public PantallaScores(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        camara = new OrthographicCamera(1280,800);
        camara.position.set(1280 / 2, 800 / 2, 0);
        camara.update();
        fondoScoresTextura = new Texture(Gdx.files.internal("PantallaScores.png"));
        fondo = new Fondo(fondoScoresTextura);
        batch = new SpriteBatch();

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
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        escena.draw();
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