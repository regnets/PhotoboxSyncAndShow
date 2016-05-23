package com.github.regnets.photobox.show;

import java.util.ArrayList;

public class FileListingMetaDataChecker {
	private ArrayList<String> validExtensions;

	public FileListingMetaDataChecker() {
		validExtensions = new ArrayList<String>();
	}

	public void addValidExtension(String extension) {
		if (!validExtensions.contains(extension)) {
			validExtensions.add(extension);
		}
	}

	public boolean hasValidExtension(String filePath) {
		String extension = "";

		int i = filePath.lastIndexOf('.');
		int p = Math.max(filePath.lastIndexOf('/'), filePath.lastIndexOf('\\'));

		if (i > p) {
			extension = filePath.substring(i + 1);
			if (validExtensions.contains(extension)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}