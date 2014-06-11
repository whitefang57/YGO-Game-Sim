package ygoUtil;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class YGOWriter {
	public static void writeTrunk(ArrayList<String> cardsInTrunk) {
		Collections.sort(cardsInTrunk);
		try {
			PrintWriter trunk = new PrintWriter("trunk.txt");
			for (String s : cardsInTrunk)
				trunk.println(s);
			trunk.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Trunk Not Found, Writing", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void writeStats(ArrayList<Integer> stats) {
		try {
			PrintWriter unlocked = new PrintWriter("stats.txt");
			for (int i = 0; i < stats.size(); i++) {
				if (i != stats.size() - 1)
					unlocked.println(stats.get(i));
				else
					unlocked.print(stats.get(i));
			}
			unlocked.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Stats File Not Found, Writing", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void writePackList() {
		try {
			PrintWriter packList = new PrintWriter("stats.txt");
			packList.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Pack List Not Found, Writing", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void writeDeckList() {
		try {
			PrintWriter deckList = new PrintWriter("deckList.txt");
			deckList.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Deck List Not Found, Writing", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
}
