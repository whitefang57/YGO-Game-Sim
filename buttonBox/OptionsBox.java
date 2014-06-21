package buttonBox;

import mainMenu.MenuBox;
import ygoUtil.YGOReader;
import ygoUtil.YGOWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class OptionsBox extends JFrame {
	private Checkbox debugCheck;
	private ArrayList<Integer> statistics;

	public OptionsBox() {
		statistics = YGOReader.readStats();
		// 0 is unlocked packs, 1 is unlocked decks, 2 is total dp, 3 is wins, 4 is losses,
		// 5 is debugMode on or off (1 is on, 0 is off)

		setTitle("Options");
		setSize(240, 225);
		setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		getContentPane().add(panel);

		JPanel checkboxes = new JPanel();
		debugCheck = new Checkbox("Debug Mode", statistics.get(5) == 1);
		debugCheck.addItemListener(new CheckBoxListener());
		checkboxes.add(debugCheck);

		panel.add(checkboxes, BorderLayout.CENTER);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				MenuBox frame = new MenuBox();
				frame.setVisible(true);
				frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			}
		});
	}

	class CheckBoxListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			Object source = e.getSource();
			int i = 0;
			if (e.getStateChange() == ItemEvent.SELECTED) {
				i = 1;
			}
			if (source == debugCheck) {
				statistics.set(5, i);
			}
			YGOWriter.writeStats(statistics);
		}
	}
}
