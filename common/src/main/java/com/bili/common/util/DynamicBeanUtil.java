package com.bili.common.util;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
 
@Component
public class DynamicBeanUtil {
 
    @Resource
    private ApplicationContext applicationContext;
 
    public Object createBean(Class<?> beanClass) throws Exception {
        // 创建Bean
        Object bean = beanClass.getDeclaredConstructor().newInstance();
 
        // 如果需要对bean进行赋值，可以使用applicationContext.getAutowireCapableBeanFactory()
        // 这里是示例，假设我们有一个setName方法
        applicationContext.getAutowireCapableBeanFactory().autowireBean(bean);
 
        return bean;
    }
}