package eu.blazesoft.raidz;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.input.Cursor;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;


public class Game {
	private long lastFrame;
	private int fps;
	private long lastFPS;
	
	private String gameTitle = "Raidz";
	// screen size, will rework that later
	private int	screenWidth = 800;
	private int	screenHeight = 600;
	
	public void start() {
		try {
			Display.setDisplayMode(new DisplayMode(screenWidth, screenHeight));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		init(); // init OpenGL
		//init_objects(); // init ingame objects
		getDelta(); // call once before loop to initialise lastFrame
		lastFPS = getTime(); // call before loop to initialise fps timer

		while (!Display.isCloseRequested()) {
			int delta = getDelta();
			
			input();
			update(delta);
			render();

			Display.update();
			Display.sync(60); // cap fps to 60fps
		}
		Display.destroy();
	}
	
	public int getDelta() {
	    long time = getTime();
	    int delta = (int) (time - lastFrame);
	    lastFrame = time;
	    return delta;
	}
	
	public long getTime() {
	    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	public void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle(gameTitle + " [FPS: " + fps+"]");
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}
	
	private void init() {
		// grab the mouse
		Mouse.setGrabbed(true);
		//Mouse.setNativeCursor(Cursor);
		// enable textures since we're going to use these for our sprites
		glEnable(GL_TEXTURE_2D);
		// disable the OpenGL depth test since we're rendering 2D graphics
		glDisable(GL_DEPTH_TEST);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, screenWidth, 0, screenHeight, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glViewport(0, 0, screenWidth, screenHeight);
		//
	}
	
	// function for handling input
	private void input() {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            Display.destroy();
            System.exit(0);
        }
		System.out.println(Mouse.getX() + " / " + Mouse.getY());
	}

	// main update function
	private void update(int delta) {
		//planet.update(delta);
		updateFPS(); // update FPS Counter
	}
	
	// main render function
	private void render() {
		// Clear The Screen And The Depth Buffer
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		//planet.render();
		glPushMatrix();
		glColor3f(1f, 0f, 0f);
        glTranslatef(Mouse.getX(), Mouse.getY(), 0);
        glBegin(GL_QUADS);
        //glTexCoord2f(0, 0);
        glVertex2f(-16, -16);
        //glTexCoord2f(1, 0);
        glVertex2f(16, -16);
        //glTexCoord2f(1, 1);
        glVertex2f(16, 16);
        //glTexCoord2f(0, 1);
        glVertex2f(-16, 16);
        glEnd();
    glPopMatrix();
	}
	
	public int getScreenCenterX() {
		return screenWidth/2;
	}
	
	public int getScreenCenterY() {
		return screenHeight/2;
	}
	
	public static void main(String[] argv) {
		Game game = new Game();
		game.start();
	}
	
}
