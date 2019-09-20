package sushigame.view;

import java.util.Comparator;

import sushigame.model.Chef;

public class PlatesSoldComparator implements Comparator<Chef> {

	@Override
	public int compare(Chef a, Chef b) {
		// TODO Auto-generated method stub
		return (int)(Math.round(b.getWeightSold() * 100) - Math.round(a.getWeightSold() * 100));
	}

}