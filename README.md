# Dynamic Branch Prediction using Machine Learning Techniques

This application contains an implementation of few neural network algorithm for dynamic branch prediction. The simulated algorithms are:
  - Perceptron
  - Perceptron with Global/Local history
  - Learning Vector Quantization Global/Local history
  - Support Vector Machine (SVM) Global
  - Support Vector Machine (SVM) Local
  - Naive Bayesian
  - Naive Bayesian with Set Associativity
  - Naive Bayesian (improved)

### Installation
Clone the repository:
```sh
$ git clone [git-repo-url] someFolder
$ cd someFolder
```
> **Note:** Make sure to check your installed Java version, because this repository needs Java 1.8 and your favorite IDE is configured to run that JDK.
```sh
$ java -version
```
> **Note:** Open the project with your IDE and make sure to download [traces] if you want to test it locally.

Running the project requires passing the program arguments. This can be done by opening `Main.java` from the main package. Depending on your IDE you need to edit configurations before running the `Main.java` file.
> **Note:** Configuration is commented in `Main.java` class so you need to follow instructions on how to simulate different algorithms.

Sample of a configuration from `Main.java` file: 
```
/**
 * To run the Predictors you must add two arguments:
 *
 * First: the path of the Trace file
 * Second: choose an algorithm (read below for more info)
 *
 * Algorithms:
 *  0 = Perceptron Predictor
 *  1 = Perceptron with Global and Local History Predictor
 *  2 = LVQ Predictor
 *  3 = SVM with Global History Predictor
 *  4 = SVM with Global and Local History Predictor
 *  5 = Naive Bayesian with Global History Predictor
 *  6 = Naive Bayesian with Set Associativity Predictor
 *  7 = Naive Bayesian with Local History Predictor
 */
```
And you can do this, which means run `LVQ` on trace `gcc.txt`: 
```
C:\Users\...\traces\gcc.txt 2
```
So the first argument is the trace you want to simulate and the second indicates the algorithm. After running the main file the results of execution are shown in console.

[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)

[git-repo-url]: <https://github.com/Elbani/DBPML.git>
[traces]: https://drive.google.com/folderview?id=0B9GfMW5KBEynUVZjd2VVTzJHcWs&usp=sharing

