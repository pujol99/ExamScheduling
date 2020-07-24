package com.company;

import java.util.*;

public class Student{
    private int id;
    private List<Lecture> lectures = new ArrayList<>();

    public Student(int id){

        this.id = id;
    }

    public int getId(){

        return id;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void addLecture(Lecture l){
        lectures.add(l);
    }

    public void info(){
        System.out.print("\tStudent " + id + ", lectures: {");
        lectures.forEach((lecture) -> {
            System.out.print(" " + lecture.getId());
        });
        System.out.print(" }\n");
    }

    public boolean checkError(){
        //check if student has +1 lecture in same day
        List<Day> days = new ArrayList<>();
        for(int i = 0; i < lectures.size(); i++){
            Day day = lectures.get(i).getLectureDay();
            if(days.contains(day)){
                return true;
            }else{
                days.add(day);
            }
        }
        return false;
    }
}
