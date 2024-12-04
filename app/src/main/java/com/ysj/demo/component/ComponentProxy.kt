package com.ysj.demo.component

import com.ysj.lib.bcu.modifier.component.di.api.Component
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy


/**
 * 演示，如果被 *component.di.checkImpl=false*，且 [Component] 注解的接口没有实现类的处理。
 *
 * @author Ysj
 * Create time: 2024/12/4
 */
object ComponentProxy {

    private const val TAG = "ComponentProxy"

    private object InvocationHandlerImpl : InvocationHandler {

        override fun invoke(proxy: Any, method: Method, args: Array<out Any>?): Any? {
            throw IllegalStateException("本异常为功能演示，请在 build.gradle 中将注释掉的 runtimeOnly(project(\":demo1\")) 放开")
        }

    }

    @JvmStatic
    @Suppress("UNCHECKED_CAST")
    fun <T> proxy(clazz: Class<T>): T {
        return Proxy.newProxyInstance(
            clazz.classLoader,
            arrayOf(clazz),
            InvocationHandlerImpl
        ) as T
    }

}