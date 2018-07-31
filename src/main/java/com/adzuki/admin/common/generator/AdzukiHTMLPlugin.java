package com.adzuki.admin.common.generator;

import java.util.List;

import org.mybatis.generator.api.PluginAdapter;

/**
 * TODO : gen springmvc html
 * @author yechao
 *
 */
public class AdzukiHTMLPlugin extends PluginAdapter {

	@Override
	public boolean validate(List<String> warnings) {
		return false;
	}

}
