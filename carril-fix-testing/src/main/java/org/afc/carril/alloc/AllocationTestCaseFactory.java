package org.afc.carril.alloc;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import org.afc.carril.FixBaseMessage;
import org.afc.util.DateUtil;

public class AllocationTestCaseFactory {

	public static String createAllocationMessageForRawFix(int i) {
		return "8=Fix.4.2|9=627|35=J|49=INTFXSTR2|56=AFCDUKFIX|34=100|52=20110809-10:56:12.345|70=AllocID:000999|71=0|196=AllocLinkID:000001|197=1|57=UUID:1234567|73=5|11=CI:1234|198=SCI:1234|11=CI:2234|198=SCI:2234|11=CI:3234|198=SCI:3234|11=CI:4234|198=SCI:4234|11=CI:5234|198=SCI:5234|124=4|32=40000.44|17=EX:123|31=1.2124|32=50000.55|17=EX:456|31=1.2125|32=60000.66|17=EX:789|31=1.2126|32=70000.77|17=EX:ABC|31=1.2127|54=1|55=USD/JPY|48=USD/JPY|22=6|460=4|461=MRCXXX|53=1000.0|6=1.2246|15=USD|63=0|64=20110811|78=3|79=ACC0001|80=100000.12|79=ACC0002|80=200000.12|79=ACC0003|80=300000.12|118=1224600.21|75=20110809|60=20110809-10:56:12.345|58=Hello world!|10=226|";
	}

	public static String createAllocationMessageForRawFixFormat(int i) {
		return "8=Fix.4.2|35=J|49=INTFXSTR2|56=AFCDUKFIX|70=AllocID:000999|71=0|196=AllocLinkID:000001|197=1|73=5|11=CI:1234|198=SCI:1234|11=CI:2234|198=SCI:2234|11=CI:3234|198=SCI:3234|11=CI:4234|198=SCI:4234|11=CI:5234|198=SCI:5234|124=4|32=40000.44|17=EX:123|31=1.2124|32=50000.55|17=EX:456|31=1.2125|32=60000.66|17=EX:789|31=1.2126|32=70000.77|17=EX:ABC|31=1.2127|54=1|55=USD/JPY|48=USD/JPY|22=6|53=1000.0|6=1.2246|15=USD|63=0|64=20110811|78=3|79=ACC0001|80=100000.12|79=ACC0002|80=200000.12|79=ACC0003|80=300000.12|118=1224600.21|75=20110809|60=20110809-10:56:12.345|58=Hello world!|";
	}
	
	public static Allocation createAllocationObjectAsInput(int i) throws ParseException {		
		Allocation allocation = createAllocationObject(i);
		allocation.setContext(new FixBaseMessage.Context("Fix.4.2", "INTFXSTR2", "AFCDUKFIX"));

		return allocation;
	}

	public static Allocation createAllocationObjectAsExpected(int i) throws ParseException {
		Allocation allocation = createAllocationObjectAsInput(i);
		allocation.setContext(new FixBaseMessage.Context("Fix.4.2", "J", "INTFXSTR2", "AFCDUKFIX", 100, DateUtil.parseUTCTimestamp("2011-08-09 10:56:12.345")));
		return allocation;
	}

	private static Allocation createAllocationObject(int i) throws ParseException {
		Allocation allocation = new Allocation();
		allocation.setMsgID("DEAL.REQ");
		allocation.setProductType("ALC");
		allocation.setAllocID("AllocID:000999");
		allocation.setAllocLinkID("AllocLinkID:000001");
		allocation.setAllocLinkType(1);
		allocation.setAllocTransType('0');
		allocation.setAvgPx(BigDecimal.valueOf(1.2246));
		//allocation.setCFICode("MRCXXX");
		allocation.setCurrency("USD");
		allocation.setFutSettDate(DateUtil.parseUTCTimestamp("2011-08-11 00:00:00.000"));
		allocation.setIdSource("6");
		allocation.setNetMoney(BigDecimal.valueOf(1224600.21));
		//allocation.setProduct(4);
		allocation.setSecurityID("USD/JPY");
		// allocation.setSenderSubID(String) ; should from state
		allocation.setSettlementType('0');
		allocation.setShares(BigDecimal.valueOf(1000.00));
		allocation.setSide('1');
		allocation.setSymbol("USD/JPY");
		allocation.setText("Hello world!");
		allocation.setTradeDate(DateUtil.parseUTCTimestamp("2011-08-09 00:00:00.000"));
		allocation.setTransactTime(DateUtil.parseUTCTimestamp("2011-08-09 10:56:12.345"));
		//allocation.setUUID("UUID:1234567");
		List<Allocs> allocs = new LinkedList<Allocs>();
		allocs.add(new Allocs("ACC0001", BigDecimal.valueOf(100000.12)));
		allocs.add(new Allocs("ACC0002", BigDecimal.valueOf(200000.12)));
		allocs.add(new Allocs("ACC0003", BigDecimal.valueOf(300000.12)));
		allocation.setNoAllocs(allocs);

		List<Execs> execs = new LinkedList<Execs>();
		execs.add(new Execs("EX:123", BigDecimal.valueOf(40000.44), BigDecimal.valueOf(1.2124)));
		execs.add(new Execs("EX:456", BigDecimal.valueOf(50000.55), BigDecimal.valueOf(1.2125)));
		execs.add(new Execs("EX:789", BigDecimal.valueOf(60000.66), BigDecimal.valueOf(1.2126)));
		execs.add(new Execs("EX:ABC", BigDecimal.valueOf(70000.77), BigDecimal.valueOf(1.2127)));
		allocation.setNoExecs(execs);

		List<Order> orders = new LinkedList<Order>();
		orders.add(new Order("CI:1234", "SCI:1234"));
		orders.add(new Order("CI:2234", "SCI:2234"));
		orders.add(new Order("CI:3234", "SCI:3234"));
		orders.add(new Order("CI:4234", "SCI:4234"));
		orders.add(new Order("CI:5234", "SCI:5234"));
		allocation.setNoOrder(orders);

		return allocation;
	}
}
