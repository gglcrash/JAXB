package com.netcracker.jaxb.managers.xml;

import jdk.internal.org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.io.CharArrayWriter;
import java.lang.reflect.Field;
import java.util.Stack;

/**
 * Created by Никита on 18.12.2016.
 */
public class JaxbHandler extends DefaultHandler {

    Object object;
    Class<?> clazz;
    Stack<Field> stack;
    Stack<Object> objStack;

    public JaxbHandler(Object object) {
        this.object = object;
        this.clazz = object.getClass();
        this.stack = new Stack<Field>();
    }

    private CharArrayWriter contents = new CharArrayWriter();

    @Override
    public void startElement(String namespaceURI,
                             String localName,
                             String qName,
                             Attributes attr) {
        contents.reset();
        try {
            stack.add(clazz.getDeclaredField(localName));
        } catch (NoSuchFieldException e) {
            //TODO: something
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
        if (!stack.empty()) {
            Field f = stack.pop();
            String s = contents.toString();
            try {
                boolean flag = f.isAccessible();
                if (!flag)
                    f.setAccessible(true);


                f.set(object, convert(f.getType(), s));
                if (!flag)
                    f.setAccessible(false);
            } catch (IllegalAccessException e) {


            }
            int i = 0;
        }

    }


    @Override
    public void characters(char ch[],
                           int start, int length) {

        //TODO: maybe add attribute type and then can use it to cast this shit to it's object
        contents.write(ch, start, length);
        int i = 0;
    }

}
