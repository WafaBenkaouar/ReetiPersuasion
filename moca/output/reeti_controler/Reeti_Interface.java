package moca.output.reeti_controler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import liburbi.UClient;
import liburbi.call.UCallbackListener;
import liburbi.call.URBIEvent;

import org.egl.libs.EGL_Utils;

public class Reeti_Interface implements UCallbackListener {

	static final String IP_REETI = "129.88.50.112";

	static UClient cli;

	double d = 0;

	enum transition {
		smooth, time, speed, accel, sin, timeout
	};

	static double[] happy = { 50.5, 67.5, 81.0, 100.0, 100.0, 65.0, 39.0, 100.0, 100.0, 50.0, 50.0,
					48.0, 32.0, 100.0, 100.0 };
	static double[] sad = { 45.5, 40.5, 40.0, 0.0, 0.0, 100.0, 60.0, 0.0, 0.0, 50.0, 50.0, 48.0,
					32.0, 70.0, 70.0 };
	static double[] mhappy;
	static double[] msad;
	static double[] mneutral;

	public static Double[] values_motors = new Double[15];
	public static String[] names_motors = new String[15];

	public static Map<Integer, String> servos = new HashMap<Integer, String>();

	public Reeti_Interface() {
		init();

	}

	public void init() {
		try {
			this.cli = new UClient(IP_REETI, 54001, UClient.URBI_BUFLEN);
		} catch (Exception e) {
			System.out.println("Exception on client creation : " + e);
		}
		System.out.println("INFO : Uclient created");

		names_motors[0] = "neckRotat";
		names_motors[1] = "neckTilt";
		names_motors[2] = "neckPan";
		names_motors[3] = "leftLC";
		names_motors[4] = "rightLC";
		names_motors[5] = "topLip";
		names_motors[6] = "bottomLip";
		names_motors[7] = "rightEar";
		names_motors[8] = "leftEar";
		names_motors[9] = "rightEyeTilt";
		names_motors[10] = "leftEyeTilt";
		names_motors[11] = "rightEyePan";
		names_motors[12] = "leftEyePan";
		names_motors[13] = "rightEyeLid";
		names_motors[14] = "leftEyeLid";

		for (int i = 0; i < 14; i++) {
			values_motors[i] = (double) 50;
			servos.put(i, names_motors[i]);
		}

		mhappy = interpolationPose(happy, sad, 0.66);
		msad = interpolationPose(happy, sad, 0.33);
		mneutral = interpolationPose(happy, sad, 0.50);
		mneutral[5] = 0;
		mneutral[6] = 100;
		mneutral[3] = mneutral[4] = 50;

		this.cli.setCallback(this, "dist");

	}

	public static void main(String[] args) {

		Reeti_Interface ri = new Reeti_Interface();
		// ri.init();
		try {
			ri.cli = new UClient(IP_REETI, 54001, UClient.URBI_BUFLEN);
			ri.cli.setCallback(ri, "dist");
		} catch (Exception e) {
			System.out.println("Exception on client creation : " + e);
		}
		System.out.println("INFO : Uclient created");
		// say("Hi");

		/*
		 * double[] happy = { 50.5, 67.5, 81.0, 100.0, 100.0, 65.0, 39.0, 100.0,
		 * 100.0, 50.0, 50.0, 48.0, 32.0, 100.0, 100.0 }; double[] sad = { 45.5,
		 * 40.5, 40.0, 0.0, 0.0, 100.0, 60.0, 0.0, 0.0, 50.0, 50.0, 48.0, 32.0,
		 * 70.0, 70.0 }; double[] mhappy = interpolationPose(happy, sad, 0.66);
		 * double[] msad = interpolationPose(happy, sad, 0.33);
		 * 
		 * ReetiPose p0 = new ReetiPose("p0");
		 * p0.ajouteJoint(JointReeti.OREILLE_DROITE);
		 * p0.ajouteMouvement(JointReeti.OREILLE_DROITE, 0);
		 * p0.setColor("rouge"); System.out.println(p0.toString());
		 */
		// TODO
		// UCallbackListener listener = new UCallbackListener();
		try {
			// cli.setCallback(listener, "Global.servo");
			ri.cli.send("loop dist: Global.servo.leftEyeLid;");
			ri.cli.send("loop dist: Global.servo.rightEar;");
			ri.cli.send("dist: Global.servo.rightEar=50;");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * System.out.println(p0.toUrbiCommand("smooth", 0.3f));
		 * ri.sendUrbi(p0.toUrbiCommand("smooth", 0.3f)); System.out.println(""
		 * + ri.cli.getRecvBuffer());
		 */
		/*
		 * setMotorsValue(happy); moveAllMotors(transition.smooth, 1);
		 * say("coucou");
		 * 
		 * setMotorsValue(sad); moveAllMotors(transition.smooth, 1);
		 * 
		 * setMotorsValue(mhappy); moveAllMotors(transition.smooth, 1);
		 * 
		 * setMotorsValue(happy); moveAllMotors(transition.smooth, 1);
		 * 
		 * setMotorsValue(msad); moveAllMotors(transition.smooth, 1);
		 * 
		 * setMotorsValue(sad); moveAllMotors(transition.smooth, 1);
		 */
	}

	public static double slerp(double a, double b, double percent) {
		return (Math.abs(a - b) * percent) + Math.min(a, b);
	}

	/*
	 * Linear interpolation of a new pose between 2 poses according to
	 * percentage of the first pose
	 * 
	 * @param 2 list of parameters values for the motors
	 * 
	 * @param percentage of pa from 0 to 1
	 * 
	 * @return a pose
	 */
	public static double[] interpolationPose(double[] pa, double[] pb, double percent) {

		double[] res = new double[15];
		for (int i = 0; i < pa.length; i++) {
			res[i] = slerp(pa[i], pb[i], percent);
		}
		return res;

	}

	public static void lookAtScreen() {

		setVariable(names_motors[2], 100);
		EGL_Utils.delay(1000);
		setVariable(names_motors[3], 0);
		EGL_Utils.delay(1000);
		setVariable(names_motors[7], 0);

	}

	public static void lookAtUser() {

		setVariable(names_motors[7], 100);
		EGL_Utils.delay(1000);
		setVariable(names_motors[8], 0);
		EGL_Utils.delay(1000);
		setVariable(names_motors[3], 100);
	}

	private static void move() {

		setVariable(names_motors[4], 100);
		setVariable(names_motors[3], 100);
		setVariable(names_motors[6], 100);

		setVariable(names_motors[7], 100);
		setVariable(names_motors[8], 0);

		setVariable(names_motors[1], 100);

	}

	/*
	 * public String[] XMLReader(String path){ SAXBuilder sxb=new SAXBuilder();
	 * org.jdom.Document document; try { document = sxb.build(new File(path)); }
	 * catch (JDOMException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); }
	 * 
	 * Element racine = document.getRootElement(); List<Element>
	 * listRec=racine.getChildren("pose");
	 * 
	 * 
	 * }
	 */
	public static String[] listPose() {
		// File poses = new File("Reeti_poses.txt");
		String[] pose = { "labas", "sleepy", "Neutral" };

		return pose;

	}

	public static void walk() {

		setVariable(names_motors[4], 0);
		setVariable(names_motors[3], 0);
		setVariable(names_motors[6], 0);

		setVariable(names_motors[7], 0);
		setVariable(names_motors[8], 0);

		setVariable(names_motors[1], 0);
		System.out.println("WALK TO DONE");

	}

	public static void setVariable(String variable, String value) {
		try {
			cli.send("servo." + variable + "=" + value + "  ;");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void moveAll(double[] values) {
		setMotorsValue(values);
		try {
			cli.send("servo.moveAll" + values + "  ;");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void setMotorsValue(double[] values) {
		int i = 0;
		while (i < values_motors.length) {
			values_motors[i] = values[i];
			i++;
		}
	}

	public static String moveAllMotors(transition t, double stroke_time) {
		String urbi_message = "{";
		for (int i = 0; i < values_motors.length; i++) {
			urbi_message += "Global.servo." + names_motors[i] + "=" + values_motors[i] + " "
							+ t.name() + ":" + stroke_time + "s, \n";
		}
		urbi_message += "";
		return urbi_message;
	}

	public static void sendUrbi(String message) {
		String color = "Global.servo.color=\"";
		if (message.contains("mhappy")) {
			setMotorsValue(mhappy);
			color += "yellow\",";
		} else if (message.contains("happy")) {
			setMotorsValue(happy);
			color += "red\",";
		} else if (message.contains("msad")) {
			setMotorsValue(msad);
			color += "green\",";
		} else if (message.contains("sad")) {
			setMotorsValue(sad);
			color += "blue\",";
		} else {
			setMotorsValue(mneutral);
			color += "white\",";
		}

		try {
			cli.send(moveAllMotors(transition.smooth, 0.2) + color + "};");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void setVariable(String variable, Number value) {
		setVariable(variable, "" + value);
	}

	public static void say(String phrase) {
		try {
			cli.send("tts.sayWithSynchro(\" " + phrase + " \");");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*************************************************/
	public static void regard(int maintien, int rate) {
		try {
			cli.send("tts.sayWithSynchro(\"  \");");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ------------------------------------------------
	public static boolean isReachableByPing() {

		return Pinger.isReachablebyPing(IP_REETI);
	}

	@Override
	public void actionPerformed(URBIEvent event) {
		// TODO Auto-generated method stub
		System.out.println("Caught event " + event.getTag() + "for command " + event.getCmd());
		if (event.getTag().equals("dist")) {
			String distval = event.getCmd();
			try {
				d = Double.parseDouble(distval);
			} catch (Exception e) {

			}
			System.out.println(" got event : " + event.getTag() + " val " + distval + " in double"
							+ d);
		}

	}
}
