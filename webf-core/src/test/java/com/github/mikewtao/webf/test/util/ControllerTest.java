package com.github.mikewtao.webf.test.util;

import com.github.mikewtao.webf.annotation.Controller;
import com.github.mikewtao.webf.annotation.Handler;
import com.github.mikewtao.webf.annotation.RequestMethod;

@Controller
public class ControllerTest {

	@Handler(value="/test",method=RequestMethod.GET)
	public void Test(){
		
	}
}
