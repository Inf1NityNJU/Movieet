package moviereview.service.impl;

import moviereview.bean.EstimateResultBean;
import moviereview.bean.PredictBean;
import moviereview.bean.PredictResultBean;

/**
 * Created by Kray on 2017/6/7.
 */
public class PredictDescriptionFactory {

    private static PredictDescriptionFactory predictDescriptionFactory;

    static PredictDescriptionFactory getPredictDescriptionFactory(){
        if(predictDescriptionFactory == null){
            predictDescriptionFactory = new PredictDescriptionFactory();
        }
        return predictDescriptionFactory;
    }

    private PredictDescriptionFactory(){

    }

    String getPredictDescription(PredictResultBean predictResultBean){
        return predictResultBean.getBoxOffice() + "";
    }

    String getEstimateDescription(EstimateResultBean estimateResultBean){
        return estimateResultBean.getBoxOffice() + "";
    }



}
