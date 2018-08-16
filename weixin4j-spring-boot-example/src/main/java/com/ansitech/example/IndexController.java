package com.ansitech.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.weixin4j.spring.WeixinTemplate;

/**
 * IndexController
 *
 * @author yangqisheng
 */
@RestController
public class IndexController {

    @Autowired
    private WeixinTemplate weixinTemplate;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "hello," + weixinTemplate.getAppId() + "," + weixinTemplate.getWeixinConfig().getApiDomain();
    }
}
