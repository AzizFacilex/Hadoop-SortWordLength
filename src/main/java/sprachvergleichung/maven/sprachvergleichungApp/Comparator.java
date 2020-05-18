package Hadoop.Sprachvergleichung;

import java.util.Arrays;
import org.apache.hadoop.io.WritableComparator;

public class Comparator extends WritableComparator {

	@Override
	public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {

		byte firstKeyBytes[] = Arrays.copyOfRange(b1, s1 + 1, l1 + s1 + 1);
		String firstKey = new String(firstKeyBytes);
		int lengthOfFirstWord = firstKey
				.substring(firstKey.indexOf("-") + 2, firstKey.lastIndexOf("-") - 1).length();

		byte secondKeyBytes[] = Arrays.copyOfRange(b2, s2 + 1, l2 + s2 + 1);
		String secondKey = new String(secondKeyBytes);
		int lengthOfSecondWord = secondKey
				.substring(secondKey.indexOf("-") + 2, secondKey.lastIndexOf("-") - 1).length();
		
		return Integer.compare(lengthOfFirstWord, lengthOfSecondWord) * (-1);

	}
}