package frontend;

import backend.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static frontend.MainInterface.frontend;


public class Play implements ActionListener {
        public JFileChooser browse;
        public Play(JFileChooser browse) {
            this.browse = browse;
            //Play class implements the Play actionListener from the MainInterface class

        }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (browse.getSelectedFile() != null) {
                    try {
                        Player.filepath = browse.getSelectedFile().getCanonicalPath();
                        //If valid file is selected from the FileChooser and path is found then the file is played
                    } catch (IOException iox) {
                        System.err.println("Canonical path not found.");
                        System.err.println(iox.getMessage());
                        System.exit(1);
                        //If selected file path cannot be found then file is not played and error message displayed
                    }
                }

                if (Player.filepath != null) {
                    frontend.setState(Frame.ICONIFIED);
                    ImageIcon img = new ImageIcon(System.getProperty("user.dir") + "\\icons\\play.png");
                    frontend.setIconImage(img.getImage());
                    Player.play();
                    //Play method is run and the window taskbar icon is reset to the play icon
                }

            }
        });
    }
}
