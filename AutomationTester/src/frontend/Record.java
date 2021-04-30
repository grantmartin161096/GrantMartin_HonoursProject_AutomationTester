package frontend;

import backend.Recorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Record implements ActionListener {
    public JTextField file;
    public JFileChooser browse;
    public Record(JTextField file, JFileChooser browse) {
        this.file = file;
        this.browse = browse;
        //Record class implements the Record actionListener from the MainInterface class
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                String fileStr = file.getText();
                //The recorded user events are captured as Strings

                fileStr = fileStr.replace("\\", "");
                // Ignoring backslashes causing IO Exception.
                Recorder.file = fileStr;
                // Saving file to current browsing directory instead of just program root.
                try {
                    Recorder.root = browse.getCurrentDirectory().getCanonicalPath() + "\\";
                    //user events path are captured
                } catch (IOException iox) {
                    System.err.println("Canonical path not found.");
                    System.err.println(iox.getMessage());
                    System.exit(1);
                    //If user events path cannot be found then error message is displayed
                }

                System.out.println(Recorder.file);
                //User recorded events are outputted to a file

                if (Recorder.file != null) {
                    MainInterface.frontend.setState(Frame.ICONIFIED);
                    ImageIcon img = new ImageIcon(System.getProperty("user.dir") + "\\icons\\record.png");
                    MainInterface.frontend.setIconImage(img.getImage());
                    Recorder.record();
                    //Record method is run and the window taskbar icon is reset to the record icon
                }


            }
        });
    }

}
