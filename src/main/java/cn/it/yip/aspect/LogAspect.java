package cn.it.yip.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 日志打印切面类
 *
 * @program: blog
 * @author: YipSouL
 * @create: 2019-06-17 16:55
 **/
@Aspect
@Component
@Slf4j
@Order(1)
public class LogAspect {

    private static final String LINE_SEPARATOR = System.lineSeparator();

    public static final ObjectMapper mapper = new ObjectMapper();

    @Pointcut("@annotation(cn.it.yip.aspect.WebLog)")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        //开始请求打印日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        log.info("=================================Start=====================================");
        LogInfo logInfo = getAspectLogDescription(joinPoint, attributes);
        log.info("RequestInfo : {}", logInfo);
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        Long startTime = System.currentTimeMillis();
        Object result = point.proceed();
        //打印出参
        log.info("Response Args : {}", mapper.writeValueAsString(result));
        //执行时长
        log.info("Time-Consuming : {} ms", System.currentTimeMillis() - startTime);
        return result;
    }

    /**
     * 在切点之后织入
     *
     * @throws Throwable
     */
    @After("webLog()")
    public void doAfter() throws Throwable {
        // 接口结束后换行，方便分割查看
        log.info("=================================End=====================================" + LINE_SEPARATOR);
    }

    /**
     * 获取描述信息注解
     *
     * @param joinPoint
     * @return
     */
    private LogInfo getAspectLogDescription(JoinPoint joinPoint, ServletRequestAttributes attributes) throws Exception {
        //获取请求体
        HttpServletRequest request = attributes.getRequest();
        //获取全路径请求名字
        String targetName = joinPoint.getTarget().getClass().getName();
        //方法名
        String methodName = joinPoint.getSignature().getName();
        //参数
        Object[] args = joinPoint.getArgs();
        Class<?> clazz = Class.forName(targetName);
        Method[] methods = clazz.getMethods();
        StringBuilder sb = new StringBuilder();
        for (Method method : methods) {
            //如果方法名相同
            if (methodName.equals(method.getName())) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                //如果该方法的参数数量相同，则为同一个方法
                if (parameterTypes.length == args.length) {
                    sb.append(method.getAnnotation(WebLog.class).description());
                    break;
                }
            }
        }
        return new LogInfo(request.getRequestURL().toString(), sb.toString(), request.getMethod(),
                targetName + "." + methodName, request.getRemoteAddr(), joinPoint.getArgs());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    /**
     * 高内聚内部类
     */
    private class LogInfo {
        private String url;//请求url
        private String description;//请求描述
        private String httpMethod; //http method
        private String classMethod; //controller全路径
        private String ip; //请求ip
        private Object[] requestArgs;

        @Override
        public String toString() {
            return "LogInfo{" +
                    "请求路径url='" + url + '\'' +
                    ", 描述信息description='" + description + '\'' +
                    ",httpMethod='" + httpMethod + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", 请求ip='" + ip + '\'' +
                    ", 请求参数requestArgs=" + Arrays.toString(requestArgs) +
                    '}';
        }
    }
}
