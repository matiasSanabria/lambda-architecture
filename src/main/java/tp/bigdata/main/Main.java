package tp.bigdata.main;

import com.backtype.hadoop.pail.Pail;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import tp.bigdata.batchlayer.BatchWorkflow;
import tp.bigdata.schema.Page;
import tp.bigdata.schema.Product;
import tp.bigdata.schema.User;
import tp.bigdata.tap.DataPailStructure;
import tp.bigdata.tap.SplitDataPailStructure;

import static tp.bigdata.batchlayer.BatchWorkflow.batchWorkflow;
import static tp.bigdata.test.Data.makeUserPurchase;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Main {
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.out.println("hadoop jar <path/to/jar> <path/to/dataset>");
			return;
		}
		
		if (!new File(args[0]).exists()) {
			System.out.println("The file does not exists");
			return;
		}
		
		String path = args[0];
		
		initTestData(path);
		batchWorkflow();
	}
	
	public static void initTestData(String path) throws Exception {
		FileSystem fs = FileSystem.get(new Configuration());
		fs.delete(new Path(BatchWorkflow.DATA_ROOT), true);
		fs.mkdirs(new Path(BatchWorkflow.DATA_ROOT));
		
		Pail masterPail = Pail.create(BatchWorkflow.MASTER_ROOT, new SplitDataPailStructure());
		Pail newPail = Pail.create(BatchWorkflow.NEW_ROOT, new DataPailStructure());
		
		Pail.TypedRecordOutputStream os;
		
		File file = new File(path);
		File[] files = file.listFiles();
		
		for (File f : files) {
			BufferedReader reader = null;
			
			try {
				os = newPail.openWrite();
				
				reader = new BufferedReader(new FileReader(f));
				String line = reader.readLine();
				
				while ((line = reader.readLine()) != null) {
					String[] tokens = line.split(",");
					
//					long timeSecs = new Data().getTime();
					String userId = tokens[0];
					String username = tokens[1];
					String name = tokens[2];
					String email = tokens[3];
					String userType = tokens[4];
					String address = tokens[5];
					String birdthday = tokens[6];
					String phone = tokens[7];
					String url = tokens[8];
					String urlType = tokens[9];
					String barcode = tokens[10];
					String description = tokens[11];
					String salePrice = tokens[12];
					String stock = tokens[13];
					String quantity = tokens[14];
					
					User user = new User();
					user.set_user_id(Long.parseLong(userId));
					user.set_username(username);
					user.set_name(name);
					user.set_email(email);
					user.set_user_type(userType);
					user.set_address(address);
					user.set_birthday(birdthday);
					user.set_phone(phone);
					
					Page page = new Page();
					page.set_url(url);
					page.set_url_type(urlType);
					
					Product product = new Product();
					product.set_barcode(barcode);
					product.set_description(description);
					product.set_sale_price(Double.parseDouble(salePrice));
					product.set_stock(Double.parseDouble(stock));
					
					os.writeObject(makeUserPurchase(1, 
													user, 
													product, 
													Integer.parseInt(quantity), 
													Integer.parseInt("123456")));
					os.close();
				}
			} finally {
				if (reader != null) {
					reader.close();
				}
			}
		}
	}
}
