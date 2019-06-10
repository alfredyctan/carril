package org.afc.carril.fix.quickfix.message;

import org.afc.carril.message.CrudeMessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import quickfix.Message;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Crude implements CrudeMessage<Message> {

	private static final long serialVersionUID = 2134242578091851560L;

	private Message crude;
	
}
