import model.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DataDescriptionFrame extends JFrame {
    private JTable table;

    public DataDescriptionFrame(List<Patient> trainData, List<Patient> testData, int n) {
        setTitle("Data Description");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création de l'en-tête
        String[] columnNames = {"ID", "Age", "Education", "Sex", "Is Smoking", "Cigs/Day", "BPMeds", "Prevalent Stroke",
                "Prevalent Hyp", "Diabetes", "TotChol", "SysBP", "DiaBP", "BMI", "HeartRate", "Glucose", "TenYearCHD"};

        // Création du modèle de table
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        addDataToModel(model, trainData, "Train Data", n);
       // addDataToModel(model, testData, "Test Data", n);

        // Création de la table
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(800, 600));
        table.setFillsViewportHeight(true);

        // Ajout de la table à un JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        setVisible(true);
    }

    private void addDataToModel(DefaultTableModel model, List<Patient> data, String dataTitle, int n) {
        model.addRow(new Object[]{dataTitle, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""});
        int count = 0;
        for (Patient patient : data) {
            if (count < n) {
                model.addRow(new Object[]{patient.getId(), patient.getAge(), patient.getEducation(), patient.getSex(), patient.getIsSmoking(),
                        patient.getCigsPerDay(), patient.getBpMeds(), patient.getPreleventStroke(), patient.getPrevalentHyp(),
                        patient.getDiabetes(), patient.getTotChol(), patient.getSysBP(), patient.getDiaBP(), patient.getBMI(),
                        patient.getHeartRate(), patient.getGlucose(), patient.getTenYearCHD()});
                count++;
            } else {
                break;
            }
        }
    }
}
