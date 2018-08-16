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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.weixin4j.WeixinConfig;
import org.weixin4j.WeixinPayConfig;

/**
 * 微信配置读取类
 *
 * @author 杨启盛<qsyang@ansitech.com>
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = Weixin4jProperties.WEIXINR4J_PREFIX)
public class Weixin4jProperties {

    /**
     * 配置前缀
     */
    public static final String WEIXINR4J_PREFIX = "weixin4j";

    /**
     * 微信配置
     */
    @NestedConfigurationProperty
    private WeixinConfig config;

    /**
     * 微信支付配置
     */
    @NestedConfigurationProperty
    private WeixinPayConfig payConfig;

    public WeixinConfig getConfig() {
        return config;
    }

    public void setConfig(WeixinConfig config) {
        this.config = config;
    }

    public WeixinPayConfig getPayConfig() {
        return payConfig;
    }

    public void setPayConfig(WeixinPayConfig payConfig) {
        this.payConfig = payConfig;
    }
}
