package com.managing.conference.managingconference.service;

import com.managing.conference.managingconference.model.Session;
import com.managing.conference.managingconference.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

@Service
public class SessionService {
    @Autowired
    private SessionRepository sessionRepository;
    public Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC+3"));
    public Calendar calendarForNewSessionStartTime = Calendar.getInstance(TimeZone.getTimeZone("UTC+3"));
    public Calendar calendarForNewSessionEndTime = Calendar.getInstance(TimeZone.getTimeZone("UTC+3"));

    public SessionRepository getSessionRepository(){
        return sessionRepository;
    }

    public Session planningSession(List<Session> plannedSessions, Session newSession){
        Date endTime;
        Session lastSession;

        if (plannedSessions.size() == 0){
            lastSession = new Session();
            lastSession.setEndTime(createStartSession(9, 0));
            lastSession.setTrack(1);
        } else {
            lastSession = plannedSessions.get(plannedSessions.size()-1);
        }

        endTime = lastSession.getEndTime();
        endTime = addSessionTime(endTime, Calendar.MINUTE, newSession.getDuration());

        newSession.setStartTime(lastSession.getEndTime());
        newSession.setEndTime(endTime);

        evaluateSessionStartTime(lastSession, newSession);

        return  newSession;
    }


    public void evaluateSessionStartTime(Session lastSession, Session newSession){
        Date date = null;
        Session netWorkingEventSession = null;
        Session lunchSession = null;
        calendarForNewSessionStartTime.setTime(lastSession.getEndTime());
        calendarForNewSessionEndTime.setTime(newSession.getEndTime());



        if((calendarForNewSessionEndTime.get(Calendar.HOUR_OF_DAY) > 17) ||
                (calendarForNewSessionEndTime.get(Calendar.HOUR_OF_DAY) == 17 &&
                        calendarForNewSessionEndTime.get(Calendar.MINUTE) >= 0)){       //End of Day
            date = createStartSession(9, 0);

            date = addSessionTime(date, Calendar.DATE, 1);
            newSession.setStartTime(date);

            date = addSessionTime(date, Calendar.MINUTE, newSession.getDuration());
            newSession.setEndTime(date);
            newSession.setTrack(lastSession.getTrack()+1);

            netWorkingEventSession = createNetworkEventSession(lastSession);
            sessionRepository.save(netWorkingEventSession);
        }else if((calendarForNewSessionEndTime.get(Calendar.HOUR_OF_DAY) >= 12 &&
                calendarForNewSessionStartTime.get(Calendar.HOUR_OF_DAY) < 12)){        //Lunch Time
            date = createStartSession(13, 0);
            newSession.setStartTime(date);
            date = addSessionTime(date, Calendar.MINUTE, newSession.getDuration());
            newSession.setEndTime(date);
            newSession.setTrack(lastSession.getTrack());

            lunchSession = createLunchSession(lastSession);
            sessionRepository.save(lunchSession);
        }else {                                                                         //Rest of the Day
            newSession.setTrack(lastSession.getTrack());
        }
    }

    public Date createStartSession(int hourValue, int minuteValue){
        Date d = new Date();

        calendar.setTime(d);
        calendar.set(Calendar.HOUR_OF_DAY, hourValue);
        calendar.set(Calendar.MINUTE, minuteValue);

        return calendar.getTime();
    }

    public Date addSessionTime(Date date, int field, int value){
        calendar.setTime(date);
        calendar.add(field, value);

        date = calendar.getTime();
        return date;
    }

    public Session createNetworkEventSession(Session lastSession){
        Date end = createStartSession(17, 0);
        Date start = createStartSession(16, 0);
        if(findDifferenceInMinutes(lastSession.getEndTime(), start) > 0){
            start = lastSession.getEndTime();
        }
        int duration = findDifferenceInMinutes(end, start);
        return new Session("Networking Event", start, end, duration+1, lastSession.getTrack());
    }

    public Session createLunchSession(Session lastSession){
        Date start = createStartSession(12, 0);
        Date end = createStartSession(13, 0);
        return new Session("Lunch", start, end, 60, lastSession.getTrack());
    }

    public int findDifferenceInMinutes(Date start, Date end){
        Long diffInMillies = Math.abs(end.getTime() - start.getTime());
        Long diffInMinutes = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);

        return Integer.parseInt(diffInMinutes.toString());
    }
}
