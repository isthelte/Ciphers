/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package competition;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 *
 * @author Tharkinist Hallion
 */
public class Desperate {

    public static void main(String[] args) throws FileNotFoundException {

        File f = new File("msg3.enc").getAbsoluteFile();
        Scanner s = new Scanner(f);

        StringBuilder sb = new StringBuilder();
        while (s.hasNext()) {
            sb.append(s.nextLine());
            if (s.hasNext()) {
                sb.append("\n");
            }
        }

        String input = sb.toString();
        input = input.replaceAll("<", "").replaceAll(">", "");

        //Ceasar desperate measures --> FAIL
//        Ceasar c = new Ceasar();
//        
//        for (int i = 1; i < 45; i++) {
//            System.out.println("Key number: " + i);
//            System.out.println(c.decrypt(input, i));
//            System.out.println();
//        }

        //ColTrans desperate measures --> FAIL
//        ColTrans ct = new ColTrans();
//        
//        for (int i = 1; i < input.length(); i++) {
//            System.out.println("Key number: " + i);
//            System.out.println(ct.decrypt(input, i));
//            System.out.println();
//        }

        //frequency attack (subt)
        int countOccurence = 0;

        TreeMap<String, Integer> repetitions = new TreeMap<>();

        for (int size = 1; size < 4; size++) {
            for (int i = 0; i < input.length() - size; i++) {

                String toFind = input.substring(i, i + size);

                for (int j = 0; j < input.length() - size; j++) {
                    String toCompare = input.substring(j, j + size);

                    if (toFind.equals(toCompare)) {
                        countOccurence++;
                    }
                }

                if (countOccurence >= 2) {
                    repetitions.put(toFind, countOccurence);
                }

                countOccurence = 0;
            }
        }

        System.out.println("Here is the original message I received: ");
        System.out.println(input);
        System.out.println();

        System.out.println("We start by analysing the frequency of each single character.");
        System.out.println("Here is the statistic:");
        System.out.println("[Character -> Number of Occurence]");

        for (Map.Entry<String, Integer> entry : repetitions.entrySet()) {
            String string = entry.getKey();
            Integer integer = entry.getValue();

            if (string.length() == 1) {
                System.out.println(string + " -> " + integer);
            }
        }

        System.out.println();

        System.out.println("As we can see, The highest occurence is J (15 occurences), and I (12 occurences)");
        System.out.println("There is a fact that you can't write a message without space, "
                + "and according to wikipedia, the most frequent character"
                + " appearing in English text is e.");
        System.out.println("Let's assume that J is ' ', and I is 'e'");
        System.out.println("(Note):Convention: The plaintext is in lowercase, while the plain text is in UPPERCASE");

        System.out.println("\n" + input
                .replaceAll("J", " ")
                .replaceAll("I", "e"));

        System.out.println();
        System.out.println("We can see that the first letter is 'OeWW,' , which looks oddly similar to the word 'hello', because WW"
                + "looks like an 'll' from 'hello'");
        System.out.println("Given that assumption, proceed to replace the following: ");
        System.out.println("\t- O -> h");
        System.out.println("\t- W -> l");
        System.out.println("\t- , -> o");

        System.out.println("\n" + input
                .replaceAll("J", " ")
                .replaceAll("I", "e")
                .replaceAll("O", "h")
                .replaceAll("W", "l")
                .replaceAll(",", "o"));

        System.out.println();

        System.out.println("This may sound like a stupid assumption, but 'hello' may comes before a noun to identify a person");
        System.out.println("Since I am a student, how about trying to key in the word 'students'?");
        System.out.println("Examine the next string: '135Ge 31'....");
        System.out.println("...And it seems that I was correct. '13' and '31' in the string looks like 'st' and 'ts' from 'students'");
        System.out.println("We keep on replacing: ");
        System.out.println("\t- 1 -> s");
        System.out.println("\t- 3 -> t");
        System.out.println("\t- 5 -> u");
        System.out.println("\t- G -> d");

        System.out.println("\n" + input
                .replaceAll("J", " ")
                .replaceAll("I", "e")
                .replaceAll("O", "h")
                .replaceAll("W", "l")
                .replaceAll(",", "o")
                .replaceAll("1", "s")
                .replaceAll("3", "t")
                .replaceAll("5", "u")
                .replaceAll("G", "d"));
        System.out.println();

        System.out.println("Next, look at the string 'thQs'");
        System.out.println("Looks like the word 'this'");
        System.out.println("Proceed:");
        System.out.println("\t - Q -> i");

        System.out.println("\n" + input
                .replaceAll("J", " ")
                .replaceAll("I", "e")
                .replaceAll("O", "h")
                .replaceAll("W", "l")
                .replaceAll(",", "o")
                .replaceAll("1", "s")
                .replaceAll("3", "t")
                .replaceAll("5", "u")
                .replaceAll("G", "d")
                .replaceAll("Q", "i"));
        System.out.println();

        System.out.println("Keep going:");
        System.out.println("+'thi(new line)d' looks like 'third'");
        System.out.println("+' Ce ' looks like ' be '");
        System.out.println("So...");
        System.out.println("\t- C -> b");
        System.out.println("\t- \n -> r");

        System.out.println("\n" + input
                .replaceAll("J", " ")
                .replaceAll("I", "e")
                .replaceAll("O", "h")
                .replaceAll("W", "l")
                .replaceAll(",", "o")
                .replaceAll("1", "s")
                .replaceAll("3", "t")
                .replaceAll("5", "u")
                .replaceAll("G", "d")
                .replaceAll("Q", "i")
                .replaceAll("C", "b")
                .replaceAll("\n", "r"));
        System.out.println();

        System.out.println("now we can see '...this third YessAMe'");
        System.out.println("Seeing that this is msg3, this should means '...this third message'");
        System.out.println("Carry on!");
        System.out.println("\t- Y -> m");
        System.out.println("\t -A -> a");
        System.out.println("\t -L -> g");

        System.out.println("\n" + input
                .replaceAll("J", " ")
                .replaceAll("I", "e")
                .replaceAll("O", "h")
                .replaceAll("W", "l")
                .replaceAll(",", "o")
                .replaceAll("1", "s")
                .replaceAll("3", "t")
                .replaceAll("5", "u")
                .replaceAll("G", "d")
                .replaceAll("Q", "i")
                .replaceAll("C", "b")
                .replaceAll("\n", "r")
                .replaceAll("Y", "m")
                .replaceAll("A", "a")
                .replaceAll("M", "g"));
        System.out.println();

        System.out.println("Now look, 'de(something) this third message");
        System.out.println("Sounds like 'decrypt this third message'");
        System.out.println("Go on:");
        System.out.println("\t- E -> c");
        System.out.println("\t- F -> y");
        System.out.println("\t- ) -> p");

        System.out.println("\n" + input
                .replaceAll("J", " ")
                .replaceAll("I", "e")
                .replaceAll("O", "h")
                .replaceAll("W", "l")
                .replaceAll(",", "o")
                .replaceAll("1", "s")
                .replaceAll("3", "t")
                .replaceAll("5", "u")
                .replaceAll("G", "d")
                .replaceAll("Q", "i")
                .replaceAll("C", "b")
                .replaceAll("\n", "r")
                .replaceAll("Y", "m")
                .replaceAll("A", "a")
                .replaceAll("M", "g")
                .replaceAll("E", "c")
                .replaceAll("F", "y")
                .replaceAll("\\)", "p"));

        System.out.println("Now look back up, we see [hello students... try'");
        System.out.println("This seems legit: ");
        System.out.println("hello students,");
        System.out.println("try ...");
        System.out.println("Keep going: ");
        System.out.println("\t- N -> ,");
        System.out.println("\t- Z -> (new line)");

        System.out.println("\n" + input
                .replaceAll("J", " ")
                .replaceAll("I", "e")
                .replaceAll("O", "h")
                .replaceAll("W", "l")
                .replaceAll(",", "o")
                .replaceAll("1", "s")
                .replaceAll("3", "t")
                .replaceAll("5", "u")
                .replaceAll("G", "d")
                .replaceAll("Q", "i")
                .replaceAll("C", "b")
                .replaceAll("\n", "r")
                .replaceAll("Y", "m")
                .replaceAll("A", "a")
                .replaceAll("M", "g")
                .replaceAll("E", "c")
                .replaceAll("F", "y")
                .replaceAll("\\)", "p")
                .replaceAll("N", ",")
                .replaceAll("Z", "\n"));
        System.out.println();

        System.out.println("..And the plain text comes about:");
        System.out.println("\t- L -> .");
        System.out.println("\t- - -> 2");
        System.out.println("\t- (space) -> n");
        System.out.println("\nhello students,\n"
                + "try to decrypt this third message. it should be easier to decrypt tha  message 2.\n"
                + "\n"
                + "denis");


//                .replaceAll("L", ".")
//                .replaceAll("-", "2"));

    }
}
