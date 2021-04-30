package frontend;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ApplicationTile extends JFrame implements WindowListener {
    //ApplicationTile extends the JFrame and implements the WindowListener
    //This class is used for Handling the Frame Window Events

    private static final long serialVersionUID = 1L;
    private static final String defaultImgPath = "\\icons\\logo.png";
    //The app logo is the default image displayed on the window taskbar icon


    public ApplicationTile() {
        setTitle("Automation Tester");
        setSize(700, 750);
        setResizable(true);
        setUndecorated(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        resetIcon();
        addWindowListener(this);
        //Setup the Frame Window
        //ApplicationTile method sets the title name, size of app window and handles the window taskbar icon

    }

    public void resetIcon() {
        ImageIcon img = new ImageIcon(System.getProperty("user.dir") + defaultImgPath);
        setIconImage(img.getImage());
        //resetIcon gets the icons for the window taskbar icon for different statuses
    }

    @Override
    public void windowActivated(WindowEvent arg0) {
    }
    //Handles the Frame Window Events for starting the window

    @Override
    public void windowClosed(WindowEvent arg0) {
        if (GlobalScreen.isNativeHookRegistered()) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException e) {
                e.printStackTrace();
            }
        }
        System.runFinalization();
        System.exit(0);
    }
    //Handles the Frame Window Events for closing the window

    @Override
    public void windowClosing(WindowEvent arg0) {
    }
    //Handles the Frame Window Events for closing the window

    @Override
    public void windowDeactivated(WindowEvent arg0) {
    }
    //Handles the Frame Window Events

    @Override
    public void windowDeiconified(WindowEvent arg0) {
    }
    //Handles the Frame Window Events

    @Override
    public void windowIconified(WindowEvent arg0) {
    }
    //Handles the Frame Window Events

    @Override
    public void windowOpened(WindowEvent arg0) {
    }
    //Handles the Frame Window Events

    public void registerDelAction() {
    }
    // Register the delete action
};


