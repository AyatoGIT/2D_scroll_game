public class Location
{
  private int row;
  private int col;
  
  public Location(int r, int c)//constructor that has 2 instance variables 
  {
    row = r;
    col = c;
  }
  
  public int getRow()
  {
    return row;
  }
  
  public int getCol()
  {
    return col;
  }
  
  public boolean equals(Location otherLoc)
  {
    return row == otherLoc.getRow() && col == otherLoc.getCol();
  }
  
  public String toString()
  {
    return "(" + row + ", " + col + ")";
  }
}