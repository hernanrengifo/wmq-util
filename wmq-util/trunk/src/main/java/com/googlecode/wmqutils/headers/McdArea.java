/*
 * Copyright 2007 (C) Callista Enterprise.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *	http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package com.googlecode.wmqutils.headers;

//<mcd><Msd>jms_text</Msd><Set>Set</Set><Type>Type</Type><Fmt>Format</Fmt></mcd>
public class McdArea extends RFH2Area {
	public McdArea() {
		super("mcd");
	}
	
	public McdArea(String folderName) {
		super(folderName);
	}

	public String getMessageDomain() {
		return (String) properties.get("Msd");
	}
	public void setMessageDomain(String messageDomain) {
		properties.put("Msd", messageDomain);
	}
	public String getMessageSet() {
		return (String) properties.get("Set");
	}
	public void setMessageSet(String messageSet) {
		properties.put("Set", messageSet);
	}
	public String getMessageType() {
		return (String) properties.get("Type");
	}
	public void setMessageType(String messageType) {
		properties.put("Type", messageType);
	}
	public String getOutputFormat() {
		return (String) properties.get("Fmt");
	}
	public void setOutputFormat(String outputFormat) {
		properties.put("Fmt", outputFormat);
	}
}
