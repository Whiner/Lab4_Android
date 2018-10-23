package com.university.lab4.filework;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileScanner {

    public String getParentPath(String path){
        return new File(path).getAbsoluteFile().getParent();
    }

    public boolean isDirectory(String path){
        return new File(path).isDirectory();

    }

    public List<File> get(String path) {
        List<File> files = new ArrayList<>();
        files.addAll(getFolders(path));
        files.addAll(getFiles(path));
        return files;
    }

    private List<File> getFiles(String path) {
        File file = new File(path);
        List<File> fileList = new ArrayList<>();
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                if(f.isFile()){
                    fileList.add(f);
                }
            }
        }
        return fileList;
    }

    private List<File> getFolders(String path) {
        File file = new File(path);
        List<File> fileList = new ArrayList<>();
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                if(f.isDirectory()){
                    fileList.add(f);
                }
            }
        }
        return fileList;
    }

    public boolean isAvailable(String path) {
        return new File(path).canRead();
    }
}
