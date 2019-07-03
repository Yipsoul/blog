package cn.it.yip.repository;

import cn.it.yip.pojo.Type;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @program: blog
 * @author: YipSouL
 * @create: 2019-06-20 22:57
 **/

@SpringBootTest
@RunWith(SpringRunner.class)
public class TypeRepositoryTest {

    @Autowired
    private TypeRepository typeRepository;
    @Test
    public void findTypesByTypeNameLike() {
        Page<Type> typesByTypeNameLike = typeRepository.findTypesByTypeNameLike("%java%", PageRequest.of(0,10));
        typesByTypeNameLike.forEach(System.out::println);
    }
}