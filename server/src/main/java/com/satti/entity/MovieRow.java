package com.satti.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 缩略信息
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieRow {
    public final static Integer[] SUGGEST = {273, 60, 80, 59, 180};
    private String title;
    private String star_mine;    // 自己的评分 有的没评分
    private String release_time;
    private String[] tags;
    private String[] actor_actresses;
    private String[] languages;
    private Integer _id;
}
