package ygoUtil;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class XMLHandler {
	public static YGOResource readResources() {
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File("resources.xml");
		YGOResource resource = new YGOResource();

		try {

			Document document = builder.build(xmlFile);
			Element rootNode = document.getRootElement();

			resource.setUnlockedPacks(Integer.parseInt(rootNode.getChildText("unlockedPacks")));
			resource.setUnlockedDecks(Integer.parseInt(rootNode.getChildText("unlockedDecks")));
			resource.setTotalDuelPoints(Integer.parseInt(rootNode.getChildText("totalDuelPoints")));
			resource.setWins(Integer.parseInt(rootNode.getChildText("wins")));
			resource.setLosses(Integer.parseInt(rootNode.getChildText("losses")));
			resource.setDebugMode(Integer.parseInt(rootNode.getChildText("debugMode")));
			resource.setVersionNumber(Integer.parseInt(rootNode.getChildText("versionNumber")));

		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}

		return resource;
	}

	public static void writeResources(YGOResource resource) {
		Document document;
		Element root;
		File xmlFile = new File("resources.xml");

		try {
			if (xmlFile.exists()) {
				FileInputStream fis = new FileInputStream(xmlFile);
				SAXBuilder sb = new SAXBuilder();
				document = sb.build(fis);
				root = document.getRootElement();
				fis.close();
			} else {
				document = new Document();
				root = new Element("resources");
				root.addContent(new Element("unlockedPacks").setText(Integer.toString(resource.getUnlockedPacks())));
				root.addContent(new Element("unlockedDecks").setText(Integer.toString(resource.getUnlockedDecks())));
				root.addContent(
						new Element("totalDuelPoints").setText(Integer.toString(resource.getTotalDuelPoints())));
				root.addContent(new Element("wins").setText(Integer.toString(resource.getWins())));
				root.addContent(new Element("losses").setText(Integer.toString(resource.getLosses())));
				root.addContent(new Element("debugMode").setText(Integer.toString(resource.getDebugMode())));
				root.addContent(new Element("versionNumber").setText(Integer.toString(resource.getVersionNumber())));
			}

			root.removeChild("unlockedPacks");
			root.removeChild("unlockedDecks");
			root.removeChild("totalDuelPoints");
			root.removeChild("wins");
			root.removeChild("losses");
			root.removeChild("debugMode");
			root.removeChild("versionNumber");
			root.addContent(new Element("unlockedPacks").setText(Integer.toString(resource.getUnlockedPacks())));
			root.addContent(new Element("unlockedDecks").setText(Integer.toString(resource.getUnlockedDecks())));
			root.addContent(
					new Element("totalDuelPoints").setText(Integer.toString(resource.getTotalDuelPoints())));
			root.addContent(new Element("wins").setText(Integer.toString(resource.getWins())));
			root.addContent(new Element("losses").setText(Integer.toString(resource.getLosses())));
			root.addContent(new Element("debugMode").setText(Integer.toString(resource.getDebugMode())));
			root.addContent(new Element("versionNumber").setText(Integer.toString(resource.getVersionNumber())));

			document.setContent(root);

			try {
				FileWriter writer = new FileWriter("resources.xml");
				XMLOutputter output = new XMLOutputter();
				output.setFormat(Format.getPrettyFormat());
				output.output(document, writer);
				output.output(document, System.out);
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("DERP");
			}

		} catch (IOException io) {
			System.out.println(io.getMessage());
			System.out.println("DERP");
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
			System.out.println("DERP");
		}
	}
}