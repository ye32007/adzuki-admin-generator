package com.adzuki.admin.common.generator;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.JavaFormatter;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.DefaultJavaFormatter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class AdzukiManagerPlugin extends PluginAdapter {
	
    //BaseManager
    private String baseManager = "";
    private String targetPackage = "";
    private String targetProject = "";
    private String baseManagerName = "";
    private String genType = "mybatis";//
    
    @Override
    public boolean validate(List<String> warnings) {
    	baseManager = properties.get("baseManager").toString();
        targetPackage = properties.get("targetPackage").toString();
        targetProject = properties.get("targetProject").toString();
        String genType =  properties.get("genType").toString();
        if(StringUtils.isNotEmpty(genType) && (genType.equals("mybatis") || genType.equals("jpa"))) {
        	this.genType = genType;
        }else{
        	this.genType = "mybatis";
        }
        boolean valid = stringHasValue(baseManager) && stringHasValue(targetPackage) && stringHasValue(targetProject);

        if(!valid){
            if (!stringHasValue(baseManager)) {
                warnings.add(getString("ValidationError.99", //$NON-NLS-1$
                        "MyBaseServicePlugin", //$NON-NLS-1$
                        "baseManager")); //$NON-NLS-1$
            }
            if (!stringHasValue(targetPackage)) {
                warnings.add(getString("ValidationError.99", //$NON-NLS-1$
                        "MyBaseServicePlugin", //$NON-NLS-1$
                        "targetPackage")); //$NON-NLS-1$
            }
            if (!stringHasValue(targetProject)) {
                warnings.add(getString("ValidationError.99", //$NON-NLS-1$
                        "MyBaseServicePlugin", //$NON-NLS-1$
                        "targetProject")); //$NON-NLS-1$
            }
        }
        //com.xxxx.xx.xx.BaseManager -- > BaseManager
        baseManagerName = baseManager.substring(baseManager.lastIndexOf(".") + 1, baseManager.length());
        return valid;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        String modelType = introspectedTable.getBaseRecordType();
        List<GeneratedJavaFile> files = new ArrayList<GeneratedJavaFile>();
        files.add(generatedBusinessServiceFile(modelType));
        return files;
    }

    /**
     * 生成业务接口
     * @param recordType
     * @return
     */
    private GeneratedJavaFile generatedBusinessServiceFile(String modelType) {
        //获取当前业务类型，如t_user，对应的实体是User,所以实际获取的是User
        String modelName = modelType.substring(modelType.lastIndexOf(".") + 1, modelType.length());
        //拼接业务类名称；如：UserManager
        String managerName = modelName + "Manager";
        FullyQualifiedJavaType managerType = new FullyQualifiedJavaType(targetPackage + "." + managerName);
        TopLevelClass manager = new TopLevelClass(managerType);
        manager.setVisibility(JavaVisibility.PUBLIC);
        manager.addImportedType(new FullyQualifiedJavaType(modelType));
        manager.addImportedType(new FullyQualifiedJavaType(baseManager));
        manager.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Service"));
        manager.addAnnotation("@Service");
        //继承BaseManager
        if("jpa".equals(genType)){
        	manager.setSuperClass(new FullyQualifiedJavaType(baseManagerName + "<" + modelName + ",Long>"));
        }else{
        	manager.setSuperClass(new FullyQualifiedJavaType(baseManagerName + "<" + modelName + ">"));
        }
        JavaFormatter javaFormatter = new DefaultJavaFormatter();
        javaFormatter.setContext(context);
        return new GeneratedJavaFile(manager, targetProject, javaFormatter);
    }





}
