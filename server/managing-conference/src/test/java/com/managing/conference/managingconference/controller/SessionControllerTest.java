package com.managing.conference.managingconference.controller;

import com.managing.conference.managingconference.AbstractTest;
import com.managing.conference.managingconference.model.Session;
import com.managing.conference.managingconference.service.SessionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.anyOf;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SessionControllerTest extends AbstractTest {
    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void createSessionTest() throws Exception {
        String uri = "/api/sessions";
        Session session = new Session();
        session.setDescription("test");
        session.setDuration(10);

        String inputJson = super.mapToJson(session);
        MvcResult mvcResult = mvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        Session createdSession = super.mapFromJson(content, Session.class);
        assertEquals(200, status);
        deleteSession((int)createdSession.getId());

    }

    @Test
    public void getAllSessionsTest() throws Exception {
        String uri = "/api/sessions";
        MvcResult mvcResult = mvc.perform(get(uri))
                .andExpect(status().isOk())
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Session[] sessionlist = super.mapFromJson(content, Session[].class);
        assertTrue(sessionlist.length >= 0);
    }


    @Test
    public void deleteSessionTest() throws Exception {
        MvcResult mvcResult = deleteSession(1);

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        String  expected = "{\"deleted\":false}" + "{\"deleted\":true}";

        assertTrue(expected.contains(content));
    }

    public MvcResult deleteSession(int id) throws Exception{
        String uri = "/api/sessions/" + id;

        MvcResult mvcResult = mvc.perform(delete(uri))
                .andExpect(status().isOk())
                .andReturn();

        return mvcResult;
    }
}