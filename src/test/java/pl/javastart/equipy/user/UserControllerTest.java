package pl.javastart.equipy.user;

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
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldGetAllUsersDto() throws Exception {
        mockMvc.perform(get("/api/users")).andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.length()").value(4))
                .andDo(print());
    }


    @Test
    public void shouldGetByLastName() throws Exception {
        mockMvc.perform(get("/api/users").param("lastName", "wal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.length()").value(1))
                .andDo(print());
    }


    @Test
    @DirtiesContext
    public void shouldCreateUser() throws Exception {
        UserDto userToAdd = new UserDto();
        userToAdd.setFirstName("Walter");
        userToAdd.setLastName("WalterLastName");
        userToAdd.setPesel("11122233312");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userToAdd)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Walter"))
                .andDo(print());
    }


    @Test
    public void shouldThrowPeselDuplicationException() throws Exception {
        UserDto userToAdd = new UserDto();
        userToAdd.setFirstName("Barbra");
        userToAdd.setLastName("BarbraLastName");
        userToAdd.setPesel("11111111111");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userToAdd)))
                .andExpect(status().isConflict())
                .andDo(print());
    }

    @Test
    public void shouldNotAddUserWithProvidedIdAndThrowException() throws Exception {

        UserDto userToAdd = new UserDto();
        userToAdd.setId(199L);
        userToAdd.setFirstName("Barbra");
        userToAdd.setLastName("BarbraLastName");
        userToAdd.setPesel("11111111111");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userToAdd)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void shouldFindUserById() throws Exception {

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("jan"))
                .andDo(print());
    }

    @Test
    @DirtiesContext
    public void shouldUpdateUser() throws Exception {
        UserDto userToUpdate = new UserDto();
        userToUpdate.setId(1L);
        userToUpdate.setFirstName("Jan");
        userToUpdate.setLastName("JanLastName");
        userToUpdate.setPesel("11111111111");

        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper()
                        .writeValueAsString(userToUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("JanLastName"))
                .andDo(print());
    }

    @Test
    public void shouldNotUpdateIfThereIsAnotherUserWithProvidedPesel() throws Exception {
        UserDto userToUpdate = new UserDto();
        userToUpdate.setId(1L);
        userToUpdate.setFirstName("Jan");
        userToUpdate.setLastName("JanLastName");
        userToUpdate.setPesel("11111111114");

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper()
                                .writeValueAsString(userToUpdate)))
                .andExpect(status().isConflict())
                .andDo(print());
    }


    @Test
    public void shouldNotUpdateIfTheThereIsNoIdProvided() throws Exception {
        UserDto userToUpdate = new UserDto();
        userToUpdate.setFirstName("Jan");
        userToUpdate.setLastName("JanLastName");
        userToUpdate.setPesel("11111111111");

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper()
                                .writeValueAsString(userToUpdate)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

}