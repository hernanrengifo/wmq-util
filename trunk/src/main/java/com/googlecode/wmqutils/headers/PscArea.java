package com.googlecode.wmqutils.headers;

import java.util.ArrayList;
import java.util.List;

/*
 <psc>
 <Command>RegSub</Command>
 <Topic>t1</Topic>
 <Topic>t2</Topic>
 <SubIdentity>id123</SubIdentity>
 <QName>subq1</QName>
 <QMgrName>QM1</QMgrName>
 <SubPoint>subpoint</SubPoint>
 <Filter>filter</Filter>
 <SubName>subname</SubName>
 <SubUserData>subdata</SubUserData>
 <RegOpt>Local</RegOpt>
 <RegOpt>NewPubsOnly</RegOpt>
 <RegOpt>PubOnReqOnly</RegOpt>
 <RegOpt>CorrelAsId</RegOpt>
 <RegOpt>InformIfRet</RegOpt>
 <RegOpt>AddName</RegOpt>
 <RegOpt>FullResp</RegOpt>
 <RegOpt>JoinExcl</RegOpt>
 <RegOpt>NoAlter</RegOpt>
 <RegOpt>VariableUserId</RegOpt>
 </psc>      
 */
public class PscArea extends RFH2Area {

	public static final int COMMAND_SUBSCRIBE = 1;
	public static final int COMMAND_UNSUBSCRIBE = 2;
	public static final int COMMAND_PUBLISH = 3;
	public static final int COMMAND_REQUEST_PUBLICATION = 4;
	public static final int COMMAND_DELETE_PUBLICATION = 5;
	//public static final int COMMAND_REGISTER_PUBLISHER = 6;
	//public static final int COMMAND_UNREGISTER_PUBLISHER = 7;

	public static final int JOIN_EXCLUSIVE = 1;
	public static final int JOIN_SHARED = 2;

	private int command = COMMAND_SUBSCRIBE;
	private String subIdentity;
	private String qname;
	private String qm;
	private String subPoint;
	private String filter;
	private String subName;
	private String subUserData;
	private boolean local;
	private boolean newPubsOnly;
	private boolean pubOnReqOnly;
	private boolean correlAsId;
	private boolean informIfRetained;
	private boolean addName;
	private boolean fullResponse;
	private int joinMode;
	private boolean noAlteration;
	private boolean variableUserId;

	private List topics = new ArrayList();

	public PscArea() {
		super("psc");
	}

	public PscArea(String areaName) {
		super(areaName);
	}

	public void addTopic(String topic) {
		topics.add(topic);
	}

	public String getSubscriptionQueue() {
		return qname;
	}

	/**
	 * @param qname
	 *            the qname to set
	 */
	public void setSubscriptionQueue(String qname) {
		this.qname = qname;
	}

	/**
	 * @return the subIdentity
	 */
	public String getSubIdentity() {
		return subIdentity;
	}

	/**
	 * @param subIdentity
	 *            the subIdentity to set
	 */
	public void setSubIdentity(String subIdentity) {
		this.subIdentity = subIdentity;
	}

	/**
	 * @return the qm
	 */
	public String getSubscriptionQueueManager() {
		return qm;
	}

	/**
	 * @param qm
	 *            the qm to set
	 */
	public void setSubscriptionQueueManager(String qm) {
		this.qm = qm;
	}

	protected void propertiesToString(StringBuffer sb) {
		sb.append("<Command>");
		switch(command) {
			case COMMAND_SUBSCRIBE:
				sb.append("RegSub");
				break;
			case COMMAND_UNSUBSCRIBE:
				sb.append("DeregSub");
				break;
			case COMMAND_PUBLISH:
				sb.append("Publish");
				break;
			case COMMAND_REQUEST_PUBLICATION:
				sb.append("ReqUpdate");
				break;
			case COMMAND_DELETE_PUBLICATION:
				sb.append("DeletePub");
				break;
		}
		sb.append("</Command>");

		for (int i = 0; i < topics.size(); i++) {
			sb.append("<Topic>").append(topics.get(i)).append("</Topic>");
		}
		
		if (subName != null) {
			sb.append("<SubName>").append(subName).append("</SubName>");
		}
		if (subIdentity != null) {
			sb.append("<SubIdentity>").append(subIdentity).append(
			"</SubIdentity>");
		}
		if (qm != null) {
			sb.append("<QMgrName>").append(qm).append("</QMgrName>");
		}
		if (qname != null) {
			sb.append("<QName>").append(qname).append("</QName>");
		}

		if (subPoint != null) {
			sb.append("<SubPoint>").append(subPoint).append("</SubPoint>");
		}

		if (filter != null) {
			sb.append("<Filter>").append(filter).append("</Filter>");
		}


		if (subUserData != null) {
			sb.append("<SubUserData>").append(subUserData).append("</SubUserData>");
		}

		if (local) {
			sb.append("<RegOpt>").append("Local").append("</RegOpt>");
		}

		if (newPubsOnly) {
			sb.append("<RegOpt>").append("NewPubsOnly").append("</RegOpt>");
		}

		if (pubOnReqOnly) {
			sb.append("<RegOpt>").append("PubOnReqOnly").append("</RegOpt>");
		}

		if (correlAsId) {
			sb.append("<RegOpt>").append("CorrelAsId").append("</RegOpt>");
		}

		if (informIfRetained) {
			sb.append("<RegOpt>").append("InformIfRet").append("</RegOpt>");
		}

		if (addName) {
			sb.append("<RegOpt>").append("AddName").append("</RegOpt>");
		}

		if (fullResponse) {
			sb.append("<RegOpt>").append("FullResp").append("</RegOpt>");
		}

		if (joinMode == JOIN_EXCLUSIVE) {
			sb.append("<RegOpt>").append("JoinExcl").append("</RegOpt>");
		} else if(joinMode == JOIN_SHARED) {
			sb.append("<RegOpt>").append("JoinShared").append("</RegOpt>");
		}

		if (noAlteration) {
			sb.append("<RegOpt>").append("NoAlter").append("</RegOpt>");
		}

		if (variableUserId) {
			sb.append("<RegOpt>").append("VariableUserId").append("</RegOpt>");
		}
		
	}

	public int getCommand() {
		return command;
	}

	public void setCommand(int command) {
		this.command = command;
	}

	public String getSubPoint() {
		return subPoint;
	}

	public void setSubPoint(String subPoint) {
		this.subPoint = subPoint;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public String getSubUserData() {
		return subUserData;
	}

	public void setSubUserData(String subUserData) {
		this.subUserData = subUserData;
	}

	public boolean isLocal() {
		return local;
	}

	public void setLocal(boolean local) {
		this.local = local;
	}

	public boolean isNewPubsOnly() {
		return newPubsOnly;
	}

	public void setNewPubsOnly(boolean newPubsOnly) {
		this.newPubsOnly = newPubsOnly;
	}

	public boolean isPubOnReqOnly() {
		return pubOnReqOnly;
	}

	public void setPubOnReqOnly(boolean pubOnReqOnly) {
		this.pubOnReqOnly = pubOnReqOnly;
	}

	public boolean isCorrelAsId() {
		return correlAsId;
	}

	public void setCorrelAsId(boolean correlAsId) {
		this.correlAsId = correlAsId;
	}

	public boolean isInformIfRetained() {
		return informIfRetained;
	}

	public void setInformIfRetained(boolean informIfRetained) {
		this.informIfRetained = informIfRetained;
	}

	public boolean isAddName() {
		return addName;
	}

	public void setAddName(boolean addName) {
		this.addName = addName;
	}

	public boolean isFullResponse() {
		return fullResponse;
	}

	public void setFullResponse(boolean fullResponse) {
		this.fullResponse = fullResponse;
	}

	public int getJoinMode() {
		return joinMode;
	}

	public void setJoinMode(int joinMode) {
		this.joinMode = joinMode;
	}

	public boolean isNoAlteration() {
		return noAlteration;
	}

	public void setNoAlteration(boolean noAlteration) {
		this.noAlteration = noAlteration;
	}

	public boolean isVariableUserId() {
		return variableUserId;
	}

	public void setVariableUserId(boolean variableUserId) {
		this.variableUserId = variableUserId;
	}

	public List getTopics() {
		return topics;
	}

	public void setTopics(List topics) {
		this.topics = topics;
	}
}
