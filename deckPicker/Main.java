package deckPicker;

import packPicker.Reader;
import javax.swing.*;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		//JOptionPane.showMessageDialog(null, "I care, but I'm going to sleep");
		//return;

		ArrayList<String> cardsInTrunk = Reader.readTrunk();
		ArrayList<Integer> stats = Reader.readStats();//0 is unlocked packs,
		ArrayList<String> deckList = Reader.readDeckList();

		ListBox frame = new ListBox(deckList, stats, cardsInTrunk);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}
}
