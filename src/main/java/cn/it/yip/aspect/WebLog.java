package cn.it.yip.aspect;

import java.lang.annotation.*;

/**
 * @program: blog
 * @author: YipSouL
 * @create: 2019-06-17 17:10
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface WebLog {

    /**
     * 方法描述信息
     *
     * @return
     */
    String description() default "";
}
