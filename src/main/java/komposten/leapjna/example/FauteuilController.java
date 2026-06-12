package komposten.leapjna.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FauteuilController {

    private static final float ZONE_MORTE = 20;
    public TcpClient client = new TcpClient();
    float xOrigine;
    float zOrigine;

    void arret() {
        client.setValues(0,0,1);
    }


    class Point {

        private float x, y;

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public Point() {
            this.x = 0;
            this.y = 0;
        }

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float distance(Point origine) {
            float x0 = origine.x, y0 = origine.y;
            return (float) Math.sqrt(((x - x0) * (x - x0)) + ((y - y0) * (y - y0)));

        }
    }

    public int gererMouvement(float xPaume, float zPaume, int nbdoigstetendus) {

        zPaume = -zPaume;

        if (nbdoigstetendus <= 1) {
            if (xOrigine == 0 || zOrigine == 0) {
                xOrigine = xPaume;
                zOrigine = zPaume;
            }
        } else {
            xOrigine = 0;
            zOrigine = 0;
        }

        Point pOrigine = new Point(xOrigine, zOrigine);

        Point pPaume = new Point(xPaume, zPaume);
        float vPaume = pPaume.distance(pOrigine);

        float zoneMorte = ZONE_MORTE;
        float zonev1 = 40;
        float zonev2 = 60;
        float zonev3 = 90;
        float zonev4 = 110;

        float v1 = 1;
        float v2 = 2;
        float v3 = 3;
        float v4 = 4;
        float v5 = 5;

        float vitesse = 0;

        if (nbdoigstetendus <= 1) {

            if (vPaume <= zoneMorte) {
                vitesse = 0;
                client.setValues(0,0,vitesse);

            }
            if (vPaume >= zoneMorte) {
                vitesse = v1;
                client.setValues(zPaume,xPaume,vitesse);

            }
            if (vPaume >= zonev1) {
                vitesse = v2;
                client.setValues(zPaume,xPaume,vitesse);

            }
            if (vPaume >= zonev2) {
                vitesse = v3;
                client.setValues(zPaume,xPaume,vitesse);

            }
            if (vPaume >= zonev3) {
                vitesse = v4;
                client.setValues(zPaume,xPaume,vitesse);

            }
            if (vPaume >= zonev4) {
                vitesse = v5;
                client.setValues(zPaume,xPaume,vitesse);
            }
            
        }
    else {
    client.setValues(0,0,vitesse);
    }

        return (int) vitesse;
    }

    public void startLidar() {
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
            Thread t = new Thread() {
                public void run() {
                    while (true) {
                        try {
                            String line = reader.readLine();
                            if (line == null) {
                                continue;
                            }
                            Ligne(line);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            };
            t.start();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static final float D_cote = 500;
    private static final float D_devant = 700;
    private int danger = 0;

    private boolean dataOk = false;

    private void Ligne(String line) {
        String[] splitted = line.split(" ");
        var isStart = splitted[0].matches("S");
        if (!dataOk) {
            if (isStart) {
                dataOk = true;
            } else {
                return;
            }
        }
        var theta = Float.parseFloat(splitted[isStart ? 3 : 4]);
        var distance = Float.parseFloat(splitted[isStart ? 5 : 6]);
        var qualite = Float.parseFloat(splitted[isStart ? 7 : 8]);

        if (isStart) {
            //Envoie danger
            if (danger > 0) {
                System.out.println("danger");
            }
            client.setDanger(danger);
            //Reinitialise danger
            danger = 0;
        }

        if (qualite == 0) {
            return;
        }

        if (theta >= 45.0f && theta <= 90.0f || theta > 270.0f && theta <= 315.0f) {
            if (distance < D_cote) {
                danger = 1;
                System.out.println("Distance cote " + distance);
            }
        } else if (theta >= 00f && theta <= 45.0f || theta > 315.0f) {
            if (distance < D_devant) {
                danger = 1;
                System.out.println("Distance devant " + distance);
            }
        }

        return;

    }


}
