package org.weixin4j.spring;

import org.springframework.beans.factory.DisposableBean;
import org.weixin4j.pay.WeixinPay;
import org.weixin4j.pay.WeixinPayConfig;
import org.weixin4j.pay.WeixinPayException;
import org.weixin4j.pay.component.PayBankComponment;
import org.weixin4j.pay.component.PayWalletComponment;
import org.weixin4j.pay.component.RedpackComponment;
import org.weixin4j.pay.loader.IRsaPubKeyLoader;

/**
 * 微信支付代理模板类
 *
 * @author yangqisheng
 * @since 2.0.0
 */
public class WeixinPayTemplate extends WeixinPay implements DisposableBean {

    private final WeixinPay weixinPayProxy;

    @Override
    public void destroy() throws Exception {

    }

    public WeixinPayTemplate(WeixinPayConfig weixinPayConfig, IRsaPubKeyLoader rsaPubKeyLoader) {
        this.weixinPayProxy = new WeixinPay(weixinPayConfig);
        this.setRsaPubKeyLoader(rsaPubKeyLoader);
    }

    @Override
    public WeixinPayConfig getWeixinPayConfig() {
        return weixinPayProxy.getWeixinPayConfig();
    }

    @Override
    public String getRsaPubKey() throws WeixinPayException {
        return weixinPayProxy.getRsaPubKey();
    }

    @Override
    public PayBankComponment payBank() {
        return weixinPayProxy.payBank();
    }

    @Override
    public RedpackComponment redpack() {
        return weixinPayProxy.redpack();
    }

    @Override
    public PayWalletComponment payWallet() {
        return weixinPayProxy.payWallet();
    }
}
