package com.managing.conference.managingconference.repository;


import com.managing.conference.managingconference.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long>{

}