package moca.output.reeti_controler;

import java.util.List;
import java.util.Vector;

import moca.client.connection.DeviceClient;
import moca.thrift.generated_class.Device;
import moca.thrift.generated_class.DeviceSideRPC;
import moca.thrift.generated_class.DeviceStatus;
import moca.thrift.generated_class.Knowledge;
import moca.thrift.generated_class.KnowledgeFilter;
import moca.thrift.generated_class.Operation;
import moca.thrift.generated_class.OperationArgument;
import moca.thrift.generated_class.Service;
import moca.thrift.generated_class.ServiceType;

import org.apache.thrift.TException;
import org.egl.net.ip_network.IP_Manager;

public class DeviceSideRPC_Impl implements DeviceSideRPC.Iface {

	DeviceClient device;

	public DeviceSideRPC_Impl() {

	}

	public void setDevice(DeviceClient d) {
		this.device = d;
	}

	Vector<Service> myServices = new Vector<Service>();;

	@Override
	public List<Service> getServices() throws TException {

		if (myServices.size() == 0) {

			{
				Service SpeechService = new Service();
				SpeechService.name = "ReetiSpeech";
				SpeechService.type = ServiceType.GENERATION;
				SpeechService.exclusive_allowed = false;
				SpeechService.description = "Reeti Speech service";

				Operation say = new Operation();
				say.name = "Say";
				say.ouput_type = "string";
				say.description = "Starts a talk command on Reeti";
				say.addToInputs(new OperationArgument("sentence", "string", ""));

				SpeechService.addToOperations(say);

				myServices.add(SpeechService);
			}
			{
				Service GazeService = new Service();
				GazeService.name = "ReetiGaze";
				GazeService.type = ServiceType.GENERATION;
				GazeService.exclusive_allowed = false;
				GazeService.description = "Reeti Gaze service";

				Operation lookat = new Operation();
				lookat.name = "LookAt";
				lookat.ouput_type = "string";
				lookat.description = "Moves Reeti's head";
				lookat.addToInputs(new OperationArgument("target", "string", ""));

				GazeService.addToOperations(lookat);

				myServices.add(GazeService);
			}
			{
				Service ListPoseService = new Service();
				ListPoseService.name = "ReetiListPose";
				ListPoseService.type = ServiceType.GENERATION;
				ListPoseService.exclusive_allowed = false;
				ListPoseService.description = "Reeti List its Poses service";

				Operation listPoses = new Operation();
				listPoses.name = "ListPose";
				listPoses.ouput_type = "string";
				listPoses.description = "Lists all the poses on Reeti";
				listPoses.addToInputs(new OperationArgument("sentence", "string", ""));

				ListPoseService.addToOperations(listPoses);

				myServices.add(ListPoseService);
			}
			{
				Service UrbiService = new Service();
				UrbiService.name = "ReetiUrbi";
				UrbiService.type = ServiceType.GENERATION;
				UrbiService.exclusive_allowed = false;
				UrbiService.description = "Reeti Urbi service";

				Operation sendUrbi = new Operation();
				sendUrbi.name = "SendUrbi";
				sendUrbi.ouput_type = "string";
				sendUrbi.description = "Sends Urbi command to Reeti";
				sendUrbi.addToInputs(new OperationArgument("message", "string", ""));

				UrbiService.addToOperations(sendUrbi);

				myServices.add(UrbiService);
			}

		}

		return myServices;
	}

	@Override
	public DeviceStatus getStatus() throws TException {
		return DeviceStatus.RUNNING;
	}

	@Override
	public void deviceConnected(Device dev) throws TException {

	}

	@Override
	public void deviceDisconnected(Device dev) throws TException {

	}

	@Override
	public String callService(Device contrl_dev, Service ser, Operation op,
					List<OperationArgument> serv_args) throws TException {

		if (ser == null)
			return "I'm here";

		// System.out.println("service called");

		if (myServices.contains(ser)) {

			if (ser.getName().startsWith("ReetiSpeech") && op.getName().startsWith("Say")) {
				for (OperationArgument arg : serv_args) {
					if (arg.getName().equals("sentence")) {
						Reeti_Interface.say(arg.getValue());
						return "said";
					}
				}
			}

			if (ser.getName().startsWith("ReetiGaze") && op.getName().startsWith("LookAt")) {
				for (OperationArgument arg : serv_args) {
					if (arg.getName().equals("target") && arg.getValue().equals("user")) {
						Reeti_Interface.lookAtUser();
						return "moved";
					}
					if (arg.getName().equals("target") && arg.getValue().equals("screen")) {
						Reeti_Interface.lookAtScreen();
						return "moved";
					}
				}
			}
			if (ser.getName().startsWith("ReetiListPose") && op.getName().startsWith("ListPose")) {
				return Reeti_Interface.listPose().toString();

			}

			if (ser.getName().startsWith("ReetiUrbi") && op.getName().startsWith("SendUrbi")) {
				for (OperationArgument arg : serv_args) {
					if (arg.getName().equals("message")) {
						Reeti_Interface.sendUrbi(arg.getValue());
						return "sent";
					}
				}
			}
		}

		return "Unknown service called : " + ser.getName();
	}

	@Override
	public void knowledgeUpdated(KnowledgeFilter filter, Knowledge newData) throws TException {

	}

	/** NO EXLUSIVE SERVICE PROVIDED ****************************************************************/
	/** THE FOLLOWING METHODS ARE IGNORED **********************************************************/
	/**********************************************************************************************/

	@Override
	public boolean isServiceExclusive(Service ser) throws TException {
		return false;
	}

	@Override
	public boolean isServiceFree(Service ser) throws TException {
		return false;
	}

	@Override
	public boolean requestService(Device contrl_dev, Service ser) throws TException {
		return false;
	}

	@Override
	public boolean freeService(Device contrl_dev, Service ser) throws TException {
		return false;
	}

	@Override
	public boolean serverChange(String disconnected_ip, String new_ip, int new_port)
					throws TException {

		if (device.CONFIG.SERVER_THRIFT_IP == null // I DONT ALREADY HAVE A
													// SERVER
						|| device.CONFIG.SERVER_THRIFT_IP.equals(disconnected_ip) // MY
																					// SERVER
																					// IS
																					// THE
																					// ONE
																					// THAT
																					// GOT
																					// DISCONENCTED
						|| (device.CONFIG.SERVER_THRIFT_IP.equals("127.0.0.1") && IP_Manager
										.isOneOfMyIPs(disconnected_ip)) // IM
																		// CONNECTED
																		// ON
																		// LOCAL
																		// HOST
																		// AND
																		// THE
																		// IP IS
																		// MINE
		) {
			device.CONFIG.SERVER_THRIFT_IP = new_ip;
			device.CONFIG.SERVER_THRIFT_CLIENT_PORT = new_port;
			device.connectToThriftServer();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Integer> getIcon() throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void infoUpdated(Device origin, Knowledge newData) throws TException {
		// TODO Auto-generated method stub

	}

}
