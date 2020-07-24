package com.company;

import java.util.*;
import java.lang.Math;

public class Scheduling {
    private List<Student> students = new ArrayList<>();
    private List<Lecture> lectures = new ArrayList<>();
    private List<Day> days = new ArrayList<>();
    private Random rand = new Random();

    public Scheduling(int nStudents, int nLectures, int nDays){
        //build db
        for(int i = 0; i < nDays; i++)
            days.add(new Day(i));

        for(int i = 0; i < nStudents; i++)
            students.add(new Student(i));

        for(int i = 0; i < nLectures; i++){
            lectures.add(new Lecture(i));
            lectures.get(i).setPosibleDays(days);}

        //enroll students to random lectures
        for(int i = 0; i < students.size(); i++){
            Student student = students.get(i);
            List<Lecture> commonLectures = new ArrayList<>();
            for(int j = 0; j < Math.floor(nLectures*0.333); j++) {
                Lecture l = lectures.get(rand.nextInt(nLectures));
                enroll(student, l);
                commonLectures.add(l);
            }
            linkLectures(commonLectures);
        }
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Lecture> getLectures() { return lectures; }

    public List<Day> getDays() {
        return days;
    }

    public void linkLectures(List<Lecture> linked){
        for(int i = 0; i < linked.size(); i++){
            for(int j = i+1; j < linked.size(); j++){
                Lecture l1 = linked.get(i);
                Lecture l2 = linked.get(j);
                l1.getLinkedLectures().add(l2);
                l2.getLinkedLectures().add(l1);
            }
        }
    }

    public boolean studentInCommon(Lecture l1, Lecture l2){
        List<Student> s1 = l1.getStudents();
        List<Student> s2 = l2.getStudents();
        List<Student> s3 = new ArrayList<>();
        for(int i = 0; i < Math.max(s1.size(), s2.size()); i++){
            if(i < s1.size()){
                if(s3.contains(s1.get(i))){
                    return true;
                }else{
                    s3.add(s1.get(i));
                }
            }
            if(i < s2.size()){
                if(s3.contains(s2.get(i))){
                    return true;
                }else{
                    s3.add(s2.get(i));
                }
            }
        }
        return false;
    }

    public void enroll(Student s, Lecture l){
        if(l.getStudents().contains(s))
            return;

        s.addLecture(l);
        l.addStudent(s);
    }

    public void showStudents(int n){
        if(n > students.size()){
            System.out.println("Not that much students");
            return;
        }
        for(int i = 0; i < n; i++){
            students.get(i).info();
        }
        System.out.println("...");
    }

    public boolean checkScheduling(){
        for(int i = 0; i < students.size(); i++){
            if(students.get(i).checkError())
                return false;
        }
        return true;
    }

    public boolean checkFatalEnd(){
        //check if some lecture has no posible days
        for(int i = 0; i < lectures.size(); i++){
            Lecture l = lectures.get(i);
            if(l.getPosibleDays().size() == 0 && l.getLectureDay() == null)
                return true;
        }

        return false;
    }

    public boolean checkEnd(){
        for(int i = 0; i < lectures.size(); i++){
            if(lectures.get(i).getLectureDay() == null)
                return false;
        }
        return true;
    }

    public void solve(){
        //initial state: choose random day for a lecture
        Day day = lectures.get(0).chooseRandomDay();
        day.addLecture(lectures.get(0));

        while(true){
            //update all nodes
            while(updateLecturesDays()){}

            //select one day for a lecture
            if(checkFatalEnd() || checkEnd())
                break;

            setLectureDay();
        }

    }

    public boolean updateLecturesDays(){
        boolean changes_made = false;
        //for each lecture
        for(int i = 0; i < lectures.size(); i++){
            Lecture lecture = lectures.get(i);
            //if no day set already
            if(lecture.getLectureDay() != null)
                continue;
            if(lecture.getLinkedLectures().size() == 0){
                Day day = lecture.chooseRandomDay();
                day.addLecture(lecture);
                continue;
            }
            //for each linked lecture
            for(int j = 0; j < lecture.getLinkedLectures().size(); j++){
                Lecture linked = lecture.getLinkedLectures().get(j);
                Day linkedDay = linked.getLectureDay();
                if(linkedDay != null && lecture.getPosibleDays().contains(linkedDay)){
                    lecture.getPosibleDays().remove(linkedDay);
                    changes_made = true;
                }
            }
        }
        return changes_made;
    }

    public void setLectureDay(){
        //check the node lecture with less posible days
        int lessDays = 9999;
        Lecture lecture = null;
        for(int i = 0; i < lectures.size(); i++){
            if(lectures.get(i).getLectureDay() != null)
                continue;
            int days = lectures.get(i).getPosibleDays().size();
            if(days < lessDays){
                lessDays = days;
                lecture = lectures.get(i);
                if(lessDays < 2)
                    break;
            }
        }
        Day day = lecture.chooseRandomDay();
        day.addLecture(lecture);
    }

    public void showResult(){
        days.forEach((day) -> {
            if(day.getLectures().size() > 0) day.info();
        });
    }
}
