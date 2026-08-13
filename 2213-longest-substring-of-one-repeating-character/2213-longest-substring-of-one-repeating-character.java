class Solution {
    class Node {
        char leftChar, rightChar;
        int prefixLen, suffixLen, maxLen;

        public Node(char c) {
            this.leftChar = c;
            this.rightChar = c;
            this.prefixLen = 1;
            this.suffixLen = 1;
            this.maxLen = 1;
        }

        public Node() {}
    }

    private Node[] tree;
    private char[] chars;

    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
        int n = s.length();
        int k = queryIndices.length;
        this.chars = s.toCharArray();
        this.tree = new Node[4 * n];

        buildTree(0, 0, n - 1);

        int[] ans = new int[k];
        for (int i = 0; i < k; i++) {
            int idx = queryIndices[i];
            char c = queryCharacters.charAt(i);
            
            chars[idx] = c;
            updateTree(0, 0, n - 1, idx, c);
            
            // The root node (index 0) holds the maxLen for the full range [0, n - 1]
            ans[i] = tree[0].maxLen;
        }

        return ans;
    }

    private Node merge(Node left, Node right, int leftSize, int rightSize) {
        Node res = new Node();
        res.leftChar = left.leftChar;
        res.rightChar = right.rightChar;

        // Max length within sub-segments
        res.maxLen = Math.max(left.maxLen, right.maxLen);

        // Check if middle characters match to merge across the split
        if (left.rightChar == right.leftChar) {
            res.maxLen = Math.max(res.maxLen, left.suffixLen + right.prefixLen);
        }

        // Prefix length calculation
        if (left.prefixLen == leftSize && left.leftChar == right.leftChar) {
            res.prefixLen = leftSize + right.prefixLen;
        } else {
            res.prefixLen = left.prefixLen;
        }

        // Suffix length calculation
        if (right.suffixLen == rightSize && right.rightChar == left.rightChar) {
            res.suffixLen = rightSize + left.suffixLen;
        } else {
            res.suffixLen = right.suffixLen;
        }

        return res;
    }

    private void buildTree(int node, int start, int end) {
        if (start == end) {
            tree[node] = new Node(chars[start]);
            return;
        }
        int mid = start + (end - start) / 2;
        int leftNode = 2 * node + 1;
        int rightNode = 2 * node + 2;

        buildTree(leftNode, start, mid);
        buildTree(rightNode, mid + 1, end);

        tree[node] = merge(tree[leftNode], tree[rightNode], mid - start + 1, end - mid);
    }

    private void updateTree(int node, int start, int end, int idx, char c) {
        if (start == end) {
            tree[node] = new Node(c);
            return;
        }
        int mid = start + (end - start) / 2;
        int leftNode = 2 * node + 1;
        int rightNode = 2 * node + 2;

        if (idx <= mid) {
            updateTree(leftNode, start, mid, idx, c);
        } else {
            updateTree(rightNode, mid + 1, end, idx, c);
        }

        tree[node] = merge(tree[leftNode], tree[rightNode], mid - start + 1, end - mid);
    }
}