package com.dialectric.Database;

//imports

import com.opencsv.CSVWriter;
import org.mindrot.jbcrypt.BCrypt;

import java.io.*;
import java.sql.*;

//For loading .csv files
//For hashing

public class DBWrapper{

    private static int id = 0;
    public static String AccountType = "";

    public static void setAccountType(String val){
        AccountType = val;
    }

    public static int getID(){
	return id;
    }

    //Connection to database
    //Possibly put information into an XML file that the program would read to connect.
    //XML file could be updated to change location, rather than the java code
    public static Connection getConnection(String ip, String username, String password, String databaseName)
    {
	//chrome-orb-222916:us-west1:speech-database
	Connection connection = null;
	try{
	    Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + ip +"/" + databaseName + "?useSSL=false&allowLoadLocalInfile=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=PST8PDT", username, password);

	    Statement stmt = connection.createStatement();
	    String grantQuery = "GRANT SELECT ON " + databaseName + ".* TO " + username + ";";
	    boolean response = stmt.execute(grantQuery);
	}
	catch(SQLException e){
	    e.printStackTrace();
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
	return connection;
    }

    public static int CreateAccount(Connection con, String username, String password, String fname, String lname, String DatabaseUsername, String databaseName) throws IOException, SQLException
    {
	String query = null;
	Statement statement = con.createStatement();
	int rows;
	ResultSet resultSet;
	int prev_id;
	String revokeQuery;
	String grantQuery;
	boolean p;

	try{
	    switch(AccountType)
		{
		case "Researcher":
		    resultSet = statement.executeQuery("SELECT Researcher_ID FROM Researcher");
                    prev_id = 0;
                    while (resultSet.next()) {
                        prev_id = resultSet.getInt(1);
                    }
                    int Rid = prev_id + 1;
		    id = Rid;
		    System.out.println(id);

		    revokeQuery = "REVOKE SELECT ON " + databaseName + ".* FROM " + DatabaseUsername + ";";
                    p = statement.execute(revokeQuery);

		    grantQuery = "GRANT SELECT, INSERT ON " + databaseName + ".SubjectInfo TO " + DatabaseUsername + ";";
		    p = statement.execute(grantQuery);
		    grantQuery = "GRANT SELECT, INSERT ON " + databaseName + ".UserSession TO " + DatabaseUsername + ";";
                    p = statement.execute(grantQuery);
		    grantQuery = "GRANT SELECT, INSERT ON " + databaseName + ".Audio TO " + DatabaseUsername + ";";
                    p = statement.execute(grantQuery);
		    grantQuery = "GRANT INSERT ON " + databaseName + ".Researcher TO " + DatabaseUsername + ";";
		    p = statement.execute(grantQuery);

		    query = "INSERT INTO Researcher (Researcher_ID, First_Name, Last_Name, Username, RPassword) VALUES ("+id+", '" + fname + "', '" + lname + "', '"+ username + "', '" + password + "');";

		    //query table
		    rows = statement.executeUpdate(query);
		
		    break;
    
		case "Developer":
  		    resultSet = statement.executeQuery("SELECT Developer_ID FROM Developer");
		    prev_id = 0;
		    while (resultSet.next()) {
			prev_id = resultSet.getInt(1);
			System.out.println(prev_id);
		    }
		    int Did = prev_id + 1;
		    id = Did;

		    System.out.println(id);

		    revokeQuery = "REVOKE SELECT ON " + databaseName + ".* FROM " + DatabaseUsername + ";";
                    p = statement.execute(revokeQuery);

		    grantQuery = "GRANT INSERT, DELETE, UPDATE ON " + databaseName + ".* TO " + DatabaseUsername + ";";
                    p = statement.execute(grantQuery);

		    query = "INSERT INTO Developer (Developer_ID, First_Name, Last_Name, Username, DPassword) VALUES ("+id+", '" + fname + "', '" + lname + "', '"+ username + "', '" + password + "');";
		    //query table
		    
		    rows = statement.executeUpdate(query);                      
		    System.out.println(rows);
		    break;

		case "CareGiver":
		    resultSet = statement.executeQuery("SELECT CareGiver_ID FROM CareGiver");
                    prev_id = 0;
                    while (resultSet.next()) {
                        prev_id = resultSet.getInt(1);
                    }
                    int Cid = prev_id + 1;
		    		id = Cid;
                    System.out.println(id);
		    
		    		revokeQuery = "REVOKE SELECT ON " + databaseName + ".Researcher FROM " + DatabaseUsername + ";";
                    p = statement.execute(revokeQuery);
                    revokeQuery = "REVOKE SELECT ON " + databaseName + ".Developer FROM " + DatabaseUsername + ";";
                    p = statement.execute(revokeQuery);
                    /*revokeQuery = "REVOKE SELECT ON " + databaseName + ".Therapist FROM " + DatabaseUsername + ";";
                    p = statement.execute(revokeQuery);*/

		    		grantQuery = "GRANT INSERT ON " + databaseName + ".SubjectInfo TO " + DatabaseUsername + ";";
                    p = statement.execute(grantQuery);
                    grantQuery = "GRANT INSERT ON " + databaseName + ".UserSession TO " + DatabaseUsername + ";";
                    p = statement.execute(grantQuery);
                    grantQuery = "GRANT INSERT ON " + databaseName + ".Audio TO " + DatabaseUsername + ";";
                    p = statement.execute(grantQuery);
                    grantQuery = "GRANT INSERT ON " + databaseName + ".CareGiver TO " + DatabaseUsername + ";";
                    p = statement.execute(grantQuery);

		    query = "INSERT INTO CareGiver (CareGiver_ID, First_Name, Last_Name, Username, CPassword) VALUES ("+id+", '"  + fname + "', '" + lname + "', '"+ username + "', '" + password + "');";
		    //query table
		   
		    rows = statement.executeUpdate(query);
		    break;

		case "Therapist":
		    resultSet = statement.executeQuery("SELECT Therapist_ID FROM Therapist");
                    prev_id = 0;
                    while (resultSet.next()) {
                        prev_id = resultSet.getInt(1);
                    }
                    int Tid = prev_id + 1;
		    		id = Tid;
                    System.out.println(id);

		    revokeQuery = "REVOKE SELECT ON " + databaseName + ".Researcher FROM " + DatabaseUsername + ";";
                    p = statement.execute(revokeQuery);
                    revokeQuery = "REVOKE SELECT ON " + databaseName + ".Developer FROM " + DatabaseUsername + ";";
                    p = statement.execute(revokeQuery);
                    revokeQuery = "REVOKE SELECT ON " + databaseName + ".CareGiver FROM " + DatabaseUsername + ";";
                    p = statement.execute(revokeQuery);

                    grantQuery = "GRANT SELECT ON " + databaseName + ".SubjectInfo TO " + DatabaseUsername + ";";
                    p = statement.execute(grantQuery);
                    grantQuery = "GRANT SELECT ON " + databaseName + ".UserSession TO " + DatabaseUsername + ";";
                    p = statement.execute(grantQuery);
                    grantQuery = "GRANT SELECT ON " + databaseName + ".Audio TO " + DatabaseUsername + ";";
                    p = statement.execute(grantQuery);
		    		grantQuery = "GRANT SELECT ON " + databaseName + ".Therapy TO " + DatabaseUsername + ";";
                    p = statement.execute(grantQuery);
                    grantQuery = "GRANT INSERT ON " + databaseName + ".Therapist TO " + DatabaseUsername + ";";
                    p = statement.execute(grantQuery);

		    query = "INSERT INTO Therapist (Therapist_ID, First_Name, Last_Name, Username, TPassword) VALUES ("+id+", '" +fname + "', '" + lname + "', '"+ username + "', '" + password + "');";
		      
		    //query table
		    rows = statement.executeUpdate(query);
		    break;                                     
		}
	    System.out.println(query);
	    System.out.println("Created!");
	}
	catch(SQLException e) {
	    e.printStackTrace();
	    return -1;
        } finally {
            if (statement != null) { statement.close(); }
        }
	return 1;
    }

    public static int AddSubjectInfo(Connection con, String birthday, String sex, String race, String nationality, String speechAbility, 
				     String diagnosis, String therapistFName, String therapistLName, int CG_id) throws SQLException
    {
		Statement statement = con.createStatement();
		ResultSet resultSet;
		int rows;

		try{
			resultSet = statement.executeQuery("SELECT Subject_ID FROM SubjectInfo");
			int prev_id = 0;
			int T_id = 0;
			while (resultSet.next()) {
				prev_id = resultSet.getInt(1);
			}
			int id = prev_id + 1;

			String therapistQuery = "SELECT Therapist_ID FROM Therapist WHERE First_Name = '" + therapistFName + "' AND Last_Name = '" + therapistLName + "'";
			ResultSet rs = statement.executeQuery(therapistQuery);

			while(rs.next()) {
				T_id = rs.getInt("Therapist_ID");
			}

			String query = "INSERT INTO SubjectInfo (Subject_ID, Birthday, Sex, Race, Nationality, Speech_Ability, Diagnosis, Therapist_ID, CareGiver_ID) VALUES ("+id+", '" + birthday + "', '" + sex + "', '" + race + "', '" + nationality + "', '" + speechAbility + "', '" + diagnosis + "', "+T_id+", "+CG_id+");";
			//query table
			rows = statement.executeUpdate(query);
		} catch (SQLException e ) {
				e.printStackTrace();
				return -1;
			} finally {
				if (statement != null) { statement.close(); }
			}
		return id;
    }

    // finds an account and returns the account type or "none" if no match
    public static String FindAccount(Connection con, String username, String password, String DatabaseUsername, String databaseName) throws SQLException 
    {
	//could ask for type of account and that would be quicker with less code.	
	Statement statement = null;
	String query1 = "SELECT Username, CPassword FROM CareGiver;";
	String query2 = "SELECT Username, RPassword FROM Researcher;";
	String query3 = "SELECT Username, TPassword FROM Therapist;";
	String query4 = "SELECT Username, DPassword FROM Developer;";
	
	int foundFlag = 0;
	String check;
	String check_p;
	ResultSet rs;
	String revokeQuery;
	String grantQuery;
	boolean p;
	String salt;
	String new_hash;

	try {
	    statement = con.createStatement();
	    rs = statement.executeQuery(query1);
	    while (rs.next()) {
		check =rs.getString(1);
		if((check.compareTo(username)) == 0){
		    //check password here			
		    check_p = rs.getString(2);

		    salt = check_p.substring(0,29);                  //Get the salt of the stored password
		    new_hash = BCrypt.hashpw(password, salt);        //Hash the entered password with the salt for the account
		    
		    if((check_p.compareTo(new_hash)) == 0){
			revokeQuery = "REVOKE SELECT ON " + databaseName + ".Researcher FROM " + DatabaseUsername + ";";
			p = statement.execute(revokeQuery);
			revokeQuery = "REVOKE SELECT ON " + databaseName + ".Developer FROM " + DatabaseUsername + ";";
			p = statement.execute(revokeQuery);
			revokeQuery = "REVOKE SELECT ON " + databaseName + ".Therapist FROM " + DatabaseUsername + ";";
			p = statement.execute(revokeQuery);
			revokeQuery = "REVOKE SELECT ON " + databaseName + ".CareGiver FROM " + DatabaseUsername + ";";
			p = statement.execute(revokeQuery);
			
			grantQuery = "GRANT INSERT ON " + databaseName + ".SubjectInfo TO " + DatabaseUsername + ";";
			p = statement.execute(grantQuery);
			grantQuery = "GRANT INSERT ON " + databaseName + ".UserSession TO " + DatabaseUsername + ";";
			p = statement.execute(grantQuery);
			grantQuery = "GRANT INSERT ON " + databaseName + ".Audio TO " + DatabaseUsername + ";";
			p = statement.execute(grantQuery);
			
			return "CareGiver";
			
		    }
		}
	    }
	    
	    statement = con.createStatement();
	    rs = statement.executeQuery(query2);
	    while (rs.next()) {
		check =rs.getString(1);
		if((check.compareTo(username)) == 0){
		    //check password here
		    check_p = rs.getString(2);

		    salt = check_p.substring(0,29);                  //Get the salt of the stored password 
		    new_hash = BCrypt.hashpw(password, salt);        //Hash the entered password with the salt for the account 
		    
		    if((check_p.compareTo(new_hash)) == 0){
			revokeQuery = "REVOKE SELECT ON " + databaseName + ".Researcher FROM " + DatabaseUsername + ";";
			p = statement.execute(revokeQuery);
			revokeQuery = "REVOKE SELECT ON " + databaseName + ".Developer FROM " + DatabaseUsername + ";";
			p = statement.execute(revokeQuery);
			revokeQuery = "REVOKE SELECT ON " + databaseName + ".Therapist FROM " + DatabaseUsername + ";";
			p = statement.execute(revokeQuery);
			revokeQuery = "REVOKE SELECT ON " + databaseName + ".CareGiver FROM " + DatabaseUsername + ";";
			p = statement.execute(revokeQuery);
			
			grantQuery = "GRANT SELECT, INSERT ON " + databaseName + ".SubjectInfo TO " + DatabaseUsername + ";";
			p = statement.execute(grantQuery);
			grantQuery = "GRANT SELECT, INSERT ON " + databaseName + ".UserSession TO " + DatabaseUsername + ";";
			p = statement.execute(grantQuery);
			grantQuery = "GRANT SELECT, INSERT ON " + databaseName + ".Audio TO " + DatabaseUsername + ";";
			p = statement.execute(grantQuery);
			
			return "Researcher";
		    
		    }
		}
	    }
	    
	    statement = con.createStatement();
	    rs = statement.executeQuery(query3);
	    while (rs.next()) {
		check =rs.getString(1);
		if((check.compareTo(username)) == 0){
		    //check password here
		    check_p = rs.getString(2);

		    salt = check_p.substring(0,29);                  //Get the salt of the stored password
		    new_hash = BCrypt.hashpw(password, salt);        //Hash the entered password with the salt for the account
		    
		    if((check_p.compareTo(new_hash)) == 0){
			revokeQuery = "REVOKE SELECT ON " + databaseName + ".Researcher FROM " + DatabaseUsername + ";";
			p = statement.execute(revokeQuery);
			revokeQuery = "REVOKE SELECT ON " + databaseName + ".Developer FROM " + DatabaseUsername + ";";
			p = statement.execute(revokeQuery);
			revokeQuery = "REVOKE SELECT ON " + databaseName + ".Therapist FROM " + DatabaseUsername + ";";
			p = statement.execute(revokeQuery);
			revokeQuery = "REVOKE SELECT ON " + databaseName + ".CareGiver FROM " + DatabaseUsername + ";";
			p = statement.execute(revokeQuery);
			
			grantQuery = "GRANT SELECT ON " + databaseName + ".SubjectInfo TO " + DatabaseUsername + ";";
			p = statement.execute(grantQuery);
			grantQuery = "GRANT SELECT ON " + databaseName + ".UserSession TO " + DatabaseUsername + ";";
			p = statement.execute(grantQuery);
			grantQuery = "GRANT SELECT ON " + databaseName + ".Audio TO " + DatabaseUsername + ";";
			p = statement.execute(grantQuery);
			grantQuery = "GRANT SELECT ON " + databaseName + ".Therapy TO " + DatabaseUsername + ";";
			p = statement.execute(grantQuery);
			
			return "Therapist";
			
		    }
		}
	    }
	    
	    statement = con.createStatement();
	    rs = statement.executeQuery(query4);
	    while (rs.next()) {
		check =rs.getString(1);
		if((check.compareTo(username)) == 0){
		    //check password here
		    check_p = rs.getString(2);

		    salt = check_p.substring(0,29);                  //Get the salt of the stored password
		    new_hash = BCrypt.hashpw(password, salt);        //Hash the entered password with the salt for the account
		    
		    if((check_p.compareTo(new_hash)) == 0){
			revokeQuery = "REVOKE SELECT ON " + databaseName + ".* FROM " + DatabaseUsername + ";";
			p = statement.execute(revokeQuery);
			
			grantQuery = "GRANT INSERT, DELETE, UPDATE ON " + databaseName + ".* TO " + DatabaseUsername + ";";
			p = statement.execute(grantQuery);
			
			return "Developer";
		    
		    }
		}
	    }
	    
	} catch (SQLException e ) {
      	    e.printStackTrace();
	} finally {
	    if (statement != null) { statement.close(); }
	}

	return "none";
    }

    // looks up the user's password based on username for authentication purposes
    public static String ReturnUserPass(Connection con, String username) throws SQLException  {
		Statement statement = null;
		String query1 = "SELECT Username, CPassword FROM CareGiver;";
		String query2 = "SELECT Username, RPassword FROM Researcher;";
		String query3 = "SELECT Username, TPassword FROM Therapist;";
		String query4 = "SELECT Username, DPassword FROM Developer;";
		String password;
		String check;
		ResultSet rs;

		try {
			statement = con.createStatement();

			// check caregiver usernames
			rs = statement.executeQuery(query1);
			// loop through the rows
			while (rs.next()) {
				// check the username for a match
				check = rs.getString(1);
				if ((check.compareTo(username)) == 0) {
					// grab raw password
					password = rs.getString(2);
					return password;
				}
			}

			// check researcher usernames
			rs = statement.executeQuery(query2);
			// loop through the rows
			while (rs.next()) {
				// check the username for a match
				check = rs.getString(1);
				if ((check.compareTo(username)) == 0) {
					// grab raw password
					password = rs.getString(2);
					return password;
				}
			}

			// check therapist usernames
			rs = statement.executeQuery(query3);
			// loop through the rows
			while (rs.next()) {
				// check the username for a match
				check = rs.getString(1);
				if ((check.compareTo(username)) == 0) {
					// grab raw password
					password = rs.getString(2);
					return password;
				}
			}

			// check developer usernames
			rs = statement.executeQuery(query4);
			// loop through the rows
			while (rs.next()) {
				// check the username for a match
				check = rs.getString(1);
				if ((check.compareTo(username)) == 0) {
					// grab raw password
					password = rs.getString(2);
					return password;
				}
			}

		} catch (SQLException e ) {
			e.printStackTrace();
		} finally {
			if (statement != null) { statement.close(); }
		}

		// if not found
		return null;
	}
    
    public static int UpdateSubject(Connection con, String Element, String updatedInfo, String DatabaseUsername, String databaseName) throws SQLException
    {
	//Update subject table
	Statement stmt = null;
	boolean p;
        String revokeQuery = "REVOKE UPDATE ON " + databaseName + ".SubjectInfo FROM " + DatabaseUsername + ";";
        String grantQuery = "GRANT UPDATE ON " + databaseName + ".SubjectInfo TO " + DatabaseUsername + ";";

	//Can update own info
	try{
	    if(AccountType.equals("CareGiver"))
		{
		    stmt = con.createStatement();
                    p = stmt.execute(grantQuery);
		    String query = "UPDATE Subject SET " + Element + " = '" + updatedInfo + "' WHERE Subject_ID =  "+id+";";
		    
		    int value = stmt.executeUpdate(query);
		    p = stmt.execute(revokeQuery);
		    return value;
		}
	} catch (SQLException e ) {
	    return -1;
	} finally {
	    if (stmt != null) { stmt.close(); }
	}
	return 0;
    }

    public static int UpdateTherapy(Connection con, String updatedInfo, String DatabaseUsername, String databaseName) throws SQLException
    {
        //Update subject table
        Statement stmt = null;
	boolean p;
        String revokeQuery = "REVOKE UPDATE ON " + databaseName + ".Therapy FROM " + DatabaseUsername + ";";
        String grantQuery = "GRANT UPDATE ON " + databaseName + ".Therapy TO " + DatabaseUsername + ";";

        //Can update own info
	try{
	    if(AccountType.equals("CareGiver"))
		{
		    stmt = con.createStatement();
                    p = stmt.execute(grantQuery);

		    String idquery = "SELECT Subject_ID FROM Subject WHERE CareGiver_ID = "+id+";";
		    
		    ResultSet rs = stmt. executeQuery(idquery);
		    int sub_id = 0;
		    
		    while(rs.next())
			{
			    sub_id = rs.getInt(1);
			}
		    
		    String query = "UPDATE Therapy SET Patient_ID = " + updatedInfo + " WHERE Subject_ID = "+sub_id+";";
		    int value = stmt.executeUpdate(query);
		    p = stmt.execute(revokeQuery);
		    return value;
		}
	    else if(AccountType.equals("Therapist"))
		{
		    stmt = con.createStatement();
                    p = stmt.execute(grantQuery);

		    String query = "UPDATE Therapy SET Patient_ID = " + updatedInfo + " WHERE Subject_ID = "+id+";";
		    
		    int value = stmt.executeUpdate(query);
		    p = stmt.execute(revokeQuery);
		    return value;
		}
	} catch (SQLException e ) {
            return -1;
        } finally {
            if (stmt != null) { stmt.close(); }
        }
	return 0;
    }


    public static int UpdateTherapist(Connection con, String Element, String updatedInfo, String KnownElement, String fn, String ln, String DatabaseUsername, String databaseName) throws SQLException
    {
	//Update therapist table
	Statement stmt = null;
        int check = -1;
	boolean p;
        String revokeQuery = "REVOKE UPDATE ON " + databaseName + ".Therapist FROM " + DatabaseUsername + ";";
        String grantQuery = "GRANT UPDATE ON " + databaseName + ".Therapist TO " + DatabaseUsername + ";";

        //Can update own info
	try{
	    if(AccountType.equals("Therapist"))
		{
		    stmt = con.createStatement();
		    p = stmt.execute(grantQuery);
		    if(Element.equals("password"))
			{
			    updatedInfo = hashPassword(updatedInfo);
			
			    String pquery = "UPDATE Therapist SET " + Element + " = '" + updatedInfo + 
				"' WHERE Username = '" + KnownElement + "' AND First_Name = '" + fn + "' AND Last_Name = '" + ln + "';";
			    
			    int pvalue = stmt.executeUpdate(pquery);
			    p = stmt.execute(revokeQuery);
			    return pvalue;
			}
		    else if(Element.equals("username"))
			{
			    KnownElement = hashPassword(KnownElement);

			    String uquery = "UPDATE Therapist SET " + Element + " = '" + updatedInfo + 
				"' WHERE TPassword = '" + KnownElement + "' AND First_Name = '" + fn + "' AND Last_Name = '" + ln + "';";
			    
			    int uvalue = stmt.executeUpdate(uquery);
			    p = stmt.execute(revokeQuery);
			    return uvalue;
			}
		}
	} catch (SQLException e ) {
            return check;
        } finally {
            if (stmt != null) { stmt.close(); }
        }
	return check;
    }

    public static int UpdateDeveloper(Connection con, String Element, String updatedInfo, String KnownElement, String fn, String ln, String DatabaseUsername, String databaseName) throws SQLException
    {
	//upate developer table
	Statement stmt = null;
	boolean p;
	String revokeQuery = "REVOKE UPDATE ON " + databaseName + ".Developer FROM " + DatabaseUsername + ";";
        String grantQuery = "GRANT UPDATE ON " + databaseName + ".Developer TO " + DatabaseUsername + ";";

        try{
	    if(AccountType.equals("Developer"))
		{
		    stmt = con.createStatement();
		    p = stmt.execute(grantQuery);
		    if(Element.equals("password"))
			{
			    updatedInfo = hashPassword(updatedInfo);
			
			    String pquery = "UPDATE Developer SET " + Element + " = '" + updatedInfo +
                                "' WHERE Username = '" + KnownElement + "' AND First_Name = '" + fn + "' AND Last_Name = '" + ln + "';";
		       
			    int pvalue = stmt.executeUpdate(pquery);
			    p = stmt.execute(revokeQuery);
			    return pvalue;
			}
		    else if(Element.equals("username"))
			{
			    KnownElement = hashPassword(KnownElement);

                            String uquery = "UPDATE Developer SET " + Element + " = '" + updatedInfo +
                                "' WHERE DPassword = '" + KnownElement + "' AND First_Name = '" + fn + "' AND Last_Name = '" + ln + "';";
                            
                            int uvalue = stmt.executeUpdate(uquery);
			    p = stmt.execute(revokeQuery);
                            return uvalue;
			}
		}
	} catch (SQLException e ) {
            return -1;
        } finally {
            if (stmt != null) { stmt.close(); }
        }
	return 0;
    }

    public static int UpdateCareGiver(Connection con, String Element, String updatedInfo, String KnownElement, String fn, String ln, String DatabaseUsername, String databaseName) throws SQLException
    {
	//update CareGiver table
	Statement stmt = null;
	boolean p;
	String revokeQuery = "REVOKE UPDATE ON " + databaseName + ".CareGiver FROM " + DatabaseUsername + ";";
	String grantQuery = "GRANT UPDATE ON " + databaseName + ".CareGiver TO " + DatabaseUsername + ";";

        try{
	    if(AccountType.equals("CareGiver"))
		{
		    stmt = con.createStatement();
		    p = stmt.execute(grantQuery);

		    if(Element.equals("password"))
                        {
                            updatedInfo = hashPassword(updatedInfo);
                        
			    String cquery = "UPDATE CareGiver SET " + Element + " = '" + updatedInfo +
                                "' WHERE Username = '" + KnownElement + "' AND First_Name = '" + fn + "' AND Last_Name = '" + ln + "';";
			    
			    int cvalue = stmt.executeUpdate(cquery);
			    p = stmt.execute(revokeQuery);
			    return cvalue;
			}
		    else if(Element.equals("username"))
			{
			    KnownElement = hashPassword(KnownElement);

                            String uquery = "UPDATE CareGiver SET " + Element + " = '" + updatedInfo +
                                "' WHERE CPassword = '" + KnownElement + "' AND First_Name = '" + fn + "' AND Last_Name = '" + ln + "';";
                            
                            int uvalue = stmt.executeUpdate(uquery);
			    p = stmt.execute(revokeQuery);
                            return uvalue;
			}
		}
	} catch (SQLException e ) {
            return -1;
        } finally {
            if (stmt != null) { stmt.close(); }
        }
	return 0;
    }

    public static int UpdateResearcher(Connection con,String Element,String updatedInfo, String KnownElement, String fn, String ln, String DatabaseUsername, String databaseName) throws SQLException
    {
	//update Researcher table
	Statement stmt = null;
        int check = -1;
	boolean p;
	String revokeQuery = "REVOKE UPDATE ON " + databaseName + ".Researcher FROM " + DatabaseUsername + ";";
        String grantQuery = "GRANT UPDATE ON " + databaseName + ".Researcher TO " + DatabaseUsername + ";";

	try{        
	    if(AccountType.equals("Researcher"))
		{
		    p = stmt.execute(grantQuery);
		    if(Element.equals("password"))
                        {
                            updatedInfo = hashPassword(updatedInfo);
                        
			    String rquery = "UPDATE Researcher SET " + Element + " = '" + updatedInfo +
                                "' WHERE Username = '" + KnownElement + "' AND First_Name = '" + fn + "' AND Last_Name = '" + ln + "';";
			    stmt = con.createStatement();
			    int rvalue = stmt.executeUpdate(rquery);

			    p = stmt.execute(revokeQuery);    
			    return rvalue;

			}
		    else if(Element.equals("username"))
                        {
                            KnownElement = hashPassword(KnownElement);

                            String uquery = "UPDATE Researcher SET " + Element + " = '" + updatedInfo +
                                "' WHERE RPassword = '" + KnownElement + "' AND First_Name = '" + fn + "' AND Last_Name = '" + ln + "';";
                            stmt = con.createStatement();
                            int uvalue = stmt.executeUpdate(uquery);

			    p = stmt.execute(revokeQuery);
                            return uvalue;
                        }
		}
	} catch (SQLException e ) {
            e.printStackTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }
	return check;
    }

    public static int DeleteAccount(Connection con, String AccountType, String username, String password, String DatabaseUsername, String databaseName) throws SQLException
    {
	Statement stmt = null;
	String revokeQuery = "REVOKE DELETE ON " + databaseName + "." + AccountType + " FROM " + DatabaseUsername + ";";
        String grantQuery = "GRANT DELETE ON " + databaseName + "." + AccountType + " TO " + DatabaseUsername + ";";
	boolean p;
	int response = 0;
        try{
            stmt = con.createStatement();
	    p = stmt.execute(grantQuery);
	    String query = "DELETE FROM " + AccountType + "WHERE Username = '" + username + "';";;
            response = stmt.executeUpdate(query);
	    p = stmt.execute(revokeQuery);
        } catch (SQLException e ) {
            return -1;
        } finally{
            if (stmt != null) { stmt.close(); }
        }
        return response;
    }

    public static int UpdateQuery(Connection con, String query) throws SQLException
    {
	Statement stmt = null;
	int response = 0;
	try{
	    stmt = con.createStatement();
	    response = stmt.executeUpdate(query);
	} catch (SQLException e ) {
            return -1;
        } finally{
	    if (stmt != null) { stmt.close(); }
	}
	return response;
    }

    public static ResultSet SelectQuery(Connection con, String query) throws SQLException
    {
	Statement stmt = null;
	ResultSet response = null;
        try{
            stmt = con.createStatement();
            response = stmt.executeQuery(query);
        } catch (SQLException e ) {
            return null;
        } finally{
            if (stmt != null) { stmt.close(); }
        }
        return response;
    }

    public static int closeConnection(Connection connection)  throws SQLException
    {
        if (connection != null) {
            try {
                connection.close();
		return 0;
            } catch (SQLException e) {
                return -1;
            }
        }
	return -1;
    }

    // hash the password and return it using Bcrypt
    public static String hashPassword(String password_plaintext) {

    	// defines how many cycles the encryption performs
		int workload = 12;

		String salt = BCrypt.gensalt(workload);
		String hashed_password = BCrypt.hashpw(password_plaintext, salt);
		System.out.println(hashed_password);
		return(hashed_password);
    }

    //Functions needed for porting below

    public static void CSVCreate(Connection connection_old) throws SQLException, FileNotFoundException, IOException
    {
	//for porting purposes
	//create a CSV file from all data

	//user would provide or create CSV file under current folder                                                                                                                                    
	Boolean includeHeaders = true;
	Statement stmt = null;                                                                                                                              
	ResultSet myResultSet;

	try {
	    stmt = connection_old.createStatement();
	    CSVWriter Dwriter = new CSVWriter(new FileWriter("DevTable.csv"), ',', CSVWriter.NO_QUOTE_CHARACTER);
	    myResultSet = stmt.executeQuery("SELECT * FROM Developer");
	    Dwriter.writeAll(myResultSet, includeHeaders);
	    Dwriter.close();

	    CSVWriter Rwriter = new CSVWriter(new FileWriter("ResTable.csv"), ',', CSVWriter.NO_QUOTE_CHARACTER);
	    myResultSet = stmt.executeQuery("SELECT * FROM Researcher");
            Rwriter.writeAll(myResultSet, includeHeaders);
	    Rwriter.close();

	    CSVWriter Twriter = new CSVWriter(new FileWriter("TheTable.csv"), ',', CSVWriter.NO_QUOTE_CHARACTER);
	    myResultSet = stmt.executeQuery("SELECT * FROM Therapist");
            Twriter.writeAll(myResultSet, includeHeaders);
	    Twriter.close();

	    CSVWriter Cwriter = new CSVWriter(new FileWriter("CGTable.csv"), ',', CSVWriter.NO_QUOTE_CHARACTER);
	    myResultSet = stmt.executeQuery("SELECT * FROM CareGiver");
            Cwriter.writeAll(myResultSet, includeHeaders);
	    Cwriter.close();

	    CSVWriter Swriter = new CSVWriter(new FileWriter("SubTable.csv"), ',', CSVWriter.NO_QUOTE_CHARACTER);
            myResultSet = stmt.executeQuery("SELECT * FROM SubjectInfo");
            Swriter.writeAll(myResultSet, includeHeaders);
            Swriter.close();

	    CSVWriter Awriter = new CSVWriter(new FileWriter("AudTable.csv"), ',', CSVWriter.NO_QUOTE_CHARACTER);
	    myResultSet = stmt.executeQuery("SELECT * FROM Audio");
            Awriter.writeAll(myResultSet, includeHeaders);
	    Awriter.close();

	    CSVWriter Recwriter = new CSVWriter(new FileWriter("SessTable.csv"), ',', CSVWriter.NO_QUOTE_CHARACTER);
	    myResultSet = stmt.executeQuery("SELECT * FROM UserSession");
            Recwriter.writeAll(myResultSet, includeHeaders);
	    Recwriter.close();

	    CSVWriter Thwriter = new CSVWriter(new FileWriter("TherapyTable.csv"), ',', CSVWriter.NO_QUOTE_CHARACTER);
	    myResultSet = stmt.executeQuery("SELECT * FROM Therapy");
            Thwriter.writeAll(myResultSet, includeHeaders);
	    Thwriter.close();

	} catch (IOException | SQLException e) {
	    e.printStackTrace();  
	} finally {
	    if (stmt != null) { stmt.close(); }
	}
    }

    public static void DatabaseCreate(Connection connect_new, String dbname) throws SQLException
    {
	//Run SQL commands to create database on new server
	//Make strings for each command
	//run each command on new server connection
	Statement stmt = null;
	boolean result;
	try{
	    stmt = connect_new.createStatement();
	    
	    String CreateUSE = "USE " + dbname + ";"; //"CREATE schema SpeechPathologyDB; USE SpeechPathologyDB;";
	    result = stmt.execute(CreateUSE);
	    
	    String therapistTable = "CREATE TABLE Therapist(" +
		"Therapist_ID INT, " +
		"First_Name VARCHAR(50), " + 
		"Last_Name VARCHAR(50), " + 
		"Username VARCHAR(50), " + 
		"TPassword VARCHAR(80), " + 
		"PRIMARY KEY(Therapist_ID));";
	    result = stmt.execute(therapistTable);
	    
	    String developerTable = "CREATE TABLE Developer(" +
		"Developer_ID INT, " +
		"First_Name VARCHAR(50), " +
		"Last_Name VARCHAR(50), " +
		"Username VARCHAR(50), " +
		"DPassword  VARCHAR(80), " +
		"PRIMARY KEY(Developer_ID));";
	    result = stmt.execute(developerTable);
	    
	    String caregiverTable = "CREATE TABLE CareGiver(" +
		"CareGiver_ID INT, " +
		"First_Name VARCHAR(50), " +
		"Last_Name VARCHAR(50), " +
		"Username VARCHAR(50), " +
		"CPassword VARCHAR(80), " +
		"PRIMARY KEY(CareGiver_ID));";
	    result = stmt.execute(caregiverTable);
	    
	    String researcherTable = "CREATE TABLE Researcher(" +
		"Researcher_ID INT, " +
		"First_Name VARCHAR(50), " +
		"Last_Name VARCHAR(50), " +
		"Username VARCHAR(50), " +
		"RPassword  VARCHAR(80), " +
		"PRIMARY KEY(Researcher_ID));";
	    result = stmt.execute(researcherTable);
	    
	    String subjectinfoTable = "CREATE TABLE SubjectInfo(" +
		"Subject_ID INT, " +
		"Birthday VARCHAR(10), " +
		"Sex VARCHAR(1), " +
		"Race VARCHAR(30), " +
		"Nationality VARCHAR(16), " +
		"Speech_Ability VARCHAR(50), " +
		"Diagnosis VARCHAR(50), " +
		"Therapist_ID INT, " +
		"CareGiver_ID INT, " +
		"PRIMARY KEY(Subject_ID), " +
		"FOREIGN KEY(Therapist_ID) REFERENCES Therapist(Therapist_ID) ON DELETE SET NULL, " +
		"FOREIGN KEY(CareGiver_ID) REFERENCES CareGiver(CareGiver_ID) ON DELETE SET NULL);";
	    result = stmt.execute(subjectinfoTable);
	    
	    String usersessionTable = "CREATE TABLE UserSession(" +
		"Session_ID VARCHAR(10), " +
		"Mic VARCHAR(50), " +
		"Type_Of_Room VARCHAR(50), " +
		"Location VARCHAR(50), " +
		"Session_Number INT, " +
		"PRIMARY KEY (Session_ID));";
	    result = stmt.execute(usersessionTable);
	    
	    String audioTable = "CREATE TABLE Audio(" +
		"Audio_ID INT, " +
		"AudioDate VARCHAR(25), " +
		"Audio_Format VARCHAR(10)," +
		"Raw_Audio_Root VARCHAR(100), " +
		"Raw_Audio_Filename VARCHAR(100), " +
		"Purpose VARCHAR(1), " +
		"Noise_Profile VARCHAR(50), " +
		"Channels INT, " +
		"Sample_Rate INT, " +
		"Duration_time VARCHAR(20), " +
		"File_Size Decimal(20,5), " +
		"Bit_Depth Decimal(20,5), " +
		"ASR_Version VARCHAR(200), " +
		"ASR_Trascript VARCHAR(200), " +
		"ASR_Prompt VARCHAR(300), " +
		"ASR_Hypothesis VARCHAR(300), " +
		"Researcher_ID INT, " +
		"Developer_ID INT, " +
		"Session_ID VARCHAR(10), " +
		"Subject_ID INT, " +
		"PRIMARY KEY(Audio_ID), " +
		"FOREIGN KEY(Researcher_ID) REFERENCES Researcher(Researcher_ID) ON DELETE SET NULL, " +
		"FOREIGN KEY(Developer_ID) REFERENCES Developer(Developer_ID) ON DELETE SET NULL, " +
		"FOREIGN KEY(Session_ID) REFERENCES UserSession(Session_ID) ON DELETE SET NULL, " +
		"FOREIGN KEY(Subject_ID) REFERENCES SubjectInfo(Subject_ID) ON DELETE SET NULL);";
	    result = stmt.execute(audioTable);
	   
	    String therapyTable = "CREATE TABLE Therapy(" +
		"Patient_ID INT, " +
		"Subject_ID INT, " +
		"Therapist_ID INT, " +
		"FOREIGN KEY (Subject_ID) REFERENCES SubjectInfo(Subject_ID), " +
		"FOREIGN KEY (Therapist_ID) REFERENCES Therapist(Therapist_ID), " +
		"PRIMARY KEY(Subject_ID, Therapist_ID));";
	    result = stmt.execute(therapyTable);
	    
	} catch (SQLException e ) {
            e.printStackTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }

    }

    public static void CSVImport(Connection connect_new) throws SQLException
    {
	//Import .csv files created from CSVCreate into new database
	Statement stmt = null;
	    
	try{
	    stmt = connect_new.createStatement();

	    String allow = "SET GLOBAL local_infile = 'ON';";
	    stmt.execute(allow);

	    String loadQuery = "LOAD DATA LOCAL INFILE '" + "./DevTable.csv" + "' INTO TABLE Developer FIELDS TERMINATED BY ','" + " LINES TERMINATED BY '\n' IGNORE 1 ROWS (Developer_ID, First_Name, Last_Name, Username, DPassword);";
	    stmt.execute(loadQuery);
	    
	    loadQuery = "LOAD DATA LOCAL INFILE '" + "./TheTable.csv" + "' INTO TABLE Therapist FIELDS TERMINATED BY ','" + " LINES TERMINATED BY '\n' IGNORE 1 ROWS (Therapist_ID, First_Name, Last_Name, Username, TPassword);";
	    stmt.execute(loadQuery);
	    
	    loadQuery = "LOAD DATA LOCAL INFILE '" + "./ResTable.csv" + "' INTO TABLE Researcher FIELDS TERMINATED BY ','" + " LINES TERMINATED BY '\n' IGNORE 1 ROWS (Researcher_ID, First_Name, Last_Name, Username, RPassword);";
	    stmt.execute(loadQuery);
	    
	    loadQuery = "LOAD DATA LOCAL INFILE '" + "./CGTable.csv" + "' INTO TABLE CareGiver FIELDS TERMINATED BY ','" + " LINES TERMINATED BY '\n' IGNORE 1 ROWS (CareGiver_ID, First_Name, Last_Name, Username, CPassword);";
	    stmt.execute(loadQuery);
	    
	    loadQuery = "LOAD DATA LOCAL INFILE '" + "./SubTable.csv" + "' INTO TABLE SubjectInfo FIELDS TERMINATED BY ','" + " LINES TERMINATED BY '\n' IGNORE 1 ROWS (Subject_ID, Birthday, Sex, Race, Nationality, Speech_Ability, Diagnosis, Therapist_ID, CareGiver_ID);";
	    stmt.execute(loadQuery);
	    
	    loadQuery = "LOAD DATA LOCAL INFILE '" + "./SessTable.csv" + "' INTO TABLE UserSession FIELDS TERMINATED BY ','" + " LINES TERMINATED BY '\n' IGNORE 1 ROWS (Session_ID, Mic, Type_Of_Room, Location, Session_Number);";
	    stmt.execute(loadQuery);
	    
	    loadQuery = "LOAD DATA LOCAL INFILE '" + "./AudTable.csv" + "' INTO TABLE Audio FIELDS TERMINATED BY ','" + " LINES TERMINATED BY '\n' IGNORE 1 ROWS (Audio_ID, AudioDate, Raw_Audio_Root, Raw_Audio_FileName, Audio_Format, Purpose, Noise_Profile, Channels, Sample_Rate, Duration_time, File_Size, Bit_Depth, ASR_Version, ASR_Trascript, ASR_Prompt, ASR_Hypothesis, Researcher_ID, Developer_ID, Session_ID, Subject_ID);";
	    stmt.execute(loadQuery);
	    
	    loadQuery = "LOAD DATA LOCAL INFILE '" + "./TherapyTable.csv" + "' INTO TABLE Therapy FIELDS TERMINATED BY ','" + " LINES TERMINATED BY '\n' IGNORE 1 ROWS (Patient_ID, Subject_ID, Therapist_ID);";
	    stmt.execute(loadQuery);
	    
	    System.out.println("Loaded");
	   
	} catch (SQLException e ) {
	    e.printStackTrace();
	} finally {
	    if (stmt != null) { stmt.close(); }
	}
    }
}