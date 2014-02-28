package moca.controler.persuasion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import moca.client.connection.DeviceClient;
import moca.controler.math_woz_devices.WozReeti;
import moca.thrift.utils.DSA_ConnectionListener;

public class Persuasion implements DSA_ConnectionListener {

	private final DeviceClient deviceLink;

	// AJOUT DES WozDevices
	private final WozReeti wozReeti;
	public LinkedHashMap<String, String> persuasionPref = new LinkedHashMap();

	PersuasionGUI gui;
	public static final String[] emo = { "sad", "msad", "mhappy", "happy" };

	public static void main(String[] args) {
		new Persuasion();
	}

	public int nb_coups = 0;

	public Persuasion() {
		persuasionPref.put("Admirer", "sad");
		persuasionPref.put("Plaisanter", "msad");
		persuasionPref.put("Contraindre", "mhappy");
		persuasionPref.put("Se Vanter", "happy");
		gui = new PersuasionGUI(this);

		// CREATE MANAGERS OF KNOW DEVICES TYPES
		wozReeti = new WozReeti();

		this.setRandomPreferences();

		// INIT MOCA DSA LINK----
		String configuration_path = "./config/controler_persuasion.xml";
		// OVERIDES CONFIGURATION PATH (IF NEW ONE GIVEN)
		// if (args != null && args.length>0) configuration_path = args[0];

		// CONNECT TO MOCA
		DeviceSideRPC_Impl implementation = new DeviceSideRPC_Impl(this);
		deviceLink = DeviceClient.start(configuration_path, this, implementation, true);
		implementation.setDevice(deviceLink);

		gui.setVisible(true);
	}

	/**
	 * CALLED WHEN DSA IS CONNECTED
	 */
	@Override
	public void dsa_connected() {
		// TODO with inform
		/*
		 * System.out.println(
		 * "---------------------------------------- SUSCRIBING TO XBOX INFO ");
		 * try {
		 * deviceLink.CONFIG.call().subscribeToUpdate(deviceLink.CONFIG.DEVICE,
		 * new KnowledgeFilter().setName_filter("twitter_message.*"),
		 * ThriftUtils.DEFAULT_CALLBACK_KNOWLEDGE_SUBSCRIPTION ); } catch
		 * (Exception e) { e.printStackTrace();}
		 */

	}

	@Override
	public void dsa_disconnected() {
		// TODO Auto-generated method stub

	}

	/**
	 * CALLED WHEN DSA IS CONNECTED AND DEVICE LIST IS FORWARDED
	 */
	@Override
	public void connected_devices_updated() {

		// / SEARCH FOR REQUIERED CHANGES
		wozReeti.updateDeviceTarget(deviceLink);

	}

	public void play(String message) {

		wozReeti.sendUrbiCommand(persuasionPref.get(message));
	}

	public void say(String message) {
		wozReeti.say(message);
	}

	/*
	 * set the pr?????????f?????????rences of persuarion of the robot from 0 to 3 0 :
	 * deteste 1 : n'aime pas trop 2 : aime assez 3 : adore
	 */
	public void setRandomPreferences() {

		final List<Object> vs = new ArrayList<Object>(this.persuasionPref.values());
		Collections.shuffle(vs);
		System.out.println(vs);
		final Iterator<Object> vIter = vs.iterator();
		for (String k : this.persuasionPref.keySet())
			this.persuasionPref.put(k, "" + vIter.next());
		System.out.println(this.persuasionPref);

	}

	public LinkedHashMap<String, String> getPreferences() {
		return this.persuasionPref;
	}

	public Double getEmoValue(String emo) {

		switch (emo) {
		case "sad":
			return -1.0;
		case "msad":
			return -0.5;
		case "mhappy":
			return 0.5;
		case "happy":
			return 1.0;
		}
		return 0.0;

	}

	public void setPreferences(LinkedHashMap<String, String> pref) {
		this.persuasionPref = pref;
	}

}
