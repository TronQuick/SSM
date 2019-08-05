package com.imooc.myspringboot.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.swing.plaf.PanelUI;

@Controller
public class MyController {
    @Value("${mall.config.name}")
    private String name;
    @Value("${mall.config.description}")
    private String description;
    @Value("${mall.config.hot-sales}")
    private Integer hotSales;
    @Value("${mall.config.show-advert}")
    private Boolean showAdvert;

    @ResponseBody
    @RequestMapping("/out")
    public String out() {
        return "SpringBoot Initializr success";
    }

    @RequestMapping("/info")
    @ResponseBody
    public String info() {
         return  String.format("name:%s,description:%s,hot-sales:%s,show-advert:%s",
                name,description,hotSales,showAdvert);
    }
}
