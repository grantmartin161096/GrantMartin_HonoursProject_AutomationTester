package backend;

import frontend.MainInterface;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Player{
    public static boolean exit = false;
    public static boolean pause = false;
    public static String filepath = null;

    public static void play() throws IllegalArgumentException {
        try {
            BufferedReader in = new BufferedReader(new FileReader(filepath));
            Robot player = new Robot();
            //Constructs a Robot object in the coordinate system of the primary screen

            GlobalScreen.registerNativeHook();
            /* registers jnativehook and causes it to start listening
            to mouse events and keyboard events globally */
            Listener exitListen = new Listener();
            //listens for exiting recording
            GlobalScreen.addNativeKeyListener(exitListen);
            //Listens for added exit

            ImageIcon img = new ImageIcon(System.getProperty("user.dir") + "\\icons\\play.png");
            //gets the play image from directory
            MainInterface.frontend.setIconImage(img.getImage());
            //resetIcon gets the icons for the window taskbar icon for different statuses (play)

            String input = in.readLine();
            int x;
            int y;
            int button;
            int scroll;
            int keycode;
            int time;
            String[] split;
            /* Used to read the input from the user selected previously
             recorded test script output .txt file. */
            while(input != null && !Player.exit) {
                split = input.split(" ");
                System.err.println("[" + input + "]");
                if (split[0].equals("Move")) {
                    x = Integer.parseInt(split[1]);
                    y = Integer.parseInt(split[2]);
                    player.mouseMove(x, y);
                } else if (split[0].equals("MousePress")) {
                    button = Integer.parseInt(split[1]);
                    player.mousePress(InputEvent.getMaskForButton(button));
                } else if (split[0].equals("MouseRelease")) {
                    button = Integer.parseInt(split[1]);
                    player.mouseRelease(InputEvent.getMaskForButton(button));
                } else if (split[0].equals("Scroll")) {
                    scroll = Integer.parseInt(split[1]);
                    player.mouseWheel(scroll);
                } else if (split[0].equals("KeyPress")) {
                    keycode = Integer.parseInt(split[1]);
                    player.keyPress(keycode);
                } else if (split[0].equals("KeyRelease")) {
                    keycode = Integer.parseInt(split[1]);
                    player.keyRelease(keycode);
                } else if (split[0].equals("Wait")) {
                    time = Integer.parseInt(split[1]);
                    //handles the actions to be executed

                    while(time > 60000) {
                        time -= 60000;
                        player.delay(60000);
                        // delay has a max value of 60000, so we need to split it up.
                    }
                    player.delay(Math.abs(time));
                } else if (split[0].equals("Exit")) {
                    Player.exit = true;
                }
                input = in.readLine();
                //Used to read the input from the user
            }

            MainInterface.frontend.resetIcon();
            //resetIcon gets the icons for the window taskbar icon for different statuses
            MainInterface.frontend.setState(Frame.NORMAL);
            //Sets the normal frame designed in the MainInterface class

            GlobalScreen.removeNativeKeyListener(exitListen);
            GlobalScreen.unregisterNativeHook();
            Player.exit = false;
            in.close();
            //when input file events are complete file/test is closed and user interface displayed
        } catch (IOException iox) {
            System.err.println("Error input cannot be read from " + filepath + ".");
            System.err.println(iox.getMessage());
            //error handling

            System.exit(1);
        } catch (AWTException awtx) {
            System.err.println("Sorry, programme does not allow low-level input control.");
            System.err.println(awtx.getMessage());
            //error handling

            System.exit(1);
        } catch (NativeHookException nhx) {
            System.err.println("Error attempting to register the native hook.");
            System.err.println(nhx.getMessage());
            System.exit(1);
            //error handling
        }
    }

}
//References
//http://www.iamkarthi.com/tutorial-jnativehook-control-native-mouse-and-keyboard-calls-outside-java/