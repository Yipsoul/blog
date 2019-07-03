package cn.it.yip.service;

import cn.it.yip.exception.NotFoundException;
import cn.it.yip.pojo.Blog;
import cn.it.yip.pojo.Comment;
import cn.it.yip.repository.CommentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 评论业务处理类
 *
 * @program: blog
 * @author: YipSouL
 * @create: 2019-06-26 01:46
 **/
@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    /**
     * 保存評論
     *
     * @param comment
     * @return
     */
    @Transactional
    public Comment save(Comment comment) {
        comment = setValue(comment);
        comment.setAvatar(avatar);
        this.commentRepository.save(comment);
        return comment;
    }

    @Transactional
    public Comment save(Comment comment, String userAvatar) {
        comment = setValue(comment);
        comment.setAvatar(userAvatar != null ? userAvatar : avatar);
        this.commentRepository.save(comment);
        return comment;
    }

    private Comment setValue(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        Blog blog = this.blogService.findBlogByID(comment.getBlog().getId());
        if (parentCommentId != -1) {
            Comment parentComment = this.commentRepository.findById(parentCommentId).orElseThrow(NotFoundException::new);
            comment.setParentComment(parentComment);
        } else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        comment.setBlog(blog);
        return comment;
    }


    public List<Comment> findByBlogID(Long id) {
        List<Comment> list = this.commentRepository.findByBlogIdAndParentCommentNull(id, new Sort(Sort.Direction.DESC, "createTime"));
        return eachComment(list);
    }

    /**
     * 循环每个顶级的评论节点
     *
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            List<Comment> replys1 = comment.getReplyComments();
            for (Comment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();

    /**
     * 递归迭代，剥洋葱
     *
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment) {
        tempReplys.add(comment);//顶节点添加到临时存放集合
        if (comment.getReplyComments().size() > 0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComments().size() > 0) {
                    recursively(reply);
                }
            }
        }
    }
}
