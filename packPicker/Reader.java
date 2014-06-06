package packPicker;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Whitefang57 on 6/2/2014.
 */
public class Reader {
	public static ArrayList<String> readTrunk() {
		ArrayList<String> cardsInTrunk = new ArrayList<String>();
		try {
			FileReader reader = new FileReader("trunk.txt");
			Scanner in = new Scanner(reader);
			while (in.hasNextLine()) {
				cardsInTrunk.add(in.nextLine());
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Trunk Not Found, Writing", "ERROR", JOptionPane.ERROR_MESSAGE);
			Writer.writeTrunk(cardsInTrunk);
		}
		return cardsInTrunk;
	}

	public static int readUnlocked() {
		int unlocked = 0;
		try {
			FileReader reader = new FileReader("unlocked.txt");
			Scanner in = new Scanner(reader);
			unlocked = in.nextInt();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Unlocked Not Found, Writing", "ERROR", JOptionPane.ERROR_MESSAGE);
			Writer.writeUnlocked(0);
		}
		return unlocked;
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
			JOptionPane.showMessageDialog(null, "Pack List Not Found, Writing", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		return packList;
	}

	public static ArrayList<String> readPack(String packName) {
		ArrayList<String> cardsInPack = new ArrayList<String>();
		try {
			FileReader reader = new FileReader(packName + ".txt");
			Scanner in = new Scanner(reader);
			while (in.hasNextLine()) {
				cardsInPack.add(in.nextLine());
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Card List Not Found", "ERROR", JOptionPane.ERROR_MESSAGE);
			Writer.writePackList();
		}
		return cardsInPack;
	}
}
