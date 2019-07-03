package cn.it.yip.repository;

import cn.it.yip.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: blog
 * @author: YipSouL
 * @create: 2019-06-19 15:18
 **/
@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
    Page<Type> findTypesByTypeNameLike(String typeName, Pageable pageable);

    List<Type> findTypesByTypeNameLike(String typeName);
    @Query("select t from Type t")
    List<Type> listTop(Pageable pageable);
}
