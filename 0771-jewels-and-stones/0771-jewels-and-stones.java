class Solution {
    public int numJewelsInStones(String a, String b) {
        int c=0;
        for (int i=0;i<b.length();i++){
            char ch=b.charAt(i);
            if(a.indexOf(ch)!=-1){
                c++;
            }
        }
        return c;
    }
}