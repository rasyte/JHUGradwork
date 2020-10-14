public class PointPairs implements Comparable<PointPairs>
{
	private Point point1;
	private Point point2;
	private double distance;
	
	/**
	 * Constructor
	 * @param d The distance between point1 and point2 in the pair
	 * @param p1 The point1 in the pair
	 * @param p2 The point2 in the pair
	 */
	
	public PointPairs(double d, Point p1, Point p2)
	{
		point1 = p1;
		point2 = p2;
		distance = d;
	}
	
	/**
	 * The setDistance method sets the distance.
	 * @param d The value for the distance.
	 */
	
	public void setDistance(double d)
	{	
		distance = d;
	}
	
	/**
	 * The setP1 method sets the Point1.
	 * @param p1 The value for the Point1.
	 */
	
	public void setP1(Point p1)
	{	
		point1 = p1;
	}
	
	/**
	 * The setP2 method sets the Point2.
	 * @param p2 The value for the Point2.
	 */
	
	public void setP2(Point p2)
	{	
		point2 = p2;
	}
	
	/**
	 * The getDistance method returns the distance.
	 * @return The value of the distance.
	 */
	
	public double getDistance()
	{	
		return distance;
	}
	
	/**
	 * The getP1 method returns Point1.
	 * @return The value of the Point1.
	 */
	public Point getP1()
	{	
		return point1;
	}
	
	/**
	 * The getP2 method returns the Point2.
	 * @return The value of the Point2.
	 */
	
	public Point getP2()
	{	
		return point2;
	}
	
	/**
	 * The compareTo method compares distances.
	 * @param comparePairs PointPairs to be compared.
	 * @return The value of difference between distances of two point pairs being compared.
	 */
	
	@Override
	public int compareTo(PointPairs comparePairs) 
	{
		double compareDistance = ((PointPairs)comparePairs).getDistance();
		return  (int) (this.distance-compareDistance);
	}
}
