package com.github.regnets.photobox.data;

import java.util.ArrayList;

public class FlashAirCardList extends ArrayList<FlashAirCard> {
	private static final long serialVersionUID = -5340096411256635659L;
	
	public void add(String name, String baseDirectory) {
		this.add(new FlashAirCard(name, baseDirectory));
		
	}

	public String getInfoForAllCards() {
		StringBuilder sb = new StringBuilder();
		for (FlashAirCard fac : this) {
			sb.append(fac.toString());
			sb.append("\n");
		}
		return sb.toString();
	}

}
