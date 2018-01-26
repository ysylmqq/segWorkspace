package com.chinaGPS.gtmp.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
public class CustomerSimPOJOExample implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CustomerSimPOJOExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andSimNoIsNull() {
            addCriterion("SIM_NO is null");
            return (Criteria) this;
        }

        public Criteria andSimNoIsNotNull() {
            addCriterion("SIM_NO is not null");
            return (Criteria) this;
        }

        public Criteria andSimNoEqualTo(String value) {
            addCriterion("SIM_NO =", value, "simNo");
            return (Criteria) this;
        }

        public Criteria andSimNoNotEqualTo(String value) {
            addCriterion("SIM_NO <>", value, "simNo");
            return (Criteria) this;
        }

        public Criteria andSimNoGreaterThan(String value) {
            addCriterion("SIM_NO >", value, "simNo");
            return (Criteria) this;
        }

        public Criteria andSimNoGreaterThanOrEqualTo(String value) {
            addCriterion("SIM_NO >=", value, "simNo");
            return (Criteria) this;
        }

        public Criteria andSimNoLessThan(String value) {
            addCriterion("SIM_NO <", value, "simNo");
            return (Criteria) this;
        }

        public Criteria andSimNoLessThanOrEqualTo(String value) {
            addCriterion("SIM_NO <=", value, "simNo");
            return (Criteria) this;
        }

        public Criteria andSimNoLike(String value) {
            addCriterion("SIM_NO like", value, "simNo");
            return (Criteria) this;
        }

        public Criteria andSimNoNotLike(String value) {
            addCriterion("SIM_NO not like", value, "simNo");
            return (Criteria) this;
        }

        public Criteria andSimNoIn(List<String> values) {
            addCriterion("SIM_NO in", values, "simNo");
            return (Criteria) this;
        }

        public Criteria andSimNoNotIn(List<String> values) {
            addCriterion("SIM_NO not in", values, "simNo");
            return (Criteria) this;
        }

        public Criteria andSimNoBetween(String value1, String value2) {
            addCriterion("SIM_NO between", value1, value2, "simNo");
            return (Criteria) this;
        }

        public Criteria andSimNoNotBetween(String value1, String value2) {
            addCriterion("SIM_NO not between", value1, value2, "simNo");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNull() {
            addCriterion("START_TIME is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("START_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(Date value) {
            addCriterion("START_TIME =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(Date value) {
            addCriterion("START_TIME <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(Date value) {
            addCriterion("START_TIME >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("START_TIME >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(Date value) {
            addCriterion("START_TIME <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("START_TIME <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<Date> values) {
            addCriterion("START_TIME in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<Date> values) {
            addCriterion("START_TIME not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(Date value1, Date value2) {
            addCriterion("START_TIME between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("START_TIME not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("END_TIME is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("END_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(Date value) {
            addCriterion("END_TIME =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(Date value) {
            addCriterion("END_TIME <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(Date value) {
            addCriterion("END_TIME >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("END_TIME >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(Date value) {
            addCriterion("END_TIME <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("END_TIME <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<Date> values) {
            addCriterion("END_TIME in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<Date> values) {
            addCriterion("END_TIME not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(Date value1, Date value2) {
            addCriterion("END_TIME between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("END_TIME not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andFreePeriodIsNull() {
            addCriterion("FREE_PERIOD is null");
            return (Criteria) this;
        }

        public Criteria andFreePeriodIsNotNull() {
            addCriterion("FREE_PERIOD is not null");
            return (Criteria) this;
        }

        public Criteria andFreePeriodEqualTo(BigDecimal value) {
            addCriterion("FREE_PERIOD =", value, "freePeriod");
            return (Criteria) this;
        }

        public Criteria andFreePeriodNotEqualTo(BigDecimal value) {
            addCriterion("FREE_PERIOD <>", value, "freePeriod");
            return (Criteria) this;
        }

        public Criteria andFreePeriodGreaterThan(BigDecimal value) {
            addCriterion("FREE_PERIOD >", value, "freePeriod");
            return (Criteria) this;
        }

        public Criteria andFreePeriodGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("FREE_PERIOD >=", value, "freePeriod");
            return (Criteria) this;
        }

        public Criteria andFreePeriodLessThan(BigDecimal value) {
            addCriterion("FREE_PERIOD <", value, "freePeriod");
            return (Criteria) this;
        }

        public Criteria andFreePeriodLessThanOrEqualTo(BigDecimal value) {
            addCriterion("FREE_PERIOD <=", value, "freePeriod");
            return (Criteria) this;
        }

        public Criteria andFreePeriodIn(List<BigDecimal> values) {
            addCriterion("FREE_PERIOD in", values, "freePeriod");
            return (Criteria) this;
        }

        public Criteria andFreePeriodNotIn(List<BigDecimal> values) {
            addCriterion("FREE_PERIOD not in", values, "freePeriod");
            return (Criteria) this;
        }

        public Criteria andFreePeriodBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FREE_PERIOD between", value1, value2, "freePeriod");
            return (Criteria) this;
        }

        public Criteria andFreePeriodNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FREE_PERIOD not between", value1, value2, "freePeriod");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("STATUS is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(BigDecimal value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(BigDecimal value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(BigDecimal value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(BigDecimal value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<BigDecimal> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<BigDecimal> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStopStartTimeIsNull() {
            addCriterion("STOP_START_TIME is null");
            return (Criteria) this;
        }

        public Criteria andStopStartTimeIsNotNull() {
            addCriterion("STOP_START_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andStopStartTimeEqualTo(Date value) {
            addCriterion("STOP_START_TIME =", value, "stopStartTime");
            return (Criteria) this;
        }

        public Criteria andStopStartTimeNotEqualTo(Date value) {
            addCriterion("STOP_START_TIME <>", value, "stopStartTime");
            return (Criteria) this;
        }

        public Criteria andStopStartTimeGreaterThan(Date value) {
            addCriterion("STOP_START_TIME >", value, "stopStartTime");
            return (Criteria) this;
        }

        public Criteria andStopStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("STOP_START_TIME >=", value, "stopStartTime");
            return (Criteria) this;
        }

        public Criteria andStopStartTimeLessThan(Date value) {
            addCriterion("STOP_START_TIME <", value, "stopStartTime");
            return (Criteria) this;
        }

        public Criteria andStopStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("STOP_START_TIME <=", value, "stopStartTime");
            return (Criteria) this;
        }

        public Criteria andStopStartTimeIn(List<Date> values) {
            addCriterion("STOP_START_TIME in", values, "stopStartTime");
            return (Criteria) this;
        }

        public Criteria andStopStartTimeNotIn(List<Date> values) {
            addCriterion("STOP_START_TIME not in", values, "stopStartTime");
            return (Criteria) this;
        }

        public Criteria andStopStartTimeBetween(Date value1, Date value2) {
            addCriterion("STOP_START_TIME between", value1, value2, "stopStartTime");
            return (Criteria) this;
        }

        public Criteria andStopStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("STOP_START_TIME not between", value1, value2, "stopStartTime");
            return (Criteria) this;
        }

        public Criteria andStopEndTimeIsNull() {
            addCriterion("STOP_END_TIME is null");
            return (Criteria) this;
        }

        public Criteria andStopEndTimeIsNotNull() {
            addCriterion("STOP_END_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andStopEndTimeEqualTo(Date value) {
            addCriterion("STOP_END_TIME =", value, "stopEndTime");
            return (Criteria) this;
        }

        public Criteria andStopEndTimeNotEqualTo(Date value) {
            addCriterion("STOP_END_TIME <>", value, "stopEndTime");
            return (Criteria) this;
        }

        public Criteria andStopEndTimeGreaterThan(Date value) {
            addCriterion("STOP_END_TIME >", value, "stopEndTime");
            return (Criteria) this;
        }

        public Criteria andStopEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("STOP_END_TIME >=", value, "stopEndTime");
            return (Criteria) this;
        }

        public Criteria andStopEndTimeLessThan(Date value) {
            addCriterion("STOP_END_TIME <", value, "stopEndTime");
            return (Criteria) this;
        }

        public Criteria andStopEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("STOP_END_TIME <=", value, "stopEndTime");
            return (Criteria) this;
        }

        public Criteria andStopEndTimeIn(List<Date> values) {
            addCriterion("STOP_END_TIME in", values, "stopEndTime");
            return (Criteria) this;
        }

        public Criteria andStopEndTimeNotIn(List<Date> values) {
            addCriterion("STOP_END_TIME not in", values, "stopEndTime");
            return (Criteria) this;
        }

        public Criteria andStopEndTimeBetween(Date value1, Date value2) {
            addCriterion("STOP_END_TIME between", value1, value2, "stopEndTime");
            return (Criteria) this;
        }

        public Criteria andStopEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("STOP_END_TIME not between", value1, value2, "stopEndTime");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("REMARK is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("REMARK =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("REMARK <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("REMARK >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("REMARK >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("REMARK <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("REMARK <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("REMARK like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("REMARK not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("REMARK in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("REMARK not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("REMARK between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("REMARK not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andOperIdIsNull() {
            addCriterion("OPER_ID is null");
            return (Criteria) this;
        }

        public Criteria andOperIdIsNotNull() {
            addCriterion("OPER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOperIdEqualTo(String value) {
            addCriterion("OPER_ID =", value, "operId");
            return (Criteria) this;
        }

        public Criteria andOperIdNotEqualTo(String value) {
            addCriterion("OPER_ID <>", value, "operId");
            return (Criteria) this;
        }

        public Criteria andOperIdGreaterThan(String value) {
            addCriterion("OPER_ID >", value, "operId");
            return (Criteria) this;
        }

        public Criteria andOperIdGreaterThanOrEqualTo(String value) {
            addCriterion("OPER_ID >=", value, "operId");
            return (Criteria) this;
        }

        public Criteria andOperIdLessThan(String value) {
            addCriterion("OPER_ID <", value, "operId");
            return (Criteria) this;
        }

        public Criteria andOperIdLessThanOrEqualTo(String value) {
            addCriterion("OPER_ID <=", value, "operId");
            return (Criteria) this;
        }

        public Criteria andOperIdLike(String value) {
            addCriterion("OPER_ID like", value, "operId");
            return (Criteria) this;
        }

        public Criteria andOperIdNotLike(String value) {
            addCriterion("OPER_ID not like", value, "operId");
            return (Criteria) this;
        }

        public Criteria andOperIdIn(List<String> values) {
            addCriterion("OPER_ID in", values, "operId");
            return (Criteria) this;
        }

        public Criteria andOperIdNotIn(List<String> values) {
            addCriterion("OPER_ID not in", values, "operId");
            return (Criteria) this;
        }

        public Criteria andOperIdBetween(String value1, String value2) {
            addCriterion("OPER_ID between", value1, value2, "operId");
            return (Criteria) this;
        }

        public Criteria andOperIdNotBetween(String value1, String value2) {
            addCriterion("OPER_ID not between", value1, value2, "operId");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("CREATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("CREATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("CREATE_TIME =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("CREATE_TIME <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("CREATE_TIME >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATE_TIME >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("CREATE_TIME <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("CREATE_TIME <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("CREATE_TIME in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("CREATE_TIME not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("CREATE_TIME between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("CREATE_TIME not between", value1, value2, "createTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}