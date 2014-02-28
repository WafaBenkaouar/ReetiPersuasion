package moca.output.reeti_controler;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Vector;

import moca.output.reeti_controler.ReetiLedColor.Modes;

public class ReetiPose {

	private final LinkedHashMap<JointReeti, Float> positions;
	private final Vector<String> moteursC;
	private ReetiLedColor color;

	String name;

	public ReetiPose(String _name) {
		this.name = _name;
		this.moteursC = new Vector<String>();
		this.positions = new LinkedHashMap<JointReeti, Float>();
		this.color = new ReetiLedColor("stop");
	}

	public String toUrbiCommand(String transition, float time) {
		String urbi = "{";
		for (int i = 0; i < JointReeti.getJoints().length; i++) {
			if (positions.containsKey(JointReeti.getJoints()[i]))
				urbi += "Global.servo." + JointReeti.getJoints()[i].toString() + "="
								+ this.positions.get(JointReeti.getJoints()[i]) + " " + transition
								+ ":" + time + "s,";
		}
		if (color.getMode().equals(Modes.mode4))
			urbi += "Global.servo.changeColor(3,\"" + color.getName().toString() + "\",1),";

		urbi += "};";
		return urbi;
	}

	@Override
	public String toString() {
		String pose = "name=" + name + ";\n";
		for (int i = 0; i < JointReeti.getJoints().length; i++) {
			if (positions.containsKey(JointReeti.getJoints()[i]))
				pose += JointReeti.getJoints()[i].toString() + "="
								+ this.positions.get(JointReeti.getJoints()[i]) + "; \n";
			else
				pose += JointReeti.getJoints()[i].toString() + "=-1; \n";

		}
		if (color.getMode().equals(Modes.mode4))
			pose += "color=" + color.getName().toString();
		else
			pose += "color=(" + color.getMode().toInt() + "," + color.getR() + "," + color.getG()
							+ "," + color.getB() + "," + color.getIntensity() + ");\n";
		return pose;
		/*
		 * String xml = "<description> \n <Pose> \n"; xml += "<name>" +
		 * this.name + "</name>"; xml += "<startTime>" + this.start_time +
		 * "</startTime>"; xml += "<duration>" + this.duration + "</duration>";
		 * xml += "<backToNautral>" + this.back_to_neutral + "</backToNeutral>";
		 * 
		 * return xml;
		 */
	}

	public void ajouteJoint(JointReeti joint) {
		if (!this.moteursC.contains(joint.toString())) {
			this.moteursC.add(joint.toString());
			this.positions.put(joint, 0.0f);
		}
	}

	public void setColor(String color_name) {
		this.color = new ReetiLedColor(color_name);
	}

	public boolean ajouteMouvement(JointReeti joint, float position) {
		boolean mouvementAjoute = false;

		if (this.positions.containsKey(joint)) {
			float positionfloat = this.positions.get(joint);
			positionfloat = position;
			this.positions.put(joint, positionfloat);
			mouvementAjoute = true;
		}

		return mouvementAjoute;
	}

	public Vector<String> getMoteursLibelle() {
		return this.moteursC;
	}

	public float[] getfloatPositions() {
		float[] floatPositions = new float[JointReeti.getJoints().length];
		Arrays.fill(floatPositions, -1);
		for (int i = 0; i < JointReeti.getJoints().length; i++) {
			if (positions.containsKey(JointReeti.getJoints()[i]))
				floatPositions[i] = this.positions.get(JointReeti.getJoints()[i]);
		}
		return floatPositions;
	}

	public Set<JointReeti> getJoints() {
		return this.positions.keySet();
	}

}
