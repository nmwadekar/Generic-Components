package nmw.core.concept;

import java.util.HashSet;
import java.util.Set;

public class Combinator {
	
	private static Set<String> finalOutput = new HashSet<>();
	
  public static void main(String[] args) {
	  
	  Set<String> output = find(0, "ABC".toCharArray());
	  
	  System.out.println("output size "+output.size() + " : " +output);
  }
  
  public static Set<String> find(int key, char[] input){
	  
	  if(key >= input.length)
		  return null;
	  
	  permute(key, input);
	  
	  find(key+1, input);
	  
	  return finalOutput;
	  
  }
  
  public static void permute(int key, char[] input){
	  
	  char[] o = reduceArray(input, key);
	  
	  System.out.println(String.valueOf(input[key]) + new String(o));
	  
	  finalOutput.add(String.valueOf(input[key]) + new String(o));
	  
	  for(int i=0; i < o.length-1; i++){
			  
		  int j = i+1;
		  
			  char swapper = o[i];
			  o[i] = o[j];
			  o[j] = swapper;
			  
			  System.out.println(String.valueOf(input[key])+new String(o));
			  
			  finalOutput.add(String.valueOf(input[key])+new String(o));
		  
	  }
  }
  
  public static char[] reduceArray(char[] input, int key){
	  
	  char[] output = new char[input.length-1];
	  
	  int j=0;
	  
	  for(int i=0; i<input.length; i++){
		  
		  if(key != i)
			  output[j] = input[i];
		  else{
			  continue;
		  }
		  
		  j++;
	  }
	  
	  return output;
  }
  
}
