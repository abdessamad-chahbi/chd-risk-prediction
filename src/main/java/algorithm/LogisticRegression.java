package algorithm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

import com.opencsv.CSVWriter;
import org.tribuo.*;
import org.tribuo.classification.evaluation.LabelEvaluation;
import org.tribuo.classification.evaluation.LabelEvaluator;
import org.tribuo.classification.sgd.linear.LogisticRegressionTrainer;
import org.tribuo.data.csv.CSVLoader;
import org.tribuo.classification.*;
import org.tribuo.evaluation.TrainTestSplitter;

public class LogisticRegression {

    public LogisticRegression() throws IOException {

        LabelFactory labelFactory = new LabelFactory();
        CSVLoader<Label> csvLoader = new CSVLoader<>(labelFactory);

        // Chemins des fichiers CSV
        // String trainFilePath = "src/main/resources/dataset/train.csv";
        String trainFilePath = "src/main/resources/dataset/train_processed.csv";

        // Chargement des données CSV pour l'entraînement
        DataSource<Label> trainSource = csvLoader.loadDataSource(Paths.get(trainFilePath), "TenYearCHD");

        // Séparation des données en jeu d'entraînement et de test
        TrainTestSplitter<Label> dataSplitter = new TrainTestSplitter<>(trainSource, 0.7, 4L);

        MutableDataset trainingDataset = new MutableDataset<>(dataSplitter.getTrain());
        MutableDataset testingDataset = new MutableDataset<>(dataSplitter.getTest());

        // Entraînement du modèle
        Model<Label> diseaseModel = trainModel(trainingDataset);

        // Évaluation du modèle sur les données de test
        evaluateModel(diseaseModel, testingDataset);

        // Prédiction sur les données de test (Le fichier : test.csv)
        predict(diseaseModel);
        storePredictions(diseaseModel);
    }

    // Entraînement du modèle
    public Model<Label> trainModel(MutableDataset myDatasetTrain) {
        // Initialisation du modèle de régression logistique
        Trainer<Label> myTrainer = new LogisticRegressionTrainer();
        Model<Label> myModel =  myTrainer.train(myDatasetTrain);
        return myModel;
    }

    // Évaluation du modèle sur les données de test pour obtenir la courbe ROC
    public void evaluateModel(Model<Label> myModel, MutableDataset myDatasetTest) {
        // Évaluation du modèle
        LabelEvaluator evaluator = new LabelEvaluator();
        LabelEvaluation evaluation = evaluator.evaluate(myModel, myDatasetTest);
        System.out.println("Évaluation du modèle sur les données de test :");
        System.out.println(evaluation.toString());
        // System.out.println(evaluation.getConfusionMatrix().toString());
    }

    // Faire des prédictions sur les données de test
    public void predict(Model<Label> myModel) throws IOException {
        // Chargement des données de test depuis le fichier CSV
        String testFilePath = "src/main/resources/dataset/test_processed.csv";
        CSVLoader<Label> csvLoader = new CSVLoader<>(new LabelFactory());
        DataSource<Label> testSource = csvLoader.loadDataSource(Paths.get(testFilePath),"id");

        int count = 0;
        // Prédiction pour chaque exemple dans les données de test
        for (Example<Label> example : testSource) {
            Prediction<Label> prediction = myModel.predict(example);
            String riskOfCHD = prediction.getOutput().getLabel(); // Obtenir le label de predection
            String patientID = prediction.getExample().getOutput().getLabel(); // Obtenir l'ID du patient
            if (riskOfCHD.equals("1")) {
                System.out.println("\nLe patient de l'ID " + patientID + " présente un risque de CHD à 10 ans.");
            } else if (riskOfCHD.equals("0")) {
                System.out.println("\nLe patient de l'ID " + patientID + " ne présente pas de risque de CHD à 10 ans.");
            }
            count++;
            if (count >= 10) {
                break; // Arrêter après avoir prédit les deux premières lignes
            }
        }
    }

    // Faire des prédictions sur les données de test et les stocker dans un fichier CSV
    public void storePredictions(Model<Label> myModel) throws IOException {
        // Chargement des données de test depuis le fichier CSV
        String testFilePath = "src/main/resources/dataset/test_processed.csv";
        CSVLoader<Label> csvLoader = new CSVLoader<>(new LabelFactory());
        DataSource<Label> testSource = csvLoader.loadDataSource(Paths.get(testFilePath),"id");

        // Créer un fichier CSV pour stocker les prédictions
        String outputFilePath = "src/main/resources/output/output_predictions.csv";
        CSVWriter writer = new CSVWriter(new FileWriter(outputFilePath));

        // Écrire l'en-tête du fichier CSV
        String[] header = {"PatientID", "PredictedTenYearCHD"};
        writer.writeNext(header);

        int count = 0;
        // Prédiction pour chaque exemple dans les données de test et écriture dans le fichier CSV
        for (Example<Label> example : testSource) {
            Prediction<Label> prediction = myModel.predict(example);
            String patientID = prediction.getExample().getOutput().getLabel(); // Obtenir l'ID du patient
            String predictedTenYearCHD = prediction.getOutput().getLabel(); // Obtenir la prédiction de TenYearCHD

            // Écrire la prédiction dans le fichier CSV
            writer.writeNext(new String[]{patientID, predictedTenYearCHD});
            count++;
            if (count >= 120) {
                break; // Arrêter après avoir prédit les deux premières lignes
            }
        }

        // Fermer le writer
        writer.close();
    }

//    // Prédiction pour un exemple personnalisé
//    public void predictCustomExample(Model<Label> myModel) {
//        // Créer un exemple personnalisé // id,age,education,sex,is_smoking,cigsPerDay,BPMeds,prevalentStroke,prevalentHyp,diabetes,totChol,sysBP,diaBP,BMI,heartRate,glucose
//        double[] features = {63, 1.0, 0, 0, 0.0, 0.0, 1, 1, 0, 273.0, 152.0, 70.0, 19.69, 80.0, 79.0};
//        DenseVector featureVector = new DenseVector(features);
//        Example<Label> customExample = new ListExample<>();
//
//        // Prédiction pour l'exemple personnalisé
//        Prediction<Label> prediction = myModel.predict(customExample);
//        String riskOfCHD = prediction.getOutput().getLabel(); // Obtenir le label de prédiction
//        String patientID = customExample.getMetadata().toString(); // Obtenir l'ID du patient
//
//        if (riskOfCHD.equals("1")) {
//            System.out.println("\nLe patient de l'ID " + patientID + " présente un risque de CHD à 10 ans.");
//        } else if (riskOfCHD.equals("0")) {
//            System.out.println("\nLe patient de l'ID " + patientID + " ne présente pas de risque de CHD à 10 ans.");
//        }
//    }


}
