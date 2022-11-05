package com.example.s01tomcatwebsocket.message.handler;

import com.example.s01tomcatwebsocket.message.Message;
import org.springframework.aop.framework.AopProxyUtils;

import javax.websocket.Session;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

public interface MessageHandler<T extends Message> {
    /**
     * 执行处理消息
     *
     * @param session 会话
     * @param message 消息
     */
    void execute(Session session, Message message);

    /**
     * @return 消息类型，即每个 Message 实现类上的 TYPE 静态字段
     */
    String getType();


    /**
    * 作用: 解析 MessageHandler 对象的 Message 范型实参
    * */
    static Class<? extends Message> getMessageClass(MessageHandler<?> unknown) {
        // 获取class(或有AOP代理)
        Class<?> knownClass = AopProxyUtils.ultimateTargetClass(unknown);
        // 获得接口的 Type 数组
        Type[] interfaces = knownClass.getGenericInterfaces();
        Class<?> superclass = knownClass.getSuperclass();
        // 如果存在超类且超类是MessageHandler类型，则以上层为准(直到到达接入层)
        while (Objects.nonNull(superclass) && MessageHandler.class.isAssignableFrom(superclass)) {
            interfaces = superclass.getGenericInterfaces();
            superclass = superclass.getSuperclass();
        }
        // 遍历接入层的接口列表，如果是MessageHandler<T>则返回范型实参
        for (Type type : interfaces) {
            if (type instanceof ParameterizedType parameterizedType) {
                if (Objects.equals(parameterizedType.getRawType(), MessageHandler.class)) {
                    return (Class<Message>) parameterizedType.getActualTypeArguments()[0];
                }
            }
        }
        throw new IllegalStateException("Type::%s can't find Message proto".formatted(unknown));
    }

}