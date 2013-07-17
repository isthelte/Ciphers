/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ciphers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tharkinist Hallion
 */
public class Ceasar {

    private ArrayList<String> chars = new ArrayList<>();

    public Ceasar() {
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
        int key = Integer.parseInt(args[2]);

        //Locate the file accordingly
        File f = new File(inputFile);
        File fullFile = f.getAbsoluteFile();

        //Read the input file for the string
        StringBuilder input = new StringBuilder();
        Scanner s;
        try {
            s = new Scanner(fullFile);
            
            while (s.hasNext()) {
                input.append(s.nextLine());
                if (s.hasNext()) {
                    input.append("\n");
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Ceasar.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Start carrying out the operation
        Ceasar c = new Ceasar();
        if (op.equals("e")) {
            System.out.println(c.encrypt(input.toString(), key));
        } else {
            System.out.println(c.decrypt(input.toString(), key));
        }

        //Test
//        String plain = "ISTHELTE WINDSOR";
//        String cypher = "MWXLIPXI) MRHWSV";
//        System.out.println("Encoding text '" + plain + "'");
//        System.out.println("The key to shift is: " + key);
//        System.out.println("The cyphertext is: " + c.encrypt(plain, key) + "'");
//        
//        System.out.println("Decoding text '" + cypher + "'");
//        System.out.println("The key to shift is: " + key);
//        System.out.println("The plaintext is: " + c.decrypt(cypher, key) + "'");
    }

    public String encrypt(String input, int key) {

        //Shift the chars according to the key
        //Split the 2 parts
        List<String> part1 = chars.subList(0, key);
        List<String> part2 = chars.subList(key, chars.size() - 1);
        //Now merge them
        ArrayList<String> shifted = new ArrayList<>();
        shifted.addAll(part2);
        shifted.addAll(part1);

        //Create a mapping
        TreeMap<String, String> encMap = new TreeMap<>();
        //Fill the map
        for (int i = 0; i < shifted.size(); i++) {
            encMap.put(chars.get(i), shifted.get(i));
//            System.out.println(chars.get(i) + " - " + shifted.get(i));
        }

        //Start converting the string
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < input.length(); i++) {
            sb.append(encMap.get(input.substring(i, i + 1)));
        }

        //return the result
        return sb.toString();

    }

    public String decrypt(String input, int key) {

        //Shift the chars according to the key
        //Split the 2 parts
        List<String> part1 = chars.subList(chars.size() - key, chars.size());
        List<String> part2 = chars.subList(0, chars.size() - key);
        //Now merge them
        ArrayList<String> shifted = new ArrayList<>();
        shifted.addAll(part1);
        shifted.addAll(part2);

        //Create a mapping
        TreeMap<String, String> encMap = new TreeMap<>();
        //Fill the map
        for (int i = 0; i < shifted.size(); i++) {
            encMap.put(chars.get(i), shifted.get(i));
//            System.out.println(chars.get(i) + " - " + shifted.get(i));
        }

        //Start converting the string
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < input.length(); i++) {
            sb.append(encMap.get(input.substring(i, i + 1)));
        }

        //return the result
        return sb.toString();

    }
}
