### SpringBoot

1. 子类中的@bean可以被父类直接注册，不需要添加额外的@Configuration
    
        @Configuration
        public class ShiroConfiguration {
      
            public class RememberMeConfiguration {
              
                /**
                * SimpleCookie 会被注入到Spring容器
                **/
                @Bean
                public SimpleCookie simpleCookie() {
                    SimpleCookie cookie = new SimpleCookie("shiro-cookie");
                    return cookie;
                }
            }
        }

