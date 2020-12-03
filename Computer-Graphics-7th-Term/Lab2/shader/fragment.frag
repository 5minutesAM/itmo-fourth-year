#version 450 core

in vec3 frag_pos;
in vec3 frag_normal;
in vec2 frag_uv;

uniform vec3 light_position;
uniform sampler2D tex;

out layout (location = 0) vec4 out_color;

vec3 ambient_light()
{
    return vec3(0.15, 0.15, 0.2) * texture(tex, frag_uv).rgb;
}

vec3 diffuse_light()
{
    vec3 normal = normalize(frag_normal);
    vec3 light_direction = normalize(light_position - frag_pos);
    float diffuse = max(dot(normal, light_direction), 0.0);
    return vec3(0.8, 0.8, 0.85) * diffuse * texture(tex, frag_uv).rgb;
}

void main()
{
    vec3 color = ambient_light() + diffuse_light();
    out_color = vec4(color, 1.0);
}
