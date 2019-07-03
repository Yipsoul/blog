package cn.it.yip.service;

import cn.it.yip.pojo.Blog;
import cn.it.yip.pojo.Tag;
import cn.it.yip.pojo.Type;
import cn.it.yip.pojo.User;
import cn.it.yip.repository.BlogRepository;
import cn.it.yip.util.MarkdownUtils;
import cn.it.yip.vo.BlogVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: blog
 * @author: YipSouL
 * @create: 2019-06-22 00:35
 **/
@Service
@Slf4j
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private TypeServcie typeServcie;

    @Autowired
    private TagService tagService;


    @Transactional
    public Blog save(Blog blog) {
        return this.blogRepository.saveAndFlush(blog);
    }

    @Transactional
    public void deleteById(Long id) {
        this.blogRepository.deleteById(id);
    }

    public Blog findBlogByID(Long id) {
        return this.blogRepository.getOne(id);
    }


    public Page<Blog> findAllByPage(Pageable pageable) {
        return this.blogRepository.findAll(pageable);
    }

    public Page<Blog> findBlogListByPage(Pageable pageable, BlogVo blogVo) {
        return this.blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotBlank(blogVo.getTitle())) {
                    predicates.add(cb.like(root.<String>get("title"), "%" + blogVo.getTitle() + "%"));
                }
                if (blogVo.getTypeId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blogVo.getTypeId()));
                }
                if (blogVo.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blogVo.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }


    @Transactional
    public Blog saveOrUpdate(Blog blog, HttpSession session) {
        if (blog.getId() == null) {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(blog.getCreateTime());
            blog.setViews(0);
        } else {
            blog.setCreateTime(this.blogRepository.getOne(blog.getId()).getCreateTime());
            blog.setUpdateTime(new Date());
        }
        blog.setUser((User) session.getAttribute("user"));
        Type byID = typeServcie.findByID(blog.getType().getId());
        blog.setType(byID);
        String[] strings = StringUtils.split(blog.getTagIds(), ",");
        long[] longs = Arrays.stream(strings).mapToLong(s -> Long.valueOf(s)).toArray();
        List<Long> list = CollectionUtils.arrayToList(longs);
        List<Tag> tags = tagService.findByIdS(list);
        blog.setTags(tags);
        return this.blogRepository.saveAndFlush(blog);
    }

    public List<Blog> findListByRecommend(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        return this.blogRepository.findListByRecommend(PageRequest.of(0, size, sort));
    }

    /**
     * 根据搜索内容查询
     *
     * @param queryValue
     * @param pageable
     * @return
     */
    public Page<Blog> findListBySearchQueryValue(String queryValue, Pageable pageable) {
        return this.blogRepository.findListBySearchQueryValue("%" + queryValue + "%", pageable);
    }

    /**
     * 根据id查询博客内容并转换content属性
     *
     * @param id
     * @return
     */
    public Blog findBlogByIDConvertContent(Long id) {
        Blog blog = this.blogRepository.getOne(id);
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(b.getContent()));
        this.blogRepository.updateViews(id);
        return b;
    }

    public Page<Blog> findBlogListByTagId(Long tagId, Pageable pageable) {
        return this.blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join join = root.join("tags");
                return criteriaBuilder.equal(join.get("id"), tagId);
            }
        }, pageable);
    }

    public Long findBlogCount() {
        return this.blogRepository.count();
    }

    /**
     * 归档查询
     *
     * @return
     */
    public Map<String, List<Blog>> findBlogByArchives() {
        List<String> years = this.blogRepository.findYear();
        Map<String, List<Blog>> blogsMap = new HashMap<>();
        years.forEach(s -> {
            blogsMap.put(s, this.blogRepository.findBlogsByYear(s));
        });
        return blogsMap;
    }

    /**
     * 上传文件到服务器
     * @param staticAccessPath  對外暴露的路徑
     * @param uploadFolder  上傳文件的路徑
     * @param file  要上傳的文件
     * @return
     */
    public String upload(String url,String staticAccessPath, String uploadFolder, MultipartFile file) {
        File newFile = new File(uploadFolder);
        if (!newFile.exists()) {
            newFile.mkdir();
        }
        //截取后缀名
        String index = StringUtils.substringAfterLast(file.getContentType(), "/");
        String iamgeName = StringUtils.replaceAll(UUID.randomUUID().toString(), "-", "");
        try {
            file.transferTo(new File(newFile,"/"+iamgeName+"."+index));
        } catch (IOException e) {
            log.error("文件上传失败  {}",e);
            e.printStackTrace();
            return null;
        }
        String s = StringUtils.substringBeforeLast(staticAccessPath, "/");
        return url+s+"/"+iamgeName+"."+index;
    }
}
