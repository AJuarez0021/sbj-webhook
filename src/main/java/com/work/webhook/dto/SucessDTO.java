package com.work.webhook.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SucessDTO<T>  {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The content. */
	private transient T content;
	
        private String message;
	
}
