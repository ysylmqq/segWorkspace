package test;

import java.util.List;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.MsgSendInfo;
import com.baidu.yun.push.model.QueryMsgStatusRequest;
import com.baidu.yun.push.model.QueryMsgStatusResponse;

public class QueryMsgStatus {

	private static final String API_KEY = "jq0Hc4cGyxGEISbZcG0PoICP";
	private static final String SECRET_KEY = "3xRmGHCwDBGCFZrxsgcBqU0FnffFrszV";
	private static final String HAIMA_API_KEY = "tjK0NA1msBXFkeqtXpNGB6KD";
	private static final String HAIMA_SECRET_KEY = "ANKPqCN1Mk8Gwr0P2kOOQfd0DYUi9xdV";
	
	public static void main(String[] args) throws PushClientException,PushServerException {
        // 1. get apiKey and secretKey from developer console
        PushKeyPair pair = new PushKeyPair(HAIMA_API_KEY, HAIMA_SECRET_KEY);

        // 2. build a BaidupushClient object to access released interfaces
        BaiduPushClient pushClient = new BaiduPushClient(pair,
                BaiduPushConstants.CHANNEL_REST_URL);

        // 3. register a YunLogHandler to get detail interacting information
        // in this request.
        pushClient.setChannelLogHandler(new YunLogHandler() {
            @Override
            public void onHandle(YunLogEvent event) {
                System.out.println(event.getMessage());
            }
        });

        try {
            // 4. specify request arguments
            String[] msgIds = { "1761295012104304187","2671326477095820971","4182998552649281899","4814192522725436299"};
            QueryMsgStatusRequest request = new QueryMsgStatusRequest()
                    .addMsgIds(msgIds)
                    .addDeviceType(3);
            // 5. http request
            QueryMsgStatusResponse response = pushClient.queryMsgStatus(request);
            // Http请求返回值解析
            System.out.println("totalNum: " + response.getTotalNum() + "\n" + "result:");
            if (null != response) {
                List<?> list = response.getMsgSendInfos();
                for (int i = 0; i < list.size(); i++) {
                    Object object = list.get(i);
                    if (object instanceof MsgSendInfo) {
                        MsgSendInfo msgSendInfo = (MsgSendInfo) object;
                        StringBuilder strBuilder = new StringBuilder();
                        strBuilder.append("List[" + i + "]: {" + "msgId = "
                                + msgSendInfo.getMsgId() + ",status = "
                                + msgSendInfo.getMsgStatus() + ",sendTime = "
                                + msgSendInfo.getSendTime() + ",successCount = "
                                + msgSendInfo.getSuccessCount());
                        strBuilder.append("}\n");
                        System.out.println(strBuilder.toString());
                    }
                }
            }
        } catch (PushClientException e) {
            if (BaiduPushConstants.ERROROPTTYPE) {
                throw e;
            } else {
                e.printStackTrace();
            }
        } catch (PushServerException e) {
            if (BaiduPushConstants.ERROROPTTYPE) {
                throw e;
            } else {
                System.out.println(String.format(
                        "requestId: %d, errorCode: %d, errorMsg: %s",
                        e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
            }
        }
	}

}
