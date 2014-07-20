package buttonBox;

import mainMenu.MenuBox;
import ygoUtil.XMLHandler;
import ygoUtil.YGOReader;
import ygoUtil.YGOResource;
import ygoUtil.YGOWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DeckShopBox extends JFrame {
	private DefaultListModel<String> listModel;
	private JList<String> list;
	private ArrayList<String> deckList;
	private ArrayList<String> cardsInTrunk;
	private YGOResource resources;
	private int decksUnlocked;
	private JButton add;
	private JButton subtract;
	private JButton open;
	private int duelPoints;
	private JLabel duelPointDisplay;

	public DeckShopBox() {
		deckList = YGOReader.readDeckList();
		cardsInTrunk = YGOReader.readTrunk();
		resources = XMLHandler.readResources();
		decksUnlocked = PacksUnlockedToDecksUnlocked.swap(resources.getUnlockedPacks());
		resources.setUnlockedDecks(decksUnlocked);
		duelPoints = resources.getTotalDuelPoints();

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

		if (resources.getDebugMode() == 1) {
			add.setEnabled(decksUnlocked != deckList.size());
			subtract.setEnabled(decksUnlocked > 0);
		} else {
			add.setEnabled(false);
			subtract.setEnabled(false);
		}

		JPanel buttonPaneOne = new JPanel();

		buttonPaneOne.add(open);
		buttonPaneOne.add(Box.createHorizontalStrut(5));
		buttonPaneOne.add(add);
		buttonPaneOne.add(Box.createHorizontalStrut(5));
		buttonPaneOne.add(subtract);

		panel.add(buttonPaneOne, BorderLayout.PAGE_END);

		JPanel buttonPaneTwo = new JPanel();

		buttonPaneTwo.add(clean);
		buttonPaneTwo.add(Box.createHorizontalStrut(5));
		buttonPaneTwo.add(duelPointDisplay);

		panel.add(buttonPaneTwo, BorderLayout.PAGE_START);
		XMLHandler.writeResources(resources);

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
			if (1500 > duelPoints && resources.getDebugMode() == 0) {
				JOptionPane.showMessageDialog(null, "Not Enough DP", "ERROR",
						JOptionPane.WARNING_MESSAGE);
			} else {
				for (String s : YGOReader.readPack(deckList.get(list.getSelectedIndex()))) {
					cardsInTrunk.add(s);
				}
				list.clearSelection();
				if (resources.getDebugMode() == 0) {
					duelPoints -= 1500;
					resources.setTotalDuelPoints(duelPoints);
					XMLHandler.writeResources(resources);
					duelPointDisplay.setText("DP: " + duelPoints);
					YGOWriter.writeTrunk(cardsInTrunk);
				}
				JOptionPane.showMessageDialog(null, "Cards added to trunk", "", JOptionPane.INFORMATION_MESSAGE);
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
				listModel.insertElementAt(deckList.get(decksUnlocked - 1), decksUnlocked - 1);
				list.setSelectedIndex(decksUnlocked - 1);
				list.ensureIndexIsVisible(decksUnlocked - 1);
			}

			if (type.equals("-") && decksUnlocked > 0) {
				decksUnlocked--;
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
			resources.setTotalDuelPoints(duelPoints);
			duelPointDisplay.setText("DP: " + duelPoints);
			XMLHandler.writeResources(resources);
		}
	}

	static class PacksUnlockedToDecksUnlocked {
		public static int swap(int packsUnlocked) {
			if (packsUnlocked >= 100) {
				return 43;
			} else if (packsUnlocked >= 99) {
				return 42;
			} else if (packsUnlocked >= 98) {
				return 40;
			} else if (packsUnlocked >= 96) {
				return 39;
			} else if (packsUnlocked >= 93) {
				return 38;
			} else if (packsUnlocked >= 91) {
				return 37;
			} else if (packsUnlocked >= 89) {
				return 36;
			} else if (packsUnlocked >= 87) {
				return 35;
			} else if (packsUnlocked >= 86) {
				return 34;
			} else if (packsUnlocked >= 82) {
				return 33;
			} else if (packsUnlocked >= 80) {
				return 32;
			} else if (packsUnlocked >= 76) {
				return 30;
			} else if (packsUnlocked >= 70) {
				return 29;
			} else if (packsUnlocked >= 67) {
				return 28;
			} else if (packsUnlocked >= 65) {
				return 27;
			} else if (packsUnlocked >= 60) {
				return 26;
			} else if (packsUnlocked >= 57) {
				return 25;
			} else if (packsUnlocked >= 56) {
				return 24;
			} else if (packsUnlocked >= 52) {
				return 23;
			} else if (packsUnlocked >= 50) {
				return 22;
			} else if (packsUnlocked >= 47) {
				return 21;
			} else if (packsUnlocked >= 42) {
				return 20;
			} else if (packsUnlocked >= 39) {
				return 19;
			} else if (packsUnlocked >= 32) {
				return 17;
			} else if (packsUnlocked >= 30) {
				return 16;
			} else if (packsUnlocked >= 29) {
				return 15;
			} else if (packsUnlocked >= 28) {
				return 14;
			} else if (packsUnlocked >= 27) {
				return 13;
			} else if (packsUnlocked >= 24) {
				return 12;
			} else if (packsUnlocked >= 22) {
				return 11;
			} else if (packsUnlocked >= 19) {
				return 10;
			} else if (packsUnlocked >= 18) {
				return 8;
			} else if (packsUnlocked >= 14) {
				return 6;
			} else if (packsUnlocked >= 8) {
				return 4;
			} else if (packsUnlocked >= 1) {
				return 2;
			}
			return 0;
		}
	}
}
