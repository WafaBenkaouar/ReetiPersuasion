package moca.output.reeti_controler;

import java.util.Vector;

public enum JointReeti {

	ROTATION_COU, HOCHEMENT_TETE, INCLINAISON_COU, COMMISSURE_GAUCHE, COMMISSURE_DROITE, LEVRE_INFERIEURE, LEVRE_SUPERIEURE, OREILLE_GAUCHE, OREILLE_DROITE, OEIL_GAUCHE_HORIZONTAL, OEIL_GAUCHE_VERTICAL, PAUPIERE_GAUCHE, OEIL_DROIT_HORIZONTAL, OEIL_DROIT_VERTICAL, PAUPIERE_DROITE,

	TOUS; // l'ensemble de tous les joints

	@Override
	public String toString() {
		String result = "";
		switch (this) {
		case ROTATION_COU:
			result = "neckRotat";
			break;
		case HOCHEMENT_TETE:
			result = "neckTilt";
			break;
		case INCLINAISON_COU:
			result = "neckPan";
			break;
		case COMMISSURE_GAUCHE:
			result = "leftLC";
			break;
		case COMMISSURE_DROITE:
			result = "rightLC";
			break;
		case LEVRE_SUPERIEURE:
			result = "topLip";
			break;
		case LEVRE_INFERIEURE:
			result = "bottomLip";
			break;
		case OREILLE_GAUCHE:
			result = "leftEar";
			break;
		case OREILLE_DROITE:
			result = "rightEar";
			break;
		case OEIL_GAUCHE_VERTICAL:
			result = "leftEyeTilt";
			break;
		case OEIL_DROIT_VERTICAL:
			result = "rightEyeTilt";
			break;
		case OEIL_GAUCHE_HORIZONTAL:
			result = "leftEyePan";
			break;
		case OEIL_DROIT_HORIZONTAL:
			result = "rightEyePan";
			break;
		case PAUPIERE_GAUCHE:
			result = "leftLid";
			break;
		case PAUPIERE_DROITE:
			result = "rightLid";
			break;
		case TOUS:
			result = "Head";
		}
		return result;
	}

	public static JointReeti toJoint(String motorName) {
		JointReeti joint = JointReeti.TOUS;
		if (motorName.equals("neckRotat")) {
			joint = JointReeti.ROTATION_COU;
		} else if (motorName.equals("neckTilt")) {
			joint = JointReeti.HOCHEMENT_TETE;
		} else if (motorName.equals("neckPan")) {
			joint = JointReeti.INCLINAISON_COU;
		} else if (motorName.equals("leftLC")) {
			joint = JointReeti.COMMISSURE_GAUCHE;
		} else if (motorName.equals("rightLC")) {
			joint = JointReeti.COMMISSURE_DROITE;
		} else if (motorName.equals("topLip")) {
			joint = JointReeti.LEVRE_SUPERIEURE;
		} else if (motorName.equals("bottomLip")) {
			joint = JointReeti.LEVRE_INFERIEURE;
		} else if (motorName.equals("leftEar")) {
			joint = JointReeti.OREILLE_GAUCHE;
		} else if (motorName.equals("rightEar")) {
			joint = JointReeti.OREILLE_DROITE;
		} else if (motorName.equals("leftEyeTilt")) {
			joint = JointReeti.OEIL_GAUCHE_VERTICAL;
		} else if (motorName.equals("rightEyeTilt")) {
			joint = JointReeti.OEIL_DROIT_VERTICAL;
		} else if (motorName.equals("leftEyePan")) {
			joint = JointReeti.OEIL_GAUCHE_HORIZONTAL;
		} else if (motorName.equals("rightEyePan")) {
			joint = JointReeti.OEIL_DROIT_HORIZONTAL;
		} else if (motorName.equals("leftEyeLid")) {
			joint = JointReeti.PAUPIERE_GAUCHE;
		} else if (motorName.equals("rightEyeLid")) {
			joint = JointReeti.PAUPIERE_DROITE;

		} else if (motorName.equals("Body")) {
			joint = JointReeti.TOUS;
		} else {
			joint = JointReeti.TOUS;
		}
		return joint;
	}

	public static Vector<JointReeti> getJoints(Vector<String> motors) {
		Vector<JointReeti> joints = new Vector<JointReeti>();

		for (String motorName : motors) {
			JointReeti joint = JointReeti.toJoint(motorName);
			joints.add(joint);
		}

		return joints;
	}

	public static JointReeti[] getJoints() {
		JointReeti[] joints = { JointReeti.ROTATION_COU, JointReeti.HOCHEMENT_TETE,
						JointReeti.INCLINAISON_COU, JointReeti.COMMISSURE_GAUCHE,
						JointReeti.COMMISSURE_DROITE, JointReeti.LEVRE_SUPERIEURE,
						JointReeti.LEVRE_INFERIEURE, JointReeti.OREILLE_GAUCHE,
						JointReeti.OREILLE_DROITE, JointReeti.OEIL_GAUCHE_VERTICAL,
						JointReeti.OEIL_DROIT_VERTICAL, JointReeti.OEIL_GAUCHE_HORIZONTAL,
						JointReeti.OEIL_DROIT_HORIZONTAL, JointReeti.PAUPIERE_GAUCHE,
						JointReeti.PAUPIERE_DROITE };
		return joints;

	}
}
