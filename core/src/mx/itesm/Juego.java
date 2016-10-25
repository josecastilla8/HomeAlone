package mx.itesm;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * Representa el JUEGO completo
 *
 * Created by rmroman on 02/09/16.
 */
public class Juego extends Game
{
    // Ahora habrá un SOLO AssetManager en toda la aplicación
    private final AssetManager assetManager = new AssetManager();

    @Override
    public void create() {

        // Desde aquí avisamos que podrá cargar Mapas
        assetManager.setLoader(TiledMap.class,
                new TmxMapLoader(new InternalFileHandleResolver()));

        // Pantalla inicial
        setScreen(new MenuPrincipal(this));
    }

    // Accesor del AssetManager, para que otras clases lo utilicen :)
    public AssetManager getAssetManager() {
        return assetManager;
    }

    @Override
    public void dispose() {
        super.dispose();
        assetManager.clear();   // Esto ocurrirá al final de la aplicación
    }
}