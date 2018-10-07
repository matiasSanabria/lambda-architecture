package tp.bigdata.batchlayer;

import com.backtype.cascading.tap.PailTap;
import com.backtype.cascading.tap.PailTap.PailTapOptions;
import com.backtype.hadoop.pail.Pail;
import com.backtype.hadoop.pail.PailSpec;
import com.backtype.hadoop.pail.PailStructure;
import cascalog.ops.IdentityBuffer;
import cascalog.ops.RandLong;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import jcascalog.Api;
import jcascalog.Subquery;
import tp.bigdata.tap.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class BatchWorkflow {
	public static final String ROOT = "/root/";
	public static final String DATA_ROOT = ROOT + "data/";
	public static final String MASTER_ROOT = DATA_ROOT + "master/";
	public static final String NEW_ROOT = DATA_ROOT + "new/";
	public static final String TEMP_ROOT = ROOT + "temp/";

	public static void batchWorkflow() throws IOException {
		setApplicationConf();

		Pail masterPail = new Pail(MASTER_ROOT);
		Pail newDataPail = new Pail(NEW_ROOT);
		ingest(masterPail, newDataPail);
	}

	public static void ingest(Pail masterPail, Pail newDataPail) throws IOException {
		FileSystem fs = FileSystem.get(new Configuration());
		Path tempRootPath = new Path(TEMP_ROOT);
		
		fs.delete(tempRootPath, true);
		fs.mkdirs(tempRootPath);
		String snapshotPath = TEMP_ROOT + "newDataSnapshot";

		Pail snapshotPail = newDataPail.snapshot(snapshotPath);
		appendNewDataToMasterDataPail(masterPail, snapshotPath);
		newDataPail.deleteSnapshot(snapshotPail);
	}

	public static void appendNewDataToMasterDataPail(Pail masterPail, String snapshotPath) throws IOException {
		Pail shreddedPail = shred(snapshotPath);
		masterPail.absorb(shreddedPail);
	}

	public static Pail shred(String dataPath) throws IOException {
		String shreddedPath = TEMP_ROOT + "shredded";

		PailTap source = dataTap(dataPath);
		PailTap sink = splitDataTap(shreddedPath);

		Subquery reduced = new Subquery("?rand", "?data").predicate(new RandLong(), "?rand")
				.predicate(source, "_", "?data-in").predicate(new IdentityBuffer(), "?data-in").out("?data");

		Api.execute(sink, new Subquery("?data").predicate(reduced, "_", "?data"));
		Pail shreddedPail = new Pail(shreddedPath);
		shreddedPail.consolidate();
		return shreddedPail;
	}

	public static void setApplicationConf() {
		Map conf = new HashMap();
        String sers = "com.backtype.hadoop.ThriftSerialization";
        sers += ",";
        sers += "org.apache.hadoop.io.serializer.WritableSerialization";
        conf.put("io.serializations", sers);
        Api.setApplicationConf(conf);
	}

	public static PailTap splitDataTap(String path) {
		PailTapOptions opts = new PailTapOptions();
		opts.spec = new PailSpec((PailStructure) new SplitDataPailStructure());
		return new PailTap(path, opts);
	}

	public static PailTap dataTap(String path) {
		PailTapOptions opts = new PailTapOptions();
		opts.spec = new PailSpec((PailStructure) new DataPailStructure());
		return new PailTap(path, opts);
	}

	/**
	 * public static void initTestData() throws Exception { FileSystem fs =
	 * FileSystem.get(new Configuration()); fs.delete(new Path(DATA_ROOT), true);
	 * fs.delete(new Path(OUTPUTS_ROOT), true); fs.mkdirs(new Path(DATA_ROOT));
	 * fs.mkdirs(new Path(OUTPUTS_ROOT + "edb"));
	 * 
	 * Pail<Data> masterPail = Pail.create(MASTER_ROOT, new
	 * SplitDataPailStructure()); Pail<Data> newPail = Pail.create(NEW_ROOT, new
	 * DataPailStructure());
	 * 
	 * TypedRecordOutputStream os = newPail.openWrite(); User user1 = new User();
	 * User user2 = new User(); Product p1 = new Product(); Product p2 = new
	 * Product();
	 * 
	 * // user1 user1.set_user_id(1); user1.set_username("user1");
	 * user1.set_name("User one"); user1.set_birthday("31/03/1992");
	 * user1.set_address("En tu cora 1"); user1.set_email("user1@gmail.com");
	 * user1.set_phone("0000000"); // user2 user2.set_user_id(2);
	 * user2.set_username("user2"); user2.set_name("User two");
	 * user2.set_birthday("31/03/1992"); user2.set_address("En tu cora 2");
	 * user2.set_email("user2@gmail.com"); user2.set_phone("0000000"); // product1
	 * p1.set_product_id(1); p1.set_barcode("01"); p1.set_description("product
	 * one"); p1.set_sale_price(2000); p1.set_stock(100); // product2
	 * p1.set_product_id(2); p1.set_barcode("02"); p1.set_description("product
	 * two"); p1.set_sale_price(4000); p1.set_stock(50);
	 * 
	 * 
	 * os.writeObject(makeUserPurchase(1, user1, p1, 1, 10));
	 * os.writeObject(makeUserPurchase(3, user1, p2, 2, 62));
	 * os.writeObject(makeUserPurchase(2, user2, p1, 4, 4000));
	 * os.writeObject(makeUserPurchase(10, user1, p2, 10, 4000));
	 * os.writeObject(makeUserPurchase(6, user2, p1, 20, 10000));
	 * os.writeObject(makeUserPurchase(5, user2, p1, 20, 10600));
	 * os.writeObject(makeEquiv(1, 3)); os.writeObject(makeEquiv(3, 5));
	 * 
	 * os.writeObject(makeUpserPurchase(2, "http://foo.com/post1", 60));
	 * os.writeObject(makeUpserPurchase(2, "http://foo.com/post3", 62));
	 * 
	 * os.close();
	 * 
	 * }
	 * 
	 * public static PailTap attributeTap(String path, final DataUnit._Fields...
	 * fields) { PailTapOptions opts = new PailTapOptions(); opts.attrs = new List[]
	 * { new ArrayList<String>() {{ for(DataUnit._Fields field: fields) { add("" +
	 * field.getThriftFieldId()); } }} }; opts.spec = new PailSpec((PailStructure)
	 * new SplitDataPailStructure());
	 * 
	 * return new PailTap(path, opts); }
	 * 
	 * public static class NormalizeURL extends CascalogFunction { public void
	 * operate(FlowProcess process, FunctionCall call) { Data data = ((Data)
	 * call.getArguments() .getObject(0)).deepCopy(); DataUnit du =
	 * data.get_data_unit();
	 * 
	 * if(du.getSetField() == DataUnit._Fields.PAGE_VIEW) {
	 * normalize(du.get_page_view().get_page()); } else if(du.getSetField() ==
	 * DataUnit._Fields.PAGE_PROPERTY) { normalize(du.get_page_property().get_id());
	 * } call.getOutputCollector().add(new Tuple(data)); }
	 * 
	 * private void normalize(Page page) { if(page.getSetField() ==
	 * Page._Fields.URL) { String urlStr = page.get_url(); try { URL url = new
	 * URL(urlStr); page.set_url(url.getProtocol() + "://" + url.getHost() +
	 * url.getPath()); } catch(MalformedURLException e) { } } }
	 * 
	 * }
	 * 
	 * public static void normalizeURLs() { Tap masterDataset =
	 * splitDataTap(DATA_ROOT + "master"); Tap outTap =
	 * splitDataTap("/tmp/swa/normalized_urls");
	 * 
	 * Api.execute(outTap, new Subquery("?normalized") .predicate(masterDataset,
	 * "_", "?raw") .predicate(new NormalizeURL(), "?raw") .out("?normalized")); }
	 * 
	 * public static void deduplicatePageviews() { Tap source = attributeTap(
	 * "/tmp/swa/normalized_pageview_users", DataUnit._Fields.PAGE_VIEW); Tap outTap
	 * = splitDataTap("/tmp/swa/unique_pageviews");
	 * 
	 * Api.execute(outTap, new Subquery("?data") .predicate(source, "_", "?data")
	 * .predicate(Option.DISTINCT, true)); }
	 * 
	 * public static class ToHourBucket extends CascalogFunction { private static
	 * final int HOUR_SECS = 60 * 60;
	 * 
	 * public void operate(FlowProcess process, FunctionCall call) { int timestamp =
	 * call.getArguments().getInteger(0); int hourBucket = timestamp / HOUR_SECS;
	 * call.getOutputCollector().add(new Tuple(hourBucket)); } }
	 * 
	 * public static class ExtractPageViewFields extends CascalogFunction { public
	 * void operate(FlowProcess process, FunctionCall call) { Data data = (Data)
	 * call.getArguments().getObject(0); PageViewEdge pageview =
	 * data.get_data_unit() .get_page_view(); if(pageview.get_page().getSetField()
	 * == Page._Fields.URL) { call.getOutputCollector().add(new Tuple(
	 * pageview.get_page().get_url(), pageview.get_user(),
	 * data.get_pedigree().get_true_as_of_secs() )); } } }
	 * 
	 * public static class EmitGranularities extends CascalogFunction { public void
	 * operate(FlowProcess process, FunctionCall call) { int hourBucket =
	 * call.getArguments().getInteger(0); int dayBucket = hourBucket / 24; int
	 * weekBucket = dayBucket / 7; int monthBucket = dayBucket / 28;
	 * 
	 * call.getOutputCollector().add(new Tuple("h", hourBucket));
	 * call.getOutputCollector().add(new Tuple("d", dayBucket));
	 * call.getOutputCollector().add(new Tuple("w", weekBucket));
	 * call.getOutputCollector().add(new Tuple("m", monthBucket)); } }
	 * 
	 * public static Subquery pageviewBatchView() { Tap source =
	 * splitDataTap("/tmp/swa/unique_pageviews");
	 * 
	 * Subquery hourlyRollup = new Subquery( "?url", "?hour-bucket", "?count")
	 * .predicate(source, "_", "?pageview") .predicate(new ExtractPageViewFields(),
	 * "?pageview") .out("?url", "?person", "?timestamp") .predicate(new
	 * ToHourBucket(), "?timestamp") .out("?hour-bucket") .predicate(new Count(),
	 * "?count");
	 * 
	 * return new Subquery( "?url", "?granularity", "?bucket", "?total-pageviews")
	 * .predicate(hourlyRollup, "?url", "?hour-bucket", "?count") .predicate(new
	 * EmitGranularities(), "?hour-bucket") .out("?granularity", "?bucket")
	 * .predicate(new Sum(), "?count").out("?total-pageviews"); }
	 * 
	 * public static class ToUrlBucketedKey extends CascalogFunction { public void
	 * operate(FlowProcess process, FunctionCall call) { String url =
	 * call.getArguments().getString(0); String gran =
	 * call.getArguments().getString(1); Integer bucket =
	 * call.getArguments().getInteger(2);
	 * 
	 * String keyStr = url + "/" + gran + "-" + bucket; try {
	 * call.getOutputCollector().add( new Tuple(keyStr.getBytes("UTF-8"))); }
	 * catch(UnsupportedEncodingException e) { throw new RuntimeException(e); } } }
	 * 
	 * public static class ToSerializedLong extends CascalogFunction { public void
	 * operate(FlowProcess process, FunctionCall call) { long val =
	 * call.getArguments().getLong(0); ByteBuffer buffer = ByteBuffer.allocate(8);
	 * buffer.putLong(val); call.getOutputCollector().add( new
	 * Tuple(buffer.array())); } }
	 * 
	 * private static String getUrlFromSerializedKey(byte[] ser) { try { String key
	 * = new String(ser, "UTF-8"); return key.substring(0, key.lastIndexOf("/")); }
	 * catch(UnsupportedEncodingException e) { throw new RuntimeException(e); } }
	 * 
	 * public static class UrlOnlyScheme implements ShardingScheme { public int
	 * shardIndex(byte[] shardKey, int shardCount) { String url =
	 * getUrlFromSerializedKey(shardKey); return url.hashCode() % shardCount; } }
	 * 
	 * public static class Debug extends CascalogFunction { public void
	 * operate(FlowProcess process, FunctionCall call) { System.out.println("DEBUG:
	 * " + call.getArguments().toString()); call.getOutputCollector().add(new
	 * Tuple(1)); } }
	 * 
	 * public static void pageviewElephantDB(Subquery pageviewBatchView) { Subquery
	 * toEdb = new Subquery("?key", "?value") .predicate(pageviewBatchView, "?url",
	 * "?granularity", "?bucket", "?total-pageviews") .predicate(new
	 * ToUrlBucketedKey(), "?url", "?granularity", "?bucket") .out("?key")
	 * .predicate(new ToSerializedLong(), "?total-pageviews") .out("?value");
	 * 
	 * Api.execute(EDB.makeKeyValTap( OUTPUTS_ROOT + "edb/pageviews", new
	 * DomainSpec(new JavaBerkDB(), new UrlOnlyScheme(), 32)), toEdb); }
	 * 
	 * public static void uniquesElephantDB(Subquery uniquesView) { Subquery toEdb =
	 * new Subquery("?key", "?value") .predicate(uniquesView, "?url",
	 * "?granularity", "?bucket", "?value") .predicate(new ToUrlBucketedKey(),
	 * "?url", "?granularity", "?bucket") .out("?key");
	 * 
	 * Api.execute(EDB.makeKeyValTap( OUTPUTS_ROOT + "edb/uniques", new
	 * DomainSpec(new JavaBerkDB(), new UrlOnlyScheme(), 32)), toEdb); }
	 * 
	 * public static class ToSerializedString extends CascalogFunction { public void
	 * operate(FlowProcess process, FunctionCall call) { String str =
	 * call.getArguments().getString(0);
	 * 
	 * try { call.getOutputCollector().add( new Tuple(str.getBytes("UTF-8"))); }
	 * catch(UnsupportedEncodingException e) { throw new RuntimeException(e); } } }
	 * 
	 * public static class ToSerializedLongPair extends CascalogFunction { public
	 * void operate(FlowProcess process, FunctionCall call) { long l1 =
	 * call.getArguments().getLong(0); long l2 = call.getArguments().getLong(1);
	 * ByteBuffer buffer = ByteBuffer.allocate(16); buffer.putLong(l1);
	 * buffer.putLong(l2); call.getOutputCollector().add(new Tuple(buffer.array()));
	 * } }
	 * 
	 * public static void bounceRateElephantDB(Subquery bounceView) { Subquery toEdb
	 * = new Subquery("?key", "?value") .predicate(bounceView, "?domain",
	 * "?bounces", "?total") .predicate(new ToSerializedString(),
	 * "?domain").out("?key") .predicate(new ToSerializedLongPair(), "?bounces",
	 * "?total").out("?value");
	 * 
	 * Api.execute(EDB.makeKeyValTap( OUTPUTS_ROOT + "edb/bounces", new
	 * DomainSpec(new JavaBerkDB(), new HashModScheme(), 32)), toEdb); }
	 * 
	 * public static class ConstructHyperLogLog extends CascalogBuffer { public void
	 * operate(FlowProcess process, BufferCall call) { HyperLogLog hll = new
	 * HyperLogLog(14); Iterator<TupleEntry> it = call.getArgumentsIterator();
	 * while(it.hasNext()) { TupleEntry tuple = it.next();
	 * hll.offer(tuple.getObject(0)); } try { call.getOutputCollector().add( new
	 * Tuple(hll.getBytes())); } catch (IOException e) { throw new
	 * RuntimeException(e); } } }
	 * 
	 * public static class MergeHyperLogLog extends CascalogBuffer { public void
	 * operate(FlowProcess process, BufferCall call) { Iterator<TupleEntry> it =
	 * call.getArgumentsIterator(); HyperLogLog curr = null; try {
	 * while(it.hasNext()) { TupleEntry tuple = it.next(); byte[] serialized =
	 * (byte[]) tuple.getObject(0); HyperLogLog hll = HyperLogLog.Builder.build(
	 * serialized); if(curr==null) { curr = hll; } else { curr = (HyperLogLog)
	 * curr.merge(hll); } } call.getOutputCollector().add( new
	 * Tuple(curr.getBytes())); } catch (IOException e) { throw new
	 * RuntimeException(e); } catch(CardinalityMergeException e) { throw new
	 * RuntimeException(e); } } }
	 * 
	 * public static Subquery uniquesView() { Tap source =
	 * splitDataTap("/tmp/swa/unique_pageviews");
	 * 
	 * Subquery hourlyRollup = new Subquery("?url", "?hour-bucket",
	 * "?hyper-log-log") .predicate(source, "_", "?pageview") .predicate( new
	 * ExtractPageViewFields(), "?pageview") .out("?url", "?user", "?timestamp")
	 * .predicate(new ToHourBucket(), "?timestamp") .out("?hour-bucket")
	 * .predicate(new ConstructHyperLogLog(), "?user") .out("?hyper-log-log");
	 * 
	 * return new Subquery( "?url", "?granularity", "?bucket", "?aggregate-hll")
	 * .predicate(hourlyRollup, "?url", "?hour-bucket", "?hourly-hll")
	 * .predicate(new EmitGranularities(), "?hour-bucket") .out("?granularity",
	 * "?bucket") .predicate(new MergeHyperLogLog(), "?hourly-hll")
	 * .out("?aggregate-hll"); }
	 * 
	 * public static class ExtractDomain extends CascalogFunction { public void
	 * operate(FlowProcess process, FunctionCall call) { String urlStr =
	 * call.getArguments().getString(0); try { URL url = new URL(urlStr);
	 * call.getOutputCollector().add( new Tuple(url.getAuthority())); }
	 * catch(MalformedURLException e) { } } }
	 * 
	 * public static class AnalyzeVisits extends CascalogBuffer { private static
	 * final int VISIT_LENGTH_SECS = 60 * 15;
	 * 
	 * public void operate(FlowProcess process, BufferCall call) {
	 * Iterator<TupleEntry> it = call.getArgumentsIterator(); int bounces = 0; int
	 * visits = 0; Integer lastTime = null; int numInCurrVisit = 0;
	 * while(it.hasNext()) { TupleEntry tuple = it.next(); int timeSecs =
	 * tuple.getInteger(0); if(lastTime == null || (timeSecs - lastTime) >
	 * VISIT_LENGTH_SECS) { visits++; if(numInCurrVisit == 1) { bounces++; }
	 * numInCurrVisit = 0; } numInCurrVisit++; } if(numInCurrVisit==1) { bounces++;
	 * } call.getOutputCollector().add(new Tuple(visits, bounces)); } }
	 * 
	 * public static Subquery bouncesView() { Tap source =
	 * splitDataTap("/tmp/swa/unique_pageviews");
	 * 
	 * Subquery userVisits = new Subquery("?domain", "?user", "?num-user-visits",
	 * "?num-user-bounces") .predicate(source, "_", "?pageview") .predicate( new
	 * ExtractPageViewFields(), "?pageview") .out("?url", "?user", "?timestamp")
	 * .predicate(new ExtractDomain(), "?url") .out("?domain")
	 * .predicate(Option.SORT, "?timestamp") .predicate(new AnalyzeVisits(),
	 * "?timestamp") .out("?num-user-visits", "?num-user-bounces");
	 * 
	 * return new Subquery("?domain", "?num-visits", "?num-bounces")
	 * .predicate(userVisits, "?domain", "_", "?num-user-visits",
	 * "?num-user-bounces") .predicate(new Sum(), "?num-user-visits")
	 * .out("?num-visits") .predicate(new Sum(), "?num-user-bounces")
	 * .out("?num-bounces"); }
	 * 
	 * public static class EdgifyEquiv extends CascalogFunction { public void
	 * operate(FlowProcess process, FunctionCall call) { Data data = (Data)
	 * call.getArguments().getObject(0); EquivEdge equiv =
	 * data.get_data_unit().get_equiv(); call.getOutputCollector().add( new
	 * Tuple(equiv.get_id1(), equiv.get_id2())); } }
	 * 
	 * public static class BidirectionalEdge extends CascalogFunction { public void
	 * operate(FlowProcess process, FunctionCall call) { Object node1 =
	 * call.getArguments().getObject(0); Object node2 =
	 * call.getArguments().getObject(1); if(!node1.equals(node2)) {
	 * call.getOutputCollector().add( new Tuple(node1, node2));
	 * call.getOutputCollector().add( new Tuple(node2, node1)); } } }
	 * 
	 * public static class IterateEdges extends CascalogBuffer { public void
	 * operate(FlowProcess process, BufferCall call) { User grouped = (User)
	 * call.getGroup() .getObject(0); TreeSet<User> allIds = new TreeSet<User>();
	 * allIds.add(grouped);
	 * 
	 * Iterator<TupleEntry> it = call.getArgumentsIterator(); while(it.hasNext()) {
	 * allIds.add((User) it.next().getObject(0)); }
	 * 
	 * Iterator<User> allIdsIt = allIds.iterator(); User smallest = allIdsIt.next();
	 * boolean isProgress = allIds.size() > 2 && !grouped.equals(smallest);
	 * while(allIdsIt.hasNext()) { User id = allIdsIt.next();
	 * call.getOutputCollector().add( new Tuple(smallest, id, isProgress)); } } }
	 * 
	 * public static class MakeNormalizedPageview extends CascalogFunction { public
	 * void operate(FlowProcess process, FunctionCall call) { User newId = (User)
	 * call.getArguments() .getObject(0); Data data = ((Data)
	 * call.getArguments().getObject(1)) .deepCopy(); if(newId!=null) {
	 * data.get_data_unit().get_page_view().set_person(newId); }
	 * call.getOutputCollector().add(new Tuple(data)); } }
	 * 
	 * public static Tap runUserNormalizationIteration(int i) { Object source =
	 * Api.hfsSeqfile( "/tmp/swa/equivs" + (i - 1)); Object sink =
	 * Api.hfsSeqfile("/tmp/swa/equivs" + i);
	 * 
	 * Object iteration = new Subquery( "?b1", "?node1", "?node2", "?is-new")
	 * .predicate(source, "?n1", "?n2") .predicate(new BidirectionalEdge(), "?n1",
	 * "?n2") .out("?b1", "?b2") .predicate(new IterateEdges(), "?b2")
	 * .out("?node1", "?node2", "?is-new");
	 * 
	 * iteration = Api.selectFields(iteration, new Fields("?node1", "?node2",
	 * "?is-new"));
	 * 
	 * Subquery newEdgeSet = new Subquery("?node1", "?node2") .predicate(iteration,
	 * "?node1", "?node2", "?is-new") .predicate(Option.DISTINCT, true);
	 * 
	 * Tap progressEdgesSink = new Hfs(new SequenceFile(cascading.tuple.Fields.ALL),
	 * "/tmp/swa/equivs" + i + "-new"); Subquery progressEdges = new
	 * Subquery("?node1", "?node2") .predicate(iteration, "?node1", "?node2", true);
	 * 
	 * Api.execute(Arrays.asList((Object)sink, progressEdgesSink),
	 * Arrays.asList((Object)newEdgeSet, progressEdges)); return progressEdgesSink;
	 * }
	 * 
	 * public static void normalizeUsers() throws IOException { Tap equivs =
	 * attributeTap("/tmp/swa/normalized_users", DataUnit._Fields.EQUIV);
	 * Api.execute(Api.hfsSeqfile("/tmp/swa/equivs0"), new Subquery("?node1",
	 * "?node2") .predicate(equivs, "_", "?data") .predicate(new EdgifyEquiv(),
	 * "?data") .out("?node1", "?node2")); int i = 1; while(true) { Tap
	 * progressEdgesSink = runUserNormalizationIteration(i);
	 * 
	 * if(!new HadoopFlowProcess(new JobConf()) .openTapForRead(progressEdgesSink)
	 * .hasNext()) { break; } i++; }
	 * 
	 * Tap pageviews = attributeTap("/tmp/swa/normalized_users",
	 * DataUnit._Fields.USER_PROPERTY); Object newIds =
	 * Api.hfsSeqfile("/tmp/swa/equivs" + i); Tap result = splitDataTap(
	 * "/tmp/swa/normalized_pageview_users");
	 * 
	 * Api.execute(result, new Subquery("?normalized-pageview") .predicate(newIds,
	 * "!!newId", "?person") .predicate(pageviews, "_", "?data") .predicate(new
	 * ExtractPageViewFields(), "?data") .out("?User", "?person", "?timestamp")
	 * .predicate(new MakeNormalizedPageview(), "!!newId",
	 * "?data").out("?normalized-pageview")); }
	 */
}