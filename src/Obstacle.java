import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Obstacle extends Rectangle {

	public int speed; // horizontal speed
	public static final int OBS_DIAMETER = 20; // size of obstacle

	public boolean hit = false;
	private Image image;
	// constructor creates the obstacle and sets initial falling speed
	public Obstacle(int x, int y, int speed) {
		super(x, y, OBS_DIAMETER, OBS_DIAMETER);
		this.speed = speed;
		loadImage();
	}

	// update the position of the obstacle (called from GamePanel frequently)
	public void move(int width, int height) {
		x = x + speed;
		if(x>width) {
			y = (int) (Math.random() * height - Obstacle.OBS_DIAMETER - 50);
			x = 0;
		}
	}
	
	private void loadImage() {
		try {
			image = ImageIO.read(new File("plane.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// draw the obstacle to the screen (called from GamePanel frequently)
	public void draw(Graphics g) {
		if(!hit) {
			g.drawImage(image, x, y, OBS_DIAMETER *15, OBS_DIAMETER*4, null);
		}
		
	}

}