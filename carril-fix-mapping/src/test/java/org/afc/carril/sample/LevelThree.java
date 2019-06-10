package org.afc.carril.sample;

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
public class LevelThree {

	@Carril(name = "9301", wire = Wire.Fix)
	private String name;

	@Carril(name = "9302", wire = Wire.Fix)
	private int value;
	
}
