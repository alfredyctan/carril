package org.afc.carril.fix.tag;

import java.util.Map;

import org.afc.util.EnumUtil;

import lombok.Getter;
import lombok.experimental.Accessors;


@Accessors(fluent=true)
@Getter
public enum FixMsgType {

	_0 ("0","Heartbeat"),
	_1 ("1","TestRequest"),
	_2 ("2","ResendRequest"),
	_3 ("3","Reject"),
	_4 ("4","SequenceReset"),
	_5 ("5","Logout"),
	_6 ("6","IOI"),
	_7 ("7","Advertisement"),
	_8 ("8","ExecutionReport"),
	_9 ("9","OrderCancelReject"),
	A ("A","Logon"),
	AA("AA","DerivativeSecurityList"),
	AB("AB","NewOrderMultileg"),
	AC("AC","MultilegOrderCancelReplace"),
	AD("AD","TradeCaptureReportRequest"),
	AE("AE","TradeCaptureReport"),
	AF("AF","OrderMassStatusRequest"),
	AG("AG","QuoteRequestReject"),
	AH("AH","RFQRequest"),
	AI("AI","QuoteStatusReport"),
	AJ("AJ","QuoteResponse"),
	AK("AK","Confirmation"),
	AL("AL","PositionMaintenanceRequest"),
	AM("AM","PositionMaintenanceReport"),
	AN("AN","RequestForPositions"),
	AO("AO","RequestForPositionsAck"),
	AP("AP","PositionReport"),
	AQ("AQ","TradeCaptureReportRequestAck"),
	AR("AR","TradeCaptureReportAck"),
	AS("AS","AllocationReport"),
	AT("AT","AllocationReportAck"),
	AU("AU","ConfirmationAck"),
	AV("AV","SettlementInstructionRequest"),
	AW("AW","AssignmentReport"),
	AX("AX","CollateralRequest"),
	AY("AY","CollateralAssignment"),
	AZ("AZ","CollateralResponse"),
	B ("B","News"),
	BA("BA","CollateralReport"),
	BB("BB","CollateralInquiry"),
	BC("BC","NetworkCounterpartySystemStatusRequest"),
	BD("BD","NetworkCounterpartySystemStatusResponse"),
	BE("BE","UserRequest"),
	BF("BF","UserResponse"),
	BG("BG","CollateralInquiryAck"),
	BH("BH","ConfirmationRequest"),
	BI("BI","TradingSessionListRequest"),
	BJ("BJ","TradingSessionList"),
	BK("BK","SecurityListUpdateReport"),
	BL("BL","AdjustedPositionReport"),
	BM("BM","AllocationInstructionAlert"),
	BN("BN","ExecutionAcknowledgement"),
	BO("BO","ContraryIntentionReport"),
	BP("BP","SecurityDefinitionUpdateReport"),
	BQ("BQ","SettlementObligationReport"),
	BR("BR","DerivativeSecurityListUpdateReport"),
	BS("BS","TradingSessionListUpdateReport"),
	BT("BT","MarketDefinitionRequest"),
	BU("BU","MarketDefinition"),
	BV("BV","MarketDefinitionUpdateReport"),
	BW("BW","ApplicationMessageRequest"),
	BX("BX","ApplicationMessageRequestAck"),
	BY("BY","ApplicationMessageReport"),
	BZ("BZ","OrderMassActionReport"),
	C ("C","Email"),
	CA("CA","OrderMassActionRequest"),
	CB("CB","UserNotification"),
	CC("CC","StreamAssignmentRequest"),
	CD("CD","StreamAssignmentReport"),
	CE("CE","StreamAssignmentReportACK"),
	D ("D","NewOrderSingle"),
	E ("E","NewOrderList"),
	F ("F","OrderCancelRequest"),
	G ("G","OrderCancelReplaceRequest"),
	H ("H","OrderStatusRequest"),
	J ("J","AllocationInstruction"),
	K ("K","ListCancelRequest"),
	L ("L","ListExecute"),
	M ("M","ListStatusRequest"),
	N ("N","ListStatus"),
	P ("P","AllocationInstructionAck"),
	Q ("Q","DontKnowTrade"),
	R ("R","QuoteRequest"),
	S ("S","Quote"),
	T ("T","SettlementInstructions"),
	V ("V","MarketDataRequest"),
	W ("W","MarketDataSnapshotFullRefresh"),
	X ("X","MarketDataIncrementalRefresh"),
	Y ("Y","MarketDataRequestReject"),
	Z ("Z","QuoteCancel"),
	a ("a","QuoteStatusRequest"),
	b ("b","MassQuoteAcknowledgement"),
	c ("c","SecurityDefinitionRequest"),
	d ("d","SecurityDefinition"),
	e ("e","SecurityStatusRequest"),
	f ("f","SecurityStatus"),
	g ("g","TradingSessionStatusRequest"),
	h ("h","TradingSessionStatus"),
	i ("i","MassQuote"),
	j ("j","BusinessMessageReject"),
	k ("k","BidRequest"),
	l ("l","BidResponse"),
	m ("m","ListStrikePrice"),
	n ("n","XMLnonFIX"),
	o ("o","RegistrationInstructions"),
	p ("p","RegistrationInstructionsResponse"),
	q ("q","OrderMassCancelRequest"),
	r ("r","OrderMassCancelReport"),
	s ("s","NewOrderCross"),
	t ("t","CrossOrderCancelReplaceRequest"),
	u ("u","CrossOrderCancelRequest"),
	v ("v","SecurityTypeRequest"),
	w ("w","SecurityTypes"),
	x ("x","SecurityListRequest"),
	y ("y","SecurityList"),
	z ("z","DerivativeSecurityListRequest");

	private static final Map<String, FixMsgType> ALL_TYPES = EnumUtil.mapper(values(), FixMsgType::id);

	private final String id;

	private final String desc;

	private final String text;
	
	private FixMsgType(String id, String desc) {
		this.id = id;
		this.desc = desc;
		this.text = id  + '(' + desc + ')';
    }

	public static FixMsgType fromId(String id) {
		return EnumUtil.from(ALL_TYPES, id);
	}

    @Override
	public String toString() {
		return text;
	}
}
