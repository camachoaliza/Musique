/** 
 * FILE NAME: LyricsPanel.java
 * WHO: CS 230
 * WHEN: December 2017
 * WRITTEN BY: Liz Borecki, Aliza Camacho, Emily Mattlin
 * WHAT: Creates a panel for the user to input their own lyrics, mood, and speed
 * of a song.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LyricsPanel extends JPanel {
  //instance varibles
  private final Color lightBlue = new Color(173,216,230);
  private final Color lightPink = new Color(255,182,193);
  
  private JLabel inputLab, speedLab, scaleLab, userLab;
  private JTextField lyricFld;
  private ImageIcon textImg, enterImg, scaleImg, speedImg, emptyImg, instructImg, overImg;
  private JButton enter;
  private JComboBox<String> scaleBox, speedBox;
  
  /**
   * Constructor instantiates a LyricsPanel object
   */
  public LyricsPanel() {
    //images on buttons and labels
    textImg = createImageIcon("images/input.jpg",
                           "text");
    enterImg = createImageIcon("images/enter.jpg",
                           "enter");
    speedImg = createImageIcon("images/speed.jpg",
                           "speed");
    scaleImg = createImageIcon("images/scale.jpg",
                           "scale");
    emptyImg = createImageIcon("images/empty.jpg",
                           "empty");
    instructImg = createImageIcon("images/instruct.jpg",
                           "instructions");
    overImg = createImageIcon("images/over.jpg",
                           "too many characters");
    //labels, textfields, and buttons
    inputLab = new JLabel(textImg, JLabel.CENTER);
    speedLab = new JLabel(speedImg, JLabel.CENTER);
    scaleLab = new JLabel(scaleImg, JLabel.CENTER);
    userLab = new JLabel(instructImg, JLabel.CENTER);
    lyricFld = new JTextField(20);
    enter = new JButton(enterImg);
    
    //decorate the panel
    setLayout(new GridLayout(4,1,0,10));
    setBackground(lightPink);
    add(lyricFieldPan());
    add(scalePanel());
    add(speedPanel());
    add(bottomPanel());
  }

  /**
   * Creates and returns the JPanel with the textfield for the lyrics
   * @return JPanel Panel with input lyrics textfield
   */
  public JPanel lyricFieldPan(){
    JPanel fieldPanel = new JPanel();
    fieldPanel.add(inputLab);
    fieldPanel.add(lyricFld);
    fieldPanel.setBackground(lightBlue);
    return fieldPanel;
  }
  
  /**
   * Creates and returns the JPanel with the choice of a fast or slow song
   * @return JPanel Panel with mood choice
   */
  public JPanel speedPanel(){
    JPanel speedPanel = new JPanel();
    String[] optionsTwo = {"...", "Fast", "Slow"};
    speedBox = new JComboBox<String>(optionsTwo);
    speedPanel.add(speedLab);
    speedPanel.add(speedBox);
    speedPanel.setBackground(lightBlue);
    return speedPanel;
  }
  
  /**
   * Creates and returns the JPanel with the choice of a happy or sad song
   * @return JPanel Panel with speed choice
   */
  public JPanel scalePanel(){
    JPanel scalePanel = new JPanel();
    String[] optionsOne = {"...", "Happy", "Sad"};
    scaleBox = new JComboBox<String>(optionsOne);
    scalePanel.add(scaleLab);
    scalePanel.add(scaleBox);
    scalePanel.setBackground(lightBlue);
    return scalePanel;
  }
  
  /**
   * Creates and returns the JPanel with the instructions label and enter button
   * @return JPanel Panel with instructions and enter button
   */
  public JPanel bottomPanel(){
    JPanel bottomPanel = new JPanel(new GridLayout(2,1,0,10));
    bottomPanel.add(userLab);
    bottomPanel.add(enterPanel());
    bottomPanel.setBackground(lightPink);
    return bottomPanel;
  }
  
  /**
   * Creates and returns the JPanel containing the enter button and decorations
   * @return JPanel Panel with enter button
   */
  public JPanel enterPanel(){
    JPanel enterPanel = new JPanel(new GridLayout(1,3,0,10));
    ImageIcon noteImg = createImageIcon("images/bottom border.png",
                           "instructions");
    enterPanel.add(new JLabel(noteImg));
    enterPanel.add(enter);
    enterPanel.add(new JLabel(noteImg));
    
    enter.setBackground(lightBlue);
    enter.addActionListener(new EnterListener());
    enterPanel.setBackground(lightPink);
    return enterPanel;
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

  private class EnterListener implements ActionListener {
    /**
     * When enter button is pressed the input of the textfield, and combo
     * boxes are given to the instrument panel to switch panels and create a new Song object
     * @param event event performed
     */
    public void actionPerformed(ActionEvent event) {
      //enter button pressed
      if (event.getSource() == enter) {
        
        String lyrics = lyricFld.getText();
        String mood = (String)scaleBox.getSelectedItem();
        String speed = (String)speedBox.getSelectedItem();
        
        int moodInd = scaleBox.getSelectedIndex();
        int speedInd = speedBox.getSelectedIndex();
        
        //if the user inputs nothing or misses an option, a message displays telling 
        //them they need to fill out every option
        if (lyrics.equals("") || moodInd == 0 || speedInd == 0)
          userLab.setIcon(emptyImg);
        else if (lyrics.length()>= 501)
          userLab.setIcon(overImg);
        else
          InstrumentPanel.makeNewSong(lyrics, mood, speed);
      }
    } 
  }
}  