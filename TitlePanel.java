/** 
 * FILE NAME: TitlePanel.java
 * WHO: CS 230
 * WHEN: December 2017
 * WRITTEN BY: Liz Borecki, Aliza Camacho, Emily Mattlin
 * WHAT: Creates the Title page of the program that allows the user to choose
 * between making a random song or song with their own choice of 
 * lyrics, mood, and speed.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TitlePanel extends JPanel {
  //instance variables
  private final Color lightBlue = new Color(173,216,230);
  private final Color lightPink = new Color(255,182,193);
  private JButton lyrics, random;
  private JLabel notes, title;
  private ImageIcon notesImg, lyricsImg, randomImg, titleImg;    
  
  /**
   * Constructor instantiates a TitlePanel object
   */
  public TitlePanel() {
    notesImg = createImageIcon("images/musical-notes.jpg",
                           "notes image");
    titleImg = createImageIcon("images/title.jpg",
                           "title image");
    lyricsImg = createImageIcon("images/insertLyrics.jpg",
                           "lyrics button image");
    randomImg = createImageIcon("images/random.jpg",
                           "random button image");   
    
    notes = new JLabel(notesImg, JLabel.CENTER);
    title = new JLabel(titleImg, JLabel.CENTER);
    lyrics = new JButton(lyricsImg);
    random = new JButton(randomImg);
    
    
    setLayout(new BorderLayout(10, 10)); // hgap, vgap
    setBackground(lightBlue);
    add(makeNorthPanel(), BorderLayout.NORTH);
    add(makeCenterPanel(), BorderLayout.CENTER);
    add(makeSouthPanel(), BorderLayout.SOUTH);
  }
  
  /**
   * Creates and returns the JPanel in the north of the BorderLayout
   * @return JPanel Panel with title
   */
  private JPanel makeNorthPanel() {
    JPanel north = new JPanel(new GridLayout(3,1,1,1));
    JLabel names = new JLabel("Written by: Elizabeth Borecki, Aliza Camacho, and Emily Mattlin", JLabel.CENTER);
    JLabel rules = new JLabel("Click 'Insert lyrics' to make a customized song "+
                               "and 'Random' to make a randomly created song", JLabel.CENTER);
    rules.setFont(new Font("Serif", Font.BOLD, 40));
    north.setBackground(Color.white);
    north.add(title);
    north.add(names);
    north.add(rules);
    return north;
  }
  
  /**
   * Creates and returns the JPanel in the south of the BorderLayout
   * @return JPanel Panel with image of notes
   */
  private JPanel makeSouthPanel() {
    JPanel southPanel = new JPanel();
    southPanel.setBackground(Color.white);
    southPanel.add(notes);
    return southPanel;
  }
  
  /**
   * Creates and returns the JPanel in the center of the BorderLayout
   * containing the choice buttons of making a Random song or a customized
   * song with your own lyrics
   * @return JPanel Panel with two buttons
   */
  private JPanel makeCenterPanel () {
    JPanel centerPanel = new JPanel();
    centerPanel.setBackground(lightBlue);
    centerPanel.setLayout(new GridLayout(1, 2, 10, 10));
    
    lyrics.setBackground(lightPink);
    lyrics.addActionListener(new ButtonListener());
    random.setBackground(lightPink);
    random.addActionListener(new ButtonListener());
    
    centerPanel.add(lyrics);
    centerPanel.add(random);    
    return centerPanel;
  }
  
  /** 
   * Creates and returns an ImageIcon out of an image file.
   * @param path The path to the imagefile relevant to the current file.
   * @param description A short description to the image.
   * @return ImageIcon An ImageIcon, or null if the path was invalid. 
   */
  private static ImageIcon createImageIcon(String path, String description) {
    java.net.URL imgURL = Song.class.getResource(path);
    if (imgURL != null) {
      return new ImageIcon(imgURL, description);
    } else {
      System.err.println("Couldn't find file: " + path);
      return null;
    }
  }

  /**
   * ButtonListener is a private class for responding to button push events 
   */ 
  private class ButtonListener implements ActionListener {
    /** Opens the lyric input page if the input lyrics button is pressed
      * and opens the final page with music if the random button is pressed
      * @param event is the event which causes an action to be performed
      **/
    public void actionPerformed (ActionEvent event) {
      //input lyrics button was pressed and opens the input lyrics page
      if (event.getSource() == lyrics)
        InstrumentPanel.inputLyrics();
      //random button was pressed and creates a new Melody object
      else if (event.getSource() == random)
        InstrumentPanel.makeRandomSong();         
    }
  }
  
}