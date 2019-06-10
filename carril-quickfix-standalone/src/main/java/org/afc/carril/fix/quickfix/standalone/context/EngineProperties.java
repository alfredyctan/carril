package org.afc.carril.fix.quickfix.standalone.context;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ConfigurationProperties("engine")
public class EngineProperties {

	private static final String DEFAULT_BREAK_PATTERN = "wait=";
	
	@Data
	@Accessors(chain = true)
	public static class CarrilSettings {
		private String file;
		private String prefix;
	}
	
	@Data
	@Accessors(chain = true)
	public static class Sessions {
		private String id;
		private String dictionary;
	}

	@Data
	@Accessors(chain = true)
	public static class Capture {
		private String filter;
		private String file;
	}

	@Data
	@Accessors(chain = true)
	public static class AutoResponse {
		private String filter;
		private String repository;
		private String breakPattern = DEFAULT_BREAK_PATTERN;
		private boolean breakInclude = true;
		private boolean cyclic = true;
	}

	@Data
	@Accessors(chain = true)
	public static class Fixman {
		private String draft;
		private String sent;
		private String error;
		private String breakPattern = DEFAULT_BREAK_PATTERN;
		private boolean breakInclude = true;
	}
	
		
	private CarrilSettings carril; 
	
	private List<Capture> captures;

	private List<Sessions> sessions;
	
	private List<AutoResponse> autoResponses;
	
	private Fixman fixman;
}
