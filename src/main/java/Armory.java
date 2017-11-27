import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.*;
import java.util.Properties;


public class Armory {

    private static String propertiesFile = "src"+ File.separator+"main"+File.separator+"resources"+File.separator+"properties.properties";;
    private static String CSVFile;
    private static String XMLFile;

     public static String getItemFromProperties(String fieldName) {
        String fileLocation = propertiesFile;
        String result   = null;
        try {
            //відкриваємо файл
            File file = new File(fileLocation);
            //тепер створюємо «потік» з файла
            FileInputStream fileInput = new FileInputStream(file);
            //створюємо Properties
            Properties properties = new Properties();
            //загружаємо Properties з «потоку»
            properties.load(fileInput);
            //закриваємо «потік»
            fileInput.close();
            //тепер за допомогою  getProperty метода класу Properties можна взяти проперті
            result = properties.getProperty(fieldName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();  // помилка яка вискакує коли файл не знайдено
        } catch (IOException e) {
            e.printStackTrace(); // помилка в процессі читання файлу
        }
        return result;
    }

    private static String getItemFromCSV(String fieldName) {
        String fileLocation = CSVFile;
        String result   = null;
        String[] items;
        String separator=",";
        try {
            //відкриваємо файл використовуючи BufferedReader і FileReader
            BufferedReader br = new BufferedReader(new FileReader(fileLocation));
            //FileReader — читає файл
            //BufferedReader — розбиває прочитане на блоки (умовно — рядочки)))
            // br.readLine() - читає рядочок
            // (line = br.readLine()) != null - перевіряє чи рядок був прочитаний
            for(String line; (line = br.readLine()) != null; ) {
                //split — розділяє рядок на частини (separator — наша )
                items = line.split(separator);
                //якщо рядок = fieldName — беремо результат з другого рядка
                if (items[0].equals(fieldName)){
                    //return item
                    result=items[1];
                }
            }
            //закрити файл
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String getItemFromXML(String fieldName) {
        String fileLocation = XMLFile;
        String result   = null;
        try {
            //відкрити файл
            File fXmlFile = new File(fileLocation);
            //створити  DocumentBuilderFactory
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            //створити  DocumentBuilder
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            //розпарсити файл XML в Document
            Document doc = dBuilder.parse(fXmlFile);
            //отримати всі ноди по тегу 'item' (всі item)
            NodeList nList = doc.getElementsByTagName("item");
            //пройти по всім елементам
            for (int temp = 0; temp < nList.getLength(); temp++) {
                //отримати нод
                Node nNode = nList.item(temp);
                //перетворити нод в Element
                Element eElement = (Element) nNode;
                //якщо'element' value = fieldName
                if (eElement.getElementsByTagName("name").item(0).getTextContent().equals(fieldName) )
                    //взяти перший 'value'
                    result = eElement.getElementsByTagName("value").item(0).getTextContent();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
