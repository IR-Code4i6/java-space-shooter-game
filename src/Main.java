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

		// custom colour for the buttons
		String hexCode = "#0c0b51";
		Color customColor = Color.decode(hexCode);
		
		
		try {
            File musicFile = new File("test.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println("Error playing music: " + e.getMessage());
        }

		// Inserts Title
		JLabel titleLabel = new JLabel("", new ImageIcon(("title.png")), SwingConstants.CENTER);
		window.getContentPane().add(titleLabel);
		titleLabel.setBounds(0, 0, 800, 385);

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
				JOptionPane.showMessageDialog(window,
						"Instructions Are As Follows:Instructions Are As Follows:Instructions Are As Follows:");
			}
		});

		// add Options Button
		JButton optionsButton = new JButton("OPTIONS");
		optionsButton.setBackground(customColor);
		optionsButton.setForeground(Color.WHITE);// Set custom foreground color
		optionsButton.setFont(new Font("Fawn Script", Font.BOLD, 24));// Set custom font
		optionsButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));// Set custom border
		window.getContentPane().add(optionsButton);
		optionsButton.setBounds(300, 375, 200, 50);
		window.setBounds(300, 150, 800, 600);

		optionsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final JPanel panel = new JPanel();
				final JRadioButton button1 = new JRadioButton("Single Player ");
				final JRadioButton button2 = new JRadioButton("CO-OP");

				panel.add(button1);
				panel.add(button2);

				JOptionPane.showMessageDialog(null, panel);
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
		optionsButton.repaint();
	}
}
