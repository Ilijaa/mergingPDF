package Primer1;

import java.io.File;

public class files {



    public File[] listFilesAndFolders(String directoryName){
        File[] fList  = new File(directoryName).listFiles();

        return fList;
    }




}
