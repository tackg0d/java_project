package javaProject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Font;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;




public class libraryManagementSystem {
	
	private JFrame frmLibraryMangementSystem;
	private JTextField usernameTF;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					libraryManagementSystem window = new libraryManagementSystem();
					window.frmLibraryMangementSystem.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public libraryManagementSystem() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLibraryMangementSystem = new JFrame();
		frmLibraryMangementSystem.getContentPane().setBackground(new Color(0, 0, 128));
		frmLibraryMangementSystem.setTitle("Library Mangement System (L.M.S)");
		frmLibraryMangementSystem.setBounds(100, 100, 458, 295);
		frmLibraryMangementSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLibraryMangementSystem.getContentPane().setLayout(null);
		
		JLabel logInLB = new JLabel("LOG IN");
		logInLB.setBorder(null);
		logInLB.setFont(new Font("Times New Roman", Font.BOLD, 20));
		logInLB.setForeground(new Color(255, 0, 0));
		logInLB.setHorizontalAlignment(SwingConstants.CENTER);
		logInLB.setBackground(new Color(0, 0, 128));
		logInLB.setBounds(133, 50, 146, 35);
		frmLibraryMangementSystem.getContentPane().add(logInLB);
		
		usernameTF = new JTextField();
		usernameTF.setBounds(146, 113, 126, 20);
		frmLibraryMangementSystem.getContentPane().add(usernameTF);
		usernameTF.setColumns(10);
		
		JLabel usernameLB = new JLabel("USER NAME");
		usernameLB.setBorder(null);
		usernameLB.setBackground(new Color(0, 0, 128));
		usernameLB.setForeground(new Color(255, 0, 0));
		usernameLB.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		usernameLB.setBounds(47, 113, 89, 20);
		frmLibraryMangementSystem.getContentPane().add(usernameLB);
		
		JButton connectBT = new JButton("CONNECT");
		connectBT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String user = usernameTF.getText();
				Component frame = null;
				if (!usernameTF.getText().equals("")) {
					users myUser = new users(null);
					try {
						if (myUser.checkUser(user) == false) {
							myUser.writeToFile(user);
							JOptionPane.showMessageDialog(frame,
								    "Your username was not found in the database, you are now registered.");
							secondWindow.newScreen();
						} else {
							JOptionPane.showMessageDialog(frame,
								    "User name found. Have fun!");
							secondWindow.newScreen();
						}
						
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(frame,
						    "Error! No user name entered.",
						    "Inane error",
						    JOptionPane.ERROR_MESSAGE);
				}
				//secondWindow.newScreen();
			}
		});
		connectBT.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		connectBT.setBounds(158, 174, 103, 23);
		frmLibraryMangementSystem.getContentPane().add(connectBT);
	}
}
