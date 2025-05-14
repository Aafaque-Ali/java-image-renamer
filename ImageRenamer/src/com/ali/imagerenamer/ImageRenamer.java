package com.ali.imagerenamer;
import java.io.File;

public class ImageRenamer {

    public static void main(String[] args) {
        // Path to the folder containing images
        String folderPath = "E:/Backup-Desktop 28Nov22/SwiftThroughVSC2019/echo/mk";  // <-- We have to Change this

        // Naming sequence prefix
        String namePrefix = "img";

        // Supported image extensions
        String[] extensions = {".jpg", ".jpeg", ".png", ".gif", ".bmp"};

        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Invalid folder path.");
            return;
        }

        File[] files = folder.listFiles();
        if (files == null) {
            System.out.println("No files found in the folder.");
            return;
        }

        int count = 1;
        for (File file : files) {
            if (file.isFile() && isImage(file.getName(), extensions)) {
                String extension = getFileExtension(file.getName());
                
                // Format number with leading zeros (e.g., 001, 002, ...)
                String formattedNumber = String.format("%03d", count);

                File newFile = new File(folderPath + "/" + namePrefix + formattedNumber + extension);

                // Handle filename conflict
                while (newFile.exists()) {
                    count++;
                    formattedNumber = String.format("%03d", count);
                    newFile = new File(folderPath + "/" + namePrefix + formattedNumber + extension);
                }

                boolean success = file.renameTo(newFile);
                if (success) {
                    System.out.println("Renamed: " + file.getName() + " -> " + newFile.getName());
                } else {
                    System.out.println("Failed to rename: " + file.getName());
                }
                count++;
            }
        }
    }

    private static boolean isImage(String fileName, String[] extensions) {
        fileName = fileName.toLowerCase();
        for (String ext : extensions) {
            if (fileName.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    private static String getFileExtension(String fileName) {
        int lastDot = fileName.lastIndexOf(".");
        if (lastDot == -1) return "";
        return fileName.substring(lastDot);
    }
}
