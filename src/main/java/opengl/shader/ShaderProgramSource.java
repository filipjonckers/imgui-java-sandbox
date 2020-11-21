package opengl.shader;

public class ShaderProgramSource {
    private String vertexSource = "";
    private String fragmentSource = "";

    public void appendVertexSource(String line) {
        vertexSource += line + "\n";
    }

    public void appendFragmentSource(String line) {
        fragmentSource += line + "\n";
    }

    public String getVertexSource() {
        return vertexSource;
    }

    public String getFragmentSource() {
        return fragmentSource;
    }
}
