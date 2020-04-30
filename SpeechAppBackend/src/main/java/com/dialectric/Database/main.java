package com.dialectric.Database;

import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

class main extends DBWrapper{

    public static void main(String[] args)
    {
	//this is all used for testing! Not needed for wrapper to function
	try {
            //Connect to database
	    DBWrapper db = new DBWrapper();
	    Connection connection = db.getConnection("35.247.82.214", "SpeechPathology", "SpeechPathology", "testDB_2");

            //Input from user if new or old account
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            System.out.println("New or Old account?\n");
            String account = br.readLine();

            //If user says they are new
            if(account.equals("New")) {
            	//ask type of account they are making
				System.out.println("Researcher, Therapist, CareGiver, or Developer?\n");
				String accountType = br.readLine();

		    	setAccountType(accountType);

		    	System.out.println("Username: ");
		    	String username = br.readLine();
		    	System.out.println("Password: ");
		    	String password = br.readLine();

		    	String computed_hash = db.hashPassword(password);
		    	db.CreateAccount(connection, username, computed_hash, "SpeechPathology", "testDB_2");

		    	//if they are a new Caregiver they need to fill out a section in the subject table
				if(accountType.equals("CareGiver")) {
					System.out.println("Birthday (MM/DD/YYYY): ");
					String birthday = br.readLine();

					System.out.println("Sex (M/F): ");
					String sex = br.readLine();

					System.out.println("Race: ");
					String race = br.readLine();

					System.out.println("Nationality: ");
					String nationality = br.readLine();

					System.out.println("Speech Ability: ");
					String speechAbility = br.readLine();

					System.out.println("Diagnosis: ");
					String diagnosis = br.readLine();

					System.out.println("Therapist First Name: ");
					String therapistFName = br.readLine();

					System.out.println("Therapist Last Name: ");
					String therapistLName = br.readLine();

					int id = db.getID();
					db.AddSubjectInfo(connection, birthday, sex, race, nationality, speechAbility, diagnosis, therapistFName, therapistLName, id);
				}
			}

	    	//if user says they are old, acocunt will be searched for
            else {
            	int flag = 0;
            	while (flag == 0) {
                            System.out.println("Username: ");
                            String username = br.readLine();
                            System.out.println("Password: ");
                            String password = br.readLine();

                            //String computed_hash = db.hashPassword(password);
                            String found = db.FindAccount(connection, username, password, "SpeechPathology", "testDB_2");

                            //if found send to function with flag to search database
                            if(found.equals("Developer") || found.equals("CareGiver") || found.equals("Therapist") || found.equals("Researcher"))
                                {
                                    System.out.println("Found");
                                    AccountType = found;
                                    flag = 1;
                                }
                            else
                                {
                                    System.out.println("Account not identified. Retry or exit?");
                                    String answer = br.readLine();
                                    if(answer.equals("exit"))
                                        {
                                            System.exit(0);
                                        }
                                }
            	}
            }

            System.out.println("Update Account [y or n]: ");
            String response = br.readLine();
	    	//what to update account?
            if(response.equals("y"))
                {
                    System.out.println("_Password or Username? ");
                    response = br.readLine();
                    System.out.println("What would you like to change to? ");
                    String updatedinfo = br.readLine();

                    System.out.println("First Name: ");
                    String fname = br.readLine();
                    System.out.println("Last Name: ");
                    String lname = br.readLine();

		    //if they want to change their password
                    if(response.equals("password"))
                        {
                            System.out.println("what is your username? ");
                            String un = br.readLine();
			    db.UpdateDeveloper(connection, response, updatedinfo, un, fname, lname, "SpeechPathology", "testDB_2");
                        }
		    //if they want to change their username
                    else if(response.equals("username"))
                        {
                            System.out.println("what is your password? ");
                            String pw = br.readLine();

                            db.UpdateDeveloper(connection, response, updatedinfo, pw, fname, lname, "SpeechPathology", "testDB_2");
                        }
                }
            System.out.println("complete");

	    //Want to port out?
	    System.out.println("Port? [y or n]: ");
            response = br.readLine();
            if(response.equals("y"))
                {
		    Connection new_con;
		    //db.CSVCreate(connection);
					// TODO: Hard-coded ip and fields need to be changed
		    new_con = db.getConnection("127.0.0.1", "Hailey", "Hailey", "dbname");
		    if(new_con != null){
			//db.DatabaseCreate(new_con, "dbname");
			db.CSVImport(new_con);
			db.closeConnection(new_con);
		    }
		}

	    System.out.println("Import files? [y or n]: ");
	    response = br.readLine();
	    if(response.equals("y")){
		ImportDataFiles("gs://","File", connection);
	    }

            //when finished, close connection
            db.closeConnection(connection);

            System.exit(0);
        }
        catch(IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void ImportDataFiles(String path, String FileName, Connection con) throws IOException
    {
	DBWrapper db = new DBWrapper();

	String sex = "null";
	String ext = "null";
	String filename = "null";
	String filesize = "null";
	String duration = "null";
	String samplerate = "null";
	String bitdepth = "null";
	String ASRTrans = "null";
	String ASRPrompt = "null";
	String ASRhyp = "null";
	String audioformat = "*.wav";
	String channels = "null";
	String noise_pro = "null";
	String purpose = "null";
  	String date = "null";
	String resercher_id = "null";
	String dev_id = "null";
	String sess_id = "null";

	String tname = "null";
	String pname = "null";

	String fext = "null";
	String ffilename = "null";
	String ffilesize = "null";
	String fduration = "null";
	String fsamplerate = "null";
	String fbitdepth = "null";
	String fASRTrans = "null";
	String fASRhyp = "null";
	String fchannels = "null";
	int count = 0;

	String name = "null";

	//TODO: hard-coded paths need to be changed
	//files are name.csv, point.csv, trans.csv, header.csv
	try{
	    FileReader fr1 = new FileReader("/home/johnshai001/speech-application/src/main/java/com.dialectric.Database/name.csv");
	    CSVReader csv1 = new CSVReader(fr1);
            String[] nextRec1;

	    FileReader fr2 = new FileReader("/home/johnshai001/speech-application/src/main/java/com.dialectric.Database/header.csv");
	    CSVReader csv2 = new CSVReader(fr2);
            String[] nextRec2;

	    FileReader fr3 = new FileReader("/home/johnshai001/speech-application/src/main/java/com.dialectric.Database/trans.csv");
	    CSVReader csv3 = new CSVReader(fr3);
            String[] nextRec3;

	    FileReader fr4 = new FileReader("/home/johnshai001/speech-application/src/main/java/com.dialectric.Database/point.csv");
	    CSVReader csv4 = new CSVReader(fr4);
            String[] nextRec4;

	    int flag = 0;
	    int innerflag = 0;
	    int innerflag2 = 0;
	    int innerflag3 = 0;
	    int innerflag4 = 0;
	    String query = "null";

	    String sid = "s0";
	    String aid = "a0";

      //skips start line
	    nextRec1 = csv1.readNext();
	    nextRec2 = csv2.readNext();
	    nextRec3 = csv3.readNext();
	    nextRec4 = csv4.readNext();

	    while(flag == 0)
		{
		    if((nextRec1 = csv1.readNext()) != null) {
			for (String cell : nextRec1) {
			    if(cell.length() == 4)
				{
				    name = cell;
				    System.out.println(name);
				}
			    else
				{
				    sex = cell;
				    System.out.println(sex);
				}
			}
		    }
		    else {
			flag = 1;
			System.out.println("flag changed");
		    }

		    if(flag != 1)
			{
			    query = "INSERT INTO 'SubjectInfo' ('Subject_ID', 'Birthday', 'Sex', 'Race', 'Nationality', 'Speech_Ability', 'Diagnosis', 'Therapist_ID', 'CareGiver_ID') VALUES ('" + sid  +
				"','null','" + sex  + "','null','null','null','null','null','null');";
			    System.out.println(query);
			}

		    if(count > 0 && flag != 1)
			{
			    System.out.println("outerloop ffilename: " + ffilename + " " + ffilename.substring(0,4) + " " + name);
			    if(name.equals(ffilename.substring(0,4)))
				{
				    query = "INSERT INTO 'Audio' ('Audio_ID', 'AudioDate', 'Raw_Audio_Root', 'Raw_Audio_FileName', 'Audio_Format', 'Purpose', 'Noise_Profile', 'Channels', 'Sample_Rate', 'Duration_time', 'File_Size', 'Bit_Depth', 'ASR_Version', 'ASR_Trascript', 'ASR_Prompt', 'ASR_Hypothesis', 'Researcher_ID', 'Developer_ID', 'Session_ID', 'Subject_ID') VALUES ('" +
            aid  + "','null','" + fext + "','" + ffilename + "','" + audioformat + "','" + purpose + "','" + noise_pro + "','" + fchannels + "','" + fsamplerate + "','" + fduration + "','" + ffilesize + "','" + fbitdepth + "','null','" + fASRTrans + "','" + ASRPrompt + "','" + fASRhyp + "','null','" + dev_id + "','null','" + sid + "');";
				    System.out.println(query);

            fext = "null";
            ffilename = "null";
            ffilesize = "null";
            fduration = "null";
            fsamplerate = "null";
            fbitdepth = "null";
            fASRTrans = "null";
            fASRhyp = "null";
            fchannels = "null";
				}
			}

		    while(innerflag == 0 && flag != 1)
			{
                            ext = "null";
                            filename = "null";
                            filesize = "null";
                            duration = "null";
                            samplerate = "null";
                            bitdepth = "null";
                            ASRTrans = "null";
                            ASRPrompt = "null";
                            ASRhyp = "null";
                            channels = "null";

			    if(innerflag2 == 0){
				if(((nextRec2 = csv2.readNext()) != null) && (nextRec2[0].equals(name)))
				    {
					filename = nextRec2[1];
					ext = nextRec2[2];
					channels = nextRec2[4];
					samplerate = nextRec2[5];
					bitdepth = nextRec2[6].substring(0,2);
					duration = nextRec2[7].substring(0,11);
					filesize = nextRec2[8];

					System.out.println("parsed header: " + ext + " " + filename + " " + channels + " " + samplerate + " " + bitdepth + " " + duration + " " + filesize);
				    }
				else
				    {
					if((nextRec2 != null) && !(nextRec2[0].equals(name)))
					    {
						ffilename = nextRec2[1];
						fext = nextRec2[2];
						fchannels = nextRec2[4];
						fsamplerate = nextRec2[5];
						fbitdepth = nextRec2[6].substring(0,2);
						fduration = nextRec2[7].substring(0,11);
						ffilesize = nextRec2[8];

						System.out.println("update header: name = " + nextRec2[0] + " " + fext + " " + ffilename + " " + fchannels + " " + fsamplerate + " " + fbitdepth + " " + fduration + " " + ffilesize);

					    }
					innerflag2 = 1;
				    }
			    }

			    if(innerflag3 == 0){
				if(fASRTrans.equals("null"))
				    {
					if(((nextRec3 = csv3.readNext()) != null) && (nextRec3[0].equals(name)))
					    {
						tname = nextRec3[1] + ".wav";
						System.out.println(nextRec3[1]);
						System.out.println(filename);

						if(tname.equals(filename))
						    {
							ASRTrans = nextRec3[2];
							System.out.println("parsed trans: " + ASRTrans);

						    }
						else if( filename.equals("null"))
						    {
							ASRTrans = nextRec3[2];
                                                        System.out.println("parsed trans while filename = null: " + ASRTrans);
							filename = tname;
						    }
						else
						    {
							fASRTrans = nextRec3[2];
						    }
					    }
					else
					    {
                System.out.println("entered else statement");
						if((nextRec3 != null) && !(nextRec3[0].equals(name)))
						    {
							fASRTrans = nextRec3[2];
							System.out.println("update trans: name  = " + nextRec3[0] + " fASRTrans = " + fASRTrans);
						    }
						innerflag3 = 1;
					    }

				    }
				else
				    {
					if(tname.equals(filename))
					    {
						ASRTrans = fASRTrans;
						fASRTrans = "null";
					    }
				    }
			    }

			    /*if(innerflag4 == 0){
				if(fASRhyp.equals("null"))
				    {
              System.out.println("fASR = null");
					if(((nextRec4 = csv4.readNext()) != null) && (nextRec4[0].equals(name)))
					    {
						pname = nextRec4[1] + ".wav";
						System.out.println(nextRec4[1]);
						System.out.println(filename);

						if(pname.equals(filename))
						    {
							ASRhyp = nextRec4[2];
							System.out.println("parsed point: " + ASRhyp);

						    }
						else if( filename.equals("null"))
                                                    {
                                                        ASRhyp = nextRec4[2];
                                                        System.out.println("parsed point: " + ASRhyp);
                                                        filename = pname;
                                                    }
						else
						    {
                  fASRhyp = nextRec4[2];
							System.out.println("fASRhyp update: " + fASRhyp);
						    }
					    }
					else
					    {
						System.out.println("else");
						if((nextRec4 != null) && !(nextRec4[0].equals(name)))
						    {
							fASRhyp = nextRec4[2];
							System.out.println("update point: name = " + nextRec4[0] + " fASRhyp = " + fASRhyp);
						    }
						innerflag4 = 1;
					    }
				    }
				else
				    {
              System.outer.println("fASR != null, checkfilename");
					if(pname.equals(filename))
                                            {
                                                ASRhyp = fASRhyp;
                                                fASRhyp = "null";
                                            }
				    }
			    }*/

			    if(innerflag2 == 1 && innerflag3 == 1 )//&& innerflag4 == 1)
				{
				    System.out.println("UPDATED innerflag");
				    innerflag = 1;
				}

			    if(innerflag == 0)
				{
				    query = "INSERT INTO 'Audio' ('Audio_ID', 'AudioDate', 'Raw_Audio_Root', 'Raw_Audio_FileName', 'Audio_Format', 'Purpose', 'Noise_Profile', 'Channels', 'Sample_Rate', 'Duration_time', 'File_Size', 'Bit_Depth', 'ASR_Version', 'ASR_Trascript', 'ASR_Prompt', 'ASR_Hypothesis', 'Researcher_ID', 'Developer_ID', 'Session_ID', 'Subject_ID') VALUES ('" + aid  + "','null','" + ext + "','" + filename + "','" + audioformat + "','" + purpose + "','" + noise_pro + "','" + channels + "','" + samplerate + "','" + duration + "','" + filesize + "','" + bitdepth + "','null','" + ASRTrans + "','" + ASRPrompt + "','" + ASRhyp + "','null','" + dev_id + "','null','" + sid + "');";
				    System.out.println(query);
				}

			    System.out.println("end of inner loop ffilename: " + ffilename);
			}
		    innerflag = 0;
		    innerflag2 = innerflag3 = innerflag4 = 0;
		    count++;
        sex = "null";
        name = "null";
		}
	}
	catch(IOException e){
	    e.printStackTrace();
	}
    }
}
