import java.util.*;

/**
 * Created by jerryliu on 18/7/26.
 */
public class Solution {

    //1.twoSum
    //坑在于map.get(num)!=i 写成了 map.get(num)>0,对于测试集[1,3,2,4] 6 会返回1 1
    public int[] twoSum(int[] nums, int target) {
        int[] r = new int[2];

        if (nums == null || nums.length == 0)
            return null;
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        for (int i = 0; i < nums.length; i++) {
            int num = target - nums[i];
            if (map.get(num) != null && map.get(num) > 0) {
                r[0] = i;
                r[1] = map.get(num);
                break;
            }
        }

        return r;
    }

    //2.addTwoNumbers
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null)
            return l2;
        if (l2 == null)
            return l1;

        int carry = 0;
        ListNode head = new ListNode(-1);
        ListNode pHead = head;
        while (l1 != null || l2 != null) {
            if (l1 != null) {
                carry += l1.val;
                l1 = l1.next;
            }

            if (l2 != null) {
                carry += l2.val;
                l2 = l2.next;
            }


            ListNode n = new ListNode(carry % 10);
            pHead.next = n;
            pHead = n;
            carry = carry / 10;
            if (carry > 0) {
                ListNode t = new ListNode(carry);
                pHead.next = t;
            }
        }
        return head.next;
    }

    //3.lengthOfLongestSubstring
    public int lengthOfLongestSubstring(String s) {
        if (s == null || "".equals(s))
            return 0;
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        int max = 1;
        int start = 0;

        for (int i = 0; i < s.length(); i++) {
            String ch = s.substring(i, i + 1);
            if (map.get(ch) != null && map.get(ch) >= start) {
                start = map.get(ch) + 1;
                map.put(ch,i);
                continue;
            }

            map.put(ch, i);
            if (i - start + 1 > max)
                max = i - start + 1;
        }
        return max;
    }

    //4.findMedianSortedArrays
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 0) {
            return find(nums2);
        }
        if (nums2 == null || nums2.length == 0)
            return find(nums1);

        int[] nums3 = new int[nums1.length + nums2.length];
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < nums1.length || j < nums2.length) {

            if (i == nums1.length) {
                nums3[k++] = nums2[j++];
                continue;
            }
            if (j == nums2.length) {
                nums3[k++] = nums1[i++];
                continue;
            }

            if (nums2[j] < nums1[i]) {
                nums3[k++] = nums2[j];
                j++;
            } else {
                nums3[k++] = nums1[i];
                i++;
            }
        }

        return find(nums3);
    }

    public double find(int[] num) {
        if (num == null || num.length == 0)
            return 0.0;
        if (num.length == 1)
            return 1.0 * num[0];
        if (num.length % 2 == 1) {
            return num[num.length / 2];
        } else
            return 1.0 * (num[(num.length - 1) / 2] + num[num.length / 2]) / 2;
    }


    //5. Longest Palindromic Substring
    public String longestPalindrome(String s) {
        if (s == null || "".equals(s))
            return s;
        if (s.length() == 1)
            return s;
        int len = 1;
        int left = 0;
        int right = 0;
        int startIndex = 0;
        int endIndex = 0;

        for (int i = 0; i < s.length() - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                left = i;
                right = i + 1;
                while (left >= 0 && right <= s.length() - 1) {
                    if (s.charAt(left) == s.charAt(right)) {
                        if (right - left + 1 > len) {
                            len = right - left + 1;
                            startIndex = left;
                            endIndex = right;
                        }
                        left--;
                        right++;
                    } else {
                        break;
                    }
                }
            }
            left = right = i;

            while (left >= 0 && right <= s.length() - 1) {
                if (s.charAt(left) == s.charAt(right)) {
                    if (right - left + 1 > len) {
                        len = right - left + 1;
                        startIndex = left;
                        endIndex = right;
                    }
                    left--;
                    right++;
                } else {
                    break;
                }
            }


        }


        return s.substring(startIndex, endIndex + 1);
    }


    //6. ZigZag Conversion
    //1.暴力法,寻找规则
    //2.正解:使用两个变量当前row,row的方向,遍历每字符串遍知道每个字符串所属的列
    public String convert(String s, int numRows) {
        if (s == null || s.length() == 0 || s.length() == 1 || s.length() == 2 || numRows == 1)
            return s;

        char[][] arry = new char[numRows][s.length()];
        int circule = numRows + numRows - 2;

        if (numRows == 2) {
            for (int i = 0; i < s.length(); i++) {
                char ch = s.charAt(i);
                arry[i % circule][(i / circule)] = ch;
            }
        } else {

            for (int i = 0; i < s.length(); i++) {
                char ch = s.charAt(i);
                if (i % circule < numRows) {
                    arry[(i % circule)][(i / circule) * (numRows - 1)] = ch;
                } else {
                    arry[numRows - (i % circule - numRows + 1) - 1][(i / circule) * (numRows - 1) + (i % circule - numRows) + 1] = ch;
                }
            }
        }
        StringBuilder result = new StringBuilder();
        for (int j = 0; j < numRows; j++) {
            for (int i = 0; i < arry[j].length; i++) {
                if (arry[j][i] != '\u0000')
                    result.append(arry[j][i]);
            }

        }
        return result.toString();

    }


    //6.reverse integer
    public int reverse(int x) {

        int num = 0;
        while (x != 0) {
            if (num > Integer.MAX_VALUE / 10 || (num == Integer.MAX_VALUE / 10 && num % 10 > 7)) return 0;
            if (num < Integer.MIN_VALUE / 10 || (num == Integer.MIN_VALUE / 10 && num % 10 < -8)) return 0;
            num = num * 10 + x % 10;
            x = x / 10;
        }
        return num;
    }


    //8. String to Integer (atoi)
    public int myAtoi(String str) {
        if (str == null || "".equals(str.trim()))
            return 0;
        str = str.trim();
        int result = 0;
        int flag = 1;
        char ch = str.charAt(0);
        if (!((ch >= '0' && ch <= '9') || (ch == '-' || ch == '+')))
            return 0;
        if (ch == '-' || ch == '+') {
            flag = ch == '-' ? -1 : 1;
            str = str.substring(1);
        }
        for (int i = 0; i < str.length(); i++) {
            ch = str.charAt(i);
            if ((ch >= '0' && ch <= '9')) {
                if (ch == '-' || ch == '+') {
                    flag = ch == '-' ? -1 : 1;
                } else {
                    if (result * flag > Integer.MAX_VALUE / 10 || (result * flag == Integer.MAX_VALUE / 10 && flag * Integer.valueOf(ch - '0') > 7))
                        return Integer.MAX_VALUE;
                    if (result * flag < Integer.MIN_VALUE / 10 || (result * flag == Integer.MIN_VALUE / 10 && flag * Integer.valueOf(ch - '0') < -8))
                        return Integer.MIN_VALUE;

                    result = result * 10 + Integer.valueOf(ch - '0');
                }

            } else {
                break;
            }
        }

        return result * flag;

    }


    //9.isPalindrome
    public boolean isPalindrome(int x) {

        // Special cases:
        // As discussed above, when x < 0, x is not a palindrome.
        // Also if the last digit of the number is 0, in order to be a palindrome,
        // the first digit of the number also needs to be 0.
        // Only 0 satisfy this property.
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }

        int revertedNumber = 0;
        while (x > revertedNumber) {
            revertedNumber = revertedNumber * 10 + x % 10;
            x /= 10;
        }

        // When the length is an odd number, we can get rid of the middle digit by revertedNumber/10
        // For example when the input is 12321, at the end of the while loop we get x = 12, revertedNumber = 123,
        // since the middle digit doesn't matter in palidrome(it will always equal to itself), we can simply get rid of it.
        return x == revertedNumber || x == revertedNumber / 10;
    }

    public boolean isMatch(String s, String p) {
        if (p.isEmpty()) return s.isEmpty();
        boolean first_match = (!s.isEmpty() &&
                (p.charAt(0) == s.charAt(0) || p.charAt(0) == '.'));

        if (p.length() >= 2 && p.charAt(1) == '*') {
            return (isMatch(s, p.substring(2)) ||
                    (first_match && isMatch(s.substring(1), p)));
        } else {
            return first_match && isMatch(s.substring(1), p.substring(1));
        }


    }


    //11.坐标轴的最大面积
    public int maxArea(int[] height) {
        if (height == null || height.length == 0)
            return 0;
        int max = 0;
        for (int i = 0; i < height.length; i++) {
            for (int j = i + 1; j < height.length; j++) {
                int width = j - i;
                int high = Math.min(height[i], height[j]);
                if (width * high > max)
                    max = width * high;
            }
        }

        return max;
    }


    //12.转roma
    public String intToRoman(int num) {
        int[] arr = new int[]{};
        List<String> list = new ArrayList<String>();
        int n = num;
        int pop = 0;
        int i = 0;
        while (n != 0) {
            pop = n % 10;
            list.add(convert(pop, (int) Math.pow(10, i++)));
            n = n / 10;

        }

        StringBuffer sb = new StringBuffer();
        for (i = list.size() - 1; i >= 0; i--) {
            sb.append(list.get(i));
        }

        return sb.toString();
    }


    public String convert(int num, int pop) {
        if (pop == 1) {
            switch (num) {
                case 1:
                    return "I";
                case 2:
                    return "II";
                case 3:
                    return "III";
                case 4:
                    return "IV";
                case 5:
                    return "V";
                case 6:
                    return "VI";
                case 7:
                    return "VII";
                case 8:
                    return "VIII";
                case 9:
                    return "IX";
            }
        } else if (pop == 10) {
            switch (num) {
                case 1:
                    return "X";
                case 2:
                    return "XX";
                case 3:
                    return "XXX";
                case 4:
                    return "XL";
                case 5:
                    return "L";
                case 6:
                    return "LX";
                case 7:
                    return "LXX";
                case 8:
                    return "LXXX";
                case 9:
                    return "XC";
            }
        } else if (pop == 100) {
            switch (num) {
                case 1:
                    return "C";
                case 2:
                    return "CC";
                case 3:
                    return "CCC";
                case 4:
                    return "CD";
                case 5:
                    return "D";
                case 6:
                    return "DC";
                case 7:
                    return "DCC";
                case 8:
                    return "DCCC";
                case 9:
                    return "CM";
            }
        } else if (pop == 1000) {
            switch (num) {
                case 1:
                    return "M";
                case 2:
                    return "MM";
                case 3:
                    return "MMM";
            }
        }
        return "";
    }

    public int romanToInt(String s) {
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);

        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            if (i == s.length() - 1 || map.get(s.charAt(i)) >= map.get(s.charAt(i + 1))) {
                result = result + map.get(s.charAt(i));
            } else {
                result = result - map.get(s.charAt(i));
            }
        }
        return result;
    }

    //最长前缀
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0)
            return "";
        for (String str : strs) {
            if ("".equals(str))
                return "";
        }
        StringBuilder sb = new StringBuilder();
        int point = 0;
        while (true) {
            if (point == strs[0].length())
                return sb.toString();
            char first = strs[0].charAt(point);
            for (int i = 0; i < strs.length; i++) {
                if (point == strs[i].length() || strs[i].charAt(point) != first)
                    return sb.toString();
            }
            sb.append(first);
            point++;
        }
    }

    //15.3个数的和是0
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();

        if (nums == null || nums.length < 3)
            return result;
        Set<List<Integer>> set = new HashSet<List<Integer>>();
        Arrays.sort(nums);
        int count = 0;
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 1 && nums[i - 1] == nums[i])
                continue;
            find2(nums, 0 - nums[i], i, set);
        }
        Iterator<List<Integer>> it = set.iterator();
        while (it.hasNext()) {
            result.add(it.next());
        }
        return result;
    }

    void find2(int[] num, int target, int ignore, Set<List<Integer>> set) {
        int i = ignore + 1;
        int j = num.length - 1;
        while (i < j) {
            if (num[i] + num[j] == target) {
                List<Integer> list = new ArrayList<>(Arrays.asList(num[ignore], num[i], num[j]));
                set.add(list);
                i++;
                j--;
            } else if (num[i] + num[j] < target) {
                i++;
            } else if (num[i] + num[j] > target) {
                j--;
            }
        }
    }


    //16. 3Sum Closest
    public int threeSumClosest(int[] nums, int target) {
        if (nums == null || nums.length == 1)
            return 0;
        Arrays.sort(nums);
        int close = nums[0];
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int s = i + 1;
            int e = nums.length - 1;
            while (s < e) {
                if (Math.abs(nums[i] + nums[s] + nums[e] - target) < min) {
                    min = Math.abs(nums[i] + nums[s] + nums[e] - target);
                    close = nums[i] + nums[s] + nums[e];
                }
                if (nums[i] + nums[s] + nums[e] - target > 0) {
                    e--;
                } else {
                    s++;
                }
            }
        }
        return close;
    }

    //17 电话排列组合
    public List<String> letterCombinations(String digits) {
        Map<Character, String> map = new HashMap<Character, String>();

        map.put('2', "ab");
        map.put('3', "cd");
        map.put('4', "ghi");
        map.put('5', "jkl");
        map.put('6', "mno");
        map.put('7', "pqrs");
        map.put('8', "tuv");
        map.put('9', "wxzy");
        int length = digits.length();
        List<String> list = new ArrayList<>();
        if (digits == null || digits.length() == 0)
            return list;
        combile("", digits, map, 0, list);
        return list;
    }

    public void combile(String pre, String digits, Map<Character, String> map, int point, List<String> result) {
        if (point == digits.length()) {
            result.add(pre);
            return;
        }
        String str = map.get(digits.charAt(point));

        for (int i = 0; i < str.length(); i++) {
            combile(pre + str.substring(i, i + 1), digits, map, point + 1, result);
        }
    }


    //17 fourSum
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> list = new ArrayList<List<Integer>>();
        if (nums == null || nums.length == 0 || nums.length < 4)
            return list;
        Arrays.sort(nums);
        for (int p = 0; p <= nums.length - 4; p++) {
            if (p > 0 && nums[p] == nums[p - 1])
                continue;
            for (int i = p + 1; i <= nums.length - 3; i++) {
                if (i > p + 1 && nums[i] == nums[i - 1])
                    continue;
                ;
                int j = i + 1;
                int k = nums.length - 1;
                while (j < k) {
                    if (nums[p] + nums[i] + nums[j] + nums[k] == target) {
                        list.add(new ArrayList<>(Arrays.asList(nums[p], nums[i], nums[j], nums[k])));
                        j++;
                        k--;
                    } else if (nums[p] + nums[i] + nums[j] + nums[k] > target) {
                        k--;
                    } else if (nums[p] + nums[i] + nums[j] + nums[k] < target) {
                        j++;
                    }
                }

            }
        }
        return list;
    }

    //18 delete n node
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null || n <= 0)
            return head;
        ListNode first = head;
        ListNode second = head;
        ListNode pre = head;
        int i = 1;
        while (i < n) {
            second = second.next;
            i++;
        }
        while (second.next != null) {
            pre = first;
            second = second.next;
            first = first.next;
        }
        if (pre == first)
            pre = first;
        else
            pre.next = first.next;
        return head;
    }

    //19 valid bracket
    public boolean isValid(String s) {
        if (s == null && "".equals(s))
            return false;
        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < s.length(); i++) {
            Character ch = s.charAt(i);
            if (!stack.isEmpty() && match(ch, stack.peek())) {
                stack.pop();
                continue;
            }
            stack.push(ch);
        }
        return stack.isEmpty();
    }

    boolean match(Character c1, Character c2) {
        return c2 == '(' && c1 == ')' || c2 == '[' && c1 == ']' || c2 == '{' && c1 == '}';
    }


    //20.
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null)
            return l2;
        if (l2 == null)
            return l1;
        ListNode pHead = new ListNode(-1);
        ListNode left = l1;
        ListNode right = l2;
        ListNode curr = pHead;

        while (left != null || right != null) {
            int i = Integer.MAX_VALUE;
            int j = Integer.MAX_VALUE;
            if (left != null) {
                i = left.val;
            }
            if (right != null) {
                j = right.val;
            }
            ListNode temp = new ListNode(Math.min(i, j));
            curr.next = temp;
            curr = temp;
            if (i <= j)
                left = left.next;
            else
                right = right.next;

        }
        return pHead.next;
    }


    //combine
    public List<String> generateParenthesis(int n) {
        List<String> list = new ArrayList<>();

        int left = n;
        int right = n;

        help(left, right, "", list);
        return list;
    }

    void help(int left, int right, String pre, List<String> list) {
        if (left < 0 || right < 0 || left > right)
            return;
        if (left == 0 && right == 0) {
            list.add(pre);
        }
        help(left - 1, right, pre + "(", list);
        help(left, right - 1, pre + ")", list);
    }


    public void Perm(char list[], int k, int m, Set<String> result) {
        if (k == m) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i <= m; i++) {
                sb.append(list[i]);
            }
            if (isValid(sb.toString()))
                result.add(sb.toString());
        } else {
            for (int i = k; i <= m; i++) {
                // 从固定的数后第一个依次交换
                Swap(list, k, i);
                Perm(list, k + 1, m, result);
                // 这组递归完成之后需要交换回来
                Swap(list, k, i);
            }
        }

    }

    public void Swap(char[] list, int i, int j) {
        char t = list[i];
        list[i] = list[j];
        list[j] = t;
    }

    //23
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0)
            return null;
        if (lists.length == 1)
            return lists[0];
        ListNode head = new ListNode(-1);
        ListNode node = head;
        ListNode first = lists[0];
        for (int i = 1; i < lists.length; i++) {
            node.next = mergeTwoLists(first, lists[i]);
            first = node.next;
        }

        return head.next;
    }

    //24.
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null)
            return head;

        ListNode first = head;
        ListNode second = first.next;
        ListNode prev = null;
        ListNode result = second;
        while (first != null && second != null) {
            first.next = second.next;
            second.next = first;

            if (first.next.next == null)
                break;
            if (prev != null) {
                prev.next = second;
            }
            second = first.next.next;
            prev = first;
            first = first.next;

        }


        return result;
    }

    //25
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null)
            return head;
        ListNode first = head;
        ListNode second = head;
        ListNode prev = null;
        for (int i = 0; i < k - 1; i++) {
            second = second.next;
        }
        ListNode result = second;

        while (first != null && second != null) {

            while (first.next != second) {
                ListNode next = first.next;
                first.next = next.next;
                next.next = first;
            }
            if (prev != null)
                prev.next = second;
            for (int i = 0; i < k; i++) {
                if (second == null)
                    return result;
                second = second.next;
            }
            prev = first;
            first = first.next;
        }
        return result;

    }


    //
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;
        int curr = nums[0];
        int index = nums.length - 1;
        for (int i = 1; i <= index; i++) {
            if (nums[i] == curr) {
                back(nums, i, index);
                index--;
                curr = nums[i - 1];
                i--;
            } else {
                curr = nums[i];
            }
        }
        return index + 1;
    }

    void back(int[] nums, int start, int end) {
        for (int i = start; i <= end - 1; i++) {
            nums[i] = nums[i + 1];
        }
    }

    public int removeElement(int[] nums, int val) {
        if (nums == null || nums.length == 0)
            return 0;
        int index = nums.length - 1;
        for (int i = 0; i <= index; i++) {
            if (nums[i] == val) {
                back(nums, i, index);
                index--;
                i--;
            }
        }
        return index + 1;
    }

    public int strStr(String haystack, String needle) {
        if (haystack == null || needle == null)
            return 0;


        return haystack.indexOf(needle);
    }

    public int divide(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == -1 || divisor == 0)
            return Integer.MAX_VALUE;

        int flag = (dividend > 0 ? 1 : -1) * (divisor > 0 ? 1 : -1);
        long d1 = Math.abs((long) dividend);
        long d2 = Math.abs((long) divisor);
        long remain = d1;
        int result = 0;
        while (d1 >= d2) {
            int count = 0;
            while (d1 >= (d2 << count)) {
                count++;
            }
            remain = d1 - (d2 << (count - 1));
            d1 = d1 - (d2 << (count - 1));
            result += (1 << (count - 1));
        }
        return flag * result;
    }

    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> list = new ArrayList<Integer>();
        if (words == null || words.length == 0)
            return list;
        if (words[0].length() > s.length())
            return list;

        int length = words[0].length();
        for (String w : words) {
            if (w.length() != length)
                return list;
        }
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (String word : words) {
            map.put(word, map.get(word) == null ? 1 : map.get(word) + 1);
        }

        Map<String, Integer> currMap = new HashMap<String, Integer>();

        for (int i = 0; i < s.length(); i++) {
            int left = i;
            int right = i;
            while (right - left <= length * words.length && right + length < s.length()) {
                String curr = s.substring(right, right + length);
                if (map.get(curr) != null) {
                    currMap.put(curr, currMap.get(curr) == null ? 1 : currMap.get(curr) + 1);
                    right += length;
                    if (currMap.get(curr) > map.get(curr)) {
                        currMap.clear();
                        break;
                    }

                    if (right - left == length * words.length) {
                        list.add(left);
                        currMap.clear();
                        break;
                    }
                } else {
                    currMap.clear();
                    break;
                }

            }
        }
        return list;
    }


    public void nextPermutation(int[] nums) {
        if (nums == null || nums.length == 0)
            return;
        for (int i = nums.length - 1; i >= 1; i--) {
            if (nums[i - 1] >= nums[i]) {
                continue;
            } else {
                for (int j = nums.length - 1; j >= i; j--) {
                    if (nums[j] > nums[i - 1]) {
                        int temp = nums[i - 1];
                        nums[i - 1] = nums[j];
                        nums[j] = temp;
                        reverse(nums, i, nums.length - 1);
                        return;
                    }

                }


            }
        }

        reverse(nums, 0, nums.length - 1);

    }

    void reverse(int[] nums, int i, int j) {
        while (i <= j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
            i++;
            j--;
        }
    }


    public int longestValidParentheses(String s) {
        if (s == null || s.length() == 0)
            return 0;
        int max = 0;
        int start = 0;
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < s.length(); i++) {
            Character ch = s.charAt(i);
            if (ch == '(') {
                stack.push(i);
            } else if (ch == ')') {
                if (stack.isEmpty()) {
                    start++;
                } else {
                    stack.pop();
                    if (stack.isEmpty()) {
                        max = Math.max(max, i - start + 1);
                    } else {
                        max = Math.max(max, i - stack.peek());
                    }
                }
            }
        }


        return max;
    }


    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0)
            return -1;
        int end = nums.length - 1;
        int start = 0;
        while (start <= end) {
            int mid = (start + end) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < nums[end]) {
                if (target > nums[mid] && target <= nums[end])
                    start = mid + 1;
                else
                    end = mid - 1;
            } else {
                if (nums[start] > target && target >= nums[mid])
                    end = mid - 1;
                else
                    start = mid + 1;

            }
        }
        return -1;

    }

    public int binarySerach(int[] nums, int i, int j, int target) {
        int mid = (i + j) / 2;
        if (nums[mid] > target) {
            return binarySerach(nums, i, mid, target);
        } else if (nums[mid] < target) {
            return binarySerach(nums, mid, j, target);
        } else
            return mid;
    }

    public int[] searchRange(int[] nums, int target) {
        if (nums == null || nums.length == 0)
            return new int[]{-1, -1};
        int[] result = new int[2];
        int left = 0;
        int right = nums.length - 1;
        int s = -1;
        int e = -1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] == target) {
                s = mid;
                right = mid - 1;
            } else if (target > nums[mid])
                left = mid + 1;
            else
                right = mid - 1;
        }
        left = 0;
        right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] == target) {
                e = mid;
                left = mid + 1;
            } else if (target > nums[mid])
                left = mid + 1;
            else
                right = mid - 1;
        }


        return new int[]{s, e};
    }


    public int searchInsert(int[] nums, int target) {
        if (nums == null || nums.length == 0)
            return -1;
        int left = 0;
        int right = nums.length;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] == target)
                return mid;
            else if (target > nums[mid])
                left = mid + 1;
            else
                right = mid - 1;
        }
        return left;
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> list = new ArrayList<List<Integer>>();
        Set<List<Integer>> l = new HashSet<List<Integer>>();


        if (candidates == null || candidates.length == 0)
            return list;


        Set<List<Integer>> set = new HashSet<List<Integer>>();
        Arrays.sort(candidates);
        backtrack(set, new ArrayList<>(), candidates, target, 0);
        for (List<Integer> ll : set)
            list.add(ll);
        return list;
    }

    boolean backtrack(Set<List<Integer>> list, List<Integer> temp, int[] nums, int remain, int start) {
        if (remain < 0)
            return false;
        else if (remain == 0) {
            list.add(new ArrayList<>(temp));
            return false;
        } else {
            for (int i = start; i < nums.length; i++) {
                boolean flag;
                temp.add(nums[i]);
                flag = backtrack(list, temp, nums, remain - nums[i], i + 1);
                temp.remove(temp.size() - 1);
                if (!flag)
                    break;
            }
            return true;
        }
    }


    public int firstMissingPositive(int[] nums) {
        if (nums == null || nums.length == 0)
            return 1;
        Arrays.sort(nums);
        int start = 0;
        int curr = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] <= 0) {
                start++;
                continue;
            }
            if (nums[i] == curr || nums[i] - curr == 1) {
                curr = nums[i];
            } else {
                return curr + 1;
            }
        }
        return nums[nums.length - 1] >= 0 ? nums[nums.length - 1] + 1 : 1;
    }


    public int trap(int[] height) {
        if (height == null || height.length == 0)
            return 0;
        int max = 0;
        int result = 0;
        int[] dp = new int[height.length];
        for (int i = 0; i < height.length; i++) {
            dp[i] = max;
            max = Math.max(dp[i], height[i]);
        }
        max = 0;
        for (int i = height.length - 1; i >= 0; i--) {
            dp[i] = Math.min(max, dp[i]);
            max = Math.max(max, height[i]);
            if (dp[i] > height[i]) {
                result += dp[i] - height[i];
            }
        }
        return result;
    }


    public boolean isMatch1(String s, String p) {

        if (p.isEmpty()) return s.isEmpty();
        boolean first_match = (!s.isEmpty() &&
                (p.charAt(0) == s.charAt(0) || p.charAt(0) == '.') || p.charAt(0) == '*');

        if (p.length() >= 2 && p.charAt(1) == '*') {
            return (isMatch1(s, p.substring(1)) ||
                    (first_match && isMatch1(s.substring(1), p)));
        } else {
            return first_match && isMatch1(s.substring(1), p.substring(1));
        }


    }

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> list = new ArrayList<List<Integer>>();
        if (nums == null || nums.length == 0)
            return list;
        Set<List<Integer>> result = new HashSet<List<Integer>>();
        Perm1(nums, 0, nums.length - 1, result);
        for (List<Integer> l : result)
            list.add(l);
        return list;
    }


    public void Perm1(int list[], int k, int m, Set<List<Integer>> result) {
        if (k == m) {
            List<Integer> temp = new ArrayList<>();
            for (int i = 0; i <= m; i++) {
                temp.add(list[i]);
            }
            result.add(temp);
        } else {
            for (int i = k; i <= m; i++) {
                // 从固定的数后第一个依次交换
                Swap1(list, k, i);
                Perm1(list, k + 1, m, result);
                // 这组递归完成之后需要交换回来
                Swap1(list, k, i);
            }
        }

    }

    public void Swap1(int[] list, int i, int j) {
        int t = list[i];
        list[i] = list[j];
        list[j] = t;
    }

    public void rotate(int[][] matrix) {
        if (matrix == null || matrix.length == 0)
            return;
        int n = matrix.length;

        for (int i = 0; i < n / 2; i++) {
            for (int j = i; j < n - 1 - i; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n - j - 1][i];
                matrix[n - j - 1][i] = matrix[n - i - 1][n - j - 1];
                matrix[n - i - 1][n - j - 1] = matrix[j][n - i - 1];
                matrix[j][n - i - 1] = temp;
            }
        }
    }

    public int jump(int[] nums) {

        return 0;

    }


    public int evalRPN(String[] tokens) {
        if(tokens == null || tokens.length == 0)
            return 0;
        Stack<Integer> stack = new Stack<Integer>();
        for(int i=0;i<tokens.length;i++){
            String token = tokens[i];
            if("+".equals(token) || "-".equals(token) || "*".equals(token) || "/".equals(token)){
                int op2 = Integer.valueOf(stack.pop());
                int op1 = Integer.valueOf(stack.pop());
                int value = 0;
                switch (token){
                    case "+":
                        value = op1 + op2;
                        break;
                    case "-":
                        value = op1 - op2;
                        break;
                    case "*":
                        value = op1 * op2;
                        break;
                    case "/":
                        value = op1 / op2;
                        break;
                }
                stack.push(value);


            }else {
                stack.push(Integer.valueOf(tokens[i]));
            }
        }
        return stack.peek();
    }


    public int maxPoints(Point[] points) {
        if(points == null || points.length==0)
            return 0;
        if(points.length==1)
            return 1;
        int max = 1;
        for(int i=0;i<points.length;i++){
            HashMap<Double,Integer> map= new HashMap<Double,Integer>();
            int dup = 1;
            for(int j=0;j!=i&&j<points.length;j++){
                if(points[i].x==points[j].x && points[i].y==points[j].y){
                     dup++;
                }else {
                    if (points[i].x == points[j].x) {
                        if (map.get(Double.MAX_VALUE) != null)
                            map.put(Double.MAX_VALUE, map.get(Double.MAX_VALUE) + 1);
                        else
                            map.put(Double.MAX_VALUE, 2);
                    } else {
                        double vex = 1.0 * (points[j].y - points[i].y) / (points[j].x - points[i].x);
                        if (map.get(vex) != null)
                            map.put(vex, map.get(vex) + 1);
                        else
                            map.put(vex, 2);
                    }
                }
            }
            Set<Double> keys = map.keySet();
            if(keys.isEmpty())
                max = Math.max(max,dup);
            else {
                for (Double key : keys) {
                    max = Math.max(max, map.get(key) + dup -1);
                }
            }
        }
        return max;
    }

    public boolean sameLine(Point p1,Point p2,Point p3){
        double a ;
        double b ;
        if(p2.x == p1.x && p2.x == p3.x || p2.y==p1.y && p2.y==p3.y){
            return true;
        }
        if(samePoint(p1,p2) || samePoint(p2,p3) || samePoint(p1,p3))
            return true;
        a = 1.0*(p2.y-p1.y)/(p2.x-p1.x);
        b = p1.y-a*p1.x;
        return p3.y == a*p3.x + b;

    }

    public boolean samePoint(Point p1,Point p2){
        return p1.x == p2.x && p1.y==p2.y;
    }

    public ListNode sortList(ListNode head) {
        if(head == null || head.next == null)
            return head;
        ListNode mid = getMid(head);
        ListNode left = head;
        ListNode right = mid.next;
        mid.next = null;
        left  = sortList(left);
        right = sortList(right);
        return merge(left,right);
     }

    public ListNode merge(ListNode first,ListNode second){
            ListNode curr = new ListNode(-1);
            ListNode result = curr;
            while(first!=null && second!=null){
                if(first.val<=second.val) {
                    curr.next = first;
                    first = first.next;
                    curr = curr.next;
                }else {
                    curr.next = second;
                    second = second.next;
                    curr = curr.next;
                }
                if(first!=null)
                    curr.next = first;
                if(second!=null)
                    curr.next = second;
            }
            return result.next;
    }

    public ListNode getMid(ListNode head){
        ListNode p = head;
        ListNode first = head;
        ListNode last = head;
        while(last.next!=null && last.next.next!=null){
                first = p;
                last = p.next.next;
                p = p.next;
        }
        return first;

    }


    public ListNode insertionSortList(ListNode head) {
        if(head == null || head.next == null)
            return head;
        ListNode p = new ListNode(-1);
        p.next = null;
        ListNode result = p;
        while(head!=null){
            ListNode first = p.next;
            ListNode pre = p;
            if(first == null)
                p.next = new ListNode(head.val);
            else{
                while (first!=null && first.val<=head.val ){
                    pre = first;
                    first = first.next;
                }
                if(first==null){
                    pre.next = new ListNode(head.val);
                }else{
                    pre.next = new ListNode(head.val);
                    pre.next.next=first;
                }
            }
            head = head.next;
        }
        return result.next;
    }


    public ArrayList<Integer> postorderTraversal(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        if(root == null)
            return list;
        postorder(root,list);
        return list;
    }

    public void postorder(TreeNode root,ArrayList<Integer> list){
        if(root.left!=null)
            postorder(root.left,list);
        if(root.right!=null)
            postorder(root.right,list);
        list.add(root.val);
    }


    public ArrayList<Integer> preorderTraversal(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        if(root == null)
            return list;
        preorder(root,list);
        return list;
    }

    public void preorder(TreeNode root,ArrayList<Integer> list){
        list.add(root.val);
        if(root.left!=null)
            preorder(root.left,list);
        if(root.right!=null)
            preorder(root.right,list);

    }


    public void reorderList(ListNode head) {
        if(head==null || head.next==null || head.next.next==null)
            return ;
        ListNode first = head;
        ListNode curr = head;
        Stack<ListNode> stack = new Stack<ListNode>();
        while(first!=null){
            stack.push(first);
            first = first.next;
        }
        first = head;
        ListNode last = null;
        while(first != stack.peek() && first.next != stack.peek() && first!=last){
            ListNode tempNode = stack.pop();
            tempNode.next = first.next;
            first.next = tempNode;
            last = tempNode;
            if(first!=last)
                stack.peek().next = null;
            first = tempNode.next;
        }
    }

    public ListNode detectCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while(true){
            if(fast==null || fast.next == null)
                return null;
            fast = fast.next.next;
            if(fast == slow)
                break;
            fast = fast.next.next;
            slow = slow.next;
        }

        slow = head;
        while(slow != fast){
            slow = slow.next;
            fast = fast.next;
        }

        return slow;
    }

    public boolean hasCycle(ListNode head) {

        ListNode slow = head;
        ListNode fast = head;
        while(true){
            if(fast==null || fast.next == null)
                return false;
            fast = fast.next.next;
            slow = slow.next;
            if(fast==slow)
                return true;
        }
     }



    public List<String> wordBreak(String s, List<String> wordDict) {
        List<String> res = new ArrayList<>();
        if(s == null || s.length() == 0) return res;
        List<String> cur = new ArrayList<>();
        Set<String> dict = new HashSet<>(wordDict);
        dfs(s, 0, cur, res, dict);
        return res;
    }

    private void dfs(String s, int pos, List<String> cur, List<String> res,
                     Set<String> dict){
        if(pos == s.length()){
            String str = listToString(cur);
            res.add(str);
            return;
        }


        for(int i=pos; i<s.length(); i++){
            if(!dict.contains(s.substring(pos, i+1))) continue;
            cur.add(s.substring(pos, i+1));
            dfs(s, i+1, cur, res, dict);
            cur.remove(cur.size()-1);
        }
    }

    private String listToString(List<String> list){
        StringBuilder sb = new StringBuilder();
        //sb.append("\"");
        for(int i=0; i<list.size()-1; i++){
            sb.append(list.get(i));
            sb.append(" ");
        }
        sb.append(list.get(list.size()-1));
        //sb.append("\"");
        return sb.toString();
    }

    public RandomListNode copyRandomList(RandomListNode head) {
            if(head==null)
                return head;
        HashMap<RandomListNode,RandomListNode> map = new HashMap<RandomListNode,RandomListNode>();
        RandomListNode curr = head;
        RandomListNode res = new RandomListNode(-1);
        RandomListNode p = res;
        while(curr!=null){
            RandomListNode temp = new RandomListNode(curr.label);
            map.put(curr,temp);
            p.next = temp;
            p = p.next;
            curr=curr.next;
        }
        p = res.next;
        curr = head;
        while(p!=null){
            p.random = map.get(curr.random);
            p=p.next;
            curr=curr.next;
        }
        return res.next;
    }

    public int singleNumber3(int[] A) {
        int B[] = new int[32];
        int result = 0;
        for(int i=0;i<32;i++){
            B[i] = 0;
            for(int j=0;j<A.length;j++){
                if(((A[j] >> i) & 1)==1){
                    B[i] = B[i] + 1;
                }
            }
            B[i] = B[i] % 3;
            result = result | (B[i]<<i);
        }

        return result;

    }


    public int singleNumber(int[] A) {
        int res = 0;
        for(int i=0;i<A.length;i++){
            res = res ^ A[i];
        }
        return res;
        /*
        int[] B = new int[32];
        int result = 0;
        for(int i=0;i<32;i++){
            B[i] = 0;
            for(int j=0;j<A.length;j++){
                if(((A[j] >> i) & 1)==1){
                    B[i] = B[i] + 1;
                }
            }
            B[i] = B[i] % 2;
            result = result | (B[i]<<i);
        }
        return result;
        */
    }


    public int candy(int[] ratings) {
        if(ratings == null || ratings.length==0)
            return 0;
        if(ratings.length==1)
            return 1;
        int total = 0;
        int[] result = new int[ratings.length];
        for(int i=0;i<ratings.length;i++){
            if(i==0) {
                result[i] = 1;
                continue;
            }
            if(ratings[i]>ratings[i-1]){
                result[i]=result[i-1]+1;
            }else
                result[i] = 1;
        }
        for(int i=ratings.length-1;i>=1;i--){
            if(ratings[i-1]>ratings[i]){
                result[i-1]=Math.max(result[i-1],result[i]+1);
            }
        }
        for(int num:result)
            total+=num;
        return total;
    }


    public int canCompleteCircuit(int[] gas, int[] cost) {
            if(gas == null || cost == null )
                return 0;
        int total = 0;
        int index = 0;
        int station = 0;
        int l = gas.length;
        while(index<=gas.length-1){
            total = 0;
            station = 0;
            for(int i=index;i<=index+gas.length-1;i++){
                total = total + gas[i%l];
                if(total<cost[i%l]) {
                    break;
                }
                station ++ ;
                if(station==l)
                    return index;
                total = total - cost[i%l];
            }
            index++;
        }
        return -1;
    }

    /**
     * 最少切几刀,把字符串切成所有的子串都是回文
     * @param s
     * @return
     */
    public int minCut(String s) {
        if(s == null||s.length() == 0)
            return 0;
        int[] dp = new int[s.length()];
        dp[0] = 0;
        for(int i=1;i<s.length();i++){
            dp[i] = is_palindrome(s.substring(0,i+1))?0:i;
            for(int j=i;j>=1;j--){
                if(is_palindrome(s.substring(j,i+1))){
                    dp[i] = Math.min(dp[j-1]+1,dp[i]);
                }
            }
        }
        return dp[s.length()-1];
    }
    //判断回文串例程
    public boolean is_palindrome(String s)
    {
        int left = 0;
        int right = s.length()-1;
        while(left < right){
            if(s.charAt(left) != s.charAt(right))
                return false;
            left ++;
            right --;
        }
        return true;
    }


    public ArrayList<ArrayList<String>> partition(String s) {
        ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
        ArrayList<String> curr = new ArrayList<String>();
        if(s == null || s.length() == 0)
            return res;
        dfs(s,0,res,curr);
        return res;
    }

    public void dfs(String s,int pos,ArrayList<ArrayList<String>> res,ArrayList<String> curr){
        if(pos==s.length()){
            StringBuilder sb = new StringBuilder();
            if(curr!=null){
                for(String str:curr)
                    sb.append(str);
                if(sb.toString().equals(s)){
                    res.add(new ArrayList<>(curr));
                }
            }
        }

        for(int i=pos;i<s.length();i++){
            if(is_palindrome(s.substring(pos,i+1))){
                curr.add(s.substring(pos,i+1));
            }else{
                continue;
            }
            dfs(s,i+1,res,curr);
            curr.remove(curr.size()-1);
        }
    }


    public void solve(char[][] board) {
        if(board == null || board.length==0 || board[0].length == 0)
            return ;
        int cols = board[0].length;
        int rows = board.length;

        for(int i=0;i<rows;i++){
            dfs(board,i,0);
            dfs(board,i,cols-1);
        }
        for(int i=0;i<cols;i++){
            dfs(board,0,i);
            dfs(board,rows-1,i);
        }


        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                if(board[i][j]=='O')
                    board[i][j] = 'X';
            }
        }

        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                if(board[i][j]=='*')
                    board[i][j] = 'O';
            }
        }

    }

    void dfs(char[][] board,int row,int col){
        if(board[row][col] == 'O'){
            board[row][col] = '*';
            if(row>1)
                dfs(board,row-1,col);
            if(row<board.length-1)
                dfs(board,row+1,col);
            if(col>1)
                dfs(board,row,col-1);
            if(col<board[0].length-1)
                dfs(board,row,col+1);
        }
    }

    public int sumNumbers(TreeNode root) {
        if(root == null)
            return 0;
        List<Integer> list = new ArrayList<Integer>();
        List<Integer> curr = new ArrayList<Integer>();
        dfs(root,list,curr);
        int sum = 0;
        for(int num:list)
            sum += num;
        return sum;
    }

    void dfs(TreeNode root,List<Integer> list,List<Integer> curr){
        if(root.left== null && root.right==null){
            curr.add(root.val);
            StringBuilder sb = new StringBuilder();
            for(Integer ch : curr)
                sb.append(ch);
            list.add(Integer.valueOf(sb.toString()));
            return;
        }
        curr.add(root.val);
        if(root.left!=null) {
            dfs(root.left, list, curr);
            curr.remove(curr.size()-1);
        }
        if(root.right!=null) {
            dfs(root.right, list, curr);
            curr.remove(curr.size() - 1);
        }

    }


    /**
     *
     * Given an unsorted array of integers, find the length of the longest consecutive elements sequence.

     For example,
     Given[100, 4, 200, 1, 3, 2],
     The longest consecutive elements sequence is[1, 2, 3, 4]. Return its length:4.

     Your algorithm should run in O(n) complexity.
     * @param num
     * @return
     */
    public int longestConsecutive(int[] num) {
        if(num==null || num.length==0)
            return 0;
        HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();
        int max = 0;
        for(int i=0;i<num.length;i++)
            map.put(num[i],i);
        for(int i=0;i<num.length;i++){
            int left = num[i];
            int rifht = num[i];
            while(map.get(left)!=null || map.get(rifht)!=null){
                int maxLeft = map.get(left)==null?left+1:left;
                int maxRight = map.get(rifht)==null?rifht-1:rifht;
                max = Math.max(maxRight - maxLeft + 1,max);
                if(map.get(left)!=null){
                    left--;
                }
                if(map.get(rifht)!=null){
                    rifht++;
                }
                if(max == num.length)
                    return max;

            }
        }
        return max;
    }

    /**
     *
     * Given two words (start and end), and a dictionary, find all shortest transformation sequence(s) from start to end, such that:
     * Only one letter can be changed at a time
     * Each intermediate word must exist in the dictionary
     * For example,
     * Given:
     * start ="hit"
     * end ="cog"
     * dict =["hot","dot","dog","lot","log"]
     * @param start
     * @param end
     * @param dict
     * @return
     */
    public ArrayList<ArrayList<String>> findLadders(String start, String end, HashSet<String> dict) {
        ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> res2 = new ArrayList<ArrayList<String>>();
        ArrayList<String> curr = new ArrayList<String>();
        if(start==null || "".equals(start) || end==null || "".equals(end) || dict==null || dict.isEmpty() || start.length()!=end.length())
            return res;
        int min = Integer.MAX_VALUE;
        curr.add(start);
        dfs(start,end,dict,res,curr);
        for(int i=0;i<res.size();i++){
            min = Math.min(min,res.get(i).size());
        }
        for(int i=0;i<res.size();i++){
            if(res.get(i).size()==min)
                res2.add(res.get(i));
        }
        return res2;
    }

    void dfs(String start, String end, HashSet<String> dict,ArrayList<ArrayList<String>> res,ArrayList<String> curr){
        if(res.size()>0 && curr.size()>res.get(0).size()){
            return;
        }

        if(transer(start,end)){
            curr.add(end);
            res.add(new ArrayList<String>(curr));
            curr.remove(curr.size()-1);
            return;
        }


        for(String str:dict){
            if(curr.indexOf(str)!=-1){
                continue;
            }

            if(transer(str,start)){
                 curr.add(str);
            }else{
                continue;
            }
            dfs(str,end,dict,res,curr);
            curr.remove(curr.size()-1);
         }
    }

    boolean transer(String str,String str2){
        int count = 0;
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)!=str2.charAt(i))
                count++;
        }
        return count==1;
    }


    public boolean isPalindrome(String s) {
        if(s== null)
            return false;
        if("".equals(s.trim()))
            return true;
        int left = 0;
        int right = s.length()-1;
        while(left<=right && left<=s.length()-1 && right>=0){
            if(isAlphanumeric(s.charAt(left)) && isAlphanumeric(s.charAt(right))){
                if(!isMatch(s.charAt(left++),s.charAt(right--)))
                    return false;
            }else{
                if(!isAlphanumeric(s.charAt(left)))
                    left++;
                if(!isAlphanumeric(s.charAt(right)))
                    right--;
            }
        }
        return true;
    }

    boolean isAlphanumeric(char ch){
        return ch>='a' && ch<='z' || ch>='A' && ch<='Z' || ch>='0' && ch<='9';
    }

    boolean isMatch(char ch1,char ch2){
        return String.valueOf(ch1).equalsIgnoreCase(String.valueOf(ch2));
    }

    public int maxPathSum(TreeNode root) {
        if(root==null)
            return 0;
        Map<TreeNode,Integer> map = new HashMap<TreeNode,Integer>();


        return 0;

    }



    int leftMax(TreeNode root){
        int i = 0;
        while(root.left!=null){
            i++;
        }
        return i;
    }

    int rightMax(TreeNode root){
        int i = 0;
        while(root.right!=null){
            i++;
        }
        return i;
    }
}


/**
 * 表示一个用户行为，包含type，timestamp， info信息
 */

  class Point {
      int x;
      int y;
      Point() { x = 0; y = 0; }
      Point(int a, int b) { x = a; y = b; }
  }


class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
}

 class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }


 class RandomListNode {
      int label;
      RandomListNode next, random;
      RandomListNode(int x) { this.label = x; }
  };