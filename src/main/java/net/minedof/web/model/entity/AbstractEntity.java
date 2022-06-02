package net.minedof.web.model.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@SuppressWarnings("serial")
@MappedSuperclass
@EqualsAndHashCode(of="id")
@ToString(of="id")

@Getter
@Setter
 public abstract class AbstractEntity implements Serializable{

	@Id
	@Column(columnDefinition="VARCHAR(36)")
	private UUID id = UUID.randomUUID();
	
}
