package project; 
import java.util.ArrayList;
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
} 
 
