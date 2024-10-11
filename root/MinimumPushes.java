import java.util.Arrays;

public class MinimumPushes {

    public static void main(String[] args) {

        String input = "aabbccddeeffgghhiiiiii";
        System.out.println(minimumPushes(input));
    }

    public static int minimumPushes(String word) {

        int[] alphabets = new int[26];

        for (char c : word.toCharArray()) {
            alphabets[c - 97]++;
        }

        Arrays.sort(alphabets);

        int output = 0;
        int bucket = 1;
        int counter = 1;

        for (int j = alphabets.length - 1;  j >= 0 && alphabets[j] != 0; j--, counter++) {

            System.out.println(String.format("j=%d bucket=%d alphabet=%d counter=%d",
                    j, bucket, alphabets[j], counter));

            output += alphabets[j] * bucket;

            if(counter % 8 == 0)
                bucket++;
        }

        return output;
    }
}
