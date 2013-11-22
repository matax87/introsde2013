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

public class Person {
	private String firstname;
	private String lastname;
	private HealthProfile healthProfile;
	
	public Person(String fname, String lname, HealthProfile hp) {
		this.setFirstname(fname);
		this.setLastname(lname);
		this.setHealthProfile(hp);
	}
	
	public Person(String fname, String lname) {
	    this(fname, lname, new HealthProfile());
	}
	
	public Person() {
	    this("Pinco", "Pallino");
	}
	
	public Person(Node node) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
        
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            Node importedNode = document.importNode(node, true);
            document.appendChild(importedNode);
        
            XPath xpath = XPathFactory.newInstance().newXPath();
            
	        XPathExpression expr = xpath.compile("/person/firstname");
            node = (Node) expr.evaluate(document, XPathConstants.NODE);
            this.firstname = node.getTextContent();
	        expr = xpath.compile("/person/lastname");
            node = (Node) expr.evaluate(document, XPathConstants.NODE);
            this.lastname = node.getTextContent();
            expr = xpath.compile("/person/healthprofile");
            node = (Node) expr.evaluate(document, XPathConstants.NODE);
            if (node != null) {
                this.healthProfile = new HealthProfile(node);
            } else {
                this.healthProfile = new HealthProfile();
            }
        } catch(ParserConfigurationException e) {
            System.out.println(e.toString());
        } catch(XPathExpressionException e) {
            
        }
	}
	
	public String getFirstname() {
		return firstname;
	}
	public
	 void setFirstname(String firstname) {
		this.firstname  =  firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname  =  lastname;
	}
	
	public HealthProfile getHealthProfile() {
		return healthProfile;
	}
	
	public void setHealthProfile(HealthProfile hProfile) {
		this.healthProfile  =  hProfile;
	}
	
	public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Person: firstname = " + this.getFirstname() + " lastname = " + this.getLastname());
        buffer.append("\n\t");
        healthProfile = this.getHealthProfile();
        if (healthProfile != null) {
            buffer.append(healthProfile.toString());
		}
        return buffer.toString();
	}
}
