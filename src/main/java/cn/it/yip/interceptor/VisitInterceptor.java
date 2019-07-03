package cn.it.yip.interceptor;

import cn.it.yip.Enum.ExceptionEnum;
import cn.it.yip.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @program: blog
 * @author: YipSouL
 * @create: 2019-07-01 23:02
 **/
public class VisitInterceptor implements HandlerInterceptor {
    private final long REQUEST_COUNT = 1;//请求次数
    private final String REQUEST = "request:";
    private final Long DEFAULT_TIME_OUT = 30L;//设置Key的超时时间
    private final Long MOST_COUNT = 100L;//最多请求次数
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = request.getRemoteAddr();
        //访问次数加1
        long count = redisTemplate.opsForValue().increment(REQUEST + ip, REQUEST_COUNT);
        if (count == REQUEST_COUNT) {
            redisTemplate.expire(REQUEST + ip, DEFAULT_TIME_OUT, TimeUnit.SECONDS);
        }
        //如果请求大于最大次数
        if (count > MOST_COUNT) {
            throw new BaseException(ExceptionEnum.REQUEST_TOO_FAST);
        }
        return true;
    }

}
