/*
 * 微信公众平台(JAVA) SDK
 *
 * Copyright (c) 2014, Ansitech Network Technology Co.,Ltd All rights reserved.
 *
 * http://www.weixin4j.org/spring/boot/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.weixin4j.spring.boot.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.weixin4j.pay.loader.IRsaPubKeyLoader;
import org.weixin4j.spring.WeixinPayTemplate;
import org.weixin4j.spring.loader.DefaultRsaPubKeyLoader;

/**
 * Weixin4jPay自动注册配置类
 *
 * @author yangqisheng
 * @since 2.0.0
 */
@Configuration
@ConditionalOnClass({WeixinPayTemplate.class})
@EnableConfigurationProperties(Weixin4jProperties.class)
public class Weixin4jPayAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(Weixin4jPayAutoConfiguration.class);

    private final Weixin4jProperties properties;

    public Weixin4jPayAutoConfiguration(Weixin4jProperties properties) {
        System.out.println("weixin4j-starter: init Weixin4jPayAutoConfiguration, weixin4j-pay properties " + (properties != null ? "is setting" : "not set"));
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public IRsaPubKeyLoader rsaPubKeyLoader() {
        System.out.println("weixin4j-starter: bean IRsaPubKeyLoader loaded by spring default.");
        return new DefaultRsaPubKeyLoader(properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public WeixinPayTemplate weixinPayTemplate(IRsaPubKeyLoader rsaPubKeyLoader) {
        System.out.println("weixin4j-starter: bean WeixinPayTemplate init.");
        System.out.println("weixin4j-starter: bean " + rsaPubKeyLoader.getClass().getName() + " init.");
        return new WeixinPayTemplate(properties.getPayConfig(), rsaPubKeyLoader);
    }
}
