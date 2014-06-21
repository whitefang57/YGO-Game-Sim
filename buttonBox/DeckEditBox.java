package buttonBox;

import mainMenu.MenuBox;
import ygoUtil.YGOReader;
import ygoUtil.YGOWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DeckEditBox extends JFrame {
	private DefaultListModel<String> listModelTrunk;
	private JList<String> listTrunk;
	private DefaultListModel<String> listModelDeck;
	private JButton moveToDeck;
	private JButton moveToTrunk;
	private JList<String> listDeck;
	private ArrayList<String> cardsInDeck;

	public DeckEditBox() {
		cardsInDeck = YGOReader.readMainDeck();
		ArrayList<String> cardsInTrunk = YGOReader.readTrunk();

		setTitle("Deck Edit");
		setSize(500, 600);
		setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		getContentPane().add(panel);

		listModelTrunk = new DefaultListModel<String>();
		for (String s : cardsInTrunk)
			listModelTrunk.addElement(s);
		listTrunk = new JList<String>(listModelTrunk);
		listTrunk.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		listTrunk.setLayoutOrientation(JList.VERTICAL);
		listTrunk.setVisibleRowCount(1);
		listTrunk.addMouseListener(new RightClickListener("listTrunk"));

		listModelDeck = new DefaultListModel<String>();
		for (String s : cardsInDeck)
			listModelDeck.addElement(s);
		listDeck = new JList<String>(listModelDeck);
		listDeck.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		listDeck.setLayoutOrientation(JList.VERTICAL);
		listDeck.setVisibleRowCount(1);
		listDeck.addMouseListener(new RightClickListener("listDeck"));

		int width = (int) (2 * listTrunk.getPreferredSize().getWidth()) + 40;
		setSize(width, 600);

		panel.add(new JScrollPane(listTrunk), BorderLayout.WEST);
		panel.add(new JScrollPane(listDeck), BorderLayout.EAST);

		moveToDeck = new JButton("Move to Deck");
		moveToDeck.addActionListener(new MoveToDeckListener());
		moveToTrunk = new JButton("Move to Trunk");
		moveToTrunk.addActionListener(new MoveToTrunkListener());

		JPanel buttonPane = new JPanel();
		buttonPane.add(moveToDeck);
		buttonPane.add(Box.createHorizontalStrut(5));
		buttonPane.add(moveToTrunk);
		panel.add(buttonPane, BorderLayout.PAGE_END);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				MenuBox frame = new MenuBox();
				frame.setVisible(true);
				frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				YGOWriter.writeDeck(cardsInDeck);
			}
		});
	}

	class MoveToTrunkListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (listDeck.getSelectedIndex() == -1) {
				JOptionPane.showMessageDialog(null, "Please Select a Card", "ERROR", JOptionPane.WARNING_MESSAGE);
				return;
			}
			int cardsRemoved = 0;
			for (int i : listDeck.getSelectedIndices()) {
				listModelDeck.remove(i - cardsRemoved);
				cardsInDeck.remove(i - cardsRemoved);
				cardsRemoved++;
			}
		}
	}

	class MoveToDeckListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (listTrunk.getSelectedIndex() == -1) {
				JOptionPane.showMessageDialog(null, "Please Select a Card", "ERROR", JOptionPane.WARNING_MESSAGE);
				return;
			}
			for (int i : listTrunk.getSelectedIndices()) {
				cardsInDeck.add(listModelTrunk.get(i));
			}
			listModelDeck.clear();
			YGOWriter.writeDeck(cardsInDeck);
			for (String s : cardsInDeck)
				listModelDeck.addElement(s);

			int width = (int) (2 * listTrunk.getPreferredSize().getWidth()) + 40;
			setSize(width + 1, 600);
			setSize(width, 600);
		}
	}

	class RightClickListener extends MouseAdapter {
		private String listName;

		public RightClickListener(String name) {
			listName = name;
		}

		public void mouseClicked(MouseEvent e) {
			if (SwingUtilities.isRightMouseButton(e)) {
				if (listName.equals("listTrunk")) {
					listTrunk.setSelectedIndex(listTrunk.locationToIndex(e.getPoint()));
					moveToDeck.doClick();
					listTrunk.clearSelection();
				}
				if (listName.equals("listDeck")) {
					listDeck.setSelectedIndex(listDeck.locationToIndex(e.getPoint()));
					moveToTrunk.doClick();
					listDeck.clearSelection();
				}
			}
		}
	}
}
