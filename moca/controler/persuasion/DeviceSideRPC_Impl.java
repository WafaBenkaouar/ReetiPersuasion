package moca.controler.persuasion;

import java.util.List;
import java.util.Vector;

import org.apache.thrift.TException;
import org.egl.net.ip_network.IP_Manager;

import moca.client.connection.DeviceClient;

import moca.thrift.generated_class.Device;
import moca.thrift.generated_class.DeviceSideRPC;
import moca.thrift.generated_class.DeviceStatus;
import moca.thrift.generated_class.Knowledge;
import moca.thrift.generated_class.KnowledgeFilter;
import moca.thrift.generated_class.Operation;
import moca.thrift.generated_class.OperationArgument;
import moca.thrift.generated_class.Service;

public class DeviceSideRPC_Impl implements DeviceSideRPC.Iface {

	Persuasion controler;

	DeviceClient device;

	public DeviceSideRPC_Impl(Persuasion controler) {

		this.controler = controler;
	}

	public void setDevice(DeviceClient d) {
		this.device = d;
	}

	@Override
	public List<Service> getServices() throws TException {
		return new Vector<Service>();
	}

	@Override
	public DeviceStatus getStatus() throws TException {
		return DeviceStatus.RUNNING;
	}

	@Override
	public boolean serverChange(String disconnected_ip, String new_ip,
			int new_port) throws TException {

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
						.isOneOfMyIPs(disconnected_ip)) // IM CONNECTED ON LOCAL
														// HOST AND THE IP IS
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
	public void deviceConnected(Device dev) throws TException {

		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>     Device connection notification : "
						+ dev);
		device.getDeviceOnNetwork().add(dev);

		for (Device d : device.getDeviceOnNetwork())
			System.out.println("Device remaining : " + d.dev_alias);

		controler.connected_devices_updated();

	}

	@Override
	public void deviceDisconnected(Device dev) throws TException {

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>     Device disconnection notification : "+ dev);

		for (int i = 0; i < device.getDeviceOnNetwork().size();) {

			System.out.println(dev.toString());
			System.out.println(device.getDeviceOnNetwork().get(i).toString());
			System.out.println("--------------------------------------------------------------------------");

			dev.available = false;
			if (device.getDeviceOnNetwork().get(i).equals(dev))
				device.getDeviceOnNetwork().remove(i);
			else {
				dev.available = true;
				if (device.getDeviceOnNetwork().get(i).equals(dev))
					device.getDeviceOnNetwork().remove(i);
				else
					i++;
			}
		}

		for (Device d : device.getDeviceOnNetwork())
			System.out.println("Device remaining : " + d.dev_alias);

		controler.connected_devices_updated();

	}

	@Override
	public String callService(Device contrl_dev, Service ser, Operation op,
			List<OperationArgument> serv_args) throws TException {
		return "Service Called succesfully";
	}

	@Override
	public List<Integer> getIcon() throws TException {
		return device.CONFIG.getIcon();
	}

	@Override
	public boolean isServiceExclusive(Service ser) throws TException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isServiceFree(Service ser) throws TException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean requestService(Device contrl_dev, Service ser)
			throws TException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean freeService(Device contrl_dev, Service ser)
			throws TException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void knowledgeUpdated(KnowledgeFilter filter, Knowledge newData)
			throws TException {
		// TODO Auto-generated method stub

	}

	@Override
	public void infoUpdated(Device origin, Knowledge newData) throws TException {
		// TODO Auto-generated method stub

	}

}
