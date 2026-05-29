import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProcessBuilderDemo {
   private static final float D_cote = 500;
   private static final float D_devant = 700;



   public static void main(String[] args) {

      // create a new list of arguments for our process
      List<String> list = new ArrayList<String>();
      list.add("ultra_simple.exe");
      
      // create the process builder
      ProcessBuilder pb = new ProcessBuilder(list);
      
      // get the command list
      System.out.println(pb.command());
      try {
         Process ps = pb.start();
         var inputStream = ps.getInputStream();
         var reader = new BufferedReader(new InputStreamReader(inputStream));
         while(true)
         {
            String line = reader.readLine();
            if (line == null) continue;
            Ligne(line);
            System.out.println(line);
         }
      }
      catch(Exception e)
      {
         System.out.println(e.getMessage());
      }
      
   }

   private static boolean dataOk = false;
   public static float Ligne (String line) {
    String[] splitted = line.split(" ");
    var isStart = splitted[0].matches("S");
      if(!dataOk)
      {
         if(isStart)
         {
             dataOk = true;
         }
         else
         {
            return -1.0f;
         }
      }
   var theta = Float.parseFloat(splitted[isStart?3:4]);
   var distance = Float.parseFloat(splitted[isStart?5:6]);
   var qualite = Float.parseFloat(splitted[isStart?7:8]);
   var danger = 0 ;

   
       
   if (theta >= 45.0f && theta <= 90.0f || theta > 270.0f && theta <= 315.0f) {
               if (distance >D_cote ) {
                  danger = 0;
               }
               else {
                  danger = 1;
               }
            } 
   else if (theta >= 00f && theta <= 45.0f || theta > 270.0f && theta <= 315.0f) {
               if (distance > D_devant ) {
                  danger = 0;
               }
               else {
                  danger = 1;
               }
            }
            System.out.println(danger);
            return danger;
            
   }
}
