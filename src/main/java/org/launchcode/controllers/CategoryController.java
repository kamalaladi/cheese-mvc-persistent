package org.launchcode.controllers;

import org.launchcode.models.Category;
import org.launchcode.models.data.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("category")
public class CategoryController {
    @Autowired
    public CategoryDao categoryDao;

    @RequestMapping(value = "")
    public String index(Model model){
        Iterable<Category> list = categoryDao.findAll();

        model.addAttribute("categories", list);
        model.addAttribute("title", "Categories");
        return "category/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddCheeseForm(Model model) {
        model.addAttribute("title", "Add Category");
        model.addAttribute(new Category());
        return "category/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String addCategoryForm(Model model, @ModelAttribute @Valid Category category, Errors errors){

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Category");
            return "category/add";
        }

        categoryDao.save(category);
        return "redirect:";

    }
    @RequestMapping(value="remove", method=RequestMethod.GET)
    public String displayRemoveCategoryForm(Model model){
        model.addAttribute("categories",categoryDao.findAll());
        model.addAttribute("title","Remove Cheese");
        return "category/remove";
    }

    @RequestMapping(value="remove",method=RequestMethod.POST)
    public String processRemoveCategory(@RequestParam int[] categories){

        for(int category:categories){
            categoryDao.delete(category);
        }
        return "redirect:";
    }

}

