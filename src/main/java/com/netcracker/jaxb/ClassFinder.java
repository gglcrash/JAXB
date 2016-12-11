package com.netcracker.jaxb;

import com.netcracker.jaxb.annotations.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassFinder {
    private static Class<?> loadClass(String className) {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(className);
        }
    }
    public static List<Class<?>> processDirectory(File directory, String pkgname) {

        ArrayList<Class<?>> classes = new ArrayList();

        String[] files = directory.list();
        for (int i = 0; i < files.length; i++) {
            String fileName = files[i];
            String className = null;

            if (fileName.endsWith(".class")) {
                className = pkgname.substring(1) + '.' + fileName.substring(0, fileName.length() - 6);
            }

            if (className != null) {
                classes.add(loadClass(className));
            }

            File subdir = new File(directory, fileName);
            if (subdir.isDirectory()) {
                classes.addAll(processDirectory(subdir, pkgname + '.' + fileName));
            }
        }
        return classes;
    }

    public static Map<String,Class<?>> getClassesFromPackage(File directory, String pkgname, Class<Component> annotation){
        List<Class<?>> classes = processDirectory(directory, pkgname);
        Map<String,Class<?>> result =new HashMap<String, Class<?>>();
        for(int i = 0; i<classes.size();i++)
            if(classes.get(i).isAnnotationPresent(annotation))
                result.put(classes.get(i).getAnnotation(annotation).value(),classes.get(i));
        return result;
    }
}
