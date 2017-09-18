package org.afc.ferrocarril.message;

import java.util.Map;

import org.afc.ferrocarril.transport.AccessorMapping;

public interface QuickFixMessage extends FixMessage {
	
	public Map<String, AccessorMapping> getFixHeaderMap();

	public Map<String, AccessorMapping> getFixTrailerMap();
}
