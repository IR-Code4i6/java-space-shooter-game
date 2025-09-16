import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Random;

/**
 * Represents an alien character in the game.
 */
public class Alien extends Rectangle {

	// Class Variables:
	static final int ALIEN_DIAMETER = 120;
	private static final double ALIEN_SPEED = 5;
	private static final String IMAGE_PATH = "alien.png";
	private Random random;

	// Instance Variables:
	private int xVelocity;
	private int yVelocity;
	private Image image;
	private double speed;

	// Initializes the alien character at the specified position.
	public Alien(int x, int y) {
		super(x, y, ALIEN_DIAMETER, ALIEN_DIAMETER);
		xVelocity = 1; // The x-coordinate of the alien's position.
		yVelocity = 1; // The y-coordinate of the alien's position.
		random = new Random();
		loadImage();
		speed = ALIEN_SPEED;
	}

	// Loads the image file representing the alien character.
	private void loadImage() {
		try {
			image = ImageIO.read(new File(IMAGE_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Controls the movement of the alien on the game screen.
	// gameWidth The width of the game screen.
	// gameHeight The height of the game screen.
	public void move(int gameWidth, int gameHeight) {
		x += xVelocity;
		y += yVelocity;
		handleWallCollision(gameWidth, gameHeight);
	}

	// Checks if the alien has hit a wall and handles the collision accordingly.
	// gameWidth The width of the game screen.
	// gameHeight The height of the game screen.
	private void handleWallCollision(int gameWidth, int gameHeight) {

		// Check if the alien has hit a wall horizontally and reverse its xVelocity if
		// so.
		if (x <= 0 || x >= gameWidth - ALIEN_DIAMETER) {
			xVelocity *= -1;

			// Ensure the alien remains within the game screen bounds.
			x = Math.max(0, Math.min(gameWidth - ALIEN_DIAMETER, x));
		}
		// Check if the alien has hit a wall vertically and reverse its yVelocity if so.
		if (y <= 0 || y >= gameHeight - ALIEN_DIAMETER) {
			yVelocity *= -1;

			// Ensure the alien remains within the game screen bounds.
			y = Math.max(0, Math.min(gameHeight - ALIEN_DIAMETER, y));
		}
	}

	// Shoots a bullet towards the specified target.
	// param target The target rectangle to shoot at.
	// return The created bullet object.
	public Bullet shootBullet(Rectangle target) {
		int startX = x + width / 2;
		int startY = y + height / 2;

		// Calculate the target position with some random deviation.
		int targetX = target.x + target.width / 2 + random.nextInt(10) - 5;
		int targetY = target.y + target.height / 2 + random.nextInt(10) - 5;
		return new Bullet(startX, startY, targetX, targetY);
	}

	// Draws the alien on the provided Graphics object.
	// The Graphics object to draw on.
	public void draw(Graphics g) {
		if (image != null && g != null) {
			g.drawImage(image, x, y, ALIEN_DIAMETER, ALIEN_DIAMETER, null);
		}
	}

	// Retrieves the speed of the alien.
	// return The speed of the alien.
	public double getSpeed() {
		return speed;
	}
}
