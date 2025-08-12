#version 330 core
out vec4 FragColor;

uniform float windowWidth;
uniform float windowHeight;

in vec3 vertColor;
in vec2 TexCoord;

uniform sampler2D ourTexture;

void main() {
//    FragColor = texture(ourTexture, TexCoord);
    FragColor = vec4(1.0);
}
