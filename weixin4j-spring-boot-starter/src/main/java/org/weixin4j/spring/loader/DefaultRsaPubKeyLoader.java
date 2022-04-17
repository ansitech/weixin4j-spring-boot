package org.weixin4j.spring.loader;

import org.weixin4j.pay.Configuration;
import org.weixin4j.pay.loader.IRsaPubKeyLoader;
import org.weixin4j.pay.model.rsa.RsaXml;
import org.weixin4j.spring.boot.autoconfigure.Weixin4jProperties;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * 本地文件RSA公钥存储器
 *
 * @author yangqisheng
 * @since 1.0.0
 */
public class DefaultRsaPubKeyLoader implements IRsaPubKeyLoader {

    private String rsaPubKey = null;
    /**
     * openssl安装bin目录
     */
    private String opensslPath;
    /**
     * rsa_public_key_pkcs1证书文件地址
     */
    private String rsaPubKeyPkcs1;
    /**
     * rsa_public_key_pkcs8证书文件地址
     */
    private String rsaPubKeyPkcs8;

    public DefaultRsaPubKeyLoader(Weixin4jProperties weixin4jProperties) {
        opensslPath = weixin4jProperties.getOpensslPath();
        rsaPubKeyPkcs1 = weixin4jProperties.getRsaPubKeyPkcs1();
        rsaPubKeyPkcs8 = weixin4jProperties.getRsaPubKeyPkcs8();
    }

    @Override
    public String get() {
        if (rsaPubKey == null) {
            try {
                //读取rsa_public_key_pkcs8证书文件地址
                File file = new File(rsaPubKeyPkcs8);
                if (!file.exists()) {
                    if (Configuration.isDebug()) {
                        System.out.println("未找到 weixin4j.rsaPubKey.pkcs8 文件");
                    }
                    return null;
                }
                String absolutePath = file.getAbsolutePath();
                List<String> lines = Files.readAllLines(Paths.get(absolutePath), StandardCharsets.UTF_8);
                StringBuilder sb = new StringBuilder();
                for (String line : lines) {
                    if (line.charAt(0) == '-') {
                        continue;
                    } else {
                        sb.append(line);
                        sb.append('\r');
                    }
                }
                this.rsaPubKey = sb.toString();
            } catch (IOException ex) {
                System.out.println("读取 weixin4j.rsaPubKey.path 文件出错：" + ex.getMessage());
                ex.printStackTrace();
                return null;
            }
        }
        return rsaPubKey;
    }

    @Override
    public void refresh(RsaXml rsaXml) {
        if (rsaXml == null) {
            throw new IllegalArgumentException("rsaXml can not be null");
        }
        if (Configuration.isDebug()) {
            System.out.println("refresh RSA: " + rsaXml.toString());
        }
        if (!"SUCCESS".equals(rsaXml.getResult_code())) {
            throw new IllegalArgumentException("rsaXml refresh error " + rsaXml.getErr_code() + "," + rsaXml.getErr_code_des());
        }
        try {
            //读取PKCS#1
            File file = new File(rsaPubKeyPkcs1);
            String absolutePath = file.getAbsolutePath();
            Path path = Paths.get(absolutePath);
            try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName("UTF-8"), StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
                writer.write(rsaXml.getPub_key());
            }
            //将PKCC#1转为PKCS#8
            Runtime rt = Runtime.getRuntime();
            Process ps = rt.exec(opensslPath + "openssl rsa -RSAPublicKey_in -in " + rsaPubKeyPkcs1 + " -pubout -out " + rsaPubKeyPkcs8);
            ps.waitFor();
        } catch (IOException | InterruptedException ex) {
            System.out.println("保存 weixin4j.rsaPubKey.path 文件出错：" + ex.getMessage());
            ex.printStackTrace();
        }
    }
}