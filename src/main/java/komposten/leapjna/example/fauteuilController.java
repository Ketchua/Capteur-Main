package komposten.leapjna.example;


public class fauteuilController {

    private static final float ZONE_MORTE = 10;

    class Point
        {
            private float x, y;
            
            public float getY()
            {
                return y;
            }

            public void setY(float y)
            {
                this.y = y;
            }
        
            public float getX()
            {
                return x;
            }

            public void setX(float x)
            {
                this.x = x;
            }

            public Point()
            {
                this.x = 0;
                this.y = 0;
            }

            public Point(float x, float y)
            {
                this.x = x;
                this.y = y;
            }
        
            public float distance(Point origine)
            {
                float x0 = origine.x, y0 = origine.y;
                return (float)Math.sqrt(((x - x0) * (x - x0)) + ((y - y0) * (y - y0))); 

            }
        }


        
        public void gererMouvement(float xPaume, float zPaume){

            float xOrigine = 30;
            float zOrigine = -30;

            zPaume = - zPaume;

            Point pOrigine = new Point(xOrigine,zOrigine);
            Point pPaume = new Point(xPaume,zPaume);
            float vPaume = pPaume.distance(pOrigine);

            float zoneMorte = ZONE_MORTE;
            float zonev1 = 30 ;
            float zonev2 = 60 ;
            float zonev3 = 110;

            float v1 = 1;
            float v2 = 30;
            float v3 = 75;
            float v4 = 100;

            float vitesse = 0;

            if (vPaume <= zoneMorte){
                vitesse = 0;
            }
            if (vPaume <= zonev1){
                vitesse = v1;
            }
            if (vPaume <= zonev2){
                vitesse = v2;
            }
            if (vPaume <= zonev3){
                vitesse = v3;
            }
            if (vPaume > zonev3){
                vitesse = v4;
            }
            
            

        }

}