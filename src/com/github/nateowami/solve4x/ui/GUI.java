/*
    Solve4x - An algebra solver that shows its work
    Copyright (C) 2015  Nathaniel Paulus

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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.synth.SynthLookAndFeel;

import com.github.nateowami.solve4x.Main;
import com.github.nateowami.solve4x.config.RoundingRule;
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
        } catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
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
     * @throws IOException 
     * @throws FontFormatException 
     */
    private void startGUI() throws ClassNotFoundException, SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, FontFormatException, IOException {
        Class<?> sliderUIClass;
        sliderUIClass = Class.forName("javax.swing.plaf.synth.SynthSliderUI");
        final Field paintValue = sliderUIClass.getDeclaredField("paintValue");
        paintValue.setAccessible(true);
        
        //set the look and feel
        initLookAndFeel();
        //create the window
        JFrame MainFrame = new JFrame();
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
        JButton btnSolve = new JButton("Answer");
        //set the action listener for the button
        btnSolve.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                //get the equation from the text field
                String equation = txtfEquationEntry.getText();

                //Check to see if the input was an equation or an expression
                try {
                    //run the solver
                    Solver solver = new Solver(equation, Solver.SolveFor.SOLVE, RoundingRule.FOR_SCIENTIFIC_NOTATION);
                    //get the solution and display it to the user
                    Visual.render(solver.getSolution());
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
        btnHelp.setName("Button.help");
        //set the layout for the help button
        GridBagConstraints gbc_btnNewButton_2_1_1 = new GridBagConstraints();
        gbc_btnNewButton_2_1_1.insets = new Insets(0, 0, 5, 5);
        gbc_btnNewButton_2_1_1.anchor = GridBagConstraints.EAST;
        gbc_btnNewButton_2_1_1.gridx = 2;
        gbc_btnNewButton_2_1_1.gridy = 0;
        panelTop.add(btnHelp, gbc_btnNewButton_2_1_1);
        
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
        MainFrame.setBackground(new Color(240, 240, 240));
        //set the title of the window
        MainFrame.setTitle("Solve4x");
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
