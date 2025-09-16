import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

// Class Bullet:
// This class is responsible for creating, controlling, and rendering bullet objects in the game.
public class Bullet extends Rectangle {

	// Class Constants:
	// BULLET_SIZE: Specifies the size of a bullet.
	static final int BULLET_SIZE = 40;
	// BULLET_SPEED: Defines the speed at which a bullet moves.
	private static final int BULLET_SPEED = 15;

	// Class Variables:
	// xVelocity, yVelocity: Variables controlling the bullet's trajectory.
	private int xVelocity, yVelocity;
	// image: Image object representing the bullet.
	private BufferedImage image;
	
	//bullet2

	// Constructor:
	// Initializes a bullet at the given starting coordinates with the specified
	// target coordinates.
	public Bullet(int startX, int startY, int targetX, int targetY) {
	    super(startX, startY, BULLET_SIZE, BULLET_SIZE);
	    calculateVelocity(targetX, targetY);
	    loadImage();
	}


	// Function: calculateVelocity
	// Computes the bullet's velocity based on its target's location.
	private void calculateVelocity(int targetX, int targetY) {
		double angle = Math.atan2(targetY - y, targetX - x);
		xVelocity = (int) (BULLET_SPEED * Math.cos(angle));
		yVelocity = (int) (BULLET_SPEED * Math.sin(angle));
	}

	// Function: loadImage
	// Loads the image file representing the bullet.
	private void loadImage() {
		
		
		try {
			image = ImageIO.read(new File("bullet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Function: move
	// Modifies the bullet's position based on its velocity.
	public void move() {
		x += xVelocity;
		y += yVelocity;
	}

	// Function: draw
	// Renders the bullet using the provided Graphics object.
	public void draw(Graphics g) {
		g.drawImage(image, x, y, BULLET_SIZE, BULLET_SIZE, null);
	}
}
