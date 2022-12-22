package parallel;

import java.io.Serializable;

public class GenerationMessage implements Serializable{
  int [][] info;
  int msgSource;

  public int[][] getInfo() {
    return info;
  }
  public void setInfo(int[][] info) {
    this.info = info;
  }
  public int getMsgSource() {
    return msgSource;
  }
  public void setMsgSource(int msgSource) {
    this.msgSource = msgSource;
  }
  
  
}
