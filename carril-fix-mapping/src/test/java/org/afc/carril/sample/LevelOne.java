package org.afc.carril.sample;

import java.util.List;

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
public class LevelOne {

	@Carril(name = "9101", wire = Wire.Fix)
	private String name;

	@Carril(name = "9102", wire = Wire.Fix)
	private int value;
	
	@Carril(name = "9200", wire = Wire.Fix, implement = LevelTwo.class)
	private List<LevelTwo> twos;

}
