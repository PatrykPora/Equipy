package pl.elpepe.equipy.asset;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class AssetControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @Test
    public void shouldGetAllAssets() throws Exception {
        mockMvc.perform(get("/api/assets")).andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.length()").value(10))
                .andDo(print());
    }

    @Test
    public void shouldFindAssetByNameOrSerialNumber() throws Exception {

        mockMvc.perform(get("/api/assets").param("text", "apP"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.length()").value(3))
                .andDo(print());
    }


    @Test
    @DirtiesContext
    public void shouldSaveAsset() throws Exception {
        AssetDto assetDtoToSave = new AssetDto();
        assetDtoToSave.setSerialNumber("123");
        assetDtoToSave.setDescription("description");
        assetDtoToSave.setName("new asset");
        assetDtoToSave.setCategory("Laptopy");

        mockMvc.perform(post("/api/assets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(assetDtoToSave)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("new asset"))
                .andDo(print());
    }


    @Test
    public void shouldThrowExceptionWhenSaveAssetWithDuplicatedSerialNumber() throws Exception {
        AssetDto assetDtoToSave = new AssetDto();
        assetDtoToSave.setSerialNumber("DI3576XO526716");
        assetDtoToSave.setDescription("description");
        assetDtoToSave.setName("new asset");
        assetDtoToSave.setCategory("Laptopy");

        mockMvc.perform(post("/api/assets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(assetDtoToSave)))
                .andExpect(status().isConflict())
                .andDo(print());
    }

    @Test
    public void shouldThrowExceptionWhenSaveAssetWithProvidedId() throws Exception {
        AssetDto assetDtoToSave = new AssetDto();
        assetDtoToSave.setId(178L);
        assetDtoToSave.setSerialNumber("DI3576XO526716");
        assetDtoToSave.setDescription("description");
        assetDtoToSave.setName("new asset");
        assetDtoToSave.setCategory("Laptopy");

        mockMvc.perform(post("/api/assets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(assetDtoToSave)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}