package org.hutnkng.loader.source;

import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class SourceReader implements Iterator<URL> {
    private List<URL> links;
    private int index;

    public SourceReader() {
        try (
                InputStream sources = getClass().getClassLoader().getResource("sources").openStream();
                Reader tmp = new InputStreamReader(sources);
                BufferedReader reader = new BufferedReader(tmp);
        ) {
            links = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                URL uri = new URL(line);
                links.add(uri);
            }
            index = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasNext() {
        return index < links.size();
    }

    @Override
    public URL next() {
        return links.get(index++);
    }
}
