package com.satti.service.serviceImpl;

import com.satti.entity.Movie;
import com.satti.entity.MovieDTO;
import com.satti.entity.MovieRow;
import com.satti.service.MovieService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MongoTemplate mongoTemplate;



    // 得到电影详细信息
    @Override
    public MovieDTO getMovieDetails(Integer id) {
        Movie movie = mongoTemplate.findById(id, Movie.class);
//        Movie movie=movieMapper.getMovieById(id);
        System.out.println(movie);
        MovieDTO movieDTO=new MovieDTO();
        if (movie != null) {
            movieDTO.setMovie(movie);
        }
        String url= null;
        if (movie != null) {
            url = movie.getLink();
        }
        try {
            // Call the Python script
            ProcessBuilder pb = new ProcessBuilder("py", "C:\\Users\\lingl\\Desktop\\beautiful_soup\\test\\scrape.py", url);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), "gb2312"));

            String result = in.lines().collect(Collectors.joining("\n"));
            in.close();
            process.waitFor();

            // Split the result into parts
            String[] results = result.split(",", 3); // Split into 3 parts: rating, coverUrl, summary
            String rating = results[0];
            String coverUrl = results[1];
            String summary = results[2];
            System.out.println("summary:" + summary);

            // Set the scraped information to the movieDTO
            movieDTO.setScore_official(rating);
            movieDTO.setCover_url(coverUrl);
            movieDTO.setSummary(summary);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movieDTO;
    }

    @Override
    public List<MovieRow> searchMovies(String keyword) {
        Query query = new Query();
        // 电影名 导演 演员名同时根据关键词搜索
        query.addCriteria(new Criteria().orOperator(
                Criteria.where("title").regex(keyword, "i"),
                Criteria.where("director").regex(keyword, "i"),
                Criteria.where("actors").regex(keyword, "i")
        ));
        return findMovieRow(query);
    }

    @Override
    public List<MovieRow> categorySearch(String[] tags, String[] languages) {
        Query query = new Query();
        if ((tags == null || tags.length == 0) && (languages == null || languages.length == 0)) {
            throw new RuntimeException("分类和语言至少有一个不为空");
        }
        // 处理各种情况
//        System.out.println(tags.length);
        if (languages == null || languages.length == 0) {
            // 通过 all 查询满足多个标签的结果
            System.out.println("第一个");
            query.addCriteria(Criteria.where("tags").all((Object[]) tags));
        } else if (tags == null || tags.length == 0) {
            System.out.println("languages: " + Arrays.toString(languages));
            query.addCriteria(Criteria.where("languages").all((Object[]) languages));
        } else {
            System.out.println("第三个 tags: " + Arrays.toString(tags));
            query.addCriteria(new Criteria().andOperator(
                    Criteria.where("tags").all((Object[]) tags),
                    Criteria.where("languages").all((Object[]) languages)
            ));
        }
        return findMovieRow(query);
    }

    /*@Override
    public String watchTogetherWithTitle(Integer id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Movie movie = mongoTemplate.findOne(query, Movie.class);
        if (movie == null) {
            throw new RuntimeException("电影不存在");
        }
        return movie.getTitle();
    }*/

    @Override
    public String getTitle(Integer id) {
        Movie movie = mongoTemplate.findById(id, Movie.class);
        if (movie == null) {
            throw new RuntimeException("电影不存在");
        }
        return movie.getTitle();
    }

    @Override
    public List<MovieRow> getSuggestedMovies() {
        Integer[] ids=MovieRow.SUGGEST;
        List<MovieRow> movieRows = new ArrayList<>();
        for(Integer id:ids){
            Movie movie = mongoTemplate.findById(id, Movie.class);
            MovieRow movieRow = new MovieRow();
            if (!Arrays.asList("1", "2", "3", "4", "5").contains(movie.getStar_mine())) {
                movie.setStar_mine("暂无评分");
            }
            BeanUtils.copyProperties(movie, movieRow);
            movieRows.add(movieRow);
        }
        return movieRows;
    }

    // 根据条件在MongoDB库中查询Movie结果并转换为MovieRow
    private List<MovieRow> findMovieRow(Query query) {
        List<Movie> movies = mongoTemplate.find(query, Movie.class);
        List<MovieRow> movieRows = new ArrayList<>();
        for (Movie movie : movies) {
            MovieRow movieRow = new MovieRow();
            if (!Arrays.asList("1", "2", "3", "4", "5").contains(movie.getStar_mine())) {
                movie.setStar_mine("暂无评分");
            }
            BeanUtils.copyProperties(movie, movieRow);
            movieRows.add(movieRow);
        }
        return movieRows;
    }
}
