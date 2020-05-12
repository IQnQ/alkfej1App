package model;

import javafx.beans.property.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Survey {

    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private ArrayList<Question> questions;
    private LocalDate createdDate;
    private LocalDate fromD, toD;
    private LocalTime fromT, toT, maxT;
    private LocalDateTime from;
    private LocalDateTime to;
    private IntegerProperty limit = new SimpleIntegerProperty();
    private BooleanProperty isFilledOut = new SimpleBooleanProperty();

    public Survey(String name) {
        this.name.set(name);
        this.createdDate = LocalDate.now();
        this.questions = new ArrayList<Question>();
        this.isFilledOut.set(false);
    }

    public Survey(int id, String name, boolean isFilledOut) {
        this.id.set(id);
        this.name.set(name);
        this.isFilledOut.set(isFilledOut);
    }

    public Survey() {
        this.name.set("névtelen");
        this.createdDate = LocalDate.now();
        this.isFilledOut.set(false);
    }

    public Survey(int id, String name, String cDate, boolean isFilled, String fromD, String toD, String fromT, String toT, String maxT, int limit) {
        try {
            this.id.set(id);
            this.name.set(name);
            this.createdDate = LocalDate.parse(cDate);
            this.isFilledOut.set(isFilled);
            this.fromD = LocalDate.parse(fromD);
            this.toD = LocalDate.parse(toD);
            this.fromT = LocalTime.parse(fromT);
            this.toT = LocalTime.parse(toT);
            this.from = LocalDateTime.of(this.fromD,this.fromT);
            this.to = LocalDateTime.of(this.toD, this.toT);
            this.maxT = LocalTime.parse(maxT);
            this.limit.set(limit);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void set(String sName, LocalDate fromD, LocalDate toD, LocalTime fromT, LocalTime toT, LocalTime maxT, int limit) {
        this.name.set(sName);
        this.fromD = fromD;
        this.toD = toD;
        this.fromT = fromT;
        this.toT = toT;
        this.maxT = maxT;
        this.limit.set(limit);

    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void addQuestion(Question q) {
        questions.add(q);
    }

    public LocalDate getFromD() {
        return fromD;
    }

    public LocalDate getToD() {
        return toD;
    }

    public LocalTime getFromT() {
        return fromT;
    }

    public LocalTime getToT() {
        return toT;
    }

    public LocalTime getMaxT() {
        return maxT;
    }

    public int getLimit() {
        return limit.get();
    }

    public IntegerProperty limitProperty() {
        return limit;
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isIsFilledOut() {
        return isFilledOut.get();
    }

    public BooleanProperty isFilledOutProperty() {
        return isFilledOut;
    }

    public void setIsFilledOut(boolean isFilledOut) {
        this.isFilledOut.set(isFilledOut);
    }


}
