package ru.otus.l3.Collection;

import java.util.*;

public class Main {
    public static void main(String... args){

        List<Integer> ls = new ArrayList<Integer> ();

        for(int i= 0; i<120; i++){
            ls.add(i);
        }

        List <Integer> mls = new MyArrayList<Integer>();
        for(int i=0; i<120; i++ )
        {
            mls.add(i+1);
        }



        boolean  b =  Collections.addAll(mls,122,555,12,74,82,8);
        print(mls);

        Collections.copy(mls,ls);
        print(mls);

        Collections.sort(mls);
        print(mls);






    }

    public static void print(List ar){
        for(var i : ar){
            System.out.print(i+" ");
        }
        System.out.println(" ");
    }

}


