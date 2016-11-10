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
//Por que no se ven mis cambios?
public class MenuPrincipal implements Screen {

	private final float ALTO= 800;
	private final float ANCHO= 1280;

	private final Juego juego;
	private Stage escena;

	//Declaramos la camara
	private OrthographicCamera camara;
	private Viewport vista;
	//*********************

	//SpriteBatch sirve para administrar los trazos
	private SpriteBatch batch;
	//**********************

	//Fondo
	private Fondo fondo;
	private Texture texturaFondo;

	private Texture texturaTitulo;

	//Botones
	private Texture texturaBtnJugar;
	private Texture texturaBtnOpciones;
	private Texture texturaBtnAcercaDe;
	private Texture texturaBtnScores;


	//private final AssetManager assetManager = new AssetManager();

	public MenuPrincipal(Juego juego) {
		this.juego = juego;
	}


	public void create () {
		//float ancho= Gdx.graphics.getWidth();
		//float alto= Gdx.graphics.getHeight();
		//inicializarCamara();
		//cargarTexturas();
		//crearEscena();

		//Quien procesa los eventos
		//Gdx.input.setInputProcessor(this);

		//escena = new Stage();

		//Fondo
		escena= new Stage();

		camara = new OrthographicCamera(1280,800);
		camara.position.set(1280 / 2, 800 / 2, 0);
		camara.update();
		vista= new StretchViewport(ANCHO, ALTO, camara);

		texturaFondo = new Texture(Gdx.files.internal("FondoInicio.png"));
		fondo = new Fondo(texturaFondo);
		batch = new SpriteBatch();

		//Boton Jugar
		texturaBtnJugar= new Texture("Start.png");
		TextureRegionDrawable trBtnJugador = new TextureRegionDrawable(new TextureRegion(texturaBtnJugar));
		ImageButton btnJugar = new ImageButton(trBtnJugador);
		btnJugar.setPosition(ANCHO / 2 - btnJugar.getWidth() / 2, 0.47f * ALTO);

		escena.addActor(btnJugar);

		//Boton Opciones
		texturaBtnOpciones= new Texture("Setting.png");
		TextureRegionDrawable trBtnOpciones = new TextureRegionDrawable(new TextureRegion(texturaBtnOpciones));
		ImageButton btnOpciones = new ImageButton(trBtnOpciones);
		btnOpciones.setPosition((ANCHO/2) - btnOpciones.getWidth()/2, 0.23f * ALTO);
		escena.addActor(btnOpciones);

		//Boton Acerca de
		texturaBtnAcercaDe= new Texture("About.png");
		TextureRegionDrawable trBtnAcercaDe = new TextureRegionDrawable(new TextureRegion(texturaBtnAcercaDe));
		ImageButton btnAcercaDe = new ImageButton(trBtnAcercaDe);
		btnAcercaDe.setPosition((ANCHO/6)-(texturaBtnAcercaDe.getWidth()/2), 0.23f * ALTO);
		escena.addActor(btnAcercaDe);

		//Boton Scores
		texturaBtnScores= new Texture("High Score1.png");
		TextureRegionDrawable trBtnScores = new TextureRegionDrawable(new TextureRegion(texturaBtnScores));
		ImageButton btnScores = new ImageButton(trBtnScores);
		btnScores.setPosition((5*ANCHO /6) - (btnScores.getWidth()/ 2), 0.23f * ALTO);
		escena.addActor(btnScores);



		btnJugar.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x,float y){
				Gdx.app.log("clicked","TAP sobre el botón de jugar");
				juego.setScreen(new PantallaCargando(juego));
			}
		});

		btnOpciones.addListener(new ClickListener(){
			@Override
			public void clicked (InputEvent event, float x, float y){
				Gdx.app.log("Clicked","Se hizo tap sobre OPCIONES");
				//juego.setScreen(new PantallaOpciones(juego));
				juego.setScreen(new PantallaOpciones(juego));

			}

		});

		btnAcercaDe.addListener(new ClickListener(){
			@Override
			public void clicked (InputEvent event, float x, float y){
				Gdx.app.log("Clicked","Se hizo tap sobre ACERCA DE...");
				juego.setScreen(new PantallaAcercaDe(juego));
			}

		});

		btnScores.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x,float y){
				Gdx.app.log("clicked","TAP sobre el botón de jugar");
				juego.setScreen(new PantallaScores(juego));
			}
		});

		escena.setViewport(vista);
		Gdx.input.setInputProcessor(escena);


	}


	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camara.combined);
		escena.draw();

		batch.begin();
		fondo.render(batch);
		batch.end();
		escena.draw();
	}

	@Override
	public void show() {
		// equivale a create
		create();
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
		dispose();

	}

	@Override
	public void dispose () {

	}


}
