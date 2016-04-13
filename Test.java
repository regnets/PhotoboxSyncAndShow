import java.io.File;

public class Test {
	private String rootFolderPath = "H:\\Eigene Dateien\\";
	
	private FileListingMetaDataChecker fileListingMetadataChecker;
	private FileListingMetaDataContainer fileListingMetadataContainer;
	private File rootFolder;
	
	public Test() {
		fileListingMetadataChecker = new FileListingMetaDataChecker();
		fileListingMetadataChecker.addValidExtension("txt");

		fileListingMetadataContainer = new FileListingMetaDataContainer();

		rootFolder = new File(rootFolderPath);
	}

	private void syncFolderContent(boolean initialScan) {
		//Neue Dateien hinzufügen, bei jeden weiteren außer den ersten Scan, versuchen wir den Counter um eins zu erniedriegen, damit diese Datei als erstes angezeigt wird,
		int minCount = fileListingMetadataContainer.getSmallestCounter();
		if (!initialScan) {
			if (minCount > 0) {
				minCount--;
			}
		}
			
		for (File file : rootFolder.listFiles()) {
			if (fileListingMetadataChecker.hasValidExtension(file.getAbsolutePath())) {
				if (!fileListingMetadataContainer.contains(file.getAbsolutePath())) {
					fileListingMetadataContainer.add(file.getAbsolutePath(), minCount);
				}
			}
		}
		
		//Vorhandene prüfen
		for (int i = 0; i < fileListingMetadataContainer.size(); i++) {
			if (!((new File(fileListingMetadataContainer.getFilePath(i))).exists())) {
				fileListingMetadataContainer.remove(i);
			}
		}
	}

	private void showSomething() {
		System.out.println(fileListingMetadataContainer.getRandomFilePathWithSmallestCountAndIncreaseCounter());
	}

	public static void main(String args[]) {
		final Test t = new Test();
		t.syncFolderContent(true);
		do {
			t.showSomething();
			t.syncFolderContent(false);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (true);
		
	}
}






