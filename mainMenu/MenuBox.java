package mainMenu;

import javax.swing.*;
import java.awt.*;

public class MenuBox extends JFrame {
	public MenuBox() {
		setTitle("Main Menu");
		setSize(250, 225);
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

		duelButton.setAlignmentX(JButton.RIGHT_ALIGNMENT);
		shopPacksButton.setAlignmentX(JButton.RIGHT_ALIGNMENT);
		configButton.setAlignmentX(JButton.RIGHT_ALIGNMENT);

		deckEditButton.setAlignmentX(JButton.LEFT_ALIGNMENT);
		shopDecksButton.setAlignmentX(JButton.LEFT_ALIGNMENT);
		quitButton.setAlignmentX(JButton.LEFT_ALIGNMENT);


		JPanel buttonPaneTop = new JPanel();
		buttonPaneTop.setLayout(new BoxLayout(buttonPaneTop, BoxLayout.Y_AXIS));
		buttonPaneTop.add(logoLabel);
		buttonPaneTop.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		panel.add(buttonPaneTop,BorderLayout.PAGE_START);

		JPanel buttonPaneLeft = new JPanel();
		buttonPaneLeft.setLayout(new BoxLayout(buttonPaneLeft, BoxLayout.Y_AXIS));
		buttonPaneLeft.add(duelButton);
		buttonPaneLeft.add(Box.createVerticalStrut(5));
		buttonPaneLeft.add(shopPacksButton);
		buttonPaneLeft.add(Box.createVerticalStrut(5));
		buttonPaneLeft.add(configButton);
		buttonPaneLeft.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		panel.add(buttonPaneLeft, BorderLayout.LINE_START);

		JPanel buttonPaneRight = new JPanel();
		buttonPaneRight.setLayout(new BoxLayout(buttonPaneRight, BoxLayout.Y_AXIS));
		buttonPaneRight.add(deckEditButton);
		buttonPaneRight.add(Box.createVerticalStrut(5));
		buttonPaneRight.add(shopDecksButton);
		buttonPaneRight.add(Box.createVerticalStrut(5));
		buttonPaneRight.add(quitButton);
		buttonPaneRight.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		panel.add(buttonPaneRight, BorderLayout.LINE_END);
	}
}
