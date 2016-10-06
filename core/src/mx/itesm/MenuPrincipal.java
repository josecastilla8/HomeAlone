package mx.itesm;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MenuPrincipal implements Screen {

	private final Juego juego;

	private Stage escena;


	private Texture texturaFondo;

	private Texture texturaTitulo;

	private Texture texturaBtnJugar;
	private Texture texturaBtnOpciones;
	private Texture texturaBtnAcercaDe;
	private Texture texturaBtnScores;


	private final AssetManager assetManager = new AssetManager();

	public MenuPrincipal(Juego juego) {
		this.juego = juego;
	}


	public void create () {
		cargarTexturas();
		escena = new Stage();

		Gdx.input.setInputProcessor(escena);

		float ancho = Gdx.graphics.getWidth();
		float alto = Gdx.graphics.getHeight();


		Image imgFondo = new Image(texturaFondo);
		float escalaX = Gdx.graphics.getWidth() / imgFondo.getWidth();
		float escalaY = Gdx.graphics.getHeight() / imgFondo.getHeight();
		imgFondo.setScale(escalaX, escalaY);
		escena.addActor(imgFondo);

		TextureRegionDrawable trBtnJugador = new TextureRegionDrawable(new TextureRegion(texturaBtnJugar));
		ImageButton btnJugar = new ImageButton(trBtnJugador);
		btnJugar.setPosition(ancho / 2 - btnJugar.getWidth() / 2, 0.431f * alto);
		escena.addActor(btnJugar);

		TextureRegionDrawable trBtnOpciones = new TextureRegionDrawable(new TextureRegion(texturaBtnOpciones));
		ImageButton btnOpciones = new ImageButton(trBtnOpciones);
		btnOpciones.setPosition((ancho / 0x2) - btnOpciones.getWidth() / 2, 0.28f * alto);
		escena.addActor(btnOpciones);

		TextureRegionDrawable trBtnAcercaDe = new TextureRegionDrawable(new TextureRegion(texturaBtnAcercaDe));
		ImageButton btnAcercaDe = new ImageButton(trBtnAcercaDe);
		btnAcercaDe.setPosition((ancho / 0x5) - (btnAcercaDe.getWidth() / 2), 0.28f * alto);
		escena.addActor(btnAcercaDe);

		TextureRegionDrawable trBtnScores = new TextureRegionDrawable(new TextureRegion(texturaBtnScores));
		ImageButton btnScores = new ImageButton(trBtnScores);
		btnScores.setPosition((ancho / 1.24f) - (btnScores.getWidth()/ 2), 0.23f * alto);
		escena.addActor(btnScores);

		/*/Image imgTitulo = new Image(texturaTitulo);
		imgTitulo.setPosition(ancho/2 - imgTitulo.getWidth()/2, 0.8f*alto);
		escena.addActor(imgTitulo);
		/*/

		btnJugar.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x,float y){
				Gdx.app.log("clicked","TAP sobre el botón de jugar");
				juego.setScreen(new PantallaJuego(juego));
			}
		});

		btnOpciones.addListener(new ClickListener(){
			@Override
			public void clicked (InputEvent event, float x, float y){
				Gdx.app.log("Clicked","Se hizo tap sobre OPCIONES");
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


	}

	private void cargarTexturas(){
		assetManager.load("FondoInicio.png", Texture.class);
		assetManager.load("Start.png", Texture.class);
		assetManager.load("Setting.png", Texture.class);
		assetManager.load("About.png", Texture.class);
		//assetManager.load("titulo.png",Texture.class);
		assetManager.load("HighScore.png", Texture.class);

		assetManager.finishLoading();
		texturaFondo = assetManager.get("FondoInicio.png");
		texturaBtnJugar = assetManager.get("Start.png");
		texturaBtnOpciones = assetManager.get("Setting.png");
		texturaBtnAcercaDe = assetManager.get("About.png");
		//texturaTitulo = assetManager.get("titulo.png");
		texturaBtnScores = assetManager.get("HighScore.png");


	}


	public void render () {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		escena.draw();

	}

	@Override
	public void show() {
		// equivale a create
		create();
	}

	@Override
	public void render(float delta) {
		render();

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
		dispose();

	}

	@Override
	public void dispose () {
		texturaFondo.dispose();
		texturaBtnAcercaDe.dispose();
		//texturaTitulo.dispose();
		texturaBtnOpciones.dispose();
		texturaBtnJugar.dispose();
		texturaBtnScores.dispose();
		escena.dispose();

	}
}
