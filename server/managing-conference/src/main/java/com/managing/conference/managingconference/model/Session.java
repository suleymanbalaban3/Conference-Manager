package com.managing.conference.managingconference.model;



import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "session")
public class Session {

    private long id;
    private String description;
    @Temporal(TemporalType.TIME)
    private Date startTime;
    @Temporal(TemporalType.TIME)
    private Date endTime;
    private int duration;
    private int track;

    public Session() {

    }

    public Session(String description, Date startTime, Date endTime, int duration, int track) {
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.track = track;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "description", nullable = false)
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "start_time", nullable = true)
    public Date getStartTime() {
        return startTime;
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Column(name = "end_time", nullable = true)
    public Date getEndTime() {
        return endTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Column(name = "duration", nullable = false)
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Column(name = "track", nullable = true)
    public int getTrack() {
        return track;
    }
    public void setTrack(int track) {
        this.track = track;
    }

    @Override
    public String toString() {
        return "Session [id=" + getId() + ", description=" + getDescription() +
                ", track=" + getTrack() + ", endTime=" + getEndTime() +
                ", startTime=" + getStartTime() + ", duration=" + getDuration() + " min]";
    }

}