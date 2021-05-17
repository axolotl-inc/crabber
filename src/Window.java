import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import java.nio.IntBuffer;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Window implements Runnable{
        long window;
        Thread thread;
        public int WIDTH = 1080;
        public int HEIGHT = 720;

        public void start(){
                thread = new Thread(this,"main");
                thread.start();
        }

        public void run(){

                init();
                create();
                while(!GLFW.glfwWindowShouldClose(window)){
                        loop();
                }
                Callbacks.glfwFreeCallbacks(window);
                GLFW.glfwDestroyWindow(window);
                GLFW.glfwTerminate();
                GLFW.glfwSetErrorCallback(null).free();
                System.out.println("good bye!");
        }

        public void init(){
                GLFWErrorCallback.createPrint(System.err).set();

                if (!GLFW.glfwInit()){
                        System.exit(-1);
                }

                GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
                GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);

                window = GLFW.glfwCreateWindow(WIDTH, HEIGHT, "Crabber", MemoryUtil.NULL, MemoryUtil.NULL);

                if (window == MemoryUtil.NULL) {
                        System.exit(-1);
                }

                try (MemoryStack stack = MemoryStack.stackPush()){
                        IntBuffer width = stack.mallocInt(1);
                        IntBuffer height = stack.mallocInt(1);
                        GLFW.glfwGetWindowSize(window, width, height);
                        GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
                        GLFW.glfwSetWindowPos(window, (vidmode.width() - width.get(0)) / 2, (vidmode.height() - height.get(0)) / 2);
                }

                GLFW.glfwMakeContextCurrent(window);
                GL.createCapabilities();
                GLFW.glfwShowWindow(window);
        }

        public void create(){

        }

        public void loop(){
                processInput();
                GL11.glViewport(0, 0, WIDTH, HEIGHT);
                GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
                GLFW.glfwSwapBuffers(window);
                GLFW.glfwPollEvents();
        }

        public void processInput(){
                GL11.glClearColor(0.2f, 0.5f, 1.0f, 1.0f);
                if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS){
                        GLFW.glfwSetWindowShouldClose(window, true);
                }
        }

        public static void main(String[] args){
                new Window().start();
        }
}