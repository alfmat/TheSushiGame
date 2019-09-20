package sushigame.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import comp401sushi.AvocadoPortion;
import comp401sushi.CrabPortion;
import comp401sushi.EelPortion;
import comp401sushi.IngredientPortion;
import comp401sushi.Nigiri;
import comp401sushi.Plate;
import comp401sushi.RedPlate;
import comp401sushi.RicePortion;
import comp401sushi.Roll;
import comp401sushi.Sashimi;
import comp401sushi.SeaweedPortion;
import comp401sushi.ShrimpPortion;
import comp401sushi.Sushi;
import comp401sushi.TunaPortion;
import comp401sushi.YellowtailPortion;

public class PlayerChefView extends JPanel implements ActionListener, ChangeListener {

	private List<ChefViewListener> listeners;
//	private Sushi kmp_roll;
//	private Sushi crab_sashimi;
//	private Sushi eel_nigiri;
	private int belt_size;
	private String sushi_choice = "Nigiri";
	private String plate_color = "Red";
	private int position = 1;
	private double price = 0;
	private double[] quantity = new double[8];
	
	public PlayerChefView(int belt_size) {
		this.belt_size = belt_size;
		listeners = new ArrayList<ChefViewListener>();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// just to create some space from the next button
		add(new JLabel(" "));
		
		// text that talks about the type of sushi
		add(new JLabel("What type of sushi?"));
		
		// this is the choice of sushi
		JComboBox<String> sushi_options = new JComboBox<String>(new String[] {"Nigiri", "Sashimi", "Roll"});
		sushi_options.addActionListener(this);
		sushi_options.setActionCommand("sushi_options");
		add(sushi_options);
		
		// just to create some space from the next button
		add(new JLabel(" "));
		
		//text that asks about the plate color
		add(new JLabel("What color?"));
		
		// choice of plate color
		JComboBox<String> color_options = new JComboBox<String>(new String[] {"Red", "Green", "Blue", "Gold"});
		color_options.addActionListener(this);
		color_options.setActionCommand("color_change");
		add(color_options);
		
//		// just to create some space from the next button
//		add(new JLabel(" "));
//		
//		// another question
//		add(new JLabel("What Price?"));
//		
//		// this is the JSlider
//		JSlider price_selector = new JSlider(5, 10);
//		price_selector.addChangeListener(this);
//		price_selector.setMajorTickSpacing(1);
//		price_selector.setPaintTicks(true);
//		price_selector.setPaintLabels(true);
//		add(price_selector);
		
		// just to create some space from the next button
		add(new JLabel(" "));
		
		// position question
		add(new JLabel("What position?"));
		
		// position selector
		JComboBox<Integer> position_options = new JComboBox<Integer>(new Integer[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20});
		position_options.addActionListener(this);
		position_options.setActionCommand("position_change");
		add(position_options);
		
		// just to create some space from the next button
		add(new JLabel(" "));
		
		// this is where we can place the price slider
		JLabel price_description = new JLabel("Select price (for gold plates only!)");
		JSlider price_slider = new JSlider(500, 1000, 500);
		price_slider.addChangeListener(this);
		price_slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				JSlider source = (JSlider)e.getSource();
				price = source.getValue() / 100.0;
			}
			
		});
		price_slider.setMajorTickSpacing(100);
		price_slider.setMinorTickSpacing(10);
		price_slider.setPaintTicks(true);
		
		{
			Hashtable<Integer, JLabel> labels = new Hashtable<>();
	        labels.put(500, new JLabel("5.00"));
	        labels.put(600, new JLabel("6.00"));
	        labels.put(700, new JLabel("7.00"));
	        labels.put(800, new JLabel("8.00"));
	        labels.put(900, new JLabel("9.00"));
	        labels.put(1000, new JLabel("10.00"));
	        price_slider.setLabelTable(labels);

	        price_slider.setPaintLabels(true);
			
			add(price_description);
			add(price_slider);
		}
		
		// this is the final submit button that the user can use
		JButton submit = new JButton("Submit Plate Request");
		submit.addActionListener(this);
		submit.setOpaque(true);
		submit.setActionCommand("submit");
		add(submit);
		
		setPreferredSize(new Dimension(300, 2000));
		
		// empty panel used to compress the other panel and make the interface
		// look neater
		JPanel option_panel = new JPanel();
		option_panel.add(new JLabel(""));
		option_panel.setPreferredSize(new Dimension(200, 2000));
		add(option_panel);

//		JButton sashimi_button = new JButton("Make red plate of crab sashimi at position 3");
//		sashimi_button.setActionCommand("red_crab_sashimi_at_3");
//		sashimi_button.addActionListener(this);
//		add(sashimi_button);
//
//		JButton nigiri_button = new JButton("Make blue plate of eel nigiri at position 8");
//		nigiri_button.setActionCommand("blue_eel_nigiri_at_8");
//		nigiri_button.addActionListener(this);
//		add(nigiri_button);
//
//		JButton roll_button = new JButton("Make gold plate of KMP roll at position 5");
//		roll_button.setActionCommand("gold_kmp_roll_at_5");
//		roll_button.addActionListener(this);
//		add(roll_button);
//
//		kmp_roll = new Roll("KMP Roll", new IngredientPortion[] {new EelPortion(1.0), new AvocadoPortion(0.5), new SeaweedPortion(0.2)});
//		crab_sashimi = new Sashimi(Sashimi.SashimiType.CRAB);
//		eel_nigiri = new Nigiri(Nigiri.NigiriType.EEL);
	}

	public void registerChefListener(ChefViewListener cl) {
		listeners.add(cl);
	}
	
	public void plateRequest(String desired_sushi, String desired_color, int wanted_position) {
		switch (desired_sushi) {
		case "Roll":
			
			JDialog my_box = new JDialog();
			my_box.setLayout(new GridLayout(19, 1));
			
			// initial information
			my_box.add(new JLabel("Change quantities as you like!"));
			
			// avocado slider
			JLabel avocado_text = new JLabel("Avocado Portion (0 - 1.5)");
			JSlider avocado_quantity = new JSlider(0, 15, 0);
			avocado_quantity.addChangeListener(this);
			avocado_quantity.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					// TODO Auto-generated method stub
					JSlider source = (JSlider)e.getSource();
					quantity[0] = source.getValue() / 10.0;
				}
				
			});
			avocado_quantity.setMajorTickSpacing(5);
			avocado_quantity.setMinorTickSpacing(1);
			avocado_quantity.setPaintTicks(true);
			{
				// custom labels
				Hashtable<Integer, JLabel> labels = new Hashtable<>();
		        labels.put(0, new JLabel("0"));
		        labels.put(5, new JLabel("0.5"));
		        labels.put(10, new JLabel("1"));
		        labels.put(15, new JLabel("1.5"));
		        avocado_quantity.setLabelTable(labels);
	
		        avocado_quantity.setPaintLabels(true);
				
				my_box.add(avocado_text);
				my_box.add(avocado_quantity);
			}
			
			
			// crab slider
			JLabel crab_text = new JLabel("Crab Portion (0 - 1.5)");
			JSlider crab_quantity = new JSlider(0, 15, 0);
			crab_quantity.addChangeListener(this);
			crab_quantity.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					// TODO Auto-generated method stub
					JSlider source = (JSlider)e.getSource();
					quantity[1] = source.getValue() / 10.0;
				}
				
			});
			crab_quantity.setMajorTickSpacing(5);
			crab_quantity.setMinorTickSpacing(1);
			crab_quantity.setPaintTicks(true);
			{
				// custom labels
				Hashtable<Integer, JLabel> labels = new Hashtable<>();
		        labels.put(0, new JLabel("0"));
		        labels.put(5, new JLabel("0.5"));
		        labels.put(10, new JLabel("1"));
		        labels.put(15, new JLabel("1.5"));
		        crab_quantity.setLabelTable(labels);
		        crab_quantity.setPaintLabels(true);
				
				my_box.add(crab_text);
				my_box.add(crab_quantity);
			}
			
			// eel slider
			JLabel eel_text = new JLabel("Eel Portion (0 - 1.5)");
			JSlider eel_quantity = new JSlider(0, 15, 0);
			eel_quantity.addChangeListener(this);
			eel_quantity.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					// TODO Auto-generated method stub
					JSlider source = (JSlider)e.getSource();
					quantity[2] = source.getValue() / 10.0;
				}
				
			});
			eel_quantity.setMajorTickSpacing(5);
			eel_quantity.setMinorTickSpacing(1);
			eel_quantity.setPaintTicks(true);
			{
				// custom labels
				Hashtable<Integer, JLabel> labels = new Hashtable<>();
		        labels.put(0, new JLabel("0"));
		        labels.put(5, new JLabel("0.5"));
		        labels.put(10, new JLabel("1"));
		        labels.put(15, new JLabel("1.5"));
		        eel_quantity.setLabelTable(labels);
		        eel_quantity.setPaintLabels(true);
				
				my_box.add(eel_text);
				my_box.add(eel_quantity);
			}
			
			// yellowtail slider
			JLabel yellowtail_text = new JLabel("Yellowtail Portion (0 - 1.5)");
			JSlider yellowtail_quantity = new JSlider(0, 15, 0);
			yellowtail_quantity.addChangeListener(this);
			yellowtail_quantity.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					// TODO Auto-generated method stub
					JSlider source = (JSlider)e.getSource();
					quantity[3] = source.getValue() / 10.0;
				}
				
			});
			yellowtail_quantity.setMajorTickSpacing(5);
			yellowtail_quantity.setMinorTickSpacing(1);
			yellowtail_quantity.setPaintTicks(true);
			{
				// custom labels
				Hashtable<Integer, JLabel> labels = new Hashtable<>();
		        labels.put(0, new JLabel("0"));
		        labels.put(5, new JLabel("0.5"));
		        labels.put(10, new JLabel("1"));
		        labels.put(15, new JLabel("1.5"));
		        yellowtail_quantity.setLabelTable(labels);
		        yellowtail_quantity.setPaintLabels(true);
		        
				my_box.add(yellowtail_text);
				my_box.add(yellowtail_quantity);
			}
			
			// seaweed slider
			JLabel seaweed_text = new JLabel("Seaweed Portion (0 - 1.5)");
			JSlider seaweed_quantity = new JSlider(0, 15, 0);
			seaweed_quantity.addChangeListener(this);
			seaweed_quantity.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					// TODO Auto-generated method stub
					JSlider source = (JSlider)e.getSource();
					quantity[4] = source.getValue() / 10.0;
				}
				
			});
			seaweed_quantity.setMajorTickSpacing(5);
			seaweed_quantity.setMinorTickSpacing(1);
			seaweed_quantity.setPaintTicks(true);
			{
				// custom labels
				Hashtable<Integer, JLabel> labels = new Hashtable<>();
		        labels.put(0, new JLabel("0"));
		        labels.put(5, new JLabel("0.5"));
		        labels.put(10, new JLabel("1"));
		        labels.put(15, new JLabel("1.5"));
		        seaweed_quantity.setLabelTable(labels);
		        seaweed_quantity.setPaintLabels(true);
				
				my_box.add(seaweed_text);
				my_box.add(seaweed_quantity);
			}
			
			JLabel shrimp_text = new JLabel("Shrimp Portion (0 - 1.5)");
			JSlider shrimp_quantity = new JSlider(0, 15, 0);
			shrimp_quantity.addChangeListener(this);
			shrimp_quantity.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					// TODO Auto-generated method stub
					JSlider source = (JSlider)e.getSource();
					quantity[5] = source.getValue() / 10.0;
				}
				
			});
			shrimp_quantity.setMajorTickSpacing(5);
			shrimp_quantity.setMinorTickSpacing(1);
			shrimp_quantity.setPaintTicks(true);
			{
				// custom labels
				Hashtable<Integer, JLabel> labels = new Hashtable<>();
		        labels.put(0, new JLabel("0"));
		        labels.put(5, new JLabel("0.5"));
		        labels.put(10, new JLabel("1"));
		        labels.put(15, new JLabel("1.5"));
		        shrimp_quantity.setLabelTable(labels);
		        shrimp_quantity.setPaintLabels(true);
				
				my_box.add(shrimp_text);
				my_box.add(shrimp_quantity);
			}
			
			JLabel tuna_text = new JLabel("Tuna Portion (0 - 1.5)");
			JSlider tuna_quantity = new JSlider(0, 15, 0);
			tuna_quantity.addChangeListener(this);
			tuna_quantity.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					// TODO Auto-generated method stub
					JSlider source = (JSlider)e.getSource();
					quantity[6] = source.getValue() / 10.0;
				}
				
			});
			tuna_quantity.setMajorTickSpacing(5);
			tuna_quantity.setMinorTickSpacing(1);
			tuna_quantity.setPaintTicks(true);
			{
				// custom labels
				Hashtable<Integer, JLabel> labels = new Hashtable<>();
		        labels.put(0, new JLabel("0"));
		        labels.put(5, new JLabel("0.5"));
		        labels.put(10, new JLabel("1"));
		        labels.put(15, new JLabel("1.5"));
		        tuna_quantity.setLabelTable(labels);
		        tuna_quantity.setPaintLabels(true);
				
				my_box.add(tuna_text);
				my_box.add(tuna_quantity);
			}
			
			JLabel rice_text = new JLabel("Rice Portion (0 - 1.5)");
			JSlider rice_quantity = new JSlider(0, 15, 0);
			rice_quantity.addChangeListener(this);
			rice_quantity.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					// TODO Auto-generated method stub
					JSlider source = (JSlider)e.getSource();
					quantity[7] = source.getValue() / 10.0;
				}
				
			});
			rice_quantity.setMajorTickSpacing(5);
			rice_quantity.setMinorTickSpacing(1);
			rice_quantity.setPaintTicks(true);
			{
				// custom labels
				Hashtable<Integer, JLabel> labels = new Hashtable<>();
		        labels.put(0, new JLabel("0"));
		        labels.put(5, new JLabel("0.5"));
		        labels.put(10, new JLabel("1"));
		        labels.put(15, new JLabel("1.5"));
		        rice_quantity.setLabelTable(labels);
		        rice_quantity.setPaintLabels(true);
				
				my_box.add(rice_text);
				my_box.add(rice_quantity);
			}
			
			JButton close_button = new JButton("I'm done!");
			close_button.addActionListener(this);
			close_button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					fulfillPlateRequest(new Roll("KMP Roll", createIngredientArray(quantity)), plate_color, position);
					my_box.dispose();
					
				}
				
			});
			
			my_box.add(close_button);
			
			my_box.setVisible(true);
			my_box.setMinimumSize(new Dimension(750, 750));
			
//			double quantity = Double.parseDouble(((String) JOptionPane.showInputDialog(
//                    this,
//                    "Enter the amount of the ingredient",
//                    "Enter Quantity",
//                    JOptionPane.PLAIN_MESSAGE,
//                    null,
//                    null,
//                    "ham")).trim());
//			if (quantity < 5 || quantity > 10) 
//				JOptionPane.showMessageDialog(this, "Make sure the value is between 5 and 10");
			break;
		case "Nigiri":
		case "Sashimi":
			
			String[] possibilities = {"CRAB","EEL","YELLOWTAIL","TUNA","SHRIMP"};
			String s = (String)JOptionPane.showInputDialog(
			                    this,
			                    "Enter your choice of Sushi Type\npress ok when done",
			                    "Select Type",
			                    JOptionPane.PLAIN_MESSAGE,
			                    null,
			                    possibilities,
			                    null);
			if(s == null)
				break;
			if (desired_sushi.equals("Nigiri")) {
				Sushi my_sushi = new Nigiri(nType(s));
				fulfillPlateRequest(my_sushi, this.plate_color, this.position);
			}
				
			if (desired_sushi.equals("Sashimi")) {
				Sushi my_sushi = new Sashimi(sType(s));
				fulfillPlateRequest(my_sushi, this.plate_color, this.position);
			}
				
			break;
		}
	}
	
	public Nigiri.NigiriType nType(String type) {
		if (type.equals("CRAB"))
			return Nigiri.NigiriType.CRAB;
		else if (type.equals("EEL"))
			return Nigiri.NigiriType.EEL;
		else if (type.equals("YELLOWTAIL"))
			return Nigiri.NigiriType.YELLOWTAIL;
		else if (type.equals("TUNA"))
			return Nigiri.NigiriType.TUNA;
		else 
			return Nigiri.NigiriType.SHRIMP;
	}
	
	public Sashimi.SashimiType sType(String type) {
		if (type.equals("CRAB"))
			return Sashimi.SashimiType.CRAB;
		else if (type.equals("EEL"))
			return Sashimi.SashimiType.EEL;
		else if (type.equals("YELLOWTAIL"))
			return Sashimi.SashimiType.YELLOWTAIL;
		else if (type.equals("TUNA"))
			return Sashimi.SashimiType.TUNA;
		else 
			return Sashimi.SashimiType.SHRIMP;
	}
	
	public IngredientPortion createIngredient(int index, double quantity) {
		IngredientPortion to_be_returned = null;
		switch(index) {
		case 0:
			to_be_returned = new AvocadoPortion(quantity);
			break;
		case 1:
			to_be_returned = new CrabPortion(quantity);
			break;
		case 2:
			to_be_returned = new EelPortion(quantity);
			break;
		case 3:
			to_be_returned = new YellowtailPortion(quantity);
			break;
		case 4:
			to_be_returned = new SeaweedPortion(quantity);
			break;
		case 5:
			to_be_returned = new ShrimpPortion(quantity);
			break;
		case 6:
			to_be_returned = new TunaPortion(quantity);
			break;
		case 7:
			to_be_returned = new RicePortion(quantity);
			break;
		}
		return to_be_returned;
	}
	
	public IngredientPortion[] createIngredientArray(double q[]) {
		List<IngredientPortion> transition = new ArrayList<IngredientPortion>();
		for (int i = 0; i < q.length; i++) {
			if(q[i] > 0)
				transition.add(createIngredient(i, q[i]));
		}
		IngredientPortion[] final_output = new IngredientPortion[transition.size()];
		for (int i = 0; i < final_output.length; i++) {
			final_output[i] = transition.get(i);
		}
		return final_output;
	}
	
	public void fulfillPlateRequest(Sushi plate_sushi, String color, int plate_position) {
		switch (color) {
		case "Red":
			makeRedPlateRequest(plate_sushi, plate_position);
			break;
		case "Gold":
			makeGoldPlateRequest(plate_sushi, plate_position, this.price);
			break;
		case "Blue":
			makeBluePlateRequest(plate_sushi, plate_position);
			break;
		case "Green":
			makeGreenPlateRequest(plate_sushi, plate_position);
			break;
		}
	}

	private void makeRedPlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleRedPlateRequest(plate_sushi, plate_position - 1);
		}
	}

	private void makeGreenPlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleGreenPlateRequest(plate_sushi, plate_position - 1);
		}
	}

	private void makeBluePlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleBluePlateRequest(plate_sushi, plate_position - 1);
		}
	}
	
	private void makeGoldPlateRequest(Sushi plate_sushi, int plate_position, double price) {
		for (ChefViewListener l : listeners) {
			l.handleGoldPlateRequest(plate_sushi, plate_position - 1, price);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "sushi_options":
				JComboBox<String> box1 = (JComboBox<String>)e.getSource();
				this.sushi_choice = (String)box1.getSelectedItem();
				break;
			case "color_change":
				JComboBox<String> box2 = (JComboBox<String>)e.getSource();
				this.plate_color = (String)box2.getSelectedItem();
				break;
			case "position_change":
				JComboBox<Integer> box3 = (JComboBox<Integer>)e.getSource();
				this.position = (Integer)box3.getSelectedItem();
				break;
			case "submit":
				plateRequest(sushi_choice, plate_color, position);
				break;
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		
	}
}