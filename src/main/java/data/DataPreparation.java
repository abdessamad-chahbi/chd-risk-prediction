package data;

import model.Patient;

import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import org.apache.commons.math3.stat.inference.ChiSquareTest;

public class DataPreparation {

    public static void replaceMissingValues(String inputFilePath, String outputFilePath) throws IOException {
        // Lecteur du fichier CSV d'entrée
        BufferedReader reader = Files.newBufferedReader(Paths.get(inputFilePath));

        // Écrivain pour le fichier CSV de sortie
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFilePath));

        // Lecture de l'en-tête et écriture dans le fichier de sortie
        String header = reader.readLine();
        writer.write(header);
        writer.newLine();

        // Lecture et traitement des lignes du fichier CSV
        String line;
        double[] columnSums = new double[17]; // Tableau pour stocker les sommes des valeurs de chaque colonne
        int[] columnCounts = new int[17]; // Tableau pour stocker les nombres de valeurs non manquantes de chaque colonne

        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            for (int i = 0; i < fields.length; i++) {
                if (i == 2 || i == 5 || i == 6 || i == 10 || i == 13 || i == 14 || i == 15) { // Colonnes avec des valeurs manquantes // education, cigsPerDay, BPMeds, totChol, BMI, heartRate, glucose
                    if (!fields[i].isEmpty()) {
                        columnSums[i] += Double.parseDouble(fields[i]);
                        columnCounts[i]++;
                    }
                }
            }
        }

        // Calcul des moyennes de chaque colonne contenant des valeurs manquantes
        double[] columnAverages = new double[17];
        for (int i = 0; i < columnSums.length; i++) {
            if (columnCounts[i] != 0) {
                columnAverages[i] = columnSums[i] / columnCounts[i];
            }
        }

        // Retour au début du fichier pour le relire depuis le début
        reader.close();
        reader = Files.newBufferedReader(Paths.get(inputFilePath));
        reader.readLine(); // Ignorer l'en-tête

        // Lecture et traitement des lignes du fichier CSV (de nouveau)
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            for (int i = 0; i < fields.length; i++) {
                if (i == 2 || i == 5 || i == 6 || i == 10 || i == 13 || i == 14 || i == 15) { // Colonnes avec des valeurs manquantes // // education, cigsPerDay, BPMeds, totChol, BMI, heartRate, glucose
                    if (fields[i].isEmpty()) {
                        // Remplacer les valeurs manquantes par les moyennes calculées pour chaque colonne
                        fields[i] = String.format(Locale.ENGLISH, "%.3f", columnAverages[i]);
                    }
                }
                if (i == 3) { // Colonne "sex"
                    // Remplacer M par 1 et F par 0
                    if (fields[i].equals("M")) {
                        fields[i] = "1";
                    } else if (fields[i].equals("F")) {
                        fields[i] = "0";
                    }
                }
                if (i == 4) { // Colonne "is_smoking"
                    // Remplacer YES par 1 et NO par 0
                    if (fields[i].equals("YES")) {
                        fields[i] = "1";
                    } else if (fields[i].equals("NO")) {
                        fields[i] = "0";
                    }
                }
            }
            // Écriture de la ligne traitée dans le fichier de sortie
            writer.write(String.join(",", fields));
            writer.newLine();
        }

        // Fermeture des flux
        reader.close();
        writer.close();
    }

    public void DataDescriptionFrameCount(List<Patient> data) {
        JFrame frame = new JFrame("Description des données");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création des données pour la table
        String[] columnNames = {"champs", "age", "education", "sex", "is_smoking", "cigsPerDay", "BPMeds", "prevalentStroke", "prevalentHyp", "diabetes", "totChol", "sysBP", "diaBP", "BMI", "heartRate", "glucose", "TenYearCHD"};
        Object[][] rowData = {
                {"count", getNonNullCount(data, "age"), getNonNullCount(data, "education"), getNonNullCount(data, "sex"), getNonNullCount(data, "is_smoking"), getNonNullCount(data, "cigsPerDay"), getNonNullCount(data, "BPMeds"), getNonNullCount(data, "prevalentStroke"), getNonNullCount(data, "prevalentHyp"), getNonNullCount(data, "diabetes"), getNonNullCount(data, "totChol"), getNonNullCount(data, "sysBP"), getNonNullCount(data, "diaBP"), getNonNullCount(data, "BMI"), getNonNullCount(data, "heartRate"), getNonNullCount(data, "glucose"), getNonNullCount(data, "TenYearCHD")},
                {"count NaN", getNaNCount(data, "age"), getNaNCount(data, "education"), getNaNCount(data, "sex"), getNaNCount(data, "is_smoking"), getNaNCount(data, "cigsPerDay"), getNaNCount(data, "BPMeds"), getNaNCount(data, "prevalentStroke"), getNaNCount(data, "prevalentHyp"), getNaNCount(data, "diabetes"), getNaNCount(data, "totChol"), getNaNCount(data, "sysBP"), getNaNCount(data, "diaBP"), getNaNCount(data, "BMI"), getNaNCount(data, "heartRate"), getNaNCount(data, "glucose"), getNaNCount(data, "TenYearCHD")}
        };
        DefaultTableModel model = new DefaultTableModel(rowData, columnNames);

        // Création de la table
        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Ajout de la table à un JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        frame.setSize(800, 400);
        frame.setVisible(true);
    }

// ---------------------------------------------------------------------

    // Méthode pour analyser les caractéristiques catégorielles
    public void analyzeCategoricalFeatures(List<Patient> patients) {
        // Analyse du genre par rapport à la maladie coronarienne
        analyzeGenderVsCHD(patients);

        // Analyse de is_smoking par rapport à la maladie coronarienne
        analyzeIsSmokingVsCHD(patients);

        // Méthode pour analyser les caractéristiques catégorielles "tenYearCHD"
        analyzeTenYearCHD(patients);

        // Méthode pour analyser les caractéristiques catégorielles "Age vs CHD"
        analyzeAgeVsCHD(patients);

        performAgeCHDHypothesisTest(patients);

        // Méthode pour analyser les caractéristiques catégorielles "totChol vs CHD" avec condition sur le taux de cholestérol total
        analyzeTotCholVsCHDWithCholCondition(patients);

        // Méthode pour analyser les caractéristiques catégorielles "bpMeds vs CHD"
        analyzeBpMedsVsCHD(patients);

/*        // Analyse de la présence de diabète par rapport à la maladie coronarienne
        analyzeDiabetesVsCHD(patients);

        // Analyse du tabagisme par rapport à la maladie coronarienne
        analyzeSmokingVsCHD(patients);

        // Analyse de l'hypertension par rapport à la maladie coronarienne
        analyzeHypertensionVsCHD(patients);   */
    }

    // Méthode pour analyser les caractéristiques catégorielles "Gender vs CHD"
    public void analyzeGenderVsCHD(List<Patient> patients) {
        // Compter le nombre de patients de chaque genre avec et sans risque de maladie coronarienne
        int maleNoCHD = 0;
        int maleCHD = 0;
        int femaleNoCHD = 0;
        int femaleCHD = 0;

        for (Patient patient : patients) {
            if (patient.getSex().equalsIgnoreCase("M")) {
                if (patient.getTenYearCHD() == 1) {
                    maleCHD++;
                } else {
                    maleNoCHD++;
                }
            } else if (patient.getSex().equalsIgnoreCase("F")) {
                if (patient.getTenYearCHD() == 1) {
                    femaleCHD++;
                } else {
                    femaleNoCHD++;
                }
            }
        }

        // Appel de la méthode pour créer et afficher les graphiques
        createAndShowCharts("Male", "Female", maleNoCHD, maleCHD, femaleNoCHD, femaleCHD, "Quel genre est plus sujet aux CHD ?",
                "Gender", "Count", "Le nombre total des patients par sexe");
    }

    // Méthode pour analyser les caractéristiques catégorielles "isSmoking vs CHD"
    public void analyzeIsSmokingVsCHD(List<Patient> patients) {
        // Compter le nombre de patients fumeurs et non-fumeurs avec et sans risque de CHD
        int smokingNoCHD = 0;
        int smokingCHD = 0;
        int nonSmokingNoCHD = 0;
        int nonSmokingCHD = 0;

        for (Patient patient : patients) {
            if (patient.getIsSmoking().equalsIgnoreCase("YES")) {
                if (patient.getTenYearCHD() == 1) {
                    smokingCHD++;
                } else {
                    smokingNoCHD++;
                }
            } else if (patient.getIsSmoking().equalsIgnoreCase("NO")) {
                if (patient.getTenYearCHD() == 1) {
                    nonSmokingCHD++;
                } else {
                    nonSmokingNoCHD++;
                }
            }
        }

        // Appel de la méthode pour créer et afficher les graphiques
        createAndShowCharts("Smoking", "Non-Smoking", smokingNoCHD, smokingCHD, nonSmokingNoCHD, nonSmokingCHD, "Relation entre le tabagisme et les CHD",
                "Smoking Status", "Count", "Le nombre total des patients par tabagisme");
    }

    // Méthode pour analyser les caractéristiques catégorielles "tenYearCHD"
    public void analyzeTenYearCHD(List<Patient> patients) {
        // Compter le nombre total de patients avec et sans risque de maladie coronarienne sur 10 ans
        int noCHD = 0;
        int CHD = 0;

        for (Patient patient : patients) {
            if (patient.getTenYearCHD() == 1) {
                CHD++;
            } else {
                noCHD++;
            }
        }

        // Appel de la méthode pour créer et afficher les graphiques
        createAndShowCharts("No CHD", "CHD", noCHD, 0, 0, CHD, "Analyse de la variable 'TenYearCHD'",
                "CHD Status", "Count", "Le nombre total des patients par risque de CHD sur 10 ans");
    }



    // Méthode pour créer et afficher les graphiques en histogramme et en camembert
    private void createAndShowCharts(String firstLabel, String lastLabel, int firstNoCHD, int firstCHD, int lastNoCHD, int lastCHD,
                                     String histogramTitle, String histogramCategoryAxisLabel,
                                     String histogramValueAxisLabel, String pieChartTitle) {

        // Création du jeu de données pour le graphique en histogramme
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(firstNoCHD, firstLabel, "No Risk");
        dataset.addValue(firstCHD, firstLabel, "At Risk");
        dataset.addValue(lastNoCHD, lastLabel, "No Risk");
        dataset.addValue(lastCHD, lastLabel, "At Risk");

        // Création du graphique circulaire (camembert) pour le nombre total de patients
        int totalFirst = firstNoCHD + firstCHD;
        int totalLast = lastNoCHD + lastCHD;
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        pieDataset.setValue(firstLabel, totalFirst);
        pieDataset.setValue(lastLabel, totalLast);

        // Création du graphique en histogramme
        JFreeChart chart = ChartFactory.createBarChart(
                histogramTitle,
                histogramCategoryAxisLabel,
                histogramValueAxisLabel,
                dataset
        );

        // Personnalisation des couleurs
        Color orange = new Color(255, 165, 0);
        Color teal = new Color(0, 128, 128);
        chart.getCategoryPlot().getRenderer().setSeriesPaint(0, orange); // Orange pour No Risk
        chart.getCategoryPlot().getRenderer().setSeriesPaint(1, teal); // Teal pour At Risk

        // Personnalisation des étiquettes des barres
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        // Afficher les valeurs au-dessus de chaque barre
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", NumberFormat.getInstance()));
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultItemLabelFont(new Font("SansSerif", Font.PLAIN, 12));

        // Création du graphique circulaire (camembert)
        JFreeChart pieChart = ChartFactory.createPieChart(
                pieChartTitle,
                pieDataset,
                true, // Afficher la légende
                true, // Activer les infobulles
                false // Désactiver la rotation
        );

        // Personnalisation du graphique circulaire avec les mêmes couleurs que l'histogramme
        PiePlot piePlot = (PiePlot) pieChart.getPlot();
        piePlot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        piePlot.setNoDataMessage("Aucune donnée disponible");
        piePlot.setSectionPaint(firstLabel, orange);
        piePlot.setSectionPaint(lastLabel, teal);

        // Personnalisation des étiquettes des sections du graphique circulaire
        piePlot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} = {1}", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()));
        piePlot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));

        // Création du panneau de graphique pour l'histogramme
        ChartPanel chartPanel = new ChartPanel(chart);

        // Création du panneau de graphique pour le graphique circulaire
        ChartPanel pieChartPanel = new ChartPanel(pieChart);

        // Création d'un conteneur pour les deux graphiques
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2)); // Dispose les graphiques en une ligne

        // Ajout des graphiques aux panneaux
        panel.add(chartPanel);
        panel.add(pieChartPanel);

        // Création de la fenêtre pour afficher les graphiques
        JFrame frame = new JFrame("Analyse");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    // Méthode pour analyser les caractéristiques catégorielles "Age vs CHD"
    public void analyzeAgeVsCHD(List<Patient> patients) {
        // Compter le nombre de patients de moins de 65 ans et de 65 ans ou plus avec et sans risque de maladie coronarienne
        int under65NoCHD = 0;
        int under65CHD = 0;
        int above65NoCHD = 0;
        int above65CHD = 0;

        for (Patient patient : patients) {
            if (patient.getAge() < 50) {
                if (patient.getTenYearCHD() == 1) {
                    under65CHD++;
                } else {
                    under65NoCHD++;
                }
            } else {
                if (patient.getTenYearCHD() == 1) {
                    above65CHD++;
                } else {
                    above65NoCHD++;
                }
            }
        }

        // Appel de la méthode pour créer et afficher les graphiques
        createAndShowCharts("Under 50", "Above 50", under65NoCHD, under65CHD, above65NoCHD, above65CHD, "Analyse de l'âge par rapport à CHD",
                "Age Group", "Count", "Répartition des patients par âge");
    }

    // Méthode pour analyser les caractéristiques catégorielles "bpMeds vs CHD"
    public void analyzeBpMedsVsCHD(List<Patient> patients) {
        // Compter le nombre de patients prenant et ne prenant pas de médicaments contre l'hypertension, avec et sans risque de maladie coronarienne sur 10 ans
        int bpMedsNoCHD = 0;
        int bpMedsCHD = 0;
        int noBpMedsNoCHD = 0;
        int noBpMedsCHD = 0;

        for (Patient patient : patients) {
            if (patient.getBpMeds() == 1) {
                if (patient.getTenYearCHD() == 1) {
                    bpMedsCHD++;
                } else {
                    bpMedsNoCHD++;
                }
            } else {
                if (patient.getTenYearCHD() == 1) {
                    noBpMedsCHD++;
                } else {
                    noBpMedsNoCHD++;
                }
            }
        }

        // Appel de la méthode pour créer et afficher les graphiques
        createAndShowCharts("With BP Meds", "Without BP Meds", bpMedsNoCHD, bpMedsCHD, noBpMedsNoCHD, noBpMedsCHD, "Analyse de la prise de médicaments contre l'hypertension par rapport à CHD",
                "BP Meds Status", "Count", "Répartition des patients par prise de médicaments contre l'hypertension");
    }

    // Méthode pour analyser les caractéristiques catégorielles "totChol vs CHD" avec condition sur le taux de cholestérol total
    public void analyzeTotCholVsCHDWithCholCondition(List<Patient> patients) {
        // Compter le nombre de patients avec un taux de cholestérol total supérieur à 50, avec et sans risque de maladie coronarienne sur 10 ans
        int totCholGT50NoCHD = 0;
        int totCholGT50CHD = 0;
        int totCholLTE50NoCHD = 0;
        int totCholLTE50CHD = 0;

        for (Patient patient : patients) {
            // Vérifier si le taux de cholestérol total est supérieur à 50
            if (patient.getTotChol() < 250) {
                if (patient.getTenYearCHD() == 1) {
                    totCholGT50CHD++;
                } else {
                    totCholGT50NoCHD++;
                }
            } else {
                if (patient.getTenYearCHD() == 1) {
                    totCholLTE50CHD++;
                } else {
                    totCholLTE50NoCHD++;
                }
            }
        }

        // Appel de la méthode pour créer et afficher les graphiques
        createAndShowCharts("Total Cholesterol < 250", "Total Cholesterol > 250", totCholLTE50NoCHD, totCholLTE50CHD, totCholGT50NoCHD, totCholGT50CHD, "Analyse du taux de cholestérol total par rapport à CHD pour les patients avec un taux de cholestérol total supérieur à 250",
                "Total Cholesterol Status", "Count", "Répartition des patients par taux de cholestérol total (taux de cholestérol total > 250)");
    }

    // Méthode pour effectuer le test d'hypothèse sur l'âge et la présence de CHD
    public void performAgeCHDHypothesisTest(List<Patient> patients) {
        // Initialisation des tableaux d'observation
        long[][] observed = new long[2][2];

        // Compter le nombre de patients de moins de 65 ans et de 65 ans ou plus avec et sans CHD
        int under65NoCHD = 0;
        int under65CHD = 0;
        int above65NoCHD = 0;
        int above65CHD = 0;

        for (Patient patient : patients) {
            if (patient.getAge() < 65) {
                if (patient.getTenYearCHD() == 1) {
                    under65CHD++;
                } else {
                    under65NoCHD++;
                }
            } else {
                if (patient.getTenYearCHD() == 1) {
                    above65CHD++;
                } else {
                    above65NoCHD++;
                }
            }
        }

        // Remplir le tableau d'observation
        observed[0][0] = under65NoCHD;
        observed[0][1] = under65CHD;
        observed[1][0] = above65NoCHD;
        observed[1][1] = above65CHD;

        // Créer un objet pour le test de chi-deux
        ChiSquareTest chiSquareTest = new ChiSquareTest();

        // Effectuer le test de chi-deux
        double pValue = chiSquareTest.chiSquareTest(observed);

        // Afficher le résultat du test
        System.out.println("Test d'hypothèse :");
        System.out.println("Hypothèse nulle : L'âge et la CHD sont indépendants.");
        System.out.println("Hypothèse alternative : L'âge et la CHD sont dépendants.");
        System.out.println("p-value : " + pValue);

        // Interpréter le résultat du test
        if (pValue < 0.05) {
            System.out.println("La valeur p est significativement inférieure à 0,05, donc nous rejetons l'hypothèse nulle.");
        } else {
            System.out.println("La valeur p n'est pas significativement inférieure à 0,05, donc nous ne rejetons pas l'hypothèse nulle.");
        }
    }



    //--------------------------------------------------------------------------------------------
    // Méthode pour compter le nombre d'éléments non nuls (non NaN) dans une liste de patients
    public int getNonNullCount(List<Patient> data, String attribute) {
        int count = 0;
        for (Patient patient : data) {
            switch (attribute) {
                case "id":
                    if (!Double.isNaN(patient.getId()))
                        count++;
                    break;
                case "education":
                    if (!Double.isNaN(patient.getEducation()))
                        count++;
                    break;
                case "sex":
                    if (patient.getSex() != null)
                        count++;
                    break;
                case "age":
                    if (!Double.isNaN(patient.getAge()))
                        count++;
                    break;
                case "is_smoking":
                    if (patient.getIsSmoking() != null)
                        count++;
                    break;
                case "cigsPerDay":
                    if (!Double.isNaN(patient.getCigsPerDay()))
                        count++;
                    break;
                case "BPMeds":
                    if (!Double.isNaN(patient.getBpMeds()))
                        count++;
                    break;
                case "prevalentStroke":
                    if (!Double.isNaN(patient.getPreleventStroke()))
                        count++;
                    break;
                case "prevalentHyp":
                    if (!Double.isNaN(patient.getPrevalentHyp()))
                        count++;
                    break;
                case "diabetes":
                    if (!Double.isNaN(patient.getDiabetes()))
                        count++;
                    break;
                case "totChol":
                    if (!Double.isNaN(patient.getTotChol()))
                        count++;
                    break;
                case "sysBP":
                    if (!Double.isNaN(patient.getSysBP()))
                        count++;
                    break;
                case "diaBP":
                    if (!Double.isNaN(patient.getDiaBP()))
                        count++;
                    break;
                case "BMI":
                    if (!Double.isNaN(patient.getBMI()))
                        count++;
                    break;
                case "heartRate":
                    if (!Double.isNaN(patient.getHeartRate()))
                        count++;
                    break;
                case "glucose":
                    if (!Double.isNaN(patient.getGlucose()))
                        count++;
                    break;
                case "TenYearCHD":
                    if (!Double.isNaN(patient.getTenYearCHD()))
                        count++;
                    break;
                default:
                    break;
            }
        }
        return count;
    }

    // Méthode pour compter le nombre d'éléments NaN dans une liste de patients
    public int getNaNCount(List<Patient> data, String attribute) {
        int count = 0;
        for (Patient patient : data) {
            switch (attribute) {
                case "id":
                    if (Double.isNaN(patient.getId()))
                        count++;
                    break;
                case "education":
                    if (Double.isNaN(patient.getEducation()))
                        count++;
                    break;
                case "age":
                    if (Double.isNaN(patient.getAge()))
                        count++;
                    break;
                case "cigsPerDay":
                    if (Double.isNaN(patient.getCigsPerDay()))
                        count++;
                    break;
                case "BPMeds":
                    if (Double.isNaN(patient.getBpMeds()))
                        count++;
                    break;
                case "prevalentStroke":
                    if (Double.isNaN(patient.getPreleventStroke()))
                        count++;
                    break;
                case "prevalentHyp":
                    if (Double.isNaN(patient.getPrevalentHyp()))
                        count++;
                    break;
                case "diabetes":
                    if (Double.isNaN(patient.getDiabetes()))
                        count++;
                    break;
                case "totChol":
                    if (Double.isNaN(patient.getTotChol()))
                        count++;
                    break;
                case "sysBP":
                    if (Double.isNaN(patient.getSysBP()))
                        count++;
                    break;
                case "diaBP":
                    if (Double.isNaN(patient.getDiaBP()))
                        count++;
                    break;
                case "BMI":
                    if (Double.isNaN(patient.getBMI()))
                        count++;
                    break;
                case "heartRate":
                    if (Double.isNaN(patient.getHeartRate()))
                        count++;
                    break;
                case "glucose":
                    if (Double.isNaN(patient.getGlucose()))
                        count++;
                    break;
                case "TenYearCHD":
                    if (Double.isNaN(patient.getTenYearCHD()))
                        count++;
                    break;
                default:
                    break;
            }
        }
        return count;
    }




}
