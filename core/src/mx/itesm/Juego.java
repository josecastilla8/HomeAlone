package mx.itesm;

import com.badlogic.gdx.Game;

/**
 * Created by JoseCastilla y Javier :) y Melanie :3 on 01/09/2016.
 */
public class Juego extends Game{

    @Override
    public void create() {
        setScreen(new MenuPrincipal(this));
    }
}
