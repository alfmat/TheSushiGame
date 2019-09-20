package sushigame.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import comp401sushi.Plate;
import sushigame.model.Belt;
import sushigame.model.BeltEvent;
import sushigame.model.BeltObserver;
import sushigame.model.HistoricalPlate;
import sushigame.model.TimedPlate;

public class BeltView extends JPanel implements BeltObserver, ActionListener {

	private Belt belt;
	private JButton[] belt_labels;

	public BeltView(Belt b) {
		this.belt = b;
		belt.registerBeltObserver(this);
		setLayout(new GridLayout((belt.getSize() + 2) / 2, 1));
		setPreferredSize(new Dimension(800, 4000));
		belt_labels = new JButton[belt.getSize()];
		JLabel instructions1 = new JLabel("Click on a plate to see more info!");
		JLabel instructions2 = new JLabel("Developed by Alfred");
		add(instructions1);
		add(instructions2);
		for (int i = 0; i < belt.getSize() ; i++) {
			JButton pbutton = new JButton();
			pbutton.setPreferredSize(new Dimension(100, 10));
			pbutton.setOpaque(true);
			pbutton.setBorderPainted(false);
			pbutton.setBackground(Color.GRAY);
			pbutton.addActionListener(this);
			add(pbutton);
			belt_labels[i] = pbutton;	
		}
		refresh();
	}

	@Override
	public void handleBeltEvent(BeltEvent e) {	
		refresh();
	}

	private void refresh() {
		for (int i=0; i<belt.getSize(); i++) {
			Plate p = belt.getPlateAtPosition(i);
			JButton pbutton = belt_labels[i];

			if (p == null) {
				pbutton.setText("" + (i+1));
				pbutton.setBackground(Color.GRAY);
			} else {
				pbutton.setText(p.toString());
				switch (p.getColor()) {
				case RED:
					pbutton.setBackground(Color.RED); break;
				case GREEN:
					pbutton.setBackground(Color.GREEN); break;
				case BLUE:
					pbutton.setBackground(Color.BLUE); break;
				case GOLD:
					pbutton.setBackground(Color.YELLOW); break;
				}
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton)e.getSource();
		try {
			// some more logic needs to be added here
			DecimalFormat df = new DecimalFormat("0.00");
			Plate my_plate = belt.getPlateAtPosition(findButtonIndex(clicked));
			String color = "";
			color += my_plate.toString();
			color = color.substring(0, color.indexOf(" "));
			String sushi_type = "";
			sushi_type += my_plate.getContents().getName().trim();
			String kind = "";
			kind = sushi_type.substring(0, sushi_type.indexOf(" "));
			String contents = "\n\nContents:\n";
			for (int i = 0; i < my_plate.getContents().getIngredients().length; i++) {
				contents += "\t" + my_plate.getContents().getIngredients()[i].getName() + ": " +  
			df.format(my_plate.getContents().getIngredients()[i].getAmount()) + "\n";
			}
			if (!sushi_type.equals("Roll")) {
				sushi_type = sushi_type.substring(sushi_type.indexOf(" "), sushi_type.length());
				JOptionPane.showMessageDialog(this, 
					"Plate Color: " + color + "\n" +
					"Sushi Type: " + sushi_type + "\n" +
					"Kind: " + kind + "\n" +
					"Age: " + belt.getAgeOfPlateAtPosition(findButtonIndex(clicked)) + "\n" +
					"Chef: " + my_plate.getChef().getName() + contents
					);
			} else {
				String name = "";
				name = sushi_type.substring(0, sushi_type.indexOf(" "));
				JOptionPane.showMessageDialog(this, 
						"Plate Color: " + color + "\n" +
						"Sushi Type: " + sushi_type + "\n" +
						"Name: " + name + "\n" +
						"Age: " + belt.getAgeOfPlateAtPosition(findButtonIndex(clicked)) + "\n" +
						"Chef: " + my_plate.getChef().getName() + contents
						);
			}
		} catch (NullPointerException my_exception) {
			// do nothing if they click on a plate that does not exist
			JOptionPane.showMessageDialog(this, "This plate is empty\ntry creating a new plate");
		}
		
	}
	
	public int findButtonIndex(JButton my_button) {
		for (int i = 0; i < this.belt_labels.length; i++) {
			if (my_button == this.belt_labels[i])
				return i;
		}
		return -1;
	}
}