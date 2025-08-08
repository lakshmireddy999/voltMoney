package com.example.volt.Utilities;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileHelper {
    public static void clearDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    clearDirectory(file.getAbsolutePath());
                }
                file.delete();
            }
        }
    }

    public static File getFile(String path, String containsName, int timeoutInSeconds) {
        File file = null;
        for (int i = 0; i < timeoutInSeconds && file == null; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            file = Arrays.stream(new File(path).listFiles())
                    .filter(f -> f.isFile() && f.getName().contains(containsName))
                    .findFirst()
                    .orElse(null);
        }
        return file;
    }

    public static String getPdfContents(String path, String containsName, boolean deleteFile) {
        File pdfFile = getFile(path, containsName, 60);
        try {
            PDDocument document = PDDocument.load(pdfFile);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            document.close();
            if (deleteFile) {
                pdfFile.delete();
            }
            return text;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File getFileFromZip(File zipFile, String fileNameContains) {
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (entry.getName().contains(fileNameContains)) {
                    File tempFile = File.createTempFile("temp_", entry.getName());
                    try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = zipInputStream.read(buffer)) > 0) {
                            outputStream.write(buffer, 0, length);
                        }
                    }
                    return tempFile;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static List<String[]> readCsvContents(File csvFile) {
        List<String[]> contents = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contents.add(line.split("\","));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        contents.forEach(array -> Arrays.setAll(array, i -> array[i].replace("\uFEFF", "").replace("\"", "")));
        return contents;
    }

    public static String writeToCsvFile(List<List<String>> data) throws IOException {
        String filePath = Paths.get(System.getProperty("user.dir"), String.valueOf(System.currentTimeMillis()) + ".csv")
                .toString();
        FileWriter csvWriter = new FileWriter(filePath);
        for (List<String> rowData : data) {
            csvWriter.append(String.join(",", rowData));
            csvWriter.append("\n");
        }
        csvWriter.flush();
        return filePath;
    }
}