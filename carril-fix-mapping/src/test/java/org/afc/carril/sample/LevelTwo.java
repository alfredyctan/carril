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
public class LevelTwo {

	@Carril(name = "9201", wire = Wire.Fix)
	private String name;

	@Carril(name = "9202", wire = Wire.Fix)
	private int value;
	
	@Carril(name = "9300", wire = Wire.Fix, declare = List.class, implement = LevelThree.class)
	private List<LevelThree> threes;

}
