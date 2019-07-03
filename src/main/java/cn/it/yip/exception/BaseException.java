package cn.it.yip.exception;

import cn.it.yip.Enum.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * 自定义异常
 * @program: blog
 * @author: YipSouL
 * @create: 2019-06-28 21:48
 **/
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseException extends RuntimeException {
        private ExceptionEnum e;
}
