package moca.controler.math_woz_devices;

import moca.client.connection.DeviceClient;
import moca.thrift.generated_class.Device;

public interface WozDevice {

	public void updateDeviceTarget(DeviceClient deviceLink);

	public Device getCurrentDevice();
	
}
