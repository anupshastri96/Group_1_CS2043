package project; 
import org.junit.jupiter.api.Test; 
import static org.junit.jupiter.api.Assertions.*; 
  
class UnitTest { 
    // defined to ignore small differences in calculations
    private static final double DIFF = 1e-10; 
  
    @Test 
    void testConvertToSI() { 
        assertEquals(2.26796185, Unit.convertToSI("5 lb"), DIFF); 
        assertEquals(0.45359237, Unit.convertToSI("1 lb"), DIFF); 
        //for invalid units, since this deals with doubles and "!" is not a number I used Double.NaN
        assertEquals(Double.NaN, Unit.convertToSI("5 apples")); 
        assertEquals(Double.NaN, Unit.convertToSI("2 kg apples")); 
        assertEquals(1000.0, Unit.convertToSI("1000 g"), DIFF); 
        assertEquals(0.001, Unit.convertToSI("1 g"), DIFF); 
        assertEquals(1000000.0, Unit.convertToSI("1 Mg"), DIFF); 
    } 
  
    @Test 
    void testConvertToSIWithPrefix() { 
        assertEquals(1000.0, Unit.convertToSI("1000 daW"), DIFF); 
        assertEquals(0.001, Unit.convertToSI("1 mW"), DIFF); 
        assertEquals(1000.0, Unit.convertToSI("1 Mg"), DIFF); 
        assertEquals(0.001, Unit.convertToSI("1 g"), DIFF); 
        assertEquals(1000.0, Unit.convertToSI("1 kg"), DIFF); 
    } 
  
    @Test 
    void testConvertToSIInvalidUnit() { 
        assertEquals(Double.NaN, Unit.convertToSI("5 apples")); 
        assertEquals(Double.NaN, Unit.convertToSI("2 kg apples")); 
        assertEquals(Double.NaN, Unit.convertToSI("5 bananas")); 
    } 
  
    @Test 
    void testConvertTo() { 
        assertEquals(0.01, Unit.convertTo("10 m", "km"), DIFF); 
        assertEquals(100.0, Unit.convertTo("1 m", "cm"), DIFF); 
        assertEquals(Double.NaN, Unit.convertTo("5 apples", "m")); 
        assertEquals(Double.NaN, Unit.convertTo("2 kg", "bananas")); 
        assertEquals(5000.0, Unit.convertTo("5 kg", "g"), DIFF); 
        assertEquals(0.002, Unit.convertTo("2 g", "kg"), DIFF); 
        assertEquals(0.001, Unit.convertTo("1 m", "km"), DIFF); 
        assertEquals(1.0, Unit.convertTo("1000 kg", "Mg"), DIFF); 
    } 
  
    @Test 
    void testConvertToWithPrefix() { 
        assertEquals(5.0, Unit.convertTo("5000 kg", "t"), DIFF); 
        assertEquals(0.001, Unit.convertTo("1 kg", "t"), DIFF); 
        assertEquals(0.001, Unit.convertTo("1000 g", "t"), DIFF); 
        assertEquals(1000.0, Unit.convertTo("1 Mg", "kg"), DIFF); 
        assertEquals(Double.NaN, Unit.convertTo("5 bananas", "t")); 
    } 
  
    @Test 
    void testConvertToInvalidUnit() { 
        assertEquals(Double.NaN, Unit.convertTo("5 kg", "apples")); 
        assertEquals(Double.NaN, Unit.convertTo("2 kg", "bananas")); 
        assertEquals(Double.NaN, Unit.convertTo("5 bananas", "kg")); 
    } 
  
    @Test 
    void testExpandCompound() { 
        assertEquals("kg m s^-2", Unit.expandCompound("N")); 
        assertEquals("kg m^2 s^-2", Unit.expandCompound("J")); 
        assertEquals(Double.NaN, Unit.expandCompound("XYZ")); 
    } 
} 
 
