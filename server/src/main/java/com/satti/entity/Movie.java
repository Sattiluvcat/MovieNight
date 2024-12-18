package com.satti.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "movies")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    /**
     * {"title": "滚蛋吧！肿瘤君",
     *  "link": "https://movie.douban.com/subject/26289144/",
     *  "star_mine": "", "release_time": "2015-08-13",
     *  "actor_actresses": ["白百何", "吴彦祖", "李媛"],
     *  "director": "韩延", "duration": "128分钟",
     *  "tags": ["喜剧", "爱情"], "languages": ["汉语普通话", "英语", "韩语"]}
     */
    @Id
    private String _id; // MongoDB's default primary key

    private Integer id;
    private String title;
    private String link;
    private String star_mine;    // 自己的评分 有的没评分
//    private String star_official;
    private String release_time;
    private String[] actor_actresses;
    private String director;
    private String duration;
    private String[] tags;
    private String[] languages;

}
