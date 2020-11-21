package opengl.shader;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

public class Shader {
    private String resource;
    private ShaderProgramSource shaderSource;
    private int id;

    public Shader(String file) {
        this.resource = file;

        this.shaderSource = parse(this.resource);
        this.id = create();
    }

    private ShaderProgramSource parse(String file) {
        ShaderType type = ShaderType.NONE;
        ShaderProgramSource shaderProgramSource = new ShaderProgramSource();

        try (InputStream inputStream = getClass().getResourceAsStream(file)) {
            String[] lines = new String(inputStream.readAllBytes()).split("\n");

            for (String line : lines) {
                if (line.startsWith("#shader")) {
                    if (line.contains("vertex")) {
                        type = ShaderType.VERTEX;
                    } else if (line.contains("fragment")) {
                        type = ShaderType.FRAGMENT;
                    }

                    continue;
                }

                switch (type) {
                    case NONE:
                        continue;
                    case VERTEX:
                        shaderProgramSource.appendVertexSource(line);
                        break;
                    case FRAGMENT:
                        shaderProgramSource.appendFragmentSource(line);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return shaderProgramSource;
    }

    public int create() {
        int program = glCreateProgram();
        int vs = load(GL_VERTEX_SHADER, shaderSource.getVertexSource());
        int fs = load(GL_FRAGMENT_SHADER, shaderSource.getFragmentSource());

        glAttachShader(program, vs);
        glAttachShader(program, fs);
        glLinkProgram(program);
        glValidateProgram(program);

        glDeleteShader(vs);
        glDeleteShader(fs);

        return program;
    }

    public int getId() {
        return id;
    }

    private int load(int type, String shader) {
        int shaderID;

        shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shader);
        GL20.glCompileShader(shaderID);

        IntBuffer result = BufferUtils.createIntBuffer(1);
        glGetShaderiv(shaderID, GL_COMPILE_STATUS, result);

        if (result.get() == GL_FALSE) {
            String message = glGetShaderInfoLog(shaderID);
            glDeleteShader(shaderID);
            throw new RuntimeException("SHADER FAILED: " + (type == GL_VERTEX_SHADER ? "VERTEX " : "FRAGMENT ") + message);
        }

        return shaderID;
    }

}
