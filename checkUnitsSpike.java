package sp1;

import java.util.Scanner;
import java.lang.String;
import java.util.ArrayList;

public class checkUnitsSpike {
	static String[] time = {"s", "h", "min"};
	static String[] distance = {"m", "km"};
	
	private static boolean isIn(String[] arr, String input) {
		for(int i = 0; i<arr.length;i++) {
			if(arr[i].equals(input)) {
				return true;
			}
		}
		return false;
		
	}
	/*
	private static boolean isIntime(String input) {
		for (int i = 0;i<time.length;i++) {
			
			if(time[i]=input) {
				return true;
			}
			
		}
		
	}
	*/
	public static boolean checkUnits(String input) {
		Scanner sc = new Scanner(input);
		String left = "";
		String right = "";
		
		int i = 0;
		while(i<input.length() && input.charAt(i) != ' ') {
			i++;
		}
		i++;
		
		while(i<input.length() && input.charAt(i) != ')') {
			left += input.charAt(i);
			i++;
		}
		
		while(i<input.length() && input.charAt(i) != '=') {
			i++;
		}
		i += 2;
		
		while(i<input.length() && input.charAt(i) != ' ') {
			i++;
		}
		i++;
		
		while(i<input.length() && input.charAt(i) != ')') {
			right += input.charAt(i);
			i++;
		}
		
		
		if (isIn(time, left) && isIn(time, right)) {
			return true;
		}
		else if (isIn(distance, left) && isIn(distance, right)) {
			return true;
		}
		return false;
	}
	
	
	
	
	public static void main(String[] args) {
		String input1 = "(2 s) = (3 h)";
		String input2 = "(1 s) = (3 km)";
		
		System.out.println(checkUnits(input1));
		System.out.println(checkUnits(input2));
	}
}
