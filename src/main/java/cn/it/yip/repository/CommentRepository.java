package cn.it.yip.repository;

import cn.it.yip.pojo.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: blog
 * @author: YipSouL
 * @create: 2019-06-26 01:45
 **/
@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByBlogIdAndParentCommentNull(Long id, Sort sort);
}
