package nmw.core.concept;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ArithmeticEvaluator {

	private static final char[] OPERATORS = new char[] { '+', '-', '/', '*' };
	
	private static ScriptEngineManager mgr = new ScriptEngineManager();
	private static ScriptEngine engine = mgr.getEngineByName("JavaScript");
	
	private static Map<String, ArrayList<Object>> mappings = new HashMap<String, ArrayList<Object>>();

	public static void main(String[] args) throws ScriptException {

		String[] input = new String[]{"9","6","12", "8"};
		
		Set<String[]> a = evaluateExpression(input.length-1);
		 
		Combinator.resetOutput();
		
		Set<String[]> b =  evaluateOperands(input);
		
		formEquation(b, a);
		
		findEquation();
		
		System.out.println(mappings);

	}

	private static void findEquation() throws ScriptException{
		
		Set<String> eqs = getKeysByValue(mappings, new Integer(144));
		
		System.out.println("EQUATIONS FOUND FOR 144 - \n" + eqs);
		
		for(String eq : eqs){
			
			String equation = buildExpression(new String[]{"5", "40", "32", "12"}, new String[]{eq});
			Object result = executeEquation(equation);
			
			BigDecimal digit_48 = new BigDecimal(48);
			
			BigDecimal bd = null;
			
			
			if(result instanceof Integer){
				bd = new BigDecimal((int)result);
			}else{
				bd = new BigDecimal((double)result);
			}
			
			if(bd.equals(digit_48)){
				
				System.out.println("######### found formula = " + eq);
			}
			
			System.out.println(result+ " type "  +result.getClass());
		}
		
		
	}
	
	private static String[] convert(Set<String> input){
		
		String[] output = new String[input.size()];
		
		int i = 0;
		
		for(String s : input){
			
			output[i++] = s;
		}
		
		return output;
	}
	
	public static <T, E> Set<T> getKeysByValue(Map<T, ArrayList<E>> map, E value) {
	    Set<T> keys = new HashSet<T>();
	    for (Entry<T, ArrayList<E>> entry : map.entrySet()) {
	        if (entry.getValue().contains(value)) {
	            keys.add(entry.getKey());
	        }
	    }
	    return keys;
	}
	
	private static void formEquation(Set<String[]> operands, Set<String[]> expressions) throws ScriptException{
		
		String equation = null;
		Object result = null;
		
		for(String[] o : operands){
			
			for(String[] e : expressions){
				
				equation = buildExpression(o, e);
				result = executeEquation(equation);
				
				if(mappings.containsKey(new String(e[0]))){
				
					ArrayList<Object> l = mappings.get(new String(e[0]));
					
					l.add(result);
					
					mappings.put(new String(e[0]), l);
					
				}else{
				
					ArrayList<Object> a = new ArrayList<>();
					a.add(result);
					
					mappings.put(new String(e[0]), a);
				}
				
				System.out.println(new String(e[0]) + "#" + result);
				
				System.out.println("EQUATION ["+equation + "] = " + result /*+ " TYPE = " + result.getClass()*/);
				
			}
		}
	}


	public static Set<String> roundRobin(int n, int r, char[] input) {

		for (int i = 0; i < input.length; i++) {

			for (int j = 0; j < r; j++) {

				System.out.print(input[(i + j) % n]);
			}

			System.out.println();
		}
		return null;
	}

	public static char[] fill(char c, int n) {

		char[] output = new char[n];

		for (int i = 0; i < n; i++) {

			output[i] = c;
		}

		return output;
	}

	public static List<char[]> alterPivot(char pivot, char[] input) {

		List<char[]> output = new ArrayList<char[]>();

		for (int i = 0; i < input.length; i++) {

			char[] o = Arrays.copyOf(input, input.length);

			o[i] = pivot;

			output.add(o);
		}

		return output;
	}

	public static Set<String[]> evaluateExpression(int count) {

		Set<String[]> expressionPossibility = new HashSet<>();

		for (int i = 0; i < OPERATORS.length; i++) {

			char[] expression = fill(OPERATORS[i], count);

			for (int j = 0; j < OPERATORS.length/* && i!=j */; j++) {

				List<char[]> pivoted = alterPivot(OPERATORS[j], expression);

				for (String[] p : convert(pivoted)) {

					expressionPossibility.addAll(Combinator.find(0, p));
				}
			}
		}
		
		return expressionPossibility;
	}
	
	private static List<String[]> convert(List<char[]> input){
		
		List<String[]> output = new ArrayList<>();
		
		for(char[] c : input){
			
			String s[] = new String[]{new String(c)};
			
			output.add(s);
		}
		
		return output;
	}
	
	public static Set<String[]> evaluateOperands(String[] input) {

		Set<String[]> expressionPossibility = new HashSet<>();

		expressionPossibility.addAll(Combinator.find(0, input));
		
		return expressionPossibility;
	}

	public static String buildExpression(String[] input, String[] operator) {

		StringBuilder sb = new StringBuilder();

		char[] exp = operator[0].toCharArray();
		
		for (int i = 0; i < input.length - 1; i++) {

			sb.append(input[i]);
			sb.append(exp[i]);
		}

		sb.append(input[input.length - 1]);
		
		return sb.toString();
	}
	
	public static Object executeEquation(String eq) throws ScriptException {

		return engine.eval(eq);
	}

	public static void print(char[] input) {

		for (int i = 0; i < input.length; i++) {

			System.out.print(input[i]);
		}
	}

	public static void print(List<char[]> input) {

		for (char[] i : input) {

			print(i);
		}
	}
	
	public static void printx(Set<String[]> input) {

		for (String[] i : input) {

			printx(i);	
		}
		
		System.out.println();
	}
	
	private static void printx(String[] i){
		
		for(String s : i){
			
			System.out.print(s + "\t");
		}
		
		System.out.println();
	}
	
		
}
