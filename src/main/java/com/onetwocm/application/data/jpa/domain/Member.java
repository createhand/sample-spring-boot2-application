package com.onetwocm.application.data.jpa.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@EqualsAndHashCode(of = "uid")
@ToString
public class Member {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false, unique = true, length=50)
	private String uid;
	
	@Column(nullable = false, length=200)
	private String upw;
	
	@Column(nullable = false, unique = true, length=50)
	private String uemail;
	
	@CreationTimestamp
	private Date regdate;
	
	@UpdateTimestamp
	private Date updatedate;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="uid")
	private List<MemberRole> roles;
	
}