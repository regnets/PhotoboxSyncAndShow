package com.github.regnets.photobox.show;

import java.io.File;
import java.util.ArrayList;

public class Show implements Runnable {
	private FileListingMetaDataChecker fileListingMetadataChecker;
	private FileListingMetaDataContainer fileListingMetadataContainer;
	private File pictureFolder;
	private int displayTime;
	private GUI gui;

	public Show(String pictureFolder, ArrayList<String> validExtensions, int displayTime) {
		gui = new GUI();
		fileListingMetadataChecker = new FileListingMetaDataChecker();
		for (String validExtension : validExtensions) {
			fileListingMetadataChecker.addValidExtension(validExtension);
		}

		fileListingMetadataContainer = new FileListingMetaDataContainer();
		this.displayTime = displayTime;
		this.pictureFolder = new File(pictureFolder);

	}

	public void run() {
		this.syncFolderContent(true);
		do {
			this.showSomething();
			this.syncFolderContent(false);
			try {
				Thread.sleep(displayTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (true);
	}

	private void syncFolderContent(boolean initialScan) {
		// Neue Dateien hinzufügen, bei jeden weiteren außer den ersten Scan,
		// versuchen wir den Counter um eins zu erniedriegen, damit diese Datei
		// als erstes angezeigt wird,
		int minCount = fileListingMetadataContainer.getSmallestCounter();
		if (!initialScan) {
			if (minCount > 0) {
				minCount--;
			}
		}

		for (File file : pictureFolder.listFiles()) {
			if (fileListingMetadataChecker.hasValidExtension(file.getAbsolutePath())) {
				if (!fileListingMetadataContainer.contains(file.getAbsolutePath())) {
					fileListingMetadataContainer.add(file.getAbsolutePath(), minCount);
				}
			}
		}

		// Vorhandene prüfen
		for (int i = 0; i < fileListingMetadataContainer.size(); i++) {
			if (!((new File(fileListingMetadataContainer.getFilePath(i))).exists())) {
				fileListingMetadataContainer.remove(i);
			}
		}
	}

	private void showSomething() {
		String currentFilename = fileListingMetadataContainer.getRandomFilePathWithSmallestCountAndIncreaseCounter();
		System.out.println("Showing " + currentFilename);
		gui.refreshImage(currentFilename);
	}

}
