package packPicker;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Whitefang57 on 6/2/2014.
 */
public class Writer {
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

	public static void writeUnlocked(int i) {
		try {
			PrintWriter unlocked = new PrintWriter("unlocked.txt");
			unlocked.print(i);
			unlocked.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Unlocked File Not Found, Writing", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void writePackList() {
		try {
			PrintWriter packList = new PrintWriter("unlocked.txt");
			packList.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Pack List Not Found, Writing", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
}
