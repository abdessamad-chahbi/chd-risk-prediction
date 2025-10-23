package data;
import model.Patient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class DataLoader {

    private String trainFilePath;
    private String testFilePath;

    public DataLoader(String trainFilePath, String testFilePath) {
        this.trainFilePath = trainFilePath;
        this.testFilePath = testFilePath;
    }

    public List<Patient> loadTrainData() throws IOException {
        return loadPatientsFromCSV(trainFilePath);
    }

    public List<Patient> loadTestData() throws IOException {
        return loadPatientsFromCSV(testFilePath);
    }

    private List<Patient> loadPatientsFromCSV(String filePath) throws IOException {
        List<Patient> patients = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Skip the header line
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 16) { // Change la condition pour vérifier si la longueur est au moins égale à 16
                    Patient patient = createPatientFromCSV(parts);
                    patients.add(patient);
                }
            }
        }

        return patients;
    }

    private Patient createPatientFromCSV(String[] data) {
        int id = parseOrDefaultInt(data, 0); // Conversion de la chaîne ID en entier
        int age = parseOrDefaultInt(data, 1); // Conversion de la chaîne age en entier
        double education = parseOrDefaultDouble(data, 2); // Conversion de la chaîne education en double
        String sex = data.length > 3 ? data[3] : ""; // Vérification de la présence de la valeur de sexe
        String isSmoking = data.length > 4 ? data[4] : ""; // Vérification de la présence de la valeur de isSmoking
        double cigsPerDay = parseOrDefaultDouble(data, 5); // Conversion de la chaîne cigsPerDay en double
        double BPMeds = parseOrDefaultDouble(data, 6); // Conversion de la chaîne BPMeds en double
        int prevalentStroke = parseOrDefaultInt(data, 7); // Conversion de la chaîne prevalentStroke en entier
        int prevalentHyp = parseOrDefaultInt(data, 8); // Conversion de la chaîne prevalentHyp en entier
        int diabetes = parseOrDefaultInt(data, 9); // Conversion de la chaîne diabetes en entier
        double totChol = parseOrDefaultDouble(data, 10); // Conversion de la chaîne totChol en double
        double sysBP = parseOrDefaultDouble(data, 11); // Conversion de la chaîne sysBP en double
        double diaBP = parseOrDefaultDouble(data, 12); // Conversion de la chaîne diaBP en double
        double BMI = parseOrDefaultDouble(data, 13); // Conversion de la chaîne BMI en double
        double heartRate = parseOrDefaultDouble(data, 14); // Conversion de la chaîne heartRate en double
        double glucose = parseOrDefaultDouble(data, 15); // Conversion de la chaîne glucose en double
        int tenYearCHD = parseOrDefaultInt(data, 16); // Conversion de la chaîne TenYearCHD en entier

        // Création d'un objet Patient avec les données extraites du CSV
        return new Patient(id, age, education, sex, isSmoking, cigsPerDay, BPMeds, prevalentStroke,
                prevalentHyp, diabetes, totChol, sysBP, diaBP, BMI, heartRate, glucose, tenYearCHD);
    }

    // Fonction pour convertir une chaîne en entier avec une valeur par défaut si la chaîne est vide ou si l'index n'existe pas
    private double parseOrDefaultDouble(String[] data, int index) {
        if (index < data.length && !data[index].isEmpty()) {
            return Double.parseDouble(data[index]);
        }
        return Double.NaN; // Retourner NaN si l'index est hors limites ou si la chaîne est vide
    }

    private int parseOrDefaultInt(String[] data, int index) {
        if (index < data.length && !data[index].isEmpty()) {
            return Integer.parseInt(data[index]);
        }
        return (int) Double.NaN; // Retourner NaN si l'index est hors limites ou si la chaîne est vide
    }


}
