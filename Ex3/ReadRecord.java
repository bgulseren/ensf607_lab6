/** 
 * Started by M. Moussavi
 * March 2015
 * Completed by: Khaled Behairy, Burak Gulseren
 * 
 * class to read binary file and de-serialize objects of MusicRecord and print to console
 * 
 */

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ReadRecord {
    
    private ObjectInputStream input;
    
    /**
     *  opens an ObjectInputStream using a FileInputStream
     *  
     *  @param name - name of file to open and read from
     */
    
    private void readObjectsFromFile(String name){
        MusicRecord record;
        
        // open binary file to de-serialize objects from
        try{
        	input = new ObjectInputStream(new FileInputStream(name));
        }
        catch (IOException ioException){
            System.err.println( "Error opening file." );
            System.exit(1);
        }
        
        /** The following loop is supposed to use readObject method of
         * ObjectInputSteam to read a MusicRecord object from a binary file that
         * contains several records.
         * Loop should terminate when an EOFException is thrown.
         **/
        
       // reading the objects
        try{
            while ( true ){    
            	record = (MusicRecord) input.readObject();
            	System.out.println(record.getYear() + " " + record.getSongName() + " " + record.getSingerName() + " " + record.getPurchasePrice());
            }
        } catch(ClassNotFoundException | IOException e) {
        	System.err.println("End of file");
            // close file
            try {
    			input.close();
    		} catch (IOException x) {
    			System.err.println("Error closing file.");
    			System.exit(1);
    		}
        }
        
    }
    
    
    public static void main(String [] args){
        ReadRecord d = new ReadRecord();
        d.readObjectsFromFile("allSongs.ser");
    }
}