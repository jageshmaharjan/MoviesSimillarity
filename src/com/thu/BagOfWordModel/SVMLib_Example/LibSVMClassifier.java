package com.thu.BagOfWordModel.SVMLib_Example;

import java.io.File;
import libsvm.*;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.data.FileHandler;

/**
 * Created by jugs on 5/4/17.
 */
public class LibSVMClassifier
{
    public static void main(String[] args) throws Exception
    {
        String path = "/home/jugs/IdeaProjects/MoviesSimillarity/src/com/thu/BagOfWordModel/Algorithms/test_svm_data.txt";
        Dataset data = FileHandler.loadDataset(new File(path),2,",");

        Classifier svm = new LibSVM();
        svm.buildClassifier(data);

        Dataset dataForClassification = FileHandler.loadDataset(new File(path),2,",");

        int correct = 0;
        int incorrect = 0;

        for (Instance instance : dataForClassification)
        {
            Object predictedClassifiedValue = svm.classify(instance);
            Object actualClassifiedvalue = instance.classValue();
            if (predictedClassifiedValue.equals(actualClassifiedvalue))
                correct ++;
            else
                incorrect ++;
        }
        System.out.println("Correctly predicted "+ correct);
        System.out.println("inCorrectly predicted "+ incorrect);
    }
}
