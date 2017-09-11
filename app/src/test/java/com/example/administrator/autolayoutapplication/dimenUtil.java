package com.example.administrator.autolayoutapplication;

import android.content.Context;
import android.util.Log;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.junit.Test;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class dimenUtil {

    String path = "C:\\Users\\Administrator\\Desktop\\AutoLyaoutApp\\app\\src\\main\\res\\values\\dimens.xml";


    private String[] start = {"x", "y", "z"};
    private String[] sizeUnit = {"px", "px", "px"};
    private int[] unitSizeCount = {100, 100, 30};

    @Test
    public void test() {
        domCreateXML();

    }

    /**
     * Dom方式，创建 XML
     */
    public String domCreateXML() {
        String xmlWriter = null;
        try {
            // 创建根节点 并设置它的属性 ;
            Element root = new Element("resources");
            // 将根节点添加到文档中；
            Document doc = new Document(root);
            for (int i = 0; i < start.length; i++) {
                String s = start[i];
                String unit = sizeUnit[i];
                int count = unitSizeCount[i];
                for (int j = 0; j < count; j++) {

                    Element elePerson = new Element("dimen");
                    elePerson.setAttribute("name", s + "_" + j + unit);
                    elePerson.setText(j + unit);
                    root.addContent(elePerson);
                }
            }
            // 使xml文件 缩进效果
            Format format = Format.getPrettyFormat();
            XMLOutputter XMLOut = new XMLOutputter(format);
            XMLOut.output(doc, new FileOutputStream(path));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}