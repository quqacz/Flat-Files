package com.project;

// CSV READ/WRITE
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import java.io.BufferedReader;
import java.io.FileReader;


// JSON WRITE
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

// JSON READ
import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

// XML READ
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

// XML WRITE
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Main {

    public static void main(String[] args) throws IOException, ParseException, FileNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException {


        // CSV READ
        {
            String csvPath = "datasetCSV.csv";
            String[] headers = {"id", "first_name", "last_name", "email", "gender", "ip_address"};
            try(
                    BufferedReader br = new BufferedReader(new FileReader(csvPath));
                    CSVParser parser = CSVFormat.DEFAULT.withDelimiter(',').withHeader().parse(br);
            ) {
                for(CSVRecord record : parser) {
                    String out = "";
                    for(String header : headers){
                        out += record.get(header) + "; ";
                    }
                    System.out.println(out);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        // CSV WRITE
        {
            String[][] mockData = {{"Krishnah", "Pinwell", "Waters and Sons", "Web Developer IV"},
                    {"Isaiah", "Reisenberg", "Fadel-Mann", "Recruiter"},
                    {"Gaye", "Filipczak", "Bauch Inc", "Product Engineer"},
                    {"Flora", "Schulter", "Berge Group", "Database Administrator I"},
                    {"Joyann", "Top", "Emmerich-Hilll", "Librarian"}
            };

            String[] headers = {"first_name", "last_name", "company", "position"};

            FileWriter out = new FileWriter("outputCSV.csv");
            try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT
                    .withHeader(headers))) {
                for(int i = 0; i < mockData.length; i++){
                    printer.printRecord(mockData[i][0], mockData[i][1], mockData[i][2], mockData[i][3]);
                }
            }
        }

        // JSON WRITE
        {
            JSONObject jo = new JSONObject();

            jo.put("firstName", "Joe");
            jo.put("lastName", "Doe");
            jo.put("age", 23);

            Map m = new LinkedHashMap(2);
            m.put("streetAddress", "Banacha");
            m.put("city", "Lodz");


            jo.put("address", m);

            JSONArray ja = new JSONArray();

            m = new LinkedHashMap(2);
            m.put("type", "home");
            m.put("number", "123456789");

            ja.add(m);

            m = new LinkedHashMap(2);
            m.put("type", "work");
            m.put("number", "987456123");

            ja.add(m);

            jo.put("phoneNumbers", ja);

            PrintWriter pw = new PrintWriter("outputJSON.json");
            pw.write(jo.toJSONString());

            pw.flush();
            pw.close();
        }

        // JSON READ
        {
            Object obj = new JSONParser().parse(new FileReader("outputJSON.json"));

            JSONObject jo = (JSONObject) obj;

            String firstName = (String) jo.get("firstName");
            String lastName = (String) jo.get("lastName");

            System.out.println(firstName);
            System.out.println(lastName);

            long age = (long) jo.get("age");
            System.out.println(age);

            Map address = ((Map)jo.get("address"));

            Iterator<Map.Entry> itr1 = address.entrySet().iterator();
            while (itr1.hasNext()) {
                Map.Entry pair = itr1.next();
                System.out.println(pair.getKey() + " : " + pair.getValue());
            }

            JSONArray ja = (JSONArray) jo.get("phoneNumbers");

            Iterator itr2 = ja.iterator();

            while (itr2.hasNext())
            {
                itr1 = ((Map) itr2.next()).entrySet().iterator();
                while (itr1.hasNext()) {
                    Map.Entry pair = itr1.next();
                    System.out.println(pair.getKey() + " : " + pair.getValue());
                }
            }
        }

        // XML READ
        {
            try {
                String xmlPath = "datasetXML.xml";
                File inputFile = new File(xmlPath);
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(inputFile);
                doc.getDocumentElement().normalize();
                System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
                NodeList nList = doc.getElementsByTagName("record");
                System.out.println("----------------------------");

                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        System.out.println("id: " + eElement.getElementsByTagName("id").item(0).getTextContent());
                        System.out.println("First Name: " + eElement.getElementsByTagName("first_name").item(0).getTextContent());
                        System.out.println("Last Name: " + eElement.getElementsByTagName("last_name").item(0).getTextContent());
                        System.out.println("email: " + eElement.getElementsByTagName("email").item(0).getTextContent());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // XML WRITE
        {
            String[][] mockData = {{"Krishnah", "Pinwell", "Waters and Sons", "Web Developer IV"},
                    {"Isaiah", "Reisenberg", "Fadel-Mann", "Recruiter"},
                    {"Gaye", "Filipczak", "Bauch Inc", "Product Engineer"},
                    {"Flora", "Schulter", "Berge Group", "Database Administrator I"},
                    {"Joyann", "Top", "Emmerich-Hilll", "Librarian"}
            };

            try {

                DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
                Document document = documentBuilder.newDocument();

                Element root = document.createElement("subcontractors");
                document.appendChild(root);

                for(int i = 0; i < mockData.length; i++) {
                    Element employee = document.createElement("employee");
                    root.appendChild(employee);


                    Element firstName = document.createElement("firstName");
                    firstName.appendChild(document.createTextNode(mockData[i][0]));
                    employee.appendChild(firstName);

                    Element lastName = document.createElement("lastName");
                    lastName.appendChild(document.createTextNode(mockData[i][1]));
                    employee.appendChild(lastName);


                    Element company = document.createElement("company");
                    company.appendChild(document.createTextNode(mockData[i][2]));
                    employee.appendChild(company);

                    Element position = document.createElement("position");
                    position.appendChild(document.createTextNode(mockData[i][3]));
                    employee.appendChild(position);
                }

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource domSource = new DOMSource(document);
                StreamResult streamResult = new StreamResult(new File("outputXML.xml"));

                transformer.transform(domSource, streamResult);

            } catch (ParserConfigurationException pce) {
                pce.printStackTrace();
            } catch (TransformerException tfe) {
                tfe.printStackTrace();
            }
        }
    }

}
