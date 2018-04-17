package reporting;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import handler.MedicationHandler;
import javafx.collections.ObservableList;
import model.Medication;

public class CsvReport implements Report {

	@Override
	public void generateReport() {
		try {

			// se creaza un nou fisier medoutofstock.xml
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = null;
			try {
				docBuilder = docFactory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
			;
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("medications");
			doc.appendChild(rootElement);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer2 = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("medoutofstock.xml"));
			transformer2.transform(source, result);

			// se adauga medicamentele care nu sunt in stock in fisier
			ObservableList<Medication> list = MedicationHandler.getOutOfStockList();
			for (Medication m : list) {
				MedicationHandler.addToOutOfStock(m);
			}

			File stylesheet = new File("style.xsl");
			File xmlSource = new File("medoutofstock.xml");

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(xmlSource);

			StreamSource stylesource = new StreamSource(stylesheet);
			Transformer transformer = TransformerFactory.newInstance().newTransformer(stylesource);
			Source source2 = new DOMSource(document);
			Result outputTarget = new StreamResult(new File("medoutofstock.csv"));
			transformer.transform(source2, outputTarget);

		} catch (Exception e) {

		}

	}
}
