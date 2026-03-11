package org.example.kinobe.controller;

import org.example.kinobe.misc.Status;
import org.example.kinobe.model.Showing;
import org.example.kinobe.service.ShowingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import tools.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(ShowingController.class)
class ShowingControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ShowingService service;

    static Stream<LocalDate> invalidDates(){
        return Stream.of(
                LocalDate.now().minusDays(1),
                LocalDate.now().plusMonths(3).plusDays(1)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidDates")
    void ShowingController_createShowingWithInvalidDate_ReturnsBAD_REQUEST(){

    }

    @Test
    void ShowingController_createShowing() throws Exception {
        Showing showing = new Showing(1, LocalDate.now(), LocalTime.now(), Status.ACTIVE);
        when(service.createShowing(any(Showing.class))).thenReturn(showing);

        mockMvc.perform(post("/showing")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(showing)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.showingId").value(showing.getShowingId()))
                .andExpect(jsonPath("$.status").value(showing.getStatus()));
    }

    @Test
    void ShowingController_showingList_ReturnHttpStatusAndJson() throws Exception {
        List<Showing> showingList = new ArrayList<>();

        for(int i = 1;i <= 10;i++){
            showingList.add(Showing.builder().showingId(i).build());
        }

        when(service.showingList()).thenReturn(showingList);

        mockMvc.perform(get("/showing"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(10));
    }


    //Can be made when the Movie class is done
    @Test
    void ShowingController_showingListByMovieFK() {

    }

    //Can be made when the Theater class is done
    @Test
    void ShowingController_showingListByTheaterFK() {
    }

    @Test
    void ShowingController_getShowingById_ReturnShowingIdAndStatus() throws Exception {
        Showing showing = new Showing(1, LocalDate.now(), LocalTime.now(), Status.ACTIVE);

        when(service.getShowingById(1)).thenReturn(showing);

        mockMvc.perform(get("/showing/{showingId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.showingId").value(1))
                .andExpect(jsonPath("$.status").value(Status.ACTIVE));
    }

    @Test
    void ShowingController_updateShowing_ReturnShowingIdAndStatus() throws Exception {
        Showing showing = new Showing(1, LocalDate.now(), LocalTime.now(), Status.ACTIVE);

        when(service.updateShowing(any(Showing.class))).thenReturn(showing);

        mockMvc.perform(put("/showing/{showingId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(showing)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.showingId").value(1))
                .andExpect(jsonPath("$.status").value(Status.ACTIVE));

    }

    @Test
    void ShowingController_deleteShowingById_ReturnDeletedShowing() throws Exception {
        Showing showing = new Showing(1, LocalDate.now(), LocalTime.now(), Status.ACTIVE);
        when(service.deleteShowingById(1)).thenReturn(showing);

        mockMvc.perform(delete("/showing/{showingId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.showingId").value(1))
                .andExpect(jsonPath("$.status").value(Status.ACTIVE));
    }
}