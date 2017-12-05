

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Writer;
import java.sql.Time;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Properties;
import java.util.Scanner;

public class FileComparator {

	private int FIELD_LENGTH;
	private final String NUMERIC_REGEX = "[+-]?\\d*(\\.\\d+)?";
	private String FILE_INPUT_1;
	private String FILE_INPUT_2;
	private String FILE_OUTPUT;
	private String DELIMITER;
	private final int PROGRESSBAR_LENGTH = 50;
	private String[] HEADER_FIELDS;
	private String[] KEY_INDEX;
	private String CONFIG_FILE_PATH;
	private String ANIMATION_TEXT = "|/-\\";
	private String KEY_INDICATOR;
	private Writer fileWriter;

	public static void main(String[] args) throws IOException, InterruptedException {
		
		FileComparator fC = new FileComparator();
		
		fC.getInput();
		
		System.out.println("Starting ... " + new Time(System.currentTimeMillis()));
		
		fC.manageOutput(true);
		
		fC.compare();
		fC.manageOutput(false);
		
		System.out.println("Completed ... " + new Time(System.currentTimeMillis()));
		
//		fC.populateData();
	}
	
	private void getInput() throws FileNotFoundException, IOException{
		
		Scanner in = new Scanner(System.in);
        
		System.out.print("CONFIGURATION FILE PATH : ");
		
		CONFIG_FILE_PATH = in.nextLine();
        
		Properties config = new Properties();
		config.load(new FileInputStream(CONFIG_FILE_PATH));
		
		FILE_INPUT_1 = config.getProperty("FILE_INPUT_1");
		FILE_INPUT_2 = config.getProperty("FILE_INPUT_2");
		DELIMITER = config.getProperty("DELIMITER");
		FILE_OUTPUT = config.getProperty("FILE_OUTPUT");
		HEADER_FIELDS = config.getProperty("HEADER_FIELDS").split(DELIMITER);
		KEY_INDEX = config.getProperty("KEY_INDEX").split(DELIMITER);
		
		KEY_INDICATOR = config.getProperty("KEY_INDICATOR");
		
        in.close();
	}
	
	private void createProgressBar(){
		
		try {
			
			for (int i = 0; i < 100; i++) {
				drawProgressBar(i, 100);
				Thread.sleep(500l);
				System.out.print("Processing "+ANIMATION_TEXT.charAt(i % ANIMATION_TEXT.length()));
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private  void compare() throws IOException, InterruptedException {

		String[][] file_1 = populateArray(FILE_INPUT_1);
		String[][] file_2 = populateArray(FILE_INPUT_2);

		SortMultidimensionalArray array_1 = new SortMultidimensionalArray(file_1);
		SortMultidimensionalArray array_2 = new SortMultidimensionalArray(file_2);

		Thread thread_1 = new Thread(array_1);
		Thread thread_2 = new Thread(array_2);
		
		thread_1.start();
		thread_2.start();
		
		thread_1.join();
		thread_2.join();
		
		println("");
		
		println("INPUT_1_LENGTH"+DELIMITER+file_1.length);
		println("INPUT_2_LENGTH"+DELIMITER+file_2.length);
		
		println("");
		
		compareElements(file_1, file_2);
	}

	public void printArray(String[][] fileDataTable) throws IOException {

		for (int i = 0; i < fileDataTable.length; i++) {

			for (int j = 0; j < FIELD_LENGTH; j++) {

				print(" "+fileDataTable[i][j]);
			}
			newLine();
		}
	}

	private String[][] populateArray(String filePath) throws IOException {

		int TOTAL_RECORDS = getTotalLines(filePath);
		
		FIELD_LENGTH = HEADER_FIELDS.length;

		String[][] fileDataTable = new String[TOTAL_RECORDS][FIELD_LENGTH];

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

			String line = br.readLine();

			int i = 0;

			while (line != null) {

				String[] record = line.split(DELIMITER);

				for (int j = 0; j < record.length; j++) {
					fileDataTable[i][j] = record[j];
				}

				line = br.readLine();
				i++;

			}
			return fileDataTable;
		}
	}

	private int getTotalLines(String filePath) throws FileNotFoundException, IOException {

		try (LineNumberReader reader = new LineNumberReader(new FileReader(filePath))) {

			String line = reader.readLine();
			
			while (line != null && line.trim().length() > 0 )
				line = reader.readLine();

			return reader.getLineNumber();
		}
	}

	private void println(String input) throws IOException {

//		System.out.println(input.toUpperCase());
		
		doOutput("\n"+input);
	}
	
	private void newLine() throws IOException{
//		System.out.println();
		doOutput("\n");
	}
	
	private void print(String input) throws IOException {

//		System.out.print(input.toUpperCase());
		doOutput(input);
	}

	private void compareElements(String[][] input_1, String[][] input_2) throws IOException {

		int key = 0;

		String[] arrayKey = getSingleDimensioanlArrayKey(input_2);

		String input_key = null;
		
		int input_1_length = input_1.length;
		
		for (int i = 0; i < input_1_length; i++) {

			if(input_1[i] == null)
				continue;
			
			input_key = getKeyFromRecord(input_1[i]);
			
			key = Arrays.binarySearch(arrayKey, input_key, new SingleDimensionalComparator());
			
			if (key >= 0) {

				compareRecord(input_1, input_2, i, key);

			} else {
				 println("\nRECORD_NOT_FOUND"+"\n"+ getRecordFromKey().replace(KEY_INDICATOR, DELIMITER) + "\n" + input_key.replace(KEY_INDICATOR, DELIMITER));
			}
			
//			System.out.print("\r PROCESSING "+ANIMATION_TEXT.charAt(i % ANIMATION_TEXT.length()));
		}
		
//		System.out.println();
	}
	
	private String getKeyFromRecord(String[] input){
		
		if(input.length == 0 || input[0] == null)
			return null;
		
		StringBuffer compositeKey = new StringBuffer(); 
		
			for(int j=0; j<KEY_INDEX.length; j++){
				
				compositeKey.append(input[Integer.parseInt(KEY_INDEX[j])]);
				
				if(j < (KEY_INDEX.length-1))
					compositeKey.append(KEY_INDICATOR);
			}
				
		return compositeKey.toString();
	}
	
	private String getRecordFromKey(){
		
		StringBuffer compositeKey = new StringBuffer(); 
		
			for(int j=0; j<KEY_INDEX.length; j++){
				
				compositeKey.append(HEADER_FIELDS[Integer.parseInt(KEY_INDEX[j])]);
				
				if(j < (KEY_INDEX.length-1))
					compositeKey.append(KEY_INDICATOR);
			}
				
		return compositeKey.toString();
	}
	
	private void printOneDimensionalArray(String[] array) throws IOException{
		
		for(int i=0; i<array.length; i++){
			
			print(""+array[i]+DELIMITER);
		}
		
		newLine();
	}

	private String[] getSingleDimensioanlArrayKey(String[][] input) {

		String[] output = new String[input.length];

		StringBuffer compositeKey = new StringBuffer(); 
		
		for (int i = 0; i < input.length; i++) {

			for(int j=0; j<KEY_INDEX.length; j++){
				
				compositeKey.append(input[i][Integer.parseInt(KEY_INDEX[j])]);
				
				if(j < (KEY_INDEX.length-1))
					compositeKey.append(KEY_INDICATOR);
			}
				
			output[i] = compositeKey.toString();
			compositeKey = new StringBuffer();
		}

		Arrays.sort(output, new SingleDimensionalComparator());
		
		return output;
	}

	private void compareRecord(String[][] input_1, String[][] input_2, int input_1_row, int input_2_row) throws IOException {

		for (int i = 0; i < FIELD_LENGTH; i++) {

			if (input_1[input_1_row][i]!= null && input_2[input_2_row][i]!=null && !input_1[input_1_row][i].equals(input_2[input_2_row][i])) {

				
				println("");
				println("RECORD_MISMATCH");
				println("VALUE_1" + DELIMITER + "[" +HEADER_FIELDS[i]+"]" +DELIMITER+ input_1[input_1_row][i]);
				println("VALUE_2" + DELIMITER + "[" +HEADER_FIELDS[i] +"]" +DELIMITER+ input_2[input_2_row][i]);
				println("");
				printOneDimensionalArray(HEADER_FIELDS);
				printRecordDetails(input_1, input_1_row);
				printRecordDetails(input_2, input_2_row);
				println("");
			}
		}
	}

	private void printRecordDetails(String[][] inputArray, int rowNum) throws IOException{
		
//		printOneDimensionalArray(HEADER_FIELDS);
		
		printOneDimensionalArray(getRecordDetails(inputArray, rowNum));
	}
	
	private String[] getRecordDetails(String[][] inputArray, int rowNum){
		
		String[] outputArray = new String[FIELD_LENGTH];
		
		for(int i=0; i<FIELD_LENGTH; i++){
			
			outputArray[i] = inputArray[rowNum][i];
		}
		
		return outputArray;
	}
	
	private int compareElements(Object value_1, Object value_2) {

			return value_1.toString().trim().compareToIgnoreCase(value_2.toString().trim());
	}

	private boolean isNumeric(String input) {
		return input.matches(NUMERIC_REGEX);
	}
	
	private class MultiDimensionalComparator implements Comparator<String[]>{

		@Override
		public int compare(String[] o1, String[] o2) {
			
			return compareElements(getKeyFromRecord(o1), getKeyFromRecord(o2));
		}
	}
	
	private class SingleDimensionalComparator implements Comparator<String>{

		@Override
		public int compare(String o1, String o2) {
			
			return compareElements(o1, o2);
		}
	}
	
	private void populateData() throws IOException{
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File("test_data_2.txt")));
		
		double d = 0;
		
		for(int i=0; i<1000000; i++){
		
			d = Math.random();
			
			writer.write(i+DELIMITER+(i+1+d)+DELIMITER+(i+2+d));
			writer.newLine();
		}
		
		writer.close();
	}
	
	private void progress() throws IOException, InterruptedException{
		
		String anim= "|/-\\";
		String p = "=";
        for (int x =0 ; x < 100 ; x++){
        	p=p+"=";
                String data =  anim.charAt(x % anim.length())  + " " + x +"\r";
                System.out.write(data.getBytes());
                Thread.sleep(100);
        }
	}
	

	public void drawProgressBar(int numerator, int denominator) {
	    int percent = (int) (((double) numerator / (double) denominator) * 100);

	    String bar = "[";
	    int lines = round((PROGRESSBAR_LENGTH * numerator) / denominator);
	    int blanks = PROGRESSBAR_LENGTH - lines;

	    for (int i = 0; i < lines; i++)
	        bar += "|";

	    for (int i = 0; i < blanks; i++)
	        bar += " ";

	    bar += "] " + percent + "%";
	    
	    System.out.print(bar + "\r");
	}

	private int round(double dbl) {
	    int noDecimal = (int) dbl;
	    double decimal = dbl - noDecimal;

	    if (decimal >= 0.5)
	        return noDecimal + 1;
	    else
	        return noDecimal;
	}
	
	private void doOutput(String text) throws IOException{
		
		fileWriter.write(text);
	}
	
	private void manageOutput(boolean open) throws IOException{
		
		if(open){
			
			fileWriter = new BufferedWriter(new FileWriter(new File(FILE_OUTPUT)), 1024*4);
			
		}else{
			
			fileWriter.flush();
			fileWriter.close();
		}
		
	}
	
	private class SortMultidimensionalArray implements Runnable{
		
		private String[][] inputArray;
		
		public SortMultidimensionalArray(String[][] inputArray) {
			super();
			this.inputArray = inputArray;
		}

		@Override
		public void run() {
			
			Arrays.sort(inputArray, new MultiDimensionalComparator());
		}
	}
}
