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
import model.User;

public class UserHandler {

	public static User loginUser(String username, String password) {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		User user = null;

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse("users.xml");

			NodeList usersList = doc.getElementsByTagName("user");
			for (int i = 0; i < usersList.getLength(); i++) {
				Node u = usersList.item(i);
				if (u.getNodeType() == Node.ELEMENT_NODE) {
					Element eUser = (Element) u;
					String userId = eUser.getAttribute("id");
					String userUsername = eUser.getElementsByTagName("username").item(0).getTextContent();
					String userPassword = eUser.getElementsByTagName("password").item(0).getTextContent();
					String type = eUser.getElementsByTagName("type").item(0).getTextContent();
					if (userUsername.equals(username) && userPassword.equals(password)) {
						user = new User(Long.valueOf(userId), username, password, type);
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

		return user;
	}

	public static ObservableList<User> getUsersList() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		ObservableList<User> usersData = FXCollections.observableArrayList();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse("users.xml");

			NodeList usersList = doc.getElementsByTagName("user");
			for (int i = 0; i < usersList.getLength(); i++) {
				Node u = usersList.item(i);
				if (u.getNodeType() == Node.ELEMENT_NODE) {
					Element user = (Element) u;
					String userId = user.getAttribute("id");
					String username = user.getElementsByTagName("username").item(0).getTextContent();
					String password = user.getElementsByTagName("password").item(0).getTextContent();
					String type = user.getElementsByTagName("type").item(0).getTextContent();
					if ("regularUser".equals(type)) {
						User userObject = new User(Long.valueOf(userId), username, password, type);
						usersData.add(userObject);
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
		return usersData;
	}

	public static void edit(User user) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse("users.xml");

			NodeList usersList = doc.getElementsByTagName("user");
			for (int i = 0; i < usersList.getLength(); i++) {
				Node u = usersList.item(i);
				if (u.getNodeType() == Node.ELEMENT_NODE) {
					Element userElement = (Element) u;
					String userId = userElement.getAttribute("id");
					if (user.getUserId() == Long.valueOf(userId)) {
						userElement.getElementsByTagName("username").item(0).setTextContent(user.getUsername());
						userElement.getElementsByTagName("password").item(0).setTextContent(user.getPassword());
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
			StreamResult result = new StreamResult(new File("users.xml"));
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

	public static boolean add(User user) {

		ObservableList<User> list = getUsersList();
		boolean ok = true;
		for (User u : list) {
			if (u.getUsername().equals(user.getUsername())) {
				ok = false;
			}
		}

		if (ok) {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

			try {
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc = builder.parse("users.xml");

				Element root = doc.getDocumentElement();

				Element userElement = doc.createElement("user");
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				((Element) userElement).setAttribute("id", String.valueOf(timestamp.getTime()));

				Element username = doc.createElement("username");
				username.appendChild(doc.createTextNode(user.getUsername()));
				userElement.appendChild(username);

				Element password = doc.createElement("password");
				password.appendChild(doc.createTextNode(user.getPassword()));
				userElement.appendChild(password);

				Element type = doc.createElement("type");
				type.appendChild(doc.createTextNode("regularUser"));
				userElement.appendChild(type);

				root.appendChild(userElement);

				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = null;
				try {
					transformer = transformerFactory.newTransformer();
				} catch (TransformerConfigurationException e) {
					e.printStackTrace();
				}
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File("users.xml"));
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

	public static void delete(User user) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse("users.xml");

			NodeList usersList = doc.getElementsByTagName("user");
			for (int i = 0; i < usersList.getLength(); i++) {
				Node u = usersList.item(i);
				if (u.getNodeType() == Node.ELEMENT_NODE) {
					Element userElement = (Element) u;
					String userId = userElement.getAttribute("id");
					if (user.getUserId() == Long.valueOf(userId)) {
						userElement.getParentNode().removeChild(userElement);
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
			StreamResult result = new StreamResult(new File("users.xml"));
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
