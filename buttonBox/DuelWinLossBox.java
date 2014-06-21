package buttonBox;

import mainMenu.MenuBox;
import ygoUtil.YGOReader;
import ygoUtil.YGOWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class DuelWinLossBox extends JFrame {
	private ArrayList<Integer> statistics;

	public DuelWinLossBox() {
		statistics = YGOReader.readStats();
		// 0 is unlocked packs, 1 is unlocked decks, 2 is total dp, 3 is wins, 4 is losses,
		// 5 is debugMode on or off (1 is on, 0 is off)

		setTitle("Win or Loss?");
		setSize(250, 110);
		setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		getContentPane().add(panel);

		JLabel promptLabel = new JLabel("Did you win or lose?");
		JButton winButton = new JButton("Win");
		JButton lossButton = new JButton("Loss");

		winButton.addActionListener(new ButtonListener("Win"));
		lossButton.addActionListener(new ButtonListener("Loss"));

		promptLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		winButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
		lossButton.setAlignmentX(JButton.CENTER_ALIGNMENT);

		JPanel topPanel = new JPanel();
		topPanel.add(promptLabel);
		panel.add(topPanel, BorderLayout.PAGE_START);

		JPanel centerPanel = new JPanel();
		centerPanel.add(winButton);
		centerPanel.add(Box.createHorizontalStrut(5));
		centerPanel.add(lossButton);
		panel.add(centerPanel, BorderLayout.CENTER);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				MenuBox frame = new MenuBox();
				frame.setVisible(true);
				frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			}
		});
	}

	class ButtonListener implements ActionListener {
		private String buttonName;

		public ButtonListener(String buttonName) {
			this.buttonName = buttonName;
		}

		public void actionPerformed(ActionEvent e) {
			if (buttonName.equals("Win")) {
				statistics.set(3, statistics.get(3) + 1);
				statistics.set(2, statistics.get(2) + 600);
				JOptionPane.showMessageDialog(null, "You Gained 600 DP", "", JOptionPane.INFORMATION_MESSAGE);
			} else if (buttonName.equals("Loss")) {
				statistics.set(4, statistics.get(4) + 1);
				statistics.set(2, statistics.get(2) + 750);
				JOptionPane.showMessageDialog(null, "You Gained 750 DP", "", JOptionPane.INFORMATION_MESSAGE);
			}
			YGOWriter.writeStats(statistics);

			MenuBox frame = new MenuBox();
			frame.setVisible(true);
			frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dispose();
		}
	}
}
