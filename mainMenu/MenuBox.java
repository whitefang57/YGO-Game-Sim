package mainMenu;

import javax.swing.*;
import java.awt.*;

public class MenuBox extends JFrame {
	public MenuBox() {
		setTitle("Main Menu");
		setSize(250, 350);
		setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		getContentPane().add(panel);

		JLabel logoLabel = new JLabel(new ImageIcon("yugiohSmall.png"));
		JButton duelButton = new JButton("Duel");
		JButton deckEditButton = new JButton("Deck Edit");
		JButton shopPacksButton = new JButton("Shop Packs");
		JButton shopDecksButton = new JButton("Shop Decks");
		JButton configButton = new JButton("Options");
		JButton quitButton = new JButton("Quit");

		logoLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		duelButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
		deckEditButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
		shopPacksButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
		shopDecksButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
		configButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
		quitButton.setAlignmentX(JButton.CENTER_ALIGNMENT);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.Y_AXIS));

		buttonPane.add(logoLabel);
		buttonPane.add(Box.createVerticalStrut(10));
		buttonPane.add(duelButton);
		buttonPane.add(Box.createVerticalStrut(10));
		buttonPane.add(deckEditButton);
		buttonPane.add(Box.createVerticalStrut(10));
		buttonPane.add(shopPacksButton);
		buttonPane.add(Box.createVerticalStrut(10));
		buttonPane.add(shopDecksButton);
		buttonPane.add(Box.createVerticalStrut(10));
		buttonPane.add(configButton);
		buttonPane.add(Box.createVerticalStrut(10));
		buttonPane.add(quitButton);

		buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		panel.add(buttonPane, BorderLayout.CENTER);
	}
}
