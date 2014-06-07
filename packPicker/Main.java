package packPicker;

import javax.swing.*;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		ArrayList<String> cardsInTrunk = Reader.readTrunk();
		ArrayList<Integer> stats = Reader.readStats();//0 is unlocked packs, 1 is unlocked decks, 2 is total dp,
		ArrayList<String> fullPackList = Reader.readPackList();

		ListBox frame = new ListBox(fullPackList, stats, cardsInTrunk);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}
}
