package tools;
import java.util.*;

public class Tuple<X, Y>{
    public X x;
    public Y y;

    public Tuple(X x, Y y){
        this.x = x;
        this.y = y;
    }

    public boolean equals(Tuple<X, Y> tuple){
        System.out.println("coucou je suis passe dans equals");
        if(tuple.x == this.x && tuple.y == this.y){
            return true;
        }
        else{
            return false;
        }
    }

    public int hashCode(){ //Hash code à revoir car il y a au maximum 40*40 case dans nos cartes
        int hash = x.hashCode()*1600 +y.hashCode();
        // System.out.println("coucou je suis passe dans hashcode, voici ma valeur : " + hash);
        // System.out.println("coucou je suis passe dans hashcode, valeur de hashcode x : " + x.hashCode());
        // System.out.println("coucou je suis passe dans hashcode, valeur de hashcode y : " + y.hashCode());
        return hash;
    }
    @Override
    public String toString(){
        return "(" + x +", " +y +")";
    }
}
