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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by safin on 22/11/2016.
 */
public class PantallaHistoria implements Screen {
    private final float ALTO= 800;
    private final float ANCHO= 1280;

    private Juego juego;
    private Stage escena;

    //Declaramos la camara
    private OrthographicCamera camara;
    private Viewport vista;
    //*********************

    //SpriteBatch sirve para administrar los trazos
    private SpriteBatch batch;
    //**********************

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

    //Boton
    private Texture texturaBtnSkip;

    //private SpriteBatch batch;
    //private OrthographicCamera camara;

    private int cont= 0;

    public PantallaHistoria(Juego juego){
        this.juego= juego;
    }

    @Override
    public void show() {
        escena = new Stage();

        //Camara
        camara = new OrthographicCamera(ANCHO,ALTO);
        camara.position.set(ANCHO / 2, ALTO / 2, 0);
        camara.update();
        vista= new StretchViewport(ANCHO, ALTO, camara);

        //Fondo
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

        //Boton Skip
        texturaBtnSkip= new Texture("right.png");
        TextureRegionDrawable trBtnSkip = new TextureRegionDrawable(new TextureRegion(texturaBtnSkip));
        ImageButton btnSkip = new ImageButton(trBtnSkip);
        btnSkip.setPosition(ANCHO /1.1f - btnSkip.getWidth() / 2, 0.1f * ALTO);

        escena.addActor(btnSkip);

        btnSkip.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.log("clicked","TAP sobre el botón de skip");
                juego.setScreen(new PantallaCargando(juego, 1));
            }
        });

        escena.setViewport(vista);
        Gdx.input.setInputProcessor(escena);

    }

    @Override
    public void render(float delta) {
        cont++;
        batch.setProjectionMatrix(camara.combined);
        escena.draw();

        //Fondo

        batch.begin();
        if(cont<150) {
            fondo1.render(batch);
        }
        if(cont >150 && cont <250){
            fondo2.render(batch);
        }
        if(cont >250 && cont <350){
            fondo3.render(batch);
        }
        if(cont >350 && cont <450){
            fondo4.render(batch);
        }
        if(cont >450 && cont <550){
            fondo5.render(batch);
        }
        if(cont >550 && cont <650){
            fondo6.render(batch);
        }
        if(cont >650 && cont <750){
            fondo7.render(batch);
        }


        if(cont >750 ){
            juego.setScreen(new PantallaCargando(juego, 1));
            this.dispose();
        }
        batch.end();
        escena.draw();

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
        //fondo.dispose();
        batch.dispose();
    }
}

