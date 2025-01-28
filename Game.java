public class Game {
  private Grid grid;
  private int userRow;
  private int userCol;
  private int msElapsed;
  private int timesGet;
  private int timesAvoid;
  private int timesJumpOn;
  
  public Game() {
    grid = new Grid(7, 15);
    userRow = 6;
    userCol = 0;
    msElapsed = 0;
    timesGet = 0;
    timesAvoid = 0;
    timesJumpOn = 0;
    updateTitle();
    grid.setImage(new Location(userRow, 0), "images/Mario.gif");
  }
  
  public void play() {
    while (!isGameOver()) {
      grid.pause(100);
      handleKeyPress();
      if (msElapsed % 300 == 0) {
        scrollLeft();
        populateRightEdge();
      }
      updateTitle();
      msElapsed += 100;
    }
    System.out.println();
  }
  
  public void handleKeyPress(){
      int key = grid.checkLastKeyPressed();
    
      grid.setImage(new Location(userRow,userCol), null);
      if (key==38){
          jump();
          
          if(!grid.isValid(new Location(userRow,userCol))){
              userRow++;
          }
      }else if(key == 39){
          handleCollision(new Location(userRow,userCol+1));
          userCol++;
          if(!grid.isValid(new Location(userRow,userCol))){
              userCol--;
          }
      }else if (key == 40){
          handleCollision(new Location(userRow+1,userCol));
          userRow++;
          if(!grid.isValid(new Location(userRow,userCol))){
              userRow--;
          }
      }else if(key == 37){
          handleCollision(new Location(userRow,userCol-1));
          userCol--;
          if(!grid.isValid(new Location(userRow,userCol))){
              userCol++;
          }
      }
      
      
     
      grid.setImage(new Location(userRow,userCol), "images/Mario.gif");
  }
  
  public void handleKeyPressAir(){
      int key = grid.checkLastKeyPressed();
      
      if(key == 39){
          grid.setImage(new Location(userRow,userCol), null);
          handleCollision(new Location(userRow,userCol+1));
          userCol++;
          if(!grid.isValid(new Location(userRow,userCol))){
              userCol--;
          }
      }else if(key == 37){
          grid.setImage(new Location(userRow,userCol), null);
          handleCollision(new Location(userRow,userCol-1));
          userCol--;
          if(!grid.isValid(new Location(userRow,userCol))){
              userCol++;
          }
      }
      
      

      grid.setImage(new Location(userRow,userCol), "images/Mario.gif");
  }
  
  public void populateRightEdge(){
      int row = grid.getNumRows();
      int col = grid.getNumCols();
      
   
      int randNum1 = (int)(Math.random() * 10 + 1);
      if(randNum1 <= 2){
          grid.setImage(new Location(row-1,col-1),"images/Goomba.gif");
      }
      
      int randNum2 = (int)(Math.random() * 10 + 1);
      if(randNum2 <= 4){
          grid.setImage(new Location((int)(Math.random() * 3 + 4),col-1),"images/Coin.gif");
      }
      
  }
  
  public void jump(){
      handleCollision(new Location(userRow-1,userCol));
      grid.setImage(new Location(userRow,userCol),null);
      userRow--;
      grid.setImage(new Location(userRow,userCol),"images/Mario.gif");
      
      grid.pause(100);
      handleKeyPressAir();
      if(msElapsed % 300 == 0 ){
        scrollLeft();
        populateRightEdge();
      }
      msElapsed += 100;
      
      
      
      handleCollision(new Location(userRow-1,userCol));
      grid.setImage(new Location(userRow,userCol),null);
      userRow--;
      grid.setImage(new Location(userRow,userCol),"images/Mario.gif"); 
      
      grid.pause(100);
      handleKeyPressAir();
      if(msElapsed % 300 == 0 ){
        scrollLeft();
        populateRightEdge();
      }
      msElapsed += 100;
      
      
      
      handleJumpOn(new Location(userRow+1,userCol));
      grid.setImage(new Location(userRow,userCol),null);
      userRow++;
      grid.setImage(new Location(userRow,userCol),"images/Mario.gif"); 
      
      grid.pause(100);
      handleKeyPressAir();
      if(msElapsed % 300 == 0 ){
        scrollLeft();
        populateRightEdge();
      }
      msElapsed += 100;
      
      
      handleJumpOn(new Location(userRow+1,userCol));
      grid.setImage(new Location(userRow,userCol),null);
      userRow++;

      
  }
  
  public void jumpOn(){
      grid.setImage(new Location(userRow,userCol),null);
      userRow--;
      grid.setImage(new Location(userRow,userCol),"images/JumpOn.gif");
      grid.pause(200);
      grid.setImage(new Location(userRow,userCol),null);
      userRow++;
      grid.setImage(new Location(userRow,userCol),"images/Mario.gif");
  }
  
  

  public void scrollLeft(){
      handleCollision(new Location(userRow,userCol+1));
      
      for (int row = 0; row < grid.getNumRows(); row++){
          for(int col = 0; col+1 < grid.getNumCols(); col++){
              String temp = grid.getImage(new Location(row,col+1));
              if(grid.getImage(new Location(row,col+1)) == null){
                  //just a null checker
                 
                  if(grid.getImage(new Location(row,col)) == null){
                      
                      grid.setImage(new Location(row, col),temp);
                  }else if (grid.getImage(new Location(row,col)).equals("images/Mario.gif")){
                      grid.setImage(new Location(row,col+1),null);
                  }else{
                      
                      grid.setImage(new Location(row, col),temp);
                      
                  } 
              }else if(grid.getImage(new Location(row,col+1)).equals("images/Mario.gif")){
                  grid.setImage(new Location(row, col),null);
              }else{
                  if(grid.getImage(new Location(row,col)) == null){

                      grid.setImage(new Location(row,col+1),null);
                      grid.setImage(new Location(row, col),temp);
                  }else if (grid.getImage(new Location(row,col)).equals("images/Mario.gif")){
                      grid.setImage(new Location(row,col+1),null);
                  }else{
                      grid.setImage(new Location(row,col+1),null);
                      grid.setImage(new Location(row, col),temp);
        
                  } 
              }
          }
      }
      
  }
  
  //this method should be called right before collistion could potentially be occuring
  public void handleCollision(Location loc){
      if(grid.getImage(loc) == null){
          //nothigs gonna happen
      }else if (grid.getImage(loc).equals("images/Goomba.gif")){
          timesAvoid++;
          grid.setImage(loc,null);
      }else if(grid.getImage(loc).equals("images/Coin.gif")){
          timesGet++;
          grid.setImage(loc,null);
      }
  }
  
  public void handleJumpOn(Location loc){
      if(grid.getImage(loc) == null){
          
      }else if (grid.getImage(loc).equals("images/Goomba.gif")){
          timesJumpOn++;
          grid.setImage(loc,"images/StepOn.gif");
          jumpOn();
          grid.setImage(loc,null);
      }else if(grid.getImage(loc).equals("images/Coin.gif")){
          timesGet++;
          grid.setImage(loc,null);
      }
  }
  
  public int getScore() {
    return timesGet*100 - timesAvoid*50 + timesJumpOn*300;
  }
  
  public void updateTitle() {
    grid.setTitle("Your Score:  " + getScore() +
                  "\n" + "You got hit " + timesAvoid + " times" +
                  "\n" + "You got " + timesGet + "coins");
  }
  
  public boolean isGameOver(){
    if(msElapsed > 100000 || timesAvoid > 5){
        return true;
    }
    return false;
  }
  
  public static void test() {
    Game game = new Game();
    game.play();
  }
  
  public static void main(String[] args) {
    test();
  }
}