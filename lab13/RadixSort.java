import java.util.Arrays;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1
     * length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        int maxLen = 0;
        for (String s : asciis) {
            if (s.length() > maxLen) {
                maxLen = s.length();
            }
        }

        String[] res = Arrays.copyOf(asciis, asciis.length);
        for (int i = maxLen - 1; i >= 0; i--) {
            sortHelperLSD(res, i);
        }
        return res;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * 
     * @param asciis Input array of Strings
     * @param index  The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        int R = 256;
        int[] counts = new int[R + 1];
        for (String item : asciis) {
            int c = charAtOrMinChar(index, item);
            counts[c]++;
        }
        int[] starts = new int[R + 1];
        int pos = 0;
        for (int i = 0; i < R + 1; i++) {
            starts[i] = pos;
            pos += counts[i];
        }
        String[] sorted = new String[asciis.length];
        for (int i = 0; i < asciis.length; i++) {
            String item = asciis[i];
            int c = charAtOrMinChar(index, item);
            int place = starts[c];
            sorted[place] = item;
            starts[c]++;
        }
        for (int i = 0; i < asciis.length; i++) {
            asciis[i] = sorted[i];
        }
    }

    private static int charAtOrMinChar(int index, String item) {
        if (index < item.length() && index >= 0) {
            return item.charAt(index) + 1;
        } else {
            return 0;
        }
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the
     * sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start  int for where to start sorting in this method (includes String
     *               at start)
     * @param end    int for where to end sorting in this method (does not include
     *               String at end)
     * @param index  the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // 基本情况：如果要排序的范围小于等于一个元素，则无需排序。
        if (end - start <= 1) {
            return;
        }

        // 确定当前索引处字符范围的范围（ASCII值）。
        int R = 256; // 假设使用ASCII字符集，根据字符集大小调整
        int[] counts = new int[R + 2];

        // 计算当前范围内每个字符的出现次数。
        for (int i = start; i < end; i++) {
            int c = (index < asciis[i].length()) ? (int) asciis[i].charAt(index) + 2 : 1;
            counts[c]++;
        }

        // 计算每个字符的起始位置。
        int[] starts = new int[R + 2];
        int pos = 0;
        for (int i = 0; i < R + 2; i++) {
            starts[i] = pos;
            pos += counts[i];
        }

        // 根据当前字符索引执行元素重新排列。
        String[] sorted = new String[end - start];
        for (int i = start; i < end; i++) {
            int c = (index < asciis[i].length()) ? (int) asciis[i].charAt(index) + 1 : 0;
            int place = starts[c];
            sorted[place] = asciis[i];
            starts[c]++;
        }

        // 将排序后的数组复制回原始数组。
        System.arraycopy(sorted, 0, asciis, start, end - start);

        // 递归地对每个子范围进行下一个字符索引的排序。
        for (int i = 0; i < R; i++) {
            sortHelperMSD(asciis, start + starts[i], start + starts[i + 1], index + 1);
        }
    }


    public static void main(String[] args) {
        String[] asciis = new String[] {"56", "112", "94", "4", "9", "82", "394", "80" };
        String[] res = RadixSort.sort(asciis);
        for (String s : res) {
            System.out.print(s + " ");
        }

        System.out.println();

        String[] asciis2 = new String[] {"  ", "      ", "    ", " " };
        String[] res2 = RadixSort.sort(asciis2);
        for (String s : res2) {
            System.out.print(s + ",");
        }
    }

}
