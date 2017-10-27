package org.eclipse.framework.webf.core;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogRecordTest {

	private static final Logger logger=LoggerFactory.getLogger(LogRecordTest.class);
	
	@Test
	public void TestLog(){
		//System.out.println("1111");
		logger.info("打印一行日志");
	}

}
