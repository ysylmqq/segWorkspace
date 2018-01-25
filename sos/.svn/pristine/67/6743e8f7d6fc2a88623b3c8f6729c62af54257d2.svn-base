package com.gboss.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_bd_ubi_sales")
public class UbiSales extends BaseEntity {

	private static final long serialVersionUID = -138549514897823085L;

	private int sales_id;
	private String sales;
	private String id_card;
	private String email;
	private int is_deleted;

	@Id
	@Column(name = "sales_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getSales_id() {
		return sales_id;
	}

	public void setSales_id(int sales_id) {
		this.sales_id = sales_id;
	}

	@Column(name = "sales")
	public String getSales() {
		return sales;
	}

	public void setSales(String sales) {
		this.sales = sales;
	}

	@Column(name = "id_card")
	public String getId_card() {
		return id_card;
	}

	public void setId_card(String id_card) {
		this.id_card = id_card;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "is_deleted")
	public int getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(int is_deleted) {
		this.is_deleted = is_deleted;
	}

}
