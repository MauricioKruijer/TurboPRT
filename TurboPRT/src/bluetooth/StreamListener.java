package bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ricardo
 */
public class StreamListener implements Runnable{
    /* Declare variables */
    private DeviceConnection devCon;
    private BluetoothService btService;
    private InputStream strm_in = null;
    private int readByte = -1;
    public String readBytes = null;

    public StreamListener(BluetoothService btService, DeviceConnection devConnection){
        this.btService = btService;
        this.devCon = devConnection;

        Thread t = new Thread(this);
        t.start();
    }

    public synchronized void handelIncomingData(String readData){
        if(readData.equals("y")){
            System.out.println("bot communicated: y");
            notifyAll();
        }
        else if(readData.equals("n")){
            System.out.println("bot communicated: n");
            notifyAll();
        }
        else{
            //Bot sends the distances around him (Left, Front, Right, Behind)
            String[] distances = readData.split(",");
//            btService.guiController.getFrameMain().botStatusPane.setBotDistances(distances);
            System.out.print("bot communicated: ");
            for(int i = 0; i < distances.length; i++){
                System.out.print(distances[i]);
                if(i < distances.length -1)
                    System.out.print("\t ,");
            }
            notifyAll();
        }
    }

    public void run() {
        try {
            strm_in = devCon.getConnection().openInputStream();
        } catch (IOException ex) {
            Logger.getLogger(StreamListener.class.getName()).log(Level.SEVERE, null, ex);
        }

        while(true){
            try {
                readByte = -1;
                readBytes = null;

                while ((readByte = strm_in.read()) != -1) {
                    readBytes += readByte;
                }

                System.out.println("StreamListener: data received");
            } catch (IOException ex) {
                Logger.getLogger(StreamListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
