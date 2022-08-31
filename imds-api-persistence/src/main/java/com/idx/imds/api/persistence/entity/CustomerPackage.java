package com.idx.imds.api.persistence.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dtt.generic.persistence.model.BaseModel;

@Entity
@Table(name = "core_user_package")
public class CustomerPackage extends BaseModel{
	private static final long serialVersionUID = -3413536448788356497L;
	private Long id;
	private Long userId;
	private CorePackage packages;
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "user_id")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "package_id", referencedColumnName = "id")
	public CorePackage getPackages() {
		return packages;
	}

	public void setPackages(CorePackage packages) {
		this.packages = packages;
	}

	@Override
	public String toString() {
		return "CustomerPackage [id=" + id + ", userId=" + userId + ", packages=" + packages + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, packages, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerPackage other = (CustomerPackage) obj;
		return Objects.equals(id, other.id) && Objects.equals(packages, other.packages)
				&& Objects.equals(userId, other.userId);
	}
 
	

	
}
