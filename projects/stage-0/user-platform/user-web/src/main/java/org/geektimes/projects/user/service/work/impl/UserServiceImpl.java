package org.geektimes.projects.user.service.work.impl;

import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.repository.work.MyDBUserRepository;
import org.geektimes.projects.user.service.work.UserService;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * {@link }
 *
 * @description: 用户服务实现
 * @author: magic_json
 * @create: 2021-03-03 22:49
 * @since 1.0
 **/
public class UserServiceImpl implements UserService {



    @Override
    public boolean register(User user) {
        return new MyDBUserRepository().save(user);
    }

    @Override
    public boolean deregister(User user) {
        return false;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public User queryUserById(Long id) {
        return null;
    }

    @Override
    public User queryUserByNameAndPassword(String name, String password) {
        return null;
    }


    public static String convert2String(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream));
        return reader.lines().collect(Collectors.joining(
                System.getProperty("line.separator")));
    }


    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;

        Object obj = beanClass.newInstance();

        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            Method setter = property.getWriteMethod();
            if (setter != null) {
                setter.invoke(obj, map.get(property.getName()));
            }
        }

        return obj;
    }
}

