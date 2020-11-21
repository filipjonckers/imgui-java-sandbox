package opengl.shader;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

public enum ShaderType {
    NONE,
    VERTEX(GL_VERTEX_SHADER),
    FRAGMENT(GL_FRAGMENT_SHADER),
    ;

    private int glShaderType;

    ShaderType() {}

    ShaderType(int glShaderType) {
        this.glShaderType = glShaderType;
    }

    public int getGlShaderType() {
        return glShaderType;
    }
}
