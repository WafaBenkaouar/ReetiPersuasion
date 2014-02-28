package moca.output.reeti_controler;

import java.util.Vector;

import moca.client.connection.DeviceClient;

import org.egl.libs.EGL_Utils;
import org.egl.net.ip_network.IP_Client;
import org.egl.net.ip_network.IP_Manager;

public class ReetiControler {

	static DeviceClient deviceLink;


	static boolean connected = false;

	public static void main(String[] args) {



		String configuration_path = "./config/client_ReetiRobot.xml";		
		// OVERIDES CONFIGURATION PATH (IF NEW ONE GIVEN)
		if (args != null && args.length>0) configuration_path = args[0];

		Reeti_Interface ri = new Reeti_Interface();
		System.out.println("reeti client crated");
		
		while (true){

			boolean reached = ri.isReachableByPing();
			//System.out.println(reached);
			//ri.lookAtScreen();

			if (!reached && connected ){
				System.out.println("DISCONNECT REETI");
				deviceLink.disconnect();
				connected = false;
			}
			
			if (reached && !connected ){
				System.out.println("CONNECT REETI");
				if (deviceLink == null){
					DeviceSideRPC_Impl implementation = new DeviceSideRPC_Impl();
					deviceLink = DeviceClient.start(configuration_path,implementation);
					implementation.setDevice(deviceLink);
				}
				else
					deviceLink.try_connect();
				connected = true;
			}
			EGL_Utils.delay(100);
		}


	}

}
