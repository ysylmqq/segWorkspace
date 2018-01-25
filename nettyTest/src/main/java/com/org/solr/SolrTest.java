package com.org.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.junit.Test;

/**
 * solr当中存放的数据是在内存当种，也会持久化到磁盘，需要设置
 * 数据源---->分词器(经过分词器之后)----->索引库(存放到索引库)
 */
public class SolrTest {

	public static final String solrUlr = "http://192.168.139.128:8080/solrAdmin";
	
	public static SolrServer solrServer;
	
	static {
		//实例化solr对象
		solrServer = new HttpSolrServer(solrUlr);
	}
	
	/**
	 * 往solr服务器添加内容
	 * @throws SolrServerException
	 * @throws IOException
	 */
	@Test
	public void testAdd() throws SolrServerException, IOException{
	
		//实例化添加数据类
		SolrInputDocument doc1 = new SolrInputDocument();
		doc1.setField("id", "1004");
		doc1.setField("title", "ysy4");
		solrServer.add(doc1);
		
		SolrInputDocument doc2 = new SolrInputDocument();
		doc2.setField("id", "1005");
		doc2.setField("title", "ysy5");
		solrServer.add(doc2);
		
		SolrInputDocument doc3 = new SolrInputDocument();
		doc3.setField("id", "1006");
		doc3.setField("title", "ysy6");
		solrServer.add(doc3);
		//提交到solr服务器
		solrServer.commit();
	}
	
	
	@Test
	public void testQuery() throws SolrServerException{
		//构建solr查询分析其
		SolrQuery solrQuery = new SolrQuery();
		//q参数标示查询 有些组合查询不出来，很正常和分词器有关 AND OR NOT
		// id:[1000 TO 10007]  1000<=id<=1007;id:{1000 TO 10007}  1000<id<1007
		solrQuery.set("q", "title:ysy4 OR id:1005");
		//solrQuery.set("q", "id:[1000 TO 10007]");
		//solrQuery.addFilterQuery("id:1005");
		//查询
		//设置高亮
		solrQuery.setHighlight(true);
		solrQuery.setHighlightSimplePre("<font style='color:red'>");//高亮前缀
		solrQuery.setHighlightSimplePost("</font>");//高亮后缀
		solrQuery.setHighlightSnippets(1);//分片数
		solrQuery.setHighlightFragsize(100);//每个分片的最大长度，默认100
		solrQuery.setFacet(true)
			.setFacetMinCount(1)
			.setFacetLimit(5);
		
		
		
		/*ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("q", "*:*");*/
		QueryResponse  response = solrServer.query(solrQuery);
		SolrDocumentList result = response.getResults();
		int nums = result.size();
		System.err.println("结果是  "+nums);
		
		for (int i = 0; i < nums; i++) {
			SolrDocument doc = result.get(i);
			System.out.println(doc.toString());
		}
	}
	
	@Test
	public void testDel() throws SolrServerException, IOException{
		//删除单个
		//solrServer.deleteById("1001");
		//批量删除
		solrServer.deleteByQuery("id:1001 id:1002");
		solrServer.commit();
	}
}
