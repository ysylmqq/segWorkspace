package testtools;

import javax.jms.BytesMessage;
import cc.chinagps.gboss.comcenter.buff.ComCenterDataBuff.DeliverUnitVersion;
import cc.chinagps.gboss.comcenter.buff.GBossDataBuff.UnitVersion;

public class WriteActiveMQUnitVersion extends WriteActiveMQ {

	public WriteActiveMQUnitVersion() {
		super("unitVersion");
	}
	
	private byte[] CreateDeliverUnitVersion(long callletter) {
		
		DeliverUnitVersion.Builder deliverunitversion = DeliverUnitVersion.newBuilder();
		UnitVersion.Builder unitversion = deliverunitversion.getUnitVersionBuilder();
		unitversion.setCallLetter("" + callletter);
		unitversion.setVersion("V1.0");
		return deliverunitversion.build().toByteArray();
	}
	
	/*
	 * 向ActiveMQ内写GPS
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		long callletter = 18922819616L;
		for(int i=0; i<100; i++) {
			try {
				BytesMessage sendmsg;
				sendmsg = session.createBytesMessage();
				sendmsg.writeBytes(CreateDeliverUnitVersion(callletter + i));
				producer.send(sendmsg);
				if ((i % 10) == 0) {
					try {
						session.commit();
					} catch (Exception ex) {
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			session.commit();
		} catch (Exception ex) {
		}
	}
}
