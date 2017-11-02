package com.github.mikewtao.webf.test.util;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.mikewtao.webf.utils.webfUtil;

public class WebfUtilLoadClassTest {

	@Test
	public void test() {
		webfUtil.loadClass("com.github.mikewtao.webf.InterceptorAdapter");
	}

}
