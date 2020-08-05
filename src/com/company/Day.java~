package com.company;

import java.util.*;

public class Day {
    private int id;
    private List<Lecture> lectures = new ArrayList<>();

    public Day(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void addLecture(Lecture l){
        lectures.add(l);
    }

    public void info(){
        System.out.println("\nDay " + id + ", lectures:");
        lectures.forEach((lecture) -> {
            lecture.info();
        });
    }
}
