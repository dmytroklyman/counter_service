package de.klyman.increment;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.klyman.increment.exception.MaxValueReachedException;
import de.klyman.increment.exception.MinValueReachedException;
import de.klyman.increment.model.ApiError;
import de.klyman.increment.model.Counter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class CounterController {

	private Integer counter = 0;
	
	/**
	 * GET request gets the current value of the counter
	 * 
	 * @return ResponseEntity<Object>
	 */
	@Operation(summary = "Get counter value")
	@ApiResponses(value = { 
		@ApiResponse(
		        responseCode = "200",
		        content = @Content(
		        	schema = @Schema(implementation = Counter.class)
		        ),
		        description = "Counter response"
		    )
	})
	@RequestMapping(value = "/counter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> get() {
		return this.generateResponse();
	}

	/**
	 * PUT request increments the counter
	 * 
	 * @return ResponseEntity<Object>
	 */
	@Operation(summary = "Increment counter value")
	@ApiResponses(value = { 
		@ApiResponse(
		        responseCode = "200",
		        content = @Content(
		        	schema = @Schema(implementation = Counter.class)
		        ),
		        description = "Counter response"
		    ),
		@ApiResponse(
				responseCode = "500",
				content = @Content(
					schema = @Schema(implementation = ApiError.class)
			    ),
				description = "Counter response - error processing of the request"
			)
	})
	@RequestMapping(value = "/counter", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> put() {
		try {
			this.increment();
		} catch (MaxValueReachedException ex) {
			ApiError errorResponse = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex);
			return this.generateResponse(errorResponse);
		}

		return this.generateResponse();
	}
	
	/**
	 * Usually POST requests are used to create the entity and PUT - for change.
	 * But in this case I implemented support of POST request just for easier usage.
	 * 
	 * POST request makes here the same as PUT request.
	 * 
	 * @return ResponseEntity<Object>
	 */
	@Operation(summary = "Increment counter value")
	@ApiResponses(value = { 
		@ApiResponse(
		        responseCode = "200",
		        content = @Content(
		        	schema = @Schema(implementation = Counter.class)
		        ),
		        description = "Counter response"
		    ),
		@ApiResponse(
				responseCode = "500",
				content = @Content(
					schema = @Schema(implementation = ApiError.class)
			    ),
				description = "Counter response - error processing of the request"
			)
	})
	@RequestMapping(value = "/counter", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> post() {		
		return this.put();
	}
	
	/**
	 * DELETE request decrements the counter
	 * 
	 * @return ResponseEntity<Object>
	 */
	@Operation(summary = "Decrement counter value")
	@ApiResponses(value = { 
		@ApiResponse(
		        responseCode = "200",
		        content = @Content(
		        	schema = @Schema(implementation = Counter.class)
		        ),
		        description = "Counter response"
		    ),
		@ApiResponse(
				responseCode = "500",
				content = @Content(
					schema = @Schema(implementation = ApiError.class)
			    ),
				description = "Counter response - error processing of the request"
			)
	})
	@RequestMapping(value = "/counter", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> delete() {
		try {
			this.decrement();
		} catch (MinValueReachedException ex) {
			ApiError errorResponse = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex);
			return this.generateResponse(errorResponse);
		}

		return this.generateResponse();
	}
	
	/**
	 * Increment counter
	 * 
	 * @return Integer
	 * @throws MaxValueReachedException 
	 */
	private Integer increment() throws MaxValueReachedException {
		if (counter == Integer.MAX_VALUE) {
			throw new MaxValueReachedException("Max counter value is reached. Further incrementing is impossible.");
		}
		
		return ++counter;
	}
	
	/**
	 * Decrement counter.
	 * 
	 * Task contained no details about the limits of the counter 
	 * and no information about if it can be negative or not,
	 * so I assume that counter is an integer and can be negative. 
	 * 
	 * So possible counter values: from MIN_VALUE (-2147483648) to MAX_VALUE (2147483647)
	 * 
	 * @return Integer
	 * @throws MinValueReachedException 
	 */
	private Integer decrement() throws MinValueReachedException {
		if (counter == Integer.MIN_VALUE) {
			throw new MinValueReachedException("Min counter value is reached. Further decrementing is impossible.");
		}

		return --counter;
	}
	
	/**
	 * Generate success response
	 * 
	 * @return Map<String, Integer>
	 */
	private ResponseEntity<Object> generateResponse() {
		Counter response = new Counter(this.counter);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Generate error response
	 * 
	 * @return Map<String, Integer>
	 */
	private ResponseEntity<Object> generateResponse(ApiError errorResponse) {
		return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
	}

}