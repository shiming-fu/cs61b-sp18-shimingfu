package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        int[] numInBucket = new int[M];
        int N = oomages.size();
        for (Oomage s : oomages) {
            int bucketNumber = (s.hashCode() & 0x7FFFFFFF) % M;
            numInBucket[bucketNumber] += 1;
        }
        for (int bucketsize : numInBucket) {
            if (bucketsize > N / 2.5 || bucketsize < N / 50) {
                return false;
            }
        }
        return true;
    }
}
