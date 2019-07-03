package cn.it.yip.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: blog
 * @author: YipSouL
 * @create: 2019-06-22 00:43
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogVo {

    private String title;
    private boolean recommend;
    private Long typeId;
}
