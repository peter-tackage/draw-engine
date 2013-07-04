package com.moac.drawengine.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class TestDataUtils {

    public static Map<Long, Set<Long>> readTestDataFile(String _filename) {
        try {
            URL url = ClassLoader.getSystemClassLoader().getResource(_filename);
            if(url == null)
                throw new IllegalArgumentException("File not found on classpath: " + _filename);

            File file = new File(url.toURI());
            Scanner scanner = new Scanner(file);
            Map<Long, Set<Long>> group = new HashMap<Long, Set<Long>>();

            while(scanner.hasNextLine()) {
                if(scanner.hasNextLong()) {
                    Map.Entry<Long, Set<Long>> entry = processLine(scanner.nextLine());
                    group.put(entry.getKey(), entry.getValue());
                } else {
                    scanner.nextLine(); // skip
                }
            }
            scanner.close();
            return group;
        } catch(URISyntaxException ex) {
            throw new RuntimeException("File could not be read: " + _filename, ex);
        } catch(FileNotFoundException ex) {
            throw new RuntimeException("File could not be read: " + _filename, ex);
        }
    }

    private static Map.Entry<Long, Set<Long>> processLine(String _line) {
        Scanner scanner = new Scanner(_line);
        Long memberId = scanner.nextLong();

        Set<Long> restrictions = new HashSet<Long>();
        if(scanner.hasNext()) {
            String inner = scanner.next();
            Scanner scannerInner = new Scanner(inner);
            scannerInner.useDelimiter(",");
            while(scannerInner.hasNextLong()) {
                Long rId = scannerInner.nextLong();
                restrictions.add(rId);
            }
        }
        return new AbstractMap.SimpleEntry<Long, Set<Long>>(memberId, restrictions);
    }
}
