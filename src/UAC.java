import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;
//import javax.swing.JOptionPane;

public final class UAC {

    public static String batName = "elevate.bat";
    static Runtime runtime = Runtime.getRuntime();

    public static void run() {
        if (checkForUac()) {//uac is on
            //JOptionPane.showMessageDialog(null, "I am not elevated");
            //attempt elevation
            new UAC().elevate();
            System.exit(0);
        } /*else {//uac is not on
            //if we get here we are elevated
            JOptionPane.showMessageDialog(null, "I am elevated");
            
            try {
            	Process stop = runtime.exec("netsh wlan stop hostednetwork");
            	stop.waitFor();
				Process start = runtime.exec("netsh wlan start hostednetwork");
				start.waitFor();
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }*/
    }

    private static boolean checkForUac() {
        File dummyFile = new File("c:/aaa.txt");
        dummyFile.deleteOnExit();
        try {
            //attempt to craete file in c:/
            try (FileWriter fw = new FileWriter(dummyFile, true)) {
            }
        } catch (IOException ex) {//we cannot UAC muts be on
            //ex.printStackTrace();
            return true;
        }
        return false;
    }

    private void elevate() {
        //create batch file in temporary directory as we have access to it regardless of UAC on or off
        File file = new File(System.getProperty("java.io.tmpdir") + "/" + batName);
        file.deleteOnExit();
        createBatchFile(file);
        //JOptionPane.showMessageDialog(null, file);
        runBatchFile();
    }

    private String getJarLocation() {
        return getClass().getProtectionDomain().getCodeSource().getLocation().getPath().substring(1);
    }

    private void runBatchFile() {
  
        String[] cmd = new String[]{"cmd","/C",
            System.getProperty("java.io.tmpdir") + "/" + batName + " java -jar " + getJarLocation()};
        
        //String path = System.getProperty("java.io.tmpdir") + "/" + batName + " java -jar " + getJarLocation();
        try {
        	//Process test = runtime.exec("ipconfig /all");
        	Process startUAC = runtime.exec(cmd);
        	//Wypisywanie efektow powyzszej komendy
        	BufferedReader inStreamReader = new BufferedReader(new InputStreamReader(startUAC.getInputStream())); 
        	String stream;
        	    while((stream = inStreamReader.readLine()) != null){
        	        System.out.println(stream);
        	    }
            //Runtime.getRuntime().exec("cmd /B start /C min "+path);
            //proc.waitFor();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void createBatchFile(File file) {
        try {
            try (FileWriter fw = new FileWriter(file, true)) {
                fw.write(
                		"@echo Set objShell = CreateObject(\"Shell.Application\") > %temp%\\sudo.tmp.vbs\r\n"
                                + "@echo args = Right(\"%*\", (Len(\"%*\") - Len(\"%1\"))) >> %temp%\\sudo.tmp.vbs\r\n"
                                + "@echo objShell.ShellExecute \"%1\", args, \"\", \"runas\", 0 >> %temp%\\sudo.tmp.vbs\r\n"
                                + "@cscript %temp%\\sudo.tmp.vbs\r\n"
                                + "del /f %temp%\\sudo.tmp.vbs\r\n");
            }
        } catch (IOException ex) {
            //ex.printStackTrace();
        }
    }
}