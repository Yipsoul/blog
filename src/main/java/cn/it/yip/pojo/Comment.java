package cn.it.yip.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 评论表
 *
 * @program: blog
 * @author: YipSouL
 * @create: 2019-06-18 01:06
 **/
@Data
@ToString(exclude = {"blog","replyComments","parentComment"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;//昵称
    private String email;//邮箱
    private String content;//评论内容
    private String avatar;//头像
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;//评论时间

    @ManyToOne
    private Blog blog;


    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyComments = new ArrayList<>();


    private boolean adminComment;

    @ManyToOne
    private Comment parentComment;
}
