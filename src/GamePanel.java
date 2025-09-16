import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable, KeyListener {

	// Constants:
	private static final int GAME_WIDTH = 600; // Width of the game window
	private static final int GAME_HEIGHT = 700; // Height of the game window
	private static final int DELAY = 10; // Delay between frames in milliseconds
	private static final int DOUBLE_BULLET_TIME = 20 * 1000; // Time in milliseconds to double the number of bullets
	private static final long FIRE_DELAY = 200; // Delay between fires in milliseconds
	private static final int MAX_BULLETS = 2; // Max bullets that can be fired per delay
	private static final long ALIEN_BULLET_DELAY = 3000; // Delay between alien bullets in milliseconds

	// Variables:
	private boolean gunHitByAlienBullet; // Flag to track if the gun was hit by an alien bullet
	private Thread animator; // Thread for game animation
	private Alien alien; // The alien object
	private Gun gun; // The gun object
	private List<Bullet> bullets; // List of bullets
	private Score score; // The score object
	private List<Background> backgroundLayers; // List of background layers
	private Random random;
	private long timeRemaining;
	private long lastFireTime; // The last time a bullet was fired
	private int bulletsFired; // The number of bullets fired within the delay
	private String endMessage = "";
	private Obstacle obs; // Creates the obstacle that the user should avoid
	private long lastBulletDoublingTime; // The last time the bullet count was doubled
	private long lastAlienBulletTime; // Last time the alien shot a bullet
	private List<Bullet> alienBullets; // List of bullets fired by the alien

	// Constructs a new GamePanel object.
	public GamePanel() {
		super(); // Call the parent class constructor.

		// Set the preferred size of the game panel.
		setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
		setFocusable(true); // Set the panel to be focusable.
		requestFocus(); // Request focus for the panel.

		random = new Random(); // Create a Random object for generating random values.

		alien = new Alien(GAME_WIDTH / 2, GAME_HEIGHT / 2); // Create an alien at the center of the game window
		gun = new Gun(GAME_WIDTH, GAME_HEIGHT); // Create a gun object
		bullets = new ArrayList<>(); // Initialize the list of bullets
		score = new Score(GAME_WIDTH, GAME_HEIGHT); // Create a score object
		obs = new Obstacle((int) (Math.random() * GAME_HEIGHT - Obstacle.OBS_DIAMETER - 50), 1, 5); // Create an
																									// obstacle
		alienBullets = new ArrayList<>(); // Initialize the list of alien bullets
		gunHitByAlienBullet = false; // Initialize the flag

		// Create the background layers
		backgroundLayers = new ArrayList<>();
		backgroundLayers.add(new Background("background_layer1.png", 0.1));
		backgroundLayers.add(new Background("background_layer2.png", 0.2));
		backgroundLayers.add(new Background("background_layer3.png", 0.3));
		backgroundLayers.add(new Background("background_layer4.png", 0.4));
		backgroundLayers.add(new Background("background_layer5.png", 0.5));
		backgroundLayers.add(new Background("background_layer6.png", 0.6));
		backgroundLayers.add(new Background("background_layer7.png", 0.7));

		// Add key listener to the Gun object
		gun.addKeyListener(this);
		addKeyListener(this); // Register the GamePanel as a KeyListener

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				// Check if the fire delay has passed or the max bullets has not been reached
				if (System.currentTimeMillis() - lastFireTime >= FIRE_DELAY || bulletsFired < MAX_BULLETS) {
					bullets.add(new Bullet(gun.x + (bulletsFired * Bullet.BULLET_SIZE), gun.y, e.getX(), e.getY())); // Fire
																														// a
																														// bullet
					bulletsFired++; // Increment the number of bullets fired

					// If the fire delay has passed, reset the last fire time and the number of
					// bullets fired
					if (System.currentTimeMillis() - lastFireTime >= FIRE_DELAY) {
						lastFireTime = System.currentTimeMillis();
						bulletsFired = 1;
					}
				}
			}
		});
	}

	// handles the event when a key is pressed
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_A) {
			gun.move(-5, 0, getWidth(), getHeight()); // Move the gun left by 5 units
		} else if (keyCode == KeyEvent.VK_D) {
			gun.move(5, 0, getWidth(), getHeight()); // Move the gun right by 5 units
		}
	}

	// handles the event when a key is released
	public void keyReleased(KeyEvent e) {
	}

	// Start the game animation when the panel is added to the frame.
	public void addNotify() {
		super.addNotify();
		// Start the animation thread if it is not already running.
		if (animator == null || !animator.isAlive()) {
			animator = new Thread(this);
			animator.start();
		}
	}

	// Override the paintComponent method to paint the game elements on the panel.
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Draw the background layers
		for (Background layer : backgroundLayers) {
			layer.draw(g);
		}

		alien.draw(g); // Draw the alien
		gun.draw(g); // Draw the gun
		for (Bullet bullet : bullets) {
			bullet.draw(g); // Draw each bullet
		}
		obs.draw(g); // draw obstacle
		score.draw(g); // Draw the score

		// Set the color and font for drawing the time remaining
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 20));

		// Draw the time remaining on the panel
		g.drawString("Time: " + timeRemaining, 10, 20);

		// Draw the end message
		// Check if the end message is not empty
		if (!endMessage.isEmpty()) {
			// Set the color to yellow
			g.setColor(Color.YELLOW);

			// Set the font for drawing the end message
			g.setFont(new Font("Arial", Font.BOLD, 30));

			// Get the font metrics for calculating the position of the end message
			FontMetrics metrics = g.getFontMetrics(g.getFont());

			// Calculate the x-coordinate to center the end message horizontally
			int x = (GAME_WIDTH - metrics.stringWidth(endMessage)) / 2;

			// Calculate the y-coordinate to center the end message vertically
			int y = GAME_HEIGHT / 2;

			// Draw the end message at the calculated coordinates
			g.drawString(endMessage, x, y);
		}

		// Draw the alien's bullets
		for (Bullet alienBullet : alienBullets) {
			alienBullet.draw(g); // Draw each alien bullet
		}

		Toolkit.getDefaultToolkit().sync(); // Sync the graphics state
	}

	// The run method executes the main game loop.
	// It updates the positions of game objects, checks for collisions,
	// updates the score, and repaints the panel.
	// After the game ends, it checks the final score and displays the end message.
	public void run() {
		long startTime = System.currentTimeMillis();
		long endTime = startTime + 30 * 1000; // 30 seconds
		int timer = 0;

		lastAlienBulletTime = System.currentTimeMillis(); // Initialize the last alien bullet time

		while (System.currentTimeMillis() < endTime) {
			alien.move(GAME_WIDTH, GAME_HEIGHT); // Move the alien
			obs.move(GAME_WIDTH, GAME_HEIGHT); // Move the obstacle

			if (timer < 100) {
				timer++;
			}

			// Check if the alien should shoot a bullet
			if (System.currentTimeMillis() - lastAlienBulletTime >= ALIEN_BULLET_DELAY) {
				for (int i = 0; i < 3; i++) { // Fire 3 bullets
					Bullet alienBullet = alien.shootBullet(gun); // Alien shoots a bullet towards the gun
					alienBullets.add(alienBullet); // Add the alien's bullet to the list of alien bullets
				}
				lastAlienBulletTime = System.currentTimeMillis(); // Update the last alien bullet time
			}

			// Move and check collisions for the alien bullets
			for (int i = alienBullets.size() - 1; i >= 0; i--) {
				Bullet alienBullet = alienBullets.get(i);
				alienBullet.move();

				// Check for collisions with the gun
				if (alienBullet.intersects(gun)) {
					gunHitByAlienBullet = true; // Set the flag to true
					alienBullets.remove(i); // Remove the bullet from the list
					break; // Exit the loop after the collision is detected
				}

				// Check for collisions with other game objects (if applicable)
				// ...

				// Remove bullets that have moved off the screen
				if (alienBullet.getY() < 0 || alienBullet.getY() > GAME_HEIGHT) {
					alienBullets.remove(i); // Remove the bullet from the list
				}

				// Update the score based on the flag
				if (gunHitByAlienBullet) {
					score.increaseScore(-2); // Subtract 2 points from the score
					gunHitByAlienBullet = false; // Reset the flag
				}
			}

			for (Bullet bullet : bullets) {
				bullet.move(); // Move each bullet

				if (bullet.intersects(obs)) {
					if (timer >= 100) {
						score.increaseScore(-1); // Decrease score
						timer = 0;
						obs.x = 0;
						obs.y = (int) (Math.random() * GAME_HEIGHT - Obstacle.OBS_DIAMETER - 50);
					}
				}

				if (bullet.intersects(alien)) {
					bullets.remove(bullet); // Remove the bullet if it hits the alien
					score.increaseScore(1); // Increase the score by 1

					// Reset the alien's position to avoid the bullet
					int targetX = random.nextInt(GAME_WIDTH - Alien.ALIEN_DIAMETER);
					int targetY = random.nextInt(GAME_HEIGHT - Alien.ALIEN_DIAMETER);

					// Calculate the new position for the alien based on the bullet's position
					if (bullet.getX() < alien.getX()) {
						alien.x = targetX + Alien.ALIEN_DIAMETER;
					} else {
						alien.x = targetX - Alien.ALIEN_DIAMETER;
					}

					if (bullet.getY() < alien.getY()) {
						alien.y = targetY + Alien.ALIEN_DIAMETER;
					} else {
						alien.y = targetY - Alien.ALIEN_DIAMETER;
					}

					break; // Exit the loop to avoid concurrent modification
				}
			}

			// Update the background layers
			for (Background layer : backgroundLayers) {
				layer.update(alien.getSpeed());
			}

			repaint(); // Repaint the panel

			timeRemaining = (endTime - System.currentTimeMillis()) / 1000; // Calculate the remaining time in seconds

			try {
				Thread.sleep(DELAY); // Delay between frames
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}

		// After the game ends, check the score and set the appropriate end message
		if (score.getScore() >= 20) {
			endMessage = "Earth is safe!";
		} else {
			endMessage = "Alien took over!";
		}
		repaint(); // Repaint the panel to display the end message
	}

	// The Background class represents a background layer of the game.
	private class Background {
		private Image image; // Image for the background layer
		private double speed; // Speed of the background layer
		private int x; // X position of the background layer

		// Constructs a new Background object with the specified image path and speed.
		public Background(String imagePath, double speed) {
			try {
				image = ImageIO.read(new File(imagePath)); // Load the image
			} catch (IOException e) {
				e.printStackTrace();
			}

			this.speed = speed;
			x = 0;
		}

		// Update the background layer's position based on the alien's speed.
		public void update(double alienSpeed) {
			// Update the X position based on the speed and alien's speed
			x -= (int) (speed + alienSpeed);
			if (x < -image.getWidth(null)) {
				x = 0;
			}
		}

		// Draw the background layer on the graphics object.
		public void draw(Graphics g) {
			g.drawImage(image, x, 0, null);

			// Draw the image again for seamless looping
			if (x < 0) {
				g.drawImage(image, x + image.getWidth(null), 0, null);
			}
		}
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

}
