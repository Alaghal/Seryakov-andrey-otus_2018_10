package ru.otus.l2.memory;

import java.util.*;

public class Main {
    public static void main(String... args){


        List<Integer> ls = new ArrayList<Integer> ();
        ls.add(3);
        ls.add(5);
        ls.add(6);
        ls.add(8);
        ls.add(0);




       List <Integer> mls = new MyArrayList<Integer>();

        boolean  b =  Collections.addAll(mls,1,2,3,4,5,8);
        System.out.println(mls);


        //Collections.copy(mls,ls);

     //   MyArrayList.copy(mls,ls);
     //  MyArrayList.sort(mls, new IntegerComparator());

       // System.out.println(mls);

      //  MyArrayList.cop




    }


}

class IntegerComparator implements Comparator<Integer> {


    @Override
    public int compare(Integer o1, Integer o2) {

        if((int)o1 > (int)o2) {
            return 1;
        }
        else if( (int)o1 < (int)o2)
            return -1;
        else
            return 0;
    }
}
