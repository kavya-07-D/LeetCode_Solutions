import java.util.*;

class Solution {
    public String smallestNumber(String num, long t) {
        // Step 1: Prime factorize t
        long tempT = t;
        int[] req = new int[10]; // req[2], req[3], req[5], req[7]
        int[] primes = {2, 3, 5, 7};
        for (int p : primes) {
            while (tempT % p == 0) {
                req[p]++;
                tempT /= p;
            }
        }
        if (tempT > 1) return "-1"; // Invalid prime factors

        int n = num.length();
        
        // Count factors for digits 1..9
        int[][] digitFactors = new int[10][10];
        for (int d = 1; d <= 9; d++) {
            int val = d;
            for (int p : primes) {
                while (val % p == 0) {
                    digitFactors[d][p]++;
                    val /= p;
                }
            }
        }

        // Check if num itself (or prefix) can be used
        // Prefix factorization requirements
        int[][] prefFactors = new int[n + 1][10];
        boolean[] hasZero = new boolean[n + 1];

        for (int i = 0; i < n; i++) {
            int d = num.charAt(i) - '0';
            hasZero[i + 1] = hasZero[i] || (d == 0);
            for (int p : primes) {
                prefFactors[i + 1][p] = prefFactors[i][p] + (d > 0 ? digitFactors[d][p] : 0);
            }
        }

        // Try same length: find longest prefix we can match
        for (int i = n; i >= 0; i--) {
            if (i < n && hasZero[i]) continue; // Cannot match prefix with a 0

            int[] curReq = new int[10];
            for (int p : primes) {
                curReq[p] = Math.max(0, req[p] - (i > 0 ? prefFactors[i][p] : 0));
            }

            int startDigit = (i == n) ? 10 : (num.charAt(i) - '0' + 1);
            if (i == n) {
                // Check if num itself works
                if (!hasZero[n] && curReq[2] == 0 && curReq[3] == 0 && curReq[5] == 0 && curReq[7] == 0) {
                    return num;
                }
                continue;
            }

            for (int d = startDigit; d <= 9; d++) {
                int[] remReq = new int[10];
                for (int p : primes) {
                    remReq[p] = Math.max(0, curReq[p] - digitFactors[d][p]);
                }
                int remLen = n - 1 - i;
                if (isPossible(remReq, remLen)) {
                    // Construct result
                    StringBuilder sb = new StringBuilder(num.substring(0, i));
                    sb.append(d);
                    sb.append(constructSuffix(remReq, remLen));
                    return sb.toString();
                }
            }
        }

        // If same length isn't possible, expand length
        int len = n + 1;
        while (true) {
            if (isPossible(req, len)) {
                return constructSuffix(req, len);
            }
            len++;
        }
    }

    // Check if remaining factors can fit in remLen digits
    private boolean isPossible(int[] req, int remLen) {
        int minLen = getMinDigits(req[2], req[3], req[5], req[7]);
        return minLen <= remLen;
    }

    // Calculate minimum digits to fulfill required prime exponents
    private int getMinDigits(int c2, int c3, int c5, int c7) {
        int count = c5 + c7;
        count += c3 / 2; // 9s
        c3 %= 2;

        count += c2 / 3; // 8s
        c2 %= 3;

        if (c3 == 1 && c2 == 2) { // 9 and 8 already handled, leftover 3 and 4 -> combine to 6 and 2 or 6 and 6
            count += 2; // e.g. 6 and 6
        } else if (c3 == 1 && c2 == 1) {
            count += 1; // 6
        } else if (c3 == 1) {
            count += 1; // 3
        } else if (c2 == 2) {
            count += 1; // 4
        } else if (c2 == 1) {
            count += 1; // 2
        }

        return count;
    }

    // Construct the lexicographically smallest suffix of given length
    private String constructSuffix(int[] req, int len) {
        StringBuilder sb = new StringBuilder();
        int c2 = req[2], c3 = req[3], c5 = req[5], c7 = req[7];

        for (int pos = 0; pos < len; pos++) {
            for (int d = 1; d <= 9; d++) {
                int rem2 = Math.max(0, c2 - (d == 2 || d == 6 ? 1 : d == 4 ? 2 : d == 8 ? 3 : 0));
                int rem3 = Math.max(0, c3 - (d == 3 || d == 6 ? 1 : d == 9 ? 2 : 0));
                int rem5 = Math.max(0, c5 - (d == 5 ? 1 : 0));
                int rem7 = Math.max(0, c7 - (d == 7 ? 1 : 0));

                if (getMinDigits(rem2, rem3, rem5, rem7) <= len - 1 - pos) {
                    sb.append(d);
                    c2 = rem2; c3 = rem3; c5 = rem5; c7 = rem7;
                    break;
                }
            }
        }
        return sb.toString();
    }
}