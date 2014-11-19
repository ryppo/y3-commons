package org.y3.commons.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *  Helper class for file zip activities
 * @author christian.rybotycky
 */
/** 
 * <p>Title: org.y3.commons.file - ZipHelper</p>
 * <p>Description: Helper class for file zip activities</p>
 * <p>Copyright: 2013</p>
 * <p>Organisation: IT-Happens.de</p>
 * @author Christian.Rybotycky
 */
public class ZipHelper {
    
    /**
     * Puts content of a directory (incl. sub folders) to a zip file
     * If zip file does not exist, it will be created
     * Otherwise the files inside are overwritten
     * @param dirName name of the directory to zip
     * @param nameZipFile name of target zip file
     * @throws IOException 
     */
    public static void zipDir(String dirName, String nameZipFile) throws IOException {
        File dirFile = new File(dirName);
        ZipOutputStream zip = null;
        FileOutputStream fos = null;
        fos = new FileOutputStream(nameZipFile);
        zip = new ZipOutputStream(fos);
        addFolderToZip(dirFile, zip);
        zip.finish();
        zip.close();
        fos.close();
    }
    
    /**
     * Adds a folder to a zip file 
     * @param srcFolder source folder to zip
     * @param zip output zip stream
     * @throws IOException 
     */
    public static void addFolderToZip(File srcFolder, ZipOutputStream zip) throws IOException {
        File[] files = srcFolder.listFiles();
        byte[] buffer = new byte[1024];
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                addFolderToZip(files[i], zip);
                continue;
            }
            FileInputStream fis = new FileInputStream(files[i].getPath());
            zip.putNextEntry(new ZipEntry(files[i].getPath()));
            int len;
            while((len = fis.read(buffer)) > 0) {
                zip.write(buffer, 0, len);
            }
            zip.closeEntry();
            fis.close();
        }
    }

}
