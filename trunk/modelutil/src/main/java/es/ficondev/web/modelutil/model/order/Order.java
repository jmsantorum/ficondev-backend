package es.ficondev.web.modelutil.model.order;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import es.ficondev.web.modelutil.model.account.physical.Client;

@Entity
@Table(name="Ordere")
public class Order implements Serializable {

	private static final long serialVersionUID = -5298991031120016489L;

	private Long		orderId;
	
	private Client		client;
	private Boolean		distributed;
	
	private Long 		version;
	
	public Order() {
	}

	public Order(Client client) {
		this.client = client;
	}
	
	@SequenceGenerator( // It only takes effect for
			name = "OrderIdGenerator", // databases providing identifier
			sequenceName = "OrderSeq") // generators.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "OrderIdGenerator")
	public Long getOrderId() {
		return orderId;
	}
	
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="accountId")
	public Client getClient() {
		return client;
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
	
	public Boolean getDistributed() {
		return distributed;
	}
	
	public void setDistributed(Boolean distributed) {
		this.distributed = distributed;
	}
	
	@Version
	public Long getVersion() {
		return version;
	}
	
	public void setVersion(Long version) {
		this.version = version;
	}
}
