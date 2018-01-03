/**
 * WHO: Aliza Camacho, Liz Borecki, Emily Mattlin
 * WHAT: Takes the output of Melody.java, which is a text file formatted with each line consisting
 * of a frequency, duration, and syllable of a word, and organizes it into two LinkeLists holding
 * the note names and syllables.
 */

import java.util.*;
import java.io.*;

public class FormatMusicText{
  //Instance variable
  private LinkedList<String> syllables;
  private LinkedList<Double> frequencies;
  private LinkedList<String> notes;
  private String fileContents;
  
  /**
   * Constructor to instantiate a instance of FormatMusicText and turns the input
   * file into one string
   * @param fileName name of the text file given from Melody 
   */
  public FormatMusicText(String fileName){
    fileContents = fileToString(fileName);
    frequencies = sortFreqs();
    notes = freqToNote();
    syllables = sortSylls();
  }
  
  /**
   * Sorts through the fileContents to find only the frequencies
   * then turns all the frequencies into doubles 
   * and adds them to a LinkedList
   * @return frequencies in a LinkedList
   */
  public LinkedList<Double> sortFreqs(){
    LinkedList<Double> freq = new LinkedList<Double>();  
    String[] everyLine = fileContents.split("\n");
    
    for (int i=0; i<everyLine.length; i++){
      String[] threeComps = everyLine[i].split(" ");
      double freqNum = Double.parseDouble(threeComps[0]);
      freq.add(freqNum);
    }
    return freq;
  }
  
  /**
   * Sorts through the fileContents to find only the syllables
   * and adds all the syllables to a Linked List
   * @return syllables in a LinkedList
   */
  public LinkedList<String> sortSylls(){
    LinkedList<String> sylls = new LinkedList<String>(); 
    String[] everyLine = fileContents.split("\n");
    
    for (int i=0; i<everyLine.length; i++){
      String[] threeComps = everyLine[i].split(" ");
      sylls.add(threeComps[2]);
    }
    return sylls;
  }
  
  /**
   * Gets the LinkedList containing the frequencies
   * @return frequencies
   */
  public LinkedList<Double> getFreqs(){
    return frequencies;
  }
  
  /**
   * Gets the LinkedList containing the syllables
   * @return syllables
   */
  public LinkedList<String> getSylls(){
    return syllables;
  }
  
  /**
   * Gets the LinkedList containing the notes
   * @return notes
   */
  public LinkedList<String> getNotes(){
    return notes;
  }
  
  /**
   * Uses hashMap method to map every frequency to a note name
   * and adds all the note names to a LinkedList
   * @return notes in a LinkedList
   */
  public LinkedList<String> freqToNote(){
    LinkedList<String> allNotes = new LinkedList();
    HashMap<String, Double> noteMap = HashThat.makeHashTable();
    
    for (int i=0; i<frequencies.size(); i++){
      String note = (String)HashThat.getKeyFromValue(noteMap, frequencies.get(i));
      allNotes.add(note);
    }
    
    return allNotes;
  }
  
  /**
   * Reads a file into a string and catches a FileNotFound 
   * exception if the file does not exist 
   * @param fileName output from Melody
   * @return file's contents in a single String
   */
  public static String fileToString (String fileName) {
    try {
      Scanner fileScan = new Scanner(new File(fileName));

      StringBuilder builder = new StringBuilder();
      while (fileScan.hasNext()) { 
        builder.append(fileScan.nextLine());
        builder.append("\n");
      }
      fileScan.close(); 
      return builder.toString();
    } catch (FileNotFoundException ex) {
      System.out.println(ex); 
      return "";
    } 
  }
  
  /**
   * Creates string representation of LinkedLists containing
   * the syllables, frequencies, and notes
   * @return string representation of FormatMusicText
   */
  public String toString(){
    String s = "";
    s += "Notes:\n" + notes;
    s += "\nCorresponding syllables:\n" + syllables;   
    return s;
  }
  
  public static void main(String[] args){
    FormatMusicText smallSong = new FormatMusicText("song.txt");
    System.out.println(smallSong.toString() + "\ngetSylls:\n" + smallSong.getSylls()
                      + "\ngetFreqs:\n" + smallSong.getFreqs()
                         + "\ngetNotes:\n" + smallSong.getNotes());
  }
}
