package packPicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ListBox extends JFrame {
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

	public ListBox(ArrayList<String> fullPackList, ArrayList<Integer> stats, ArrayList<String> cardsInTrunk) {
		this.fullPackList = fullPackList;
		this.cardsInTrunk = cardsInTrunk;
		statistics = stats;
		packsUnlocked = stats.get(0);

		setTitle("Pick a Pack");
		setSize(300, 400);
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

		open.addActionListener(new OpenListener());
		numberOfPacks.addActionListener(new OpenListener());
		add.addActionListener(new UnlockListener(add));
		subtract.addActionListener(new UnlockListener(subtract));
		clean.addActionListener(new CleanListener());

		add.setEnabled(packsUnlocked != fullPackList.size());
		subtract.setEnabled(packsUnlocked > 0);

		JPanel buttonPaneOne = new JPanel();
		buttonPaneOne.setLayout(new BoxLayout(buttonPaneOne, BoxLayout.LINE_AXIS));

		buttonPaneOne.add(open);
		Spacer.addSpace(buttonPaneOne);
		buttonPaneOne.add(numberOfPacks);
		Spacer.addSpace(buttonPaneOne);
		buttonPaneOne.add(add);
		Spacer.addSpace(buttonPaneOne);
		buttonPaneOne.add(subtract);

		panel.add(buttonPaneOne, BorderLayout.PAGE_END);
		buttonPaneOne.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		JPanel buttonPaneTwo = new JPanel();
		buttonPaneOne.setLayout(new BoxLayout(buttonPaneOne, BoxLayout.LINE_AXIS));
		buttonPaneTwo.add(clean);

		panel.add(buttonPaneTwo, BorderLayout.PAGE_START);
		buttonPaneOne.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
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
					JOptionPane
							.showMessageDialog(null, "Please Enter a Valid Number", "ERROR",
									JOptionPane.WARNING_MESSAGE);
					return;
				}
			}
			ArrayList<String> commonCards = new ArrayList<String>();
			ArrayList<String> rareCards = new ArrayList<String>();

			for (String s : Reader.readPack(fullPackList.get(list.getSelectedIndex()))) {
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

			Writer.writeTrunk(cardsInTrunk);
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
				statistics.remove(0);
				statistics.add(0, packsUnlocked);
				Writer.writeStats(statistics);
				listModel.insertElementAt(fullPackList.get(packsUnlocked - 1), packsUnlocked - 1);
				list.setSelectedIndex(packsUnlocked - 1);
				list.ensureIndexIsVisible(packsUnlocked - 1);
			}

			if (type.equals("-") && packsUnlocked > 0) {
				packsUnlocked--;
				statistics.remove(0);
				statistics.add(0, packsUnlocked);
				Writer.writeStats(statistics);
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
			Writer.writeTrunk(cardsInTrunk);
			JOptionPane.showMessageDialog(null, "You gained " + cardsRemoved * 15 + " DP", "DP Earned",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	static class Spacer {
		public static void addSpace(JPanel buttonPane) {
			buttonPane.add(Box.createHorizontalStrut(5));
			buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
			buttonPane.add(Box.createHorizontalStrut(5));
		}
	}
}
