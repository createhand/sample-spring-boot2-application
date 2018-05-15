package com.onetwocm.application.data.jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@EqualsAndHashCode(of = "rno")
@ToString
public class MemberRole {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rno;
	
	@Column
	@Enumerated(EnumType.STRING)
	private MemberRoleType roleName;
	
	public enum MemberRoleType {
		USER(), 
		AGENT(), 
		ADMIN();
		
		MemberRoleType() {
		}
	}
	
}