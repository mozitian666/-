package com.coursesystem.dto;

import java.util.List;

public class CourseCreateRequest {
    private String code;
    private String name;
    private String description;
    private Integer credits;
    private Integer capacity;
    private String dayOfWeek;
    private String startTime;
    private String endTime;
    private List<Long> prerequisiteIds;

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public String getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

    public List<Long> getPrerequisiteIds() { return prerequisiteIds; }
    public void setPrerequisiteIds(List<Long> prerequisiteIds) { this.prerequisiteIds = prerequisiteIds; }
}

