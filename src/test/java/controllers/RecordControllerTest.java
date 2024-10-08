package controllers;

import org.example.Main;
import org.example.SessionFactoryConfiguration;
import org.example.controllers.RecordController;
import org.example.databases.Cat;
import org.example.databases.CatRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;


import javax.persistence.EntityNotFoundException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ContextConfiguration(classes = {
        Main.class,
        CatRepository.class,
        SessionFactoryConfiguration.class
})
@WebMvcTest(RecordController.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CatRepository catRepository;


    @Test
    public void testAddRecordSuccess() throws Exception {
        String record = "Test record";
        mockMvc.perform(post("/add")
                        .param("record", record)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is(200))
                .andExpect(content().string("Record added successfully"));
    }

    @Test void testGetRecordSuccess() throws Exception {
        Cat cat = catRepository.get(1L);
        Assertions.assertNull(cat);
        catRepository.put(new Cat(1, "test"));
        mockMvc.perform(get("/get")
                        .param("id", String.valueOf(1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().json("{\"id\":1,\"record\":\"test\"}"));
    }

    @Test
    public void testGetCatNotFound() throws Exception {
        mockMvc.perform(get("/get")
                        .param("id", "999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404))
                .andExpect(result ->
                        Assertions.assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
                .andExpect(result ->
                        Assertions
                                .assertEquals("Cat with id 999 not found", result.getResolvedException()
                                .getMessage()));
    }

    @Test
    public void testUpdateRecordSuccess() throws Exception {
        Long id = 1L;
        String updatedRecord = "Updated Cat Record";
        catRepository.put(new Cat(id, "Old Cat Record"));
        mockMvc.perform(post("/update")
                        .param("id", String.valueOf(id))
                        .param("new_record", updatedRecord)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is(200))
                .andExpect(content().string("Record updated successfully"));
        Assertions.assertEquals(catRepository.get(id).getRecord(), updatedRecord);
    }

    @Test
    public void testUpdateRecordNotFound() throws Exception {
        Long id = 999L;
        String updatedRecord = "Updated Cat Record";
        mockMvc.perform(post("/update")
                        .param("id", String.valueOf(id))
                        .param("new_record", updatedRecord)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        Assertions.assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
                .andExpect(result ->
                        Assertions
                                .assertEquals("Record not found with id: " + id, result.getResolvedException()
                                        .getMessage()));
    }

    @Test
    public void testRemoveRecordSuccess() throws Exception {
        Long id = 1L;
        Cat existingCat = new Cat(id, "Cat to be deleted");
        catRepository.put(existingCat);
        mockMvc.perform(post("/remove")
                        .param("id", String.valueOf(id))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().string("Record deleted successfully"));
    }

    @Test
    public void testRemoveRecordNotFound() throws Exception {
        Long id = 999L; // Assuming this ID does not exist
        mockMvc.perform(post("/remove")
                        .param("id", String.valueOf(id))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        Assertions.assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
                .andExpect(result ->
                        Assertions.assertEquals("Record not found with id: " + id, result.getResolvedException().getMessage()));
    }
}




