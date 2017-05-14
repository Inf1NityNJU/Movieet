package moviereview.service.impl;

import moviereview.model.Movie;
import moviereview.model.Page;
import moviereview.repository.MovieRepository;
import moviereview.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by Kray on 2017/3/7.
 */
@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    /**
     * @param keyword  关键字
     * @param orderBy  按什么排序
     * @param sortType asc 还是 desc
     * @param size     每页大小
     * @param page     第几页
     * @return Movie 分页列表
     */
    public Page<Movie> findMoviesByKeyword(String keyword, String orderBy, String sortType, int size, int page) {
        //        Sort sort = new Sort(sortType, asc);
        ArrayList<Movie> movies = (ArrayList<Movie>) movieRepository.findMoviesByTitleLike(keyword);
        if (movies == null) {
            return new Page<Movie>();
        }
//        movies.sort(MovieComparatorFactory.sortMoviesBySortType(sort.toString()));
//
        if (page * size > movies.size()) {
            return new Page<Movie>();
        } else {
            return new Page<Movie>(
                    page,
                    size,
                    orderBy,
                    sortType,
                    movies.size() + "",
                    movies.subList(page * size, Math.min((page + 1) * size, movies.size())));
        }
    }

//    /**
//     * 根据通过搜索电影分类tag得到按照时间排序的相关电影列表
//     *
//     * @param tags          电影分类tag
//     * @param movieSortType 决定时间按由近到远还是由远到近排序
//     * @return 如果属于该电影分类tag的电影存在，返回该分类按照时间排序的movieVO列表
//     * 否则返回null
//     */
//    public Page<Movie> findMoviesByTags(String[] tags, int page, String movieSortType, boolean asc) {
//        Sort sort = new Sort(movieSortType, asc);
//        Set<Movie> tempSet = new HashSet<>();
//
//        tempSet.addAll(movieDao.findMoviesByTags(tags));
//
//        ArrayList<Movie> movies = new ArrayList<>();
//        movies.addAll(tempSet);
//        movies.sort(MovieComparatorFactory.sortMoviesBySortType(sort.toString()));
//
//        if (page * 10 > movies.size()) {
//            return new Page<Movie>();
//        } else {
//            return new Page<Movie>(
//                    page,
//                    10,
//                    sort.getOrder(),
//                    sort.getAsc(),
//                    movies.size() + "",
//                    movies.subList(page * 10, Math.min((page + 1) * 10, movies.size())));
//        }
//    }
}
