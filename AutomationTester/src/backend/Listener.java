package backend;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import static backend.Player.exit;

public class Listener implements NativeKeyListener {
    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
    }
    /* The method NativeKeyEvent.getKeyText(e.getKeyCode()) will return the key that was pressed.
    Where e is the NativeKeyEvent accepted by each of the overriden methods. */

    @Override
    public void nativeKeyReleased(final NativeKeyEvent e) {
        String key = NativeKeyEvent.getKeyText(e.getKeyCode());

        if (key.equals("Escape")) {
            exit = true;
            /* When the Escape key is pressed the recording or replaying
             is stopped and user interface is displayed. */
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
    }
    /* The method NativeKeyEvent.getKeyText(e.getKeyCode()) will return the key that was pressed.
    Where e is the NativeKeyEvent accepted by each of the overriden methods. */
}
//References
//http://www.iamkarthi.com/tutorial-jnativehook-control-native-mouse-and-keyboard-calls-outside-java/