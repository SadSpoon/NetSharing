import java.io.IOException;

import javax.swing.JOptionPane;


final public class cmd {
	static private Runtime runtime = Runtime.getRuntime();
	static public void exec(String command) {
		Process order;
		try {
			order = runtime.exec(command);
			order.waitFor();
		} catch (IOException | InterruptedException e) {
			JOptionPane.showMessageDialog(null, "cmd error");
			e.printStackTrace();
		}
		
	}
}
