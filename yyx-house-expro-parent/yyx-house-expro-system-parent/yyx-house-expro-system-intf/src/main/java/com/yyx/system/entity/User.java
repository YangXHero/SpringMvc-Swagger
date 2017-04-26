package com.yyx.system.entity;

import java.io.Serializable;


@SuppressWarnings("serial")
public class User implements Serializable{
    private Long id;

    private String loginName;

    private Short userType;

    private String userName;

    private Long deptId;

    private String customMenu;

    private String photoUrl;

    private String password;

    private String email;

    private String mobile;

    private String telephone;

    private String hasvalid;

    private byte[] headPhoto;
    
    private String[] ids;
    
    private Integer type;

    /*
	 * @return the ids
	 */
	public String[] getIds() {
		return ids;
	}

	/*
	 * @param ids the ids to set
	 */
	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Short getUserType() {
        return userType;
    }

    public void setUserType(Short userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getCustomMenu() {
        return customMenu;
    }

    public void setCustomMenu(String customMenu) {
        this.customMenu = customMenu;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getHasvalid() {
        return hasvalid;
    }

    public void setHasvalid(String hasvalid) {
        this.hasvalid = hasvalid;
    }

    public byte[] getHeadPhoto() {
        return headPhoto;
    }

    public void setHeadPhoto(byte[] headPhoto) {
        this.headPhoto = headPhoto;
    }

	/*
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/*
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}
    
    
}