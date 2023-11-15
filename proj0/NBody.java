public class NBody{
    public static double readRadius(String filename)
    {
        double r;
        In in = new In(filename);
        int num = in.readInt();
        r = in.readDouble();
        return r;
    }
    public static Planet[] readPlanets(String filename)
    {
        double xxPos,xxVel,yyPos,yyVel,mass;
        String imgFileName;
        In in = new In(filename);
        int num = in.readInt();
        double secondItemInFile = in.readDouble();
        Planet[]p=new Planet[num];
        for(int i=0; i<num;i++)
        {
            xxPos = in.readDouble();
            yyPos = in.readDouble();
            xxVel = in.readDouble();
            yyVel = in.readDouble();
            mass = in.readDouble();
            imgFileName = in.readString();
            p[i]=new Planet(xxPos,yyPos,xxVel,yyVel,mass,imgFileName);
        }
        return p;
    }
    public static void main(String[] args)
    {
        double T = Double.parseDouble(args[0]);
        double dt =Double.parseDouble(args[1]);
        String filename = args[2];
        double r = readRadius(filename);
        Planet[] planets = readPlanets(filename);

        // set the universe scale
        StdDraw.setXscale(-r, r);
        StdDraw.setYscale(-r, r);
        StdDraw.enableDoubleBuffering();

        double t = 0;
        int num = planets.length;
        while(t <= T){
            double[] xForces = new double[num];
            double[] yForces = new double[num];
            for(int i = 0; i < num; i++){
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for(int i = 0; i < num; i++){
                planets[i].update(dt, xForces[i], yForces[i]);
            }

            // draw the backgroud picture
            StdDraw.picture(0, 0, "images/starfield.jpg");

            // draw all the planets
            for (Planet planet : planets) {
                planet.draw();
            }

            StdDraw.show();
            StdDraw.pause(10);
            t += dt;
        }

        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", r);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}