package project; 
import java.util.ArrayList;
import java.util.Scanner;
public class Functions { 
    public static String convert(String expression, String unit) { 
        Unit unitConverter = new Unit(); 
        return unitConverter.convertTo(expression, unit); 
    } 
    
    
	public static String simplify(String expression) { 
	    Unit unit = new Unit(); 
	  
	    String[] parts = expression.split(" "); 
	    double value = Double.parseDouble(parts[0]); 
	    String unitString = parts[1]; 
	  
	    String baseUnits = unit.expandCompound(unitString); 
	  
	    if (baseUnits == null) { 
	        baseUnits = unit.convertToSI(unitString); 
	    } 
	   
	    String simplifiedExpression = value + " " + baseUnits; 
	    return "Simplified expression: " + simplifiedExpression; 
	} 
	
	
    public static boolean checkUnits(String equation) { 
        Unit unitConverter = new Unit(); 
  
        String[] sides = equation.split("="); 
  
        if (sides.length == 2) { 
            String rhs = convToString(unitConverter, sides[0]); 
            String lhs = convToString(unitConverter, sides[1]); 
  
            return unitConverter.findDimension(rhs) == unitConverter.findDimension(lhs); 
        } 
        else {
            return false; 
        } 
    } 
    
    
    private static String convToString(Unit unitConverter, String input) { 
        String[] components = input.split(" "); 
        StringBuilder expanded = new StringBuilder(); 
        
        for (String component : components) { 
            expanded.append(unitConverter.expandCompound(component)).append(" "); 
        } 
  
        return expanded.toString().trim(); 
    } 


    public static String checkUnits(String input) {
	    ArrayList<String> leftUnits = new ArrayList<String>();
	    ArrayList<String> rightUnits = new ArrayList<String>();
	    ArrayList<Double> powerL = new ArrayList<>();
	    ArrayList<Double> powerR = new ArrayList<>();
    	String[] splitString = input.split("=", 2);
    	
    	leftUnits = scanning(leftUnits, splitString[0]);
        rightUnits = scanning(rightUnits, splitString[1]);
 	
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
        	leftUnits.set(i, unit.getUnits(unit.convertToSI("1" + leftUnits.get(i))));
        	for(int j = 0; j < i; j++) {
        		if(leftUnits.get(i).equals(leftUnits.get(j))) {
        			powerL.set(j, powerL.get(j) + powerL.get(i));
        			powerL.set(i, 0.0);
        		}
        	}
        }
        for(int i = 0; i < currentSizeR; i++) {
        	rightUnits.set(i, unit.getUnits(unit.convertToSI("1" + rightUnits.get(i))));
        	for(int j = 0; j < i; j++) {
        		if(rightUnits.get(i).equals(rightUnits.get(j))) {
        			powerR.set(j, powerR.get(j) + powerR.get(i));
        			powerR.set(i, 0.0);
        		}
        	}
        }
        cleanZero(leftUnits, powerL);
        cleanZero(rightUnits, powerR);
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
    
    private static ArrayList<String> scanning(ArrayList<String> units, String in) {
    //connvert string to array list of tokens.
        Scanner scan = new Scanner(in);
        while(scan.hasNext()) {
            units.add(scan.next());
        }
        scan.close();
        return units;
    }
    
    
    private static void cleanZero(ArrayList<String> units, ArrayList<Double> powers) {
        for(int i = units.size() - 1; i >= 0; i--) {
            if(powers.get(i) == 0) {
                powers.remove(i);
                units.remove(i);
            }
            
        }
    }
    
    private static ArrayList<Double> setExp(ArrayList<String> units, double multiplier) {
        ArrayList<Double> power = new ArrayList<>();
        
        String filler = "";
        
        double exponent = 0;

        int divide = 0;
        boolean foundEx;
        boolean numOnly = true;
        
        for(int i = 0; i < units.size(); i++) {//for every unit
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
 
