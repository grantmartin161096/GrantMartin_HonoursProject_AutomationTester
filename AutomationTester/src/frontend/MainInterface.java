package frontend;

import backend.Player;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainInterface extends Player {
    //MainInterface class extends methods from the Player class
    public static ApplicationTile frontend = null;
    //Before the run method is executed the frontend is not presented to end-user

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //The run method starts the Automation Tester application
                frontend = new ApplicationTile();
                //frontend calls the ApplicationTile method in the ApplicationTile class
                frontend.setVisible(true);
                //frontend interface is then presented to the end-user


                JPanel actions = new JPanel();
                //The JPanel is used to construct and manage the frontend user interface
                GridBagLayout gridbag = new GridBagLayout();
                GridBagConstraints c = new GridBagConstraints();
                //Gridbag is used to manage and control which columns and rows the frontend elements are displayed
                JLabel recordImage = new JLabel("", JLabel.CENTER);
                JLabel playImage = new JLabel("", JLabel.CENTER);
                JLabel logoImage = new JLabel("", JLabel.CENTER);
                JLabel name;
                JLabel status = new JLabel("STATUS: No Test Execution Started", JLabel.CENTER);
                JLabel helpRecord = new JLabel("Enter file name, before pressing Record, for example: example.txt", JLabel.CENTER);
                JLabel helpPlay = new JLabel("Select file from file chooser, before pressing PLAY", JLabel.CENTER);
                //The JLabel elements set and control the images and written content displayed on the frontend interface

                ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "\\icons\\record.png");
                Image img = icon.getImage();
                Image imgScale = img.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(imgScale);
                recordImage.setIcon(scaledIcon);
                //The record image is located from directory, given size dimensions and displayed

                ImageIcon icon1 = new ImageIcon(System.getProperty("user.dir") + "\\icons\\play.png");
                Image img1 = icon1.getImage();
                Image imgScale1 = img1.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon1 = new ImageIcon(imgScale1);
                playImage.setIcon(scaledIcon1);
                //The play image is located from directory, given size dimensions and displayed

                ImageIcon icon2 = new ImageIcon(System.getProperty("user.dir") + "\\icons\\logo.png");
                Image img2 = icon2.getImage();
                Image imgScale2 = img2.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon2 = new ImageIcon(imgScale2);
                logoImage.setIcon(scaledIcon2);
                //The logo image is located from directory, given size dimensions and displayed

                name = new JLabel("Automation Tester", JLabel.CENTER);
                name.setFont(new Font("Verdana", Font.BOLD, 16));
                status.setFont(new Font("Verdana", Font.BOLD, 16));
                helpRecord.setFont(new Font("Verdana", Font.BOLD, 12));
                helpPlay.setFont(new Font("Verdana", Font.BOLD, 12));
                //JLabel text font type, style and size are set

                JButton record = new JButton("RECORD STEPS");
                record.setSize(200, 100);
                JButton play = new JButton("PLAY TEST");
                play.setSize(200, 100);
                JButton about = new JButton("About");
                about.setSize(50, 50);
                //JButtons content and size are set

                JTextField filename = new JTextField("example.txt", 10);
                JFileChooser browse = new JFileChooser(System.getProperty("user.dir"));
                //FileChooser and file name input field are set
                browse.setMultiSelectionEnabled(true);
                // Allow multiple selection
                filename.setFont(new Font("Verdana", Font.BOLD, 12));
                //JTextField content and size are set

                browse.setDragEnabled(true);
                browse.setDialogTitle("Multiple file and directory selection:");
                browse.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                browse.setDialogTitle("Choose input file");
                browse.setControlButtonsAreShown(false);
                browse.setMultiSelectionEnabled(true);
                //FileChooser is set to allows multiple file selections, dragging of files

                play.addActionListener(new Play(browse));
                //Listener is set on the Play button
                record.addActionListener(new Record(filename, browse));
                //Listener is set on the Play button

                actions.setLayout(gridbag);
                actions.setLayout(gridbag);
                //layout is used to set out the order in which the JPanel elements are displayed on the page

                Border blackline = BorderFactory.createLineBorder(Color.black);
                //border colour is set

                c.weightx = 1;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 1;
                c.gridy = 1;
                actions.add(logoImage, c);
                c.gridy = 3;
                actions.add(about, c);
                c.gridy = 2;
                actions.add(name, c);
                c.gridy = 4;
                actions.add(recordImage, c);
                c.gridy = 5;
                actions.add(record, c);
                record.setForeground(Color.BLACK);
                record.setBackground(Color.RED);
                record.setBorder(blackline);
                c.gridy = 6;
                actions.add(helpRecord, c);
                c.gridy = 7;
                actions.add(filename, c);
                filename.setBorder(blackline);
                c.gridy = 8;
                actions.add(browse, c);
                browse.setBorder(blackline);
                c.gridy = 9;
                actions.add(playImage, c);
                c.gridy = 10;
                actions.add(helpPlay, c);
                c.gridy = 11;
                actions.add(play, c);
                play.setForeground(Color.BLACK);
                play.setBackground(Color.GREEN);
                play.setBorder(blackline);
                c.gridy = 12;
                actions.add(status, c);
                //gridy orders each JPanel element in the frontend interface

                actions.setBorder(blackline);
                actions.setBackground(Color.WHITE);

                frontend.add(actions);
                frontend.registerDelAction();
                // Create AbstractAction
                // It is an implementation of javax.swing.Action
                AbstractAction a=new AbstractAction(){

                    // Write the handler
                    public void actionPerformed(ActionEvent ae)
                    {
                        JFileChooser jf=(JFileChooser)ae.getSource();
                        try
                        {

                            // Get the selected files
                            File[] selectedFiles = jf.getSelectedFiles();

                            // If some file is selected
                            if(selectedFiles!=null)
                            {
                                // If user confirms to delete
                                if(askConfirm()==JOptionPane.YES_OPTION)
                                {

                                    // Call Files.delete(), if any problem occurs
                                    // the exception can be printed, it can be
                                    // analysed
                                    for(File f:selectedFiles)
                                        java.nio.file.Files.delete(f.toPath());

                                    // Rescan the directory after deletion
                                    jf.rescanCurrentDirectory();
                                }
                            }
                        }catch(Exception e){
                            System.out.println(e);
                        }
                    }
                };

                // Get action map and map, "delAction" with a
                browse.getActionMap().put("delAction",a);

                // Get input map when jf is in focused window and put a keystroke DELETE
                // associate the key stroke (DELETE) (here) with "delAction"
                browse.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DELETE"),"delAction");
                record.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            status.setText("STATUS: Test Recorded");
                        }
                });
                //When record button is clicked the status is updated to Test Recorded

                play.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        status.setText("STATUS: Test Executed");
                    }
                });
                //When play button is clicked the status is updated to Test Executed

                about.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(frontend, "This application allows users to Record and Play test cases on web browsers and emulators",
                                "About Appliction", JOptionPane.PLAIN_MESSAGE);
                    }
                });
                //When about button is clicked pop-up message is displayed explaining what the Automation Tester app does


            }
        });
    }

    private static int askConfirm() {
        // Ask the user whether he/she wants to confirm deleting
        // Return the option chosen by the user either YES/NO
        return JOptionPane.showConfirmDialog(frontend,"Are you sure want to delete this file?","Confirm",JOptionPane.YES_NO_OPTION);
    }


    public static void run() {
        MainInterface.run();
        //MainInterface is run
    }

}
//BorderLayout.CENTER
//https://javatutorial.net/java-swing-jframe-layouts#:~:text=Java%20SWING%20JFrame%20Layouts%20Example%201%20Explanation.%20Java,layout%20used%20by%20JPanel.%20...%20More%20items...%20
//https://www.youtube.com/watch?v=1q7VzBiEchk
//https://www.tutorialspoint.com/swingexamples/show_input_dialog_text.htm
//https://www.tutorialspoint.com/swingexamples/show_dialog_with_no_icon.htm
//https://www.geeksforgeeks.org/message-dialogs-java-gui/
//https://java-demos.blogspot.com/2013/07/how-to-delete-file-through-jfilechooser.html