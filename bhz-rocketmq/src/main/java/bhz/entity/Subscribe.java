package bhz.entity;

import java.util.Date;

public class Subscribe {
    private Integer id;

    private String proname;

    private String url;

    private String topic;

    private String tag;

    private String gro;

    private String consumefromwhere;

    private String consumethreadmin;

    private String consumethreadmax;

    private String pullthresholdforqueue;

    private String consumemessagebatchmaxsize;

    private String pullbatchsize;

    private String pullinterval;

    private String businesskey;

    private String status;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname == null ? null : proname.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic == null ? null : topic.trim();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    public String getGro() {
        return gro;
    }

    public void setGro(String gro) {
        this.gro = gro == null ? null : gro.trim();
    }

    public String getConsumefromwhere() {
        return consumefromwhere;
    }

    public void setConsumefromwhere(String consumefromwhere) {
        this.consumefromwhere = consumefromwhere == null ? null : consumefromwhere.trim();
    }

    public String getConsumethreadmin() {
        return consumethreadmin;
    }

    public void setConsumethreadmin(String consumethreadmin) {
        this.consumethreadmin = consumethreadmin == null ? null : consumethreadmin.trim();
    }

    public String getConsumethreadmax() {
        return consumethreadmax;
    }

    public void setConsumethreadmax(String consumethreadmax) {
        this.consumethreadmax = consumethreadmax == null ? null : consumethreadmax.trim();
    }

    public String getPullthresholdforqueue() {
        return pullthresholdforqueue;
    }

    public void setPullthresholdforqueue(String pullthresholdforqueue) {
        this.pullthresholdforqueue = pullthresholdforqueue == null ? null : pullthresholdforqueue.trim();
    }

    public String getConsumemessagebatchmaxsize() {
        return consumemessagebatchmaxsize;
    }

    public void setConsumemessagebatchmaxsize(String consumemessagebatchmaxsize) {
        this.consumemessagebatchmaxsize = consumemessagebatchmaxsize == null ? null : consumemessagebatchmaxsize.trim();
    }

    public String getPullbatchsize() {
        return pullbatchsize;
    }

    public void setPullbatchsize(String pullbatchsize) {
        this.pullbatchsize = pullbatchsize == null ? null : pullbatchsize.trim();
    }

    public String getPullinterval() {
        return pullinterval;
    }

    public void setPullinterval(String pullinterval) {
        this.pullinterval = pullinterval == null ? null : pullinterval.trim();
    }

    public String getBusinesskey() {
        return businesskey;
    }

    public void setBusinesskey(String businesskey) {
        this.businesskey = businesskey == null ? null : businesskey.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}