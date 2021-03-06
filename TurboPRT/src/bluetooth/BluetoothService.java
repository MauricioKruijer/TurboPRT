package bluetooth;

/**
 *
 * @author Ricardo
 */
public class BluetoothService {
    /* Declare variables */
    public DeviceConnection devConnection;
    public StreamSender strmSender;
    public StreamListener strmListener;
	private boolean connected;

    public boolean connectToDevice(String address, int port){
        //Open connection
        devConnection = new DeviceConnection(address, port);
        if(!devConnection.connect())
            return false;

        //Open stream sender
        strmSender = new StreamSender(devConnection);

        this.connected = true;
        return true;
    }

    public void disconnect(){
        strmSender = null;
        strmListener = null;
        devConnection.disconnect();
        
        this.connected = false;
    }

    public void sendCommand(String command){
        if(this.connected)
            strmSender.sendCommand(command);
    }
}
