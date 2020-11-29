#include <GL/glew.h>
#include <GLFW/glfw3.h>

#include <glm/gtc/type_ptr.hpp>

#include <iostream>
#include <memory>

#include "camera.hpp"
#include "glprogram.hpp"
#include "model.hpp"

const int width = 1600;
const int height = 1200;

std::unique_ptr<Camera> camera;

void mouse_callback(GLFWwindow* window, double xpos, double ypos)
{
    camera->on_mouse_movement(xpos, ypos);
}

// Process keys that don't depend on the press duration
void key_callback(GLFWwindow* window, int key, int scancode, int action, int mods)
{
    if (action == GLFW_PRESS)
    {
        switch (key)
        {
        case GLFW_KEY_ESCAPE:
            glfwSetWindowShouldClose(window, GL_TRUE);
            break;
        }
    }
}

int main()
{
    if (glfwInit() != GLFW_TRUE)
        return -1;

    // Use OpenGL 4.5 (forward compatibility removes deprecated functionality)
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 5);
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
    glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

    glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);

    GLFWwindow* window = glfwCreateWindow(width, height, "Computer Graphics Lab 2", nullptr, nullptr);
    glfwMakeContextCurrent(window);

    glewExperimental = GL_TRUE; // use GL 3.2+ core context
    if (auto err{glewInit()}; err != GLEW_OK)
    {
        std::cerr << "glewInit() error: " << glewGetErrorString(err) << std::endl;
        return -1;
    }

    glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
    glfwSetCursorPosCallback(window, mouse_callback);
    glfwSetKeyCallback(window, key_callback);

    TextureLoader tex_loader("../data");
    Model model("../data/desk.fbx", tex_loader);
    model.instantiate_meshes();

    GlProgram program({{"../shader/vertex.vert", GL_VERTEX_SHADER}, {"../shader/fragment.frag", GL_FRAGMENT_SHADER}});
    program.use();

    camera = std::make_unique<Camera>(width, height);

    GLint mvp_uniform = program.uniform_location("mvp");

    glEnable(GL_DEPTH_TEST);
    while (!glfwWindowShouldClose(window))
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glm::mat4 mvp = camera->vp_matrix() * glm::mat4(1.0f) /* model to world */;
        glUniformMatrix4fv(mvp_uniform, 1, GL_FALSE, glm::value_ptr(mvp));

        model.draw_meshes();

        glfwSwapBuffers(window);
        glfwPollEvents();

        glm::vec3 movement{0, 0, 0};
        if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS)
            movement += CAMERA_FWD;
        if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS)
            movement -= CAMERA_FWD;
        if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS)
            movement += CAMERA_RIGHT;
        if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS)
            movement -= CAMERA_RIGHT;
        if (glm::length(movement) > 0.0f)
            camera->on_key_movement(movement);
    }

    glfwTerminate();
}
