package bluetooth;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ricardo
 */
public class StreamSender implements Runnable{
    /* Declare variables */
    private DeviceConnection devCon;
    private LinkedBlockingQueue<String> commandQueue = new LinkedBlockingQueue<String>();
    public OutputStream strm_out = null;

    /* Constructor */
    public StreamSender(DeviceConnection deviceConnection){
        this.devCon = deviceConnection;
        
        Thread t = new Thread(this);
        t.start();
    }

    /* Methods */
    public void sendCommandToDevice(String command){
        try {
            //Send data to bot
            //System.out.println("Sending command to bot: " + command);
            //strm_out.write(command.getBytes().length);
            if(strm_out == null) strm_out = devCon.getConnection().openOutputStream();
            strm_out.write((command + "\n").getBytes());
        } catch (IOException ex) {
            Logger.getLogger(DeviceConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public OutputStream getStream(){
        try {
            if(strm_out == null)
                strm_out = devCon.getConnection().openOutputStream();
        } catch (IOException ex) {
            Logger.getLogger(DeviceConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return strm_out;
    }

    public void sendCommand(String command){
        commandQueue.add(command);
    }

    public synchronized void run() {
        while(true){
            while(devCon.isConnected()){
//                Iterator<String> iterator = commandQueue.iterator();
//                commandQueue.clear();

                while(!commandQueue.isEmpty()){
					try {
						sendCommandToDevice(commandQueue.take());
					} catch (InterruptedException ex) {
						Logger.getLogger(StreamSender.class.getName()).log(Level.SEVERE, null, ex);
					}
					
                    try {
                        this.wait(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(StreamSender.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                try {
                    this.wait(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(StreamSender.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
			System.out.println("Lost connection");
        }
    }
}
