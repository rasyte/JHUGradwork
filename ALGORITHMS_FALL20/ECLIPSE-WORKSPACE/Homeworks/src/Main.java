import static java.lang.Math.sqrt;
import static java.lang.Math.pow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.Level;


//Assumption: all points have valid, unique values (x,y)
//Assumption: all points are doubles 
/**
 * @author Rasa Rasiulyte
 * This program takes an array of Point objects, where each point defined by x,y coordinates. 
 * And returns the m number of closest pairs of points in the given array.
 * Input: Point objPoints, int m
 * Output: Sorted objPointsPairs array of size m, which contains closest pairs of points with x,y coordinates in acceding order. 
 * Assumptions: all input points are unique. Duplicate set of points will considered as valid points with distance of zero. 
 */

public class Main 
{
	private static Logger log = Logger.getLogger("tracing");
	
	public static void main(String[] args) 
	{	
		Scanner intScan = null;
		Scanner doubleScan = null; 
		Point<Double> Point1 = null; 
		Point<Double> Point2 = null; 
		
	    //INPUT for m
		int m = 0;
		
		//INPUT for P
		try
		{
			intScan = new Scanner(System.in);
	        System.out.println("Enter number of closest point pairs you want to find:");
	        m = intScan.nextInt();
	        
			doubleScan = new Scanner(System.in);
	        System.out.println("Enter your array of x y coordinates for each point separated by spaces. (Ex: 2 2 1 4):");
	        String in = doubleScan.nextLine();
		
	        //create array from the input
	        ArrayList<Double> input = new ArrayList<Double>();
	        String[] num = in.split(" ");
		
	        for (int i = 0; i < num.length; i++)
	        {
	            input.add(Double.valueOf(num[i]));
	        }
	         int countCoordinates = input.size();
	         
	         if(m > (countCoordinates*(countCoordinates-1))/2)
	         {
	        	 throw new Exception("Invalid m input. You entered " + m + " number of closets pairs to find. This value is larger than number of possible premutations of the point pairs in the array. ");
	         }
	         
	         int numPoints = countCoordinates/2;
	         
	         //ArrayList<Double> coordinates = input;        
	         Point<Double>[] objPoints = new Point[numPoints];
	         
	         if (countCoordinates%2 ==0 ) //each point should have 2 coordinates, so number needs to be even
	         	{
	          
	         		int pointCounter = countCoordinates/2;
	 				for (int i = 0; i < pointCounter; i++)
	         		{
	         			int x = 2 * i; 
	         			int y = x + 1;
	         			objPoints[i] = new Point<Double> (input.get(x), input.get(y));  			
	         		}
	         	}
	         else
	         	{
	        	 	throw new Exception(" You entered " + countCoordinates+ " x,y coordinates. It has to be an even number.");

	         	}
	         
	     	//initialize pintsPairs array here for values for coordinates and distance	 		
	 		int cPairs = (numPoints*(numPoints -1))/2; //will be n(n-1)/2 count of pairs, cPairs		
	 		PointPairs[] objPointPairs = new PointPairs[cPairs]; 
	 		populatePointPairsArray(objPoints, Point1, Point2, objPointPairs);
			Sorts.sort(objPointPairs);
			printSortedArrayOutput(m, objPointPairs);
		}
		catch (InputMismatchException e)
		{
			System.out.println("Exiting program due to invalid input exception: " + e.toString());
			System.exit(0);
		}
		catch(Exception e)
		{
			System.out.println("Exiting program due to: " + e.toString());
			System.exit(0);
		}
	}

	/**
	 * Print out output array 
	 * @param m number of items to print
	 * @param objPointPairs array of sorted points pairs and distances
	 */
	
	private static void printSortedArrayOutput(int m, PointPairs[] objPointPairs) {
		System.out.println ("Shortest distances between " + m + " pairs are following: ");
		for (int z = 0; z < m; z++)
		{	
			System.out.printf("Distance = %.2f", objPointPairs[z].getDistance());
			System.out.println( "\n" + 	"	Point (x = "+ objPointPairs[z].getP1().getX() + ", y = " + objPointPairs[z].getP1().getY() + ")");
			System.out.println( "	Point (x = "+ objPointPairs[z].getP2().getX() + ", y = " + objPointPairs[z].getP2().getY() + ")" );
		}
	}
	
	/**
	 * The populatePointPairsArray method creates array with all pairs of points and their distances. 
	 * @param objPoints
	 * @param Point1
	 * @param Point2
	 * @param objPointPairs
	 */
	
	private static void populatePointPairsArray(Point<Double>[] objPoints,
			Point<Double> Point1, Point<Double> Point2, PointPairs[] objPointPairs) 
	{
		double distance;
		
		int z = 0; 
		int k = 0; //counter for pointPairs
		for(int i = 0; i < objPoints.length; i++) 
		{
			z++;
			for (int j = i + 1; j < objPoints.length; j++)
			{	
				distance = computeDistance(objPoints[i].getX(), objPoints[i].getY(), objPoints[j].getX(), objPoints[j].getY());
				Point1 = new Point<>(objPoints[i].getX(), objPoints[i].getY());
				Point2 = new Point<>( objPoints[j].getX(), objPoints[j].getY());
				objPointPairs[k] = new PointPairs(distance, Point1, Point2);
				k++; 
				z++;
			}
		}
		
		log.info("***TRACING Estimated Time Complexity: O(n^2) " + "\n" +
		"ACTUAL LOC executed: " + z + "\n" +
		"EXPECTED LOC: < " + (int)Math.pow(objPoints.length, 2));
	}
	
	/**
	 * The computeDistance method computes distance between two given points.
	 * @param point_x1 x coordinate of point1.
	 * @param point_y1 y coordinate of point1.
	 * @param point_x2 x coordinate of point2.
	 * @param point_y2 y coordinate of point2.
	 * @return The Euclidean distance between two points
	 */
	
	public static double computeDistance(double point_x1,  double point_y1, double point_x2, double point_y2) 
	{
		double dist = 0;
		dist = (double)sqrt( (pow((point_x1 - point_x2),2)) + (pow((point_y1 - point_y2),2)));
		return dist; 
	}

} 
