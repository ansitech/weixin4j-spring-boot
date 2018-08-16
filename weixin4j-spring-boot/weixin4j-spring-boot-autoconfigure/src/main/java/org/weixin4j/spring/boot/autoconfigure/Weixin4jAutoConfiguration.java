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
import org.weixin4j.factory.WeixinFactory;
import org.weixin4j.loader.DefaultTicketLoader;
import org.weixin4j.loader.DefaultTokenLoader;
import org.weixin4j.loader.ITicketLoader;
import org.weixin4j.loader.ITokenLoader;
import org.weixin4j.spi.DefaultEventMessageHandler;
import org.weixin4j.spi.DefaultNormalMessageHandler;
import org.weixin4j.spi.IEventMessageHandler;
import org.weixin4j.spi.IMessageHandler;
import org.weixin4j.spi.INormalMessageHandler;
import org.weixin4j.spring.MessageFactoryBean;
import org.weixin4j.spring.WeixinFactoryBean;
import org.weixin4j.spring.WeixinTemplate;

/**
 * Weixin4j自动注册配置类
 *
 * @author 杨启盛<qsyang@ansitech.com>
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass({WeixinTemplate.class})
@EnableConfigurationProperties(Weixin4jProperties.class)
public class Weixin4jAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(Weixin4jAutoConfiguration.class);

    private final Weixin4jProperties properties;

    public Weixin4jAutoConfiguration(Weixin4jProperties properties) {
        if (logger.isDebugEnabled()) {
            System.out.println("init Weixin4jAutoConfiguration, weixin4j properties " + (properties == null ? " is setting" : "not set"));
        }
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public ITokenLoader tokenLoader() {
        if (logger.isDebugEnabled()) {
            System.out.println("bean ITokenLoader loaded by default.");
        }
        return new DefaultTokenLoader();
    }

    @Bean
    @ConditionalOnMissingBean
    public ITicketLoader ticketLoader() {
        if (logger.isDebugEnabled()) {
            System.out.println("bean ITicketLoader loaded by default.");
        }
        return new DefaultTicketLoader();
    }

    @Bean
    @ConditionalOnMissingBean
    public WeixinFactory weixinFactory(ITokenLoader tokenLoader, ITicketLoader ticketLoader) throws Exception {
        if (logger.isDebugEnabled()) {
            System.out.println("bean WeixinFactory init.");
        }
        WeixinFactoryBean factory = new WeixinFactoryBean();
        if (properties != null) {
            factory.setWeixinConfig(properties.getConfig());
            factory.setWeixinPayConfig(properties.getPayConfig());
        }
        factory.setTokenLoader(tokenLoader);
        factory.setTicketLoader(ticketLoader);
        return factory.getObject();
    }

    @Bean
    @ConditionalOnMissingBean
    public WeixinTemplate weixinTemplate(WeixinFactory weixinFactory) {
        if (logger.isDebugEnabled()) {
            System.out.println("bean WeixinTemplate init.");
        }
        WeixinTemplate weixinTemplate = new WeixinTemplate(weixinFactory);
        return weixinTemplate;
    }

    @Bean
    @ConditionalOnMissingBean
    public INormalMessageHandler normalMessageHandler() {
        if (logger.isDebugEnabled()) {
            System.out.println("bean INormalMessageHandler loaded by default.");
        }
        return new DefaultNormalMessageHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public IEventMessageHandler eventMessageHandler() {
        if (logger.isDebugEnabled()) {
            System.out.println("bean IEventMessageHandler loaded by default.");
        }
        return new DefaultEventMessageHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public IMessageHandler messageFactoryBean(INormalMessageHandler normalMessageHandler, IEventMessageHandler eventMessageHandler) throws Exception {
        if (logger.isDebugEnabled()) {
            System.out.println("bean IMessageHandler loaded by default.");
        }
        MessageFactoryBean factory = new MessageFactoryBean();
        factory.setNormalMessageHandler(normalMessageHandler);
        factory.setEventMessageHandler(eventMessageHandler);
        return factory.getObject();
    }
}
