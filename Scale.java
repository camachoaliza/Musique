/**
 * FILE NAME: Scale.java
 * WHO:  Emily, Liz, Aliza
 * WHEN: December 2017
 * WHAT: Extends the AdjListGraph class to make a graph of a random major or minor scale from a selected .txt file
 * Started By: CS230 Instructors
 * Modified By: Liz Borecki
 */

import java.io.FileNotFoundException;
import java.util.*;

public class Scale<T> extends AdjListGraph<T> {
  
  /*
   * Constructs an empty graph by calling the AdjListGraph<T> constructor
   */
  public Scale() {
    super();
  }
  
  /*
   * Reads a TXT file and creates a Scale<String> from it.
   * 
   * selects a random txt file name from an array depending on the parameter
   * sends the txt file name and a new scale object to the parent class of AdjListGraph<T>'s method loadTGF
   * to create the graph in the scale
   * 
   * txt files were used over TGF files as TGF files had formatting errors and txt files did not
   * 
   * @param String feeling - the preference for the type of scale to be created
   * @return a scale(graph) created from the TXT file
   * @throws FileNotFoundException if TGF/TXT file is not found.
   */
  public static Scale<String> chooseScale(String feeling) throws FileNotFoundException {
    //an array of happy/major and an array of sad/minor text files of scales to choose from to create
    String[] majorScales = {"gmajor.txt", "cmajor.txt", "dmajor.txt", "amajor.txt","bbmajor.txt"};
    String[] minorScales = {"aminor.txt", "eminor.txt", "fminor.txt", "bminor.txt"}; //"a#minor.txt",
    
    //initalizes the variable to load a  graph into and to return for the output 
    Scale<String> scale = new Scale<String>();
    //initalizes a string for the filename
    String fileName = "";
    
    //if the parameter is equal to happy, a random file name with a major scale graph from majorScales is selected
     if (feeling.equals("happy")){
      Random r = new Random();
      int index = r.nextInt((majorScales.length) );
      fileName = majorScales[index];
    }
    //if the parameter is equal to sad, a random file name with a minor scale graph from minorScales is selected
    else if (feeling.equals("sad")){
      Random r = new Random();
      int index = r.nextInt((minorScales.length));
      fileName = minorScales[index];
    }
    //if there is an error, and the string does not equal happy or sad, chooses gmajor scale as a default
    else {
      fileName = majorScales[0];
    }
    
    /*sends the filename for the textfile to be loaded into the scale object, uses the method loadTGF in the parent
    * class of AdjListGraph<T> that works with textfiles in addition to TGFs
    */
    AdjListGraph.loadTGF(fileName, scale);
    
    //returns the created scale object
    return scale;
  }  

}
