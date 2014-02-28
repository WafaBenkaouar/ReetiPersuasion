package moca.controler.math_woz_devices;

import java.util.List;
import java.util.Vector;

import moca.client.connection.DeviceClient;
import moca.thrift.generated_class.Device;
import moca.thrift.generated_class.DeviceSideRPC;
import moca.thrift.generated_class.DeviceSideRPC.AsyncClient.getServices_call;
import moca.thrift.generated_class.Operation;
import moca.thrift.generated_class.OperationArgument;
import moca.thrift.generated_class.Service;
import moca.thrift.utils.ThriftUtils;

import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;

public class WozReeti implements WozDevice {

	Device ReetiDevice;
	Service ReetiSpeechService;
	Service ReetiUrbiService;
	Service ReetiGazeService;
	Operation ReetiSpeechOp;
	Operation ReetiUrbiOp;
	Operation ReetiGazeOp;

	DeviceClient deviceLink;

	@Override
	public void updateDeviceTarget(DeviceClient link) {

		deviceLink = link;

		System.out.println("UPDATING Reeti TARGET ... ");

		// already have an active SN device. Not changing anything
		if (ReetiDevice != null) {
			int i = 0;
			while (i < deviceLink.getDeviceOnNetwork().size()) {
				if (deviceLink.getDeviceOnNetwork().get(i).equals(ReetiDevice)) {
					System.out.println("   FOUND : " + deviceLink.getDeviceOnNetwork().get(i));
					System.out.println("   MATCH : " + ReetiDevice);
					return;
				}
				i++;
			}
		}

		System.out.println("   NEW Reeti TARGET REQUIRED ");

		// MARC device is null or was disconnected - Searching for a new one

		ReetiDevice = null;

		for (Device d : deviceLink.getDeviceOnNetwork())
			if (d.getDev_alias().equals("Reeti"))
				ReetiDevice = d;

		System.out.println("   SELECTED NEW Reeti INSTANCE : " + ReetiDevice);

		if (ReetiDevice != null) {

			try {
				ThriftUtils.call(ReetiDevice)
								.getServices(new AsyncMethodCallback<DeviceSideRPC.AsyncClient.getServices_call>() {

									@Override
									public void onError(Exception exception) {
										ReetiSpeechService = null;
										System.err.println("Error occured while retrieving MARC's service");

									}

									@Override
									public void onComplete(getServices_call response) {

										try {
											List<Service> services = response.getResult();

											for (Service s : services) {
												if (s.getName().startsWith("ReetiSpeech")) {
													ReetiSpeechService = s;
													for (Operation op : s.getOperations()) {
														if (op.getName().equals("Say"))
															ReetiSpeechOp = op;
													}
												}
												if (s.getName().startsWith("ReetiGaze")) {
													ReetiGazeService = s;
													for (Operation op : s.getOperations()) {
														if (op.getName().equals("LookAt"))
															ReetiGazeOp = op;
													}
												}
												if (s.getName().startsWith("ReetiUrbi")) {
													ReetiUrbiService = s;
													for (Operation op : s.getOperations()) {
														if (op.getName().equals("SendUrbi"))
															ReetiUrbiOp = op;
													}
												}
											}

										} catch (TException e) {
											e.printStackTrace();
										}

									}
								});
			} catch (TException e) {
				e.printStackTrace();
			}

		} else
			ReetiSpeechService = null;

	}

	@Override
	public Device getCurrentDevice() {
		return ReetiDevice;
	}

	/***** SPECIFIC METHODS ***************/

	public void say(String text) {

		if (ReetiDevice != null || ReetiSpeechService != null) {

			List<OperationArgument> args = new Vector<OperationArgument>();

			OperationArgument textArg = new OperationArgument("sentence", "string", text);
			args.add(textArg);

			try {
				ThriftUtils.call(ReetiDevice).callService(deviceLink.CONFIG.DEVICE,
								ReetiSpeechService, ReetiSpeechOp, args,
								ThriftUtils.DEFAULT_CALL_SERVICE_CALLBACK);
			} catch (TException e) {
				e.printStackTrace();
			}
		}

	}

	public void sendUrbiCommand(String text) {

		if (ReetiDevice != null || ReetiUrbiService != null) {

			List<OperationArgument> args = new Vector<OperationArgument>();
			// System.out.println("send Urbi command called");
			OperationArgument textArg = new OperationArgument("message", "string", text);
			args.add(textArg);

			try {
				ThriftUtils.call(ReetiDevice).callService(deviceLink.CONFIG.DEVICE,
								ReetiUrbiService, ReetiUrbiOp, args,
								ThriftUtils.DEFAULT_CALL_SERVICE_CALLBACK);
			} catch (TException e) {
				e.printStackTrace();
			}
		}

	}

	public void lookat_Screen() {

		if (ReetiDevice != null || ReetiGazeService != null) {

			List<OperationArgument> args = new Vector<OperationArgument>();

			OperationArgument textArg = new OperationArgument("target", "string", "screen");
			args.add(textArg);

			try {
				ThriftUtils.call(ReetiDevice).callService(deviceLink.CONFIG.DEVICE,
								ReetiGazeService, ReetiGazeOp, args,
								ThriftUtils.DEFAULT_CALL_SERVICE_CALLBACK);
			} catch (TException e) {
				e.printStackTrace();
			}
		}

	}

	public void lookat_User() {

		if (ReetiDevice != null || ReetiGazeService != null) {

			List<OperationArgument> args = new Vector<OperationArgument>();

			OperationArgument textArg = new OperationArgument("target", "string", "user");
			args.add(textArg);

			try {
				ThriftUtils.call(ReetiDevice).callService(deviceLink.CONFIG.DEVICE,
								ReetiGazeService, ReetiGazeOp, args,
								ThriftUtils.DEFAULT_CALL_SERVICE_CALLBACK);
			} catch (TException e) {
				e.printStackTrace();
			}
		}
	}

}
