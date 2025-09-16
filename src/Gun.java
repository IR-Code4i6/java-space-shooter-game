import java.awt.*;
import javax.swing.ImageIcon;

// The Gun class represents a gun in the game. It handles the movement and drawing of the gun.
public class Gun extends Rectangle {

    private final int GUN_WIDTH; // Width of the gun
    private final int GUN_HEIGHT; // Height of the gun
    private Image gunImage; // Image for the gun

    // Constructor:
    // Initializes a Gun object with the specified game width and height.
    // Sets the gun's position at the bottom center of the game window.
    // Loads the gun image from a file.
    // gameWidth: The width of the game window
    // gameHeight: The height of the game window
    public Gun(int gameWidth, int gameHeight) {
        GUN_WIDTH = 100;
        GUN_HEIGHT = 80;
        x = (gameWidth - GUN_WIDTH) / 2; // Set the x position of the gun
        y = gameHeight - GUN_HEIGHT; // Set the y position of the gun
        gunImage = loadImage("gun.png"); // Load the gun image from the specified file path
    }

    // Method move:
    // Moves the gun based on the specified direction.
    // dx: The change in x position
    // dy: The change in y position
    // gameWidth: The width of the game window
    // gameHeight: The height of the game window
    public void move(int dx, int dy, int gameWidth, int gameHeight) {
        int newX = x + dx * 2;
        int newY = y + dy * 2;

        // Check if the new position is within the game window's bounds
        if (newX >= 0 && newX + GUN_WIDTH <= gameWidth) {
            x = newX; // Update the x position if within bounds
        }

        if (newY >= 0 && newY + GUN_HEIGHT <= gameHeight) {
            y = newY; // Update the y position if within bounds
        }
    }

    // Method draw:
    // Draws the gun on the graphics object.
    // g: The Graphics object to draw on
    public void draw(Graphics g) {
        if (gunImage != null) {
            int gunWidth = GUN_WIDTH * 1; // Double the width of the gun image
            int gunHeight = GUN_HEIGHT * 1; // Double the height of the gun image
            g.drawImage(gunImage, x, y, gunWidth, gunHeight, null);
        }
    }

    // Method loadImage:
    // Loads an image file from the specified path.
    // Returns the loaded image, or null if the image file cannot be found or loaded.
    // imagePath: The path to the image file
    private Image loadImage(String imagePath) {
        try {
            return new ImageIcon(imagePath).getImage();
        } catch (Exception e) {
            System.out.println("Failed to load image: " + imagePath);
            e.printStackTrace();
            return null;
        }
    }

    // Method addKeyListener:
    // Adds a KeyListener to the GamePanel.
    // gamePanel: The GamePanel to add the KeyListener to
    public void addKeyListener(GamePanel gamePanel) {
        // TODO: Implement this method
    }
}
