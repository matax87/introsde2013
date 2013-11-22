package pojos;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class HealthProfile {
	private double weight; // in kg
	private double height; // in m
	
	public HealthProfile(double weight, double height) {
		this.weight = weight;
		this.height = height;
	}
	
	public HealthProfile() {
    	this(85.5, 1.72);
	}
	
	public HealthProfile(Node node) {        
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
        
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            Node importedNode = document.importNode(node, true);
            document.appendChild(importedNode);
        
            XPath xpath = XPathFactory.newInstance().newXPath();
            
	        XPathExpression expr = xpath.compile("/healthprofile/weight");
            this.weight = (Double) expr.evaluate(document, XPathConstants.NUMBER);
	        expr = xpath.compile("/healthprofile/height");
            this.height = (Double) expr.evaluate(document, XPathConstants.NUMBER);
        } catch(ParserConfigurationException e) {
            System.out.println(e.toString());
        } catch(XPathExpressionException e) {
            
        }
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}
	
	public double getBMI() {
	    return this.weight / (this.height * this.height);
	}
	
	public String toString() {
	    return "HealthProfile: height = " + this.getHeight() + " weight = " + this.getWeight() + " bmi = " + this.getBMI();
	}	
}
