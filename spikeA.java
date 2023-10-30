import java.util.ArrayList;

public class spikeA {
	public static void main(String[] args) {
		String[][] units = {{"m", "ft", "km"},
							{"kg", "lb", "g"},
							{"A"},
							{"K", "F", "C"},
							{"mol"},
							{"cd"},
							{"s", "hr", "min"} };
		
		System.out.println("Units of measuring distance:");
		for(int i = 0; i < units[0].length; i++) {
			System.out.println(units[0][i]);
		}
	}
}
	

//array[][] = [[m, feet], [kg, lb, g], [A], [K, F, C], [mol], [cd], [s, hr, min]]

//m, kg, A, K, mol, cd, s 
