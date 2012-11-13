package bluetooth;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

/**
 *
 * @author Ricardo
 */
public class DeviceConnection{
    /* Declare variables */
    private StreamConnection con = null;
    private String devAddress = null;
    private int port = 0;

    /* Constructor */
    public DeviceConnection(String devAddress, int port){
        this.devAddress = devAddress;
        this.port = port;
    }

    /* Methods */
    public boolean connect() {
        String connectionURL = "btspp://"+devAddress+":"+port+";authenticate=false;encrypt=false;master=false;";

        try {
            con = (StreamConnection) Connector.open(connectionURL, Connector.WRITE, false);

            return true;
        } catch (IOException ex) {
            Logger.getLogger(DeviceConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean disconnect(){
        try{
            if(con != null) con.close();

            return true;
        } catch(IOException ex){
            Logger.getLogger(DeviceConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean isConnected(){
        if(con != null)
            return true;

        return false;
    }

    public StreamConnection getConnection(){
        return con;
    }
}
