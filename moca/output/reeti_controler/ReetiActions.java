package moca.output.reeti_controler;
import java.awt.Color;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import liburbi.UClient;


public class ReetiActions {
	
	UClient cli;
	
			public static String ROTATION_COU = "neckRotat";
			public static String HOCHEMENT_TETE = "neckTilt";
			public static String INCLINAISON_COU = "neckPan";
			public static String COMMISSURE_GAUCHE = "leftLC";
			public static String COMMISSURE_DROITE = "rightLC";
			public static String LEVRE_INFERIEURE = "bottomLip";
			public static String LEVRE_SUPERIEURE = "topLip";
			public static String OREILLE_GAUCHE = "leftEar"; 
			public static String OREILLE_DROITE = "rightEar";
			public static String OEIL_GAUCHE_HORIZONTAL = "leftEyePan";
			public static String OEIL_GAUCHE_VERTICAL = "leftEyeTilt";
			public static String PAUPIERE_GAUCHE_ = "leftEyeLid";
			public static String OEIL_DROIT_HORIZONTAL = "rightEyePan";
			public static String OEIL_DROIT_VERTICAL = "rightEyeTilt";
			public static String PAUPIERE_DROITE = "rightEyeLid";
	
	public ReetiActions(String ip) throws Exception {
		

			this.cli = new UClient(ip, 54001, UClient.URBI_BUFLEN) ;
		
		
	}
	
	public void stop(){
		cli.disconnect();
	}
	
	public void setYellow(){
		try {
			setNeutral();
			
			
			setColor(Color.YELLOW);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setGreen(){
		try {
			setNeutral();
			
			setVariable(COMMISSURE_GAUCHE, 100);
			setVariable(COMMISSURE_DROITE, 100);
			setVariable(LEVRE_INFERIEURE, 0);
			
			setVariable(OREILLE_DROITE, 100);
			setVariable(OREILLE_GAUCHE, 100);
			
			setVariable(HOCHEMENT_TETE, 80);
			
			setColor(Color.GREEN);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setRed(){
		try {
			setNeutral();
			
			setVariable(COMMISSURE_GAUCHE, 0);
			setVariable(COMMISSURE_DROITE, 0);
			setVariable(LEVRE_INFERIEURE, 0);
			
			setVariable(OREILLE_DROITE, 0);
			setVariable(OREILLE_GAUCHE, 0);
			
			setVariable(HOCHEMENT_TETE, 0);
			
			setColor(Color.RED);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setNeutral(){
		try {
			cli.send("servo.neutralPosition() ;");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setColor(Color color) throws IOException{
		
		int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        
		cli.send("servo.changeLedColorRGB(0,"+ r + "," + g + "," + b + ",1) ; ");
		
	}
	
	public void sayS(String phrase){
		try {
			cli.send("tts.sayWithSynchro(\" "+ phrase + " \");");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void say(String phrase){
		try {
			cli.send("tts.say(\" "+ phrase + " \");");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void playFile(String filename){
		try {
			
			cli.sendFile(filename);
			this.setNeutral();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void blinkLeft(){
		try {
			setVariable(PAUPIERE_GAUCHE_, 0);
			
			TimeUnit.SECONDS.sleep(1);
			
			setVariable(PAUPIERE_GAUCHE_, 100);
			
		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void blinkRight(){
		try {
			setVariable(PAUPIERE_DROITE, 0);
			
			TimeUnit.SECONDS.sleep(1);
			
			setVariable(PAUPIERE_DROITE, 100);
			
		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setVariable(String variable , String value){
		try {
			cli.send("servo." + variable + "=" + value + "  ;");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setVariable(String variable , Number value){
		setVariable(variable, ""+value);
	}
	
 public void getInfo(){
	 
	 
	 
 }
	
	

}
