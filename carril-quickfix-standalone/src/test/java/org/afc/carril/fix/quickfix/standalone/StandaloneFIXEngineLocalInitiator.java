package org.afc.carril.fix.quickfix.standalone;

import org.afc.carril.fix.quickfix.standalone.StandaloneFIXEngine;

import org.afc.SystemEnvironment;
import org.afc.util.ClasspathUtil;

public class StandaloneFIXEngineLocalInitiator {

	public static void main(String[] args) {
		SystemEnvironment.set("sfe", "local", "sg", "default", "initiator");
		ClasspathUtil.addSystemClasspath("target/config");
		StandaloneFIXEngine.main(new String[] { "--spring.profiles.active=local,sg,default,initiator" });
	}
}
