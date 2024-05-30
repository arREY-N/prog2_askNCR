public class Patient {
    private static String patientId;
    private static String name;
    private static int age;
    private static String sex;
    private static String diagnosis;
    private static String careRecommendation;

    public Patient(String patientId, String name, int age, String sex, String diagnosis, String careRecommendation) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.diagnosis = diagnosis;
        this.careRecommendation = careRecommendation;
    }

    public static String getPatientId() {
        return patientId;
    }

    public static String getName() {
        return name;
    }

    public static int getAge() {
        return age;
    }

    public static String getSex() {
        return sex;
    }

    public static String getDiagnosis() {
        return diagnosis;
    }

    public static String getCareRecommendation() {
        return careRecommendation;
    }
}
