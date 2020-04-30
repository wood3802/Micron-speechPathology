CREATE schema DB;

USE DB;

CREATE TABLE Therapist(
       Therapist_ID VARCHAR(10),
       First_Name VARCHAR(50),
       Last_Name VARCHAR(50),
       Username VARCHAR(50),
       TPassword VARCHAR(80),
       PRIMARY KEY(Therapist_ID)
);

CREATE TABLE Developer(
       Developer_ID VARCHAR(10),
       First_Name VARCHAR(50),
       Last_Name VARCHAR(50),
       Username	  VARCHAR(50),
       DPassword   VARCHAR(80),
       PRIMARY KEY(Developer_ID)
);

CREATE TABLE CareGiver(
       CareGiver_ID VARCHAR(10),
       First_Name VARCHAR(50),
       Last_Name VARCHAR(50),
       Username	  VARCHAR(50),
       CPassword   VARCHAR(80),
       PRIMARY KEY(CareGiver_ID)
);

CREATE TABLE Researcher(
       Researcher_ID VARCHAR(10),
       First_Name VARCHAR(50),
       Last_Name VARCHAR(50),
       Username	  VARCHAR(50),
       RPassword   VARCHAR(80),
       PRIMARY KEY(Researcher_ID)
);

CREATE TABLE SubjectInfo(
       Subject_ID VARCHAR(10),
       Birthday VARCHAR(10),
       Sex VARCHAR(1),
       Race VARCHAR(30),
       Nationality VARCHAR(16),
       Speech_Ability VARCHAR(50),
       Diagnosis VARCHAR(50),
       Therapist_ID VARCHAR(10),
       CareGiver_ID VARCHAR(10),
       PRIMARY KEY(Subject_ID),
       FOREIGN KEY(Therapist_ID) REFERENCES Therapist(Therapist_ID) ON DELETE SET NULL,
       FOREIGN KEY(CareGiver_ID) REFERENCES CareGiver(CareGiver_ID) ON DELETE SET NULL
);

CREATE TABLE UserSession(
       Session_ID VARCHAR(10),
       Mic VARCHAR(50),
       Type_Of_Room VARCHAR(50),
       Location VARCHAR(50),
       Session_Number INT,
       PRIMARY KEY (Session_ID) 
);

CREATE TABLE Audio(
       Audio_ID VARCHAR(10),
       AudioDate VARCHAR(25),
       Raw_Audio_Root VARCHAR(200),
       Raw_Audio_FileName VARCHAR(100),
       Audio_Format VARCHAR(10),
       Purpose VARCHAR(1),
       Noise_Profile VARCHAR(50),
       Channels INT,      
       Sample_Rate INT,
       Duration_time VARCHAR(20),
       File_Size Decimal(20,5),
       Bit_Depth INT,
       ASR_Version VARCHAR(200),
       ASR_Trascript VARCHAR(200),
       ASR_Prompt VARCHAR(300),
       ASR_Hyphothesis VARCHAR(300),
       Researcher_ID VARCHAR(10),
       Developer_ID VARCHAR(10),
       Session_ID VARCHAR(10),
       Subject_ID VARCHAR(10),
       PRIMARY KEY(Audio_ID),
       FOREIGN KEY(Researcher_ID) REFERENCES Researcher(Researcher_ID) ON DELETE SET NULL,
       FOREIGN KEY(Developer_ID) REFERENCES Developer(Developer_ID) ON DELETE SET NULL,
       FOREIGN KEY(Session_ID) REFERENCES UserSession(Session_ID) ON DELETE SET NULL,
       FOREIGN KEY(Subject_ID) REFERENCES SubjectInfo(Subject_ID) ON DELETE SET NULL
);

CREATE TABLE Therapy(
       Patient_ID INT,
       Subject_ID VARCHAR(10),
       Therapist_ID VARCHAR(10),
       FOREIGN KEY (Subject_ID) REFERENCES SubjectInfo(Subject_ID),
       FOREIGN KEY (Therapist_ID) REFERENCES Therapist(Therapist_ID),
       PRIMARY KEY(Subject_ID, Therapist_ID)
);
