package cn.it.yip.service;

import cn.it.yip.pojo.Tag;
import cn.it.yip.pojo.Type;
import cn.it.yip.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: blog
 * @author: YipSouL
 * @create: 2019-06-21 00:46
 **/
@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public Page<Tag> tagList(Pageable pageable) {
        return this.tagRepository.findAll(pageable);
    }

    public void deleteByID(Long id) {
        this.tagRepository.deleteById(id);
    }

    public Tag findByID(Long id) {
        return this.tagRepository.findById(id).orElse(new Tag());
    }

    public Tag save(Tag tag) {
        return this.tagRepository.saveAndFlush(tag);
    }

    public Page<Tag> findTypesLike(String searchContent, Pageable pageable) {
        return this.tagRepository.findTagByTagNameLike("%" + searchContent + "%", pageable);
    }

    public List<Tag> finAll() {
        return this.tagRepository.findAll();
    }

    public List<Tag> findByIdS(List<Long> longs) {
        return tagRepository.findAllById(longs);
    }

    public List<Tag> listTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"blogs.size");
        return this.tagRepository.listTop(PageRequest.of(0,size,sort));
    }
}
