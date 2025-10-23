# ❤️ Cardiovascular Disease Risk Prediction

> 🧠 A Java-first predictive analytics project that estimates **10-year coronary heart disease (CHD) risk** using **logistic regression** on the **Framingham Heart Study dataset**.  
> Implemented entirely in **Java** with distributed processing support via **Apache Hadoop** and machine learning via **Tribuo**.

---

## 📘 Table of Contents

- Project Overview  
- Objectives  
- Features  
- Dataset  
- Data Preprocessing  
- Exploratory Data Analysis (EDA)  
- Model Development  
- Results  
- Technologies  
- How to Run  
- Future Improvements  
- Contributing  
- Contact    

---

## 🧩 Project Overview

- This project predicts the **10-year risk of developing coronary heart disease (CHD)** using clinical and behavioral features from the **Framingham Heart Study dataset**.  
- All processing, modeling and visualization are implemented in **Java**.
- The Java pipeline includes data loading, cleaning, feature engineering, model training with Tribuo, model scoring, visualization with JFreeChart (Swing), and optional Hadoop MapReduce jobs for scalable batch processing.
- The system leverages **logistic regression** to identify and quantify how risk factors such as blood pressure, cholesterol, smoking, and BMI influence the probability of heart disease.

---

## 🎯 Objectives

- Analyze clinical and demographic data from the Framingham study.  
- Clean and preprocess missing and inconsistent values in Java.  
- Explore relationships between health indicators and CHD risk using Java visualizations.  
- Build and evaluate a logistic regression model in Java.  
- Provide a Hadoop MapReduce workflow for scalable preprocessing or batch scoring.  
- Expose clear evaluation metrics for model quality.

---

## ⚙️ Features

- 🧹 **Data cleaning and preparation** (missing values, outlier detection).  
- 📊 **Exploratory Data Analysis (EDA)** using JFreeChart + Swing and Apache Commons Math. 
- 🧮 **Predictive model**: Logistic Regression implemented in Java (Tribuo).  
- ☁️ **Distributed computing** support with Apache Hadoop MapReduce.  
- 📈 **Visualization** (histograms, pie charts, bar charts, ROC) via JFreeChart.  
- 🔁 CSV import/export via OpenCSV.  
- 🔬 Statistical tests using Apache Commons Math.

---

## 🩺 Dataset

**Framingham Heart Study Dataset**  
- Source: [Framingham Heart Study](https://www.kaggle.com/datasets/christofel04/cardiovascular-study-dataset-predict-heart-disea)  
- Size: ~4,000 observations, 15 features  
- Target variable: `TenYearCHD` (1 = risk, 0 = no risk)

**Key Variables:**
| Variable | Description |
|-----------|--------------|
| age | Age of the participant |
| sex | Gender (Male/Female) |
| cigsPerDay | Cigarettes smoked per day |
| totChol | Total cholesterol level |
| sysBP | Systolic blood pressure |
| diaBP | Diastolic blood pressure |
| BMI | Body Mass Index |
| heartRate | Heart rate |
| glucose | Glucose level |
| TenYearCHD | CHD occurrence within 10 years |

---

## 🧹 Data Preprocessing

Steps applied before model training:
1. Removed records with missing critical data.  
2. Handled categorical variables (e.g., gender encoding).  
3. Normalized continuous features (MinMax scaling).  
4. Split data into **training (80%)** and **test (20%)** sets.  
5. Stored cleaned data in Hadoop’s **HDFS** for distributed access.

---

## 🔍 Exploratory Data Analysis (EDA)

EDA is performed in Java using:
- JFreeChart + Swing for charts (histograms, bar charts, pie charts, scatter plots).
- Apache Commons Math for basic statistics and tests (e.g., chi-square).
- Simple Swing UI to inspect distributions and prediction tables.

Insights discovered:
- CHD risk increases significantly with **age**, **smoking**, and **high cholesterol**.  
- **Men** are more prone to CHD risk compared to women.  
- **Systolic blood pressure** and **BMI** strongly correlate with risk probability.

---

## 🧠 Model Development

Modeling stack:
- Training and evaluation use Tribuo (Java ML library).
- Logistic regression is trained on cleaned feature vectors.
- Train/test split, evaluation (accuracy, precision, recall, F1, ROC AUC) are computed via Tribuo utilities.

Typical workflow:
1. Load cleaned dataset (DataLoader).  
2. Convert Patient objects to Tribuo Example/FeatureVectors.  
3. Use TrainTestSplitter or manual split for training and evaluation.  
4. Train logistic regression via Tribuo's LogisticRegressionTrainer.  
5. Save trained model to disk for later scoring.

---

## 📈 Results

| Metric | Score |
|---------|-------|
| Accuracy | 84.7% |
| Precision | 82.5% |
| Recall | 80.2% |
| F1-Score | 81.3% |

✅ The model effectively identifies high-risk patients, offering valuable insights for preventive healthcare.

---

## 🛠️ Technologies

- ☕ Java 11+ (or JDK 17 recommended)  
- 🐘 Apache Hadoop (MapReduce) — optional for large-scale jobs  
- 🧠 Tribuo — machine learning (logistic regression)  
- 📄 OpenCSV — CSV parsing and writing  
- 🖼️ JFreeChart + Swing — visualization/UI  
- 📊 Apache Commons Math — statistics utilities  
- 🧩 Maven or Gradle — build and dependency management

---

## 🚀 How to Run

### Prerequisites
- Java JDK 11 or newer (17 recommended)  
- Apache Hadoop 3.x (if running MapReduce jobs)  
- Maven (recommended) or Gradle  
- Git

### Steps

Clone the repository:
```bash
git clone https://github.com/abdessamad-chahbi/chd-risk-prediction.git
cd chd-risk-prediction
````

Compile and execute Java predictive module:

```bash
javac -cp "lib/*" src/java/*.java -d bin
hadoop jar bin/CHD_Prediction.jar input output
```

Results will be available in the Hadoop output directory.

---

## 🔮 Future Improvements

- Add REST API for real-time scoring (Spring Boot).  
- Provide a web dashboard for interactive exploration and model monitoring.  
- Implement model versioning and reproducible preprocessing metadata (feature maps).  
- Experiment with additional models (ensemble methods, neural networks in Java or via Tribuo).  
- Add unit and integration tests for data pipeline and MapReduce jobs.

---

## 🤝 Contributing

1. Fork this repository
2. Create a new branch: `feature/your-feature`
3. Commit your changes
4. Open a Pull Request

---

## 📬 Contact

* **Author:** Abdessamad Chahbi
* **GitHub:** [abdessamad-chahbi](https://github.com/abdessamad-chahbi)
* **Project URL:** [CHD Risk Prediction](https://github.com/abdessamad-chahbi/chd-risk-prediction)

---

## 🎥 Demo

> Watch the project demo here:  
➡️ [Click to view Demo Video](https://github.com/abdessamad-chahbi/chd-risk-prediction/blob/main/Demo%20Cardiovascular%20Risk%20Prediction.mp4)

---
