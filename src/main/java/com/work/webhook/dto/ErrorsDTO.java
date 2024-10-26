package com.work.webhook.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ErrorsDTO implements java.io.Serializable{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The code. */
	private Integer code;
	
	/** The descroption. */
	private String description;
}
