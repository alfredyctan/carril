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
public class Logon implements FixMessage {

	private static final long serialVersionUID = -1152225918988882483L;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@Accessors(chain = false)
	private Context context;
	
	@Carril(wire = Wire.Fix)
	private Integer encryptMethod;

	@Carril(wire = Wire.Fix)
	private Integer heartBtInt;

	@Carril(wire = Wire.Fix)
	private Boolean resetSeqNumFlag;

}
