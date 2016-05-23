package com.github.regnets.photobox.sync;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import com.github.regnets.photobox.Utility;
import com.github.regnets.photobox.data.FlashAirCard;
import com.github.regnets.photobox.data.FlashAirCardList;

public class Sync implements Runnable {
	private FlashAirCardList facl;
	private String syncFolder;
	private int syncTime;


	public Sync(FlashAirCardList facl, String syncFolder, int syncTime) {
		this.facl = facl;
		this.syncFolder = syncFolder;
		this.syncTime = syncTime;
	}
	
	public void run() {
		while (true) {
			for (FlashAirCard fac : facl) {
				fac.connect();
				if (fac.isConnected()) {
					for (String fileURL : fac.getNewFileList()) {
						if (!Files.exists(Paths.get(getLocalPath(syncFolder, fileURL)))) {
							Utility.print(new Date() + " Saving file " + fileURL);
							saveImage(fileURL, syncFolder);
						}
					}
				}
				try {
					Thread.sleep(syncTime);
				} catch (InterruptedException e) {

				}
			}
		}
	}

	public String getLocalFileName(String fileURL) {
		try {
			URL url = new URL(fileURL);
			return url.getHost() + "_" + url.getFile().substring(url.getFile().lastIndexOf("/") + 1);
		} catch (MalformedURLException e) {
		}
		return "";
	}

	public String getLocalPath(String localFolder, String fileURL) {
		return localFolder + "/" + getLocalFileName(fileURL);
	}

	public void saveImage(String url, String localPath) {
		try {
			URL url1 = new URL(url);
			String fileName = localPath + "/" + url1.getHost() + "_" + url1.getFile().substring(url1.getFile().lastIndexOf("/") + 1);
			if (!Files.exists(Paths.get(fileName))) {
				InputStream in = new BufferedInputStream((new URL(url)).openStream());
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] buf = new byte[1024];
				int n = 0;
				while (-1 != (n = in.read(buf))) {
					out.write(buf, 0, n);
				}
				out.close();
				in.close();
				byte[] response = out.toByteArray();
				FileOutputStream fos = new FileOutputStream(fileName);
				fos.write(response);
				fos.close();
			} else {
				System.out.println("File Exists");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
