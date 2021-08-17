package de.klyman.increment;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import de.klyman.increment.model.ApiError;

/**
 * Application error controller which is called for unhandled errors
 */
@Controller
public class AppErrorController implements ErrorController {

    @RequestMapping("/error")
	@ResponseBody
	public ResponseEntity<Object> getErrorPath() {
    	Exception ex = new Exception("Error processing the request");
    	ApiError errorResponse = new ApiError(HttpStatus.BAD_REQUEST, ex);
		return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
	}

}
