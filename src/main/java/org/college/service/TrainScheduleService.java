package org.college.service;

import org.college.model.TrainSchedule;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainScheduleService {
    public List<TrainSchedule> getFullSchedule() {
        List<TrainSchedule> res = new ArrayList<TrainSchedule>();
        TrainSchedule t1= new TrainSchedule("Lakeshore", 800, 900);

        res.add(new TrainSchedule("Lakeshore", 800, 900));
        res.add(new TrainSchedule("Lakeshore", 1200, 1300));
        res.add(new TrainSchedule("Lakeshore", 1000, 1100));
        res.add(new TrainSchedule("Lakeshore", 1400, 1500));
        res.add(new TrainSchedule("Lakeshore", 1600, 1700));
        res.add(new TrainSchedule("Barrie", 0730, 930));
        res.add(new TrainSchedule("Barrie", 1030, 1230));
        res.add(new TrainSchedule("Barrie", 1230, 1430));
        return res;

    }
}
