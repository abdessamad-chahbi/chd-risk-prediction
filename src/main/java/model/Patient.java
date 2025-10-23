package model;

public class Patient {

    private int id;
    private double education;

    // Variables démographiques
    private String sex;
    private int age;

    // Variables comportementales
    private String isSmoking;
    private double cigsPerDay;

    // Antécédents médicaux
    private double bpMeds;
    private int preleventStroke;
    private int prevalentHyp;
    private int diabetes;

    // Variables médicales actuelles
    private double totChol;
    private double sysBP;
    private double diaBP;
    private double BMI;
    private double heartRate;
    private double glucose;

    // Variable de prédiction
    private int tenYearCHD;

    // Getters et Setters
    // Vous pouvez les générer automatiquement selon votre IDE pour éviter de les taper manuellement

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getEducation() {
        return education;
    }

    public void setEducation(double education) {
        this.education = education;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getIsSmoking() {
        return isSmoking;
    }

    public void setIsSmoking(String isSmoking) {
        this.isSmoking = isSmoking;
    }

    public double getCigsPerDay() {
        return cigsPerDay;
    }

    public void setCigsPerDay(double cigsPerDay) {
        this.cigsPerDay = cigsPerDay;
    }

    public double getBpMeds() {
        return bpMeds;
    }

    public void setBpMeds(double bpMeds) {
        this.bpMeds = bpMeds;
    }

    public int getPreleventStroke() {
        return preleventStroke;
    }

    public void setPreleventStroke(int preleventStroke) {
        this.preleventStroke = preleventStroke;
    }

    public int getPrevalentHyp() {
        return prevalentHyp;
    }

    public void setPrevalentHyp(int prevalentHyp) {
        this.prevalentHyp = prevalentHyp;
    }

    public int getDiabetes() {
        return diabetes;
    }

    public void setDiabetes(int diabetes) {
        this.diabetes = diabetes;
    }

    public double getTotChol() {
        return totChol;
    }

    public void setTotChol(double totChol) {
        this.totChol = totChol;
    }

    public double getSysBP() {
        return sysBP;
    }

    public void setSysBP(double sysBP) {
        this.sysBP = sysBP;
    }

    public double getDiaBP() {
        return diaBP;
    }

    public void setDiaBP(double diaBP) {
        this.diaBP = diaBP;
    }

    public double getBMI() {
        return BMI;
    }

    public void setBMI(double BMI) {
        this.BMI = BMI;
    }

    public double getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(double heartRate) {
        this.heartRate = heartRate;
    }

    public double getGlucose() {
        return glucose;
    }

    public void setGlucose(double glucose) {
        this.glucose = glucose;
    }

    public int getTenYearCHD() {
        return tenYearCHD;
    }

    public void setTenYearCHD(int tenYearCHD) {
        this.tenYearCHD = tenYearCHD;
    }

    public Patient() {

    }

    public Patient(int id, int age, double education, String sex, String isSmoking, double cigsPerDay,
                   double BPMeds, int preleventStroke, int prevalentHyp, int diabetes, double totChol,
                   double sysBP, double diaBP, double BMI, double heartRate, double glucose, int tenYearCHD) {
        this.id = id;
        this.age = age;
        this.education = education;
        this.sex = sex;
        this.isSmoking = isSmoking;
        this.cigsPerDay = cigsPerDay;
        this.bpMeds = BPMeds;
        this.preleventStroke = preleventStroke;
        this.prevalentHyp = prevalentHyp;
        this.diabetes = diabetes;
        this.totChol = totChol;
        this.sysBP = sysBP;
        this.diaBP = diaBP;
        this.BMI = BMI;
        this.heartRate = heartRate;
        this.glucose = glucose;
        this.tenYearCHD = tenYearCHD;
    }

}
