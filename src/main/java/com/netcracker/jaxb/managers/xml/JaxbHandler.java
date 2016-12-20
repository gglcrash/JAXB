package com.netcracker.jaxb.managers.xml;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.io.CharArrayWriter;
import java.lang.reflect.Field;
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
            try {
                Field f = object.getClass().getDeclaredField(localName);
                boolean flag = f.isAccessible();
                if (!flag)
                    f.setAccessible(true);
                if (f.getType().isPrimitive())
                    object = new Object();
                else
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
        if (!objStack.empty()) {
            String s = contents.toString();
            object = objStack.pop();

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

            int i = 0;
        }

    }


    @Override
    public void characters(char ch[],
                           int start, int length) {
        contents.write(ch, start, length);
        int i = 0;
    }

}
