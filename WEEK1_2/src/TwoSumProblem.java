import java.util.*;

class TwoSumVariants {

    // 1. Classic Two Sum
    public static int[] twoSum(int[] arr, int target) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            int diff = target - arr[i];

            if (map.containsKey(diff)) {
                return new int[]{map.get(diff), i};
            }
            map.put(arr[i], i);
        }
        return new int[]{-1, -1};
    }

    // 2. Find duplicates
    public static Set<Integer> findDuplicates(int[] arr) {
        Set<Integer> seen = new HashSet<>();
        Set<Integer> duplicates = new HashSet<>();

        for (int num : arr) {
            if (!seen.add(num)) {
                duplicates.add(num);
            }
        }
        return duplicates;
    }

    // 3. 3-Sum
    public static List<List<Integer>> threeSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 2; i++) {
            int left = i + 1, right = nums.length - 1;

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];

                if (sum == target) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    left++;
                    right--;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] arr = {100, 200, 300, 500, 700};

        // Two sum
        int[] res = twoSum(arr, 500);
        System.out.println("Two Sum indices: " + res[0] + ", " + res[1]);

        // Duplicates
        int[] dupArr = {100, 200, 300, 200, 100};
        System.out.println("Duplicates: " + findDuplicates(dupArr));

        // 3-sum
        System.out.println("3-Sum: " + threeSum(arr, 1000));
    }
}