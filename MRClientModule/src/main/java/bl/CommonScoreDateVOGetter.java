package bl;

import bl.date.DateChecker;
import bl.date.DateFormatter;
import bl.date.DateUtil;
import po.ReviewPO;
import vo.ScoreDateVO;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vivian on 2017/3/28.
 */
public class CommonScoreDateVOGetter {
    ScoreDateVO scoreDateVO;

    List<ReviewPO> reviewPOList;
    String startDate;
    String endDate;
    DateChecker dateChecker;
    DateFormatter dateFormatter;
    DateUtil dateUtil;

    public CommonScoreDateVOGetter(List<ReviewPO> reviewPOList, String startDate, String endDate, DateChecker dateChecker, DateFormatter dateFormatter, DateUtil dateUtil) {
        this.reviewPOList = reviewPOList;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dateChecker = dateChecker;
        this.dateFormatter = dateFormatter;
        this.dateUtil = dateUtil;
    }

    public ScoreDateVO getScoreDateVO(){
        scoreDateVO = getEmptyScoreDateVO();
        if (reviewPOList.size()==0){
            return scoreDateVO;
        }else {
            return getNotEmptyScoreDateVO();
        }
    }

    private ScoreDateVO getEmptyScoreDateVO(){
        List<String> dates = new ArrayList<>();

        LocalDate startDate = dateFormatter.parse(this.startDate);
        LocalDate endDate = dateFormatter.parse(this.endDate);

        while (!startDate.isAfter(endDate)) {
            dates.add(dateFormatter.format(startDate));
            startDate = dateUtil.plus(startDate, 1);
        }

        List<Double> scores = new ArrayList<>();
        return new ScoreDateVO(dates, scores);
    }

    private ScoreDateVO getNotEmptyScoreDateVO(){
        List<String> dates = scoreDateVO.dates;

        /**
         * 由于要求是：展示电影综合评分随着时间的变化，因此每一个时间节点都需要一个List来记录这个时间所有的评分，
         * 再根据所有的评分计算这个时间节点的综合评分。
         * 所以此处用了两个list嵌套，外层的list用于表示改时间段内所有的时间节点，内层list表示每一个时间节点对应的一组评分
         */
        List<ArrayList<Integer>> allScoresOnCertainDates = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < dates.size(); i++) {
            ArrayList<Integer> tempScores = new ArrayList<>();
            allScoresOnCertainDates.add(tempScores);
        }

        LocalDate startDate = dateFormatter.parse(this.startDate);
        for (ReviewPO reviewPO : reviewPOList) {
            LocalDate date =
                    Instant.ofEpochMilli(reviewPO.getTime() * 1000l).atZone(ZoneId.systemDefault()).toLocalDate();
            if (dateChecker.check(date)) {
                int index = dateUtil.between(startDate, date);
                int score = reviewPO.getScore();
                ArrayList<Integer> tempScores = allScoresOnCertainDates.get(index);
                tempScores.add(score);
                allScoresOnCertainDates.set(index, tempScores);
            }
        }

        //计算各个时间节点的综合评分,若没有评分，默认为null
        List<Double> scores = scoreDateVO.scores;
        for (int i=0;i<allScoresOnCertainDates.size();i++){
            if (allScoresOnCertainDates.get(i).size()==0){
                scores.add(null);
            }else {
                Double score = 0.0;
                List<Integer> currentScores = allScoresOnCertainDates.get(i);
                for (int j=0; j<currentScores.size();j++){
                    score += currentScores.get(j);
                }
                score = score/currentScores.size();
                // 保留一位小数
                DecimalFormat df = new DecimalFormat("#.#");
                score = Double.parseDouble(df.format(score));
                scores.add(score);
            }
        }

        return new ScoreDateVO(dates, scores);
    }
}
