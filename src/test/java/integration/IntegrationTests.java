package integration;


import com.minesweeper.MinesweeperApplication;
import com.minesweeper.dto.BoardDTO;
import com.minesweeper.dto.NewBoardRequestDTO;
import com.minesweeper.dto.Status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MinesweeperApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class IntegrationTests {
    private static final TestRestTemplate restTemplate = new TestRestTemplate();

    @LocalServerPort
    private int port;

    @Test
    public void createBoardTest() {
        final NewBoardRequestDTO newBoardRequestDTO = new NewBoardRequestDTO();
        newBoardRequestDTO.setUserId(1);
        newBoardRequestDTO.setRows(10);
        newBoardRequestDTO.setColumns(10);
        newBoardRequestDTO.setMines(10);

        final HttpEntity<NewBoardRequestDTO> entity = new HttpEntity<>(newBoardRequestDTO, new HttpHeaders());
        final ResponseEntity<BoardDTO> response = restTemplate.exchange(buildURL("/api/v1/boards"),
                HttpMethod.POST, entity, BoardDTO.class);

        final BoardDTO boardDTO = response.getBody();
        assertNotNull(boardDTO);
        assertEquals(Status.PLAYING, boardDTO.getStatus());
        assertNotNull(boardDTO.getStartTime());
        assertNotNull(boardDTO.getLastTimePlayed());
        assertNotNull(boardDTO.getCells());
        assertEquals(newBoardRequestDTO.getUserId(), boardDTO.getUserId());
        assertEquals(newBoardRequestDTO.getUserId(), boardDTO.getUserId());
        assertEquals(newBoardRequestDTO.getRows(), boardDTO.getRows());
        assertEquals(newBoardRequestDTO.getColumns(), boardDTO.getColumns());
    }

    private String buildURL(String uri) {
        return "http://localhost:" + port + uri;
    }
}

