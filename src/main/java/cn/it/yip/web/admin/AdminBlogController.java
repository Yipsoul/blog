package cn.it.yip.web.admin;

import cn.it.yip.Enum.ExceptionEnum;
import cn.it.yip.aspect.RepeatSubmit;
import cn.it.yip.aspect.WebLog;
import cn.it.yip.exception.BaseException;
import cn.it.yip.pojo.Blog;
import cn.it.yip.pojo.Tag;
import cn.it.yip.pojo.Type;
import cn.it.yip.service.BlogService;
import cn.it.yip.service.TagService;
import cn.it.yip.service.TypeServcie;
import cn.it.yip.vo.BlogVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @program: blog
 * @author: YipSouL
 * @create: 2019-06-22 00:33
 **/
@Controller
@RequestMapping("admin/blogs")
public class AdminBlogController {

    private final String DEFAULT_FLAG = "原创";
    @Autowired
    private BlogService blogService;

    @Value("${file.staticAccessPath}")
    private String staticAccessPath;
    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Value("${myblog.url}")
    private String url;
    @Autowired
    private TypeServcie typeServcie;

    @Autowired
    private TagService tagService;

    @WebLog(description = "查询所有博客")
    @GetMapping("list")
    public String blogList(BlogVo blogVo, @PageableDefault(size = 30, sort = {"updateTime"}, direction = Sort.Direction.ASC) Pageable pageable, Model model) {
        List<Type> typeList = typeServcie.finAll();
        Page<Blog> blogListByPage = this.blogService.findAllByPage(pageable);
        model.addAttribute("types", typeList);
        model.addAttribute("page", blogListByPage);
        model.addAttribute("blog", new Blog());
        return "admin/blogs";
    }
    @WebLog(description = "搜索博客接口")
    @PostMapping("/search")
    public String search(BlogVo blogVo, @PageableDefault(size = 50, sort = {"updateTime"}, direction = Sort.Direction.ASC) Pageable pageable, Model model) {
        List<Type> typeList = typeServcie.finAll();
        Page<Blog> blogListByPage = this.blogService.findBlogListByPage(pageable, blogVo);
        model.addAttribute("types", typeList);
        model.addAttribute("page", blogListByPage);
        return "admin/blogs";
    }
    @WebLog(description = "跳转博客添加页面")
    @GetMapping("/input")
    public String addInput(Model model) {
        setAttr(model);
        model.addAttribute("blog",new Blog());
        return "admin/blog-input";
    }

    @WebLog(description = "添加博客")
    @PostMapping("/add")
    public String add(Blog blog, HttpSession session, RedirectAttributes attributes) {
        if (StringUtils.isBlank(blog.getFlag())) {
            blog.setFlag(DEFAULT_FLAG);
        }
        Blog b = this.blogService.saveOrUpdate(blog, session);
        attributes.addFlashAttribute("message", b != null ? "操作成功" : "操作失败");
        return "redirect:list";
    }

    @WebLog(description = "根据博客的Id删除博客")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        System.out.println(id);
        this.blogService.deleteById(id);
        return "redirect:../list";
    }


    @WebLog(description = "修改博客")
    @GetMapping("edit/{id}")
    public String editTag(@PathVariable("id") Long id, Model model) {
        Blog blog = this.blogService.findBlogByID(id);
        blog.init();
        setAttr(model);
        model.addAttribute("blog", blog);
        return "admin/blog-input";
    }

    @WebLog(description = "上传图片")
    @PostMapping("/upload")
    @RepeatSubmit
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file){
        String imgUrl = this.blogService.upload(url,staticAccessPath,uploadFolder,file);
        if(StringUtils.isBlank(imgUrl)){
            throw new BaseException(ExceptionEnum.UPLOAD_FILE_FAIL);
        }
        return ResponseEntity.ok(imgUrl);
    }

    private void setAttr(Model model) {
        List<Type> typeList = typeServcie.finAll();
        model.addAttribute("types", typeList);
        List<Tag> tags = tagService.finAll();
        model.addAttribute("tags", tags);
    }
}
