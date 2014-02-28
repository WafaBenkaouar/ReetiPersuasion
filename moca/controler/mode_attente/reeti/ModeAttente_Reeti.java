package controler.mode_attente.reeti;

import java.util.Random;

import moca.client.connection.DeviceClient;
import moca.thrift.utils.DSA_ConnectionListener;
import output.reeti_controler.JointReeti;
import output.reeti_controler.ReetiPose;
import controler.math_woz_devices.WozReeti;

public class ModeAttente_Reeti implements DSA_ConnectionListener {

	private final DeviceClient deviceLink;

	// AJOUT DES WozDevices
	private final WozReeti wozReeti;
	public boolean breathing = false;
	public boolean gazing = false;
	public boolean smiling = false;
	public boolean blinking = false;
	public boolean vocals = false;
	public static String[] vocalList = { "\\item=Throat", "\\item=throat_01", "\\item=throat_02",
					"\\item=throat_03", "\\item=Cough", "\\item=cough_01", "\\item=cough_02" };

	public static void main(String[] args) {
		new ModeAttente_Reeti();
	}

	public ModeAttente_Reeti() {

		// CREATE MANAGERS OF KNOW DEVICES TYPES
		wozReeti = new WozReeti();

		// INIT MOCA DSA LINK----
		String configuration_path = "./config/controler_mode-attente_reeti.xml";

		// CONNECT TO MOCA
		DeviceSideRPC_Impl implementation = new DeviceSideRPC_Impl(this);
		deviceLink = DeviceClient.start(configuration_path, this, implementation, true);
		implementation.setDevice(deviceLink);

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

	public void breath(float t, int amplitude, double frequence, float dephasage) {
		ReetiPose p_neck_init = new ReetiPose("neck_init");
		p_neck_init.ajouteJoint(JointReeti.HOCHEMENT_TETE);
		p_neck_init.ajouteMouvement(JointReeti.HOCHEMENT_TETE, 71.5f);

		// inspiration movement
		ReetiPose p_neck_inspiration = new ReetiPose("neck_insp");
		p_neck_inspiration.ajouteJoint(JointReeti.INCLINAISON_COU);
		p_neck_inspiration.ajouteMouvement(JointReeti.HOCHEMENT_TETE, amplitude);

		// compute expiration movement

		return;
	}

	public void blink(int MIN_DURATION_CLOSING, int MAX_DURATION_CLOSING, int MIN_DURATION_BETWEEN,
					int MAX_DURATION_BETWEEN, int MIN_DURATION_OPENING, int MAX_DURATION_OPENING,
					int MIN_DURATION_AFTER, int MAX_DURATION_AFTER) {

	}

	public void gazeMvt(int type, int frequence) {

	}

	public void rictus(int type, int frequence) {

	}

	public void vocal() {
		Random rnd = new Random();
		this.wozReeti.say(vocalList[rnd.nextInt(vocalList.length)]);
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

	/*
	 * public void play(String message){ for(int i =0; i<persuasionPref.length;
	 * i++){ if (persuasionPref[i] == message) message = emo[i]; }
	 * wozReeti.sendUrbiCommand(message);
	 * System.out.println("player.playPose(\"Emotions/"+message+".load\");"); }
	 */

	/*
	 * public void setRandomPreferences(){ Random rnd = new Random(); for (int i
	 * = persuasionPref.length - 1; i > 0; i--) { int index = rnd.nextInt(i +
	 * 1); // Simple swap String a = persuasionPref[index];
	 * persuasionPref[index] = persuasionPref[i]; persuasionPref[i] = a; }
	 * 
	 * }
	 */

	public void start() {
	}

	public void stop() {
	}

}
