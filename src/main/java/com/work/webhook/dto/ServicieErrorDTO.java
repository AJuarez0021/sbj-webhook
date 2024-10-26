package com.work.webhook.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class ServicieErrorDTO  implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The status. */
	@JsonIgnore
	private HttpStatus status;
        
        private String message;
        
        private List<ErrorsDTO> errors;
	
	@Builder
	public ServicieErrorDTO(String message, HttpStatus status, List<ErrorsDTO> errors) {
			
		this.status = status;
		this.message = message;
                this.errors = errors;
	}
	
}
