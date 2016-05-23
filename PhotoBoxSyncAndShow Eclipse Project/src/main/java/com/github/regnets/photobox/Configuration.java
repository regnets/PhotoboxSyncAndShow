package com.github.regnets.photobox;

import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

import com.github.regnets.photobox.data.FlashAirCard;
import com.github.regnets.photobox.data.FlashAirCardList;

public class Configuration {
	private static String DEFAULT_CONFIGURATION_FILE_PATH = "config.xml";
	private XMLConfiguration xmlConfiguration;

	public Configuration() {
		this(DEFAULT_CONFIGURATION_FILE_PATH);
	}

	public Configuration(String configurationFilePath) {
		try {
			xmlConfiguration = new XMLConfiguration(configurationFilePath);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}

	public FlashAirCardList getCards() {
		FlashAirCardList tmp = new FlashAirCardList();
		List<HierarchicalConfiguration> fields = xmlConfiguration.configurationsAt("cards.card");
		for (HierarchicalConfiguration sub : fields) {
			tmp.add(new FlashAirCard(sub.getString("address"), sub.getString("basepath")));
		}
		return tmp;
	}

	public String getSyncFolder() {
		return xmlConfiguration.getString("syncfolder");
	}
	
	public int getSyncTime() {
		return xmlConfiguration.getInt("synctime");
	}
	
	public int getDisplayTime() {
		return xmlConfiguration.getInt("displaytime");
	}
	
}
