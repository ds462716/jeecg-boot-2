package org.jeecg.common.util;

import org.jeecg.common.api.vo.Result;

import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 验证javaBean的各个属性值是否合法
 * @author laowang
 */
public class ValidField {
    /**
     * 获取属性名数组
     * @param o
     * @return
     */
    /**
     * @NotNull缺少参数，@NotBlank参数不能为空
     * @param o
     * @return
     */
    public static Result checkField(Object o){
        Field[] fields=o.getClass().getDeclaredFields();
        Result<Object> result = new Result<>();
        String msg="参数:";//保存返回的提示信息
        boolean rightOrNot=true;//参数是否合法
        boolean hostCodeLegal=true;//主机代码是否合法
        int code=500;
        for(int i=0;i<fields.length;i++){
            Annotation notNullField = fields[i].getAnnotation(NotNull.class);//该属性头是否有NotNull注解
            if (oConvertUtils.isEmpty(notNullField))continue;//该属性头是否有NotNull注解
            String fieldName = fields[i].getName();//如果有NotNull注解，则拿到属性名和值
            Object fieldValueByName = getFieldValueByName(fieldName, o);
            if (oConvertUtils.isEmpty(fieldValueByName)){
                msg+=fieldName+",";
                rightOrNot=false;
            }
        }
        if (rightOrNot==false)msg+="不能为空";
        result.setMessage(msg);
        result.setSuccess(rightOrNot);
        result.setTimestamp(new Date().getTime());
        result.setCode(code);
        return result;
    }

    /**
     * 根据属性名获取属性值
     * @param fieldName
     * @param o
     * @return
     */
    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * 除去目标对象的前后空格
     * @param o
     * @return
     */
    public static Result trimSpace(Object o) {
        Class<?> clazz = o.getClass();
        Method[] methods =clazz.getMethods();
        Field[] declaredFields = clazz.getDeclaredFields();
        for(Field field:declaredFields){
            field.setAccessible(true);
            Object fieldValueByName = getFieldValueByName(field.getName(), o);//字段值
            
            if (oConvertUtils.isEmpty(fieldValueByName))continue;//是否为空
            if (!field.getType().equals(String.class))continue;//是否是String类型
            String afterTrim = fieldValueByName.toString().trim();//处理
            for (Method method:methods){
                String fieldName = field.getName();
                String firstLetter = fieldName.substring(0, 1).toUpperCase();
                String setter = "set" + firstLetter + fieldName.substring(1);
                if ((method.getName()).equals(setter)){
                    try {
                        method.invoke(o,afterTrim);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return new Result().success("操作成功");
    }
    /**
     * 校验追溯码是否与当前环节对应
     */
    public static Result checkTraceCode(String traceCode,String targetLink) {
        String sourceLink = traceCode.substring(1, 3);
        if (!sourceLink.equals(targetLink))return new Result(false,"追溯码与环节不匹配,追溯码的第二第三位正确的对应关系应为：药材经营：11，种植：04，饮片加工：23，饮片经营：31",500,new Date().getTime());
        return new Result().success("操作成功");
    }



}
