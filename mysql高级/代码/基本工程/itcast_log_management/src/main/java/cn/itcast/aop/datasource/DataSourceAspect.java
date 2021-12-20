package cn.itcast.aop.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@EnableAspectJAutoProxy
@Order(-9999)
public class DataSourceAspect {


    @Before("execution(* cn.itcast.service.*.*(..))")
    public void beforeExecute(JoinPoint joinPoint){

        String name = joinPoint.getSignature().getName();
        System.out.println("------> 拦截的方法名 : " + name);

        for (String key : ChooseDataSource.METHOD_TYPE_MAP.keySet()) {
            for (String type : ChooseDataSource.METHOD_TYPE_MAP.get(key)) {
                if(name.startsWith(type)){
                    DataSourceHandler.putDataSource(key);
                    System.out.println("---------> 获取当前使用的数据库连接池 : " + key);
                    break;
                }
            }
        }

    }


}
