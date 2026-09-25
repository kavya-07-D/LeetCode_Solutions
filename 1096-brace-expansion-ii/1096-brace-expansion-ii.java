import java.util.*;

class Solution {
    public List<String> braceExpansionII(String expression) {
        Set<String> result = solve(expression);
        return new ArrayList<>(result);
    }

    private Set<String> solve(String s) {
        Set<String> result = new TreeSet<>();

        // Find the first '{'
        int open = s.indexOf('{');

        // No braces → just return the string
        if (open == -1) {
            result.add(s);
            return result;
        }

        // Find matching '}'
        int count = 0;
        int close = -1;

        for (int i = open; i < s.length(); i++) {
            if (s.charAt(i) == '{') {
                count++;
            } else if (s.charAt(i) == '}') {
                count--;
                if (count == 0) {
                    close = i;
                    break;
                }
            }
        }

        // Expand the content inside braces
        String inside = s.substring(open + 1, close);

        // Split by top-level commas
        List<String> parts = new ArrayList<>();
        int start = 0;
        count = 0;

        for (int i = 0; i < inside.length(); i++) {
            char c = inside.charAt(i);

            if (c == '{') {
                count++;
            } else if (c == '}') {
                count--;
            } else if (c == ',' && count == 0) {
                parts.add(inside.substring(start, i));
                start = i + 1;
            }
        }

        parts.add(inside.substring(start));

        // Recursively expand each part
        Set<String> expanded = new TreeSet<>();

        for (String part : parts) {
            expanded.addAll(solve(part));
        }

        // Prefix and suffix
        String prefix = s.substring(0, open);
        String suffix = s.substring(close + 1);

        // Concatenate prefix + expanded + suffix
        for (String x : expanded) {
            Set<String> left = solve(prefix + x + suffix);
            result.addAll(left);
        }

        return result;
    }
}