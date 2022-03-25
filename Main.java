package ru.netology.core7;

import ru.netology.core6.GameProgress;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] args) {
        zipOpen("\\Users\\sosko\\Games\\savegames\\zip_SG.zip", "\\Users\\sosko\\Games\\savegames");
        File dir = new File("\\Users\\sosko\\Games\\savegames");
        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                if (file.getName().contains("save")) {
                    System.out.println(openProgress(file.getPath()));
                }
            }
        }
    }

    public static void zipOpen(String path, String inPath) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(path))) {
            ZipEntry entry;
            String name;
            while ((entry = zis.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fos = new FileOutputStream(inPath + "\\" + name);
                for (int c = zis.read(); c != -1; c = zis.read()) {
                    fos.write(c);
                }
                fos.flush();
                zis.closeEntry();
                fos.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static GameProgress openProgress(String path) {
        GameProgress gameProgress = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return gameProgress;
    }
}
