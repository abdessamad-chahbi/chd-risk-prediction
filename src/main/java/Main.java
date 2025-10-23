import algorithm.LogisticRegression;
import data.DataLoader;
import data.DataPreparation;
import model.Patient;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Chemins des fichiers CSV
        String trainFilePath = "src/main/resources/dataset/train.csv";
        String testFilePath = "src/main/resources/dataset/test.csv";
        String outputTrainFilePath = "src/main/resources/dataset/train_processed.csv";
        String outputTestFilePath = "src/main/resources/dataset/test_processed.csv";

        // Création d'un DataLoader
        DataLoader dataLoader = new DataLoader(trainFilePath, testFilePath);
        DataLoader dataLoaderAfterPrep = new DataLoader(outputTrainFilePath, outputTestFilePath);

        try {
            // Chargement des données d'entraînement
            List<Patient> trainData = dataLoader.loadTrainData();

            // Chargement des données de test
            List<Patient> testData = dataLoader.loadTestData();

            // Création d'un DataPreparation
            DataPreparation dataPreparation = new DataPreparation();

            // Affichage le nombre de données existent et manquantes sous forme de table
            // dataPreparation.DataDescriptionFrameCount(trainData);

            // Affichage des données sous forme de table
            // new DataDescriptionFrame(trainData, testData);
            // new DataDescriptionFrame(trainData, testData, 30);

            // Affichage le nombre de données existent et manquantes apres preparation sous forme de table
            List<Patient> trainDataAfterPrep = dataLoaderAfterPrep.loadTrainData();
            dataPreparation.DataDescriptionFrameCount(trainDataAfterPrep);;

            // Affichage des données apres preparation  sous forme de table
             new DataDescriptionFrame(trainDataAfterPrep, testData, 30);

            // Pour afficher les histogrammes
           dataPreparation.analyzeCategoricalFeatures(trainData);

            // Remplacer les valeurs manquantes
            // dataPreparation.replaceMissingValues(trainFilePath, outputTrainFilePath);
           // dataPreparation.replaceMissingValues(testFilePath, outputTestFilePath);

            // Entraîner le modèle de régression logistique
            LogisticRegression logisticRegression = new LogisticRegression();
            // new LogisticRegressionTest();

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des données : " + e.getMessage());
        }

    }
}
