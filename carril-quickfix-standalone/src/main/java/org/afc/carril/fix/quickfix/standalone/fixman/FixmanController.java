package org.afc.carril.fix.quickfix.standalone.fixman;

import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import org.afc.concurrent.VerboseRunnable;

@RestController
@RequestMapping(value = "/fixman",  method = RequestMethod.POST, headers = {"content-type=multipart/mixed","content-type=multipart/form-data" })
@ManagedResource(objectName = "org.afc.carril.fix.quickfix.standalone:name=FixmanController", description = "Fixman Controller")
public class FixmanController {

	private static final Logger logger = LoggerFactory.getLogger(FixmanController.class);

	@Autowired
	private Fixman fixman;

	@Autowired
	private ExecutorService executor;

	@ManagedOperation(description = "manually send fix message")
	@ManagedOperationParameters({ 
		@ManagedOperationParameter(name = "send", description = "message to be sent") 
	})
	@RequestMapping("/send")
	public ResponseEntity<String> send(@RequestPart(value = "fix", required = true) String fix) {
		executor.execute(new VerboseRunnable(
			() -> fixman.send(fix.trim())
		));
		return ResponseEntity.ok("fix msg sending request submitted");
	}

}
