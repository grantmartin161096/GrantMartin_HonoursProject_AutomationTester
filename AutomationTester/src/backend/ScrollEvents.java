package backend;

import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;


public class ScrollEvents implements NativeMouseWheelListener {
    private String filepath;

    public ScrollEvents(String filepath){
        this.filepath = filepath;
    }

    @Override
    public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
        try {
            long time = System.nanoTime();
            long prevTime = Recorder.getTime();
            Recorder.setTime(time);
            time = Math.abs(time - prevTime);
            int timeMs = (int) (time / Recorder.convertRate);
            //scroll event time and duration are calculated

            BufferedWriter out = new BufferedWriter(new FileWriter(filepath, true));
            //writes scroll events to file

            int notch = e.getWheelRotation();
            //Returns the number of "clicks" the mouse wheel was rotated, as an integer.
            out.write("Scroll " + notch);
            //Scroll event is captured in output file
            out.newLine();
            //new line
            out.write("Wait " + timeMs);
            //Wait is recorded and captured in output file
            out.newLine();
            //new line
            out.close();
            //file closed
        }
        catch (IOException iox) {
            System.err.println("Error when writing to file.");
            System.err.println(iox.getMessage());
            System.exit(1);
            //error handling
        }
    }

}
//References
//http://www.iamkarthi.com/tutorial-jnativehook-control-native-mouse-and-keyboard-calls-outside-java/
