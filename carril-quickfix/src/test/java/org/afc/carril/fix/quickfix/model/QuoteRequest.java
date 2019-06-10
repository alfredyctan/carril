package org.afc.carril.fix.quickfix.model;

import java.util.List;

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
public class QuoteRequest implements FixMessage {

	private static final long serialVersionUID = -1152225918988882483L;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@Accessors(chain = false)
	private Context context;

	@Carril(wire = Wire.Fix)
	private String quoteReqID;

//	@Carril(wire = Wire.Fix)
//	private Integer noRelatedSym;

	@Carril(wire = Wire.Fix, implement = Symbol.class)
	private List<Symbol> symbols;
}
