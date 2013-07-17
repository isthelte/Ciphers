/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ciphers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tharkinist Hallion
 */
public class ColTrans {

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
            Logger.getLogger(ColTrans.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Start carrying out the operation
        ColTrans c = new ColTrans();
        if (op.equals("e")) {
            System.out.println(c.encrypt(input.toString(), key));
        } else {
            System.out.println(c.decrypt(input.toString(), key));
        }

        
        
        //Test
//        String plain = "COLUMNAR TRANSPOSITIONS IS AN ALGORITHM FOR ENCRYPTION.\n THIS IS RIDICULOUS.";
//        int keyy = 20;
//
//        System.out.println("The plaintext is '" + plain + "'");
//        System.out.println("The number of columns is " + keyy);
//        System.out.println("The ciphertext is '" + c.encrypt(plain, keyy) + "'");
//        System.out.println();
//        System.out.println("The ciphertext is '" + c.encrypt(plain, keyy) + "'");
//        System.out.println("The number of columns is " + keyy);
//        System.out.println("The plain is '" + c.decrypt(c.encrypt(plain, keyy), keyy) + "'");

    }

    public String encrypt(String input, int key) {

        //Create an arraylist to store the fragments of the input
        ArrayList<String> parts = new ArrayList<>();

        //Fill the input with " " so that it fits the table
        while (input.length() % key != 0) {
            input += " ";
        }

        //Now start encoding
        //First, divide the input into parts
        for (int i = 0; i < input.length(); i += key) {
            parts.add(input.substring(i, i + key));
        }
        //Then, assemble the parts in columnar order
        StringBuilder output = new StringBuilder("");
        for (int i = 0; i < key; i++) {
            for (String part : parts) {
                output.append(part.substring(i, i + 1));
            }
        }

        return output.toString();

    }

    public String decrypt(String input, int key) {

        String output = encrypt(input, input.length() / key);
        
        return output;

    }
}
