package cn.it.yip.handler;

import cn.it.yip.exception.BaseException;
import cn.it.yip.vo.ExceptionVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * controller層異常處理
 * @program: blog
 * @author: YipSouL
 * @create: 2019-06-17 16:37
 **/
@ControllerAdvice
@Slf4j
public class CommonExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public ModelAndView exceptionHanler(HttpServletRequest request,Exception e) throws Exception {
        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
            log.error("Request URL : {}, Excepetion : {}",request.getRequestURL(),e.getMessage());
            throw e;
        }
        BaseException base = (BaseException) e;
        ModelAndView mv = new ModelAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("message",new ExceptionVo(base.getE()));
        log.error("Request URL : {}, status : {}, message : {}",request.getRequestURL(),base.getE().getCode(),base.getE().getMsg());
        mv.setViewName("error/error");
        return mv;
    }
}
