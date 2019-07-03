package cn.it.yip.web.admin;

import cn.it.yip.aspect.WebLog;
import cn.it.yip.pojo.Tag;
import cn.it.yip.pojo.Type;
import cn.it.yip.service.TagService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @program: blog
 * @author: YipSouL
 * @create: 2019-06-21 00:44
 **/
@Controller
@RequestMapping("admin/tags")
public class AdminTagController {

    @Autowired
    private TagService tagService;

    @WebLog(description = "标签管理分页接口")
    @GetMapping("list")
    public String typesList(@PageableDefault(size = 30, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable, Model model) {
        Page<Tag> tagPage = this.tagService.tagList(pageable);
        model.addAttribute("tags", tagPage);
        return "admin/tags";
    }

    @WebLog(description = "根据id删除标签")
    @GetMapping("delete/{id}")
    public String deleteTag(@PathVariable("id") Long id) {
        this.tagService.deleteByID(id);
        return "redirect:../list";
    }

    @WebLog(description = "编辑分类")
    @GetMapping("edit/{id}")
    public String editTag(@PathVariable("id") Long id, Model model) {
        Tag tag = this.tagService.findByID(id);
        model.addAttribute("tag", tag);
        return "admin/tags-input";
    }

    @WebLog(description = "添加标签接口")
    @PostMapping("/add")
    public String addTag(Tag tag, RedirectAttributes attributes) {
        Tag t = this.tagService.save(tag);
        if (t == null)
            attributes.addFlashAttribute("message", "添加失败");
        else
            attributes.addFlashAttribute("message", "添加成功");
        return "redirect:list";
    }


    @WebLog(description = "跳转到添加标签页面")
    @GetMapping("input")
    public String tagInput(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }



    @WebLog(description = "标签搜索接口")
    @PostMapping("/search")
    public String searchTag(@RequestParam String searchContent, @PageableDefault(size = 50,sort = {"id"},direction = Sort.Direction.ASC) Pageable pageable, Model model) {
        if (StringUtils.isBlank(searchContent)) {
            return "redirect:list";
        } else {
            Page<Tag> tags = this.tagService.findTypesLike(searchContent, pageable);
            model.addAttribute("tags", tags);
            return "admin/tags";
        }
    }
}
