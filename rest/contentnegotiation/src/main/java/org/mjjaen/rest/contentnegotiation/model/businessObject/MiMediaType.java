package org.mjjaen.rest.contentnegotiation.model.businessObject;

import java.io.Serializable;

import org.springframework.http.MediaType;

public class MiMediaType extends MediaType implements Serializable {
	private static final long serialVersionUID = 8762774510621509634L;
	
	public final static MediaType APPLICATION_XML_UTF8;
	public final static String APPLICATION_XML_UTF8_VALUE = APPLICATION_XML_VALUE + ";charset=UTF-8";;
	
	static {
		APPLICATION_XML_UTF8 = valueOf(APPLICATION_XML_UTF8_VALUE);
	}
	
	public MiMediaType(String type) {
		super(type);
	}
}
