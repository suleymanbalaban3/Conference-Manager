package com.managing.conference.managingconference.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.managing.conference.managingconference.model.Session;
import com.managing.conference.managingconference.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.managing.conference.managingconference.exception.ResourceNotFoundException;
import com.managing.conference.managingconference.repository.SessionRepository;

@RestController
@RequestMapping("/api")
public class SessionController {
    @Autowired
    private SessionService sessionService;

    @GetMapping("/sessions")
    public List<Session> getAllSessions() {
        return sessionService.getSessionRepository().findAll();
    }

    @PostMapping("/sessions")
    public Session createSession(@Valid @RequestBody Session session) {
        List<Session>sessions = sessionService.getSessionRepository().findAll(Sort.by(Sort.Direction.ASC, "endTime"));
        session = sessionService.planningSession(sessions, session);
        return sessionService.getSessionRepository().save(session);
    }

    @DeleteMapping("/sessions/{id}")
    public Map<String, Boolean> deleteSession(@PathVariable(value = "id") Long sessionId){
        Map<String, Boolean> response = new HashMap<>();
        try{
            Session session = sessionService.getSessionRepository().findById(sessionId)
                    .orElseThrow(() -> new ResourceNotFoundException("Session not found for this id :: " + sessionId));

            sessionService.getSessionRepository().delete(session);
            response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
        }catch (Exception e){
             response = new HashMap<>();
            response.put("deleted", Boolean.FALSE);
        }

        return response;
    }
}