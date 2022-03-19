package no.arnemunthekaas.engine.utils;

import no.arnemunthekaas.engine.renderer.Shader;
import no.arnemunthekaas.engine.renderer.Texture;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetPool {

    private static Map<String, Shader> shaders = new HashMap<>();
    private static Map<String, Texture> textures = new HashMap<>();

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

}
