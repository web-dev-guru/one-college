package org.college.controller;

import org.college.model.TrainSchedule;
import org.college.service.TrainScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/schedule")
public class TrainScheduleController {
    @Autowired
    private  TrainScheduleService trainScheduleService;

    @GetMapping()
    public List<TrainSchedule> getEntireSchedule()
    {
        return trainScheduleService.getFullSchedule();
    }


    @GetMapping("/{line}")
    public List<TrainSchedule> getScheduleByLineAndDeparture(@PathVariable String line, @RequestParam(required = false) Integer departure) {
        List<TrainSchedule> allTrains = trainScheduleService.getFullSchedule();
        return allTrains.stream()
                .filter(train -> train.getLine().equals(line))
                .filter(train -> departure == null || train.getDeparture() == departure)
                .collect(Collectors.toList());
    }
}
