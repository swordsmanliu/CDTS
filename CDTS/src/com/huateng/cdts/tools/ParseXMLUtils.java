package com.huateng.cdts.tools;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

public class ParseXMLUtils {
    public static Map<String, Object> parseXML(String xml,String getchild)  
            throws JDOMException, IOException {  
        /** *���ڴ�Žڵ����Ϣ** */  
        Map<String, Object> map = new LinkedHashMap<String, Object>();  
        /** *����һ���µ��ַ���*** */  
        StringReader xmlReader = new StringReader(xml);  
        /** **�����µ�����ԴSAX ��������ʹ�� InputSource ������ȷ����ζ�ȡ XML ���� */  
        InputSource xmlSource = new InputSource(xmlReader);  
        /** *����һ��SAXBuilder* */  
        SAXBuilder builder = new SAXBuilder();  
        /** *ͨ������ԴSAX����һ��Document** */  
        Document doc = builder.build(xmlSource);  
        /** *��ø��ڵ�** */  
        Element elt = doc.getRootElement();  
        /** *���BODY�ڵ�** */  
        System.out.println(elt.getText());
        Element Head = elt.getChild(getchild);  
        /** *���Head�ڵ�����������ӽڵ�*** */  
        List<Element> child = Head.getChildren();  
        /** *������body�ڵ��������е��ӽڵ㣬�ڵ����ƺ�������put��map* */  
        for (Element childEle : child) {  
            map.put(childEle.getName(), childEle.getText());  
        }  
        return map;  
    } 
    public static String parseXMLgetvalue(String xmlString,String getchild,String childElement)  
            throws JDOMException, IOException { 
    	
    	String value = new String();
        /** *����һ���µ��ַ���*** */  
        StringReader xmlReader = new StringReader(xmlString);  
        /** **�����µ�����ԴSAX ��������ʹ�� InputSource ������ȷ����ζ�ȡ XML ���� */  
        InputSource xmlSource = new InputSource(xmlReader);  
        /** *����һ��SAXBuilder* */  
        SAXBuilder builder = new SAXBuilder();  
        /** *ͨ������ԴSAX����һ��Document** */  
        Document doc = builder.build(xmlSource); 
        /** *��ø��ڵ�** */  
        Element elt = doc.getRootElement();  
        /** *���BODY�ڵ�** */  
        Element Head = elt.getChild(getchild);
        /** *���Head�ڵ�����������ӽڵ�*** */  
        
        List<Element> child = Head.getChildren(); 
        for(Element childEle:child){
        	if(childEle.getName().equals(childElement)){
        		value = childEle.getValue();
        	}
        }
        return value;  
    }  
}