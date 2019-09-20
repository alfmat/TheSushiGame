package sushigame.view;

import java.util.Comparator;

import sushigame.model.Chef;

public class PlatesSpoiledComparator implements Comparator<Chef> {

	@Override
	public int compare(Chef a, Chef b) {
		// TODO Auto-generated method stub
		return (int)(Math.round(a.getWeightSpoiled() * 100.0) - Math.round(b.getWeightSpoiled() * 100.0));
	}

}
