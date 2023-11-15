package project;
import java.util.*;
import java.io.*;

public class Driver{



    public static void main(String args[]){
        //An instance of the Constance class
        Constant newConstant = new Constant();
       
       
       
        /*  trying to read an input file from the command line which contains constants defined by the user.
            Then add it to the Constants hash map */
        try {
        
        File file = new File(args[1]);
        Scanner myReader = new Scanner(file);

        //Looping through the file
            while (myReader.hasNextLine()) {

                String line = myReader.nextLine();
                //Storing each constant symbol and their corresponding value in each line, in the String array.
                String[] newConstants = line.split(" ");
                //Checking if the contents in the input file were valid
                if(newConstants[0].equals("CONST") && newConstants.length==3){

                    String symbol = newConstants[1];
                    String value  = newConstants[2];
                    //Calling the funtion in the Constant class.
                    newConstant.addConstants(symbol, value);
                }else{
                    System.out.println("Invalid Line.");
                }
            }
            myReader.close();
        } 
        
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        finally{
            // stores the function or symbol(S,U,C,Ex) the user wants to use.
            String UserInput;
            //The physics equation that the user wants to convert,simplify or check.
            String equation;

            Scanner scan = new Scanner(System.in);

            //Unless the user types "Ex" or exit, we want the program to run without terminating after each use.
            while(!(UserInput.equals("Ex"))){
                System.out.print("Program: Select (S)implify, Check (U)nits, (C)onvert Units, (Ex)it\nUser: ");
                UserInput = scan.nextLine();
                equation = scan.nextLine();

                if(UserInput== "S"){
                    System.out.println("Program: "+simplify(equation));
                }

                if(UserInput== "U"){
                    System.out.println("Program: "+checkUnits(equation)); 
                }

                if(UserInput== "C"){
                    System.out.println("Program: "+convert(equation));
                }

                if(UserInput== "Ex"){
                    System.out.println("Bye!");
                    break;
                }
            }
        }
    }






}


