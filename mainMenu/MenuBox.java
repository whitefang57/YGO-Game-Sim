package mainMenu;

import buttonBox.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBox extends JFrame {
	public MenuBox() {
		setTitle("Main Menu");
		setSize(240, 230);
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

		duelButton.addActionListener(new DuelListener());
		deckEditButton.addActionListener(new DeckEditListener());
		shopPacksButton.addActionListener(new ShopPackListener());
		shopDecksButton.addActionListener(new ShopDeckListener());
		configButton.addActionListener(new OptionsListener());
		quitButton.addActionListener(new QuitListener());

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
		panel.add(buttonPaneTop, BorderLayout.PAGE_START);

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

	class DuelListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			DuelWinLossBox frame = new DuelWinLossBox();
			frame.setVisible(true);
			frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dispose();
		}
	}

	class DeckEditListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			DeckEditBox frame = new DeckEditBox();
			frame.setVisible(true);
			frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dispose();
		}
	}

	class ShopPackListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			PackShopBox frame = new PackShopBox();
			frame.setVisible(true);
			frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dispose();
		}
	}

	class ShopDeckListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			DeckShopBox frame = new DeckShopBox();
			frame.setVisible(true);
			frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dispose();
		}
	}

	class OptionsListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			OptionsBox frame = new OptionsBox();
			frame.setVisible(true);
			frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dispose();
		}
	}

	class QuitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for (Frame f : Frame.getFrames())
				f.dispose();
		}
	}
}
