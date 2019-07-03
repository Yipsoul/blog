package cn.it.yip.web;

import cn.it.yip.aspect.RepeatSubmit;
import cn.it.yip.aspect.WebLog;
import cn.it.yip.pojo.*;
import cn.it.yip.service.BlogService;
import cn.it.yip.service.CommentService;
import cn.it.yip.service.TagService;
import cn.it.yip.service.TypeServcie;
import cn.it.yip.vo.BlogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @program: blog
 * @author: YipSouL
 * @create: 2019-06-17 16:29
 **/
@Controller
public class IndexController {
    private final Integer DEFAULT_LISTRECOMMEND = 6;
    private final Integer DEFAULT_LISTTYPE = 6;
    private final Integer DEFAULT_LISTTAG = 10;

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeServcie typeServcie;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;
    @WebLog(description = "博客首页接口")
    @RequestMapping("/")
    public String index(HttpServletRequest request, @PageableDefault(size = 40, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page", blogService.findAllByPage(pageable));
        model.addAttribute("types", typeServcie.listTop(DEFAULT_LISTTYPE));
        model.addAttribute("tags", tagService.listTop(DEFAULT_LISTTAG));
        model.addAttribute("recommendBlogs", blogService.findListByRecommend(DEFAULT_LISTRECOMMEND));
        return "index";
    }

    @WebLog(description = "根据博客Id查看博客详情")
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable("id") Long id, Model model) {
        model.addAttribute("blog", this.blogService.findBlogByIDConvertContent(id));
        model.addAttribute("comments", this.commentService.findByBlogID(id));
        return "blog";
    }

    @WebLog(description = "根据搜索内容展示列表")
    @PostMapping("/search")
    public String blogByQuery(@RequestParam String queryValue, @PageableDefault(size = 50, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page", this.blogService.findListBySearchQueryValue(queryValue, pageable));
        model.addAttribute("queryValue", queryValue);
        return "search";
    }

    @RequestMapping("/types")
    public String types() {
        return "types";
    }

    @WebLog(description = "提交评论内容")
    @RepeatSubmit
    @PostMapping("/comments")
    public String comments(Comment comment, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        comment.setAdminComment(user != null);
        Long id = comment.getBlog().getId();
        Comment newComment = this.commentService.save(comment,user != null ? user.getAvatar() : null);
        return "redirect:/blog/" + id;
    }
    @WebLog(description = "通过博客id查询所有评论")
    @GetMapping("/comments/{id}")
    public String commnetsByBolgId(@PathVariable("id") Long id, Model model) {
        model.addAttribute("comments", this.commentService.findByBlogID(id));
        return "blog :: commentLists";
    }

    @WebLog(description = "分类首页接口")
    @GetMapping("/type.html")
    public String typeIndex(@PageableDefault(size = 50, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        setTypeModel(model, null, pageable);
        return "types";
    }


    @WebLog(description = "根据分类id查询博客列表接口")
    @GetMapping("/type/{id}")
    public String typeByID(@PathVariable Long id, @PageableDefault(size = 50, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        setTypeModel(model, id, pageable);
        return "types";
    }

    @WebLog(description = "标签首页接口")
    @GetMapping("/tags.html")
    public String tagsIndex(@PageableDefault(size = 50, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        setTagModel(model,null,pageable);
        return "tags";
    }

    @WebLog(description = "根据标签id获取博客列表接口")
    @GetMapping("/tags/{id}")
    public String tagsByID(@PageableDefault(size = 50, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,@PathVariable("id") Long id, Model model) {
       setTagModel(model,id,pageable);
        return "tags";
    }

    @WebLog(description = "归档页接口")
    @GetMapping("/archives.html")
    public String archivesIndex(Model model){
        model.addAttribute("archivesMap",this.blogService.findBlogByArchives());
        model.addAttribute("count",this.blogService.findBlogCount());
        return "archives";
    }

    @WebLog(description = "关于我页面接口")
    @GetMapping("/about.html")
    public String aboutIndex(){
        return "about";
    }


    private void setTypeModel(Model model, Long index, Pageable pageable) {
        List<Type> typeList = this.typeServcie.finAll();
        model.addAttribute("typeList", typeList);
        BlogVo blogVo = new BlogVo();
        //todo 清空数据库后从一开始
        blogVo.setTypeId(index == null ? 1 : index);
        Page<Blog> blogListByPage = this.blogService.findBlogListByPage(pageable, blogVo);
        model.addAttribute("type", index == null ? typeList.get(0) : this.typeServcie.findByID(index));
        model.addAttribute("page", blogListByPage);
    }

    private void setTagModel(Model model, Long index, Pageable pageable) {
        List<Tag> tags = this.tagService.finAll();
        //todo 清空数据库后从一开始
        model.addAttribute("tags", tags);
        model.addAttribute("tag", index == null ? tags.get(0) : this.tagService.findByID(index));
        model.addAttribute("page", this.blogService.findBlogListByTagId(index == null ? 1 : index, pageable));
    }
}
