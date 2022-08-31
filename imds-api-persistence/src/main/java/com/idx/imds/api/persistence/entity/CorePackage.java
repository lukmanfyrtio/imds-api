package com.idx.imds.api.persistence.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.dtt.generic.persistence.model.BaseModel;

@Entity
@Table(name = "core_package")
public class CorePackage extends BaseModel {

	private static final long serialVersionUID = -1433934505204307006L;
	private Long id;
	private String packageCode;
	private String packageCred;
	private String packageName;
	private String topicName;	
	private String packageUser;
	private Boolean isSandbox;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	@Column(name = "package_code")
	public String getPackageCode() {
		return packageCode;
	}

	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}

	@Column(name = "package_cred")
	public String getPackageCred() {
		return packageCred;
	}

	public void setPackageCred(String packageCred) {
		this.packageCred = packageCred;
	}

	@Column(name = "package_name")
	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	@Column(name = "topic_name")
	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	
	
	@Column(name = "package_user")
	public String getPackageUser() {
		return packageUser;
	}

	public void setPackageUser(String packageUser) {
		this.packageUser = packageUser;
	}
	@Column(name="is_sandbox")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getIsSandbox() {
		return isSandbox;
	}
	public void setIsSandbox(Boolean isSandbox) {
		this.isSandbox = isSandbox;
	}
	@Override
	public String toString() {
		return "CorePackage [id=" + id + ", packageCode=" + packageCode + ", packageCred=" + packageCred
				+ ", packageName=" + packageName + ", topicName=" + topicName + ", packageUser=" + packageUser + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, packageCode, packageCred, packageName, packageUser, topicName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CorePackage other = (CorePackage) obj;
		return Objects.equals(id, other.id) && Objects.equals(packageCode, other.packageCode)
				&& Objects.equals(packageCred, other.packageCred) && Objects.equals(packageName, other.packageName)
				&& Objects.equals(packageUser, other.packageUser) && Objects.equals(topicName, other.topicName);
	}

}
