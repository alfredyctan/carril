package org.afc.carril.message;

import java.util.Map;

import org.afc.carril.transport.AccessorMapping;

public interface QuickFixMessage extends FixMessage {
	
	public Map<String, AccessorMapping> getFixHeaderMap();

	public Map<String, AccessorMapping> getFixTrailerMap();
}
