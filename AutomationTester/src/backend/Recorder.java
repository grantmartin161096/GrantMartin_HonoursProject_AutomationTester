package backend;

import frontend.MainInterface;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
/* jnativehook is an opensource native hook library for Java.
It functions as a Global Mouse and Keyboard Listener. */

import static java.awt.Frame.NORMAL;
import static java.lang.System.nanoTime;

public class Recorder {
    private static long time = 0;
    public static final int convertRate = 1000000;
    public static String root = System.getProperty("user.dir") + "\\";
    public static String file = null;

    public static void setTime(long time) {
        Recorder.time = time;
        //sets the time in milliseconds
    }

    public static long getTime() {
        return Recorder.time;
        //gets the recording time in milliseconds
    }

    public static void record() {
        try {
            GlobalScreen.registerNativeHook();
            /* registers jnativehook and causes it to start listening
            to mouse events and keyboard events globally */
        }
        catch (NativeHookException nhx) {
            System.err.println("Error attempting to register the native hook.");
            System.err.println(nhx.getMessage());
            //Error attempting to register the native hook
            System.exit(1);
        }
        System.out.println(root);


        time = nanoTime();
        KeyEvents keyListener = new KeyEvents(root + file);
        MouseEvents mouseListener = new MouseEvents(root + file);
        MouseEvents mouseMotionListener = new MouseEvents(root + file);
        ScrollEvents mouseWheelListener = new ScrollEvents(root + file);
        /* returns the current value of the system timer,
        in nanoseconds for each key, mouse, scroll events */

        GlobalScreen.addNativeKeyListener(keyListener);
        GlobalScreen.addNativeMouseListener(mouseListener);
        GlobalScreen.addNativeMouseMotionListener(mouseMotionListener);
        GlobalScreen.addNativeMouseWheelListener(mouseWheelListener);
        //Listens for added keyboard and mouse events

        while (GlobalScreen.isNativeHookRegistered()) {

        }

        GlobalScreen.removeNativeKeyListener(keyListener);
        GlobalScreen.removeNativeMouseListener(mouseListener);
        GlobalScreen.removeNativeMouseMotionListener(mouseMotionListener);
        GlobalScreen.removeNativeMouseWheelListener(mouseWheelListener);
        //Removes the listeners that are not needed

        MainInterface.frontend.resetIcon();
        //resetIcon gets the icons for the window taskbar icon for different statuses (Record)
        MainInterface.frontend.setState(NORMAL);
        //Sets the normal frame designed in the MainInterface class
    }

}
//References
//http://www.iamkarthi.com/tutorial-jnativehook-control-native-mouse-and-keyboard-calls-outside-java/