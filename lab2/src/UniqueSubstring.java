public class UniqueSubstring {

    public static String UniqueSubsting(String str) {

        int[] charIndex = new int[256];

        for (int i = 0; i < 256; i++) {
            charIndex[i] = -1;
        }

        int left = 0, maxLength = 0;
        String maxSubstring = "";

        for (int right = 0; right < str.length(); right++) {
            char currentChar = str.charAt(right);

            if (charIndex[currentChar] >= left) {
                left = charIndex[currentChar] + 1;
            }
            charIndex[currentChar] = right;

            if (right - left + 1 > maxLength) {
                maxLength = right - left + 1;
                maxSubstring = str.substring(left, right + 1);
            }
        }
        return maxSubstring;
    }


    public static void main(String[] args) {

        String test = "aaborba";
        System.out.println(UniqueSubsting(test));
    }
}
