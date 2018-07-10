package com.profisien.as400;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.profisien.as400.component.SocketConnection;

@Component
public class As400ClientRoute extends RouteBuilder {
	
	@Autowired
	CamelContext camelContext;
	
	@Override
	public void configure() throws Exception {
		
		SocketConnection connection = new SocketConnection(camelContext);
		
		from("timer://foo?period=6000")
			.bean(connection, "test");
		
	}

}
