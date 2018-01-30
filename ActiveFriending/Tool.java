package ActiveFriending;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Tool {
	public static void to_gnuplot(String path)
	{
		File singleFile=new File(path);
		ArrayList<String> opt_size=new ArrayList<String>();
		ArrayList<String> rba_size=new ArrayList<String>();
		ArrayList<String> opt_prob=new ArrayList<String>();
		ArrayList<String> rba_prob=new ArrayList<String>();
		String inString = "";
		try {
            BufferedReader reader = new BufferedReader(new FileReader(singleFile));
            while((inString = reader.readLine())!= null){
            	//System.out.println(inString);
            	//System.out.println(inString);
            	String[] tempString = null;
    			tempString=inString.split(" ");
    			int node_1=Integer.parseInt(tempString[0])-1; 
    			int node_2=Integer.parseInt(tempString[1])-1; 
    			
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(" The path of data is incorrect.");
        } catch (IOException ex) {
            System.out.println("Error in reading data.");
        }
		System.out.println("loaded");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
