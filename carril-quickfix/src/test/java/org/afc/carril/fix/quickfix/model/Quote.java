package org.afc.carril.fix.quickfix.model;

import org.afc.carril.annotation.Carril;
import org.afc.carril.annotation.Carril.Wire;
import org.afc.carril.message.FixMessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Quote implements FixMessage {

	private static final long serialVersionUID = -1152225918988882483L;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@Accessors(chain = false)
	private Context context;
	
	@Carril(wire = Wire.Fix)
	private String quoteReqID;

	@Carril(wire = Wire.Fix)
	private String quoteID;

	@Carril(wire = Wire.Fix)
	private String symbol;

	@Carril(wire = Wire.Fix)
	private String securityID;

	@Carril(wire = Wire.Fix)
	private String securityIDSource;

	@Carril(wire = Wire.Fix)
	private Integer encodedSecurityDescLen;

	@Carril(wire = Wire.Fix)
	private String encodedSecurityDesc;

	@Carril(wire = Wire.Fix)
	private Character side;

}
