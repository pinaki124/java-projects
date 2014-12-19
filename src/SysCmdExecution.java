import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class SysCmdExecution {
	public static void executeCommand (String[] command) throws IOException {
		Runtime r = Runtime.getRuntime();
		Process p = null;
		
		try {
			p = r.exec(command);
		} catch (IOException | NullPointerException | IndexOutOfBoundsException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			if ( !p.waitFor(5, TimeUnit.SECONDS) ) {
				System.out.println("Error : Command execution timeout") ;
				p.destroy() ;
			}
		} catch (InterruptedException | NullPointerException e) {
			// TODO Auto-generated catch block
            System.out.println("Error: Command interrupt encountered.");
            e.printStackTrace();;
		}
		
		BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = "";

		while ((line = b.readLine()) != null) {
		  System.out.println(line);
		}
		
		b.close() ;
		
		BufferedReader b1 = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		String line1 = "";
		
		if ( b1.ready() ) { 
			System.out.println("Error encountered:") ;
		}
		
		while ((line1 = b1.readLine()) != null) {
		  System.out.println(line1);
		}

		b1.close();
	}
	
	public static void main(String[] args) throws IOException {
		String[] cmd = { "/bin/sh", "-c", "ps -ef | grep pinaki" } ; 
		// TODO Auto-generated method stub
		executeCommand(cmd);
	}
}
