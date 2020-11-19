package javaProject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class users extends libraryManagementSystem {
	
	String userName;
	
	public users(String user) {
		userName = user;
	}

	void writeToFile(String username) throws IOException {
		FileWriter fw = new FileWriter("users.txt", true);
		PrintWriter pw = new PrintWriter(fw);
	    pw.write(username + '\n');
	    
	    pw.close();
	}
	
	boolean checkUser(String username) throws IOException {
		Scanner fileScan = new Scanner (new File("users.txt"));
		  boolean found = false; // added this variable
		  while (fileScan.hasNextLine()) {
		    String input = fileScan.nextLine();
		    //String Username = input.substring(0,input.indexOf(' '));

		    if (input.equals(username)) {
		      found = true;
		    } 
		  }

		  return found;
	}
}
