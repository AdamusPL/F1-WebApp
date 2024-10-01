package com.typerf1.typerf1.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.*;
import java.util.ArrayList;

public class UrlFromTxtParser {

    public ArrayList<URL> add(String filename, ResourceLoader resourceLoader) throws IOException {
        // Getting the resource using the ResourceLoader
        Resource resource = resourceLoader.getResource("classpath:" + filename);
        // Opening an InputStream to read the content of the resource
        InputStream inputStream = resource.getInputStream();
        // Creating a BufferedReader to read text from the InputStream efficiently
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        // StringBuilder to accumulate the lines read from the file
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        ArrayList<URL> links = new ArrayList<>();
        // Reading each line from the file and appending it to the StringBuilder
        while ((line = reader.readLine()) != null) {
            String[] data = line.split("/");
            URL url = new URL(data[8], line);
            links.add(url);
        }
        // Closing the BufferedReader
        reader.close();
        // Returning the contents of the file as a string
        return links;
    }
}
