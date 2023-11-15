public class Planet {
    double xxPos;
    double yyPos;
    double xxVel;
    double yyVel;
    double mass;
    String imgFileName;

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
    double calcDistance(Planet p)
    {
        double r;
        r=Math.sqrt((this.xxPos-p.xxPos)*(this.xxPos-p.xxPos)+(this.yyPos-p.yyPos)*(this.yyPos-p.yyPos));
        return r;
    }
    double calcForceExertedBy(Planet p)
    {
        double r;
        double f;
        r = calcDistance(p);
        f = 6.67*1E-11*(this.mass*p.mass/(r*r));
        return f;
    }
    double calcForceExertedByX(Planet p)
    {
        double r,f,fx;
        r=this.calcDistance(p);
        f=this.calcForceExertedBy(p);
        fx=f*(p.xxPos-this.xxPos)/r;
        return fx;
    }
    double calcForceExertedByY(Planet p)
    {
        double r,f,fy;
        r=this.calcDistance(p);
        f=this.calcForceExertedBy(p);
        fy=f*(p.yyPos-this.yyPos)/r;
        return fy;
    }
    double calcNetForceExertedByX(Planet[] p)
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
    double calcNetForceExertedByY(Planet[] p)
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
    void update(double dt, double fx, double fy)
    {
        double ax,ay,vx,vy,xpos,ypos;
        ax = fx/this.mass;
        ay = fy/this.mass;
        vx = ax * dt + this.xxVel;
        vy = ay * dt + this.yyVel;

        xpos=this.xxPos+vx*dt;
        ypos=this.yyPos+vy*dt;

        this.xxVel=vx;
        this.yyVel=vy;
        this.xxPos=xpos;
        this.yyPos=ypos;
    }
    void draw()
    {
        String img_root = "./images/"+this.imgFileName;
        StdDraw.picture(this.xxPos,this.yyPos,img_root);
    }
}