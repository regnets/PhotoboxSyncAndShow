package com.github.regnets.photobox.show;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

class FileListingMetaDataContainer {
	private ArrayList<FileListingMetaData> fileListingMetadata;
	private String lastFilePath = "";

	public FileListingMetaDataContainer() {
		fileListingMetadata = new ArrayList<FileListingMetaData>();
	}

	public String getFilePath(int i) {
		return fileListingMetadata.get(i).filePath;
	}

	public String getRandomFilePathWithSmallestCountAndIncreaseCounter() {
		int minCount = getSmallestCounter();
		System.out.println("minCount: " + minCount);
		ArrayList<FileListingMetaData> temp = new ArrayList<FileListingMetaData>();
		for (FileListingMetaData f : fileListingMetadata) {
			if (f.count == minCount && !(f.filePath.equals(lastFilePath))) {
				temp.add(f);
			}
		}
		//Per Zufall eine Datei aus den neu erzeugten Array auswählen.
		int r = ThreadLocalRandom.current().nextInt(0, temp.size());
		//Damit nie eine Datei zwei mal hintereinander angezeigt wird, wird der Pfad der Datei gespeichert und beim nächsten Durchgang ausgeschlossen.  
		lastFilePath = temp.get(r).filePath;
		
		temp.get(r).count++;
		return temp.get(r).filePath;
	}

	public int getSmallestCounter() {
		int minCount = Integer.MAX_VALUE;
		for (FileListingMetaData f : fileListingMetadata) {
			if (f.count < minCount) {
				minCount = f.count;
			}
		}
		if (minCount == Integer.MAX_VALUE) {
			minCount = 0;
		}
		return minCount;
	}

	public int getCount(int i) {
		return fileListingMetadata.get(i).count;
	}

	public int size() {
		return fileListingMetadata.size();
	}

	public void add(String filePath, int count) {
		fileListingMetadata.add(new FileListingMetaData(filePath, count));
	}

	public boolean contains(String filePath) {
		for (FileListingMetaData f : fileListingMetadata) {
			if (f.filePath.equals(filePath)) {
				return true;
			}
		}
		return false;
	}

	public void remove(int i) {
		fileListingMetadata.remove(i);
	}
}