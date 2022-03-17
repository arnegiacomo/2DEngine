package no.arnemunthekaas.engine.renderer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;

public class Shader {

    private int shaderProgramID;

    private String vertexSrc;
    private String fragmentSrc;
    private String filepath;

    /**
     * Locate and create a shader object from a filepath. Important that shader has a vertex and fragment type in same file.@
     * This only works for vertex x fragment shaders.
     *
     * @param filepath of .glsl shader
     */
    public Shader(String filepath) {
        // Todo -> Add ability for more shader types
        this.filepath = filepath;

        try {
            String source = new String(Files.readAllBytes(Paths.get(filepath)));
            String[] splitStr = source.split("(#type)( )+([a-zA-Z]+)"); // Read entire shader

            int index = source.indexOf("#type") + 6; // Represents index of first word after #type, to find type of shader
            int eol = source.indexOf(System.lineSeparator(), index); // Finds end of first line, to find what type of shader
            String firstPattern = source.substring(index, eol).trim(); // First pattern

            index = source.indexOf("#type", eol) + 6; // Finds next type
            eol = source.indexOf(System.lineSeparator(), index);
            String secondPattern = source.substring(index, eol).trim(); // Second pattern

            if (firstPattern.equals("vertex"))
                vertexSrc = splitStr[1];
            else if (firstPattern.equals("fragment"))
                fragmentSrc = splitStr[1];
            else
                throw new IOException("Unexpected token '" + firstPattern + "'");

            if (secondPattern.equals("vertex"))
                vertexSrc = splitStr[2];
            else if (secondPattern.equals("fragment"))
                fragmentSrc = splitStr[2];
            else
                throw new IOException("Unexpected token '" + secondPattern + "'");

        } catch (IOException e) {
            e.printStackTrace();
            assert false : "Error: Could not open shader file: '" + filepath + "'";
        }

    }

    /**
     * Compile and link shader
     */
    public void compile() {
        int vertexID, fragmentID;
        // First load and compile the vertex shader
        vertexID = glCreateShader(GL_VERTEX_SHADER);
        // Pass the shader source to the GPU
        glShaderSource(vertexID, vertexSrc);
        glCompileShader(vertexID);

        // Check for errors in compilation
        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
        if(success == GL_FALSE) {
            int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '" + filepath + "'\n\tVertex shader compilation failed.");
            System.out.println(glGetShaderInfoLog(vertexID, len));
            assert false : "";
        }

        // First load and compile the fragment shader
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        // Pass the shader source to the GPU
        glShaderSource(fragmentID, fragmentSrc);
        glCompileShader(fragmentID);

        // Check for errors in compilation
        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if(success == GL_FALSE) {
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '" + filepath + "'\n\tFragment shader compilation failed.");
            System.out.println(glGetShaderInfoLog(fragmentID, len));
            assert false : "";
        }

        // Link shaders and check for errors
        shaderProgramID = glCreateProgram();
        glAttachShader(shaderProgramID, vertexID);
        glAttachShader(shaderProgramID, fragmentID);
        glLinkProgram(shaderProgramID);

        // Check for linking errors
        success = glGetProgrami(shaderProgramID, GL_LINK_STATUS);
        if (success == GL_FALSE) {
            int len = glGetProgrami(shaderProgramID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '" + filepath + "'\n\tLinking shaders failed.");
            System.out.println(glGetProgrami(shaderProgramID, len));
            assert false : "";
        }
    }

    /**
     * Use shader
     */
    public void use() {
        // Bind shader program
        glUseProgram(shaderProgramID);
    }

    /**
     * Detach shader
     */
    public void detach() {
        glUseProgram(0);
    }

}
