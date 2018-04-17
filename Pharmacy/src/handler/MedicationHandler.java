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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Medication;

public class MedicationHandler {

	public static ObservableList<Medication> getMedicationsList() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		ObservableList<Medication> medicationsData = FXCollections.observableArrayList();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse("medications.xml");

			NodeList medicationsList = doc.getElementsByTagName("medication");
			for (int i = 0; i < medicationsList.getLength(); i++) {
				Node m = medicationsList.item(i);
				if (m.getNodeType() == Node.ELEMENT_NODE) {
					Element medication = (Element) m;
					String medicationId = medication.getAttribute("id");
					String name = medication.getElementsByTagName("name").item(0).getTextContent();
					String ingredients = medication.getElementsByTagName("ingredients").item(0).getTextContent();
					String manufacturer = medication.getElementsByTagName("manufacturer").item(0).getTextContent();
					String quantity = medication.getElementsByTagName("quantity").item(0).getTextContent();
					String price = medication.getElementsByTagName("price").item(0).getTextContent();

					Medication medicationObject = new Medication(Long.valueOf(medicationId), name, ingredients,
							manufacturer, Integer.valueOf(quantity), Integer.valueOf(price));
					medicationsData.add(medicationObject);

				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return medicationsData;
	}

	public static ObservableList<Medication> getMedicationsList(String nameSearch, String ingredientsSearch,
			String manufacturerSearch) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		ObservableList<Medication> medicationsData = FXCollections.observableArrayList();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse("medications.xml");

			NodeList medicationsList = doc.getElementsByTagName("medication");
			for (int i = 0; i < medicationsList.getLength(); i++) {
				Node m = medicationsList.item(i);
				if (m.getNodeType() == Node.ELEMENT_NODE) {
					Element medication = (Element) m;
					String medicationId = medication.getAttribute("id");
					String name = medication.getElementsByTagName("name").item(0).getTextContent();
					String ingredients = medication.getElementsByTagName("ingredients").item(0).getTextContent();
					String manufacturer = medication.getElementsByTagName("manufacturer").item(0).getTextContent();
					String quantity = medication.getElementsByTagName("quantity").item(0).getTextContent();
					String price = medication.getElementsByTagName("price").item(0).getTextContent();

					if (name.toUpperCase().contains(nameSearch.toUpperCase())
							&& ingredients.toUpperCase().contains(ingredientsSearch.toUpperCase())
							&& manufacturer.toUpperCase().contains(manufacturerSearch.toUpperCase())) {
						Medication medicationObject = new Medication(Long.valueOf(medicationId), name, ingredients,
								manufacturer, Integer.valueOf(quantity), Integer.valueOf(price));
						medicationsData.add(medicationObject);
					}

				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return medicationsData;
	}

	public static void edit(Medication medication) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse("medications.xml");

			NodeList medicationsList = doc.getElementsByTagName("medication");
			for (int i = 0; i < medicationsList.getLength(); i++) {
				Node m = medicationsList.item(i);
				if (m.getNodeType() == Node.ELEMENT_NODE) {
					Element medicationElement = (Element) m;
					String medicationId = medicationElement.getAttribute("id");
					if (medication.getMedicationId() == Long.valueOf(medicationId)) {
						medicationElement.getElementsByTagName("name").item(0).setTextContent(medication.getName());
						medicationElement.getElementsByTagName("ingredients").item(0)
								.setTextContent(medication.getIngredients());
						medicationElement.getElementsByTagName("manufacturer").item(0)
								.setTextContent(medication.getManufacturer());
						medicationElement.getElementsByTagName("quantity").item(0)
								.setTextContent(String.valueOf(medication.getQuantity()));
						medicationElement.getElementsByTagName("price").item(0)
								.setTextContent(String.valueOf(medication.getPrice()));
						break;
					}
				}
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = null;
			try {
				transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			} catch (TransformerConfigurationException e) {
				e.printStackTrace();
			}
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("medications.xml"));
			try {
				transformer.transform(source, result);
			} catch (TransformerException e) {
				e.printStackTrace();
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean add(Medication medication) {
		ObservableList<Medication> list = getMedicationsList();
		boolean ok = true;
		for (Medication m : list) {
			if (m.getName().equals(medication.getName())) {
				ok = false;
			}
		}

		if (ok) {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

			try {
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc = builder.parse("medications.xml");

				Element root = doc.getDocumentElement();

				Element medicationElement = doc.createElement("medication");
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				((Element) medicationElement).setAttribute("id", String.valueOf(timestamp.getTime()));

				Element name = doc.createElement("name");
				name.appendChild(doc.createTextNode(medication.getName()));
				medicationElement.appendChild(name);

				Element ingredients = doc.createElement("ingredients");
				ingredients.appendChild(doc.createTextNode(medication.getIngredients()));
				medicationElement.appendChild(ingredients);

				Element manufacturer = doc.createElement("manufacturer");
				manufacturer.appendChild(doc.createTextNode(medication.getManufacturer()));
				medicationElement.appendChild(manufacturer);

				Element quantity = doc.createElement("quantity");
				quantity.appendChild(doc.createTextNode(Integer.toString(medication.getQuantity())));
				medicationElement.appendChild(quantity);

				Element price = doc.createElement("price");
				price.appendChild(doc.createTextNode(Integer.toString(medication.getPrice())));
				medicationElement.appendChild(price);

				root.appendChild(medicationElement);

				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = null;
				try {
					transformer = transformerFactory.newTransformer();
				} catch (TransformerConfigurationException e) {
					e.printStackTrace();
				}
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File("medications.xml"));
				try {
					transformer.setOutputProperty(OutputKeys.INDENT, "yes");
					transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
					transformer.transform(source, result);
				} catch (TransformerException e) {
					e.printStackTrace();
				}

			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ok;
	}

	public static void delete(Medication medication) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse("medications.xml");

			NodeList medicationsList = doc.getElementsByTagName("medication");
			for (int i = 0; i < medicationsList.getLength(); i++) {
				Node m = medicationsList.item(i);
				if (m.getNodeType() == Node.ELEMENT_NODE) {
					Element medicationElement = (Element) m;
					String medicationId = medicationElement.getAttribute("id");
					if (medication.getMedicationId() == Long.valueOf(medicationId)) {
						medicationElement.getParentNode().removeChild(medicationElement);
						break;
					}
				}
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = null;
			try {
				transformer = transformerFactory.newTransformer();
			} catch (TransformerConfigurationException e) {
				e.printStackTrace();
			}
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("medications.xml"));
			try {
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
				transformer.transform(source, result);
			} catch (TransformerException e) {
				e.printStackTrace();
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ObservableList<Medication> getOutOfStockList() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		ObservableList<Medication> medicationsData = FXCollections.observableArrayList();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse("medications.xml");

			NodeList medicationsList = doc.getElementsByTagName("medication");
			for (int i = 0; i < medicationsList.getLength(); i++) {
				Node m = medicationsList.item(i);
				if (m.getNodeType() == Node.ELEMENT_NODE) {
					Element medication = (Element) m;
					String medicationId = medication.getAttribute("id");
					String name = medication.getElementsByTagName("name").item(0).getTextContent();
					String ingredients = medication.getElementsByTagName("ingredients").item(0).getTextContent();
					String manufacturer = medication.getElementsByTagName("manufacturer").item(0).getTextContent();
					String quantity = medication.getElementsByTagName("quantity").item(0).getTextContent();
					String price = medication.getElementsByTagName("price").item(0).getTextContent();

					if (Integer.valueOf(quantity).intValue() == 0) {
						Medication medicationObject = new Medication(Long.valueOf(medicationId), name, ingredients,
								manufacturer, Integer.valueOf(quantity), Integer.valueOf(price));
						medicationsData.add(medicationObject);
					}

				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return medicationsData;
	}

	public static void addToOutOfStock(Medication medication) {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse("medoutofstock.xml");

			// NodeList medicationsList = doc.getElementsByTagName("medication");

			Element root = doc.getDocumentElement();

			Element medicationElement = doc.createElement("medication");
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			((Element) medicationElement).setAttribute("id", String.valueOf(timestamp.getTime()));

			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode(medication.getName()));
			medicationElement.appendChild(name);

			Element ingredients = doc.createElement("ingredients");
			ingredients.appendChild(doc.createTextNode(medication.getIngredients()));
			medicationElement.appendChild(ingredients);

			Element manufacturer = doc.createElement("manufacturer");
			manufacturer.appendChild(doc.createTextNode(medication.getManufacturer()));
			medicationElement.appendChild(manufacturer);

			Element quantity = doc.createElement("quantity");
			quantity.appendChild(doc.createTextNode(Integer.toString(medication.getQuantity())));
			medicationElement.appendChild(quantity);

			Element price = doc.createElement("price");
			price.appendChild(doc.createTextNode(Integer.toString(medication.getPrice())));
			medicationElement.appendChild(price);

			root.appendChild(medicationElement);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = null;
			try {
				transformer = transformerFactory.newTransformer();
			} catch (TransformerConfigurationException e) {
				e.printStackTrace();
			}
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("medoutofstock.xml"));
			try {
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
				transformer.transform(source, result);
			} catch (TransformerException e) {
				e.printStackTrace();
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
