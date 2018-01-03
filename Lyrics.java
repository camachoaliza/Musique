/**
 * FILE NAME: Lyrics.java
 * WHO: Aliza Camacho, Liz Borecki, Emily Mattlin
 * WHEN: December 2017
 * WHAT: This class uses an original algorithm to get a LinkedList of syllables of a String
 * The toSyallables method is nowhere near perfect, but it handles many common exceptions 
 * like with words that end in "ed" or a vowel that is silent
 * The toSyllables method does not work with portmanteaus
 */
import java.util.LinkedList;
import java.util.*;
import javafoundations.*;
import java.util.Scanner;
import java.io.*;

public class Lyrics {
    //instance variables
    private String songLyrics; //what is coming into the class
    private LinkedList <String> syllabels; //what the result is of the class
    private final char[] vowels = {'a', 'e', 'i', 'o', 'u', 'y', 'A', 'E', 'I', 'O', 'U', 'Y'};
  
  /**
   * Constructor for the Lyrics object
   * Instantiates instance variables
   * @param userLyrics The user's lyrics input
   */
  public Lyrics (String userLyrics) {
    songLyrics = userLyrics; 
    syllabels = new LinkedList<String>();
  }
  
  /**
   * Takes the string and splits it by the spaces to return an array of the words
   * @return A string array of the words without spaces
   */
  public String[] toWords() {
    String[] words = songLyrics.split("\\s");
    return words;
  }

   /**
   * Takes an array of Strings and separates each String words into syllables and puts the syllables into a LinkedList
   * The details of how the words are being divided are within the comments in the method
   * @param array an array of Strings of words
   * @return A LinkedList of syllable Strings
   */
  public LinkedList<String> toSyllables(String[] array){
    LinkedList<String> list = new LinkedList<String>();
    String s = "";
    char c;
    boolean vowelEnd = false;
    for(String i : array){ //for each word in the array
      if(i.length() == 1){ //if the word is only one letter,
          list.add(i); // add the word to the list of syllables
      }
      else{
        for(int j = 0; j < i.length(); j++){ // for each letter in a word
          c = i.charAt(j); //c is the current letter
          s += c; //s is the syllable that will be added to the list of syllables
          
          if(s.length()>1 && j != i.length()-2){ //if the syllable has more than one letter so far and the current iteration number through the values is equal to the length of the word - 2
            //ex: if i == "word", c == 'o', s == "wo", j == 1, and i.length() - 2 == 2, so the statement is true
            if(j == i.length()-1){ //in our example, so be true, j == 3, so s == "word"
              list.add(s); // s is added to the list of syllables
              s = ""; // s is cleared
            }
            
            else if((!isVowel(c) && isVowel(i.charAt(j-1)) && isEdWord(s, j, i))){ //if current c is not a vowel, but previous letter was, and it is not an "EdWord" (see below)
              //examples of what this is true for: "started" when c == 'r', "car" when c == "r", "apple" when c == "p"
              list.add(s); // s is added to the list of syllables
              s = ""; // s is cleared
            }
          }

          else if(!isVowel(c) && s.length()>1 && j == i.length()-2 && isVowel(i.charAt(i.length()-1)) && (i.charAt(i.length()-1) != 'E' && i.charAt(i.length()-1) != 'e')) {
            //if c is not a vowel, s length > 1, current iteration number through the values is equal to the length of the word - 2, the last letter of the word is a vowel that is not the letter 'e' or 'E'
            list.add(s); // s is added to the list of syllables
            s = ""; // s is cleared
            vowelEnd = true;
          }

          else if(s.length()==1 && j == i.length()-1 && vowelEnd){
            //if s is one letter, there is a vowel that isn't 'e' or 'E' at the end and the iteration is through the last letter on the current word
            list.add(s); // s is added to the list of syllables
            s = ""; // s is cleared
            vowelEnd = false;
          }
        }
      }
    }
    return list; // return the list of syllables
  }

  /**
   * Checks if a character is a vowel
   * @param c the character being checked
   * @return boolean that returns true if character c is a vowel, and false if it isn't
   */
  private boolean isVowel(char c){ //is c a vowel?
    for (char i : vowels){
      if( c == i){
        return true;
      }
    }
    return false;
  }
  
  /**
   * Checks if the word ends in "ed" and if it needs to be divided before that "ed" like when the preceding character is 't' or 'd'
   * @param s A String that is the syllable being built so far
   * @param j An integer of how many letters into the word we have incremented at the moment of this method call
   * @param i The full word being checked for an "ed" ending
   * @return boolean that returns true if the word ends in "ted" or "ded"
   */
  private boolean isEdWord(String s, int j, String i){ 
    //examples: returns true if word needs to be divided before "ed" like "divided" would be true, "learned" would return false
    if(j != i.length()-1 && j != i.length()-2){
      return !(i.charAt(j+1) == 'e' && i.charAt(j+2) == 'd' && (s.charAt(s.length()-1) != 'd' && s.charAt(s.length()-1) != 't'));
    }
    return true;
  }
}