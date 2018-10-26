### 异常处理

1. CookieRememberMeManager 重启系统导致的秘钥不匹配问题
   
>  异常原因:
>  1. 系统前一次启动，并进行了（RememberMe: true ）登录,会在浏览器生成 Cookie。
>  2. CookieManager没有配置好特定Cookie加密秘钥。
>  3. 重启服务器之后导致秘钥改变。

            org.apache.shiro.crypto.CryptoException: Unable to execute 'doFinal' with cipher instance [javax.crypto.Cipher@48c22c79].
    

