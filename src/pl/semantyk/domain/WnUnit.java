package pl.semantyk.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "JEDNOSTKA_WN", uniqueConstraints = { @UniqueConstraint(columnNames = "ID_JEDN_WN") })
public class WnUnit implements Serializable, Cloneable {

	private static final long serialVersionUID = -8158459942832026319L;

	/**
	 * Identyfikator encji,
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_JEDN_WN", unique = true, nullable = false)
	private Integer id;

	/**
	 * nazwa jednostki leksykalnej.
	 */
	@Column(name = "NAZWA")
	private String name;

	/**
	 * pozycja jednostki.
	 */
	@Column(name = "POZYCJA")
	private String position;

	/**
	 * domena jednostki.
	 */
	@Column(name = "DOMENA")
	private String domain;

	/**
	 * wariant jednostki
	 */
	@Column(name = "WARIANT")
	private Integer variant;

	@OneToMany(mappedBy = "id.child", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<LexicalRel> children = new HashSet<>();

	@OneToMany(mappedBy = "id.parent", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<LexicalRel> parents = new HashSet<>();

	@ManyToOne(targetEntity = WikiUnit.class, optional = false)
	@JoinColumn(name = "ID_JEDN_WIKI", nullable = false)
	private WikiUnit wikiUnit;

	/**
	 * Synset
	 */
	@ManyToOne(targetEntity = Synset.class, cascade = CascadeType.ALL, optional = true)
	@JoinColumn(name = "ID_SYNSET")
	private Synset synset;

	public WnUnit() {
	}

	public WnUnit(Integer id, String name, String position, String domain,
			Integer variant) {
		this.id = id;
		this.name = name;
		this.position = position;
		this.domain = domain;
		this.variant = variant;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String pos) {
		this.position = pos;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomian(String domain) {
		this.domain = domain;
	}

	public Integer getVariant() {
		return variant;
	}

	public void setVariant(Integer variant) {
		this.variant = variant;
	}

	public Set<LexicalRel> getChildren() {
		return children;
	}

	public void setChildren(Set<LexicalRel> children) {
		this.children = children;
	}

	public Set<LexicalRel> getParents() {
		return parents;
	}

	public void setParents(Set<LexicalRel> parents) {
		this.parents = parents;
	}

	public WikiUnit getWikiUnit() {
		return wikiUnit;
	}

	public void setWikiUnit(WikiUnit wikiUnit) {
		this.wikiUnit = wikiUnit;
	}

	public Synset getSynset() {
		return synset;
	}

	public void setSynset(Synset synset) {
		this.synset = synset;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		WnUnit that = (WnUnit) o;

		if (id != null ? !id.equals(that.id) : that.id != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "LexicalUnitRaw{" + "id=" + id + ", name='" + name + '\''
				+ ", pos='" + position + '\'' + ", domain='" + domain + '\''
				+ ", variant=" + variant + '}';
	}
}
