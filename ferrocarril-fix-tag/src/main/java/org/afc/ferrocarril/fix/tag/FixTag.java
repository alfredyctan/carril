package org.afc.ferrocarril.fix.tag;

public enum FixTag {

	Account(1),
	AvgPx(6),
	BeginString(8),
	BodyLength(9),
	CheckSum(10),
	CIOrdID(11),
	CumQty(14),
	Currency(15),
	ExecID(17),
	ExecTransType(20),
	HandlInst(21),
	IDSource(22),
	LastPx(31),
	LastShares(32),
	LinesOfText(33),
	MsgSeqNum(34),
	MsgType(35),
	OrderID(37),
	OrderQty(38),
	OrdStatus(39),
	OrdType(40),
	OrigClOrdID(41),
	Price(44),
	SecurityID(48),
	SenderCompID(49),
	SenderSubID(50),
	SendingTime(52),
	Shares(53),
	Side(54),
	Symbol(55),
	TargetCompID(56),
	UUID(57),
	Text(58),
	TimeInForce(59),
	TransactTime(60),
	ValidUntilTime(62),
	SettlmntType(63),
	FutSettDate(64),
	SymbolSfx(65),
	AllocID(70),
	AllocTransType(71),
	NoOrder(73),
	TradeDate(75),
	ExecBroker(76),
	NoAllocs(78),
	AllocAccount(79),
	AllocShares(80),
	AllocStatus(87),
	AllocRejCode(88),
	EncryptMethod(98),
	OrdRejReason(103),
	Issuer(106),
	SecurityDesc(107),
	HeartBtInt(108),
	ClientID(109),
	MinQty(110),
	QuoteID(117),
	NetMoney(118),
	NoExecs(124),
	DKReason(127),
	QuoteReqID(131),
	BidPx(132),
	OfferPx(133),
	BidSize(134),
	OfferSize(135),
	ResetSeqNumFlag(141),
	NoRelatedSym(146),
	HeadLine(148),
	ExecType(150),
	LeavesQty(151),
	SecurityType(167),
	BidSpotRate(188),
	BidForwardPoints(189),
	OfferSpotRate(190),
	OfferForwardPoints(191),
	OrderQty2(192),
	FutSettDate2(193),
	LastSpotRate(194),
	LastForwardPts(195),
	AllocLinkID(196),
	AllocLinkType(197),
	SecondaryOrderID(198),
	MaturityMonthYear(200),
	PutOrCall(201),
	StrikePrice(202),
	MaturityDay(205),
	OptAttribute(206),
	SecurityExchange(207),
	ContractMultiplier(231),
	CouponRate(233),
	QuoteAckStatus(297),
	QuoteCancelType(298),
	QuoteRejectReason(300),
	QuoteRequestType(303),
	TradingSessionID(336),
	TradeSesStatus(340),
	EncodedIssuerLen(348),
	EncodedIssuer(349),
	EncodedSecurityDescLen(350),
	EncodedSecurityDesc(351),
	RefMsgType(372),
	BusinessRejectReason(380),
	Product(460),
	CFICode(461),
	SecondaryClOrdID(526),
	MassStatusReqID(584),
	MassStatusReqType(585),
	BidForwardPoints2(642),
	OfferForwardPoints2(643),
	TotNumReports(911),
	StartDate(916),
	EndDate(917),
	TimeBracket(943),
	QuoteType(5082),
	BidPx2(6050),
	OfferPx2(6051),
	SecondaryQty(6054),
	LastPx2(6160),
	TenorValue(6215),
	TenorValue2(6216),
	ApplicationPingID(7532),
	StreamReference(7533),
	BidPriAmt(8901),
	BidCntAmt(8902),
	AskPriAmt(8903),
	AskCntAmt(8903),
	BidPriAmt2(8905),
	BidCntAmt2(8906),
	AskPriAmt2(8907),
	AskCntAmt2(8908),
	PriAmt(8909),
	CntAmt(8910),
	PriAmt2(8911),
	CntAmt2(8912),
	OrderedQty(9000),
	BusClient(9001),
	CustomerBranch(9002),
	DomAccount(9003),
	CINCode(9004),
	RetailName(9005),
	OriginalChannel(9006),
	FCAAccount(9007),
	FCACurrency(9008),
	BidDomCurrency(9009),
	BidDomPriRate(9010),
	BidDomCntRate(9011),
	BidDomAmount(9012),
	AskDomCurrency(9013),
	AskDomPriRate(9014),
	AskDomCntRate(9015),
	AskDomAmount(9016),
	DealtSideDomPriTRate(9017),
	DealtSideDomCntTRate(9018),
	DealtTRate(9019),
	IndirectCompTRate(9020);
	
	private static final int MAX = 10000;

	private static final FixTag[] ALL_TAGS = new FixTag[MAX];
	
	static {
		for (FixTag tag : FixTag.values()) {
			ALL_TAGS[tag.id] = tag;
		}
	}
	
	private int id;

	private String idAsString;

	private String text;
	
	private FixTag(int id) {
		this.id = id;
		this.idAsString = String.valueOf(id);
		this.text = name() + '(' + idAsString + ')'; 
    }

	public int id() {
	    return id;
    }

	public String idAsString() {
	    return idAsString;
    }

	public static FixTag fromID(int i) {
		return ALL_TAGS[i];
	}

	public String toString() {
		return text;
	}
}
