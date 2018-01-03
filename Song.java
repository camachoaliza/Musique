/**
 * FILE NAME: Song.java
 * WHO: Aliza Camacho, Liz Borecki, Emily Mattlin
 * WHEN: December 2017
 * WHAT: This class plays a random song using one Scale, composes it and records it into a text file, 
 * and records a .wav file of the song composed
 */
import java.util.LinkedList;
import java.util.*;
import javafoundations.*;
import java.util.Scanner;
import java.io.*;

public class Song{
 //instance variables
 private String lyrics, mood;
 private double[] durations;

 private double[] ourDurs;
 private double[] allNotes;
 private Scale ourScale;

 private final String[] moods = {"happy", "sad"};
 private final double[] slow = {0.375, 0.5, 0.75, 1, 1.5}; //time lengths of longer notes
 private final double[] fast = {0.0625, 0.125, 0.25, 0.375, 0.5}; //time lengths of shorter notes
 private final double[][] speeds = {slow, fast};
 private final double timeSignature = 1.5;


 /**
   * Constructor
   * Make a new instance of a song object
   */
 public Song(){ 

 }

 /**
   * Sets the lyrics to user input, or if random, sets lyrics randomly from an array of lyrics
   * @param l The user's string of lyrics
   */
 public void setLyrics(String l){
  if(l==("random")){
   String[] poems = {"I have eaten the plums that were in the ice box and which you were probably saving for breakfast Forgive me they were delicious so sweet and so cold", "I'm nobody! Who are you? Are you nobody, too? Then there's a pair of us -- don't tell! They'd advertise -- you know! How dreary to be somebody! How public like a frog To tell one's name the livelong day To an admiring bog!", "For sale. Baby shoes. Never worn.", "Hold fast to dreams For if dreams die Life is a broken-winged bird That cannot fly. Hold fast to dreams For when dreams go Life is a barren field Frozen with snow."};
   int rnd = new Random().nextInt(moods.length);
   this.lyrics = poems[rnd];
  }
  else{this.lyrics = l;}
  ourDurs = new double[getSyllablesSize()];
 }

 /**
   * Sets the mood to user input, or if random, sets mood randomly
   * @param m The user's mood "happy," "sad," or "random"
   */
 public void setMood(String m){
  if(m==("random")){
   int rnd = new Random().nextInt(moods.length);
   this.mood = moods[rnd];
  }
  else{this.mood = m;}
 }

 /**
   * Sets the speed to user input, or if random, sets mood randomly
   * @param s The user's speed "fast," "slow," or "random"
   */
 public void setSpeed(String s){
  if(s==("random")){
   int rnd = new Random().nextInt(speeds.length);
   durations = speeds[rnd];
  }
  else if(s == "fast"){
   durations = fast;
  }
  else{
   durations = slow;
  }
 }

 /**
   * Sets the speed to user input, or if random, sets mood randomly
   * @param s The user's speed "fast," "slow," or "random"
   */
 public double getDuration(){
  Random rnd = new Random();
       int index = rnd.nextInt(5);
     return durations[index];
 }

 /**
   * Uses the methods in the Lyrics class to get a LinkedList of the syllables from the lyrics string
   * @return The syllables of the lyrics in a LinkedList of Strings
   */
 public LinkedList<String> getSyllables(){
  Lyrics ourLyrics = new Lyrics(lyrics);
  String[] lyricsArray = ourLyrics.toWords();
  LinkedList<String> lyricsSyllables = ourLyrics.toSyllables(lyricsArray);
  return lyricsSyllables;
 }

 /**
   * Gets the amount of syllables in the lyrics
   * @return the amount of syllables
   */
 public int getSyllablesSize(){
  LinkedList<String> syllables = this.getSyllables();
  return syllables.size();
 }

 /**
   * Picks a random scale using mood, assigns note frequencies from that scale into an array of all the notes
   * @return the note frequencies array
   * @throws FileNotFoundException for chooseScale() method for txt files of scales
   */ 
 public double[] pickNote() throws FileNotFoundException {
  Random random = new Random();
  int noteIndex = random.nextInt(14 - 0 + 1) + 0;
  double note;
  LinkedList<String> options = new LinkedList<String>();
  double[] notes = new double[getSyllablesSize()];
  HashMap<String, Double> map = HashThat.makeHashTable();
  ourScale = Scale.chooseScale(this.mood);

  for(int i = 0; i < getSyllablesSize(); i++){
   options = ourScale.getSuccessors(ourScale.getVertex(noteIndex));
   int rnd = new Random().nextInt(5);
      note = map.get(options.get(rnd));
      notes[i] = note;
  }
  return notes;
 }

 /**
   * Creates a note for the StdAudio play() method, with the frequency of the note and the duration of the note
   * @param hz The frequency of the note
   * @param duration the duration of the note
   * @return The sin graph of the note being played and the duration
   */
 public static double[] note(double hz, double duration) {
        int dur = (int) (StdAudio.SAMPLE_RATE * duration);
        double[] noteCurrent = new double[dur+1];
        for (int i = 0; i <= dur; i++){
            noteCurrent[i] = 0.5 * Math.sin(2 * Math.PI * i * hz / StdAudio.SAMPLE_RATE);
        }
        return noteCurrent;
    }

    /**
   * Creates a single dimensional array from a 2D array
   * @param input a 2D array of doubles
   * @return Single dimensional array
   */
    public static double[] combine(double[][] input) {
        int combinedLength = 0;
        for (int i = 0; i < input.length; i++) {
            combinedLength += input[i].length;
        }
        double[] output = new double[combinedLength];
        int x = 0;
        for (int y = 0; y < input.length; y++) {
            for (int z = 0; z < input[y].length; z++) {
                output[x] += input[y][z];
                x++;
            }
        }
        return output;
    }

    /**
   * Uses the list of random notes and assigns durations to them, 
   * writes them to a text file and saves them onto a .wav file in the form of a song
   * @return The text file name where the note frequencies, durations, and syllables are written to
   * @throws FileNotFoundException from chooseScale() method for txt files of scales
   */
    public String compose() throws FileNotFoundException{
  LinkedList<String> syllables = this.getSyllables();
  double[][] songToSave = new double[getSyllablesSize()][2];
  allNotes = pickNote();
  double dur=0;
  try {
   PrintWriter pw = new PrintWriter("song.txt");
   for(int i = 0; i < syllables.size(); i++){
    ourDurs[i] = getDuration() * timeSignature;
    pw.println( allNotes[i] + " " + ourDurs[i] + " " + syllables.get(i) + " " );
    songToSave[i] = note(allNotes[i], ourDurs[i]);
   }
   pw.close();
  } catch (Exception ex) {
    ex.printStackTrace();
  }
  double[] test = combine(songToSave);
  StdAudio.save("newSong.wav", test);
  return "song.txt";
    }

    /**
   * Plays the song using the note frequencies and the durations of the song
   * and uses the StdAudio.play method to play the song
   * @throws FileNotFoundException from chooseScale() method for txt files of scales
   */
 public void playSong() throws FileNotFoundException {
//  StdAudio.play()
   for(int i = 0; i < getSyllablesSize(); i++){
    System.out.println(ourDurs[i]);
    StdAudio.play(note(allNotes[i], ourDurs[i]));
   }
 }
}