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
        /** *用于存放节点的信息** */  
        Map<String, Object> map = new LinkedHashMap<String, Object>();  
        /** *创建一个新的字符串*** */  
        StringReader xmlReader = new StringReader(xml);  
        /** **创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入 */  
        InputSource xmlSource = new InputSource(xmlReader);  
        /** *创建一个SAXBuilder* */  
        SAXBuilder builder = new SAXBuilder();  
        /** *通过输入源SAX构造一个Document** */  
        Document doc = builder.build(xmlSource);  
        /** *获得根节点** */  
        Element elt = doc.getRootElement();  
        /** *获得BODY节点** */  
        System.out.println(elt.getText());
        Element Head = elt.getChild(getchild);  
        /** *获得Head节点下面的所有子节点*** */  
        List<Element> child = Head.getChildren();  
        /** *遍历出body节点下面所有的子节点，节点名称和内容用put到map* */  
        for (Element childEle : child) {  
            map.put(childEle.getName(), childEle.getText());  
        }  
        return map;  
    } 
    public static String parseXMLgetvalue(String xmlString,String getchild,String childElement)  
            throws JDOMException, IOException { 
    	
    	String value = new String();
        /** *创建一个新的字符串*** */  
        StringReader xmlReader = new StringReader(xmlString);  
        /** **创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入 */  
        InputSource xmlSource = new InputSource(xmlReader);  
        /** *创建一个SAXBuilder* */  
        SAXBuilder builder = new SAXBuilder();  
        /** *通过输入源SAX构造一个Document** */  
        Document doc = builder.build(xmlSource); 
        /** *获得根节点** */  
        Element elt = doc.getRootElement();  
        /** *获得BODY节点** */  
        Element Head = elt.getChild(getchild);
        /** *获得Head节点下面的所有子节点*** */  
        
        List<Element> child = Head.getChildren(); 
        for(Element childEle:child){
        	if(childEle.getName().equals(childElement)){
        		value = childEle.getValue();
        	}
        }
        return value;  
    }  
}