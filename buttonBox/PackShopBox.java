package buttonBox;

import mainMenu.MenuBox;
import ygoUtil.YGOReader;
import ygoUtil.YGOWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PackShopBox extends JFrame {
	private DefaultListModel<String> listModel;
	private JList<String> list;
	private JTextField numberOfPacks;
	private ArrayList<String> fullPackList;
	private ArrayList<String> cardsInTrunk;
	private ArrayList<Integer> statistics;
	private int packsUnlocked;
	private JButton add;
	private JButton subtract;
	private JButton open;
	private int duelPoints;
	private JLabel duelPointDisplay;

	public PackShopBox() {
		fullPackList = YGOReader.readPackList();
		cardsInTrunk = YGOReader.readTrunk();
		statistics = YGOReader.readStats();
		// 0 is unlocked packs, 1 is unlocked decks, 2 is total dp, 3 is wins, 4 is losses,
		// 5 is debugMode on or off (1 is on, 0 is off)
		packsUnlocked = 9 + statistics.get(4) / 3;
		duelPoints = statistics.get(2);

		setTitle("Pick a Pack");
		setSize(300, 400);
		setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		getContentPane().add(panel);

		listModel = new DefaultListModel<String>();
		int i = 0;
		for (String s : fullPackList) {
			if (i < packsUnlocked)
				listModel.addElement(s);
			i++;
		}

		list = new JList<String>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(1);
		list.addMouseListener(new DoubleClickListener());

		panel.add(new JScrollPane(list), BorderLayout.CENTER);

		open = new JButton("Open Pack");
		numberOfPacks = new JTextField();
		add = new JButton("+");
		subtract = new JButton("-");
		JButton clean = new JButton("Clean Trunk");
		duelPointDisplay = new JLabel("DP: " + duelPoints);

		open.addActionListener(new OpenListener());
		numberOfPacks.addActionListener(new OpenListener());
		add.addActionListener(new UnlockListener(add));
		subtract.addActionListener(new UnlockListener(subtract));
		clean.addActionListener(new CleanListener());

		open.setAlignmentX(JButton.CENTER_ALIGNMENT);
		numberOfPacks.setAlignmentX(JButton.CENTER_ALIGNMENT);
		add.setAlignmentX(JButton.CENTER_ALIGNMENT);
		subtract.setAlignmentX(JButton.CENTER_ALIGNMENT);
		clean.setAlignmentX(JButton.CENTER_ALIGNMENT);
		duelPointDisplay.setAlignmentX(JLabel.CENTER_ALIGNMENT);

		if (statistics.get(5) == 1) {
			add.setEnabled(packsUnlocked != fullPackList.size());
			subtract.setEnabled(packsUnlocked > 0);
		} else {
			add.setEnabled(false);
			subtract.setEnabled(false);
		}
		numberOfPacks.setPreferredSize(new Dimension(25, 25));

		JPanel buttonPaneOne = new JPanel();

		buttonPaneOne.add(open);
		buttonPaneOne.add(Box.createHorizontalStrut(5));
		buttonPaneOne.add(numberOfPacks);
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
			int packsToOpen = 1;
			if (list.getSelectedIndex() == -1) {
				JOptionPane.showMessageDialog(null, "Please Select a Pack", "ERROR", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (!numberOfPacks.getText().equals("")) {
				try {
					packsToOpen = Integer.parseInt(numberOfPacks.getText());
					if (packsToOpen > 10) {
						JOptionPane.showMessageDialog(null, "Please Enter a Smaller Number", "ERROR",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (packsToOpen < 1) {
						JOptionPane.showMessageDialog(null, "Please Enter a Valid Number", "ERROR",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
				} catch (NumberFormatException n) {
					JOptionPane.showMessageDialog(null, "Please Enter a Valid Number", "ERROR",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
			}
			if (packsToOpen * 150 > duelPoints) {
				JOptionPane.showMessageDialog(null, "Not Enough DP", "ERROR",
						JOptionPane.WARNING_MESSAGE);
			} else {
				duelPoints -= packsToOpen * 150;
				statistics.set(2, duelPoints);
				YGOWriter.writeStats(statistics);
				duelPointDisplay.setText("DP: " + duelPoints);

				ArrayList<String> commonCards = new ArrayList<String>();
				ArrayList<String> rareCards = new ArrayList<String>();

				for (String s : YGOReader.readPack(fullPackList.get(list.getSelectedIndex()))) {
					if (s.substring(0, 1).equals("R"))
						rareCards.add(s.substring(3));
					else if (s.substring(0, 1).equals("C"))
						commonCards.add(s.substring(3));
				}

				list.clearSelection();

				if (rareCards.size() != 0 && commonCards.size() != 0) {
					for (int i = 0; i < packsToOpen; i++) {
						String rare = rareCards.get((int) (Math.random() * rareCards.size()));
						String[] common = new String[]{commonCards.get((int) (Math.random() * commonCards.size())),
								commonCards.get((int) (Math.random() * commonCards.size())),
								commonCards.get((int) (Math.random() * commonCards.size())),
								commonCards.get((int) (Math.random() * commonCards.size()))};
						cardsInTrunk.add(rare);
						cardsInTrunk.add(common[0]);
						cardsInTrunk.add(common[1]);
						cardsInTrunk.add(common[2]);
						cardsInTrunk.add(common[3]);
						JOptionPane.showMessageDialog(null,
								"You got: \n" + rare + "\n" + common[0] + "\n" + common[1] + "\n" + common[2] + "\n" +
										common[3], "Pack " + (i + 1), JOptionPane.INFORMATION_MESSAGE
						);
					}
				}
			}

			YGOWriter.writeTrunk(cardsInTrunk);
		}
	}

	class UnlockListener implements ActionListener {
		private String type;

		public UnlockListener(JButton b) {
			type = b.getText();
		}

		public void actionPerformed(ActionEvent e) {
			if (type.equals("+") && packsUnlocked < fullPackList.size()) {
				packsUnlocked++;
				statistics.set(0, packsUnlocked);
				YGOWriter.writeStats(statistics);
				listModel.insertElementAt(fullPackList.get(packsUnlocked - 1), packsUnlocked - 1);
				list.setSelectedIndex(packsUnlocked - 1);
				list.ensureIndexIsVisible(packsUnlocked - 1);
			}

			if (type.equals("-") && packsUnlocked > 0) {
				packsUnlocked--;
				statistics.remove(0);
				statistics.add(0, packsUnlocked);
				YGOWriter.writeStats(statistics);
				listModel.remove(packsUnlocked);
				list.setSelectedIndex(packsUnlocked - 1);
				list.ensureIndexIsVisible(packsUnlocked - 1);
			}

			add.setEnabled(packsUnlocked != fullPackList.size());
			subtract.setEnabled(packsUnlocked > 0);
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
}
