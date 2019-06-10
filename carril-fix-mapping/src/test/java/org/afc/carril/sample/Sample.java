package org.afc.carril.sample;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.afc.carril.annotation.Carril;
import org.afc.carril.annotation.Carril.Section;
import org.afc.carril.annotation.Carril.Wire;
import org.afc.carril.message.FixMessage;
import org.afc.carril.text.FixDateFormat;
import org.afc.carril.text.FixDateTimeFormat;
import org.afc.carril.text.FixDateTimestampFormat;
import org.afc.carril.text.FixTimeFormat;
import org.afc.carril.text.FixTimestampFormat;

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
public class Sample implements FixMessage {

	private static final long serialVersionUID = -3032184418199850731L;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@Accessors(chain = false)
	private Context context;

	@Carril(name = "9100", wire = Wire.Fix, declare = List.class, implement = LevelOne.class)
	private List<LevelOne> ones;

	@Carril(wire = Wire.Fix, name = "MsgID")
	private String msgID;

	@Carril(wire = Wire.Fix)
	private String fromSchema;

	@Carril(name = "9501", wire = Wire.Fix, section = Section.Trailer)
	private Integer integer;

	@Carril(name = "9001", wire = Wire.Fix)
	private String string;

	@Carril(name = "9002", wire = Wire.Fix)
	private BigDecimal decimal;

	@Carril(name = "9003", wire = Wire.Fix)
	private Double floating;

	@Carril(name = "9004", wire = Wire.Fix, format = FixDateTimeFormat.class)
	private LocalDateTime datetime;

	@Carril(name = "9052", wire = Wire.Fix, section = Section.Header, format = FixDateTimestampFormat.class)
	private LocalDateTime datetimestamp;
	
	@Carril(name = "9005", wire = Wire.Fix, format = FixDateFormat.class)
	private LocalDate date;

	@Carril(name = "9006", wire = Wire.Fix, format = FixTimeFormat.class)
	private LocalTime time;

	@Carril(name = "9007", wire = Wire.Fix, format = FixTimestampFormat.class)
	private LocalTime timestamp;

	@Carril(name = "9008", wire = Wire.Fix, section = Section.Trailer, getter = "getBool", setter = "setBool")
	private Boolean bool;

	@Carril(name = "9011", wire = Wire.Fix)
	private byte[] bytes;

	@Carril(wire = Wire.Fix)
	private BigDecimal field1;

	@Carril(wire = Wire.Fix)
	private BigDecimal field2;

	@Carril(wire = Wire.Fix)
	private String constant;

	@Carril(name = "9050", wire = Wire.Fix)
	private BigDecimal optional;

	@Carril(name = "9060", wire = Wire.Fix)
	private BigDecimal prohibit;

}
