package com.example.demo.controller;

import com.example.demo.entity.News;
import com.example.demo.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/manager/news")
@RequiredArgsConstructor
public class NewsManagementController {

    private final NewsService newsService;

    /** Danh sách + phân trang */
    @GetMapping
    public String list(@RequestParam(defaultValue="0") int page,
                       @RequestParam(defaultValue="10") int size,
                       Model model) {
        model.addAttribute("page", newsService.findAll(page, size));
        return "manager/news/list";
    }

    /** Form tạo mới */
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("news", new News()); // tên attribute là news
        return "manager/news/form";
    }


    /** Lưu (tạo hoặc update) */
    @PostMapping("/save")
    public String save(@ModelAttribute News news, RedirectAttributes ra) {
        newsService.save(news);
        ra.addFlashAttribute("success","Đã lưu bản tin!");
        return "redirect:/manager/news";
    }

    /** Xoá */
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra){
        newsService.delete(id);
        ra.addFlashAttribute("success","Đã xoá bản tin!");
        return "redirect:/manager/news";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        News news = newsService.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
        model.addAttribute("news", news); // vẫn dùng tên news
        return "manager/news/form";
    }

}
