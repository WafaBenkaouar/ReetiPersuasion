package moca.output.reeti_controler;

import liburbi.UClient;

public class ReetiSequence {

	/*
	 * A sequence starts from a pose p0 and can intertoplate to P1 and then back
	 * to p0 according to some parameters
	 */
	// D'abord on cronstruit p0 à partir de l'état courant
	// puis on initialise p1
	// on utilise plusieur mode d'interpolation pour passé de p0 à p1 avec poss
	// de changer param SIRRAD
	ReetiPose[] poses;
	ReetiPose p0;
	UClient cli;

	public ReetiSequence(UClient cli, ReetiPose[] _p1) {
		this.cli = cli;
		this.poses = _p1;
	}

	// TODO stuck here cause can't fetch the current position
	public void initP0() {
		// si un moteur va etre active par P1 alors on prend sa valeur courante,
		// sinon pas besoin
		p0 = new ReetiPose("p0");
		p0.ajouteJoint(JointReeti.TOUS);
		for (JointReeti j : this.p0.getJoints()) {

		}
	}

}
