/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package import_csv_database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Scanner;



public class Import_csv_database
{
	private static Connection con;
 
	public static void main(String[] args) throws FileNotFoundException, SQLException
	{
 
		long startTime = System.currentTimeMillis();
		//File researcher = new File("/Users/wei/Desktop/database_csv/Researcher.csv");
 
		//Scanner in = new Scanner(researcher);
 
		getConnect();
		System.out.println("connected");
                
               // insert_data_resesrcher(in);
               // in.close();
                
               // File CareGiver  = new File("/Users/wei/Desktop/database_csv/CareGiver.csv");
               // Scanner cg = new Scanner(CareGiver);
                //insert_data_caregiver(cg);
               // cg.close();
                
               // File Therapist  = new File("/Users/wei/Desktop/database_csv/th.csv");
               // Scanner tp = new Scanner(Therapist);
               // insert_data_therapist(tp);
               // tp.close();
                
                //File Subject  = new File("/Users/wei/Desktop/database_csv/Subject.csv");
                //Scanner sb = new Scanner(Subject);
                //insert_data_subject(sb);
                //sb.close();
                
                //File Audio  = new File("/Users/wei/Desktop/database_csv/Audio.csv");
                //Scanner ad = new Scanner(Audio);
                //insert_data_audio(ad);
                //ad.close();
                
                //File Records  = new File("/Users/wei/Desktop/database_csv/Record.csv");
                //Scanner rc = new Scanner(Records);
                //insert_data_records(rc);
                //rc.close();
                
                //File Developer  = new File("/Users/wei/Desktop/database_csv/Developer.csv");
                //Scanner dp = new Scanner(Developer);
                //insert_data_developer(dp);
                //dp.close();
                
                
		long EndTime = System.currentTimeMillis();
		long time = (EndTime - startTime) / 1000;
 
		System.out.println("titaltime：" + time);
                
                
	}
        private static void insert_data_caregiver(Scanner in) throws SQLException
	{
		int count = 0;
		String sql = "insert into CareGiver (CareGiver_ID,First_Name,Last_Name,Username,Password)"
				+ "values(?,?,?,?,?)";
 
		con.setAutoCommit(false);
		PreparedStatement pstmt = con.prepareStatement(sql);
		//in.next();
		while (in.hasNext())
		{
			String temp1 = in.nextLine();
			String[] temp = temp1.split(",");
 
			if (temp.length < 5)
				continue;
 
			if (temp.length == 5)
			{
				pstmt.setString(1, temp[0]);
				pstmt.setString(2, temp[1]);
				pstmt.setString(3, temp[2]);
				pstmt.setString(4, temp[3]);
				pstmt.setString(5, temp[4]);
			}
 
			pstmt.addBatch();
 
			count++;
				count = execute(pstmt, count);
		}
		pstmt.executeBatch();
		con.commit();
 
	}
        private static void insert_data_therapy(Scanner in) throws SQLException
	{
		int count = 0;
		String sql = "insert into Therapy (Patient_ID,Subject_number,Therapist_number)"
				+ "values(?,?,?)";
 
		con.setAutoCommit(false);
		PreparedStatement pstmt = con.prepareStatement(sql);
		//in.next();
		while (in.hasNext())
		{
			String temp1 = in.nextLine();
			String[] temp = temp1.split(",");
 
			if (temp.length < 3)
				continue;
 
			if (temp.length == 3)
			{
				pstmt.setInt(1, Integer.valueOf(temp[0]));
				pstmt.setString(2, temp[1]);
				pstmt.setString(3, temp[2]);
			}
 
			pstmt.addBatch();
 
			count++;
				count = execute(pstmt, count);
		}
		pstmt.executeBatch();
		con.commit();
 
	}
        private static void insert_data_records(Scanner in) throws SQLException
	{
		int count = 0;
		String sql = "insert into Records (records,Mic,Type_Of_Room,Location,Session_Number,Subject_number,Audio_number)"
				+ "values(?,?,?,?,?,?,?)";
 
		con.setAutoCommit(false);
		PreparedStatement pstmt = con.prepareStatement(sql);
		//in.next();
		while (in.hasNext())
		{
			String temp1 = in.nextLine();
			String[] temp = temp1.split(",");
 
			if (temp.length < 7)
				continue;
 
			if (temp.length == 7)
			{
				pstmt.setString(1, temp[0]);
				pstmt.setString(2, temp[1]);
				pstmt.setString(3, temp[2]);
				pstmt.setString(4, temp[3]);
				pstmt.setInt(5, Integer.valueOf(temp[4]));
                                pstmt.setString(6, temp[5]);
                                pstmt.setString(7, temp[6]);
			}
 
			pstmt.addBatch();
 
			count++;
				count = execute(pstmt, count);
		}
		pstmt.executeBatch();
		con.commit();
 
	}
        private static void insert_data_audio(Scanner in) throws SQLException
	{
		int count = 0;
		String sql = "insert into Audio (Audio_ID,Date,Audio_Root,Purpose,Mono_Stereo,Sample_Rate,Bit_Rate,Duration,ASR_Error,ASR_Trascript,ASR_Version,Noise_Profile,Researcher_number)"
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
 
		con.setAutoCommit(false);
		PreparedStatement pstmt = con.prepareStatement(sql);
		//in.next();
		while (in.hasNext())
		{
			String temp1 = in.nextLine();
			String[] temp = temp1.split(",");
 
			if (temp.length < 13)
				continue;
 
			if (temp.length == 13)
			{
                         
				pstmt.setString(1, temp[0]);
                                Timestamp date = Timestamp.valueOf(temp[1]);
				pstmt.setTimestamp(2, date);
				pstmt.setString(3, temp[2]);
				pstmt.setString(4, temp[3]);
				pstmt.setString(5, temp[4]);
                                pstmt.setInt(6, Integer.valueOf(temp[5]));
                                pstmt.setInt(7, Integer.valueOf(temp[6]));
                                pstmt.setInt(8, Integer.valueOf(temp[7]));
                                pstmt.setString(9, temp[8]);
                                pstmt.setString(10, temp[9]);
                                pstmt.setString(11, temp[10]);
                                pstmt.setString(12, temp[11]);
                                pstmt.setString(13, temp[12]);
			}
 
			pstmt.addBatch();
 
			count++;
				count = execute(pstmt, count);
		}
		pstmt.executeBatch();
		con.commit();
 
	}
        private static void insert_data_subject(Scanner in) throws SQLException
	{
		int count = 0;
		String sql = "insert into Subject (Subject_ID,Birthday,Sex,Race,Nationality,Speech_Ability,Diagnosis,Therapist_number,CareGiver_number)"
				+ "values(?,?,?,?,?,?,?,?,?)";
 
		con.setAutoCommit(false);
		PreparedStatement pstmt = con.prepareStatement(sql);
		//in.next();
		while (in.hasNext())
		{
			String temp1 = in.nextLine();
			String[] temp = temp1.split(",");
 
			if (temp.length < 9)
				continue;
 
			if (temp.length == 9)
			{
				pstmt.setString(1, temp[0]);
				pstmt.setString(2, temp[1]);
				pstmt.setString(3, temp[2]);
				pstmt.setString(4, temp[3]);
				pstmt.setString(5, temp[4]);
                                pstmt.setString(6, temp[5]);
                                pstmt.setString(7, temp[6]);
                                pstmt.setString(8, temp[7]);
                                pstmt.setString(9, temp[8]);
			}
 
			pstmt.addBatch();
 
			count++;
				count = execute(pstmt, count);
		}
		pstmt.executeBatch();
		con.commit();
 
	}
        private static void insert_data_therapist(Scanner in) throws SQLException
        {
            int count = 0;
		String sql = "insert into Therapist (Therapist_ID,First_Name,Last_Name,Username,Password)"
				+ "values(?,?,?,?,?)";
 
		con.setAutoCommit(false);
		PreparedStatement pstmt = con.prepareStatement(sql);
		//in.next();
		while (in.hasNext())
		{
			String temp1 = in.nextLine();
			String[] temp = temp1.split(",");
 
			if (temp.length < 5)
				continue;
 
			if (temp.length == 5)
			{
				pstmt.setString(1, temp[0]);
				pstmt.setString(2, temp[1]);
				pstmt.setString(3, temp[2]);
				pstmt.setString(4, temp[3]);
				pstmt.setString(5, temp[4]);
			}
 
			pstmt.addBatch();
 
			count++;
				count = execute(pstmt, count);
		}
		pstmt.executeBatch();
		con.commit();
    }
        private static void insert_data_developer(Scanner in) throws SQLException
	{
		int count = 0;
		String sql = "insert into Developer (Developer_ID,First_Name,Last_Name,Username,Password)"
				+ "values(?,?,?,?,?)";
 
		con.setAutoCommit(false);
		PreparedStatement pstmt = con.prepareStatement(sql);
		//in.next();
		while (in.hasNext())
		{
			String temp1 = in.nextLine();
			String[] temp = temp1.split(",");
 
			if (temp.length < 5)
				continue;
 
			if (temp.length == 5)
			{
				pstmt.setString(1, temp[0]);
				pstmt.setString(2, temp[1]);
				pstmt.setString(3, temp[2]);
				pstmt.setString(4, temp[3]);
				pstmt.setString(5, temp[4]);
			}
 
			pstmt.addBatch();
 
			count++;
				count = execute(pstmt, count);
		}
		pstmt.executeBatch();
		con.commit();
 
	}
	private static void insert_data_resesrcher(Scanner in) throws SQLException
	{
		int count = 0;
		String sql = "insert into Researcher (Researcher_ID,First_Name,Last_Name,Username,Password)"
				+ "values(?,?,?,?,?)";
 
		con.setAutoCommit(false);
		PreparedStatement pstmt = con.prepareStatement(sql);
		//in.next();
		while (in.hasNext())
		{
			String temp1 = in.nextLine();
			String[] temp = temp1.split(",");
 
			if (temp.length < 5)
				continue;
 
			if (temp.length == 5)
			{
				pstmt.setString(1, temp[0]);
				pstmt.setString(2, temp[1]);
				pstmt.setString(3, temp[2]);
				pstmt.setString(4, temp[3]);
				pstmt.setString(5, temp[4]);
			}
 
			pstmt.addBatch();
 
			count++;
				count = execute(pstmt, count);
		}
		pstmt.executeBatch();
		con.commit();
 
	}
 
	public static int execute(PreparedStatement pstmt, int count) throws SQLException
	{
 
		pstmt.executeBatch();
		con.commit();
		return 0;
 
	}
 
	private static void getConnect()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://35.247.82.214/testDB_2",
					"SpeechPathology", "SpeechPathology");
		}
		catch (ClassNotFoundException | SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 
}