/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reusable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.Runtime;
import java.lang.Process;
import java.util.logging.Level;
import java.util.logging.Logger;
import static reusable.ExecuteOSCommands.getOSname;

/**
 * @author Melis Oner
 */
public class FileStuff {

    private static Runtime rt;
    private String projectDirectory;

    public FileStuff() {
        Logger.getLogger(FileStuff.class.getName()).log(Level.INFO, "FileStuff constructor...");
        rt = Runtime.getRuntime();
        Logger.getLogger(FileStuff.class.getName()).log(Level.INFO, "Current Directory..." + getProjectDirectory() );
        
    }

    /**
     * @return the currentDirectory
     */
    public String getProjectDirectory() {
        char [] tmp = new char[100];
        int i = 0;
        if (ExecuteOSCommands.IS_WINDOWS) {
            try {
                Logger.getLogger(FileStuff.class.getName()).log(Level.INFO, "Trying to execute Current Directory...");
                Process p1 = rt.exec("cmd /c CD");
                InputStream is = p1.getInputStream();
                int data = is.read();
                while (data != -1){
                  tmp[i] = (char) data;
                  i++;
                  data = is.read();
                }
                //currentDirectory = new String(tmp).trim();
            } catch (IOException ex) {
                Logger.getLogger(FileStuff.class.getName()).log(Level.SEVERE, "IOException caught while getting current directory " + ex);
            }
        }
        
        projectDirectory =  new String(tmp).trim();
        return projectDirectory;
    }

    /**
     * @param currentDirectory the currentDirectory to set
     */
    public void setProjectDirectory(String currentDirectory) {
        this.projectDirectory = currentDirectory;
    }

}
