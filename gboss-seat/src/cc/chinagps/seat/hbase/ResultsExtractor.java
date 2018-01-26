package cc.chinagps.seat.hbase;

import org.apache.hadoop.hbase.client.ResultScanner;

public interface ResultsExtractor<T> {
	T extractData(ResultScanner scanner) throws Exception;
}
