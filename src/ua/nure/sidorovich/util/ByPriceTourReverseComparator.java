package ua.nure.sidorovich.util;

import java.util.Comparator;

import ua.nure.sidorovich.entety.Tour;


public class ByPriceTourReverseComparator implements Comparator<Tour>{

	@Override
	public int compare(Tour tour1, Tour tour2) {
		if(tour1.isItHot() && !tour2.isItHot()){
			return -1;
		} else if(!tour1.isItHot() && tour2.isItHot()){
			return 1;
		} else {
			return - (int)(tour2.getPrice() - tour1.getPrice());
		}
	}

}
