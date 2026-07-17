class Solution {
    void helper(int index,int[]nums,ArrayList<Integer> temp,List<List<Integer>> ans,int lastRemove)
    {
     if(index==nums.length)
     {
        ans.add(new ArrayList<>(temp));
        return;
     }
     if(lastRemove!=nums[index])
     {
        temp.add(nums[index]);
        helper(index+1,nums,temp,ans,lastRemove);
        lastRemove = temp.remove(temp.size()-1);
     }
     helper(index+1,nums,temp,ans,lastRemove);
    }
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(nums);
        helper(0,nums,new ArrayList<>(),ans,Integer.MIN_VALUE);
        return ans;
    }
}