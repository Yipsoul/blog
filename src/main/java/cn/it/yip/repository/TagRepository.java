package cn.it.yip.repository;

import cn.it.yip.pojo.Tag;
import cn.it.yip.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: tags
 * @author: YipSouL
 * @create: 2019-06-19 15:18
 **/
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Page<Tag> findTagByTagNameLike(String tagName, Pageable pageable);

    @Query("select t from Tag t")
    List<Tag> listTop(Pageable pageable);
}
