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
      list.add("COM8");
      
      // create the process builder
      ProcessBuilder pb = new ProcessBuilder(list);
      
      // get the command list
      try {
         Process ps = pb.start();
         var inputStream = ps.getInputStream();
         var reader = new BufferedReader(new InputStreamReader(inputStream));
         while(true)
         {
            String line = reader.readLine();
            if (line == null) continue;
            Ligne(line);
            //System.out.println(line);
         }
      }
      catch(Exception e)
      {
         System.out.println(e.getMessage());
      }
      
   }

   private static int danger = 0 ;

   private static boolean dataOk = false;
   public static void Ligne (String line) {
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
            return;
         }
      }
   var theta = Float.parseFloat(splitted[isStart?3:4]);
   var distance = Float.parseFloat(splitted[isStart?5:6]);
   var qualite = Float.parseFloat(splitted[isStart?7:8]);


   if(isStart)
   {
      //Envoie danger
      if(danger>0)
      {
         System.out.println("danger");
      }
      //Reinitialise danger
      danger = 0;
   }

   if(qualite == 0) return;
       
   if (theta >= 45.0f && theta <= 90.0f || theta > 270.0f && theta <= 315.0f) {
               if (distance < D_cote ) {
                  danger = 1;
                  System.out.println("Distance cote " + distance);
               }
            } 
   else if (theta >= 00f && theta <= 45.0f || theta > 315.0f) {
               if (distance < D_devant ) {
                  danger = 1;
                  System.out.println("Distance devant " + distance);
               }
            }

            return;
            
   }
}
