package com.github.regnets.photobox.data;
import java.util.ArrayList;

import com.github.regnets.photobox.sync.FlashAirAPI;

public class FlashAirCard {
	private String address;
	private String baseDirectory;
	private boolean connected;
	private ArrayList<String> fileURLs;
	private ArrayList<String> newFileURLs;
	FlashAirAPI api;

	public FlashAirCard(String address, String baseDirectory) {
		setAddress(address);
		setBaseDirectory(baseDirectory);
		fileURLs = new ArrayList<String>();
		newFileURLs = new ArrayList<String>();
		connected = false;
		api = new FlashAirAPI();
	}

	public void changeBaseDirectory(String baseDirectory) {
		setBaseDirectory(baseDirectory);
	}

	public boolean isConnected() {
		return connected;
	}

	public boolean hasFilesChanged() {
		return api.hasFilesChanged(address, baseDirectory);
	}

	public void connect() {
		connected = api.connect(address);
	}

	public ArrayList<String> getFileList() {
		return api.getFileList(address, baseDirectory);
	}

	public ArrayList<String> getNewFileList() {
		ArrayList<String> currentFileURLs = getFileList();
		newFileURLs.clear();
		
		for (String currentFileURL : currentFileURLs) {
			boolean found = false;
			for (String fileURL : fileURLs) {
				if (currentFileURL.equals(fileURL)) {
					found = true;
				}
			}
			if (found == false) {
				newFileURLs.add(currentFileURL);
			}
		}
		if (fileURLs.size() == 0) {
			for (String currentFileURL : currentFileURLs) {
				fileURLs.add(currentFileURL);
			}
		}
		return newFileURLs;
	}

	
	public int getNumberOfFiles() {
		return api.getFileCount(address, baseDirectory);
	}

	public String getName() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setBaseDirectory(String baseDirectory) {
		if (!baseDirectory.startsWith("/")) {
			baseDirectory += "/";
		}
		if (baseDirectory.endsWith("/")) {
			baseDirectory = baseDirectory.substring(0,
					baseDirectory.length() - 1);
		}
		this.baseDirectory = baseDirectory;
	}

	public String getBaseDirectory() {
		return baseDirectory;
	}

	private String VarVal(String var, String val) {
		return var + ": " + val + "\n";
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(VarVal("Name", address));
		sb.append(VarVal("Base Directory", baseDirectory));
		sb.append(VarVal("Connected", String.valueOf(connected)));
		sb.append(VarVal("File Count", String.valueOf(getNumberOfFiles())));
		sb.append(VarVal("Files Changed?", String.valueOf(hasFilesChanged())));
		return sb.toString();
	}

}
