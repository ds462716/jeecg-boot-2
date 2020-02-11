package org.jeecg.modules.aspect;

import com.alibaba.fastjson.JSONArray;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.jeecg.common.api.vo.Result;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Set;
@Aspect
@Component
public class ApiAspect {
    private ObjectError error;
    private ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final ExecutableValidator validator = factory.getValidator().forExecutables();
    private <T> Set<ConstraintViolation<T>> validMethodParams(T obj, Method method, Object[] params) {
        return validator.validateParameters(obj, method, params);
    }
    @Pointcut("execution(public * org.jeecg.modules.api.webservice.*ServiceImpl.*(..))")
    public void valid() {
    }
    @Around("valid()")
    public Object around(ProceedingJoinPoint pjp) {
        try {
            /**
             * 取参数，如果没有参数则执行被拦截方法
             */
            Object[] objects = pjp.getArgs();
            if (objects.length == 0) {
                return pjp.proceed();
            }

            /**
             * 校验普通参数
             */
            //  获得切入目标对象
            Object target = pjp.getThis();
            // 获得切入的方法
            Method method = ((MethodSignature) pjp.getSignature()).getMethod();
            // 执行校验，获得校验结果
            Set<ConstraintViolation<Object>> validResult = validMethodParams(target, method, objects);
            String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);// 获得方法的参数名称   
            //如果有校验不通过的
            if (!validResult.isEmpty()) {
                String  errorMsg="参数：";
                for(ConstraintViolation<Object> constraintViolation : validResult) {
                    PathImpl pathImpl = (PathImpl) constraintViolation.getPropertyPath();  // 获得校验的参数路径信息
                    int paramIndex = pathImpl.getLeafNode().getParameterIndex(); // 获得校验的参数位置
                    String paramName = parameterNames[paramIndex];  // 获得校验的参数名称
                    errorMsg+=paramName;
                }
                //返回第一条
                errorMsg+=validResult.iterator().next().getMessage();
                return JSONArray.toJSON(new Result(false,errorMsg,500,new Date().getTime())).toString();
            }
            /**
             * 异常处理
             */
            Object proceed;
            try {
                 proceed = pjp.proceed();
            }catch (Exception e){
                return JSONArray.toJSON(new Result(false, e.getMessage(), 500, new Date().getTime())).toString();
            }
            return proceed;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }
}
