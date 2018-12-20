package org.launchcode.controllers;

import org.launchcode.models.Category;
import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.Normalizer;

@Controller
@RequestMapping(value = "menu")
public class MenuController {


    @Autowired
    MenuDao menuDao;

    @Autowired
    CheeseDao cheeseDao;


    @RequestMapping(value = "")
    private String index(Model model){

        model.addAttribute("menus",menuDao.findAll());
        model.addAttribute("title", "Menus");
        return "menu/index";

    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    private String displayMenuForm(Model model){
        model.addAttribute("title", "Add Menu");
        Model model1 = model.addAttribute(new Menu());
        return "menu/add";
    }


    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddMenuForm(Model model, @ModelAttribute @Valid Menu newMenu,
                                     Errors errors) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Menu");
            return "menu/add";
        }
        menuDao.save(newMenu);

        return "redirect:view/"+newMenu.getId();
    }

    @RequestMapping(path = "view/{menuID}",method = RequestMethod.GET)
    public String viewMenu(Model model,@PathVariable int menuID) {

        Menu menu = menuDao.findOne(menuID);
        model.addAttribute("title", menu.getName());
        model.addAttribute("cheeses", menu.getCheeses());
        model.addAttribute("menuId", menu.getId());

        return "menu/view";
    }

    @RequestMapping(value = "add-item/{menuID}",method = RequestMethod.GET)
    public String addItem(Model model,@PathVariable int menuID) {

        Menu menu= menuDao.findOne(menuID);

        AddMenuItemForm form=new AddMenuItemForm(cheeseDao.findAll(),menu);
        model.addAttribute("title","Add Item to menu "+menu.getName());
        model.addAttribute("form",form);
        return "menu/add-item";

    }

    @RequestMapping(value = "add-item/{menuID}",method = RequestMethod.POST)
    public String addItem(Model model,
                          @ModelAttribute @Valid AddMenuItemForm form,Errors errors,@PathVariable int menuID){

        if (errors.hasErrors()){
            model.addAttribute("form",form);
            return "menu/add-item/"+menuID;

        }

        Cheese thecheese =cheeseDao.findOne(form.getCheeseId());
        Menu themenu =menuDao.findOne(menuID);
        themenu.addItem(thecheese);
        menuDao.save(themenu);

        return "redirect:/menu/view/" + themenu.getId();
    }

}






