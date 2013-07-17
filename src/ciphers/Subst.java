/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ciphers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.String;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tharkinist Hallion
 */
public class Subst {

    private ArrayList<String> chars = new ArrayList<>();
    private static final String SEPARATOR = " - ";

    public Subst() {
        //Get the aphabets
        String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ .,()-!?\n0123456789";

        for (int i = 0; i < alphabets.length(); i++) {
            chars.add(alphabets.substring(i, i + 1));
        }
    }

    public static void main(String[] args) {
        
        //Analize the arguments
        String op = args[0];
        String inputFile = args[1];
        String keyFile = args[2];

        Subst sub = new Subst();

        //Start carrying out the operation
        if (op.equals("g")) {
            //Generate the map
            TreeMap<String, String> map = sub.generate();
            
            StringBuilder mapText = new StringBuilder();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String character = entry.getKey();
                String sub_character = entry.getValue();
                
                mapText.append(character).append(SEPARATOR).append(sub_character).append("\n");
            }
            
            //write the map to the keyFile, ignoring the inputFile
            try {
                File fKey = new File(keyFile);
                File fullFileKey = fKey.getAbsoluteFile();
                
                PrintWriter pw = new PrintWriter(new FileWriter(fullFileKey), false);
                pw.write(mapText.toString());
                pw.close();
            } catch (IOException ex) {
                Logger.getLogger(Subst.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else if (op.equals("e") || op.equals("d")) {
            //Locate the file accordingly
            File fInput = new File(inputFile);
            File fullFileInput = fInput.getAbsoluteFile();
            File fKey = new File(keyFile);
            File fullFileKey = fKey.getAbsoluteFile();

            //Read the input file for the input string
            StringBuilder input = new StringBuilder();
            Scanner s;
            try {
                s = new Scanner(fullFileInput);

                while (s.hasNext()) {
                    input.append(s.nextLine());
                    if (s.hasNext()) {
                        input.append("\n");
                    }
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Subst.class.getName()).log(Level.SEVERE, null, ex);
            }

            //Read the key file for the map
            //generate the map
            TreeMap<String, String> map = new TreeMap<>();

            Scanner s2;
            try {
                s2 = new Scanner(fullFileKey);

                //The map always starts with \n as the first key, so we treat it differently
                s2.nextLine();
                map.put("\n", s2.nextLine().split(SEPARATOR)[1]);

                //Take care of the rest
                while (s2.hasNext()) {
                    String line = s2.nextLine();
                    String[] chars = line.split(SEPARATOR);

                    //We check if the mappping has \n as the substituted character
                    //If it does, then we skip the next line
                    if (line.endsWith(SEPARATOR)) {
                        s2.nextLine();
                        map.put(chars[0], "\n");
                    } else {
                        //If it does not, then carry on
                        map.put(chars[0], chars[1]);
                    }
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Subst.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //See if we should encrypt, or decrypt
            if (op.equals("e")){
                System.out.println(sub.encrypt(input.toString(), map));
            } else {
                System.out.println(sub.decrypt(input.toString(), map));
            }
        }

        //=======================================================================================

//        //Test
//        TreeMap<String, String> map = sub.generate();
//        //Try to print it out
//        for (Map.Entry<String, String> entry : map.entrySet()) {
//            String original = entry.getKey();
//            String substituted = entry.getValue();
//
//            System.out.println(original + " - " + substituted);
//        }

//        String plain = "ISTHELTE WINDSOR";
//
//        System.out.println("Encoding text '" + plain + "'");
//        System.out.println("The cyphertext is: " + s.encrypt(plain, map) + "'");
//        
//        System.out.println("Decoding text '" + s.encrypt(plain, map) + "'");
//        System.out.println("The plaintext is: '" + s.decrypt(s.encrypt(plain, map), map) + "'");

    }

    public TreeMap<String, String> generate() {

        //Make an emptymap
        TreeMap<String, String> map = new TreeMap<>();

        //Make a copy of the chars ArrayList
        ArrayList<String> chars_Copy = new ArrayList<>();
        chars_Copy.addAll(chars);

        //Loop until we've gone through the full length of the chars
        for (int i = 0; i < chars.size(); i++) {

            //Get a random number
            int rand = new Random().nextInt(chars_Copy.size());

            //Use the number to pick the character from the copied arraylist
            map.put(chars.get(i), chars_Copy.get(rand));
            //Then remove that char from the copied arrayList
            chars_Copy.remove(rand);
        }

        return map;
    }

    public String encrypt(String input, TreeMap<String, String> map) {

        StringBuilder output = new StringBuilder("");

        for (int i = 0; i < input.length(); i++) {

            output.append(map.get(input.substring(i, i + 1)));

        }

        return output.toString();

    }

    public String decrypt(String input, TreeMap<String, String> map) {

        StringBuilder output = new StringBuilder("");

        for (int i = 0; i < input.length(); i++) {

            String part = input.substring(i, i + 1);

            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                if (value.equals(part)) {
                    output.append(key);
                    break;
                }
            }

        }

        return output.toString();

    }
}
