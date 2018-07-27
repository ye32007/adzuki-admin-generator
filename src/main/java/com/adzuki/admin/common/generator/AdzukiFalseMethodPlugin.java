package com.adzuki.admin.common.generator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import tk.mybatis.mapper.generator.FalseMethodPlugin;

public class AdzukiFalseMethodPlugin extends FalseMethodPlugin{
	
	
	 /**
     * 使用lombok，废弃getter方法
     */
    @Override
    public boolean modelGetterMethodGenerated(Method method,TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable,Plugin.ModelClassType modelClassType) {
        return false;
    }
    
    /**
     * 使用lombok，废弃setter方法
     */
    @Override
    public boolean modelSetterMethodGenerated(Method method,TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable,Plugin.ModelClassType modelClassType) {
        return false;
    }

}
