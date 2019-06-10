package org.afc.carril.fix.quickfix.standalone.util;

import org.afc.carril.fix.quickfix.standalone.util.modifier.DelegatedModifier;
import org.afc.carril.fix.quickfix.standalone.util.modifier.TagModifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.afc.AFCException;

import quickfix.Message;

public class FixTagResolver {
	
	private static final Logger logger = LoggerFactory.getLogger(FixTagResolver.class);

	private static final String O = "%{";

	private static final int OL = O.length();
	
	private static final String C = "}";

	private static final int CL = C.length();

	private static final int OCL = OL + CL;

	private static ThreadLocal<TagModifier> modifier = ThreadLocal.withInitial(() -> new DelegatedModifier());
	
	public static String resolve(String str, Message message) {
		return resolve(new StringBuilder(str), message).toString();
	}

	public static StringBuilder resolve(StringBuilder str, Message message) {
		int start = -1;
		int end = -1;
		int open = 0;
		int pOpen = 0;
		int pClose = -1;
		while ((pOpen = str.indexOf(O, pOpen)) > -1) {
//			logger.info("open  loop, start:[{}], end:[{}], open:[{}], pOpen:[{}], pClose:[{}]", start, end, open, pOpen, pClose);
			if (start == -1) {
				start = pOpen;
			}
			open++;
			
			pClose = pOpen;
			pOpen = str.indexOf(O, pOpen + OL);
			while ((pClose = str.indexOf(C, pClose + 1)) > -1) {
//				logger.info("close loop, start:[{}], end:[{}], open:[{}], pOpen:[{}], pClose:[{}]", start, end, open, pOpen, pClose);
				if (pOpen == -1 || pClose < pOpen) {
					//found close
					open--;
					end = pClose;
				} else {
					//found new open
					break;
				}
			}
			if (pClose == -1 && open != 0) {
//				logger.info("err loop, start:[{}], end:[{}], open:[{}], pOpen:[{}], pClose:[{}]", start, end, open, pOpen, pClose);
				throw new AFCException("incomplete close, pos:" + start);
			}
			if (open == 0 && start > -1 && end > -1) {
				String subStr = str.substring(start + OL, end);
				StringBuilder resolved = resolve(new StringBuilder(subStr), message);
				str.replace(start, end + CL, resolved.toString());
				pOpen = pOpen - subStr.length() - OCL + resolved.length();
				start = -1;
				end = -1;
			}			
		}
		
		return modifier.get().modify(str, message, null);
	}

	public static void addPluginModifier(Class<? extends TagModifier> clazz) {
		DelegatedModifier.addPluginModifier(clazz);
	}
}
