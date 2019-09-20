package sushigame.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import sushigame.model.Belt;
import sushigame.model.BeltEvent;
import sushigame.model.BeltObserver;
import sushigame.model.Chef;
import sushigame.model.SushiGameModel;

public class ScoreboardWidget extends JPanel implements BeltObserver, ActionListener {

	private SushiGameModel game_model;
	private JLabel display;
	private String user_command;
	
	public ScoreboardWidget(SushiGameModel gm) {
		game_model = gm;
		game_model.getBelt().registerBeltObserver(this);
		
		user_command = "by_balance";
		
		display = new JLabel();
		display.setVerticalAlignment(SwingConstants.TOP);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(display, BorderLayout.CENTER);
		display.setText(makeScoreboardHTML());
		
		JButton by_balance = new JButton("Organize by Balance");
		by_balance.addActionListener(this);
		by_balance.setActionCommand("by_balance");

		JButton by_spoiled_plates = new JButton("Organize by Spoiled Plates");
		by_spoiled_plates.addActionListener(this);
		by_spoiled_plates.setActionCommand("by_spoiled_plates");
		
		JButton by_plates_sold = new JButton("Organize by Plates Sold");
		by_plates_sold.addActionListener(this);
		by_plates_sold.setActionCommand("by_plates_sold");
		
		add(by_balance);
		add(by_spoiled_plates);
		add(by_plates_sold);
	}

	private String makeScoreboardHTML() {
		String sb_html = "<html>";
		sb_html += "<h1>Scoreboard</h1>";

		// Create an array of all chefs and sort by balance.
		Chef[] opponent_chefs= game_model.getOpponentChefs();
		Chef[] chefs = new Chef[opponent_chefs.length+1];
		chefs[0] = game_model.getPlayerChef();
		for (int i=1; i<chefs.length; i++) {
			chefs[i] = opponent_chefs[i-1];
		}
		Arrays.sort(chefs, getComparator());
		
		if (user_command.equals("by_balance"))
			for (Chef c : chefs) {
				sb_html += c.getName() + " ($" + Math.round(c.getBalance()*100.0)/100.0 + ") <br>";
			}
		else if (user_command.equals("by_plates_sold"))
			for (Chef c : chefs) {
				sb_html += c.getName() + " (" + Math.round(c.getWeightSold()*100.0)/100.0 + " Oz) <br>";
			}
		else 
			for (Chef c : chefs) {
				sb_html += c.getName() + " (" + Math.round(c.getWeightSpoiled()*100.0)/100.0 + " Oz) <br>";
			}
		return sb_html;
	}

	public void refresh() {
		display.setText(makeScoreboardHTML());		
	}
	
	@Override
	public void handleBeltEvent(BeltEvent e) {
		if (e.getType() == BeltEvent.EventType.ROTATE) {
			refresh();
		}		
	}
	
	public Comparator<Chef> getComparator() {
		if(user_command.equals("by_balance")) {
			return new HighToLowBalanceComparator();
		} else if (user_command.equals("by_plates_sold")) {
			return new PlatesSoldComparator();
		} else {
			return new PlatesSpoiledComparator();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton pressed = (JButton)e.getSource();
		String desire = pressed.getActionCommand();
		switch(desire) {
		case "by_balance":
			user_command = "by_balance";
			refresh();
			break;
		case "by_plates_sold":
			user_command = "by_plates_sold";
			refresh();
			break;
		case "by_spoiled_plates":
			user_command = "by_spoiled_plates";
			refresh();
			break;
		}
	}

}
