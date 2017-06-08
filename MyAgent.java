import java.util.Random;

public class MyAgent extends Agent
{
    Random r;

    /**
     * Constructs a new agent, giving it the game and telling it whether it is Red or Yellow.
     *
     * @param game The game the agent will be playing.
     * @param iAmRed True if the agent is Red, False if the agent is Yellow.
     */
    public MyAgent(Connect4Game game, boolean iAmRed)
    {
        super(game, iAmRed);
        r = new Random();
    }

    /**
     * The move method is run every time it is this agent's turn in the game. You may assume that
     * when move() is called, the game has at least one open slot for a token, and the game has not
     * already been won.
     *
     * By the end of the move method, the agent should have placed one token into the game at some
     * point.
     *
     * After the move() method is called, the game engine will check to make sure the move was
     * valid. A move might be invalid if:
     * - No token was place into the game.
     * - More than one token was placed into the game.
     * - A previous token was removed from the game.
     * - The color of a previous token was changed.
     * - There are empty spaces below where the token was placed.
     *
     * If an invalid move is made, the game engine will announce it and the game will be ended.
     *
     */
    public void move()
    {
      int i = iCanWin();
      int j = theyCanWin();
      int k = randomMove();
      if (i != -1){
        System.out.println("value of i " + i);
        moveOnColumn(i);
      }
      else if(j != -1){
        System.out.println("value of j " + j);
        moveOnColumn(j);
      }
      else {
        System.out.println("value of k " + k);
        moveOnColumn(k);
      }
      }

    /**
     * Drops a token into a particular column so that it will fall to the bottom of the column.
     * If the column is already full, nothing will change.
     *
     * @param columnNumber The column into which to drop the token.
     */
    public void moveOnColumn(int columnNumber)
    {
        int lowestEmptySlotIndex = getLowestEmptyIndex(myGame.getColumn(columnNumber));   // Find the top empty slot in the column
                                                                                                  // If the column is full, lowestEmptySlot will be -1
        if (lowestEmptySlotIndex > -1)  // if the column is not full
        {
            Connect4Slot lowestEmptySlot = myGame.getColumn(columnNumber).getSlot(lowestEmptySlotIndex);  // get the slot in this column at this index
            if (iAmRed) // If the current agent is the Red player...
            {
                lowestEmptySlot.addRed(); // Place a red token into the empty slot
            }
            else // If the current agent is the Yellow player (not the Red player)...
            {
                lowestEmptySlot.addYellow(); // Place a yellow token into the empty slot
            }
        }
    }

    /**
     * Returns the index of the top empty slot in a particular column.
     *
     * @param column The column to check.
     * @return the index of the top empty slot in a particular column; -1 if the column is already full.
     */
    public int getLowestEmptyIndex(Connect4Column column) {
        int lowestEmptySlot = -1;
        for  (int i = 0; i < column.getRowCount(); i++)
        {
            if (!column.getSlot(i).getIsFilled())
            {
                lowestEmptySlot = i;
            }
        }
        return lowestEmptySlot;
    }

    /**
     * Returns a random valid move. If your agent doesn't know what to do, making a random move
     * can allow the game to go on anyway.
     *
     * @return a random valid move.
     */
    public int randomMove()
    {
        int i = r.nextInt(myGame.getColumnCount());
        while (getLowestEmptyIndex(myGame.getColumn(i)) == -1)
        {
            i = r.nextInt(myGame.getColumnCount());
        }
        return i;
    }

    /**
     * Returns the column that would allow the agent to win.
     *
     * You might want your agent to check to see if it has a winning move available to it so that
     * it can go ahead and make that move. Implement this method to return what column would
     * allow the agent to win.
     *
     * @return the column that would allow the agent to win.
     */
    public int iCanWin()
    {
      for(int i = 0; i < myGame.getColumnCount(); i++){
        int j = getLowestEmptyIndex(myGame.getColumn(i));
          if(j != -1){

            int count = 0;
            for(int k = j+1; k < myGame.getRowCount(); k++){
              Connect4Slot slot1 = myGame.getColumn(i).getSlot(k);
              if(slot1.getIsRed()){
                count++;
                if(count == 3){
                  return i;
                }
              }
              else
                break;
            }
            int countLeft = checkLeft(i);
            int countRight = checkRight(i);
            if((countLeft + countRight) > 2){
              return i;
          }
          int countLeftBelow = checkLeftBelow(i);
          int countRightAbove = checkRightAbove(i);
          if((countLeftBelow + countRightAbove) > 2){
            return i;
          }
          int countLeftAbove = checkLeftAbove(i);
          int countRightBelow = checkRightBelow(i);
          if((countLeftAbove + countRightBelow) > 2){
            return i;
          }
      }
    }
  return -1;
}

    /**
     * Returns the column that would allow the opponent to win.
     *
     * You might want your agent to check to see if the opponent would have any winning moves
     * available so your agent can block them. Implement this method to return what column should
     * be blocked to prevent the opponent from winning.
     *
     * @return the column that would allow the opponent to win.
     */
     public int theyCanWin(){
       for(int i = 0; i < myGame.getColumnCount(); i++){
         int j = getLowestEmptyIndex(myGame.getColumn(i));
           if(j != -1){
             Connect4Slot slot = myGame.getColumn(i).getSlot(j);
             int count = 0;
             for(int k = j+1; k < myGame.getRowCount(); k++){
               Connect4Slot slot1 = myGame.getColumn(i).getSlot(k);
               if(slot1.getIsFilled()){
                 if(!slot1.getIsRed()){
                   count++;
                   if(count == 3){
                   return i;
                 }
               }
               else
                break;
             }
           }
           int countLeft = checkLeftEnemy(i);
           int countRight = checkRightEnemy(i);
           if((countLeft + countRight) > 2){
             return i;
       }
       int countLeftBelow = checkLeftBelowEnemy(i);
       int countRightAbove = checkRightAboveEnemy(i);
       if((countLeftBelow + countRightAbove) > 2){
         return i;
       }
       int countLeftAbove = checkLeftAboveEnemy(i);
       int countRightBelow = checkRightBelowEnemy(i);
       if((countLeftAbove + countRightBelow) > 2){
         return i;
       }

     }
   }
    return -1;
     }

    public int checkLeft(int num){
      int j = getLowestEmptyIndex(myGame.getColumn(num));
      int count = 0;
      if(j != -1){
      for(int k = num-1; k >= 0; k--){
        Connect4Slot slot = myGame.getColumn(k).getSlot(j);
        if(slot.getIsFilled()){
          if(slot.getIsRed()){
            count++;
          }
          else
            break;
          }
          else
            break;
          }
        }
      return count;
    }
    public int checkRight(int num){
      int j = getLowestEmptyIndex(myGame.getColumn(num));
      int count = 0;
      if(j != -1){
      for(int k = num+1; k < myGame.getColumnCount(); k++){
        Connect4Slot slot = myGame.getColumn(k).getSlot(j);
        if(slot.getIsFilled()){
          if(slot.getIsRed()){
            count++;
          }
          else
            break;
          }
          else
            break;
          }
        }
      return count;
    }
    public int checkLeftEnemy(int num){
      int j = getLowestEmptyIndex(myGame.getColumn(num));
      int count = 0;
      if(j != -1){
      for(int k = num-1; k >= 0; k--){
        Connect4Slot slot = myGame.getColumn(k).getSlot(j);
        if(slot.getIsFilled()){
          if(!slot.getIsRed()){
            count++;
          }
          else
            break;
          }
          else
            break;
          }
        }
      return count;
    }
    public int checkRightEnemy(int num){
      int j = getLowestEmptyIndex(myGame.getColumn(num));
      int count = 0;
      if(j != -1){
      for(int k = num+1; k < myGame.getColumnCount(); k++){
        Connect4Slot slot = myGame.getColumn(k).getSlot(j);
        if(slot.getIsFilled()){
          if(!slot.getIsRed()){
            count++;
          }
          else
            break;
          }
          else
            break;
          }
        }
      return count;
    }
    public int checkLeftBelow(int num){
      int j = getLowestEmptyIndex(myGame.getColumn(num));
      int count = 0;
      if(j != -1){
      for(int k = num-1; k >= 0; k--){
        if(j < myGame.getRowCount()-1){
          j++;
        Connect4Slot slot = myGame.getColumn(k).getSlot(j);
        if(slot.getIsFilled()){
          if(slot.getIsRed()){
            count++;
          }
          else
            break;
          }
          else
            break;
          }
        }
        }
      return count;
    }
    public int checkRightAbove(int num){
      int j = getLowestEmptyIndex(myGame.getColumn(num));
      int count = 0;
      if(j != -1){
      for(int k = num+1; k < myGame.getColumnCount(); k++){
        if(j > 0){
          j--;
        Connect4Slot slot = myGame.getColumn(k).getSlot(j);
        if(slot.getIsFilled()){
          if(slot.getIsRed()){
            count++;
          }
          else
            break;
          }
          else
            break;
          }
        }
        }
      return count;
    }

    public int checkLeftAbove(int num){
      int j = getLowestEmptyIndex(myGame.getColumn(num));
      int count = 0;
      if(j != -1){
      for(int k = num-1; k > 0; k--){
        if(j > 0){
          j--;
        Connect4Slot slot = myGame.getColumn(k).getSlot(j);
        if(slot.getIsFilled()){
          if(slot.getIsRed()){
            count++;
          }
          else
            break;
          }
          else
            break;
          }
        }
        }
      return count;
    }
    public int checkRightBelow(int num){
      int j = getLowestEmptyIndex(myGame.getColumn(num));
      int count = 0;
      if(j != -1){
      for(int k = num+1; k < myGame.getColumnCount(); k++){
        if(j < myGame.getRowCount()-1){
          j++;
        Connect4Slot slot = myGame.getColumn(k).getSlot(j);
        if(slot.getIsFilled()){
          if(slot.getIsRed()){
            count++;
          }
          else
            break;
          }
          else
            break;
          }
        }
        }
      return count;
    }

    public int checkLeftBelowEnemy(int num){
      int j = getLowestEmptyIndex(myGame.getColumn(num));
      int count = 0;
      if(j != -1){
      for(int k = num-1; k >= 0; k--){
        if(j < myGame.getRowCount()-1){
          j++;
        Connect4Slot slot = myGame.getColumn(k).getSlot(j);
        if(slot.getIsFilled()){
          if(!slot.getIsRed()){
            count++;
          }
          else
            break;
          }
          else
            break;
          }
        }
        }
      return count;
    }
    public int checkRightAboveEnemy(int num){
      int j = getLowestEmptyIndex(myGame.getColumn(num));
      int count = 0;
      if(j != -1){
      for(int k = num+1; k < myGame.getColumnCount(); k++){
        if(j > 0){
          j--;
        Connect4Slot slot = myGame.getColumn(k).getSlot(j);
        if(slot.getIsFilled()){
          if(!slot.getIsRed()){
            count++;
          }
          else
            break;
          }
          else
            break;
          }
        }
        }
      return count;
    }

    public int checkLeftAboveEnemy(int num){
      int j = getLowestEmptyIndex(myGame.getColumn(num));
      int count = 0;
      if(j != -1){
      for(int k = num-1; k > 0; k--){
        if(j > 0){
          j--;
        Connect4Slot slot = myGame.getColumn(k).getSlot(j);
        if(slot.getIsFilled()){
          if(!slot.getIsRed()){
            count++;
          }
          else
            break;
          }
          else
            break;
          }
        }
        }
      return count;
    }
    public int checkRightBelowEnemy(int num){
      int j = getLowestEmptyIndex(myGame.getColumn(num));
      int count = 0;
      if(j != -1){
      for(int k = num+1; k < myGame.getColumnCount(); k++){
        if(j < myGame.getRowCount()-1){
          j++;
        Connect4Slot slot = myGame.getColumn(k).getSlot(j);
        if(slot.getIsFilled()){
          if(!slot.getIsRed()){
            count++;
          }
          else
            break;
          }
          else
            break;
          }
        }
        }
      return count;
    }

    /**
     * Returns the name of this agent.
     *
     * @return the agent's name
     */
    public String getName()
    {
        return "My Agent";
    }
}
