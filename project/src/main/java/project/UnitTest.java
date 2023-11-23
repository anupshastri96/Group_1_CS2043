package project; 
import org.junit.jupiter.api.Test; 
import static org.junit.jupiter.api.Assertions.*; 
  
class UnitTest { 
    // Testing all types of input (valid, invalid and a mix of both) using the convertToSI method
    @Test 
    void testConvertToSI() { 
        assertEquals("2.26796 kg", Unit.convertToSI("5 lb")); 
        assertEquals("0.45359237 kg", Unit.convertToSI("1 lb")); 
        assertEquals("!", Unit.convertToSI("5 apples")); 
        assertEquals("!", Unit.convertToSI("2 kg apples")); 
        assertEquals("1000.0 kg", Unit.convertToSI("1000 g")); 
        assertEquals("0.001 kg", Unit.convertToSI("1 g")); 
        assertEquals("1000000.0 g", Unit.convertToSI("1 Mg")); 
    } 
  
    @Test 
    void testConvertToSIWithPrefix() { 
        // Testing Prefixes
        assertEquals("1000.0 kW", Unit.convertToSI("1000 daW")); 
        assertEquals("0.001 kW", Unit.convertToSI("1 mW")); 
        assertEquals("1000.0 kg", Unit.convertToSI("1 Mg")); 
        assertEquals("0.001 kg", Unit.convertToSI("1 g")); 
        assertEquals("1000.0 g", Unit.convertToSI("1 kg")); 
    } 
  
    @Test 
    void testConvertToSIInvalidUnit() { 
        // Trying invalid units & a mix of invalid and valid
        assertEquals("!", Unit.convertToSI("5 apples")); 
        assertEquals("!", Unit.convertToSI("2 kg apples")); 
    } 
  
    @Test 
    void testConvertTo() { 
        // Testing convert method
        assertEquals("0.01 km", Unit.convertTo("10 m", "km")); 
        assertEquals("100.0 cm", Unit.convertTo("1 m", "cm")); 
        assertEquals("! (Invalid unit)", Unit.convertTo("5 apples", "m")); 
        assertEquals("! (Invalid unit)", Unit.convertTo("2 kg", "bananas")); 
        assertEquals("5000.0 g", Unit.convertTo("5 kg", "g")); 
        assertEquals("0.002 kg", Unit.convertTo("2 g", "kg")); 
        assertEquals("0.001 km", Unit.convertTo("1 m", "km")); 
        assertEquals("1.0 Mg", Unit.convertTo("1000 kg", "Mg")); 
    } 
  
  
    @Test 
    void testConvertToInvalidUnit() { 
        // Test cases for convertTo with invalid units 
        assertEquals("!", Unit.convertTo("5 kg", "apples")); 
        assertEquals("!", Unit.convertTo("2 kg", "bananas")); 
        assertEquals("!", Unit.convertTo("5 bananas", "kg")); 
    } 
  
    @Test 
    void testExpandCompound() { 
        // Test cases for expandCompound 
        assertEquals("kg m s^-2", Unit.expandCompound("N")); 
        assertEquals("kg m^2 s^-2", Unit.expandCompound("J")); 
        assertEquals("!", Unit.expandCompound("XYZ")); 
        assertEquals("!", Unit.expandCompound("ABCD")); 
    } 
} 
 
