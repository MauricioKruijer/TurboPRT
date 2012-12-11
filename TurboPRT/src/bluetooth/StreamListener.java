package bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import turboprt.Podcar;

/**
 *
 * @author Ricardo
 */
public class StreamListener implements Runnable{
    /* Declare variables */
	private Podcar device;
    private DeviceConnection devCon;
    private BluetoothService btService;
    private InputStream strm_in = null;
    private int readByte = -1;
    public String readBytes = null;

    public StreamListener(Podcar device){
		this.device = device;
        this.btService = device.btService;
        this.devCon = device.btService.devConnection;

        Thread t = new Thread(this);
        t.start();
    }

    public void run() {
        try {
			strm_in = devCon.getConnection().openInputStream();
        } catch (IOException ex) {
            Logger.getLogger(StreamListener.class.getName()).log(Level.SEVERE, null, ex);
        }

        while(true){
			int readLineCount = 0;
            try {
                char inChar ;
                int rcount = 0 ;
                char buffer[] = new char[34] ;

                if ( strm_in.available() > 0 ) {
                    rcount = 0 ;
                    while (true)  {
                        inChar = (char) strm_in.read() ;
                        if ( inChar == (char)27 ) { // Get in sync
                            rcount = 0 ; // Start over for esthetic reasons change "for" into "while"
                            continue;
                        } // Sync on ascii esc
                        if ( inChar == (char)10 || inChar == (char)13 ) {
                            if ( rcount == 0 ) continue; // Skip multiple NL/LF
                            else break ;
                        }
                        buffer[rcount++] = inChar;
                    }
                }
                else {
                    Thread.sleep(1000);
                    continue;
                }
				
				device.processCommand(String.valueOf(buffer).trim());
				
            } catch (InterruptedException ex) {
				device.disconnect();
				return;
//                ex.printStackTrace();
            } catch (IOException ex){
				device.disconnect();
				return;
                //ex.printStackTrace();
            }
        }
    }
}
