package buttonBox;

import mainMenu.MenuBox;
import ygoUtil.XMLHandler;
import ygoUtil.YGOResource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OptionsBox extends JFrame {
	private Checkbox debugCheck;
	private YGOResource resources;

	public OptionsBox() {
		resources = XMLHandler.readResources();

		setTitle("Options");
		setSize(240, 225);
		setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		getContentPane().add(panel);

		JPanel checkboxes = new JPanel();
		debugCheck = new Checkbox("Debug Mode", resources.getDebugMode() == 1);
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
				resources.setDebugMode(i);
			}
			XMLHandler.writeResources(resources);
		}
	}
}
