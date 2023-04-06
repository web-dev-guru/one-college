package org.college.model;
import lombok.Data;


@Data
public class TrainSchedule {

    private Integer id;

    private String line;

    private Integer departure;

    private Integer arrival;
    public TrainSchedule(String line, Integer departure, Integer arrival){
        this.line= line;
        this.departure=departure;
        this.arrival= arrival;

    }
}
