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

	public static void writeDeck(ArrayList<String> cardsInDeck) {
		Collections.sort(cardsInDeck);
		try {
			PrintWriter deck = new PrintWriter("mainDeck.txt");
			for (String s : cardsInDeck)
				deck.println(s);
			deck.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Deck Not Found, Writing", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void writePackList() {
		try {
			PrintWriter packList = new PrintWriter("packList.txt");
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
