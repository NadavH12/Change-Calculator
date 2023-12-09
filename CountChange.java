/*

Nadav Horowitz CS 210 2/20/2022

This program takes input from a text file in the form of names of coins and their integer values,
the program sums and reports the total value for each line in the file, the total value among all lines,
and the average value per line. The program outputs it's report into a text file.

*/

import java.io.*;
import java.util.*;
public class CountChange {
    //Main method, constructs scanner object to read from the console, then prompts user for a name of an input file
    //If the user provided input file is not in the same directory as the program, the method will throw a 
    //FileNotFoundException
    //If the correct input file is found, the method constructs another Scanner object, this time to read from the file
    //The program then constructs a PrintStream object and also a File object, for the ability to output its report into
    //a text file in the same directory of the program
    //The program then calls countChange, passing the Scanner that reads the input file, and the PrintStream that
    //prints to the output file
    public static void main(String[] args)
            throws FileNotFoundException{
        Scanner console = new Scanner(System.in);
        System.out.println("Please enter the name of the input file (include \".txt\" suffix) ");
        String fileName = console.nextLine();
        Scanner input = new Scanner(new File(fileName));
        int nameLength = fileName.length();
        fileName = fileName.substring(0,nameLength-4);
        PrintStream output = new PrintStream(new File(fileName+"-out.txt"));
        countChange(input,output);
    }

    //countChange method keeps track of the number of lines in the input file and also the running total
    //of the value of all of the coins in all lines
    //The method uses a while loop to traverse each line in the input file individually
    //The method creates a String of each line in the file, and passes that String to the method processLine
    //which returns the value of the coins in that line
    //The method then prints the line number and the value of the coins in that line to the output file
    //Once the while loop has traversed the whole file, the method prints the total value, and the average
    //value per line to the output file
    public static void countChange(Scanner input, PrintStream output){
        int counter = 0;
        double total = 0.0;
        while(input.hasNext()){
            counter++;
            String line = input.nextLine();
            double dollarValue = processLine(line);
            total = total + dollarValue;
            output.printf("Line " + counter + " : $%.2f\n", dollarValue);
        }
        output.printf("Total: $%.2f\n", total);
        double dCounter = (double) counter;
        double average = (total/dCounter);
        output.printf("Average Change: $%.2f\n", average);
    }

    //processLine method accepts a String of a line in the input file as a parameter, and traverses it keeping track of the
    //value of the valid coin tokens in that line. Valid coin tokens are integers 1,5,10,25, or the names
    //of the individual coins case insensitivly. Valid tokens also include coin names or coin values with punctuation marks 
    //added to their ends.
    //The method takes the inputed line and converts it to all lowercase to be case insensitive
    //The method then constructs an additional Scanner object to read tokens from the line
    //The method then calls helper method punctuationMarkRemover passing it the now lowercase String
    //The String returns with all punctuaton marks at the ends removed
    //The method then adds the value of the token to the total if it is a valid token, or does not if it is invalid
    //After processing all tokens, the method adds up the total value of the inputted line and returns it to the calling function
    public static double processLine(String line){
        double totalValue = 0.0;
        line = line.toLowerCase();
        Scanner lineScan = new Scanner(line);
        while(lineScan.hasNext()){
             String token = lineScan.next();              
             token=punctuationMarkRemover(token);           
             if (token.equals("penny") || token.equals("1")) {
                  totalValue = totalValue + 0.01;
             }
             if (token.equals("nickel") || token.equals("5")) {
                  totalValue = totalValue + 0.05;
             }
             if (token.equals("dime") || token.equals("10")) {
                  totalValue = totalValue + 0.1;
             }                 
             if (token.equals("quarter") || token.equals("25")) {
                  totalValue = totalValue + 0.25;
             }
        }
        return totalValue;
    }
    
    //Passed a String from processLine
    //Checks to see if the last character of the String is a punctuation mark, if it is, it removes it
    //Method continues until the last character of the token is not a punctuation mark or if the length of the token is 1
    //then returns the now formatted String
    public static String punctuationMarkRemover(String token){
         int tokenLength=token.length();
         while(tokenLength>1 && (token.endsWith(",")||token.endsWith("!")||token.endsWith("?"))){
                  token=token.substring(0,tokenLength-1);
                  tokenLength=token.length();
         }
         return token;
    }
}