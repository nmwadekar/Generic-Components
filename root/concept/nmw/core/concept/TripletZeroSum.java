import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TripletZeroSum {

    public static void main(String... args){

        int[] input = new int[]{3,0,-2,-1,1,2};

        threeSum(input);
    }

    public static List<List<Integer>> threeSum(int[] nums) {

        Set<String> uniqueTriplets = new HashSet<>();
        List<List<Integer>> output = new ArrayList<>();

        if (nums == null || nums.length < 3) {

            return output;
        }

        Arrays.sort(nums);

        int[] sortedTriplet = new int[3];

        String uniqueString;

        int x,y,z;

        for (int n = 0; n < nums.length; n++) {

            for (int i = n + 1, j = nums.length - 1; j > 0 && i < nums.length && i != j && j != n; /*i++, j--*/) {

                x = nums[n];
                y = nums[i];
                z = nums[j];

                if(x+y+z > 0){
                    j--;
                }else if(x+y+z < 0){
                    i++;
                }
                else if (x+y+z == 0) {

                    sortedTriplet[0] = x;
                    sortedTriplet[1] = y;
                    sortedTriplet[2] = z;

                    Arrays.sort(sortedTriplet);

                    uniqueString = sortedTriplet[0] + "" + sortedTriplet[1] + "" + sortedTriplet[2];

                    if (!uniqueTriplets.contains(uniqueString)) {
                        uniqueTriplets.add(uniqueString);

                        output.add(Arrays.asList(sortedTriplet[0], sortedTriplet[1], sortedTriplet[2]));

                        System.out.println(String.format("%d,%d,%d", sortedTriplet[0], sortedTriplet[1], sortedTriplet[2]));
                    }

                    i++;
                    j--;
                }
            }
        }

        return output;
    }
}
