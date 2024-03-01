package dev.beomseok.company.dto;

import dev.beomseok.company.domain.Work;
import lombok.Getter;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Getter
public class WorkResponse {
    private List<WorkInfo> detail;
    private long sum;

    public WorkResponse(List<Work> result) {
        this.detail = new ArrayList<>();
        this.sum = 0;

        result.forEach(work -> {
            long minutes = Duration.between(work.getWorkStart(), work.getWorkEnd()).toMinutes();
            addMinutes(minutes);
            addWorkInfo(new WorkInfo(work.getWorkStart().toLocalDate(),minutes));
        });
    }

    private void addMinutes(long minute){
        this.sum += minute;
    }

    private void addWorkInfo(WorkInfo workInfo){
        this.detail.add(workInfo);
    }
}
