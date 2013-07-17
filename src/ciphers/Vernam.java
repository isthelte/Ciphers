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
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tharkinist Hallion
 */
public class Vernam {

    //Get the aphabets
    private String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ .,()-!?\n0123456789";
    //Use value to look up char -> ArrayList
    private ArrayList<String> chars = new ArrayList<>();
    //Use char to look up value -> TreeMap
    private TreeMap<String, Integer> charToValue = new TreeMap<>();

    public Vernam() {

        for (int i = 0; i < alphabets.length(); i++) {
            chars.add(alphabets.substring(i, i + 1));
        }

        for (int i = 0; i < chars.size(); i++) {
            charToValue.put(chars.get(i), i);
        }
    }

    public static void main(String[] args) {

        //Analize the arguments
        String op = args[0];
        String inputFileName = args[1];
        String keyFileName = args[2];
        int n = Integer.parseInt(args[3]);

        Vernam v = new Vernam();

        //Start carrying out the operation
        if (op.equals("g")) {
            //Generate the map
            String map = v.generate(n);

            //write the map to the keyFile, ignoring the inputFile
            try {
                File fKey = new File(keyFileName);
                File fullFileKey = fKey.getAbsoluteFile();

                PrintWriter pw = new PrintWriter(new FileWriter(fullFileKey), false);
                pw.write(map);
                pw.close();
            } catch (IOException ex) {
                Logger.getLogger(Vernam.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (op.equals("e") || op.equals("d")) {
            try {
                //Locate the file accordingly
                File fInput = new File(inputFileName);
                File fullFileInput = fInput.getAbsoluteFile();
                File fKey = new File(keyFileName);
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
                    Logger.getLogger(Vernam.class.getName()).log(Level.SEVERE, null, ex);
                }

                //Read the key file for the key
                String key = v.getKey(n, input.length(), fullFileKey);

                //See if we should encrypt, or decrypt
                if (op.equals("e")) {
                    System.out.println(v.encrypt(input.toString(), key));
                } else {
                    System.out.println(v.decrypt(input.toString(), key));
                }
                
            //=====================================================================================================
            //        //Test: Printing out what's in the treemap
            //        for (Map.Entry<String, Integer> en : v.charValue.entrySet()) {
            //            String character = en.getKey();
            //            int value = en.getValue().intValue();
            //
            //            System.out.println(character + " - " + value);
            //
            //        }
            //Test reading file
//                String plain = "ISTHELTE";
//                String keyy = "L553KLM9";
//
//                System.out.println("Encoding text '" + plain + "'");
//                System.out.println("The key to shift is: " + keyy);
//                System.out.println("The cyphertext is: " + v.encrypt(plain, keyy) + "'");
//
//                System.out.println("\nCypher text '" + v.encrypt(plain, keyy) + "'");
//                System.out.println("The key to shift is: " + keyy);
//                System.out.println("The plaintext is: " + v.decrypt(v.encrypt(plain, keyy), keyy) + "'");
            
            } catch (FileNotFoundException ex) {
                System.out.println("The file cannot be found");
            } catch (StringIndexOutOfBoundsException ex) {
                System.out.println("Cannot get the key with the same length as the input (That is, we ran out of key).");
            }
        }


//        //=====================================================================================================
//        //Test: Printing out what's in the treemap
//        for (Map.Entry<String, Integer> en : v.charToValue.entrySet()) {
//            String character = en.getKey();
//            int value = en.getValue().intValue();
//            
//            System.out.println(character + " - " + value);
//        }
//        System.out.println("====================");
//        for (int i = 0; i < v.chars.size(); i++) {
//            System.out.println(v.chars.get(i) + " - " + i);
//        }

//        //Test reading file
//
//        String plain = "ISTHELTE";
//        String key = "IRIENNE.";
//        
//        System.out.println("Encoding text '" + plain + "'");
//        System.out.println("The key to shift is: " + key);
//        System.out.println("The cyphertext is: " + v.encrypt(plain, key) + "'");
//        
//        System.out.println("\nCypher text '" + v.encrypt(plain, key) + "'");
//        System.out.println("The key to shift is: " + key);
//        System.out.println("The plaintext is: " + v.decrypt(v.encrypt(plain, key), key) + "'");

    }

    public String encrypt(String input, String key) {

        ArrayList<Integer> inputValues = new ArrayList<>();
        ArrayList<Integer> keyValues = new ArrayList<>();
        ArrayList<Integer> resultValues = new ArrayList<>();

        //Look up the value of each character in the input, and store them 
        for (int i = 0; i < input.length(); i++) {
            inputValues.add(charToValue.get(input.substring(i, i + 1)));
        }

        //Look up the value of each character in the key, and store them
        for (int i = 0; i < input.length(); i++) {
            keyValues.add(charToValue.get(key.substring(i, i + 1)));
        }

        //Compute the result and store it in the resultValues
        for (int i = 0; i < input.length(); i++) {
            int inputValue = inputValues.get(i).intValue();
            int keyValue = keyValues.get(i).intValue();

            resultValues.add((inputValue + keyValue) % chars.size());
        }

        //Now use all the values to look up the according String
        //And build the resulting String
        StringBuilder output = new StringBuilder();
        for (Integer integer : resultValues) {
            String character = chars.get(integer);

            output.append(character);
        }

        return output.toString();
    }

    public String decrypt(String input, String key) {

        ArrayList<Integer> inputValues = new ArrayList<>();
        ArrayList<Integer> keyValues = new ArrayList<>();
        ArrayList<Integer> resultValues = new ArrayList<>();

        //Look up the value of each character in the input, and store them 
        for (int i = 0; i < input.length(); i++) {
            inputValues.add(charToValue.get(input.substring(i, i + 1)));
        }

        //Look up the value of each character in the key, and store them
        for (int i = 0; i < input.length(); i++) {
            keyValues.add(charToValue.get(key.substring(i, i + 1)));
        }

        //Compute the result and store it in the resultValues
        for (int i = 0; i < input.length(); i++) {
            int inputValue = inputValues.get(i).intValue();
            int keyValue = keyValues.get(i).intValue();

            resultValues.add(((inputValue - keyValue) + (chars.size()*2)) % chars.size());
        }

        //Now use all the values to look up the according String
        //And build the resulting String
        StringBuilder output = new StringBuilder();
        for (Integer integer : resultValues) {
            String character = chars.get(integer);

            output.append(character);
        }

        return output.toString();

    }

    public String generate(int length) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(alphabets.charAt(new Random().nextInt(alphabets.length())));
        }

        return sb.toString();
    }

    //The NosuchElementException is thrown when the key length exceeds the String available in the file
    public String getKey(int startFrom, int length, File keyFile) throws FileNotFoundException, StringIndexOutOfBoundsException {

        StringBuilder sb = new StringBuilder();

        Scanner s = new Scanner(keyFile);

        //Take all the chars to the string builder
        while (s.hasNext()) {
            sb.append(s.nextLine());
            if (s.hasNext()) {
                sb.append("\n");
            }
        }

        String fullKey = sb.toString();

        return fullKey.substring(startFrom, startFrom + length);
    }
}
