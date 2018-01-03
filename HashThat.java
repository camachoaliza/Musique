/**
 * FILE NAME: HashThat.java
 * WHO: Aliza Camacho, Liz Borecki, Emily Mattlin
 * WHEN: December 2017
 * WHAT: This class uses the key of a music note and the value of its 
 * frequency in a map for distinguishing between the two over three octaves
 */
import java.util.HashMap;
import java.util.Map;

public class HashThat {

  /**
   * Static method to make the music HashMap
   * @return The hashmap
   */
  public static HashMap<String, Double> makeHashTable(){
   HashMap<String, Double> noteMap = new HashMap<String, Double>();
   noteMap.put("C4", new Double( 261.6));
   noteMap.put("C#4", new Double( 277.2));
   noteMap.put("D4", new Double( 293.7));
   noteMap.put("Eb4", new Double( 311.1));
   noteMap.put("E4", new Double( 329.6));
   noteMap.put("F4", new Double( 349.2));
   noteMap.put("F#4", new Double( 370.0));
   noteMap.put("G4", new Double( 392.0));
   noteMap.put("G#4", new Double( 415.3));
   noteMap.put("A4", new Double( 440.0));
   noteMap.put("Bb4", new Double( 466.2));
   noteMap.put("B4", new Double( 493.9));

   noteMap.put("C5", new Double( 523.3 ));
   noteMap.put("C#5", new Double( 554.4));
   noteMap.put("D5", new Double( 587.3));
   noteMap.put("Eb5", new Double( 622.3));
   noteMap.put("E5", new Double( 659.3));
   noteMap.put("F5", new Double( 698.5));
   noteMap.put("F#5", new Double( 740.0));
   noteMap.put("G5", new Double( 784.0));
   noteMap.put("G#5", new Double( 830.6));
   noteMap.put("A5", new Double( 880.0));
   noteMap.put("Bb5", new Double( 932.3));
   noteMap.put("B5", new Double( 987.8));

   noteMap.put("C6", new Double( 1047 ));
   noteMap.put("C#6", new Double( 1109));
   noteMap.put("D6", new Double( 1175));
   noteMap.put("Eb6", new Double( 1245));
   noteMap.put("E6", new Double( 1319));
   noteMap.put("F6", new Double( 1397));
   noteMap.put("F#6", new Double( 1480));
   noteMap.put("G6", new Double( 1568));
   noteMap.put("G#6", new Double( 1661));
   noteMap.put("A6", new Double( 1760));
   noteMap.put("Bb6", new Double( 1865));
   noteMap.put("B6", new Double( 1976));
   return noteMap;
  }
  
  /**
   * Static method to get key from the value in the hashmap
   * @param hm A Map that the method uses 
   * @param value object to get key from
   * @return The Key object
   */
  public static Object getKeyFromValue(Map<String,Double> hm, Object value) {
    for (Object o : hm.keySet()) {
      if (hm.get(o).equals(value)) {
        return o;
      }
    }
    return null;
  }
  
}