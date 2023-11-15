public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static final double G=6.67e-11;

    public Planet(double xP, double yP, double xV,
                  double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p)
    {
        this(p.xxPos,p.yyPos,p.xxVel,p.yyVel,p.mass,p.imgFileName);
    }
    public double calcDistance(Planet p)
    {
        return Math.sqrt((xxPos-p.xxPos)*(xxPos-p.xxPos)+(yyPos-p.yyPos)*(yyPos-p.yyPos));
    }
    public double calcForceExertedBy(Planet p)
    {
        double r;
        double f;
        r = calcDistance(p);
        f = G*(this.mass*p.mass/(r*r));
        return f;
    }
    public double calcForceExertedByX(Planet p)
    {
        double r,f,fx;
        r=this.calcDistance(p);
        f=this.calcForceExertedBy(p);
        fx=f*(p.xxPos-this.xxPos)/r;
        return fx;
    }
    public double calcForceExertedByY(Planet p)
    {
        double r,f,fy;
        r=this.calcDistance(p);
        f=this.calcForceExertedBy(p);
        fy=f*(p.yyPos-this.yyPos)/r;
        return fy;
    }
    public double calcNetForceExertedByX(Planet[] p)
    {
        double fx=0.0;
        for(int i=0; i<p.length; i++)
        {
            if(this.equals(p[i]))
            {
                continue;
            }
            fx = fx + calcForceExertedByX(p[i]);
        }
        return fx;
    }
    public double calcNetForceExertedByY(Planet[] p)
    {
        double fy=0.0;
        for(int i=0; i<p.length; i++)
        {
            if(this.equals(p[i]))
            {
                continue;
            }
            fy = fy + calcForceExertedByY(p[i]);
        }
        return fy;
    }
    public void update(double dt, double fx, double fy)
    {
        double ax = fx / mass;
        double ay = fy / mass;
        xxVel += dt * ax;
        yyVel += dt * ay;
        xxPos += xxVel * dt;
        yyPos += yyVel * dt;
    }
    public void draw()
    {
        String img_root = "./images/"+this.imgFileName;
        StdDraw.picture(this.xxPos,this.yyPos,img_root);
    }
}