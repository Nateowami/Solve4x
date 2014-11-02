/*
    Solve4x - An algebra solver that shows its work
    Copyright (C) 2014 Solve4x project

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.nateowami.solve4x.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.text.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.synth.SynthLookAndFeel;

import com.github.nateowami.solve4x.Main;
import com.github.nateowami.solve4x.solver.ParsingException;
import com.github.nateowami.solve4x.solver.Solver;
import com.github.nateowami.solve4x.visual.Visual;

/**
 * The GUI for this program
 * @author Tribex, Nateowami
 */
public class GUI {

    /**
     * Starts the GUI
     */
    public GUI(){
        UIManager.put("Slider.paintValue", false);

        //All this because of the reflection needed for the silly slider.
        try {
            startGUI();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static JTextField txtfEquationEntry;

    /**
     * Makes all the GUI widgets and puts the the GUI on-screen
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    private void startGUI() throws ClassNotFoundException, SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Class<?> sliderUIClass;
        sliderUIClass = Class.forName("javax.swing.plaf.synth.SynthSliderUI");
        final Field paintValue = sliderUIClass.getDeclaredField("paintValue");
        paintValue.setAccessible(true);


        //set the look and feel
        initLookAndFeel();
        //create the window
        JFrame MainFrame = new JFrame();
        //set the window icon
        MainFrame.setIconImage(Icon.getPlayIcon());
        //make a new GridBagLayout and add it to the window
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
        MainFrame.getContentPane().setLayout(gridBagLayout);

        //create a new JPanel for the top of the window and set all the layout stuff
        JPanel panelTop = new JPanel();
        GridBagConstraints gbc_panelTop = new GridBagConstraints();
        gbc_panelTop.insets = new Insets(2, 0, 0, -5);
        gbc_panelTop.fill = GridBagConstraints.HORIZONTAL;
        gbc_panelTop.gridx = 0;
        gbc_panelTop.gridy = 0;
        MainFrame.getContentPane().add(panelTop, gbc_panelTop);
        GridBagLayout gbl_panelTop = new GridBagLayout();
        gbl_panelTop.columnWidths = new int[]{0, 0, 0, 0, 0};
        gbl_panelTop.rowHeights = new int[]{0, 0, 0};
        gbl_panelTop.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_panelTop.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        panelTop.setLayout(gbl_panelTop);

        //create a JPanel for typing the equation and set the layout for it
        JPanel panelEquation = new JPanel();
        GridBagConstraints gbc_panelEquation = new GridBagConstraints();
        gbc_panelEquation.fill = GridBagConstraints.HORIZONTAL;
        gbc_panelEquation.insets = new Insets(0, 0, 5, 5);
        gbc_panelEquation.gridx = 0;
        gbc_panelEquation.gridy = 0;
        panelTop.add(panelEquation, gbc_panelEquation);

        panelEquation.setName("Panel.solve");
        GridBagLayout gbl_panelEquation = new GridBagLayout();
        gbl_panelEquation.columnWidths = new int[] {0};
        gbl_panelEquation.rowHeights = new int[]{25, 0};
        gbl_panelEquation.columnWeights = new double[]{1.0, 0.0};
        gbl_panelEquation.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        panelEquation.setLayout(gbl_panelEquation);

        //add a text field and set it's layout
        txtfEquationEntry = new JTextField();
        GridBagConstraints gbc_txtfEquationEntry = new GridBagConstraints();
        gbc_txtfEquationEntry.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtfEquationEntry.insets = new Insets(0, 0, 0, 5);
        gbc_txtfEquationEntry.gridx = 0;
        gbc_txtfEquationEntry.gridy = 0;
        panelEquation.add(txtfEquationEntry, gbc_txtfEquationEntry);
        txtfEquationEntry.setColumns(10);

        //make the "Solve" button
        JButton btnSolve = new JButton("Build Lesson");
        btnSolve.setToolTipText("Generate a Lesson from Your Equation");
        //set the action listener for the button
        btnSolve.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                //get the equation from the text field
                String equation = txtfEquationEntry.getText();

                //Check to see if the input was an equation or an expression
                try {
                    //run the solver
                    Solver solver = new Solver(equation, Solver.SolveFor.SOLVE);
                    //get the solution and display it to the user
                    Visual.render(solver.getSolution());
                    //notify the user of the equation evaluation status
                    txtfEquationEntry.setText("Equation Evaluation Status: "+ true);

                } catch (ParsingException err) {
                    //there must have been a problem with the equation the user entered
                    err.printStackTrace();
                    txtfEquationEntry.setText("ERROR: Malformed Entry.");
                }
            }
        });
        //set the layout for the solving button
        GridBagConstraints gbc_btnSolve = new GridBagConstraints();
        gbc_btnSolve.insets = new Insets(0, 0, 0, 5);
        gbc_btnSolve.anchor = GridBagConstraints.WEST;
        gbc_btnSolve.gridx = 1;
        gbc_btnSolve.gridy = 0;
        panelEquation.add(btnSolve, gbc_btnSolve);

        //make the settings button
        JButton btnConfigure = new JButton("⚙");
        btnConfigure.setToolTipText("Configure Solve4x");
        //set the action listener
        btnConfigure.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                //TODO do something when the settings button is clicked
            }
        });

        btnConfigure.setName("Button.configure");

        //set the layout and add the settings button to the JPanel
        GridBagConstraints gbc_btnNewButton_1_1 = new GridBagConstraints();
        gbc_btnNewButton_1_1.anchor = GridBagConstraints.EAST;
        gbc_btnNewButton_1_1.insets = new Insets(0, 0, 5, 5);
        gbc_btnNewButton_1_1.gridx = 1;
        gbc_btnNewButton_1_1.gridy = 0;
        panelTop.add(btnConfigure, gbc_btnNewButton_1_1);

        //create the help button
        JButton btnHelp = new JButton("?");
        btnHelp.setToolTipText("Solve4x Help");
        btnHelp.setName("Button.help");
        //set the layout for the help button
        GridBagConstraints gbc_btnNewButton_2_1_1 = new GridBagConstraints();
        gbc_btnNewButton_2_1_1.insets = new Insets(0, 0, 5, 5);
        gbc_btnNewButton_2_1_1.anchor = GridBagConstraints.EAST;
        gbc_btnNewButton_2_1_1.gridx = 2;
        gbc_btnNewButton_2_1_1.gridy = 0;
        panelTop.add(btnHelp, gbc_btnNewButton_2_1_1);

        //create the volume button
        final JButton btnVol = new JButton();
        btnVol.setToolTipText("Adjust the Volume");
        //set the image for the volume button
        btnVol.setIcon(new ImageIcon(Icon.getVolumeIcon(2)));
        //and set the layout
        GridBagConstraints gbc_btnVol = new GridBagConstraints();
        gbc_btnVol.insets = new Insets(0, 0, 5, 0);
        gbc_btnVol.gridx = 3;
        gbc_btnVol.gridy = 0;
        panelTop.add(btnVol, gbc_btnVol);
        //set the action listener for the volume button
        btnVol.addActionListener(new ActionListener() {
            Integer cnt = 0;
            public void actionPerformed(ActionEvent e)
            {
                switch (cnt) {

                //figure out the current state of the button and change the image on the button
                case 0:
                    btnVol.setIcon(new ImageIcon(Icon.getVolumeIcon(3)));
                    cnt = 1;
                    break;

                case 1:
                    btnVol.setIcon(new ImageIcon(Icon.getVolumeIcon(0)));
                    cnt = 2;
                    break;
                case 2:
                    btnVol.setIcon(new ImageIcon(Icon.getVolumeIcon(1)));
                    cnt = 3;
                    break;
                case 3:
                    btnVol.setIcon(new ImageIcon(Icon.getVolumeIcon(2)));
                    cnt = 0;
                    break;
                }
            }
        });

        //TODO:Subclass
        //create a JPanel for the main area where the solution is shown
        JPanel panelDrawing = new JPanel();
        panelDrawing.setName("Panel.draw");
        //set the layout
        GridBagConstraints gbc_panelDrawing = new GridBagConstraints();
        gbc_panelDrawing.insets = new Insets(-5, 3, 2, -2);
        gbc_panelDrawing.fill = GridBagConstraints.BOTH;
        gbc_panelDrawing.gridx = 0;
        gbc_panelDrawing.gridy = 1;
        MainFrame.getContentPane().add(panelDrawing, gbc_panelDrawing);

        //create the panel for the footer
        JPanel panelBottom = new JPanel();
        GridBagConstraints gbc_panelBottom = new GridBagConstraints();
        gbc_panelBottom.insets = new Insets(0, 0, -10, 0);
        gbc_panelBottom.fill = GridBagConstraints.BOTH;
        gbc_panelBottom.gridx = 0;
        gbc_panelBottom.gridy = 2;
        MainFrame.getContentPane().add(panelBottom, gbc_panelBottom);
        GridBagLayout gbl_panelBottom = new GridBagLayout();
        gbl_panelBottom.columnWidths = new int[]{0, 0, 0};
        gbl_panelBottom.rowHeights = new int[]{0, 0, 0};
        gbl_panelBottom.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        gbl_panelBottom.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
        panelBottom.setLayout(gbl_panelBottom);

        //create the JPanel for the play/pause button and the slider
        JPanel panelPlaybackControls = new JPanel();
        panelPlaybackControls.setName("Panel.playbackControls");
        //set the layout
        GridBagConstraints gbc_panelPlaybackControls = new GridBagConstraints();
        gbc_panelPlaybackControls.fill = GridBagConstraints.HORIZONTAL;
        gbc_panelPlaybackControls.insets = new Insets(0, 2, 5, 5);
        gbc_panelPlaybackControls.gridx = 0;
        gbc_panelPlaybackControls.gridy = 0;
        //add it to the footer
        panelBottom.add(panelPlaybackControls, gbc_panelPlaybackControls);
        //and work more with layouts
        GridBagLayout gbl_panelPlaybackControls = new GridBagLayout();
        gbl_panelPlaybackControls.rowHeights = new int[]{0, 0};
        gbl_panelPlaybackControls.columnWeights = new double[]{1.0};
        gbl_panelPlaybackControls.rowWeights = new double[]{1.0, Double.MIN_VALUE};
        panelPlaybackControls.setLayout(gbl_panelPlaybackControls);

        //create the panel that holds the play/pause button
        JPanel playbackControlButtons = new JPanel();
        GridBagConstraints gbc_playbackControlButtons = new GridBagConstraints();
        gbc_playbackControlButtons.fill = GridBagConstraints.BOTH;
        gbc_playbackControlButtons.gridx = 0;
        gbc_playbackControlButtons.gridy = 0;
        panelPlaybackControls.add(playbackControlButtons, gbc_playbackControlButtons);
        GridBagLayout gbl_playbackControlButtons = new GridBagLayout();
        gbl_playbackControlButtons.columnWidths = new int[]{66, 210, 0};
        gbl_playbackControlButtons.rowHeights = new int[]{42, 0};
        gbl_playbackControlButtons.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_playbackControlButtons.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        playbackControlButtons.setLayout(gbl_playbackControlButtons);

        //create the play/pause button
        final JButton btnPlay = new JButton();
        GridBagConstraints gbc_btnPlay = new GridBagConstraints();
        gbc_btnPlay.fill = GridBagConstraints.BOTH;
        gbc_btnPlay.insets = new Insets(0, 0, 0, 5);
        gbc_btnPlay.gridx = 0;
        gbc_btnPlay.gridy = 0;
        //add it to the playbackcontrols panel
        playbackControlButtons.add(btnPlay, gbc_btnPlay);
        //set the icon for the play/pause button
        btnPlay.setIcon(new ImageIcon(Icon.getPlayIcon()));

        //set the action listener for the play/pause button
        btnPlay.addActionListener(new ActionListener() {
            Boolean play = false;

            @Override
            public void actionPerformed(ActionEvent arg0) {
                //Toggle the icon
                if (play == false) {
                    btnPlay.setIcon(new ImageIcon(Icon.getPauseIcon()));
                    play = true;
                } else {
                    btnPlay.setIcon(new ImageIcon(Icon.getPlayIcon()));
                    play = false;
                }
            }

        });

        //create the JPanel to hold the slider
        JPanel panelSlider = new JPanel();
        panelSlider.setName("Panel.slider");

        //set the layout
        GridBagConstraints gbc_panelSlider = new GridBagConstraints();
        gbc_panelSlider.fill = GridBagConstraints.BOTH;
        gbc_panelSlider.gridx = 1;
        gbc_panelSlider.gridy = 0;
        playbackControlButtons.add(panelSlider, gbc_panelSlider);
        try {
        } catch (IllegalArgumentException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

                //create the JSlider
                JSlider slider = new JSlider();
                slider.setToolTipText("Lesson Timeline");
                paintValue.set(slider.getUI(), false);
                panelSlider.setLayout(new BorderLayout(0, 0));
                slider.setValue(0);
                slider.setMinimum(0);
                panelSlider.add(slider);

        //create the panel for holding buttons on the bottom right
        JPanel panelInfo = new JPanel();
        panelInfo.setName("Panel.info");
        //set the layout
        GridBagConstraints gbc_panelInfo = new GridBagConstraints();
        gbc_panelInfo.insets = new Insets(0, 0, 5, -5);
        gbc_panelInfo.fill = GridBagConstraints.BOTH;
        gbc_panelInfo.gridx = 1;
        gbc_panelInfo.gridy = 0;
        //add the panel it to the footer
        panelBottom.add(panelInfo, gbc_panelInfo);
        //and do more with layouts
        GridBagLayout gbl_panelInfo = new GridBagLayout();
        gbl_panelInfo.columnWidths = new int[]{0, 0, 0, 0, 0};
        gbl_panelInfo.rowHeights = new int[]{0, 0};
        gbl_panelInfo.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_panelInfo.rowWeights = new double[]{1.0, Double.MIN_VALUE};
        panelInfo.setLayout(gbl_panelInfo);

        //create the help button
        JButton btnHelp_Btm = new JButton("Help");
        btnHelp_Btm.setToolTipText("Solve4x Help");
        //set the layout
        GridBagConstraints gbc_btnHelp_Btm = new GridBagConstraints();
        gbc_btnHelp_Btm.fill = GridBagConstraints.VERTICAL;
        gbc_btnHelp_Btm.anchor = GridBagConstraints.EAST;
        gbc_btnHelp_Btm.insets = new Insets(0, 0, 0, 5);
        gbc_btnHelp_Btm.gridx = 0;
        gbc_btnHelp_Btm.gridy = 0;
        //and add it to the panel
        panelInfo.add(btnHelp_Btm, gbc_btnHelp_Btm);

        //create the update button
        JButton btnUpdate = new JButton("Update");
        btnUpdate.setToolTipText("Update Solve4x");
        //create the layout
        GridBagConstraints gbc_btnUpdate = new GridBagConstraints();
        gbc_btnUpdate.fill = GridBagConstraints.VERTICAL;
        gbc_btnUpdate.anchor = GridBagConstraints.EAST;
        gbc_btnUpdate.insets = new Insets(0, 0, 0, 5);
        gbc_btnUpdate.gridx = 1;
        gbc_btnUpdate.gridy = 0;
        //and add it to the info panel
        panelInfo.add(btnUpdate, gbc_btnUpdate);

        //create the website button
        JButton btnWebsite = new JButton("Website");
        btnWebsite.setToolTipText("Solve4x Website");
        //add a layout
        GridBagConstraints gbc_btnWebsite = new GridBagConstraints();
        gbc_btnWebsite.fill = GridBagConstraints.VERTICAL;
        gbc_btnWebsite.anchor = GridBagConstraints.EAST;
        gbc_btnWebsite.insets = new Insets(0, 0, 0, 5);
        gbc_btnWebsite.gridx = 2;
        gbc_btnWebsite.gridy = 0;
        //and add it to the info panel
        panelInfo.add(btnWebsite, gbc_btnWebsite);

        //create the about button
        JButton btnAbout = new JButton("About");
        btnAbout.setToolTipText("About Solve4x");
        //make a layout
        GridBagConstraints gbc_btnAbout = new GridBagConstraints();
        gbc_btnAbout.fill = GridBagConstraints.VERTICAL;
        gbc_btnAbout.anchor = GridBagConstraints.EAST;
        gbc_btnAbout.gridx = 3;
        gbc_btnAbout.gridy = 0;
        //add it to the info panel (which is inside the footer)
        panelInfo.add(btnAbout, gbc_btnAbout);

        //invoke the layout
        panelEquation.doLayout();
        panelTop.doLayout();
        MainFrame.doLayout();

        //do things with the size/location
        //set the size and location of the window
        MainFrame.setBounds(0, 0, 715, 500);
        //set the minimum size for the window
        MainFrame.setMinimumSize(new Dimension(600, 400));
        //centralize the window
        MainFrame.setLocationRelativeTo(null);

        //make the program end when the user clicks the X
        MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //set a background color
        MainFrame.setBackground(new Color(230, 230, 230));
        //set the title of the window
        MainFrame.setTitle("Solve4x - An Audio-Visual Algebra Solver");
        //make the window visible
        MainFrame.setVisible(true);

    }

    /**
     * Sets the look and feel to the Solve4x look and feel with Synths.
     * Loads the xml files that define the look and feel,
     */
    private static void initLookAndFeel() {
        SynthLookAndFeel lookAndFeel = new SynthLookAndFeel();

        // SynthLookAndFeel load() method throws a checked exception
        // (java.text.ParseException) so it must be handled
        try {
            lookAndFeel.load(Main.class.getResourceAsStream("/xml/defaults.xml"), Main.class);
            lookAndFeel.load(Main.class.getResourceAsStream("/xml/slider.xml"), Main.class);
            lookAndFeel.load(Main.class.getResourceAsStream("/xml/custom-panels.xml"), Main.class);
            lookAndFeel.load(Main.class.getResourceAsStream("/xml/custom-buttons.xml"), Main.class);

            UIManager.setLookAndFeel(lookAndFeel);
        }

        //if the look and feel can't be found/can't be used
        catch (ParseException e) {
            System.err.println("Couldn't get specified look and feel ("
                    + lookAndFeel
                    + "), for some reason.");
            System.err.println("Using the default look and feel.");
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            //print the error
            e.printStackTrace();
        }
    }
}



