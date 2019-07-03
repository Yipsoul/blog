package cn.it.yip.config;


import cn.it.yip.interceptor.LoginInterceptor;
import cn.it.yip.interceptor.VisitInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: blog
 * @author: YipSouL
 * @create: 2019-06-18 10:55
 **/
@Configuration
public class MyWebConfig implements WebMvcConfigurer {
    @Value("${file.staticAccessPath}")
    private String staticAccessPath;
    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //添加资源目录映射
        //TODO 当部署到linux服务器的时候addResourceLocations里面的参数要改成("file:")
       registry.addResourceHandler(staticAccessPath).addResourceLocations("file:"+uploadFolder);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加登录后台拦截
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/admin/**")
                .excludePathPatterns("/admin", "/admin/login", "/admin/editPassword", "/admin/changePassword");
        registry.addInterceptor(this.getVisistInterceptor()).addPathPatterns("/**");

    }
    @Bean
    public VisitInterceptor getVisistInterceptor(){
        return new VisitInterceptor();
    }
}
