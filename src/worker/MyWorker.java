/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.SwingWorker;


/**
 *
 * @author levin
 */
public class MyWorker extends SwingWorker<List<String>,String>
{
  private String status;
  private final AtomicBoolean stopRequest = new AtomicBoolean();
  private final ArrayList<String> measurementList = new ArrayList<>();
  
  public void stop ()
  {
    stopRequest.set(true);
  }
  
  private void setStatus (String status)
  {
    firePropertyChange("Status", this.status, status);
    this.status = status;
  }
  
  @Override
  protected List<String> doInBackground () throws Exception
  {
    int cnt = 0;
   
    try
    {
      System.out.println("MyWorker startet jetzt");
      setStatus("Messung gestartet");
      while (!isCancelled() && ! stopRequest.get())
      {
        cnt++;
        if (cnt>20)
          throw new Exception("Error.... im Thread");
        Thread.sleep(500);
        setStatus("Messung " + cnt + " durchgeführt");
        measurementList.add("Messung " + cnt);
      }
      if (isCancelled())
        return null;
      
     return measurementList;
    }
    finally
    {
      System.out.println("MyWorker zu Ende");
    }
    
  }
  
}
