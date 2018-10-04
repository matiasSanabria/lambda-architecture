package tp.bigdata.test;

import tp.bigdata.schema.*;

public class DataProcess {
	public static Pedigree makePedigree(Long timeSecs) {
		return new Pedigree(timeSecs);
	}
	
	public static Data makeFacts(String username, String pageUrl, 
			String barcode, int quantity, String date, Long timeSecs) {
		return new Data(makePedigree(timeSecs),
				DataUnit.factsEdge(
						new FactsEdge(User.username(username),
									Page.url(pageUrl),
									Product.barcode(barcode),
									quantity,
									date
							)
					));
		
	}
}
