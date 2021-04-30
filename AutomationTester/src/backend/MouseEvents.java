package backend;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

public class MouseEvents implements NativeMouseInputListener {

    private String filepath;

    public MouseEvents(String filepath){
        this.filepath = filepath;
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent e) {
    }
    /* The method NativeKeyEvent.getMouseEvent(e.getButton()) will return the position clicked on screen.
    Where e is the NativeKeyEvent accepted by each of the overriden methods. */

    @Override
    public void nativeMousePressed(NativeMouseEvent e) {
        try {
            long time = System.nanoTime();
            long prevTime = Recorder.getTime();
            Recorder.setTime(time);
            time = Math.abs(time - prevTime);
            int timeMs = (int) (time / Recorder.convertRate);
            //mouse event time and duration are calculated

            BufferedWriter out = new BufferedWriter(new FileWriter(filepath, true));

            int button = e.getButton();

            if (button==2) {
                button = 3;
            } else if (button==3) {
                button = 2;
            }

            out.write("MousePress " + button);
            out.newLine();
            out.write("Wait " + timeMs);
            out.newLine();
            out.close();
        }
        catch (IOException iox) {
            System.err.println("Error writing.");
            System.err.println(iox.getMessage());

            try {
                BufferedWriter out = new BufferedWriter(new FileWriter("a.txt", true));

                out.write("KeyRecorder error writing");
                out.newLine();
                out.close();
            } catch (IOException idox) {

            }

            System.exit(1);
        }
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent e) {
        /* The method NativeKeyEvent.getMouseEvent(e.getButton()) will return the position clicked on screen.
    Where e is the NativeKeyEvent accepted by each of the overriden methods. */
        try {
            long time = System.nanoTime();
            long prevTime = Recorder.getTime();
            Recorder.setTime(time);
            time = Math.abs(time - prevTime);
            int timeMs = (int) (time / Recorder.convertRate);
            //mouse event time and duration are calculated

            BufferedWriter out = new BufferedWriter(new FileWriter(filepath, true));
            //mouse events written to file

            int button = e.getButton();
            //get button

            if (button==2) {
                button = 3;
            } else if (button==3) {
                button = 2;
            }

            out.write("MouseRelease " + button);
            //mouse release is captured in output file.
            out.newLine();
            //new line
            out.write("Wait " + timeMs);
            //Wait is recorded and captured in output file
            out.newLine();
            //new line
            out.close();
            //file closr
        }
        catch (IOException iox) {
            System.err.println("Error writing.");
            System.err.println(iox.getMessage());
            //error handling

            try {
                BufferedWriter out = new BufferedWriter(new FileWriter("a.txt", true));

                out.write("KeyRecorder error writing");
                //error message is outputted to the file
                out.newLine();
                //new line
                out.close();
                //file close
            } catch (IOException idox) {

            }

            System.exit(1);
            //exit
        }
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent e) {
        // When mouse is pressed, nativeMouseMoved does not trigger. But this function does.
        nativeMouseMoved(e);
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent e) {
        try {
            long time = System.nanoTime();
            long prevTime = Recorder.getTime();
            Recorder.setTime(time);
            time = Math.abs(time - prevTime);
            int timeMs = (int) (time / Recorder.convertRate);

            BufferedWriter out = new BufferedWriter(new FileWriter(filepath, true));

            int x = e.getX();
            //get the mouse x coordinators
            int y = e.getY();
            //get the mouse y coordinators
            out.write("Move " + x + " " + y);
            //the x and y coordinates of the mouse are written to the output file
            out.newLine();
            //new line
            out.write("Wait " + timeMs);
            //Wait is recorded and captured in output file
            out.newLine();
            //new line
            out.close();
            //file close
        }
        catch (IOException iox) {
            System.err.println("Error writing.");
            System.err.println(iox.getMessage());
            //error handling

            try {
                BufferedWriter out = new BufferedWriter(new FileWriter("a.txt", true));

                out.write("KeyRecorder error writing");
                //error message is outputted to the file
                out.newLine();
                //new line
                out.close();
                //file close
            } catch (IOException idox) {

            }

            System.exit(1);
            //exit
        }
    }

}
//References
//http://www.iamkarthi.com/tutorial-jnativehook-control-native-mouse-and-keyboard-calls-outside-java/