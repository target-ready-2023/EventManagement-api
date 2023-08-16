package com.target.eventmanagementsystem.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.target.eventmanagementsystem.models.Gender;
import com.target.eventmanagementsystem.models.User;
import com.target.eventmanagementsystem.models.UserRoles;
import com.target.eventmanagementsystem.service.EventRegistrationService;
import com.target.eventmanagementsystem.service.EventService;
import com.target.eventmanagementsystem.service.UserService;

@WebMvcTest
public class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @MockBean
    private EventRegistrationService eventRegistrationService;

    @MockBean
    private UserService userService;

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldGetAllUsers() throws Exception{

        List<User> mockUsers = Arrays.asList(
            new User((long) 1, "FN 1", "LN1", LocalDate.of(2023, 9, 20),Gender.MALE,"email 1","password 1",UserRoles.STUDENT),
            new User((long) 2, "FN 2", "LN2", LocalDate.of(2023, 4, 3),Gender.FEMALE,"email 2","password 2",UserRoles.ADMIN)
            );

        when(userService.getAllUsers()).thenReturn(mockUsers);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data", hasSize(2)));

        verify(userService).getAllUsers();
    }

    @Test
    void shouldGetUserById() throws Exception{

        User mockUser = new User((long) 1, "FN 1", "LN1", LocalDate.of(2023, 9, 20),Gender.MALE,"email 1","password 1",UserRoles.STUDENT);
            
        when(userService.getUserById((long) 1)).thenReturn(mockUser);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/1"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(mockUser.getId()));

        verify(userService).getUserById((long) 1);
    }

    @Test
    void shouldCreateUser() throws Exception {
        User mockUser = new User(null, "FN 1", "LN1", LocalDate.of(2023, 9, 20),Gender.MALE,"email 1","password 1",UserRoles.STUDENT);
        User createdUser = new User((long) 1, "FN 1", "LN1", LocalDate.of(2023, 9, 20),Gender.MALE,"email 1","password 1",UserRoles.STUDENT);

        when(userService.createUser(mockUser)).thenReturn(createdUser);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
        .content(asJsonString(mockUser))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(userService).createUser(mockUser);
    }

    @Test
    void shouldUpdateUser() throws Exception {
        long userId = (long) 1;
        User updateUser = new User(null, "FN 1", "LN1", LocalDate.of(2023, 9, 20),Gender.MALE,"email 1","password 1",UserRoles.STUDENT);
        User updatedUser = new User((long) 1, "FN 1", "LN1", LocalDate.of(2023, 9, 20),Gender.MALE,"email 1","password 1",UserRoles.STUDENT);

        when(userService.updateUser(updateUser)).thenReturn(updateUser);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/{userId}",userId)
        .content(asJsonString(updateUser))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());

        verify(userService).updateUser(updatedUser);
    }

    @Test
    void shouldDeleteUser() throws Exception {
        long userId = (long) 1;

        doNothing().when(userService).deleteUser(userId);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/{id}", userId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(userService).deleteUser((long)1);
}
}
