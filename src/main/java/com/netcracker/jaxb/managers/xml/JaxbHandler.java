package com.netcracker.jaxb.managers.xml;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.io.CharArrayWriter;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Stack;


public class JaxbHandler extends DefaultHandler {

    Object object;
    Class<?> clazz;
    Stack<Object> objStack;


    public JaxbHandler(Object object) {
        this.object = object;
        this.clazz = object.getClass();
        objStack = new Stack<Object>();
    }

    private CharArrayWriter contents = new CharArrayWriter();

    @Override
    public void startElement(String namespaceURI,
                             String localName,
                             String qName,
                             Attributes attr) {
        contents.reset();
        boolean empty = objStack.empty();
        objStack.push(object);
        if (!empty) {
            if (localName.compareTo("Items") == 0) {
                try {
                    initObjects(attr.getValue("cname"), Class.forName(attr.getValue("type")));

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                type = attr.getValue("type");
                initObjects(localName, null);
            }

        }
    }

    private String type;

    private void initObjects(String localName, Class<?> colClazz) {

        if (!Collection.class.isAssignableFrom(object.getClass())) {
            try {
                Field f = object.getClass().getDeclaredField(localName);
                boolean flag = f.isAccessible();
                if (!flag)
                    f.setAccessible(true);
                if (f.getType().isPrimitive())
                    object = new Object();
                else if (colClazz != null) {
                    object = colClazz.newInstance();
                } else
                    object = f.getType().newInstance();
                if (!flag)
                    f.setAccessible(false);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            try {
                object = Class.forName(type).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    private Object convert(Class<?> targetType, String text) {
        PropertyEditor editor = PropertyEditorManager.findEditor(targetType);
        editor.setAsText(text);
        return editor.getValue();
    }

    @Override
    public void endElement(String uri,
                           String localName, String qName) {
        if (objStack.size() > 1) {
            String s = contents.toString();
            Object tmpObj = object;
            object = objStack.pop();

            if (Collection.class.isAssignableFrom(object.getClass())) {
                Collection col = (Collection) object;
                col.add(tmpObj);
            } else
                fillSimpleFields(localName, s);

        }

    }


    @Override
    public void characters(char ch[],
                           int start, int length) {
        contents.write(ch, start, length);
        int i = 0;
    }

    private void fillSimpleFields(String localName, String s) {

        try {
            Field f = object.getClass().getDeclaredField(localName);

            boolean flag = f.isAccessible();
            if (!flag)
                f.setAccessible(true);
            f.set(object, convert(f.getType(), s));
            if (!flag)
                f.setAccessible(false);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
