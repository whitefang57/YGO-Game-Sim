package deckPicker;

import packPicker.Reader;
import packPicker.Writer;

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
	private ArrayList<String> deckList;
	private ArrayList<String> cardsInTrunk;
	private ArrayList<Integer> statistics;
	private int decksUnlocked;
	private JButton add;
	private JButton subtract;
	private JButton open;
	private int duelPoints;

	public ListBox(ArrayList<String> fullDeckList, ArrayList<Integer> stats, ArrayList<String> cardsInTrunk) {
		this.deckList = fullDeckList;
		this.cardsInTrunk = cardsInTrunk;
		statistics = stats;
		decksUnlocked = stats.get(1);
		duelPoints = stats.get(2);

		setTitle("Pick a Pack");
		setSize(300, 400);
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

		open.addActionListener(new OpenListener());
		add.addActionListener(new UnlockListener(add));
		subtract.addActionListener(new UnlockListener(subtract));
		clean.addActionListener(new CleanListener());

		add.setEnabled(decksUnlocked != deckList.size());
		subtract.setEnabled(decksUnlocked > 0);

		JPanel buttonPaneOne = new JPanel();
		buttonPaneOne.setLayout(new BoxLayout(buttonPaneOne, BoxLayout.LINE_AXIS));

		buttonPaneOne.add(open);
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
			if (list.getSelectedIndex() == -1) {
				JOptionPane.showMessageDialog(null, "Please Select a Deck", "ERROR", JOptionPane.WARNING_MESSAGE);
				return;
			}

			for (String s : Reader.readPack(deckList.get(list.getSelectedIndex()))) {
				cardsInTrunk.add(s);
			}

			list.clearSelection();
			JOptionPane.showMessageDialog(null, "Cards added to trunk", "", JOptionPane.INFORMATION_MESSAGE);
			Writer.writeTrunk(cardsInTrunk);
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
				statistics.remove(1);
				statistics.add(1, decksUnlocked);
				Writer.writeStats(statistics);
				listModel.insertElementAt(deckList.get(decksUnlocked - 1), decksUnlocked - 1);
				list.setSelectedIndex(decksUnlocked - 1);
				list.ensureIndexIsVisible(decksUnlocked - 1);
			}

			if (type.equals("-") && decksUnlocked > 0) {
				decksUnlocked--;
				statistics.remove(0);
				statistics.add(0, decksUnlocked);
				Writer.writeStats(statistics);
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
