import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

class Main {
	public static void main(String[] args) {

		JWindow window = new JWindow();
		window.setLayout(null);

		// Play the music. NOT MY CODE
		try {
			File musicFile = new File("test.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
			System.out.println("Error playing music: " + e.getMessage());
		}

		// custom colour for the buttons
		String hexCode = "#0c0b51";
		Color customColor = Color.decode(hexCode);

		// Inserts Title
		JLabel titleLabel = new JLabel("", new ImageIcon(("title.png")), SwingConstants.CENTER);
		window.getContentPane().add(titleLabel);
		titleLabel.setBounds(0, 0, 800, 385);

		// Instruction label
		String instructions = "<html><center>Rule #1: Shoot with the Mouse, Move W and D<br>";
		instructions += "Rule #2: Minimum Threshhold for Victory is 20<br>";
		instructions += "Rule #3: Avoid Hitting Civilians and Destroy any Threats<br>";
		instructions += "Note - Score Decreases if Civilians are Hit !<br>";
		instructions += "</center></html>";
		JLabel instructionLabel = new JLabel(instructions, SwingConstants.CENTER);
		window.getContentPane().add(instructionLabel);
		instructionLabel.setBounds(289, 440, 220, 130);
		instructionLabel.setForeground(Color.WHITE);
		instructionLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));// Set custom border
		instructionLabel.setVisible(false);

		// add Play Button
		JButton playButton = new JButton("PLAY");
		playButton.setBackground(customColor);
		playButton.setForeground(Color.WHITE);// Set custom foreground color
		playButton.setFont(new Font("Fawn Script", Font.BOLD, 24));// Set custom font
		playButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));// Set custom border
		window.getContentPane().add(playButton);
		playButton.setBounds(300, 225, 200, 50);

		// Plays Game if user clicks "PLAY" button
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				window.dispose();
				JFrame frame = new GameFrame();
				frame.setVisible(true);
			}
		});

		// add Instruction Button
		JButton instructionButton = new JButton("INSTRUCTIONS");
		instructionButton.setBackground(customColor);
		instructionButton.setForeground(Color.WHITE);// Set custom foreground color
		instructionButton.setFont(new Font("Fawn Script", Font.BOLD, 24));// Set custom font
		instructionButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));// Set custom border
		window.getContentPane().add(instructionButton);
		instructionButton.setBounds(300, 300, 200, 50);

		instructionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				instructionLabel.setVisible(true);
			}
		});

		// add Quit Button
		JButton quitButton = new JButton("QUIT");
		quitButton.setBackground(customColor);
		quitButton.setForeground(Color.WHITE);// Set custom foreground color
		quitButton.setFont(new Font("Fawn Script", Font.BOLD, 24));// Set custom font
		quitButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));// Set custom border
		window.getContentPane().add(quitButton);
		quitButton.setBounds(300, 375, 200, 50);
		window.setBounds(300, 150, 800, 600);

		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Change the background color of JOptionPane
				UIManager.put("OptionPane.background", customColor);
				UIManager.put("Panel.background", customColor);
				UIManager.put("OptionPane.messageForeground", Color.WHITE);
				JOptionPane.showMessageDialog(window, "GAME OVER!");

			}
		});

		window.setVisible(true);

		// adds Background Image
		JLabel imageLabel = new JLabel("", new ImageIcon(("menubackground.png")), SwingConstants.CENTER);
		window.getContentPane().add(imageLabel);
		imageLabel.setBounds(0, 0, 800, 600);

		// Repaints to avoid buttons being hidden under the background image
		playButton.repaint();
		instructionButton.repaint();
		quitButton.repaint();
	}
}