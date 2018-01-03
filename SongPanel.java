/** 
 * FILE NAME: SongPanel.java
 * WHO: CS 230
 * WHEN: December 2017
 * WRITTEN BY: Liz Borecki, Aliza Camacho, Emily Mattlin
 * WHAT: Final panel that displays all the notes assigned to each syllable
 * and allows the user to play the song. Then the user can choose to
 * either quit or start again.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.FileNotFoundException;


public class SongPanel extends JPanel {
  //instance variables
  private final Color lightBlue = new Color(173,216,230);
  private final Color lightPink = new Color(255,182,193);
  
  private JButton play, quit, newSong;
  private JLabel titleLab;
  private ImageIcon playImg, titleImg, quitImg, newSongImg;
  private FormatMusicText musicText;
  private Song song; 
  
  /**
   * Constructor that takes a Song object as input
   * @param m Song object 
   * @exception FileNotFoundException if file does not exist
   */
  public SongPanel(Song music) {
    try{
      this.song = music;
      String txtfile = song.compose();
      musicText = new FormatMusicText(txtfile);
    }catch(FileNotFoundException ex){
      System.out.println("Error");
    }
    
    //creates images
    titleImg = createImageIcon("images/yourSong.jpg",
                           "title image");
    playImg = createImageIcon("images/play.jpg",
                           "play image");  
    quitImg = createImageIcon("images/quit.jpg",
                           "quit image");
    newSongImg = createImageIcon("images/newSong.jpg",
                           "new song image");
    //creates labels and buttons
    titleLab = new JLabel(titleImg, JLabel.CENTER);
    play = new JButton(playImg);
    quit = new JButton(quitImg);
    newSong = new JButton(newSongImg);
    //decorates main panel
    setLayout(new GridLayout(3,1,10, 10)); // hgap, vgap
    setBackground(lightPink); 
    add(makeTopPanel());
    add(makeMiddlePanel());
    add(makeBottomPanel());
  }
  
  /**
   * Creates and returns the top panel in the final GridLayout Panel.  
   * @return JPanel Panel that contains the title at the top
   */
  private JPanel makeTopPanel() {
    JPanel top = new JPanel();
    top.setBackground(lightBlue);
    top.add(titleLab);   
    return top;
  }
  
  /**
   * Creates and returns the bottom panel in the final GridLayout Panel. 
   * @return JPanel Panel that contains play, quit, and start again button
   */
  private JPanel makeBottomPanel() {
    JPanel bottom = new JPanel(new GridLayout(2, 1, 1, 1));
    bottom.setBackground(lightPink);
    bottom.add(makePlayPanel());
    bottom.add(makeQuitRedo());
    return bottom;
  }
  
  /**
   * Creates and returns the panel in the South panel containing the 
   * play button to play the song
   * @return JPanel Panel that contains the title at the top
   */
  public JPanel makePlayPanel(){
    JPanel playPanel = new JPanel();
    playPanel.setBackground(lightPink);
    
    //decorating and adding listeners to buttons
    playPanel.add(play);
    play.setBackground(Color.white);
    play.addActionListener(new ButtonListener());
    return playPanel;
  }
  
  /**
   * Creates and returns the panel in the south panel containing the 
   * quit and start again button
   * @return JPanel Panel that contains the quit and start again button
   */
  public JPanel makeQuitRedo(){
    JPanel quitRedoPanel = new JPanel(new GridLayout(1, 4, 1, 1));
    quitRedoPanel.setBackground(lightBlue);
    ImageIcon noteImg = createImageIcon("images/bottom border.png",
                           "instructions");
    
    quitRedoPanel.add(new JLabel(noteImg));
    quitRedoPanel.add(quit);
    quitRedoPanel.add(newSong);
    quitRedoPanel.add(new JLabel(noteImg));
    
    //decorating and adding listeners to buttons
    quit.setBackground(lightBlue);
    quit.addActionListener(new ButtonListener());
    newSong.setBackground(lightBlue);
    newSong.addActionListener(new ButtonListener());
    
    return quitRedoPanel;
  }
  
  /**
   * Creates and returns the middle panel in the final GridLayout Panel.  
   * Contains all the notes and their corresponding syllables
   * @return JPanel Panel that contains all the syllables and notes
   */
  private JPanel makeMiddlePanel () {
    JPanel middle = new JPanel();   
    middle.setBackground(lightPink);
    
    //uses a FormatMusicText obect to access the syllables and notes in the textfile
    LinkedList<String> syllables = musicText.sortSylls();
    LinkedList<String> notes = musicText.getNotes();
    
    //creates a notePanel for each note-syllable pairing
    for (int i=0;i<notes.size(); i++){
      JPanel notePanel = makeNotePanel(syllables.get(i), notes.get(i));
      middle.add(notePanel);
    }
    
    return middle;
  }

  /**
   * Creates and returns the panel of a note and its corresponding syllable
   * @return JPanel Panel that contains a note and syllable
   */
  public JPanel makeNotePanel(String syllable, String note){
    JPanel notePanel = new JPanel(new GridLayout(1, 2, 10, 10));
    JLabel noteLab = new JLabel(note);
    JLabel syllaLab = new JLabel(syllable);
    
    notePanel.add(noteLab);
    notePanel.add(syllaLab);
    
    noteLab.setFont(new Font("Serif", Font.BOLD, 40));
    syllaLab.setFont(new Font("Serif", Font.BOLD, 40));

    return notePanel;
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
    /** 
     * If play button is pressed the song created in the Song object is
     * played, the system exits if quit is pressed, and the user
     * is taken back to the home page if start again is pressed
     * @param event is the event which causes an action to be performed
     * @exception FileNotFoundException when file called does not exist 
     **/
    public void actionPerformed (ActionEvent event){
      if (event.getSource() == play){
        //song.compose();
        try{
          song.playSong();
        }catch(FileNotFoundException ex){
          System.out.println("Error");
        }
        
      }
      //quit button was pressed
      else if (event.getSource() == quit)
        System.exit(0);
      //start again button was pressed
      else if (event.getSource() == newSong)
        InstrumentPanel.startAgain();
    }
  }
  
}