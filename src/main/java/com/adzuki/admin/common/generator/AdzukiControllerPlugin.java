package com.adzuki.admin.common.generator;

import java.util.List;

import org.mybatis.generator.api.PluginAdapter;
/**
 * TODO : gen springmvc controller
 * @author yechao
 *
 */
public class AdzukiControllerPlugin extends PluginAdapter {

	@Override
	public boolean validate(List<String> warnings) {
		return false;
	}

}
