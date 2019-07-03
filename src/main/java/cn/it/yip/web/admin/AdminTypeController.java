package cn.it.yip.web.admin;

import cn.it.yip.aspect.WebLog;
import cn.it.yip.pojo.Type;
import cn.it.yip.service.TypeServcie;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @program: blog
 * @author: YipSouL
 * @create: 2019-06-20 16:49
 **/
@Controller
@RequestMapping("/admin/types")
public class AdminTypeController {
    @Autowired
    private TypeServcie typeServcie;

    @WebLog(description = "分类管理分页接口")
    @GetMapping("list")
    public String typesList(@PageableDefault(size = 30, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable, Model model) {
        Page<Type> typePage = this.typeServcie.typesList(pageable);
        model.addAttribute("types", typePage);
        return "admin/types";
    }

    @WebLog(description = "跳转到添加分类页面")
    @GetMapping("input")
    public String typeInput(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    @WebLog(description = "添加分类接口")
    @PostMapping("/add")
    public String addType(Type type, RedirectAttributes attributes) {
        Type t = this.typeServcie.save(type);
        if (t == null)
            attributes.addFlashAttribute("message", "添加失败");
        else
            attributes.addFlashAttribute("message", "添加成功");
        return "redirect:list";
    }

    @WebLog(description = "根据id删除分类接口")
    @GetMapping("/delete/{id}")
    public String deleteType(@PathVariable("id") Long id) {
        // this.typeServcie.delete(id);
        return "redirect:../list";
    }

    @WebLog(description = "分类搜索接口")
    @PostMapping("/search")
    public String searchType(@RequestParam String searchContent, @PageableDefault(size = 50,sort = {"id"},direction = Sort.Direction.ASC) Pageable pageable, Model model) {
        if (StringUtils.isBlank(searchContent)) {
            return "redirect:list";
        } else {
            Page<Type> types = this.typeServcie.findTypesLike(searchContent, pageable);
            model.addAttribute("types", types);
            return "admin/types";
        }
    }

    @WebLog(description = "编辑分类")
    @GetMapping("edit/{id}")
    public String editType(@PathVariable("id")Long id,Model model){
        Type type = this.typeServcie.findByID(id);
        model.addAttribute("type",type);
        return "admin/types-input";
    }
}
