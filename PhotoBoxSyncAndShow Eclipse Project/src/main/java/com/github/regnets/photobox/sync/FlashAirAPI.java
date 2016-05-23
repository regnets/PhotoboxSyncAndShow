package com.github.regnets.photobox.sync;
import java.util.ArrayList;

public class FlashAirAPI {
	SimpleHTTPClient client;

	private final String protocolPrefix = "http://";
	private final String apiEndpoint = "/command.cgi?";

	public FlashAirAPI() {
		client = new SimpleHTTPClient();
	}

	public boolean connect(String address) {
		client.getText(getURL(address));
		if (client.statusCode == 200) {
			return true;
		}
		return false;
	}

	public ArrayList<String> getFileList(String address, String path) {
		client.getText(getAPIURLWithOperationCode(address, 100) + "&DIR=" + path);
		ArrayList<String> tempFileList = new ArrayList<String>();
		if (client.statusCode == 200) {
			for (String line : client.lines) {
				if (line.contains(",")) {
					String[] values = line.split(",");
					tempFileList.add(getURL(address) + values[0] + "/" + values[1]);
				}
			}
		}
		return tempFileList;
	}

	public int getFileCount(String address, String path) {
		client.getText(getAPIURLWithOperationCode(address, 101) + "&DIR=" + path);
		if (client.statusCode == 200) {
			return Integer.parseInt(client.content);
		}
		return -1;
	}

	public boolean hasFilesChanged(String address, String path) {
		client.getText(getAPIURLWithOperationCode(address, 102));
		if (client.statusCode == 200) {
			if (client.content.equals("0")) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	private String getAPIURLWithOperationCode(String address, int operationCode) {
		return getAPIURL(address) + "op=" + String.valueOf(operationCode);
	}

	public String getAPIURL(String address) {
		return getURL(address) + apiEndpoint;
	}

	public String getURL(String address) {
		return protocolPrefix + address;
	}
}