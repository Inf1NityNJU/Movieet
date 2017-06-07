package moviereview.service.impl;

import moviereview.bean.CountryScoreInYearBean;
import moviereview.repository.CountryRepository;
import moviereview.repository.CountryScoreInYearRepository;
import moviereview.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kray on 2017/6/7.
 */
@Service
public class AnalysisServiceImpl implements AnalysisService {

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    CountryScoreInYearRepository countryScoreInYearRepository;

    public CountryScoreInYearBean getCountryScoreInYearOfCountry(int countryid) {
        String countryName = countryRepository.findCountryByCountryId(countryid);
        List<Integer> yearList = new ArrayList<Integer>();
        List<Double> scoreList = new ArrayList<Double>();
        for (int year = 1970; year <= 2017; year++) {
            yearList.add(year);
            scoreList.add(countryScoreInYearRepository.findCountryScoreInYear(countryid, year));
        }
        return new CountryScoreInYearBean(countryName, yearList, scoreList);
    }

}