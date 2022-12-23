package parallel;

import java.io.Serializable;
import java.rmi.RemoteException;

public class GenerationMessage implements Serializable {
  int [][] info;
  int msgSource;

  public GenerationMessage(int[][] info, int msgSource) throws RemoteException {
    this.info = info;
    this.msgSource = msgSource;
  }

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
