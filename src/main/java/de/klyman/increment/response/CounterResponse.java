package de.klyman.increment.response;

public class CounterResponse {
	
	private Integer counter;
	
	public CounterResponse(Integer c) { 
       this.counter = c;
    }
	
	public Integer getCounter() {
		return this.counter;
	}

}
