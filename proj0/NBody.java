public class NBody{
    static double readRadius(String filename)
    {
        double r;
        In in = new In(filename);
        int firstItemInFile = in.readInt();
        r = in.readDouble();
        return r;
    }
    static Planet[] readPlanets(String filename)
    {
        double xxPos,xxVel,yyPos,yyVel,mass;
        String imgFileName;
        In in = new In(filename);
        int firstItemInFile = in.readInt();
        double secondItemInFile = in.readDouble();
        Planet[]p=new Planet[5];
        for(int i=0; i<5;i++)
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
    public static void main(String args[])
    {
        double T =Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];

        double now_t=0.0;
        double universe_radius;
        Planet[]p=new Planet[5];

        double[] xForces = new double[5];
        double[] yForces = new double[5];


        universe_radius = readRadius("./data/planets.txt");
        p=readPlanets("./data/planets.txt");

        StdDraw.enableDoubleBuffering();

        while (now_t<T)
        {
            for(int i=0;i<5;i++) {
                xForces[i] = p[i].calcNetForceExertedByX(p);
                yForces[i] = p[i].calcNetForceExertedByY(p);
                p[i].update(dt, xForces[i], yForces[i]);
            }
                StdDraw.setScale(-universe_radius,universe_radius);
                StdDraw.clear();
                StdDraw.picture(0, 0, "./images/starfield.jpg");
                for(int i=0;i<5;i++)
                {
                    p[i].draw();
                }
                StdDraw.show();
                StdDraw.pause(10);

                now_t+=dt;
        }
    }
}