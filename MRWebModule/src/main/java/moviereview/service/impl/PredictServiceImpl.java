//package moviereview.service.impl;
//
//import moviereview.bean.PlotDataBean;
//import moviereview.bean.PredictBean;
//import moviereview.bean.PredictResultBean;
//import moviereview.repository.ActorRepository;
//import moviereview.repository.DirectorRepository;
//import moviereview.repository.GenreRepository;
//import moviereview.service.PredictService;
//import moviereview.util.FileTransaction;
//import org.apache.commons.math3.distribution.TDistribution;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import weka.classifiers.trees.M5P;
//import weka.core.*;
//import weka.core.converters.ConverterUtils.DataSource;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by SilverNarcissus on 2017/5/31.
// */
//@Service
//public class PredictServiceImpl implements PredictService {
//    /**
//     * 区间预测所得出的点
//     */
//    private static final int POINT_NUMBER = 500;
//    /**
//     * 模型位置
//     */
//    private static final String MODEL_LOCATION = "TEST";
//    /**
//     * 数据集位置
//     */
//    private static final String DATA_SET_LOCATION = "/Users/SilverNarcissus/Desktop/weka/pre2.arff";
//
//    @Autowired
//    private ActorRepository actorRepository;
//
//    @Autowired
//    private DirectorRepository directorRepository;
//
//    @Autowired
//    private GenreRepository genreRepository;
//
//    /**
//     * 数据集
//     */
//    private Instances data;
//
//    /**
//     * 模型集合
//     */
//    private List<M5P> models;
//
//
//    public PredictServiceImpl() {
//        getDataSet();
//        //buildModel();
//        download();
//        loadModel();
//    }
//
//    /**
//     * 输入一个含有演员及导演信息的样本，进行预测
//     *
//     * @param predictBean 样本
//     * @return 预测结果
//     */
//    public PredictResultBean wekaPredict(PredictBean predictBean) {
//        return doPredict(getActorsFactor(predictBean.getActors()),
//                getDirectorsFactor(predictBean.getDirectors()),
//                getGenres(predictBean.getGenres()));
//    }
//
//    @Override
//    public List<PlotDataBean> intervalEstimation(PredictBean predictBean) {
//        int sampleNum = 0;
//        double sampleSum = 0;
//        double sampleSquareSum = 0;
//
//        //分析类型
//        for (int genreId : predictBean.getGenres()) {
//
//        }
//
//        //分析演员
//        for (int actorId : predictBean.getActors()){
//
//        }
//
//        //分析导演
//        for (int actorId : predictBean.getDirectors()){
//
//        }
//
//        double average = sampleSum / sampleNum;
//        double temp = sampleNum / (sampleNum - 1);
//        return estimateAverage(sampleNum, average,
//                temp * (sampleSquareSum / sampleNum - Math.pow(average, 2)));
//    }
//
//    /**
//     * 输入一个含有演员及导演信息的样本，进行预测
//     *
//     * @param actorFactor    演员因子
//     * @param directorFactor 导演因子
//     * @return 预测得分
//     */
//    private PredictResultBean doPredict(double actorFactor, double directorFactor, List<String> genres) {
//        //初始化预测值数组
//        List<Double> predictValue = new ArrayList<>();
//
//        //初始化属性位置
//        Attribute actorFactorAtt = new Attribute("actor_factor", 0);
//        Attribute directorFactorAtt = new Attribute("director_factor", 1);
//        Attribute genreAtt = new Attribute("genre", (ArrayList<String>) null, 3);
//        //初始化需要预测的样本
//        Instance sample = new DenseInstance(4);
//        sample.setDataset(data);
//        sample.setValue(actorFactorAtt, actorFactor);
//        sample.setValue(directorFactorAtt, directorFactor);
//
//        //使用不同的模型预测不同的值，如评分、票房等
//        for (M5P model : models) {
//            //用来记录各类型的预测得分和
//            double sum = 0;
//            //对每个类型进行预测，最后取平均值
//            for (String genre : genres) {
//                sample.setValue(genreAtt, genre);
//                //
//                try {
//                    sum += model.classifyInstance(sample);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            predictValue.add(sum / genres.size());
//        }
//        //假数据
//        for (int i = 0; i < 4; i++) {
//            predictValue.add(233.0);
//        }
//        //
//        return new PredictResultBean(predictValue);
//    }
//
//    /**
//     * 建立模型，仅需要在数据集修改后调用一次
//     */
//    private void buildModel() {
//        M5P newModel = new M5P();
//        String[] options = {"-M", "4.0"};
//        try {
//            newModel.setOptions(options);
//            newModel.buildClassifier(data);
//            SerializationHelper.write(MODEL_LOCATION, newModel);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        FileTransaction.upload();
//    }
//
//    /**
//     * 读取分类模型
//     */
//    private void loadModel() {
//        models = new ArrayList<>();
//        try {
//            models.add((M5P) SerializationHelper.read(MODEL_LOCATION));
//            //System.out.println(modelForScoreEn);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 获取数据集
//     */
//    private void getDataSet() {
//        try {
//            DataSource source = new DataSource(DATA_SET_LOCATION);
//            data = source.getDataSet();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        data.setClassIndex(data.numAttributes() - 2);
//        System.out.println("Out!");
//    }
//
//    private void download() {
//        FileTransaction.download();
//        System.out.println("Out!");
//    }
//
//    /**
//     * 获取演员因子
//     */
//    private double getActorsFactor(List<Integer> actors) {
//        double sum = 0;
//        for (int id : actors) {
//            sum = actorRepository.findActorFactors(id);
//        }
//        return sum / actors.size();
//    }
//
//    /**
//     * 获取导演因子
//     */
//    private double getDirectorsFactor(List<Integer> directors) {
//        double sum = 0;
//        for (int id : directors) {
//            sum = directorRepository.findDirectorFactors(id);
//        }
//        return sum / directors.size();
//    }
//
//    /**
//     * 获取类型字符串
//     */
//    private List<String> getGenres(List<Integer> genres) {
//        List<String> result = new ArrayList<>(genres.size());
//        for (int id : genres) {
//            result.add(genreRepository.findGenreById(id));
//        }
//        return result;
//    }
//
//    /**
//     * 估计得分平均值的分布
//     *
//     * @param number 样本数量
//     * @param avg    样本平均值
//     * @param s      样本修正方差
//     * @return t分布点集
//     */
//    private List<PlotDataBean> estimateAverage(int number, double avg, double s) {
//        TDistribution tDistribution = new TDistribution(number - 1);
//        List<PlotDataBean> result = new ArrayList<>(POINT_NUMBER);
//
//        for (double i = 0; i < POINT_NUMBER; i++) {
//            double realX = -5 + (i * 10) / POINT_NUMBER;
//            double x = realX * s / Math.sqrt(number) + avg;
//            double y = tDistribution.density(realX);
//
//            result.add(new PlotDataBean(x, y));
//        }
//
//        return result;
//    }
//
//}
