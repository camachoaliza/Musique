/** 
 * FILE NAME: MusiqueGUI.java
 * WHO: CS 230
 * WHEN: December 2017
 * WRITTEN BY: Liz Borecki, Aliza Camacho, Emily Mattlin
 * WHAT: GUI that displays the panel consisting of each panel
 * and shows it on a JFrame
 */
import javax.swing.JFrame;

public class MusiqueGUI {

  public static void main (String[] args) {
    // creates and shows a Frame 
    JFrame frame = new JFrame("Musique");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    //creates an instance of the Song object
    Song song = new Song();

    //create a panel, and add it to the frame
    InstrumentPanel container = new InstrumentPanel(song);
    frame.getContentPane().add(container.getPanels());
    
    frame.pack();
    frame.setVisible(true);
  }
}