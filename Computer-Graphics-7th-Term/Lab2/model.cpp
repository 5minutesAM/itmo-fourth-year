#include "model.hpp"

void Model::instantiate()
{
    if (empty())
        return;

    glGenBuffers(2, _vertex_buffers);
    glGenVertexArrays(1, &_vao);
    glBindVertexArray(_vao);

    glBindBuffer(GL_ARRAY_BUFFER, _vbo);
    glBufferData(GL_ARRAY_BUFFER, _vertices.size() * sizeof(Vertex), _vertices.data(), GL_STATIC_DRAW);

    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, _ebo);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, _indices.size() * sizeof(unsigned int), _indices.data(), GL_STATIC_DRAW);

    glEnableVertexAttribArray(0); /* position */
    glVertexAttribPointer(
        0, 3 /* vec3 */, GL_FLOAT, GL_FALSE /* not normalized */, sizeof(Vertex) /* stride */,
        (void*)offsetof(Vertex, position) /* offset */);

    glEnableVertexAttribArray(1); /* normal */
    glVertexAttribPointer(
        1, 3 /* vec3 */, GL_FLOAT, GL_FALSE /* not normalized */, sizeof(Vertex) /* stride */,
        (void*)offsetof(Vertex, normal) /* offset */);

    glEnableVertexAttribArray(2); /* uv */
    glVertexAttribPointer(
        2, 2 /* vec2 */, GL_FLOAT, GL_FALSE /* not normalized */, sizeof(Vertex) /* stride */,
        (void*)offsetof(Vertex, uv) /* offset */);

    glBindVertexArray(0);
}

void Model::draw() const
{
    if (empty())
        return;

    if (_texture)
        glBindTextureUnit(0, *_texture);

    glBindVertexArray(_vao);
    glDrawElements(GL_TRIANGLES, _indices.size(), GL_UNSIGNED_INT, 0);
    glBindVertexArray(0);
}
