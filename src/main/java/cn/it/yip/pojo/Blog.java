package cn.it.yip.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 博客信息表
 *
 * @program: blog
 * @author: YipSouL
 * @create: 2019-06-18 00:58
 **/
@Data
@ToString(exclude = {"commentList"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_blog")
public class Blog {
    @ManyToOne
    private Type type;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "blog")
    private List<Comment> commentList = new ArrayList<>();
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //标题
    private String title;
    //博客描述
    private String description;

    //内容
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content;
    //首图
    private String firstPicture;
    //标记
    private String flag;
    //浏览次数
    private Integer views;
    //赞赏是否开启
    private boolean appreciation;
    //版权开启
    private boolean share;
    //评论开启
    private boolean commentabled;
    //发布
    private boolean published;
    //推荐
    private boolean recommend;
    @Transient
    private String tagIds;
    //创建时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    //更新时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    public void init(){
        //获取该博客的多个标签，将多个标签的id赋值给tagIds
        Object[] tagIds = this.getTags().stream().map(Tag::getId).toArray();
        this.setTagIds(StringUtils.join(tagIds,","));
    }


}
