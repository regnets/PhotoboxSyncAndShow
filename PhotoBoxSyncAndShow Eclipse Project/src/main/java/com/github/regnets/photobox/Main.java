/**
 * 
 */
package com.github.regnets.photobox;

import java.util.ArrayList;

import com.github.regnets.photobox.data.FlashAirCardList;
import com.github.regnets.photobox.show.Show;
import com.github.regnets.photobox.sync.Sync;

/**
 * @author andre
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Configuration configuration = new Configuration();
		FlashAirCardList facl = configuration.getCards();
		ArrayList<String> validExtensions = new ArrayList<String>();
		validExtensions.add("JPG");
		String syncFolder = configuration.getSyncFolder();
		Sync sync = new Sync(facl, syncFolder, configuration.getSyncTime());
		Show show = new Show(syncFolder, validExtensions, configuration.getDisplayTime());
		
		new Thread(sync).start();
		new Thread(show).start();
	}
}
