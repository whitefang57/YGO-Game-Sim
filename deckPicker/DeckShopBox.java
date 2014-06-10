package deckPicker;

import mainMenu.MenuBox;
import ygo.YGOReader;
import ygo.YGOWriter;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DeckShopBox extends JFrame {
	private DefaultListModel<String> listModel;
	private JList<String> list;
	private ArrayList<String> deckList;
	private ArrayList<String> cardsInTrunk;
	private ArrayList<Integer> statistics;
	private int decksUnlocked;
	private JButton add;
	private JButton subtract;
	private JButton open;
	private int duelPoints;
	private JLabel duelPointDisplay;

	public DeckShopBox() {
		deckList = YGOReader.readDeckList();
		cardsInTrunk = YGOReader.readTrunk();
		statistics = YGOReader.readStats();
		//0 is unlocked packs, 1 is unlocked decks, 2 is total dp,
		decksUnlocked = statistics.get(1);
		duelPoints = statistics.get(2);

		setTitle("Pick a Pack");
		setSize(300, 400);
		setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		getContentPane().add(panel);

		listModel = new DefaultListModel<String>();
		int i = 0;
		for (String s : deckList) {
			if (i < decksUnlocked)
				listModel.addElement(s);
			i++;
		}

		list = new JList<String>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(1);
		list.addMouseListener(new DoubleClickListener());

		panel.add(new JScrollPane(list), BorderLayout.CENTER);

		open = new JButton("Open Deck");
		add = new JButton("+");
		subtract = new JButton("-");
		JButton clean = new JButton("Clean Trunk");
		duelPointDisplay = new JLabel("DP: " + duelPoints);

		open.addActionListener(new OpenListener());
		add.addActionListener(new UnlockListener(add));
		subtract.addActionListener(new UnlockListener(subtract));
		clean.addActionListener(new CleanListener());

		add.setEnabled(decksUnlocked != deckList.size());
		subtract.setEnabled(decksUnlocked > 0);

		JPanel buttonPaneOne = new JPanel();

		buttonPaneOne.add(open);
		Spacer.addSpace(buttonPaneOne);
		buttonPaneOne.add(add);
		Spacer.addSpace(buttonPaneOne);
		buttonPaneOne.add(subtract);

		panel.add(buttonPaneOne, BorderLayout.PAGE_END);

		JPanel buttonPaneTwo = new JPanel();

		buttonPaneTwo.add(clean);
		Spacer.addSpace(buttonPaneTwo);
		buttonPaneTwo.add(duelPointDisplay);

		panel.add(buttonPaneTwo, BorderLayout.PAGE_START);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				MenuBox frame = new MenuBox();
				frame.setVisible(true);
				frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			}
		});
	}

	class DoubleClickListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				open.doClick();
			}
		}
	}

	class OpenListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (list.getSelectedIndex() == -1) {
				JOptionPane.showMessageDialog(null, "Please Select a Deck", "ERROR", JOptionPane.WARNING_MESSAGE);
				return;
			}

			if (1500 > duelPoints) {
				JOptionPane.showMessageDialog(null, "Not Enough DP", "ERROR",
						JOptionPane.WARNING_MESSAGE);
			} else {
				for (String s : YGOReader.readPack(deckList.get(list.getSelectedIndex()))) {
					cardsInTrunk.add(s);
				}

				list.clearSelection();
				duelPoints -= 1500;
				statistics.set(2, duelPoints);
				YGOWriter.writeStats(statistics);
				duelPointDisplay.setText("DP: " + duelPoints);
				JOptionPane.showMessageDialog(null, "Cards added to trunk", "", JOptionPane.INFORMATION_MESSAGE);
				YGOWriter.writeTrunk(cardsInTrunk);
			}
		}
	}

	class UnlockListener implements ActionListener {
		private String type;

		public UnlockListener(JButton b) {
			type = b.getText();
		}

		public void actionPerformed(ActionEvent e) {
			if (type.equals("+") && decksUnlocked < deckList.size()) {
				decksUnlocked++;
				statistics.set(1, decksUnlocked);
				YGOWriter.writeStats(statistics);
				listModel.insertElementAt(deckList.get(decksUnlocked - 1), decksUnlocked - 1);
				list.setSelectedIndex(decksUnlocked - 1);
				list.ensureIndexIsVisible(decksUnlocked - 1);
			}

			if (type.equals("-") && decksUnlocked > 0) {
				decksUnlocked--;
				statistics.set(1, decksUnlocked);
				YGOWriter.writeStats(statistics);
				listModel.remove(decksUnlocked);
				list.setSelectedIndex(decksUnlocked - 1);
				list.ensureIndexIsVisible(decksUnlocked - 1);
			}

			add.setEnabled(decksUnlocked != deckList.size());
			subtract.setEnabled(decksUnlocked > 0);
		}
	}

	class CleanListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int cardsRemoved = 0;
			for (int i = 0; i < cardsInTrunk.size() - 3; i++)
				if (cardsInTrunk.get(i).equals(cardsInTrunk.get(i + 1)) &&
						cardsInTrunk.get(i).equals(cardsInTrunk.get(i + 2)) &&
						cardsInTrunk.get(i).equals(cardsInTrunk.get(i + 3))) {
					cardsInTrunk.remove(i + 3);
					cardsRemoved++;
					i--;
				}
			YGOWriter.writeTrunk(cardsInTrunk);
			duelPoints += cardsRemoved * 20;
			JOptionPane.showMessageDialog(null, "You gained " + cardsRemoved * 20 + " DP", "DP Earned",
					JOptionPane.INFORMATION_MESSAGE);
			statistics.set(2, duelPoints);
			duelPointDisplay.setText("DP: " + duelPoints);
			YGOWriter.writeStats(statistics);
		}
	}

	static class Spacer {
		public static void addSpace(JPanel buttonPane) {
			buttonPane.add(Box.createHorizontalStrut(5));
		}
	}
}
