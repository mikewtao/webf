package org.eclipse.framework.webf.core;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class XmlViewResolver extends ViewResolve {

	@Override
	public Object renderView(Object obj, Object param){
		Class<?> clzz=(Class<?>) param;
		try {
			return bean2Xml(obj,clzz);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String bean2Xml(Object obj, Class<?> load) throws Exception {
		JAXBContext context = JAXBContext.newInstance(load);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "GBK");
		StringWriter writer = new StringWriter();
		marshaller.marshal(obj, writer);
		return writer.toString();
	}
}
