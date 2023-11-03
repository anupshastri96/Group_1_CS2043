package project;

import java.util.HashMap;

public class Constant {
  private HashMap<String, String> constant = new HashMap<>();
  
  public Constant() {
      addConstant("c", "299792458m/s"); //speed of light
      addConstant("h", "6.62607015E-34J s"); //planck's constant
      addConstant("hbar", "1.054571817E-34J s"); //reduced planck's constant
      addConstant("mu_0", "1.25663706E-6m kg s^-2 A^-2"); //magnetic constant
      addConstant("epsilon_0", "8.85418782E-12m^-3 kg^-1 s^4 A^2"); //electric constant
      addConstant("k_B", "1.380649E-23m^2 kg s^-2 K-1"); //boltzmann constant
      addConstant("G", "6.67430E-11N m^2 kg^-2"); //gravitational constant
      addConstant("k_e", "8.987E9N m^2 C^-2"); //coulomb's constant
      addConstant("sigma", "5.670374419E-8W m^-2 K^-4"); //stefan boltzmann constant
      addConstant("e", "1.602176634E-19C"); //elementary charge
      addConstant("alpha", "7.2973525693E-3"); //fine-structure constant
      addConstant("m_e", "9.1093837E-31kg"); //mass of electron
      addConstant("m_p", "1.672621923E-27kg"); //mass of proton
      addConstant("m_n", "1.674927498E-27kg"); //mass of neutron
      addConstant("euler", "2.71828182"); //euler's number
      addConstant("pi", "3.1415926"); //pi
  }
  
  public void addConstant(String symbol, String value) {
      constant.put(symbol, value);
  }
  
  public String convertConstant(String symbol) {
      return constant.get(symbol);
  }
}