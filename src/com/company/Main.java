package com.company;

public class Main{

    public static void main(String[] args) {
	// write your code here
        Scheduling current = new Scheduling(50, 12, 12);

        current.showStudents(10);

        current.solve();

        if(!current.checkScheduling()){
            System.out.println("No scheduling posible");
            return;}

        current.showResult();
    }
}
