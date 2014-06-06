package packPicker;

import javax.swing.*;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		//JOptionPane.showMessageDialog(null, "I care, but I'm going to sleep");
		//return;

		ArrayList<String> cardsInTrunk = Reader.readTrunk();
		ArrayList<Integer> stats = Reader.readStats();//0 is unlocked packs,
		ArrayList<String> fullPackList = Reader.readPackList();

		ListBox frame = new ListBox(fullPackList, stats, cardsInTrunk);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}
}
