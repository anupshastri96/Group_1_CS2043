package project;

import java.util.Scanner;
import java.util.ArrayList;

public class CheckUnits {
    private ArrayList<String> leftUnits = new ArrayList<String>();
    private ArrayList<String> rightUnits = new ArrayList<String>();
    ArrayList<Double> powerL = new ArrayList<>();
    ArrayList<Double> powerR = new ArrayList<>();
    Scanner scan;

    String checkUnits(String input) {
        String left = "";
        String right = "";
        boolean equalsFound = false;
        
        for(int i = 0; i < input.length(); i++) {
            if(equalsFound == false && input.charAt(i) == '=') {
                equalsFound = true;
            }
            else if(equalsFound == false) {
                left = left + input.charAt(i);
            }
            else {
                right = right + input.charAt(i);
            }
        }
        
        leftUnits = scanning(leftUnits, left);
        rightUnits = scanning(rightUnits, right);
        
        powerL = setExp(leftUnits, 1);
        powerR = setExp(rightUnits, 1);
        
        cleanZero(leftUnits, powerL);
        cleanZero(rightUnits, powerR);

        Unit unit = new Unit();
        Constant constant = new Constant();
        String filler = "";
        int currentSize = leftUnits.size();
        for(int i = 0; i < currentSize; i++) {
            filler = "";
            filler = constant.convertConstant(leftUnits.get(i));
            if(filler == null) {
                filler = "";
                filler = unit.expandCompound(leftUnits.get(i));
                if(filler == null) {
                    filler = "";
                    leftUnits.set(i, 0 + leftUnits.get(i));
                    filler = unit.convertToSI(leftUnits.get(i));
                    leftUnits.set(i, filler);
                }
                else {
                    leftUnits = scanning(leftUnits, filler);
                    powerL = setExp(leftUnits, powerL.get(i));
                    cleanZero(leftUnits, powerL);
                    leftUnits.remove(i);
                    powerL.remove(i);
                }
            }
            else {
                leftUnits = scanning(leftUnits, filler);
                powerL = setExp(leftUnits, powerL.get(i));
                cleanZero(leftUnits, powerL);
                leftUnits.remove(i);
                powerL.remove(i);
            }
            filler = "";
            for(int j = 0; j < leftUnits.get(i).length(); j++) {
                if(!Character.isDigit(leftUnits.get(i).charAt(j)) && leftUnits.get(i).charAt(j) != '.') {
                    filler = filler + leftUnits.get(i).charAt(j);
                }
            }
            leftUnits.set(i, filler);
        }
        
        
        
        int currentSizeR = rightUnits.size();
        for(int i = 0; i < currentSizeR; i++) {
            filler = "";
            filler = constant.convertConstant(rightUnits.get(i));
            if(filler == null) {
                filler = "";
                filler = unit.expandCompound(rightUnits.get(i));
                if(filler == null) {
                    filler = "";
                    rightUnits.set(i, 0 + rightUnits.get(i));
                    filler = unit.convertToSI(rightUnits.get(i));
                }
                else {
                    rightUnits = scanning(rightUnits, filler);
                    powerR = setExp(rightUnits, powerR.get(i));
                    cleanZero(rightUnits, powerR);
                    rightUnits.remove(i);
                    powerR.remove(i);
                }
            }
            else {
                rightUnits = scanning(rightUnits, filler);
                powerR = setExp(rightUnits, powerR.get(i));
                cleanZero(rightUnits, powerR);
                rightUnits.remove(i);
                powerR.remove(i);
            }
            filler = "";
            for(int j = 0; j < rightUnits.get(i).length(); j++) {
                if(!Character.isDigit(rightUnits.get(i).charAt(j)) && rightUnits.get(i).charAt(j) != '.') {
                    filler = filler + rightUnits.get(i).charAt(j);
                }
            }
            rightUnits.set(i, filler);
            
        }
        currentSize = leftUnits.size();
        currentSizeR = rightUnits.size();
        for(int i = 0; i < currentSize; i++) {
            for(int j = 0; j < currentSizeR; j++) {
                if(leftUnits.get(i).equals(rightUnits.get(j))) {
                   powerR.set(j, powerR.get(j) - powerL.get(i));
                   powerL.set(i, 0.0);
                }
            }
        }
        cleanZero(leftUnits, powerL);
        cleanZero(rightUnits, powerR);
        
        
        

        String returnMessage = "";
        if(leftUnits.size() == 0 && rightUnits.size() == 0) {
            returnMessage = "The units match.";
        }
        else {
            returnMessage = "The units do not match, here are the units that do not match (corrected to SI base)";
            returnMessage += "\nLeft Side Units Remaining: ";
            for(int i = 0; i < leftUnits.size(); i++) {
                returnMessage += leftUnits.get(i) + "^" + powerL.get(i) + " ";
            }
            returnMessage += "\nRight Side Units Remaining: ";
            for(int i = 0; i < rightUnits.size(); i++) {
                returnMessage += rightUnits.get(i) + "^" + powerR.get(i) + " ";
            }
        }
        
        
        
        return returnMessage;
    }
    
    private ArrayList<String> scanning(ArrayList<String> units, String in) {
        scan = new Scanner(in);
        while(scan.hasNext()) {
            units.add(scan.next());
        }
        scan.close();
        return units;
    }
    
    
    private void cleanZero(ArrayList<String> units, ArrayList<Double> powers) {
        for(int i = units.size() - 1; i >= 0; i--) {
            if(powers.get(i) == 0) {
                powers.remove(i);
                units.remove(i);
            }
            
        }
    }
    
    private ArrayList<Double> setExp(ArrayList<String> units, double multiplier) {
        ArrayList<Double> power = new ArrayList<>();
        
        String filler = "";
        
        double exponent = 0;

        int divide = 0;
        boolean foundEx;
        boolean numOnly = true;
        
        for(int i = 0; i < units.size(); i++) {
            foundEx = false;
            filler = "";
            exponent = 0;
            numOnly = true;
            for(int j = 0; j < units.get(i).length(); j++) {
                if(foundEx == false && units.get(i).charAt(j) == '^') {
                    foundEx = true;  
                }
                else if(foundEx == true) {
                    filler = filler + units.get(i).charAt(j);
                }
                if(units.get(i).charAt(j) == '/') {
                    divide = 1;
                    break;
                }
                if(!Character.isDigit(units.get(i).charAt(j)) && units.get(i).charAt(j) != '.') {
                    numOnly = false;
                }
            }
            if(foundEx == true) {
                exponent = Double.parseDouble(filler);
                filler = "";
                for(int k = 0; k < units.get(i).length(); k++) {
                    if(units.get(i).charAt(k) == '^') {
                        break;
                    }
                    filler = filler + units.get(i).charAt(k);
                }
                units.set(i, filler);
            }
            else {
                exponent = 1;
            }
            if(divide == 2) {
                exponent = 0 - exponent;
                divide = 0;
            }
            else if(divide == 1) {
                divide = 2;
                exponent = 0;
            }
            if(numOnly == true) {
                exponent = 0;
            }
            exponent = exponent * multiplier;
            power.add(exponent);
            for(int l = i-2; l >= 0; l--) {
                if(units.get(l).equals(units.get(i))) {
                    power.set(l, power.get(l) + power.get(i));
                    power.set(i, 0.0);
                    break;
                }
            }
        }
        return power;
    }
}