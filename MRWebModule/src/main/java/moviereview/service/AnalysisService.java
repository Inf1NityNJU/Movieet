package moviereview.service;

import moviereview.bean.CountryScoreInYearBean;

/**
 * Created by Kray on 2017/6/7.
 */
public interface AnalysisService {
    /**
     * 得到国家根据年份不同的分数列表
     *
     * @param countryid 国家 id
     * @return
     */
    public CountryScoreInYearBean getCountryScoreInYearOfCountry(int countryid);

}
