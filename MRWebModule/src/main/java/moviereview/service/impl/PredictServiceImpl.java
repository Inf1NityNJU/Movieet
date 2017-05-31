package moviereview.service.impl;

import java.util.ArrayList;

import weka.classifiers.trees.M5P;
import weka.core.*;
import weka.core.converters.ConverterUtils.*;

/**
 * Created by SilverNarcissus on 2017/5/31.
 */
public class PredictServiceImpl {
    private static final String MODEL_LOCATION = "/Users/SilverNarcissus/Desktop/weka/m5p.model";

    private static final String DATASET_LOCATION = "/Users/SilverNarcissus/Desktop/weka/pre2.csv";

    private Instances data;

    private M5P model;


    public PredictServiceImpl() {
        getDataSet();
        loadModel();
    }

    /**
     * 输入一个含有演员及导演信息的样本，进行预测
     *
     * @param actorFactor    演员因子
     * @param directorFactor 导演因子
     * @return 预测得分
     */
    public double predict(double actorFactor, double directorFactor, String genre) {
        Instance sample = new DenseInstance(4);
        sample.setDataset(data);

        Attribute actorFactorAtt = new Attribute("actor_factor", 0);
        Attribute directorFactorAtt = new Attribute("director_factor", 1);
        Attribute scoreAtt = new Attribute("score", 2);
        Attribute genreAtt = new Attribute("genre", (ArrayList<String>) null, 3);
        //sample.setValue(id, 1);
        sample.setValue(actorFactorAtt, actorFactor);
        sample.setValue(directorFactorAtt, directorFactor);
        sample.setValue(genreAtt, genre);
        //sample.setValue(score, 10);


        //
        double result = 0;
        try {
            result = model.classifyInstance(sample);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 建立模型，仅需要在数据集修改后调用一次
     */
    private void buildModel() {
        M5P newModel = new M5P();
        String[] options = {"-M", "4.0"};
        try {
            newModel.setOptions(options);
            newModel.buildClassifier(data);
            SerializationHelper.write(MODEL_LOCATION, newModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取分类模型
     */
    private void loadModel() {
        try {
            model = (M5P) SerializationHelper.read(MODEL_LOCATION);
            //System.out.println(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据集
     */
    private void getDataSet() {
        try {
            DataSource source = new DataSource(DATASET_LOCATION);
            data = source.getDataSet();
        } catch (Exception e) {
            e.printStackTrace();
        }
        data.setClassIndex(data.numAttributes() - 2);
    }
}
