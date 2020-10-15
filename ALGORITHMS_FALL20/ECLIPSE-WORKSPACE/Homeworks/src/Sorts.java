import java.util.Arrays;
import java.util.Random;
import java.util.logging.Logger; 

public class Sorts 
{
	static int z = 0; 
	static int e = 0; 
	
	private static Logger log = Logger.getLogger("tracing");
	
	 /**
     * Sorts the specified array of objects into ascending order, according
     * to the {@linkplain Comparable natural ordering} of its elements.
     * All elements in the array must implement the {@link Comparable}
     * interface.  Furthermore, all elements in the array must be
     * <i>mutually comparable</i> (that is, {@code e1.compareTo(e2)} must
     * not throw a {@code ClassCastException} for any elements {@code e1}
     * and {@code e2} in the array).
     *
     * <p>This sort is guaranteed to be <i>stable</i>:  equal elements will
     * not be reordered as a result of the sort.
     *
     * <p>Implementation note: This implementation is a stable, adaptive,
     * iterative mergesort that requires far fewer than n lg(n) comparisons
     * when the input array is partially sorted, while offering the
     * performance of a traditional mergesort when the input array is
     * randomly ordered.  If the input array is nearly sorted, the
     * implementation requires approximately n comparisons.  Temporary
     * storage requirements vary from a small constant for nearly sorted
     * input arrays to n/2 object references for randomly ordered input
     * arrays.
     *
     * <p>The implementation takes equal advantage of ascending and
     * descending order in its input array, and can take advantage of
     * ascending and descending order in different parts of the same
     * input array.  It is well-suited to merging two or more sorted arrays:
     * simply concatenate the arrays and sort the resulting array.
     *
     * <p>The implementation was adapted from Tim Peters's list sort for Python
     * (<a href="http://svn.python.org/projects/python/trunk/Objects/listsort.txt">
     * TimSort</a>).  It uses techniques from Peter McIlroy's "Optimistic
     * Sorting and Information Theoretic Complexity", in Proceedings of the
     * Fourth Annual ACM-SIAM Symposium on Discrete Algorithms, pp 467-474,
     * January 1993.
     *
     * @param a the array to be sorted
     * @throws ClassCastException if the array contains elements that are not
     *         <i>mutually comparable</i> (for example, strings and integers)
     * @throws IllegalArgumentException (optional) if the natural
     *         ordering of the array elements is found to violate the
     *         {@link Comparable} contract
     */
	
	public static void sort( Object[] a )
	{
		Object[] aux = a.clone();
		mergeSort(aux, a, 0, a.length, 0);
		traceing(e);
	}
	
	private static final int INSERTIONSORT_THRESHOLD = 7;
	private static void mergeSort(Object[] src, Object[] dest, int low, int high, int off) 
	{
		// TRACING: calculate value of n log(n) and set to e 
		e = (int) (Math.log(src.length) / Math.log(2))* src.length;		
		//int z = 0; 
		
		int length = high - low;
	 
		// Insertion sort on smallest arrays
		if (length < INSERTIONSORT_THRESHOLD) 
		{
			for (int i=low; i<high; i++)
			{
				for (int j=i; j>low && ((Comparable) dest[j-1]).compareTo(dest[j])>0; j--)
					{
						swap(dest, j, j-1);
						z++; 
					}
			}
			return;
		}
		
		// Recursively sort halves of dest into src
		int destLow  = low;
		int destHigh = high;
		low  += off;
		high += off;
		int mid = (low + high) >>> 1;
		mergeSort(dest, src, low, mid, -off);
		mergeSort(dest, src, mid, high, -off);
		
		
		// If list is already sorted, just copy from src to dest.  This is an
		// optimization that results in faster sorts for nearly ordered lists.
		if (((Comparable)src[mid-1]).compareTo(src[mid]) <= 0) 
		{
			System.arraycopy(src, low, dest, destLow, length);
			return;
		}
		
		// Merge sorted halves (now in src) into dest
		for(int i = destLow, p = low, q = mid; i < destHigh; i++) 
			{
				if (q >= high || p < mid && ((Comparable)src[p]).compareTo(src[q])<=0)
				{
					dest[i] = src[p++];
					z++;
				}
				else
				{
					dest[i] = src[q++];
					z++;
				}
			}
		
		 }

	public static void traceing(int e) {
		log.info("***TRACING Estimated Time Complexity: O(n log(n)) " + "\n" + 
		"ACTUAL LOC executed: " + z + "\n" +
		"EXPECTED LOC: < " + e);
	}
	
	  /**
	  * Swaps x[a] with x[b].
	  */
	
	  private static void swap(Object[] x, int a, int b) 
	  {
	      Object t = x[a];
	      x[a] = x[b];
	      x[b] = t;
	  }
}
