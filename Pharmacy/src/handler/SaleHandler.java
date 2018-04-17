package handler;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

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
import org.xml.sax.SAXException;

import model.Sale;

public class SaleHandler {

	public static boolean add(Sale sale) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		boolean ok = false;
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse("sales.xml");

			Element root = doc.getDocumentElement();

			Element saleElement = doc.createElement("sale");
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			((Element) saleElement).setAttribute("id", String.valueOf(timestamp.getTime()));

			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode(sale.getName()));
			saleElement.appendChild(name);

			Element manufacturer = doc.createElement("manufacturer");
			manufacturer.appendChild(doc.createTextNode(sale.getManufacturer()));
			saleElement.appendChild(manufacturer);

			Element quantity = doc.createElement("quantity");
			quantity.appendChild(doc.createTextNode(Integer.toString(sale.getQuantity())));
			saleElement.appendChild(quantity);

			Element totalPrice = doc.createElement("totalPrice");
			totalPrice.appendChild(doc.createTextNode(Integer.toString(sale.getTotalPrice())));
			saleElement.appendChild(totalPrice);

			root.appendChild(saleElement);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = null;
			try {
				transformer = transformerFactory.newTransformer();
			} catch (TransformerConfigurationException e) {
				e.printStackTrace();
			}
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("sales.xml"));
			try {
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
				transformer.transform(source, result);
			} catch (TransformerException e) {
				e.printStackTrace();
			}
			ok = true;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ok;
	}

}
