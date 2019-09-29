package com.metanit;
/*import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;


public class Main {
    private static ArrayList<Gamer> gamers = new ArrayList<>();

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

        HavingXML havingXML = new HavingXML();


        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        XMLHandler handler = new XMLHandler();



        parser.parse("gamers.xml", handler);

        for (Gamer gamer : gamers)
            System.out.println(String.format("Имя сотрудника: %s, его должность: %s", gamer.getName(), gamer.getLevel()));
    }

    private static class XMLHandler extends DefaultHandler {
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equals("gamer")) {
                String name = attributes.getValue("name");
                String level = attributes.getValue("level");
                gamers.add(new Gamer(name, level));
            }
        }
    }
}*/



import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class Main_1 {
   /* // Список для сотрудников из XML файла
    private static ArrayDeque<Gamer> gamers = new ArrayDeque<>();

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

        HavingXML havingXML = new HavingXML();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse("gamers.xml");
        NodeList gamerElements = document.getDocumentElement().getElementsByTagName("gamer");

        for (int i = 0; i < gamerElements.getLength(); i++) {
            Node employee = gamerElements.item(i);

            NamedNodeMap attributes = employee.getAttributes();

            gamers.add(new Gamer(attributes.getNamedItem("name").getNodeValue(), attributes.getNamedItem("level").getNodeValue()));
        }

        for (Gamer gamer : gamers)
            System.out.println(String.format("Информации о сотруднике: имя - %s, должность - %s.", gamer.getName(), gamer.getLevel()));
    }*/

}

