package com.satti.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 包含评论信息（直接推送 不显示了 好麻烦…）、豆瓣评分、封面以及其它所有信息
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {
    private Movie movie;
    private String score_official;
    private String cover_url;
}
