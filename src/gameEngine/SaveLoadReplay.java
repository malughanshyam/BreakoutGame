package gameEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/*
 * Save and load game replay file, encode and decode game state into json string and write it into replay file.
 */
public class SaveLoadReplay {
	private String ReplayFileName;
	private boolean withoutX11;// flag for junit test, forbid x11 usage in junit
								// test

	public SaveLoadReplay() {
		this.withoutX11 = false;
	}

	public boolean isWithoutX11() {
		return withoutX11;
	}

	public void setWithoutX11(boolean withoutX11) {
		this.withoutX11 = withoutX11;
	}

	/*
	 * Encode replay data into json, then store to a .json file
	 */
	public void SaveReplay(LinkedList<Object> ReplayList) throws IOException {
		FileWriter file = new FileWriter(getReplayFileName());
		for (Object obj : ReplayList) {
			file.write(encode((StoreDimensions) obj));
			file.flush();
		}
		file.close();
		if (!withoutX11) {
			File f = new File(getReplayFileName());
			JOptionPane.showMessageDialog(null,
					"Game Saved to the file " + f.getName() + " !");
		}
	}

	/*
	 * Read line by line from .json file, decode each line to replay data
	 */
	public LinkedList<Object> LoadReplay() throws IOException, ParseException {
		JSONParser parser = new JSONParser();
		BufferedReader br = new BufferedReader(new FileReader(
				getReplayFileName()));
		String line;
		Object object;
		LinkedList<Object> ReplayList = new LinkedList<Object>();
		while ((line = br.readLine()) != null) {
			// process the line.
			object = parser.parse(line);
			ReplayList.add(decode(object));
		}
		br.close();
		if (!withoutX11) {
			File f = new File(getReplayFileName());
			JOptionPane.showMessageDialog(null, "Game Load from " + f.getName()
					+ " complete! \nClick Resume to continue !");
		}
		return ReplayList;
	}

	/*
	 * encode StoreDimensions data structure into json
	 */
	public String encode(StoreDimensions obj) throws IOException {
		StringWriter out = new StringWriter();
		obj.writeJSONString(out);
		return out.toString() + "\r\n";
	}

	/*
	 * decode json object to a StoreDimensions data
	 */
	public StoreDimensions decode(Object obj) throws ParseException {
		JSONParser parser = new JSONParser();

		JSONObject jsonObject;
		StoreDimensions storedimensions = new StoreDimensions();

		obj = parser.parse(obj.toString());
		jsonObject = (JSONObject) obj;
		storedimensions.parseJSONString(jsonObject);
		return storedimensions;
	}

	public String getReplayFileName() {
		return ReplayFileName;
	}

	public void setReplayFileName(String replayFileName) {
		ReplayFileName = replayFileName;
	}
}
