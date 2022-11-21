# TCC *vs* LCC

Explain under which circumstances *Tight Class Cohesion* (TCC) and *Loose Class Cohesion* (LCC) metrics produce the same value for a given Java class. Build an example of such as class and include the code below or find one example in an open-source project from Github and include the link to the class below. Could LCC be lower than TCC for any given class? Explain.

## Answer













## Code of the exercise
```java
class Rationnel{

    int num;
    int den;

    Rationnel(int num, int den){
        this.num = num;
        this.den = den;
    }
    Rationnel add(Rationnel r){
        return new Rationnel(this.num*r.den + this.den*r.num, this.den*r.den);
    }
    Rationnel sub(Rationnel r){
        return new Rationnel(this.num*r.den - this.den*r.num, this.den*r.den);
    }
    Rationnel mul(Rationnel r){
        return new Rationnel(this.num*r.num, this.den*r.den);
    }
    Rationnel div(Rationnel r){
        return new Rationnel(this.num*r.den, this.den*r.num);
    }
    void affiche(){
        System.out.println(this.num + "/" + this.den);
    }    
}
```