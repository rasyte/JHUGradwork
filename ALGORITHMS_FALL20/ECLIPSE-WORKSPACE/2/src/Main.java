
/* Rasa Rasiulyte, 425.218.9263
 * Programming Assignment 3
 * https://blackboard.jhu.edu/bbcswebdav/pid-8595515-dt-content-rid-95312797_2/courses/EN.605.621.84.FA20/prog2_FA20.pdf
 * 
 * *****************************************************************************************************
 * PART A: Give an efficient algorithm that takes strings s, x, and y and decides if s is an interweaving of
		   x and y. Derive the computational complexity of your algorithm.
		   
   PSEUDOCODE and COMPUTATIONAL COMPLEXITY
   *************************************************************************************************
   * To check if string s interweaving of string x and y create function isInterweaved(s, x, y) that takes three inputs
   * s - for sequence of interweaved signals, and x, y for sequence of signals that are emited by each ship
   * This function will return boolean that indicates if strings interleaving or not.
   * To determine interweaving it will recursively call rcrsInterweaving procedure that is responsible for 
   * checking if there are any substrings of x and y in the s sequence and if it found, will return true.
   * The recursive function will terminate once x and y both found in sequence s, and all symbols been processed.
   * The recursive function worst case is when all symbols in s were visited, which results in O(S).
   * The isInterweaved function contains double loop that helps to iterate over x and y. The time complexity
   * for this function O(XY). Given both functions, the overall TIME COMPLEXITY of this algorithm will be O(XYS) per permutation.
   
   ISINTERWEAVED(s, x, y):
   	FOR i = 0 to x.length
    	FOR j = 0 to y.length
        	String s1, s2, s3 //substrings of s
            IF RCRSINTERWEAVE(substring, x, y, x, y) 
            	return TRUE
   RCRSINTERWEAVE(s, x', y', x, y, xPresent, yPresent):
   	IF x'.length == 0 
    	xPresent = TRUE
        x' = x
    IF y'.length == 0 
    	yPresent = TRUE
        y'=y
    IF s.lenght == 0 AND xPresent AND yPresent
    	return TRUE
    IF s.length == 0 
    	RETURN FALSE
     
    RETURN 
    	(s == x' AND RCRSINTERWEAVE(s1...sn, x'...x, y', x, y, xPresent, yPresent)) OR 
        (s == y' AND RCRSINTERWEAVE(s1...sn, x', y'...y, x, y, xPresent, yPresent)        

   *************************************************************************************************
   
   Part B:  Implement your algorithm and test its run time to verify your complexity analysis. Remember
			that CPU time is not a valid measure for testing run time. You must use something such as the number
			of comparisons.
 * 
 */

public class main 
{
  	//counter to test the complexity analysis that is tracking comparisons that are being make in the code 
	static public int cComparison = 0; 
	
	public static void main(String[] args) 
	{
		System.out.println("Programming Assignment 2" );
		System.out.println("By Rasa Rasiulyte" );
		System.out.println("Based on the analysis, the time complexity for this algorithm is: O(SXY)."  + "\n");
      
		test("101", "0", "100010101");
       	test("101", "101", "101101101101");
       	test("101", "0", "x100010101y");
       	test("101", "0", "0");
     	test("0", "101", "0");
       	test("101", "0", "x676zz");
	}
  
	/**
	* @desc creates different permutations of s to determine if string is interweaved of x and y
	* @param a - x sequence of signals
    * @param b - y sequence of signals
    * @param c - sequence of signals (s) to examine if it is interweaving of x and y
	* @return TRUE is sequence is interweaving of x and y 
	 */
	private static boolean isInterweaved(String a, String b, String c) 
	{
		//check that the size of string s legit
		if (a.length() < (b.length() + c.length()))
		{
          	cComparison++;
			return false;
		}
		
		for(int i = 0; i <= b.length(); i++)
		{
			for(int j = 0; j <= c.length(); j++) 
			{
				String s1 = a.substring(i);
				String s2 = a.substring(0, a.length() - j);
				
				if (rcrsInterweave(s1, b, c, b, c, false, false) || rcrsInterweave(s2, b , c, b, c, false, false)) 
				{
					return true;
				}
				else if(i < a.length() - j) 
				{
                  	String s3 = a.substring(i, a.length() - j);
					if(rcrsInterweave(s3, b, c, b, c, false, false)) 
					{
						 return true;
					}
				}
			}
		}
		cComparison++;	
		return false;
	}
	/**
	* @desc Checks for repetionion of the substrings b and c  in the sequence a.
	* @param a - sequence to examine 
    * @param b - substring of b2
    * @param c - substring of c2
    * @param b2 - original b value
    * @param c2 - original c value
    * @param bPresent, cPresent - tells if substring within a
	* @return TRUE if sequence a contains repetition of substrings 
	 */
	private static boolean rcrsInterweave(String a, String b, String c, String b2, String c2, boolean bPresent, boolean cPresent) 
	{		
		//CASE: all characters in b2 have been found within a
		if (b.length() == 0) 
		{
			cComparison++;
			bPresent = true;
			b = b2;
		}
		//CASE: all characters in c2 have been found within a
		if(c.length() == 0) 
		{
			cComparison++;
			cPresent = true;
			c = c2;
		}	
		//CASE: a empty and interleaving found
		if (a.length() == 0 && bPresent && cPresent) 
		{
			cComparison++;
			return true;
		}
		//CASE: a empty 		
		if (a.length() == 0) 
		{
			cComparison++;
			return false;
		}

		return ((a.charAt(0) == b.charAt(0) && rcrsInterweave(a.substring(1), b.substring(1), c, b2, c2, bPresent, cPresent)) || 
				(a.charAt(0) == c.charAt(0) && rcrsInterweave(a.substring(1), b, c.substring(1), b2, c2, bPresent, cPresent)));
	}
	
	/**
	 * @desc Function to run test case
	 * @param String A - x, String B - y, String C - s
	*/ 
	static void test(String A, String B, String C)
	 {
		int X = A.length();
		int Y = B.length();
		int S = C.length();
		int estimatedComparisons = 3*(X*Y*S);
				
			if(isInterweaved(C, A, B))
			{
				//Calculate and print estimated number of comparisons based on algorithms complexity O(XYC) 				
				System.out.println("Test Case: " + C + " is an interweaving of " + A + " and " + B);
			}
		    else
		    {
			   System.out.println("Test Case: " + C + " is NOT an interweaving of " + A + " and " + B);
		    }
		
		System.out.println("Expected number of comparisons: <" + estimatedComparisons  + " \nActual number of comparisons: " + cComparison+"\n");
		cComparison = 0; 
	}
}
