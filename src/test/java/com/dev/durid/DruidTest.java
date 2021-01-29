package com.dev.durid;

import com.alibaba.druid.filter.config.ConfigTools;
import org.junit.Test;

/**
 * @author liaonanzhou
 * @date 2021-01-29 10:38
 * @description
 */
public class DruidTest {

    private static final String PUBLIC_KEY =
            "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBANHjzIc4MSv/i6fUHDauJLgyGiBlhhvFvyisRONfRLXkntvWVXP06m9tILi/oZ2fZewBHR6ZM0E4KaK54j7ZY9UCAwEAAQ==";
    private static final String PRIVATE_KEY =
            "MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEA0ePMhzgxK/+Lp9QcNq4kuDIaIGWGG8W/KKxE419EteSe29ZVc/Tqb20guL+hnZ9l7AEdHpkzQTgporniPtlj1QIDAQABAkBEDwzsL+Fp9fRQGGyJopBPlhd4t9LzwHyK8MlpvSOnowM0JtWMuAMOsZ6xWeQFVsRMRojweTX5aqJmlDzqHqWBAiEA6vdwcdSH6qqYzcyBQnnrsIWgpN0D3cr7xDGr04TPi6ECIQDkrbHGCr2/2o2YJG75iV1pcj6g6bn5T4kGtJeGsYPLtQIgYE2sc6tFTeRAHtF8BTANbttGHWxMwmII0L//CgG8QyECIFPllc8+qZb1q/UhiMVsLCU/kNKgbWaWU4NuRhlYjy3xAiBeLg0bsMfQGeH/DKj36HuN8++tlM4PIUm/FI1J+GoeJw==";
    private static final String ENCRYPT_PWD = "LGxKQ7YYp9xuQdGE7gu4jqHCGgy6ZlwxMuL3WsEvtT1LUYjFe5xX/UcoSbuS9ZMoidxCDoMq3ifUSQWgkvpp/A==";

    /**
     * 获取druid publicKey、privateKey、password
     * java -cp C:\Users\liaonanzhou\.m2\repository\com\alibaba\druid\1.2.2\druid-1.2.2.jar com.alibaba.druid.filter.config.ConfigTools ${password}
     **/
    @Test
    public void decryptPwd() {
        try {
            String pwd = ConfigTools.decrypt(PUBLIC_KEY, ENCRYPT_PWD);
            System.out.println(pwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void encryptPwd() {
        try {
            String pwd = ConfigTools.encrypt(PRIVATE_KEY, "root");
            System.out.println(pwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
