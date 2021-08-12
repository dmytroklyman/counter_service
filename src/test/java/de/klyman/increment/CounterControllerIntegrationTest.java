package de.klyman.increment;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CounterController.class)
public class CounterControllerIntegrationTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Test
    void whenSendGetRequest() throws Exception {
		String url = "/counter";

		this.mockMvc.perform(get(url))
		  .andExpect(status().isOk())
		  .andExpect(content().json("{counter: 0}"));
    }
	
	@Test
    void whenIncrementDecrement() throws Exception {
		String url = "/counter";

		this.mockMvc.perform(get(url))
		  .andExpect(status().isOk())
		  .andExpect(content().json("{counter: 0}"));
		
		Integer decrementAmount = 10;
		
		for (Integer i = 0; i < decrementAmount; ++i) {
			this.mockMvc.perform(delete(url))
				.andExpect(status().isOk())
				.andExpect(content().json("{counter: " + (-i - 1) + "}"));
		}
		
		Integer incrementAmount = 20;
		
		for (Integer i = 0; i < incrementAmount; ++i) {
			this.mockMvc.perform(put(url))
				.andExpect(status().isOk())
				.andExpect(content().json("{counter: " + (i - decrementAmount + 1) + "}"));
		}
    }
}
