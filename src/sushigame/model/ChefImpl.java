package sushigame.model;

import java.util.ArrayList;
import java.util.List;

import comp401sushi.Plate;
import comp401sushi.RedPlate;
import comp401sushi.Sushi;
import comp401sushi.Nigiri.NigiriType;
import comp401sushi.Plate.Color;
import comp401sushi.Sashimi.SashimiType;

public class ChefImpl implements Chef, BeltObserver {

	private double balance;
	private List<HistoricalPlate> plate_history;
	private List<HistoricalPlate> plate_spoiled;
	private List<HistoricalPlate> plate_consumed;
	private String name;
	private ChefsBelt belt;
	private boolean already_placed_this_rotation;
	
	public ChefImpl(String name, double starting_balance, ChefsBelt belt) {
		this.name = name;
		this.balance = starting_balance;
		this.belt = belt;
		belt.registerBeltObserver(this);
		already_placed_this_rotation = false;
		plate_history = new ArrayList<HistoricalPlate>();
		plate_spoiled = new ArrayList<HistoricalPlate>();
		plate_consumed = new ArrayList<HistoricalPlate>();
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String n) {
		this.name = n;
	}

	@Override
	public HistoricalPlate[] getPlateHistory(int history_length) {
		if (history_length < 1 || (plate_history.size() == 0)) {
			return new HistoricalPlate[0];
		}

		if (history_length > plate_history.size()) {
			history_length = plate_history.size();
		}
		return plate_history.subList(plate_history.size()-history_length, plate_history.size()-1).toArray(new HistoricalPlate[history_length]);
	}

	@Override
	public HistoricalPlate[] getPlateHistory() {
		return getPlateHistory(plate_history.size());
	}

	@Override
	public double getBalance() {
		return balance;
	}

	@Override
	public void makeAndPlacePlate(Plate plate, int position) 
			throws InsufficientBalanceException, BeltFullException, AlreadyPlacedThisRotationException {

		if (already_placed_this_rotation) {
			throw new AlreadyPlacedThisRotationException();
		}
		
		if (plate.getContents().getCost() > balance) {
			throw new InsufficientBalanceException();
		}
		belt.setPlateNearestToPosition(plate, position);
		balance = balance - plate.getContents().getCost();
		already_placed_this_rotation = true;
	}

	@Override
	public void handleBeltEvent(BeltEvent e) {
		if (e.getType() == BeltEvent.EventType.PLATE_CONSUMED) {
			Plate plate = ((PlateEvent) e).getPlate();
			if (plate.getChef() == this) {
				balance += plate.getPrice();
				Customer consumer = belt.getCustomerAtPosition(((PlateEvent) e).getPosition());
				HistoricalPlate to_be_added = new HistoricalPlateImpl(plate, consumer);
				plate_consumed.add(to_be_added);
				plate_history.add(to_be_added);
			}
		} else if (e.getType() == BeltEvent.EventType.PLATE_SPOILED) {
			Plate plate = ((PlateEvent) e).getPlate();
			HistoricalPlate plate_to_add = new HistoricalPlateImpl(plate, null);
			plate_history.add(plate_to_add);
			plate_spoiled.add(plate_to_add);
		} else if (e.getType() == BeltEvent.EventType.ROTATE) {
			already_placed_this_rotation = false;
		}
	}
	
	@Override
	public boolean alreadyPlacedThisRotation() {
		return already_placed_this_rotation;
	}

	@Override
	public int getPlatesSold() {
		return plate_consumed.size();
	}
	
	public double getWeightSold() {
		double total = 0;
		for (int i = 0; i < plate_consumed.size(); i++) {
			for (int j = 0; j < plate_consumed.get(i).getContents().getIngredients().length; j++) {
				total += plate_consumed.get(i).getContents().getIngredients()[j].getAmount();
			}
		}
		return total;
	}
	
	public double getWeightSpoiled() {
		double total = 0;
		for (int i = 0; i < plate_spoiled.size(); i++) {
			for (int j = 0; j < plate_spoiled.get(i).getContents().getIngredients().length; j++) {
				total += plate_spoiled.get(i).getContents().getIngredients()[j].getAmount();
			}
		}
		return total;
	}

	@Override
	public int getPlatesSpoiled() {
		return plate_spoiled.size();
	}
}
