package tp.bigdata.test;

import tp.bigdata.schema.*;

public class Data {
	public static Pedigree makePedigree(int timeSecs) {
		return new Pedigree(timeSecs);
	}
	
	public static tp.bigdata.schema.Data makeUserPurchase(
			int purchaseId, User user, Product product, int quantity, int timeSecs) {

		UserPurchaseEdge upe = new UserPurchaseEdge(purchaseId, user, product, quantity);
		UserProperty up = new UserProperty(upe, null);
		DataUnit du = new DataUnit();		
		
		du.set_user_property(up);
		return new tp.bigdata.schema.Data(du, new Pedigree(timeSecs));
	}
}
