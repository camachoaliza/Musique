/** 
 * FILE NAME: InstrumentPanel.java
 * WHO: CS 230
 * WHEN: December 2017
 * WRITTEN BY: Liz Borecki, Aliza Camacho, Emily Mattlin
 * WHAT: A Cardlayout object which is actually a container of all the
 * panels that need to be switched between. The panels it contains is
 * the TitlePanel, LyricsPanel, and the SongPanel.
 */
import java.awt.*;
import javax.swing.*;

public class InstrumentPanel{

  protected static JPanel cards;
  protected static CardLayout layout;
  
  protected final static String TITLEPANEL = "Title Page";
  protected final static String LYRICPANEL = "Lyric Input Page";
  protected final static String SONGPANEL = "Music Page";
  protected static Song newSong;
  
  /**
   * Constructor instantiates an InstrumentPanel object
   * @param s song from the lyrics panel or random button
   */
  public InstrumentPanel(Song s){
    newSong = s;
    layout = new CardLayout();
    cards = new JPanel(layout);
    //Components controlled by the CardLayout are initialized:
    JPanel titlePg = new TitlePanel();
    
    //add it to the panel
    cards.add(titlePg, TITLEPANEL);
    layout.show(cards, TITLEPANEL);
  }
  
  /**
   * Creates a LyricPanel and adds it to the InstrumentPanel's cards. Then
   * it displays the LyricPanel, and hides the TitlePanel
   */
  public static void inputLyrics(){
    JPanel lyricInputPg = new LyricsPanel();
    cards.add(lyricInputPg, LYRICPANEL);
    layout.show(cards, LYRICPANEL);
  }
  
  /**
   * Displays the TitlePanel again, so the user can create a new song
   */
  public static void startAgain(){
    layout.show(cards, TITLEPANEL);
  }
  
  /**
   * Creates a SongPanel and adds it to the InstrumentPanel's cards. Then sets
   * the Song objects lyrics, mood, and speed to random settings. Finally 
   * it displays the SongPanel with the random song, hides the TitlePanel, and
   * prints the scale being used to the command-line
   */
  public static void makeRandomSong(){
    newSong.setLyrics("random");
    newSong.setMood("random");
    newSong.setSpeed("random");
    JPanel musicPg = new SongPanel(newSong);
    cards.add(musicPg, SONGPANEL);
    layout.show(cards, SONGPANEL);
  }
  
  /**
   * Creates a SongPanel and adds it to the InstrumentPanel's cards. Then sets
   * the Song objects lyrics, mood, and speed to input from the LyricsPanel. Finally 
   * it displays the SongPanel with the user's song, and hides the LyricsPanel
   */
  public static void makeNewSong(String lyrics, String mood, String speed){
    newSong.setLyrics(lyrics);
    newSong.setMood(mood);
    newSong.setSpeed(speed);
    JPanel musicPg = new SongPanel(newSong);
    cards.add(musicPg, SONGPANEL);
    layout.show(cards, SONGPANEL);
  }
  
  /**
   * Gets all the JPanels creates in an InstrumentPanel
   * @return JPanel the cards in the InstrumentPanel container
   */
  public static JPanel getPanels(){
    return cards;
  }
}