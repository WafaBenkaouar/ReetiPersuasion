package moca.output.reeti_controler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Pinger {


	private static boolean checkAvailability(List<String> outputLines) {

		for(String line : outputLines) {
			if(line.contains("unreachable")) {
				return false;
			}
			if(line.contains("TTL=") || line.contains(", 0.0% packet loss")) {
				return true;
			}
		}
		return false;

	}

	public static boolean isReachablebyPing(String ip) {

		try {
			String command;

			if(System.getProperty("os.name").toLowerCase().startsWith("windows")) {
				// For Windows
				command = "ping -w 200 -n 2 " + ip;
			} else {
				// For Linux and OSX
				command = "ping -W 0.2 -c 2 " + ip;
			}

			Process proc = Runtime.getRuntime().exec(command);
			StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUTPUT");
			outputGobbler.start();

			proc.waitFor();
			return checkAvailability(outputGobbler.getOutputLines());

		} catch(IOException | InterruptedException ex) {
			Logger.getLogger(StreamGobbler.class.getName()).log(Level.SEVERE, null, ex);
		}

		return false;
	}

	static class StreamGobbler extends Thread {
		protected InputStream is;

		protected String type;

		protected List<String> outputLines;

		StreamGobbler(InputStream is, String type) {
			this.is = is;
			this.type = type;
			outputLines = new ArrayList<String>();
		}

		public List<String> getOutputLines() {
			return outputLines;
		}

		@Override
		public void run() {
			try {
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String line;
				while((line = br.readLine()) != null) {
					outputLines.add(line);
				}
			} catch(IOException ex) {
				Logger.getLogger(StreamGobbler.class.getName()).log(Level.SEVERE, null, ex);
			}

		}
	}
}
