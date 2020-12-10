package com.dhcc.demo.test;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;

public class Dom4jElementTest {
    public static  int count=0;
    @Test
    public void elementTest() throws IOException {
        Document document = DocumentHelper.createDocument();

        addElement(document,"root/service/sysHead/id").setText("11111");
        addElement(document,"root/service/sysHead/id1/aaaa").setText("11111");
        addElement(document,"root/service/sysHead/id2").setText("11111");
        addElement(document,"root/service/sysHead/id3").setText("11111");
        addElement(document,"root/service/sysHead/id4").setText("11111");
        addElement(document,"root/service/sysHead/id5").setText("11111");

        System.out.println("执行 selectSingleNode 方法"+count+"次");
        System.out.println(formatXml(document));
    }
    public static Element addElement(Document document, String elementPath ) {
        //当前元素名称
        int curElementIndex = elementPath.lastIndexOf("/");
        String curElementName = elementPath.substring(curElementIndex+1);
        //前一个元素
        String preElementPath = elementPath.substring(0, curElementIndex);
        int preElementIndex = preElementPath.lastIndexOf("/");
        String preElementName = preElementPath.substring(preElementIndex+1);

        Element preElement = (Element) document.selectSingleNode(preElementPath);
        count++;
        // 如果前一节点为空继续调用
        if(preElement!=null){
            return  preElement.addElement(curElementName);
        }else{
            // 如果不包含 "/" 则为根节点
            if (!preElementPath.contains("/")) {
                if (document.getRootElement() == null) {
                    preElement=document.addElement(preElementName);
                }
            }else{
                preElement=addElement(document, preElementPath);
            }
        }
        return preElement.addElement(curElementName);
    }
    public static String formatXml(Document doc) throws  IOException {
        OutputFormat formater= OutputFormat.createPrettyPrint();
        formater.setEncoding("UTF-8");
        StringWriter out=new StringWriter();
        XMLWriter writer=new XMLWriter(out,formater);
        writer.write(doc);
        writer.close();
        return out.toString();
    }

}
