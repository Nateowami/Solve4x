/*
    Solve4x - A Java program to solve and explain algebra problems
    Copyright (C) 2013 Nathaniel Paulus

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
package com.github.nateowami.solve4x.av.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.MalformedInputException;
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
import com.github.nateowami.solve4x.solver.SolveFor;
import com.github.nateowami.solve4x.solver.Solver;
import java.awt.FlowLayout;
import java.awt.BorderLayout;


/**
 * The GUI for this program
 * @author Tribex, Nateowami
 */
public class GUI {
	
	/**
	 * Starts the GUI
	 */
	public GUI(){
		startGUI();
	}

	private static JTextField txtfEquationEntry;

	/**
	 * Makes all the GUI widgets and puts the the GUI on-screen
	 */
	private void startGUI() {
		
		initLookAndFeel();
		JFrame MainFrame = new JFrame();
		MainFrame.setIconImage(Icon.getPlayIcon());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		MainFrame.getContentPane().setLayout(gridBagLayout);

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

		txtfEquationEntry = new JTextField();
		GridBagConstraints gbc_txtfEquationEntry = new GridBagConstraints();
		gbc_txtfEquationEntry.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtfEquationEntry.insets = new Insets(0, 0, 0, 5);
		gbc_txtfEquationEntry.gridx = 0;
		gbc_txtfEquationEntry.gridy = 0;
		panelEquation.add(txtfEquationEntry, gbc_txtfEquationEntry);
		txtfEquationEntry.setColumns(10);

		JButton btnSolve = new JButton("Build Lesson");
		btnSolve.setToolTipText("Generate a Lesson from Your Equation");
		btnSolve.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String equation = txtfEquationEntry.getText();
				Boolean isValid;

				//Check to see if the input was an equation or an expression

				try {
					Solver solver = new Solver(equation, SolveFor.SOLVE);
					solver.solve();
				} catch (MalformedInputException err) {
					// TODO After GUI is done, make this do something useful
					err.printStackTrace();
					txtfEquationEntry.setText("ERROR: Malformed Entry.");
				}

				txtfEquationEntry.setText("Equation Evaluation Status: "+ true);
			}
		});
		GridBagConstraints gbc_btnSolve = new GridBagConstraints();
		gbc_btnSolve.insets = new Insets(0, 0, 0, 5);
		gbc_btnSolve.anchor = GridBagConstraints.WEST;
		gbc_btnSolve.gridx = 1;
		gbc_btnSolve.gridy = 0;
		panelEquation.add(btnSolve, gbc_btnSolve);

		JButton btnConfigure = new JButton("⚙");
		btnConfigure.setToolTipText("Configure Solve4x");
		btnConfigure.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});

		btnConfigure.setName("Button.configure");

		GridBagConstraints gbc_btnNewButton_1_1 = new GridBagConstraints();
		gbc_btnNewButton_1_1.anchor = GridBagConstraints.EAST;
		gbc_btnNewButton_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1_1.gridx = 1;
		gbc_btnNewButton_1_1.gridy = 0;
		panelTop.add(btnConfigure, gbc_btnNewButton_1_1);

		JButton btnHelp = new JButton("?");
		btnHelp.setToolTipText("Solve4x Help");
		btnHelp.setName("Button.help");
		GridBagConstraints gbc_btnNewButton_2_1_1 = new GridBagConstraints();
		gbc_btnNewButton_2_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_2_1_1.anchor = GridBagConstraints.EAST;
		gbc_btnNewButton_2_1_1.gridx = 2;
		gbc_btnNewButton_2_1_1.gridy = 0;
		panelTop.add(btnHelp, gbc_btnNewButton_2_1_1);

		final JButton btnVol = new JButton();
		btnVol.setToolTipText("Adjust the Volume");
		btnVol.setIcon(new ImageIcon(Main.class.getResource("/rsc/icons/icon-volume-medium.png")));
		GridBagConstraints gbc_btnVol = new GridBagConstraints();
		gbc_btnVol.insets = new Insets(0, 0, 5, 0);
		gbc_btnVol.gridx = 3;
		gbc_btnVol.gridy = 0;
		panelTop.add(btnVol, gbc_btnVol);
		btnVol.addActionListener(new ActionListener() {
			Integer cnt = 0;
			public void actionPerformed(ActionEvent e)
			{
				switch (cnt) {

				case 0: 
					btnVol.setIcon(new ImageIcon(Main.class.getResource("/rsc/icons/icon-volume-high.png")));
					cnt = 1;
					break;

				case 1: 
					btnVol.setIcon(new ImageIcon(Main.class.getResource("/rsc/icons/icon-volume-mute.png")));
					cnt = 2;
					break;
				case 2: 
					btnVol.setIcon(new ImageIcon(Main.class.getResource("/rsc/icons/icon-volume-low.png")));
					cnt = 3;
					break;
				case 3: 
					btnVol.setIcon(new ImageIcon(Main.class.getResource("/rsc/icons/icon-volume-medium.png")));
					cnt = 0;
					break;
				}
			}
		});      
		//TODO:Subclass
		JPanel panelDrawing = new JPanel();
		panelDrawing.setName("Panel.draw");
		GridBagConstraints gbc_panelDrawing = new GridBagConstraints();
		gbc_panelDrawing.insets = new Insets(-5, 3, 2, -2);
		gbc_panelDrawing.fill = GridBagConstraints.BOTH;
		gbc_panelDrawing.gridx = 0;
		gbc_panelDrawing.gridy = 1;
		MainFrame.getContentPane().add(panelDrawing, gbc_panelDrawing);

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

		JPanel panelPlaybackControls = new JPanel();
		panelPlaybackControls.setName("Panel.playbackControls");
		GridBagConstraints gbc_panelPlaybackControls = new GridBagConstraints();
		gbc_panelPlaybackControls.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelPlaybackControls.insets = new Insets(0, 2, 5, 5);
		gbc_panelPlaybackControls.gridx = 0;
		gbc_panelPlaybackControls.gridy = 0;
		panelBottom.add(panelPlaybackControls, gbc_panelPlaybackControls);
		GridBagLayout gbl_panelPlaybackControls = new GridBagLayout();
		gbl_panelPlaybackControls.rowHeights = new int[]{0, 0};
		gbl_panelPlaybackControls.columnWeights = new double[]{1.0};
		gbl_panelPlaybackControls.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelPlaybackControls.setLayout(gbl_panelPlaybackControls);

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

		final JButton btnPlay = new JButton();
		GridBagConstraints gbc_btnPlay = new GridBagConstraints();
		gbc_btnPlay.fill = GridBagConstraints.BOTH;
		gbc_btnPlay.insets = new Insets(0, 0, 0, 5);
		gbc_btnPlay.gridx = 0;
		gbc_btnPlay.gridy = 0;
		playbackControlButtons.add(btnPlay, gbc_btnPlay);
		btnPlay.setIcon(new ImageIcon(Icon.getPlayIcon()));
		
		//Switch between pause and play on click
		btnPlay.addActionListener(new ActionListener() {
			Boolean play = false;
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (play == false) {
					btnPlay.setIcon(new ImageIcon(Icon.getPauseIcon()));
					play = true;
				} else {
					btnPlay.setIcon(new ImageIcon(Icon.getPlayIcon()));
					play = false;
				}
			}
			
		});

		JPanel panelSlider = new JPanel();
		panelSlider.setName("Panel.slider");
		GridBagConstraints gbc_panelSlider = new GridBagConstraints();
		gbc_panelSlider.fill = GridBagConstraints.BOTH;
		gbc_panelSlider.gridx = 1;
		gbc_panelSlider.gridy = 0;
		playbackControlButtons.add(panelSlider, gbc_panelSlider);
		panelSlider.setLayout(new BorderLayout(0, 0));

		JSlider slider = new JSlider();
		panelSlider.add(slider);
		slider.setToolTipText("Lesson Timeline");
		slider.setValue(0);
		slider.setMinimum(1);
		slider.setFont(new Font("SansSerif", Font.BOLD, 14));

		JPanel panelInfo = new JPanel();
		panelInfo.setName("Panel.info");
		GridBagConstraints gbc_panelInfo = new GridBagConstraints();
		gbc_panelInfo.insets = new Insets(0, 0, 5, -5);
		gbc_panelInfo.fill = GridBagConstraints.BOTH;
		gbc_panelInfo.gridx = 1;
		gbc_panelInfo.gridy = 0;
		panelBottom.add(panelInfo, gbc_panelInfo);
		GridBagLayout gbl_panelInfo = new GridBagLayout();
		gbl_panelInfo.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panelInfo.rowHeights = new int[]{0, 0};
		gbl_panelInfo.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelInfo.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelInfo.setLayout(gbl_panelInfo);

		JButton btnHelp_Btm = new JButton("Help");
		btnHelp_Btm.setToolTipText("Solve4x Help");
		GridBagConstraints gbc_btnHelp_Btm = new GridBagConstraints();
		gbc_btnHelp_Btm.fill = GridBagConstraints.VERTICAL;
		gbc_btnHelp_Btm.anchor = GridBagConstraints.EAST;
		gbc_btnHelp_Btm.insets = new Insets(0, 0, 0, 5);
		gbc_btnHelp_Btm.gridx = 0;
		gbc_btnHelp_Btm.gridy = 0;
		panelInfo.add(btnHelp_Btm, gbc_btnHelp_Btm);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.setToolTipText("Update Solve4x");
		GridBagConstraints gbc_btnUpdate = new GridBagConstraints();
		gbc_btnUpdate.fill = GridBagConstraints.VERTICAL;
		gbc_btnUpdate.anchor = GridBagConstraints.EAST;
		gbc_btnUpdate.insets = new Insets(0, 0, 0, 5);
		gbc_btnUpdate.gridx = 1;
		gbc_btnUpdate.gridy = 0;
		panelInfo.add(btnUpdate, gbc_btnUpdate);

		JButton btnWebsite = new JButton("Website");
		btnWebsite.setToolTipText("Solve4x Website");
		GridBagConstraints gbc_btnWebsite = new GridBagConstraints();
		gbc_btnWebsite.fill = GridBagConstraints.VERTICAL;
		gbc_btnWebsite.anchor = GridBagConstraints.EAST;
		gbc_btnWebsite.insets = new Insets(0, 0, 0, 5);
		gbc_btnWebsite.gridx = 2;
		gbc_btnWebsite.gridy = 0;
		panelInfo.add(btnWebsite, gbc_btnWebsite);

		JButton btnAbout = new JButton("About");
		btnAbout.setToolTipText("About Solve4x");
		GridBagConstraints gbc_btnAbout = new GridBagConstraints();
		gbc_btnAbout.fill = GridBagConstraints.VERTICAL;
		gbc_btnAbout.anchor = GridBagConstraints.EAST;
		gbc_btnAbout.gridx = 3;
		gbc_btnAbout.gridy = 0;
		panelInfo.add(btnAbout, gbc_btnAbout);

		panelEquation.doLayout();
		panelTop.doLayout();

		MainFrame.doLayout();
		MainFrame.setBounds(0, 0, 715, 500);
		MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MainFrame.setBackground(new Color(230, 230, 230));
		MainFrame.setTitle("Solve4x - GUI Prototype");
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
			lookAndFeel.load(Main.class.getResourceAsStream("/rsc/xml/defaults.xml"), Main.class);
			lookAndFeel.load(Main.class.getResourceAsStream("/rsc/xml/slider.xml"), Main.class);
			lookAndFeel.load(Main.class.getResourceAsStream("/rsc/xml/custom-panels.xml"), Main.class);
			lookAndFeel.load(Main.class.getResourceAsStream("/rsc/xml/custom-buttons.xml"), Main.class);

			UIManager.setLookAndFeel(lookAndFeel);
		} 

		catch (ParseException e) {
			System.err.println("Couldn't get specified look and feel ("
					+ lookAndFeel
					+ "), for some reason.");
			System.err.println("Using the default look and feel.");
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}



