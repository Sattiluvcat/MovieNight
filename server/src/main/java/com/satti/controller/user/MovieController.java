package com.satti.controller.user;

import com.satti.entity.*;
import com.satti.result.PageResult;
import com.satti.service.MailService;
import com.satti.service.MovieService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.satti.result.Result;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/movie")
@Slf4j
public class MovieController {
    @Autowired
    private MovieService movieService;
    @Autowired
    private MailService mailService;

    @GetMapping("/hello")
    public String hello(){
        System.out.println("Hello World!");
        return "hello";
    }

    // 4th: 用户登录后记录信息
    @PostMapping("/login")
    public Result login(@RequestBody UserInfo userInfo, HttpSession session) {
        log.info("NEW USER! their nickname: {}, title: {}, contact: {}", userInfo.getNickname(), userInfo.getTitle(), userInfo.getContact());
        session.setAttribute("userInfo", userInfo);
        return Result.success();
    }

    // TODO 00th: 电影分页查询 check again
    @GetMapping("/page")
    public PageResult paginatedSearch(@RequestParam List<MovieRow> movieRows,
                                                      @RequestParam int page,
                                                      @RequestParam int size) {
        int total = movieRows.size();
        int start = (page - 1) * size;
        int end = Math.min(start + size, total);
        List<MovieRow> paginatedMovies = movieRows.subList(start, end);
        return new PageResult<>(total, paginatedMovies, page, size);
    }

    // 0th: 电影模糊搜索
    @GetMapping("/search")
    public Result<List<MovieRow>> searchMovies(@RequestParam String keyword){
        log.info("searching movies with keyword: {}", keyword);
        List<MovieRow> movieRows =movieService.searchMovies(keyword);
        return Result.success(movieRows);
    }

    // 分类与语言同时搜索
    @GetMapping("/categorySearch")
    public Result<List<MovieRow>> categorySearch(@RequestParam String tags,
                                                 @RequestParam String languages) {
        // 处理中文编码问题 此处解码 其实可以不解码 调试的时候解码
        log.info("searching movies with tags: {}, languages: {}", tags, languages);
        String[] tagList = null;
        String[] languageList = null;
        // 此处不能直接split(",") 会造成空字符串问题（length!=0）
        if(tags!=null&& !tags.isEmpty())
            tagList = tags.split(",").clone();
        if(languages!=null&& !languages.isEmpty())
            languageList = languages.split(",").clone();
        List<MovieRow> movieRows = movieService.categorySearch(tagList, languageList);
        System.out.println("movieRows: " + movieRows);
        return Result.success(movieRows);
    }


    // 电影详情
    @GetMapping("/{id}")
    public Result<MovieDTO> movieDetails(@PathVariable Integer id){
        log.info("getting movie details with id: {}", id);
        MovieDTO movieDTO = movieService.getMovieDetails(id);
        return Result.success(movieDTO);
    }

    // 一起看
    @PostMapping("/{id}/watch-together")
    public Result watchTogether(@PathVariable Integer id, HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        if (userInfo == null) {
            return Result.failure("请登录后再操作噢");
        }
        String title = movieService.getTitle(id);
        String emailContent = String.format("用户 %s (%s) 邀请你一起看: %s\n联系方式: %s",
                userInfo.getNickname(), userInfo.getTitle(), title, userInfo.getContact());
        System.out.println(emailContent);
        mailService.sendSimpleMail("***@foxmail.com", "一起看电影", emailContent);
        return Result.success();
    }


    // 评论——和一起看一样，没准备放到数据库里(暂时)
    @PostMapping("/{id}/comment")
    public Result comment(@PathVariable Integer id, @RequestBody String comment, HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        if (userInfo == null) {
            return Result.failure("请登录后再操作噢");
        }
        String title = movieService.getTitle(id);
        String emailContent = String.format("用户 %s (%s) 对电影: %s\n发布了评论: %s\n联系方式: %s",
                userInfo.getNickname(), userInfo.getTitle(), title, comment, userInfo.getContact());
        mailService.sendSimpleMail("***@foxmail.com", "电影评论", emailContent);
        return Result.success();
    }

    // 登出
    @PostMapping("/logout")
    public Result logout(HttpSession session) {
        log.info("logging out");
        session.invalidate();
        return Result.success();
    }

    // last:管理端的增删改权限 现在直接改MongoDB即可 之后可以做细化（主要是前端不咋会）

}
