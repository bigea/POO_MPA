package tools;
import java.util.*;
import java.lang.Long;

public class Tuple<X, Y>{
    public X x;
    public Y y;

    public Tuple(X x, Y y){
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o){
        Tuple<X, Y> tuple = (Tuple<X, Y>)o;
        // System.out.println("coucou je suis passe dans equals");
        if(tuple.x == this.x && tuple.y == this.y){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public int hashCode(){ //Hash code à revoir car il y a au maximum 40*40 case dans nos cartes
        int hash = x.hashCode()*2500 +y.hashCode();
        // System.out.println("coucou je suis passe dans hashcode, voici ma valeur : " + hash);
        // System.out.println("coucou je suis passe dans hashcode, valeur de hashcode x : " + x.hashCode());
        // System.out.println("coucou je suis passe dans hashcode, valeur de hashcode y : " + y.hashCode());
        return Long.hashCode(hash);
    }
    @Override
    public String toString(){
        return "(" + x +", " +y +")";
    }
}
