package project;

import java.lang.Math;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Unit {
  private String unitList[][] = {{"m", "in", "ft", "yd", "mi"}, //Length: meter, inch, foot, yard, mile
                          {"kg", "lb", "oz", "u"}, //Mass: kg, pound, ounce, dalton
                          {"s", "min", "h"}, //Time: second, minute, hour
                          {"A"}, //Electric Current: ampere
                          {"K", "F", "C"}, //Temperature: Kelvin, Fahrenheit, Celsius
                          {"mol"}, //Amount of Substance: mole
                          {"cd"}}; //Luminous Intensity: candela
                         
  
  //map where key = prefix & value = exponent to base 10 to multiply by
  private HashMap<String, Integer> prefix = new HashMap<>();
  //map where key = unit & value = String which contains formula to get SI,
  //giving functions and values delimited by commas
  private HashMap<String, String> formulaToSI = new HashMap<>();
  private HashMap<String, String> formulaFromSI = new HashMap<>();
  //map giving the equivalent units from a compound unit
  private HashMap<String, String> compound = new HashMap<>();
  
  public Unit() {
      //prefixes
      prefixAdder("a", -18);
      prefixAdder("f", -15);
      prefixAdder("p", -12);
      prefixAdder("n", -9);
      prefixAdder("mu", -6);
      prefixAdder("m", -3);
      prefixAdder("c", -2);
      prefixAdder("d", -1);
      prefixAdder("", 0);
      prefixAdder("da", 1);
      prefixAdder("h", 2);
      prefixAdder("k", 3);
      prefixAdder("M", 6);
      prefixAdder("G", 9);
      prefixAdder("T", 12);
      prefixAdder("P", 15);
      prefixAdder("E", 18);
      
      //length
      formulaAdder(0, "in", "/,39.3700787402");
      formulaAdder(1, "in", "*,39.3700787402");
      
      formulaAdder(0, "ft", "/,3.280839895");
      formulaAdder(1, "ft", "*,3.280839895");
      
      formulaAdder(0, "yd", "/,1.0936132983");
      formulaAdder(1, "yd", "*,1.0936132983");
      
      formulaAdder(0, "mi", "*,1609.344");
      formulaAdder(1, "mi", "/,1609.344");

      //mass
      formulaAdder(0, "lb", "/,2.2046226218");
      formulaAdder(1, "lb", "*,2.2046226218");
      
      formulaAdder(0, "oz", "/,35.2739619496");
      formulaAdder(1, "oz", "*,35.2739619496");
      
      formulaAdder(0, "u", "*,1.66053906660,^,-27");
      formulaAdder(1, "u", "*,6.0221366516752,^,26");
      
      
      //time
      formulaAdder(0, "min", "*,60");
      formulaAdder(1, "min", "/,60");
      
      formulaAdder(0, "hr", "*,3600");
      formulaAdder(1, "hr", "/,3600");
      
      //temperature
      formulaAdder(0, "F", "-,32,*,5,/,9,+,273.15");
      formulaAdder(1, "F", "-,273.15,*,9,/,5,+,32");
      
      formulaAdder(0, "C", "+,273.15");
      formulaAdder(1, "C", "-,273.15");
      
      //compunds
      compoundAdder("N", "kg m s^-2");
      compoundAdder("J", "kg m^2 s^-2");
      compoundAdder("C", "A s");
      compoundAdder("W", "kg m^2s^-3");
      compoundAdder("Pa", "kg m^-1 s^-2");
  }
  
  //note: will override previously declared prefixes
  public void prefixAdder(String key, int exponent) {
      prefix.put(key, exponent);
  }
  public void compoundAdder(String key, String newUnits) {
      compound.put(key, newUnits);
  }
  //if direction = 0, converts to SI, if direction = 1, converts from SI
  public void formulaAdder(int direction, String key, String formula) {
      if(direction == 0) {
          formulaToSI.put(key, formula);
      }
      else if(direction == 1) {
          formulaFromSI.put(key, formula);
      }
  }
  
  public String expandCompound(String input) {
      return compound.get(input);
  }
  
  public String convertToSI(String input) {
      //spliting up unit & value
      double value = getValue(input);
      String unit = getUnits(input);
      
      int dimension = findDimension(unit);
      boolean isPrefixed = false;
      String pre = "" + unit.charAt(0);
      if(dimension == -1) {
          //check for prefixes
          String buffer = "";
          for(int i = 1; i < unit.length(); i++) {
              buffer = buffer + unit.charAt(i);
          }
          unit = buffer;
          dimension = findDimension(unit);
          //if not a one letter prefix, check if it is da
          if(dimension == -1) {
              buffer = "";
              for(int i = 1; i < unit.length(); i++) {
                  buffer = buffer + unit.charAt(i);
              }
              unit = buffer;
              dimension = findDimension(unit);
              //if not prefix then you then it is not a valid unit, return ! (it wouldn't let me return NULL)
              if(dimension == -1) {
                  return "!";
              }
              pre = "da";
          }
          //if prefix then send to prefix converter
          isPrefixed = true;
      }
      
      String SI = unitList[dimension][0];
      
      if(isPrefixed) {
          if(unit.equals("kg")) {
              value = convertPrefixes(value, pre, "k");
          }
          else {
              value = convertPrefixes(value, pre, "");
          }
      }
      
      //already SI unit
      if(SI.equals(unit)) {
          return value + unit;
      }
      
      String instructions = formulaToSI.get(unit);
      value = computeInstructions(instructions, value);
      
      String returnString = (value + SI);
      
      return returnString;
  }
  
  private double computeInstructions(String instructions, double value) {
      //get instructions for conversion and compute them
      Scanner scan = new Scanner(instructions);
      scan.useDelimiter(",");
      String instruct;
      
      while(scan.hasNext()) {
          instruct = scan.next();
          if(instruct.equals("*")) {
              instruct = scan.next();
              value = value * Double.parseDouble(instruct);
          }
          else if(instruct.equals("/")) {
              instruct = scan.next();
              value = value / Double.parseDouble(instruct);
          }
          else if(instruct.equals("+")) {
              instruct = scan.next();
              value = value + Double.parseDouble(instruct);
          }
          else if(instruct.equals("-")) {
              instruct = scan.next();
              value = value - Double.parseDouble(instruct);
          }
          else if(instruct.equals("^")) {
              instruct = scan.next();
              value = value * Math.pow(10, Double.parseDouble(instruct));
          }
      }
      scan.close();
      return value;
  }
  
  public int findDimension(String unit) {
      int dimension = -1;
      boolean found = false;
      for(int dim = 0; dim < unitList.length && found == false; dim++) {
          for(int type = 0; type < unitList[dim].length && found == false; type++) {
              if(unitList[dim][type].equals(unit)) {
                  dimension = dim;
                  found = true;
              }
          }
      }
      return dimension;
  }
  
  private double getValue(String input) {
      String value = "";
      for(int i = 0; i < input.length(); i++) {
          if(!Character.isAlphabetic(input.charAt(i))) {
              value = value + input.charAt(i);
          }
          else if(input.charAt(i) == ('E') && !Character.isAlphabetic(input.charAt(i + 1))) {
              value = value + input.charAt(i);
          }
      }
      double toReturn = Double.parseDouble(value);
      return toReturn;
  }
  
  public String getUnits(String input) {
      String toReturn = "";
      for(int i = 0; i < input.length(); i++) {
          if(Character.isAlphabetic(input.charAt(i))) {
              toReturn = toReturn + input.charAt(i);
          }
          else{
              toReturn = "";
          }
      }
      return toReturn;
  } 
  
  //converts value changed by changing prefixes
  private double convertPrefixes(double value, String currentPrefix, String newPrefix) {
      double currentExponent = prefix.get(currentPrefix);
      double newExponent = prefix.get(newPrefix);
      double difference = currentExponent - newExponent;
      double newValue = value * Math.pow(10, difference);
      return newValue;
  }
  
  
  
  public String convertTo(String input, String newUnit) {
      //for current unit
      String inputSI = convertToSI(input);
      if(inputSI.equals("!")) {
          return "!";
      }
      //spliting up unit & value
      double value = getValue(inputSI);
      String unit = getUnits(inputSI);
      int dimensionA = findDimension(unit);
      
      //For new unit
      int dimension = findDimension(newUnit);
      boolean isPrefixed = false;
      String pre = "" + newUnit.charAt(0);
      if(dimension == -1) {
          //check for prefixes
          String buffer = "";
          for(int i = 1; i < newUnit.length(); i++) {
              buffer = buffer + newUnit.charAt(i);
          }
          newUnit = buffer;
          dimension = findDimension(newUnit);
          //if not a one letter prefix, check if it is da
          if(dimension == -1) {
              buffer = "";
              for(int i = 1; i < newUnit.length(); i++) {
                  buffer = buffer + newUnit.charAt(i);
              }
              newUnit = buffer;
              dimension = findDimension(newUnit);
              //if not prefix then you then it is not a valid unit, return ! (it wouldn't let me return NULL)
              if(dimension == -1) {
                  return "!";
              }
              pre = "da";
          }
          //if prefix then send to prefix converter
          isPrefixed = true;
      }
      //check for same dimension
      if(dimensionA != dimension) {
          return "!";
      }
      if(!newUnit.equals(unit)) {
          String instructions = formulaFromSI.get(newUnit);
          value = computeInstructions(instructions, value);
      }
      
      if(isPrefixed) {
          value = convertPrefixes(value, "", pre);
          return value + pre + newUnit;
      }
      
      return value + newUnit;
  }
  
  /* Input: 
   * units (arraylist of just string units)
   * 	Example input: ["km", "km^-3", "/g", "/L^3"]
   * 
   * Return: 
   * return: arraylist of exponents
   * 	Example return: [1, -3, -1, -3]
   * changes: simplifies units to only have units remaining
   * 	Example change: ["km", "km", "g", "L"]
   */
  public ArrayList<Double> setExp(ArrayList<String> units) {
	  ArrayList<Double> exponents = new ArrayList<Double>();
	  for (int i = 0; i < units.size(); i++) {//for every unit
		  String[] unitAndEx = units.get(i).split("^", 2);
		  if (unitAndEx.length == 2) {
			  exponents.set(i, Double.parseDouble(unitAndEx[1]));
		  }
		  else {
			  exponents.set(i, 1.0);
		  }
		  units.set(i, unitAndEx[0]);
		  
		  if(units.get(i).charAt(0) == '/') {
			  units.set(i, units.get(i).substring(1));
			  exponents.set(i, exponents.get(i) * -1);
          }
	  }
	  return exponents;
  }
}
