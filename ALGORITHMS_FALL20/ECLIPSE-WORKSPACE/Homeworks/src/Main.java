import static java.lang.Math.sqrt;
import static java.lang.Math.pow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

//Assumption: all points have valid, unique values (x,y)
//Assumption: all points are doubles 
/**
 * 
 * @author Rasa Rasiulyte
 * This program takes an array of Point objects, where each point defined by x,y coordinates. 
 * And returns the m number of closest pairs of points in the given array.
 * Input: Point objPoints, int m
 * Output: Sorted objPointsPairs array of size m, which contains closest pairs of points with x,y coordinates in acceding order. 
 */

public class Main 
{
	public static void main(String[] args) 
	{	
		//TRACING: Variables used to determine time for a function call
		long currentTime = System.nanoTime();
		long previousTime;
		long elapsedTime = 0;
		
		//TRACING: Record time before program run for tracing: BEGIN
		previousTime = currentTime;
				
	    //INPUT
		int m = 5; 
		int numberOfCoordinates = 5; 											// TODO: change to input from user instead of hardcoded 
		
		/**
		Point<Double>[] objPoints = new Point[numberOfCoordinates]; 			// TODO: change to input of array to be from user instead of hardcoded
		objPoints[0] = new Point<Double> (5.1, 5.0); 							// TODO: must be formated as doubles 
		objPoints[1] = new Point<Double> (13.0, 15.0);
		objPoints[2] = new Point<Double> (-3.0, 5.0);
		objPoints[3] = new Point<Double> (13.1, 15.0);
		objPoints[4] = new Point<Double> (0.0, 0.0);
		*/
		
		//INPUT from console 
		Scanner scan = new Scanner(System.in);
        System.out.println("Enter your array of x y coordinates for each point separated by spaces. (Ex: 2.0 4.8 3.1 0.0):");
        String in = scan.nextLine();
        
        //create array from the input
        ArrayList<Double> input = new ArrayList<Double>();
        String[] num = in.split(" ");
        for (int i = 0; i < num.length; i++)
        {
            input.add(Double.valueOf(num[i]));
        }
         int countCoordinates = input.size();
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
        			System.out.println("adding " + input.get(x) + " and " + input.get(y) + " to the point array.");        			
        		}
        	}
        else
        	{
        		System.out.println("Enter even number of values;  x,y coordinates needs to be defined for each point.");
        	}
        //System.out.println("objPoints.length, number of coordinates:  " + objPoints.length);
       
        
		Point<Double> Point1 = null; 
		Point<Double> Point2 = null; 
		
		//initialize pintsPairs array here for values for coordinates and distance
		
		int cPairs = (numPoints*(numPoints -1))/2; //will be n(n-1)/2 count of pairs, cPairs		
		PointPairs[] objPointPairs = new PointPairs[cPairs]; 
		
		//TRACING: record time before and after populatePointPairsArray, step 1-3 for tracing
		previousTime = currentTime;
		populatePointPairsArray(objPoints, Point1, Point2, objPointPairs);
		currentTime = System.nanoTime();
		//TRACING: Compute and print elapsed time in nanoseconds
		elapsedTime = (currentTime - previousTime);
		System.out.println("TRACING STEP 1 TO 3: Populating PointPairsArray method computed in " + elapsedTime + " nanoseconds.");
		
		//OUTPUT sorted m PointPairs
		previousTime = currentTime;
		Arrays.sort(objPointPairs);
		currentTime = System.nanoTime();
		//TRACING: Compute and print elapsed time in nanoseconds
		elapsedTime = (currentTime - previousTime);
		System.out.println("TRACING STEP 4: PointPairsArray sort method computed in " + elapsedTime + " nanoseconds.");
		
		previousTime = currentTime;
		printSortedArrayOutput(m, objPointPairs);
		//TRACING: Compute and print  elapsed time in nanoseconds
		elapsedTime = (currentTime - previousTime);
		System.out.println("TRACING STEP 5: Print sorted array output computed in " + elapsedTime + " nanoseconds.");
		
		//TRACING: Record time after program run
		currentTime = System.nanoTime();
		//TRACING: Compute and print elapsed time in nanoseconds
		elapsedTime = (currentTime - previousTime);
		System.out.println("TRACING END OF THE PROGRAM: Program completed in " + elapsedTime + " nanoseconds.");
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
			System.out.println( "Distance = " + objPointPairs[z].getDistance());						
			System.out.println( "	Point (x = "+ objPointPairs[z].getP1().getX() + ", y = " + objPointPairs[z].getP1().getY() + ")");
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
		int k = 0; //counter for pointPairs
		for(int i = 0; i < objPoints.length; i++) 
		{
			for (int j = i + 1; j < objPoints.length; j++)
			{	
				distance = computeDistance(objPoints[i].getX(), objPoints[i].getY(), objPoints[j].getX(), objPoints[j].getY());
				Point1 = new Point<>(objPoints[i].getX(), objPoints[i].getY());
				Point2 = new Point<>( objPoints[j].getX(), objPoints[j].getY());
				objPointPairs[k] = new PointPairs(distance, Point1, Point2);
				k++; 
			}
		}
	}
	
	/**
	 * The computeDistance method computes distance between two given points.
	 * @param point_x1 x coordinate of point1.
	 * @param point_y1 y coordinate of point1.
	 * @param point_x2 x coordinate of point2.
	 * @param point_y2 y coordinate of point2.
	 * @return The Euclidean distance between two points
	 */
	
	public static double computeDistance(double point_x1,  double point_y1, double point_x2, double point_y2) {
		
		double dist = 0;
		dist = (double)sqrt( (pow((point_x1 - point_x2),2)) + (pow((point_y1 - point_y2),2)) );
		return dist; 
	}
} 
