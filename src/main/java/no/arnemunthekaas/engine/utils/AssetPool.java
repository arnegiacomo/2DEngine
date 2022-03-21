package no.arnemunthekaas.engine.utils;

import no.arnemunthekaas.engine.entities.components.Spritesheet;
import no.arnemunthekaas.engine.renderer.Shader;
import no.arnemunthekaas.engine.renderer.Texture;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetPool {

    private static Map<String, Shader> shaders = new HashMap<>();
    private static Map<String, Texture> textures = new HashMap<>();
    private static Map<String, Spritesheet> spritesheets = new HashMap<>();

    /**
     *
     * @param resourceName
     * @return Reference to shader
     */
    public static Shader getShader(String resourceName) {
        File file = new File(resourceName);

        if (shaders.containsKey(file.getAbsolutePath()))
            return shaders.get(file.getAbsolutePath());
        else {
            Shader shader = new Shader(resourceName);
            shader.compile();
            shaders.put(file.getAbsolutePath(), shader);
            return shader;
        }
    }

    /**
     *
     * @param resourceName
     * @return Reference to texture
     */
    public static Texture getTexture(String resourceName) {
        File file = new File(resourceName);

        if (textures.containsKey(file.getAbsolutePath()))
            return textures.get(file.getAbsolutePath());
        else {
            Texture texture = new Texture(resourceName);
            textures.put(file.getAbsolutePath(), texture);
            return texture;
        }
    }

    /**
     *
     * @param spritesheet
     */
    public static void addSpriteSheet(String resourceName, Spritesheet spritesheet) {
        File file = new File(resourceName);
        if(!spritesheets.containsKey(file.getAbsolutePath()))
            spritesheets.put(file.getAbsolutePath(), spritesheet);

    }

    /**
     *
     * @param resourceName
     * @return
     */
    public static Spritesheet getSpriteSheet(String resourceName) {
        File file = new File(resourceName);
        if(!spritesheets.containsKey(file.getAbsolutePath()))
            assert false : "Error: Tried to access spritesheed '" + resourceName + "' and it has not been added to assetpool";

        return spritesheets.getOrDefault(file.getAbsolutePath(), null); // TODO Default null texture instead of null value? Like minecraft pink texture?
    }

}