package com.dialectric.models;

// model for patient (SubjectInfo table in DB)
public class Patient {
    private int id;
    private String birthday;
    private String sex;
    private String race;
    private String nationality;
    private String speechability;
    private String diagnosis;
    private int therapistid;
    private int caregiverid;

    // initialize
    public Patient() {
        this.birthday = "";
        this.sex = "";
        this.race = "";
        this.nationality = "";
        this.speechability = "";
        this.diagnosis = "";
    }

    public void setId(int id) { this.id = id; }
    public int getId() { return id; }

    public void setBirthday(String birthday) { this.birthday = birthday; }
    public String getBirthday() { return birthday; }

    public void setSex(String sex) { this.sex = sex; }
    public String getSex() { return sex; };

    public void setRace(String race) { this.race = race; }
    public String getRace() { return race; }

    public void setNationality(String nationality) { this.nationality = nationality; }
    public String getNationality() { return nationality; }

    public void setSpeechability(String speechability) { this.speechability = speechability; }
    public String getSpeechability() { return speechability; }

    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public String getDiagnosis() { return diagnosis; }

    public void setTherapistid(int therapistid) { this.therapistid = therapistid; }
    public int getTherapistid() { return therapistid; }

    public void setCaregiverid(int caregiverid) { this.caregiverid = caregiverid; }
    public int getCaregiverid() { return caregiverid; }
}
