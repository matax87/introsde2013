import generated.*;

import java.math.BigDecimal;
import java.util.List;
import java.io.IOException;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.bind.JAXBException;
import java.net.URL;
import java.net.MalformedURLException;

import java.util.regex.*;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.htmlcleaner.*;

/**
 *
 * @author Matteo Matassoni
 */
public class PeopleXMLGenerator {
	static Document doc;
    static XPath xpath;
    
    final static String xmlFilename = "people.xml";
    final String measure = "cms";
	
	public PeopleXMLGenerator() throws ParserConfigurationException, SAXException, IOException, MalformedURLException {
	    HtmlCleaner cleaner = new HtmlCleaner();
        final String siteUrl = "http://www.celeb-height-weight.psyphil.com";
 
        TagNode node = cleaner.clean(new URL(siteUrl));
        doc = new DomSerializer(new CleanerProperties()).createDOM(node);

        //creating xpath object
        getXPathObj();
    }
    
    private XPath getXPathObj() {
        XPathFactory factory = XPathFactory.newInstance();
        xpath = factory.newXPath();
        return xpath;
    }
    
    private NodeList getNames() throws XPathExpressionException {
	    String expression = "//div[@class = 'entry']/table/tbody/tr[position() > 1]/td[1]";
        XPathExpression expr = xpath.compile(expression);
        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        return nodes;

    }
    
    private NodeList getHeights() throws XPathExpressionException {
	    String expression = "//div[@class = 'entry']/table/tbody/tr[position() > 1]/td[2]";
        XPathExpression expr = xpath.compile(expression);
        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        return nodes;

    }
    
    private NodeList getWeights() throws XPathExpressionException {
	    String expression = "//div[@class = 'entry']/table/tbody/tr[position() > 1]/td[3]";
        XPathExpression expr = xpath.compile(expression);
        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        return nodes;

    }
    
    private double readHeight(String string) {
        double height = 0;
        if (!string.contains(measure)) {
                string = string + " " + measure;
        }
        String regexp = "\\d+(\\.)?\\d*\\s" + measure;
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            String heightString = matcher.group().replaceAll(measure, "").trim();
            // convert centimeters to meters and round to the second decimal
            height = round(Double.parseDouble(heightString) / 100.0, 2);
        }
        return height;
    }
    
    private double readWeight(String string) {
        double weight = 0;
        String regexp = "\\d+(\\.)?\\d*";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(string);
        // mean value
        if (matcher.find()) {
            weight = Double.parseDouble(matcher.group());
             if (matcher.find()) {
                weight = 0.5 * (weight + Double.parseDouble(matcher.group()));
             }
            // convert pounds to kg and round to the second decimal
            weight = round(weight * 0.45359237, 2);
        }
        return weight;
    }
    
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }
	
	public static void main(String[] args) {	    
	    try {
	        PeopleXMLGenerator xmlGenerator = new PeopleXMLGenerator();    
	    
	        PeopleType people = new PeopleType();
	        List<PersonType> personList = people.getPerson();
	        
            NodeList nameNodes = xmlGenerator.getNames();
            NodeList heightNodes = xmlGenerator.getHeights();
            NodeList weightNodes = xmlGenerator.getWeights();
            for (int i = 0; i < nameNodes.getLength(); i++) {
                Node nameNode = nameNodes.item(i);
                Node heightNode = heightNodes.item(i);
                Node weightNode = weightNodes.item(i);
                String fullname = nameNode.getTextContent().trim();
                String[] names = fullname.split(" ");
	            String firstname = names[0];
	            String lastname = names[names.length - 1];
                String heightString = heightNode.getTextContent().trim();
                double height = xmlGenerator.readHeight(heightString);
                String weightString = weightNode.getTextContent().trim();
                double weight = xmlGenerator.readWeight(weightString);
                PersonType person = new PersonType();
                person.setFirstname(firstname);
                person.setLastname(lastname);
                HealthProfileType hp = new HealthProfileType();
                hp.setHeight(height);
                hp.setWeight(weight);
                person.setHealthprofile(hp);
                    personList.add(person);
                }
                
                // marshall
                PeopleMarshaller marshaller = new PeopleMarshaller(people);
                marshaller.generateXMLDocument(new File(xmlFilename));
        } catch (ParserConfigurationException e) {
            System.out.println(e.toString());
        } catch (SAXException e) {
            System.out.println(e.toString());
        } catch(XPathExpressionException e) {
            System.out.println(e.toString());
        } catch(JAXBException e) {
            System.out.println(e.toString());
        } catch(IOException e) {
            System.out.println(e.toString());
        }
    }
}
