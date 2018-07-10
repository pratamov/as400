package com.profisien.as400;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.camel.CamelContext;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.profisien.as400.converter.Converter;
import com.profisien.as400.converter.ConverterFactory;

@Component
public class As400DataFormat {
	
	private final List<Converter> requestConverters = new ArrayList<>();
	private final List<Converter> responseConverters = new ArrayList<>();
	
	public As400DataFormat(CamelContext camelContext) throws ParserConfigurationException, SAXException, IOException {
		
		InputStream requestInputStream = camelContext.getClassResolver().loadResourceAsStream("response.xml");
		parseRequestFormater(requestInputStream);
		
		InputStream responseInputStream = camelContext.getClassResolver().loadResourceAsStream("response.xml");
		parseResponseFormater(responseInputStream);

	}
	
	private void parseRequestFormater(InputStream xmlInputStream) throws ParserConfigurationException, SAXException, IOException {
		
		ConverterFactory factory = new ConverterFactory();
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Element requestFormatXml = dBuilder.parse(xmlInputStream).getDocumentElement();
		requestFormatXml.normalize();
		
		NodeList structureFormatList = requestFormatXml.getChildNodes();
		for (int i = 0; i < structureFormatList.getLength(); i++) {
			Node structureFormat = structureFormatList.item(i);
			if(structureFormat.getNodeType() == Node.ELEMENT_NODE) {
				NodeList fieldFormatList = structureFormat.getChildNodes();
				for (int j = 0; j < fieldFormatList.getLength(); j++) {
					Node fieldFormat = fieldFormatList.item(j);
					if(fieldFormat.getNodeType() == Node.ELEMENT_NODE) {
						Element field = (Element) fieldFormat;
						factory.build(field);
						
						System.out.println(((Element)structureFormat).getAttribute("name") + " " + field.getAttribute("name"));
					}
				}
			}
		}
		
	}
	
	private void parseResponseFormater(InputStream xmlInputStream) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Element responseFormatXml = dBuilder.parse(xmlInputStream).getDocumentElement();
		responseFormatXml.normalize();
	}
	
}
