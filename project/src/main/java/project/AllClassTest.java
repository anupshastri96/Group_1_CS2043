package project; 
import org.junit.platform.runner.JUnitPlatform; 
import org.junit.platform.suite.api.SelectClasses; 
import org.junit.runner.RunWith; 
  
@RunWith(JUnitPlatform.class) 
@SelectClasses({FunctionsTest.class, UnitTest.class}) 
public class AllClassTest { 
} 
 
