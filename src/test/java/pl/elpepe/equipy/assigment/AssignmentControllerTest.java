package pl.elpepe.equipy.assigment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@SpringBootTest
@AutoConfigureMockMvc
class AssignmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DirtiesContext
    public void shouldCreateAssignment() throws Exception {
        AssignmentDto assignment = new AssignmentDto();
        assignment.setUserId(1L);
        assignment.setAssetId(2L);

        mockMvc.perform(post("/api/assignments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(assignment)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void shouldNotCreateAssignmentWhenUserDoesNotExist() throws Exception {
        AssignmentDto assignment = new AssignmentDto();
        assignment.setUserId(60L);
        assignment.setAssetId(2L);

        mockMvc.perform(post("/api/assignments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(assignment)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void shouldNotCreateAssignmentWhenAssetDoesNotExist() throws Exception {
        AssignmentDto assignment = new AssignmentDto();
        assignment.setUserId(1L);
        assignment.setAssetId(80L);


        mockMvc.perform(post("/api/assignments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(assignment)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void shouldNotCreateAssignmentsIfAssetIsInUse() throws Exception {
        AssignmentDto assignment = new AssignmentDto();
        assignment.setUserId(3L);
        assignment.setAssetId(5L);


        mockMvc.perform(post("/api/assignments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(assignment)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }


    @Test
    @DirtiesContext
    public void shouldReturnAssignment() throws Exception {
        mockMvc.perform(post("/api/assignments/2/end")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andDo(print());
    }

    @Test
    public void shouldNotReturnAssignmentThatHasBeenFinished() throws Exception {
        mockMvc.perform(post("/api/assignments/1/end")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void shouldNotReturnAssignmentWithWrongId() throws Exception {
        mockMvc.perform(post("/api/assignments/100/end"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

}