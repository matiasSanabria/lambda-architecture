package tp.bigdata.main;

import com.backtype.hadoop.pail.Pail;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;

import tp.bigdata.tap.DataPailStructure;
import tp.bigdata.tap.SplitDataPailStructure;

import static tp.bigdata.batchlayer.BatchWorkflow.MASTER_ROOT;
import static tp.bigdata.batchlayer.BatchWorkflow.NEW_ROOT;
import static tp.bigdata.batchlayer.BatchWorkflow.DATA_ROOT;
import static tp.bigdata.batchlayer.BatchWorkflow.batchWorkflow;
import static tp.bigdata.test.DataProcess.makeFacts;
import static tp.bigdata.batchviews.BatchViews.batchViews;

@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
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
		System.out.println("path:" + path);
		
		initTestData(path);
		batchWorkflow();
		System.out.println("fin de la ejecución del batch layer");
		batchViews();
	}
	
	public static void initTestData(String path) throws Exception {
		FileSystem fs = FileSystem.get(new Configuration());
        fs.delete(new Path(DATA_ROOT), true);
        fs.mkdirs(new Path(DATA_ROOT));
		
		Pail masterPail = Pail.create(MASTER_ROOT, new SplitDataPailStructure());
        Pail newPail = Pail.create(NEW_ROOT, new DataPailStructure());

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
					System.out.println(line);
					String[] tokens = line.split(",");
					
					String username = tokens[0];
					String url = tokens[1];
					String barcode = tokens[2];
					String quantity = tokens[3];
					String price = tokens[4];
					String date = tokens[5];
					Long timeSecs = new Date().getTime();
					
					os.writeObject(
							makeFacts(
									username, 
									url, 
									barcode,
									Integer.parseInt(quantity), 
									Double.parseDouble(price),
									date, 
									timeSecs));
				}

				os.close();
			
			} finally {
				if (reader != null) {
					reader.close();
				}
			}
		}
	}
}
