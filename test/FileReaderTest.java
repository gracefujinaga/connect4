import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.*;

public class FileReaderTest {
	
	//test reading capabilities

	//read in a 6 by 7 array of numbers correctly
    @Test
    public void testGoodArray() {
        FileReaderWriter fr = new FileReaderWriter("files/arrayOnes.txt");
        int[][] test = fr.getBoard();
        for(int i = 0; i < 6; i++) {
        	for(int j = 0; j < 7; j++) {
        		assertEquals(1, test[i][j]);
        	}
        	System.out.println();
        }
    }
    
    //reads an empty file
    @Test
    public void testBadArray() {
        FileReaderWriter fr = new FileReaderWriter("files/emptyFile.txt");
        int[][] test = fr.getBoard();
        for(int i = 0; i < 6; i++) {
        	for(int j = 0; j < 7; j++) {
        		assertEquals(0, test[i][j]);
        	}
        }
    }
    
    //filepath doesnt exist
    @Test
    public void constructorFilePathNotFound() {
    	try {
    		new FileReaderWriter("files/haha");
    	} catch (IllegalArgumentException e) {
    		return;
    	}
    	fail();
    }
    
    //null filepath
    @Test
    public void testNull() {
    	try {
    		new FileReaderWriter(null);
    	} catch (IllegalArgumentException e) {
    		return;
    	}
    	fail();
    }
    
    
    //file that doesnt fit the invariants in terms of too long
    @Test
    public void testNotRightFormat() {
        FileReaderWriter fr = new FileReaderWriter("files/fileReaderTest.txt");
        int[][] test = fr.getBoard();
        for(int i = 0; i < 6; i++) {
        	for(int j = 0; j < 7; j++) {
        		assertEquals(0, test[i][j]);
        	}
        }
    }
    
    @Test
    public void testWriting() {
    	FileReaderWriter fr = new FileReaderWriter("files/writingTest.txt");
    	
    	int[][] z = new int[6][7];
    	for(int i = 0; i < 6; i++) {
    		for(int j = 0; j < 7; j++) {
    			z[i][j] = 1;
    		}
    	}
    	fr.writeWinner(z, 1);
    	
    	FileReaderWriter fr2 = new FileReaderWriter("files/writingTest.txt");
    	
    	int[][] that = fr2.getBoard();
    	for(int i = 0; i < that.length; i++) {
    		for(int j = 0; j < that[i].length; j++) {
    			assertEquals(1, that[i][j]);
    		}
    	}
    	assertEquals(1, fr.getPlayer());
    }
    
    @Test
    public void testWrite() {
    	FileReaderWriter fr = new FileReaderWriter("files/writingTest.txt");
    	fr.writeWinner(new int[6][7], 1);
    }

  

}
