package com.company;

import java.util.*;

public class Lecture {
    private int id;
    private List<Student> students = new ArrayList<>();
    private Day lectureDay;
    private List<Day> posibleDays = new ArrayList<>();
    private List<Lecture> linkedLectures = new ArrayList<>();
    private Random rand = new Random();

    public Lecture(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public Day getLectureDay(){
        return lectureDay;
    }

    public List<Day> getPosibleDays(){
        return posibleDays;
    }

    public void setPosibleDays(List<Day> posibleDays) {
        this.posibleDays = new ArrayList<>(posibleDays);
    }

    public List<Lecture> getLinkedLectures(){
        return linkedLectures;
    }

    public Day chooseRandomDay(){
        int nDay = rand.nextInt(posibleDays.size());
        lectureDay = posibleDays.get(nDay);
        posibleDays.clear();
        return lectureDay;
    }

    public void setLectureDay(Day lectureDay){
        this.lectureDay = lectureDay;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void addStudent(Student s){
        students.add(s);
    }

    public void info() {
        System.out.print("\tLecture " + id + ", students: {");
        students.forEach((student) -> {
            System.out.print(" " + student.getId());
        });
        System.out.print(" }\n");
    }

}
