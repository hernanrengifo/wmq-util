package com.googlecode.wmqutils.headers;

// <jms><Dst>queue:///Q1</Dst><Dlv>2</Dlv><Tms>1191225020984</Tms></jms>
// <mcd><jms><Dst>queue:///Q1</Dst><Rto>queue:///Q1</Rto><Tms>1191225998671</Tms><Exp>1225761533995</Exp>
// <Cid>corrIdsdffffffffffffffffffffffffffffffffffffffffffffffffffff</Cid><Pri>7</Pri><Dlv>2</Dlv></jms>
public class JmsArea extends RFH2Area {

	private static final String KEY_DESTINATION = "Dst";
	private static final String KEY_REPLY_TO = "Rto";
	private static final String KEY_TIMESTAMP = "Tms";
	private static final String KEY_EXPIRATION = "Exp";
	private static final String KEY_CORRELATION_ID = "Cid";
	private static final String KEY_PRIORITY = "Pri";
	private static final String KEY_DELIVERY_MODE = "Dlv";
	
	public JmsArea() {
		super("jms");
	}
	
	public JmsArea(String areaName) {
		super(areaName);
	}

	public String getDestination() {
		return (String) properties.get(KEY_DESTINATION);
	}

	public void setDestination(String destination) {
		setProperty(KEY_DESTINATION, destination);
	}

	public String getReplyTo() {
		return (String) properties.get(KEY_REPLY_TO);
	}

	public void setReplyTo(String replyTo) {
		setProperty(KEY_REPLY_TO, replyTo);
	}

	public long getExpiration() {
		return returnLong(KEY_EXPIRATION, 0);
	}

	public void setExpiration(long expiration) {
		setProperty(KEY_EXPIRATION, Long.toString(expiration));
	}

	public String getCorrelationId() {
		return (String) properties.get(KEY_CORRELATION_ID);
	}

	public void setCorrelationId(String correlationId) {
		setProperty(KEY_CORRELATION_ID, correlationId);
	}


	protected int returnInt(String key, int defaultValue) {
		Object value = properties.get(key);
		
		if(value == null) {
			return defaultValue;
		} else {
			return Integer.parseInt((String)value);
		}
	}

	protected long returnLong(String key, long defaultValue) {
		Object value = properties.get(key);
		
		if(value == null) {
			return defaultValue;
		} else {
			return Long.parseLong((String)value);
		}
	}
	
	
	public int getPriority() {
		return returnInt(KEY_PRIORITY, 0);
	}

	public void setPriority(int priority) {
		setProperty(KEY_PRIORITY, Integer.toString(priority));
	}

	public int getDeliveryMode() {
		return returnInt(KEY_DELIVERY_MODE, 0);
	}

	public void setDeliveryMode(int deliveryMode) {
		setProperty(KEY_DELIVERY_MODE, Integer.toString(deliveryMode));
	}

	public long getTimestamp() {
		return returnLong(KEY_TIMESTAMP, 0);
	}

	public void setTimestamp(long timestamp) {
		setProperty(KEY_TIMESTAMP, Long.toString(timestamp));
	}
	
	protected boolean includeNullValueInToString() {
		return false;
	}

}
