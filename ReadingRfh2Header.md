The MQRFH2 class can be used for parsing the RFH2 header. The following code demonstrates how you would get a message and loop over all properties in the usr area (if such exists in the message):

```
        MQQueueManager qm = new MQQueueManager("QM1");
        int oo = MQC.MQOO_INPUT_SHARED;
        MQQueue q = qm.accessQueue("Q1", oo);
        
        MQMessage msg = new MQMessage();
        q.get(msg);
        
        MQRFH2 rfh2 = new MQRFH2(msg);
        
        UsrArea usr = (UsrArea) rfh2.getArea("usr");
        if(usr != null) {
        	String[] propNames = usr.getPropertyNames();
        	
        	for(int i = 0; i<propNames.length; i++) {
        		System.out.println("Property " + propNames[i] + 
        				" found with value " + usr.getStringProperty(propNames[i]));
        	}
        }
```