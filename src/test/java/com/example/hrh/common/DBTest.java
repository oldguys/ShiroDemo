package com.example.hrh.common;

import com.example.hrh.module.common.services.DbRegister;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.junit.Test;

import java.security.Key;


/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/16 0016 09:01
 */
public class DBTest {

    @Test
    public void test() {
        new DbRegister().registerClassToDB("com.example.hrh.module.sys.dao.entities");
    }

    @Test
    public void testEncrypt() {

        AesCipherService aesCipherService = new AesCipherService();


        Key key = aesCipherService.generateNewKey();
        System.out.println("format:" + key.getFormat());
        System.out.println("generateNewKey:" + Hex.encodeToString(key.getEncoded()));

        String  newEncodeCipherKey = Base64.encodeToString(key.getEncoded());
        System.out.println("newEncodeCipherKey:" + newEncodeCipherKey);

        byte[] newCipherKey = Base64.decode(newEncodeCipherKey);
        System.out.println("newCipherKey:" + Hex.encodeToString(newCipherKey));

        byte[] cipherKey = Base64.decode("wGiHplamyXlVB11UXWol8g==");
        System.out.println("cipherKey:" + Hex.encodeToString(cipherKey));
    }
}
