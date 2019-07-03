package cn.it.yip.service;

import cn.it.yip.pojo.Type;
import cn.it.yip.repository.TypeRepository;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @program: blog
 * @author: YipSouL
 * @create: 2019-06-19 15:16
 **/
@Service
public class TypeServcie {
    @Autowired
    private TypeRepository typeRepository;

    public Page<Type> typesList(Pageable pageable) {
        return this.typeRepository.findAll(pageable);
    }

    @Transactional
    public Type save(Type type) {
        return this.typeRepository.saveAndFlush(type);
    }

    @Transactional
    public void delete(Long id) {
        Optional<Type> type = this.typeRepository.findById(id);
        Type newType = type.get();
        if (type != null) {
            this.typeRepository.delete(newType);
        }
    }

    public Page<Type> findTypesLike(String searchContent, Pageable pageable) {
        return this.typeRepository.findTypesByTypeNameLike("%" + searchContent + "%", pageable);

    }

    public Type findByID(Long id) {
        Optional<Type> type = this.typeRepository.findById(id);
        return type.orElse(new Type());
    }

    public List<Type> finAll() {
        return this.typeRepository.findAll();
    }

    public List<Type> listTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable =  PageRequest.of(0,size,sort);
        return this.typeRepository.listTop(pageable);
    }
}
