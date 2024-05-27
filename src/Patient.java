public class Patient {
    private String patientId;
    private String name;
    private int age;
    private String sex;
    private String diagnosis;
    private String careRecommendation;

    public Patient(String patientId, String name, int age, String sex, String diagnosis, String careRecommendation) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.diagnosis = diagnosis;
        this.careRecommendation = careRecommendation;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getCareRecommendation() {
        return careRecommendation;
    }
}
