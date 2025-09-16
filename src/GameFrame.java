import javax.swing.*;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

// Class GameFrame:
// This class is responsible for creating and displaying the main game window.
// It sets window properties and initiates the game by adding the GamePanel to the frame.
public class GameFrame extends JFrame {

	// Class Variable:
	// panel: An instance of the GamePanel class which handles the game's mechanics.
	private GamePanel panel;

	// Constructor:
	// Initializes a GameFrame instance with a newly created GamePanel.
	// Sets the properties of the game window and makes it visible.
	public GameFrame() {

		panel = new GamePanel(); // Initializes the game panel

		setTitle("Game"); // Sets the title of the game window
		setResizable(false); // Disables the ability to resize the game window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensures the application closes when the window is closed
		add(panel); // Adds the game panel to the game frame

		pack(); // Adjusts the frame size to fit its components
		setLocationRelativeTo(null); // Centers the game window on the screen
		setVisible(true); // Makes the game window visible

	}

	// Method main:
	// The entry point of the game application.
	// Initiates the game by creating a new GameFrame instance.
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GameFrame(); // Initializes a new GameFrame object
			}
		});
	}
}
