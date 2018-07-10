package com.profisien.as400;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.nio.charset.Charset;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class As400ClientProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
		
		Socket socket = new Socket("localhost", 9103);
		DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
		DataInputStream din = new DataInputStream(socket.getInputStream());

		byte[] request = exchange.getIn().getBody(byte[].class);
		dout.write(request);
		dout.flush();
		
		int length = din.readInt();
		if (length > 0) {
			byte[] resbyte = new byte[length];
			din.read(resbyte);
			System.out.println("Reply from Server: " + new String(resbyte, Charset.forName("IBM285")));
		}
		else {
			System.out.println("something wrong");
		}
		
		dout.close();
		din.close();
		socket.close();
		
	}

}
