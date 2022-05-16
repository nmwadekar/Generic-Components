import java.util.Arrays;

public class ColourSort {

    public static void main(String... args) {

        sortColors(new int[]{2, 0, 2, 1, 1, 0});

//        sortColors(new int[]{1,1,0});
    }

    public static void sortColors(int[] nums) {

        int output[] = new int[nums.length];

        int left = 0, right = nums.length - 1;

        Arrays.fill(output, 1);

        for (int i = 0; i < nums.length; i++) {

            if (nums[i] < 1) {

                output[left] = nums[i];

                left++;
            } else if (nums[i] > 1) {

                output[right] = nums[i];

                right--;

            }
        }

//        nums = output.clone();

        System.arraycopy(output, 0, nums, 0, output.length);

        System.out.println("output = " + Arrays.toString(output));
        System.out.println("output actual = " + Arrays.toString(nums));
    }
}
