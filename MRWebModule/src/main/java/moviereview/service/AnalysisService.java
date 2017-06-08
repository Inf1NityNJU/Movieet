package moviereview.service;

import moviereview.bean.CountryCountBean;
import moviereview.bean.CountryScoreInYearBean;

import java.util.List;

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
    public List<CountryScoreInYearBean> getCountryScoreInYearOfCountry(int countryid);

    /**
     * 得到国家高于/低于豆瓣/IMDB 的分数列表
     *
     * @param countryid
     * @return
     */
    public List<CountryCountBean> getCountryCountOfCountry(int countryid);

}
