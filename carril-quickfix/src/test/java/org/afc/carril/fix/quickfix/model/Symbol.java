package org.afc.carril.fix.quickfix.model;

import org.afc.carril.annotation.Carril;
import org.afc.carril.annotation.Carril.Wire;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Symbol {

	@Carril(wire = Wire.Fix)
	private String symbol;

	@Carril(wire = Wire.Fix)
	private String securityID;

	@Carril(wire = Wire.Fix)
	private String securityIDSource;

	@Carril(wire = Wire.Fix)
	private String securityDesc;

	@Carril(wire = Wire.Fix)
	private Integer encodedSecurityDescLen;

	@Carril(wire = Wire.Fix)
	private String encodedSecurityDesc;

	@Carril(wire = Wire.Fix)
	private Character side;
}
