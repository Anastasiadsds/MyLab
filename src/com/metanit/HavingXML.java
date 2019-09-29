package com.metanit;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import java.io.*;
import java.util.*;
import java.io.IOException;


//export GAMERS_PATH;

    public class HavingXML {

        private static Boolean savePermission;
        public static String allLines = "";

        /**
         * Searches for PATH and converts file to ArrayDeque
         *
         * @return String with PATH to the file
         */

        public static String getFileName() {
            String collectionPath = System.getenv("GAMERS_PATH").trim();

            String extension = "";

            int i = collectionPath.lastIndexOf('.');
            if (i > 0) {
                extension = collectionPath.substring(i + 1);
            }

            try {
                File file = new File(collectionPath);
                if (file.isDirectory()) {
                    System.out.println("Variable leads to a directory: " + file.getName());
                    return null;
                }
            } catch (Exception e) {
            }

            if (collectionPath == null) {
                System.out.println("The environment variable GAMERS_PATH is not set!");
                return null;
            } else if (collectionPath.isEmpty()) {
                System.out.println("The environment variable GAMERS_PATH can not be void!");
                return null;
            } else if (!extension.equals("xml")) {
                System.out.println("Be careful: the extension of the file is not XML!");
                return collectionPath;
            } else {
                return collectionPath;
            }
        }

        /**
         * Converts XML file to ArrayDeque
         * @return
         */

        public static ArrayDeque<Gamer> convertToArrayDeque() throws IOException, SAXException, ParserConfigurationException, Exception {
            ArrayDeque<Gamer> gamers = new ArrayDeque<>();

            HavingXML havingXML = new HavingXML();

            final String FILENAME = getFileName(); //getFileName
            try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

                String line;
                while ((line = br.readLine()) != null) {
                    allLines = allLines + line;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                InputSource document = new InputSource(new StringReader(allLines));
                Document gamerDocument = builder.parse(document);
            NodeList gamerElements = gamerDocument.getDocumentElement().getElementsByTagName("gamer");

            for (int i = 0; i < gamerElements.getLength(); i++) {
                Node gamer = gamerElements.item(i);

                NamedNodeMap attributes = gamer.getAttributes();

                try
                {
                    String str = attributes.getNamedItem("level").getNodeValue();
                    double d = Double.parseDouble(str);
                }
                catch(NumberFormatException nfe)
                {
                    System.out.println("Ошибка при импорте игрока. Возможно, игрок имеет невозможный возраст");
                    //throw new Exception();
                }

                gamers.add(new Gamer(attributes.getNamedItem("name").getNodeValue(), attributes.getNamedItem("level").getNodeValue()));

            }

            for (Gamer gamer : gamers) {
                gamer.welcome();
                System.out.println(String.format("Информации об игроке: имя - %s, уровень - %s.", gamer.getName(), gamer.getLevel()));
            }
            return gamers;
        }

        public static void save(ArrayDeque<Gamer> gamers){
            try(FileOutputStream n = new FileOutputStream(HavingXML.getFileName(), false);
                PrintWriter printWriter = new PrintWriter (n)){

                Iterator<Gamer> iterator = gamers.iterator();
                while (iterator.hasNext()) {
                    StringBuffer s = new StringBuffer();
                    Gamer temp = iterator.next();

                    s.append("\"" + temp.getName() + "\"," + "\""+ temp.getLevel() + "\"");

                    printWriter.println(s);
                }

                System.out.println("<<<<<<<<<<<<<<< The source file has been updated >>>>>>>>>>>>>>>");

            }catch (Exception e){
                System.out.println("Something bad has happened; Can't save!");
            }
        }

        public static boolean checkFileRead(){
            try {
                File file = new File(HavingXML.getFileName());

                boolean exists = file.exists();
                if (exists) {
                    if (!file.canRead()) {
                        System.out.println("Permission denied: Can't read the file!");
                        return false;
                    }else {
                        return true;
                    }
                } else {
                    System.out.println("File does not exist!");
                    return false;
                }
            }catch (Exception e){
                return false;}
        }

        public static boolean checkFileWrite(){
            try {
                File file = new File(HavingXML.getFileName());
                boolean exists = file.exists();
                if (exists) {
                    if (!file.canWrite()) {
                        System.out.println("Permission denied: Can't write to the file!");
                        System.out.println("hehe");
                        return false;
                    }else {
                        return true;
                    }
                } else {
                    System.out.println("File does not exist!");
                    return false;
                }
            }catch (Exception e){return false;}
        }

        public static void setSavePermission(Boolean mode){
            savePermission = mode;
        }
        public static Boolean getSavePermission(){
            return savePermission;
        }

    }
