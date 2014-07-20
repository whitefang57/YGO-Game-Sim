package ygoUtil;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class YGOReader {
	public static ArrayList<String> readTrunk() {
		ArrayList<String> cardsInTrunk = new ArrayList<String>();
		try {
			FileReader reader = new FileReader("trunk.txt");
			Scanner in = new Scanner(reader);
			while (in.hasNextLine()) {
				cardsInTrunk.add(in.nextLine());
			}
		} catch (FileNotFoundException e) {
			YGOWriter.writeTrunk(cardsInTrunk);
		}
		return cardsInTrunk;
	}

	public static ArrayList<String> readMainDeck() {
		ArrayList<String> cardsInDeck = new ArrayList<String>();
		try {
			FileReader reader = new FileReader("mainDeck.txt");
			Scanner in = new Scanner(reader);
			while (in.hasNextLine()) {
				cardsInDeck.add(in.nextLine());
			}
		} catch (FileNotFoundException e) {
			YGOWriter.writeDeck(cardsInDeck);
		}
		return cardsInDeck;
	}

	public static ArrayList<String> readPackList() {
		ArrayList<String> packList = new ArrayList<String>();
		try {
			FileReader reader = new FileReader("packList.txt");
			Scanner in = new Scanner(reader);
			while (in.hasNextLine()) {
				packList.add(in.nextLine());
			}
		} catch (FileNotFoundException e) {
			YGOWriter.writePackList();
		}
		return packList;
	}

	public static ArrayList<String> readDeckList() {
		ArrayList<String> deckList = new ArrayList<String>();
		try {
			FileReader reader = new FileReader("deckList.txt");
			Scanner in = new Scanner(reader);
			while (in.hasNextLine()) {
				deckList.add(in.nextLine());
			}
		} catch (FileNotFoundException e) {
			YGOWriter.writeDeckList();
		}
		return deckList;
	}

	public static ArrayList<String> readPack(String packName) {
		ArrayList<String> cardsInPack = new ArrayList<String>();
		try {
			FileReader reader = new FileReader("cardLists\\" + packName + ".txt");
			Scanner in = new Scanner(reader);
			while (in.hasNextLine()) {
				cardsInPack.add(in.nextLine());
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Card List Not Found", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		return cardsInPack;
	}
}
