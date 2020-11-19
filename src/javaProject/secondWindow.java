package javaProject;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.sql.*;
public class secondWindow {

	private JFrame frmLibraryMangementSystem;
	private JTable booksTable;
	private JTextField isbnTF;
	private JTextField authorTF;
	private JTextField titleTF;
	
	private DefaultTableModel dm;
	private String[] columnNames = {
			"ISBN", "Author", "Title", "BookOut", "User"
		};
	private Object[][] rows = {{" ", " ", " ", " ", " "}};
	private Connection con;
	private JTextField userNameTF;
	
	
	/**
	 * Launch the application.
	 */
	public static void newScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					secondWindow window = new secondWindow();
					window.frmLibraryMangementSystem.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws ClassNotFoundException 
	 */
	public secondWindow() throws ClassNotFoundException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws ClassNotFoundException 
	 */
	private void initialize() throws ClassNotFoundException {
		
		
		// Define connection
				Class.forName("com.mysql.cj.jdbc.Driver");
				try {
					con = DriverManager.getConnection("jdbc:mysql://35.192.93.245/library_books", "Firestar8", "123321");
					// contents
					Statement st = con.createStatement();
					
					String query = "Select * from books";
					ResultSet rs = st.executeQuery(query);
					
					rows = new Object[50][5];
					
					int numRows = 0;
					while (rs.next()) {
						for (int i = 0; i < 5; i++) {
							rows[numRows][i] = rs.getString(i+1);
						}
						numRows++;
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					System.out.print("Conneciton Failed");
					e1.printStackTrace();
				}
				
		
		
		frmLibraryMangementSystem = new JFrame();
		frmLibraryMangementSystem.getContentPane().setBackground(new Color(102, 51, 0));
		frmLibraryMangementSystem.setTitle("Library Mangement System (L.M.S)");
		frmLibraryMangementSystem.setBounds(100, 100, 825, 569);
		frmLibraryMangementSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLibraryMangementSystem.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 774, 319);
		frmLibraryMangementSystem.getContentPane().add(scrollPane);
		
		
		// dm
		booksTable = new JTable();
		scrollPane.setViewportView(booksTable);
		dm = new DefaultTableModel(rows, columnNames);
		booksTable.setModel(dm);
		
		
		booksTable.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		booksTable.setBackground(new Color(255, 228, 196));
		
		// search button
		JButton searchBT = new JButton("Search");
		searchBT.setFont(new Font("Times New Roman", Font.BOLD, 12));
		searchBT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Statement st;
				try {
					// if isbnTF is empty
					if (isbnTF.getText().equals("")) {
						// search for authors
						if (!authorTF.getText().equals("") && titleTF.getText().equals("")) {
							st = con.createStatement();
							String query = "Select * FROM books WHERE Author = " + "'"+authorTF.getText()+"';";
							ResultSet rs = st.executeQuery(query);
							int numRows = 0;
							rows = new Object[4][5];
							while (rs.next()) {
								for (int i = 0; i < 5; i++) {
									rows[numRows][i] = rs.getString(i+1);
								}
								++numRows;
							}
							
							Object[][] newArr = new Object[4][5];
							
							for (int i = 0; i < numRows; i++) {
								for (int j = 0; j < 5; j++) {
									newArr[i][j] = rows[i][j];
								}
							}
							rows = newArr;
							dm.setDataVector(rows, columnNames);
							dm.fireTableDataChanged();
							// for title search
						} else if (!titleTF.getText().equals("") && authorTF.getText().equals("")) { 
							st = con.createStatement();
							String query = "Select * FROM books WHERE Title = " + "'"+titleTF.getText()+"';";
							ResultSet rs = st.executeQuery(query);
							int numRows = 0;
							rows = new Object[1][5];
							while (rs.next()) {
								for (int i = 0; i < 5; i++) {
									//System.out.print(rs.getString(i+1) + ' ');
									rows[numRows][i] = rs.getString(i+1);
								}
								++numRows;
							}
							
							Object[][] newArr = new Object[1][5];
							
							for (int i = 0; i < numRows; i++) {
								for (int j = 0; j < 5; j++) {
									newArr[i][j] = rows[i][j];
								}
							}
							rows = newArr;
							dm.setDataVector(rows, columnNames);
							dm.fireTableDataChanged();
							
						} else {
							// search bar with nothing in it
							st = con.createStatement();

							String query = "Select * from books";
							ResultSet rs = st.executeQuery(query);

							rows = new Object[40][5];

							int numRows = 0;
							while (rs.next()) {
								for (int i = 0; i < 5; i++) {
									rows[numRows][i] = rs.getString(i+1);
								}
								numRows++;
							}

							Object[][] newArr = new Object[numRows][5];

							for (int i = 0; i < numRows; i++) {
								for (int j = 0; j < 5; j++) {
									newArr[i][j] = rows[i][j];
								}
							}
							rows = newArr;

							dm.setDataVector(rows, columnNames);
							dm.fireTableDataChanged();
						}
					} else {
						// search bar with isbn
						st = con.createStatement();

						String query = "Select * from books WHERE isbn = " + "'"+isbnTF.getText()+"';";
						ResultSet rs = st.executeQuery(query);
						
						rows = new Object[1][5];
						while (rs.next()) {
							for (int i = 0; i < 5; i++) {
								rows[0][i] = rs.getString(i+1);
							}
						}
						
						Object[][] newArr = new Object[1][5];
						
						for (int i = 0; i < 5; ++i ) {
							newArr[0][i] = rows[0][i];
						}
						rows = newArr;
						dm.setDataVector(rows, columnNames);
						dm.fireTableDataChanged();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		searchBT.setBackground(new Color(255, 228, 196));
		searchBT.setBounds(666, 357, 95, 23);
		frmLibraryMangementSystem.getContentPane().add(searchBT);
		
		JButton returnBT = new JButton("Return");
		returnBT.setFont(new Font("Times New Roman", Font.BOLD, 12));
		returnBT.setBackground(new Color(255, 228, 196));
		returnBT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!isbnTF.getText().equals("")) {
					try {
						Statement st = con.createStatement();
						Component frame;
						if (!userNameTF.getText().equals("") ) {
//							System.out.print("test");
							String query0 = "SELECT User from books WHERE isbn = " + "'"+isbnTF.getText()+"';";
							ResultSet rs = st.executeQuery(query0);
							String sqlInput = "";
							String userInput = userNameTF.getText();
							while (rs.next()) {
								sqlInput = rs.getString(1);
							}
							//System.out.print(sqlInput);
							//System.out.print(userNameTF.getText());
						if (userInput.equals(sqlInput)) {
								String query = "UPDATE books SET " + "BookOut"  + " = " + 
										"'1'WHERE isbn = " + "'"+isbnTF.getText()+"';";
										st.executeUpdate(query);
										
										String query2 = "UPDATE books SET " + "User"  + " = " + 
												"'" + "'"+ "WHERE isbn = " + "'"+isbnTF.getText()+"';";
										st.executeUpdate(query2);
										frame = null;
										JOptionPane.showMessageDialog(frame,
											    "Please hit the refresh button to see your changes.");
							} else {
								frame = null;
								JOptionPane.showMessageDialog(frame,
									    "Please input the correct isbn and username before returning a book.",
									    "Inane error",
									    JOptionPane.ERROR_MESSAGE);
							}
						
						
						
						dm.setDataVector(rows, columnNames);
						dm.fireTableDataChanged();
						
						} else {
							frame = null;
							JOptionPane.showMessageDialog(frame,
								    "Please input the correct isbn and username before returning a book.",
								    "Inane error",
								    JOptionPane.ERROR_MESSAGE);
						}
						dm.setDataVector(rows, columnNames);
						dm.fireTableDataChanged();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		returnBT.setBounds(666, 406, 95, 23);
		frmLibraryMangementSystem.getContentPane().add(returnBT);
		
		JButton checkOutBT = new JButton("Check Out");
		checkOutBT.setFont(new Font("Times New Roman", Font.BOLD, 12));
		checkOutBT.setBackground(new Color(255, 228, 196));
		checkOutBT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Component frame;
				if (!isbnTF.getText().equals("")) {
					try {
						Statement st = con.createStatement();
						if (!userNameTF.getText().equals("")) {
						//	if ()
						String query = "UPDATE books SET " + "BookOut"  + " = " + 
						"'0'WHERE isbn = " + "'"+isbnTF.getText()+"';";
						st.executeUpdate(query);
						
						String query2 = "UPDATE books SET " + "User"  + " = " + 
								"'" +userNameTF.getText()+"'"+ "WHERE isbn = " + "'"+isbnTF.getText()+"';";
						st.executeUpdate(query2);
						frame = null;
						JOptionPane.showMessageDialog(frame,
							    "Please hit the refresh button to see your changes.");
						
						dm.setDataVector(rows, columnNames);
						dm.fireTableDataChanged();
						} else {
							frame = null;
							JOptionPane.showMessageDialog(frame,
								    "Please input a username before checking out a book.",
								    "Inane error",
								    JOptionPane.ERROR_MESSAGE);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		checkOutBT.setBounds(666, 451, 95, 23);
		frmLibraryMangementSystem.getContentPane().add(checkOutBT);
		
		JLabel isbnLB = new JLabel("ISBN");
		isbnLB.setForeground(new Color(255, 228, 196));
		isbnLB.setFont(new Font("Times New Roman", Font.BOLD, 14));
		isbnLB.setBackground(new Color(255, 228, 196));
		isbnLB.setBounds(27, 357, 46, 23);
		frmLibraryMangementSystem.getContentPane().add(isbnLB);
		
		JLabel authorLB = new JLabel("AUTHOR");
		authorLB.setFont(new Font("Times New Roman", Font.BOLD, 14));
		authorLB.setForeground(new Color(255, 228, 196));
		authorLB.setBounds(27, 405, 63, 23);
		frmLibraryMangementSystem.getContentPane().add(authorLB);
		
		JLabel titleLB = new JLabel("TITLE");
		titleLB.setFont(new Font("Times New Roman", Font.BOLD, 14));
		titleLB.setForeground(new Color(255, 228, 196));
		titleLB.setBounds(27, 450, 46, 23);
		frmLibraryMangementSystem.getContentPane().add(titleLB);
		
		isbnTF = new JTextField();
		isbnTF.setBounds(111, 358, 135, 20);
		frmLibraryMangementSystem.getContentPane().add(isbnTF);
		isbnTF.setColumns(10);
		
		authorTF = new JTextField();
		authorTF.setBounds(111, 406, 135, 20);
		frmLibraryMangementSystem.getContentPane().add(authorTF);
		authorTF.setColumns(10);
		
		titleTF = new JTextField();
		titleTF.setBounds(111, 451, 135, 20);
		frmLibraryMangementSystem.getContentPane().add(titleTF);
		titleTF.setColumns(10);
		
		JButton refreshBT = new JButton("Refresh");
		refreshBT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Statement st;

				try {
					st = con.createStatement();

					String query = "Select * from books";
					ResultSet rs = st.executeQuery(query);

					rows = new Object[40][5];

					int numRows = 0;
					while (rs.next()) {
						for (int i = 0; i < 5; i++) {
							rows[numRows][i] = rs.getString(i+1);
						}
						numRows++;
					}

					Object[][] newArr = new Object[numRows][5];

					for (int i = 0; i < numRows; i++) {
						for (int j = 0; j < 5; j++) {
							newArr[i][j] = rows[i][j];
						}
					}
					rows = newArr;

					dm.setDataVector(rows, columnNames);
					dm.fireTableDataChanged();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});
		refreshBT.setFont(new Font("Times New Roman", Font.BOLD, 12));
		refreshBT.setBackground(new Color(255, 228, 196));
		refreshBT.setBounds(666, 496, 95, 23);
		frmLibraryMangementSystem.getContentPane().add(refreshBT);
		
		JLabel userLB = new JLabel("USERNAME");
		userLB.setForeground(new Color(255, 228, 196));
		userLB.setFont(new Font("Times New Roman", Font.BOLD, 14));
		userLB.setBounds(27, 495, 74, 23);
		frmLibraryMangementSystem.getContentPane().add(userLB);
		
		userNameTF = new JTextField();
		userNameTF.setBounds(111, 497, 135, 20);
		frmLibraryMangementSystem.getContentPane().add(userNameTF);
		userNameTF.setColumns(10);
	}
}
