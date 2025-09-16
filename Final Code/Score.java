import java.awt.*;

// Class Score:
// This class is responsible for managing and displaying the game score.
public class Score {

	// Class Variables:
	// SCORE_FONT: Font type for the score display.
	private static final Font SCORE_FONT = new Font("Arial", Font.BOLD, 20);
	// score: Holds the current score value.
	private int score;
	// xPos, yPos: Defines the position of the score on the screen.
	private int xPos, yPos;

	// Constructor:
	// Takes game width and height as parameters and initializes the score
	// and position for score display.
	public Score(int gameWidth, int gameHeight) {
		// Initialize score to 0
		this.score = 0;

		// Calculate the position for score display
		this.xPos = (gameWidth - 50) / 2; // Horizontal position
		this.yPos = 30; // Vertical position
	}

	// Function: increaseScore
	// Increases the score by a given amount.
	public void increaseScore(int amount) {
		this.score += amount;
	}

	// Function: getScore
	// Returns the current score.
	public int getScore() {
		return this.score;
	}

	// Function: draw
	// Takes Graphics object as parameter and draws the score on it.
	public void draw(Graphics g) {
		// Set color for the score
		g.setColor(Color.black);

		// Set font for the score
		g.setFont(SCORE_FONT);

		// Draw the score
		g.drawString("Score: " + score, xPos, yPos);
	}
}
