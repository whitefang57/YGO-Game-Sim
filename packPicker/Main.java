package packPicker;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Whitefang57 on 6/2/2014.
 */
public class Main {
	public static void main(String[] args) {
		//JOptionPane.showMessageDialog(null, "I care, but I'm going to sleep");
		//return;

		ArrayList<String> cardsInTrunk = Reader.readTrunk();
		int packsUnlocked = Reader.readUnlocked();
		ArrayList<String> fullPackList = Reader.readPackList();

		ListBox frame = new ListBox(fullPackList, packsUnlocked, cardsInTrunk);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}
}
