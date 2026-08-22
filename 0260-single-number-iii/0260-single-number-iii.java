class Solution {
    public int[] singleNumber(int[] nums) {
        int[]b=new int[2];
        int l=0;
        for(int i=0;i<nums.length;i++){
            int count =0;
            for(int j=0;j<nums.length;j++){
                if(nums[i]==nums[j]){
                    count++;
                }
            }
            if(count==1){
                b[l]=nums[i];
                l++;
            }

        }
        return b;
    }
}