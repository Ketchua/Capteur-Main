package komposten.leapjna.example;


public class fauteuilController {

    private static final float ZONE_MORTE = 20;
    float xOrigine;
    float zOrigine;

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
        
        public int gererMouvement(float xPaume, float zPaume,int nbdoigstetendus){



            zPaume = - zPaume;

        if (nbdoigstetendus <=1){
            if (xOrigine == 0 || zOrigine == 0){
                xOrigine = xPaume;
                zOrigine = zPaume;
                }
            }
        else {
            xOrigine = 0;
            zOrigine = 0;
        }
            

            Point pOrigine = new Point(xOrigine,zOrigine);

            Point pPaume = new Point(xPaume,zPaume);
            float vPaume = pPaume.distance(pOrigine);

            float zoneMorte = ZONE_MORTE;
            float zonev1 = 40 ;
            float zonev2 = 60 ;
            float zonev3 = 90 ;
            float zonev4 = 110;

            float v1 = 1;
            float v2 = 2;
            float v3 = 3;
            float v4 = 4;
            float v5 = 5;

            float vitesse = 0;

            if (nbdoigstetendus <= 1){
            
                if (vPaume <= zoneMorte){
                    vitesse = 0;
                }
                if (vPaume >= zoneMorte){
                    vitesse = v1;
                }
                if (vPaume >= zonev1){
                    vitesse = v2;
                }
                if (vPaume >= zonev2){
                    vitesse = v3;
                }
                if (vPaume >= zonev3){
                    vitesse = v4;
                }
                if (vPaume >= zonev4){
                    vitesse = v5;   
                }
        
            
            }
            return (int) vitesse;
        }

}