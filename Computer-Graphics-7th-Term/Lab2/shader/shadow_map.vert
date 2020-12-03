#version 450 core

in layout (location = 0) vec3 position;

uniform mat4 mvp;

void main()
{
    gl_Position = mvp * vec4(position, 1.0);
}