package reporting;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xhtmlrenderer.pdf.ITextRenderer;

import handler.MedicationHandler;
import javafx.collections.ObservableList;
import model.Medication;

public class PdfReport implements Report {

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

			// se transforma din xml in pdf
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer(new StreamSource("medoutofstock.xsl"));
			transformer.transform(new StreamSource("medoutofstock.xml"),
					new StreamResult(new FileOutputStream("medoutofstock.html")));

			String File_To_Convert = "medoutofstock.html";
			String url = new File(File_To_Convert).toURI().toURL().toString();
			String HTML_TO_PDF = "medoutofstock.pdf";

			OutputStream os = new FileOutputStream(HTML_TO_PDF);
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocument(url);
			renderer.layout();
			renderer.createPDF(os);
			os.close();
			Desktop.getDesktop().open(new File("medoutofstock.pdf"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
