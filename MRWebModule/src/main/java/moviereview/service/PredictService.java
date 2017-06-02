package moviereview.service;

import moviereview.bean.EstimateResultBean;
import moviereview.bean.PredictBean;
import moviereview.bean.PredictResultBean;

/**
 * Created by SilverNarcissus on 2017/5/31.
 */
public interface PredictService {
    /**
     * 输入一个含有演员、导演及类型信息的样本，进行预测
     * 使用weka进行预测
     *
     * @param predictBean 样本
     * @return 预测结果
     */
    public PredictResultBean wekaPredict(PredictBean predictBean);

    /**
     * 输入一个含有演员、导演及类型信息的样本，进行预测
     * 使用区间估计进行预测
     *
     * @param predictBean 样本
     * @return 预测结果
     */
    public EstimateResultBean intervalEstimation(PredictBean predictBean);
}
