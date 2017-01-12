 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ik1004labb5;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author TErik
 */
public class DAOHundXML implements DAOHund {

    private Document doc;

    @Override
    public void add(DTOHund dtoHund) {
        doc = getDOMDocument();
        Element root = doc.getDocumentElement();
        Element hund = doc.createElement("hund");

        hund.setAttribute("id", Integer.toString(dtoHund.id));

        Element namn = doc.createElement("namn");
        namn.setTextContent(dtoHund.namn);
        Element ras = doc.createElement("ras");
        ras.setTextContent(dtoHund.ras);
        Element bildURL = doc.createElement("bildURL");
        bildURL.setTextContent(dtoHund.bildURL);

        hund.appendChild(namn);
        hund.appendChild(ras);
        hund.appendChild(bildURL);

        root.appendChild(hund);

        writeToXML(doc);

    }

    @Override
    public void delete(int id) {
        doc = getDOMDocument();
        Element root = doc.getDocumentElement();
        NodeList nl = root.getElementsByTagName("hund");
        String soktId;

        for (int i = 0; i < nl.getLength(); i++) {
            Element hund = (Element) nl.item(i);

            if (hund.hasAttributes()) {
                soktId = hund.getAttribute("id");
                if (soktId.equals(Integer.toString(id))) {
                    root.removeChild(hund);
                    break;
                }
            }

        }

        writeToXML(doc);
    }

    @Override
    public void update(DTOHund dtoHund) {
        doc = getDOMDocument();
        Element root = doc.getDocumentElement();
        NodeList nl = root.getElementsByTagName("hund");
        String soktId;

        for (int i = 0; i < nl.getLength(); i++) {
            Element hund = (Element) nl.item(i);
            if (hund.hasAttributes()) {
                soktId = hund.getAttribute("id");
                if (soktId.equals(Integer.toString(dtoHund.id))) {
                    hund.getElementsByTagName("namn").item(0).setTextContent(dtoHund.namn);
                    hund.getElementsByTagName("ras").item(0).setTextContent(dtoHund.ras);
                    hund.getElementsByTagName("bildURL").item(0).setTextContent(dtoHund.bildURL);
                    break;
                }
            }

        }
        writeToXML(doc);
    }

    @Override
    public List<DTOHund> getHundar() {
        List<DTOHund> hundar = new ArrayList();
        doc = getDOMDocument();
        Element root = doc.getDocumentElement();
        NodeList nl = root.getElementsByTagName("hund");

        for (int i = 0; i < nl.getLength(); i++) {
            Element hund = (Element) nl.item(i);
            DTOHund dtoHund = new DTOHund();

            if (hund.hasAttributes()) {
                String id = hund.getAttribute("id");
                dtoHund.setId(Integer.parseInt(id));
                dtoHund.setNamn(hund.getElementsByTagName("namn").item(0).getTextContent());
                dtoHund.setRas(hund.getElementsByTagName("ras").item(0).getTextContent());
                dtoHund.setBildURL(hund.getElementsByTagName("bildURL").item(0).getTextContent());
                hundar.add(dtoHund);
            }

        }

        return hundar;
    }

    private Document getDOMDocument() {

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            doc = db.parse(new File("hundar.xml"));
            return doc;
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(DAOHundXML.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (SAXException ex) {
            Logger.getLogger(DAOHundXML.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(DAOHundXML.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private void writeToXML(Document docum) {

        try {
            TransformerFactory transFac = TransformerFactory.newInstance();
            Transformer trans = transFac.newTransformer();

            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(docum);

            //för att skriva till filen
            StreamResult result = new StreamResult(new File("hundar.xml"));
            trans.transform(source, result);

            //för att skriva till outputfönstret
            StreamResult consoleResult = new StreamResult(System.out);
            trans.transform(source, consoleResult);

        } /*catch (TransformerConfigurationException ex) {
            Logger.getLogger(DAOHundXML.class.getName()).log(Level.SEVERE, null, ex);
        } */ catch (TransformerException ex) {
            Logger.getLogger(DAOHundXML.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
