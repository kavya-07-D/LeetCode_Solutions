/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        if(head == null || head.next == null)return head;
        ListNode res = new ListNode(0),temp = res,p = head;
        while(p != null){
            if(p.next != null && p.val == p.next.val){
                while(p.next != null && p.val == p.next.val)p = p.next;
                temp.next = p.next;
            }else{
                temp.next = p;
                temp = p;
            }
            p = p.next;
        }
        return res.next;
    }
}