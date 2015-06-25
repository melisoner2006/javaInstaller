/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reusable;
import java.lang.Runtime;
import java.lang.Process;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Melis
 */
public class ExecuteOSCommands {
    private static String OSname;
    static Runtime  rt; 
    public static boolean IS_WINDOWS;
    public static boolean IS_LINUX;
    public static boolean IS_SOLARIS;

    
    
    public ExecuteOSCommands(){
        Logger.getLogger(ExecuteOSCommands.class.getName()).log(Level.INFO, "ExecuteOSCommands...constructor..");
        Logger.getLogger(ExecuteOSCommands.class.getName()).log(Level.INFO, "Operating System: " + getOSname());
        rt = Runtime.getRuntime();
        IS_WINDOWS  = is_os_windows();
        IS_LINUX    = is_os_linux();
        IS_SOLARIS  = is_os_solaris();       
        unzip();
    }

    public static void getProcessResult(Runtime rt, Process p){
        // Get process' output: its InputStream
        InputStream is = p.getInputStream();
        BufferedReader reader = new java.io.BufferedReader(new InputStreamReader(is));
        // And print each line
        String s = null;
        try {
            while ((s = reader.readLine()) != null) {
                System.out.println(s);
            }
          is.close();
        } catch (IOException ex) {
            Logger.getLogger(ExecuteOSCommands.class.getName()).log(Level.SEVERE, "IOException caught when printing out the process result...", ex);
        }        
    }
    
    public static void unzip(){
        //Process p;
        if (IS_WINDOWS){
            try {
                Logger.getLogger(ExecuteOSCommands.class.getName()).log(Level.INFO, "Trying to execute command...DIR");
                //In command prompt type -> cmd /c  to see other options
                Process p1 = rt.exec("cmd /c DIR");
                getProcessResult(rt, p1);
                Process p2 = rt.exec("cmd /c unzip C:\\Users\\Melis\\Documents\\NetBeansProjects\\SnapApp\\externalpackages\\handouts.zip");
                getProcessResult(rt, p2);
                Process p3 = rt.exec("cmd /c DIR C:\\Users\\Melis\\Documents\\NetBeansProjects\\SnapApp\\");
                getProcessResult(rt, p3);
            } catch (IOException ex) {
                Logger.getLogger(ExecuteOSCommands.class.getName()).log(Level.SEVERE, "IOException caught when executing unzip...", ex);
            }
        }
        else{
            System.out.println("Something went wrong");
            System.exit(0);
        }
    }
    
    private static boolean is_os_windows(){
        return getOSname().equals("Windows");
    }
    
    private boolean is_os_linux(){
        return getOSname().equals("Linux");
    }
    
    private boolean is_os_solaris(){
        return getOSname().equals("Solaris");
    }
    
    /**
     * @return the OSname
     */
    public static String getOSname() {
        OSname = System.getProperty("os.name");
        Character.toUpperCase(OSname.charAt(0));
        int i = 0;
        char[] tmp = new char[10];
        while (OSname.length() >= 1 && OSname.charAt(i) != ' '){
           tmp[i] = OSname.charAt(i);
           i++;
        }
        return new String(tmp).trim();
    }

    /**
     * @param OSname the OSname to set
     */
    public void setOSname(String OSname) {
        this.OSname = OSname;
    }
        
}
