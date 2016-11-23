package mx.itesm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Created by Javier on 23/11/2016.
 */
public class Sonidos {
    public static Music musicaFondo;


    public static void cargaAudio(){

        musicaFondo= Gdx.audio.newMusic(Gdx.files.internal("audio/cancionJuego.mp3"));
        musicaFondo.setLooping(true);

    }
}
