package dev.beomseok.company.dto.work;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
public class WorkResponse<T extends WorkInfo> {
    private List<T> detail;
    private long sum;

    public WorkResponse() {
        this.detail = new ArrayList<>();
        this.sum = 0;
    }

    public void addMinutes(long minute){
        this.sum += minute;
    }

    public void addWorkInfo(T workInfo){
        this.detail.add(workInfo);
    }

    public void sortDetail(){
        this.detail.sort(new Comparator<>() {
            @Override
            public int compare(T o1, T o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
    }
}
