package com.netcracker.jaxb.managers;

import com.netcracker.jaxb.annotations.Component;
import com.netcracker.jaxb.annotations.JaxbElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.lang.reflect.Field;
import java.util.*;


public class XmlManager extends EntityManager {
    private String path;
    private Document doc;
    Map<Object, Element> objectMap;
    Node currentElement;

    public XmlManager(String str) {
        path = str;
        objectMap = new HashMap<Object, Element>();
        currentElement = null;
    }


    @Override
    public void marshall(Object clazz) {
        try {
            newDoc();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }


        start(clazz);
        writeToXml();
    }

    @Override
    public void unmarshall(Object clazz) {

    }

    private void start(Object obj) {
        if (objectMap.containsKey(obj)) {
            addAsRef(obj);
        } else if (Collection.class.isAssignableFrom(obj.getClass())) {
            handleCollection((Collection) obj);
        } else {
            handleObject(obj);
        }
    }

    private void handleObject(Object object) {
        Class clazz = object.getClass();

        if(clazz.isAnnotationPresent(Component.class)) {
            Field[] fields = clazz.getDeclaredFields();
            Component com = (Component)clazz.getAnnotation(Component.class);

            Element root = setCurrentElement((com.value()!="")?com.value():clazz.getName(), object);

            try {
                for (Field f : fields) {
                    if (f.isAnnotationPresent(JaxbElement.class)) {
                        boolean flag = f.isAccessible();
                        if (!flag)
                            f.setAccessible(true);
                        if (isFieldSimple(f)) {
                            Element e = doc.createElement(f.getName());


                            Object obj = f.get(object);
                            e.appendChild(doc.createTextNode(obj.toString()));
                            root.appendChild(e);
                        } else {
                            start(f.get(object));
                        }
                        if (!flag)
                            f.setAccessible(false);
                    }
                }
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
            currentElement = currentElement.getParentNode();
        }


    }

    private Element setCurrentElement(String name, Object object){
        Element root = doc.createElement(name);

        objectMap.put(object, root);
        if (currentElement == null) {
            doc.appendChild(root);
        } else {
            currentElement.appendChild(root);
        }

        currentElement = root;
        return root;
    }

    private boolean isFieldSimple(Field f) {

        return f.getType().isPrimitive() || f.getType().getName().startsWith("java.lang");
    }

    private void handleCollection(Collection col) {
        Element root = setCurrentElement(col.getClass().getName(), col);

        for (Object obj: col) {
            start(obj);
        }

        currentElement = currentElement.getParentNode();
    }

    private void addAsRef(Object obj) {
        throw new NotImplementedException();
    }

    private void newDoc() throws ParserConfigurationException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        this.doc = dBuilder.newDocument();
    }

    private void writeToXml() {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(path));
            transformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }


}
