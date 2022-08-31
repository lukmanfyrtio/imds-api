package com.idx.imds.api.persistence.entity;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.dtt.generic.persistence.model.BaseModel;

@Entity
@Table(name="core_user")
public class CustomerUser extends BaseModel{
	
	private static final long serialVersionUID = -4708703767671895216L;
	private Long id;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private Date lastLogin;
	private Boolean isActive;
	private Boolean isBlocked;
	private String userGroup;
	private String userType;
	private List<CustomerPackage> customerPackage;
	private String tokenId;
	private String tokenConfluent;
	private Date tokenMaxLifeTime;
	private Date tokenExpiredTime;
	private Date tokenCreatedTime;
	private Date suspendDate;
	private Date expiredDate;
	private Boolean isSandbox;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="user_name")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name="password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name="first_name")
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@Column(name="last_name")
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Column(name="last_login")
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	@Column(name="is_active")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	@Column(name="is_blocked")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getIsBlocked() {
		return isBlocked;
	}
	public void setIsBlocked(Boolean isBlocked) {
		this.isBlocked = isBlocked;
	}
	@Column(name="user_group")
	public String getUserGroup() {
		return userGroup;
	}
	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval=false)
	@JoinColumn(name="user_id", referencedColumnName="id")
	public List<CustomerPackage> getCustomerPackage() {
		return customerPackage;
	}
	public void setCustomerPackage(List<CustomerPackage> customerPackage) {
		this.customerPackage = customerPackage;
	}

	@Column(name="user_type")
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	

	@Column(name="token_id")
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	@Column(name="token_confluent")
	public String getTokenConfluent() {
		return tokenConfluent;
	}
	public void setTokenConfluent(String tokenConfluent) {
		this.tokenConfluent = tokenConfluent;
	}
	@Column(name="suspend_date")
	public Date getSuspendDate() {
		return suspendDate;
	}
	public void setSuspendDate(Date suspendDate) {
		this.suspendDate = suspendDate;
	}

	@Column(name="expired_date")
	public Date getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	@Column(name="token_max_life_time")
	public Date getTokenMaxLifeTime() {
		return tokenMaxLifeTime;
	}
	public void setTokenMaxLifeTime(Date tokenMaxLifeTime) {
		this.tokenMaxLifeTime = tokenMaxLifeTime;
	}

	@Column(name="token_expired_time")
	public Date getTokenExpiredTime() {
		return tokenExpiredTime;
	}
	public void setTokenExpiredTime(Date tokenExpiredTime) {
		this.tokenExpiredTime = tokenExpiredTime;
	}
	@Column(name="token_created_time")
	public Date getTokenCreatedTime() {
		return tokenCreatedTime;
	}
	public void setTokenCreatedTime(Date tokenCreatedTime) {
		this.tokenCreatedTime = tokenCreatedTime;
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
		return "CustomerUser [id=" + id + ", userName=" + userName + ", password=" + password + ", firstName="
				+ firstName + ", lastName=" + lastName + ", lastLogin=" + lastLogin + ", isActive=" + isActive
				+ ", isBlocked=" + isBlocked + ", userGroup=" + userGroup + ", userType=" + userType + ", tokenId="
				+ tokenId + ", tokenConfluent=" + tokenConfluent + ", tokenMaxLifeTime=" + tokenMaxLifeTime
				+ ", tokenExpiredTime=" + tokenExpiredTime + ", tokenCreatedTime=" + tokenCreatedTime + ", suspendDate="
				+ suspendDate + ", expiredDate=" + expiredDate + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(expiredDate, firstName, id, isActive, isBlocked, lastLogin, lastName, password, suspendDate,
				tokenConfluent, tokenCreatedTime, tokenExpiredTime, tokenId, tokenMaxLifeTime, userGroup, userName,
				userType);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerUser other = (CustomerUser) obj;
		return Objects.equals(expiredDate, other.expiredDate) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(id, other.id) && Objects.equals(isActive, other.isActive)
				&& Objects.equals(isBlocked, other.isBlocked) && Objects.equals(lastLogin, other.lastLogin)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(password, other.password)
				&& Objects.equals(suspendDate, other.suspendDate)
				&& Objects.equals(tokenConfluent, other.tokenConfluent)
				&& Objects.equals(tokenCreatedTime, other.tokenCreatedTime)
				&& Objects.equals(tokenExpiredTime, other.tokenExpiredTime) && Objects.equals(tokenId, other.tokenId)
				&& Objects.equals(tokenMaxLifeTime, other.tokenMaxLifeTime)
				&& Objects.equals(userGroup, other.userGroup) && Objects.equals(userName, other.userName)
				&& Objects.equals(userType, other.userType);
	}
}
