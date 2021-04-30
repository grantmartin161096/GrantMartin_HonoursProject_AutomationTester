package backend;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class KeyEvents implements NativeKeyListener {
    private String filepath;
    private boolean skip = false;
    private boolean exit = false;

    public KeyEvents(String filepath){
        this.filepath = filepath;
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        /* The method NativeKeyEvent.getKeyText(e.getKeyCode()) will return the key that was pressed.
    Where e is the NativeKeyEvent accepted by each of the overriden methods. */
        try {
            long time = System.nanoTime();
            long prevTime = Recorder.getTime();
            Recorder.setTime(time);
            time = Math.abs(time - prevTime);
            int timeMs = (int) (time / Recorder.convertRate);
            //key event time and duration are calculated

            BufferedWriter out = new BufferedWriter(new FileWriter(filepath, true));
            //key events to file

            int keycode = e.getKeyCode();
            //get KeyCode
            String key = NativeKeyEvent.getKeyText(keycode);
            boolean leave = false;



            if (key.equals("Shift")) {
                keycode = 16;
            } else if (key.equals("Alt")) {
                keycode = 18;
            } else if (key.equals("Escape")) {
                skip = true;
            }


            if (!skip) {
                for (int i = 0 ; i < 16*16*16*16 && !leave ; i++) {
                    if (KeyEvent.getKeyText(i).equals(key)) {
                        keycode = i;
                        leave = true;
                        //If kep pressed is "Escape" then recording or playing of events is stopped
                    }
                }

                out.write("KeyPress " + keycode + " (" + key + ")");
                //Returns the key clicked, as a string.
                //key event is captured in output file.
                out.newLine();
                //new line
                out.write("Wait " + timeMs);
                //Wait is recorded and captured in output file
                out.newLine();
                //new line
                System.out.println(out);
            } else {
                skip = false;
            }
            out.close();
            //file closed
        }
        catch (IOException iox) {
            System.err.println("Error writing.");
            System.err.println(iox.getMessage());
            System.exit(1);
            //error handling

        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        /* The method NativeKeyReleased.getKeyText(e.getKeyCode()) will return the key that was released.
    Where e is the NativeKeyEvent accepted by each of the overriden methods. */

        try {
            long time = System.nanoTime();
            long prevTime = Recorder.getTime();
            Recorder.setTime(time);
            time = Math.abs(time - prevTime);
            int timeMs = (int) (time / Recorder.convertRate);
            //key event time and duration are calculated

            BufferedWriter out = new BufferedWriter(new FileWriter(filepath, true));
            //key events to file

            int keycode = e.getKeyCode();
            //get KeyCode
            String key = NativeKeyEvent.getKeyText(keycode);
            boolean leave = false;

            if (key.equals("Shift")) {
                keycode = 16;
            } else if (key.equals("Alt")) {
                keycode = 18;
            } else if (key.equals("Escape")) {
                out.write("Exit");
                out.newLine();
                exit = true;
            }

            if (!exit) {
                for (int i = 0 ; i < 16*16*16*16 && !leave ; i++) {
                    if (KeyEvent.getKeyText(i).equals(key)) {
                        keycode = i;
                        leave = true;
                        //If kep pressed is "Escape" then recording or playing of events is stopped
                    }
                }

                out.write("KeyRelease " + keycode + " (" + key + ")");
                //Returns the key clicked, as a string.
                //key event is captured in output file.
                out.newLine();
                //new line
                out.write("Wait " + timeMs);
                //Wait is recorded and captured in output file
                out.newLine();
                //new line
            } else {
                // Reset to false, so that if you record again it won't just stop after any random button is clicked
                exit = false;
                GlobalScreen.unregisterNativeHook();
            }

            out.close();
            //close
        }
        catch (IOException iox) {
            System.err.println("Error writing.");
            System.err.println(iox.getMessage());
            //error handling

            System.exit(1);
        } catch (NativeHookException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
    }
    /* The method NativeKeyTyped.getKeyText(e.getKeyCode()) will return the key that was typed.
    Where e is the NativeKeyEvent accepted by each of the overriden methods. */

}
//References
//http://www.iamkarthi.com/tutorial-jnativehook-control-native-mouse-and-keyboard-calls-outside-java/